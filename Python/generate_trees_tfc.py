import os
from typing import Set, Any, Tuple, NamedTuple, Literal, Union

from nbtlib import nbt
from nbtlib.tag import String as StringTag, Int as IntTag

Tree = NamedTuple('Tree', name=str, feature=Literal['random', 'overlay', 'stacked'], variant=str, count=Union[int, Tuple[int, ...]], old_growth=bool)

DATA_VERSION = 2975

#path = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/src/main/resources/data/afc/structures'
#mc_path = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft./src/main/resources/assets/minecraft/textures/'
#templates = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/Python/templates/'

#TEMPLATES_DIR = './resources/structure_templates'
#STRUCTURES_DIR = '../src/main/resources/data/tfc/structures'

TEMPLATES_DIR = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/Python/structure_templates/'
STRUCTURES_DIR = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/src/main/resources/data/tfc/structures'


NORMAL_TREES = [
    Tree('kapok', 'random', 'canopy', 15, False)
]

LARGE_TREES = [
    Tree('sequoia', 'stacked', 'sequoia', (7, 8, 4, 4), True),
    Tree('kapok', 'stacked', 'kapok', (5, 1, 6), True),
    Tree('sycamore', 'random', 'round_large', 6, True)
]

DEAD_TREES = [
]


class Count:  # global mutable variables that doesn't require using the word "global" :)
    SKIPPED = 0
    NEW = 0
    MODIFIED = 0
    ERRORS = 0


def main():
    print('Verifying tree structures')
    verify_center_trunk('acacia', 35)
    verify_center_trunk('jungle', 17)
    verify_center_trunk('dead_jungle', 4)
    verify_center_trunk('dead_small', 6)
    verify_center_trunk('dead_tall', 6)
    verify_center_trunk('canopy', 15)
    verify_center_trunk('african_oak', 15)
    verify_center_trunk('emergent', 1)

    print('Tree sapling drop chances:')
    for tree in NORMAL_TREES:
        analyze_tree_leaves(tree)

    print('Making tree structures')
    for tree in NORMAL_TREES:
        make_tree_structures(tree)

    for tree in LARGE_TREES:
        make_tree_structures(tree, '_large')

    for tree in DEAD_TREES:
        make_tree_structures(tree, '_dead')

    print('New = %d, Modified = %d, Unchanged = %d, Errors = %d' % (Count.NEW, Count.MODIFIED, Count.SKIPPED, Count.ERRORS))


def make_tree_structures(tree: Tree, suffix: str = ''):
    result = tree.name + suffix
    log = tree.name
    if tree.old_growth:
        log = 'ancient_' + log
    if tree.feature == 'random':
        for i in range(1, 1 + tree.count):
            make_tree_structure(tree.variant + str(i), tree.name, str(i), result, log, tree.old_growth)
    elif tree.feature == 'overlay':
        make_tree_structure(tree.variant, tree.name, 'base', result, log, tree.old_growth)
        make_tree_structure(tree.variant + '_overlay', tree.name, 'overlay', result, log, tree.old_growth)
    elif tree.feature == 'stacked':
        for j, c in zip(range(1, 1 + len(tree.count)), tree.count):
            for i in range(1, 1 + c):
                make_tree_structure('%s_layer%d_%d' % (tree.variant, j, i), tree.name, 'layer%d_%d' % (j, i), result, log, tree.old_growth)


