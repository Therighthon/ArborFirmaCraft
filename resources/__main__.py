"""
Entrypoint for all common scripting infrastructure.

Invoke like 'python resources <actions>'
Where actions can be any list of actions to take.

"""

from argparse import ArgumentParser
from mcresources import ResourceManager, utils
from mcresources.type_definitions import ResourceIdentifier, Json
from typing import Optional, Dict, Sequence

import os
import sys
import json
import difflib

import world_gen
import assets

BOOK_LANGUAGES = ('zh_cn', 'ko_kr', 'zh_tw')
MOD_LANGUAGES = ('zh_cn', 'ru_ru', 'ko_kr', 'pt_br', 'es_es', 'ja_jp')
RESOURCE_DIR = 'src/main/resources'
EXCLUDE_PATHS: set[str] = {'firmalife_compat_data', 'firmalife_compat_assets'}

def main():
    parser = ArgumentParser(description='Entrypoint for all common scripting infrastructure.')
    parser.add_argument('actions', nargs='+', choices=(
        'validate',  # validate no resources are changed when re-running
        'all', # everything that's still in python
        # 'trees',  # generate tree NBT structures from templates RUN TREES SCRIPTS DIRECTLY
    ))
    parser.add_argument('--translate', type=str, default='en_uk', help='Runs the book translation using a single provided language')
    parser.add_argument('--translate-all', action='store_true', dest='translate_all', help='Runs the book against all provided translations')
    parser.add_argument('--local', type=str, default=None, help='Points to a local minecraft instance. Used for \'book\', to generate a hot reloadable book, and used for \'clean\', to clean said instance\'s book')
    parser.add_argument('--hotswap', action='store_true', dest='hotswap', help='Causes resource generation to also generate to --hotswap-dir')
    parser.add_argument('--hotswap-dir', type=str, default='./out/production/resources', help='Used for \'--hotswap\'')

    args = parser.parse_args()
    hotswap = args.hotswap_dir if args.hotswap else None

    for action in args.actions:
        if action == 'validate':
            print('You need to write this section if you want it to do anything')
        # elif action == 'validate_assets':
        #     validate_assets.main()
        elif action == 'all':
            touched: set[str] = resources_at(
                TempResourceManager('afc', resource_dir=RESOURCE_DIR),
                TempResourceManager('tfc', resource_dir=RESOURCE_DIR)
            )
            print('Removed Stale =', clean_generated_resources(RESOURCE_DIR, touched))

def validate_resources():
    """ Validates all resources are unchanged. """
    rm = ValidatingResourceManager('tfc', RESOURCE_DIR)
    resources_at(rm, True, True, True, True, True)
    error = rm.error_files != 0

    # for lang in BOOK_LANGUAGES:
    #     try:
    #         generate_book.main(lang, None, True, rm)
    #         error |= rm.error_files != 0
    #     except AssertionError as e:
    #         print(e)
    #         error = True
    #
    # for lang in MOD_LANGUAGES:
    #     try:
    #         format_lang.main(True, (lang,))
    #     except AssertionError as e:
    #         print(e)
    #         error = True

    assert not error, 'Validation Errors Were Present'

def resources_at(
    rm: ResourceManager,
    tfc_rm: ResourceManager
) -> set[str]:

    world_gen.generate(rm, tfc_rm)
    assets.generate(rm, tfc_rm)

    # Flush
    rm.flush()
    tfc_rm.flush()

    print('New = %d, Modified = %d, Unchanged = %d, Errors = %d' % (
        rm.new_files + tfc_rm.new_files,
        rm.modified_files + tfc_rm.modified_files,
        rm.unchanged_files + tfc_rm.unchanged_files,
        rm.error_files + tfc_rm.error_files))

    return rm.written_files | tfc_rm.written_files


class ValidatingResourceManager(ResourceManager):

    def __init__(self, domain: str, resource_dir):
        super(ValidatingResourceManager, self).__init__(domain, resource_dir)
        self.validation_error = False

    def write(self, path_parts, data_to_write):
        data_to_write = utils.del_none({'__comment__': 'This file was automatically created by mcresources', **data_to_write})
        path = os.path.join(*path_parts) + '.json'
        try:
            if not os.path.isfile(path):
                print('Error: resource generation created new file \'%s\'' % path, file=sys.stderr)
                self.error_files += 1
                return
            with open(path, 'r', encoding='utf-8') as file:
                old_data = json.load(file)
            if old_data != data_to_write:
                old_text = json.dumps(old_data, indent=self.indent)
                text = json.dumps(data_to_write, indent=self.indent)
                diff = '\n'.join(difflib.unified_diff(old_text.split('\n'), text.split('\n'), 'old', 'new', n=1))
                print('Error: resource generation modified file \'%s\' Diff:\n%s\n' % (path, diff), file=sys.stderr)
                self.error_files += 1
        except Exception as e:
            self.on_error(path, e)
            self.error_files += 1

def clean_generated_resources(path: str, exclude: set[str]) -> int:
    """
    Modified from the mcresources version to allow exclusion of folders

    Recursively removes all files generated using by mcresources, as identified by the inserted comment. Removes empty directories
    :param path: The initial path to search through
    :param exclude: A set of paths to exclude from removal. Typically obtained from `ResourceManager.written_files`
    :return: The number of removed files
    """
    removed: int = 0
    for subdir in os.listdir(path):
        if not any(ex in subdir for ex in EXCLUDE_PATHS):
            sub_path = os.path.join(path, subdir)
            if os.path.isfile(sub_path):
                # File, check if valid and then delete
                sub_path = os.path.normpath(sub_path)
                if subdir.endswith('.json') and sub_path not in exclude:
                    delete = False
                    with open(sub_path, 'r', encoding='utf-8') as file:
                        if '"__comment__": "This file was automatically created by mcresources"' in file.read():
                            delete = True
                    if delete:
                        os.remove(sub_path)
                        removed += 1
            else:
                # Folder, search recursively
                removed += clean_generated_resources(sub_path, exclude)

    if not os.listdir(path):
        # Delete empty folder
        os.rmdir(path)

    return removed

class TempResourceManager(ResourceManager):

    def __init__(self, domain: str, resource_dir):
        super().__init__(domain, resource_dir)

if __name__ == '__main__':
    main()
