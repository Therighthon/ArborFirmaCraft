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

BOOK_LANGUAGES = ('zh_cn', 'ko_kr', 'zh_tw')
MOD_LANGUAGES = ('zh_cn', 'ru_ru', 'ko_kr', 'pt_br', 'es_es', 'ja_jp')
RESOURCE_DIR = 'src/main/resources'
EXCLUDE_PATHS: set[str] = ('src/main/resources/firmalife_compat_data', 'src/main/resources/firmalife_compat_assets', 'src/main/resources/assets')


def main():
    parser = ArgumentParser(description='Entrypoint for all common scripting infrastructure.')
    parser.add_argument('actions', nargs='+', choices=(
        'validate',  # validate no resources are changed when re-running
        'worldgen',  # only world gen data (excluding tags)
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
        elif action == 'worldgen':
            touched: set[str] = resources_at(
                TempResourceManager('afc', resource_dir=RESOURCE_DIR),
                TempResourceManager('tfc', resource_dir=RESOURCE_DIR)
            )
            touched |= EXCLUDE_PATHS
            print('Removed Stale =', utils.clean_generated_resources(RESOURCE_DIR, touched))


def clean(local: Optional[str]):
    """ Cleans all generated resources files """
    clean_at(RESOURCE_DIR)
    if local:
        clean_at(local)

def clean_at(location: str):
    for tries in range(1, 1 + 3):
        try:
            utils.clean_generated_resources(location, )
            print('Clean %s' % location)
            return
        except OSError:
            print('Failed, retrying (%d / 3)' % tries)
    print('Clean Aborted')


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

    world_gen.generate(rm)

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

class TempResourceManager(ResourceManager):

    def __init__(self, domain: str, resource_dir):
        super().__init__(domain, resource_dir)

if __name__ == '__main__':
    main()