def make_tree_structure(template: str, wood: str, dest: str, wood_dir: str, log: str, old_growth: bool):
    f = nbt.load('%s/%s.nbt' % (TEMPLATES_DIR, template))
    prefix = 'tfc'
    if old_growth:
        prefix = 'afc'
    for block in f['palette']:
        if block['Name'] == 'minecraft:oak_log':
            block['Name'] = StringTag('%s:wood/log/%s' % (prefix, log))
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_wood':
            block['Name'] = StringTag('%s:wood/wood/%s' % (prefix, log))
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = StringTag('tfc:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        else:
            print('Structure: %s has an invalid block state \'%s\'' % (template, block['Name']))

    # Hack the data version, to avoid needing to run DFU on anything
    f['DataVersion'] = IntTag(DATA_VERSION)

    result_dir = '%s/%s/' % (STRUCTURES_DIR, wood_dir)
    os.makedirs(result_dir, exist_ok=True)

    file_name = result_dir + dest + '.nbt'
    try:
        if os.path.isfile(file_name):
            # Load and diff the original file - do not overwrite if source identical to avoid unnecessary git diffs due to gzip inconsistencies.
            original = nbt.load(file_name)
            if original == f:
                Count.SKIPPED += 1
                return
            else:
                Count.MODIFIED += 1
        else:
            Count.NEW += 1
        f.save(result_dir + dest + '.nbt')
    except:
        Count.ERRORS += 1


def analyze_tree_leaves(tree: Tree):
    if tree.feature == 'random':
        leaves = count_leaves_in_random_tree(tree.variant, tree.count)
    elif tree.feature == 'overlay':
        leaves = count_leaves_in_overlay_tree(tree.variant)
    else:
        raise NotImplementedError

    # Base value: every tree results in 3.5 saplings, on average, if every leaf was broken
    # We bias this towards returning larger values, for larger trees, as it requires more leaves to break
    chance = 3.5 / leaves
    if chance < 0.02:
        chance = 0.2 * 0.02 + 0.8 * chance
    print('%s: %.4f,' % (repr(tree.name), chance))


def count_leaves_in_random_tree(base_name: str, count: int) -> float:
    counts = [count_leaves_in_structure(base_name + str(i)) for i in range(1, 1 + count)]
    return sum(counts) / len(counts)


def count_leaves_in_overlay_tree(base_name: str) -> float:
    base = nbt.load('%s/%s.nbt' % (TEMPLATES_DIR, base_name))
    overlay = nbt.load('%s/%s_overlay.nbt' % (TEMPLATES_DIR, base_name))

    base_leaves = leaf_ids(base)
    leaves = set(pos_key(block) for block in base['blocks'] if block['state'] in base_leaves)
    count = len(leaves)

    for block in overlay['blocks']:
        if block['state'] in base_leaves and pos_key(block) not in leaves:
            count += 0.5
        elif pos_key(block) in leaves:
            count -= 0.5

    return count


def count_leaves_in_structure(file_name: str):
    file = nbt.load('%s/%s.nbt' % (TEMPLATES_DIR, file_name))
    leaves = leaf_ids(file)
    return sum(block['state'] in leaves for block in file['blocks'])


def leaf_ids(file: nbt.File) -> Set[int]:
    return {i for i, block in enumerate(file['palette']) if block['Name'] == 'minecraft:oak_leaves'}


def pos_key(tag: Any, key: str = 'pos') -> Tuple[int, int, int]:
    pos = tag[key]
    return int(pos[0]), int(pos[1]), int(pos[2])


def verify_center_trunk(prefix: str, count: int):
    for i in range(1, 1 + count):
        root = nbt.load('%s/%s%d.nbt' % (TEMPLATES_DIR, prefix, i))
        sx, sy, sz = pos_key(root, 'size')
        if sx % 2 != 1 or sz % 2 != 1:
            print('Non-odd dimensions: %d x %d x %d on %s%d' % (sx, sy, sz, prefix, i))
            continue

        center = sx // 2, 0, sz // 2
        center_state = None
        for block in root['blocks']:
            if pos_key(block) == center:
                center_state = int(block['state'])
                break

        if center_state is None:
            print('Cannot find center trunk state on %s%d' % (prefix, i))
            continue

        state = str(root['palette'][center_state]['Name'])
        if state not in ('minecraft:oak_wood', 'minecraft:oak_log'):
            print('Illegal center state, expected log, got: %s, on %s%d' % (state,prefix, i))


if __name__ == '__main__':
    main()
