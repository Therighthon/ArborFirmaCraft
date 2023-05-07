import os
from typing import Set, Any, Tuple, NamedTuple, Literal, Union

from nbtlib import nbt
from nbtlib.tag import String as StringTag, Int as IntTag

Tree = NamedTuple('Tree', name=str, feature=Literal['random', 'overlay', 'stacked'], variant=str, count=Union[int, Tuple[int, ...]], log=str)

DATA_VERSION = 2975

#path = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/src/main/resources/data/afc/structures'
#mc_path = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft./src/main/resources/assets/minecraft/textures/'
#templates = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/Python/templates/'

#TEMPLATES_DIR = './resources/structure_templates'
#STRUCTURES_DIR = '../src/main/resources/data/tfc/structures'

TEMPLATES_DIR = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/Python/structure_templates/'
STRUCTURES_DIR = 'E:/Documents/GitHub/Therighthon/ArborFirmaCraft/src/main/resources/data/tfc/structures'


NORMAL_TREES = [
    Tree('baobab', 'random', 'baobab', 11, 'baobab'),
    Tree('cypress', 'random', 'slender', 14, 'cypress'),
    Tree('eucalyptus', 'random', 'tall_branches', 18, 'eucalyptus'),
    Tree('fig', 'random', 'emergent', 6, 'fig'),
    Tree('hevea', 'random', 'tall_branches', 18, 'hevea'),
    Tree('mahogany', 'random', 'jungle', 17, 'mahogany'),
    Tree('teak', 'random', 'jungle', 17, 'teak'),
    Tree('tualang', 'random', 'emergent', 6, 'tualang'),

    Tree('gum_arabic', 'random', 'acacia', 35, 'acacia'),
    Tree('acacia_koa', 'random', 'canopy', 15, 'acacia'),
    Tree('evergreen_ash', 'overlay', 'normal', 0, 'ash'),
    Tree('mulga_blackwood', 'random', 'acacia', 35, 'blackwood'),
    Tree('mountain_fir', 'random', 'fir', 9, 'douglas_fir'),
    Tree('scrub_hickory', 'random', 'blackwood', 10, 'hickory'),
    Tree('bigleaf_maple', 'random', 'round', 23, 'maple'),
    Tree('weeping_maple', 'random', 'willow', 7, 'maple'),
    Tree('live_oak', 'random', 'round', 23, 'oak'),
    Tree('black_oak', 'random', 'african_oak', 15, 'oak'),
    Tree('stone_pine', 'random', 'stone_pine', 19, 'pine'),
    Tree('red_pine', 'random', 'red_pine', 12, 'pine'),
    Tree('giant_rosewood', 'random', 'canopy', 15, 'rosewood'),
    Tree('coast_spruce', 'random', 'conifer', 9, 'spruce'),
    Tree('sitka_spruce', 'random', 'fir', 9, 'spruce'),
    Tree('atlas_cedar', 'random', 'atlas', 17, 'white_cedar'),
    Tree('weeping_willow', 'random', 'willow_large', 14, 'willow'),

    Tree('rainbow_eucalyptus', 'random', 'canopy', 15, 'rainbow_eucalyptus'),
    Tree('mountain_ash', 'random', 'aspen', 16, 'eucalyptus'),
    Tree('weeping_cypress', 'random', 'nootka', 11, 'cypress'),
    Tree('redcedar', 'random', 'fluffyconifer', 10, 'cypress'),
    Tree('bald_cypress', 'random', 'fir', 9, 'cypress')
]

LARGE_TREES = [
    #Tree('bigleaf_maple', 'random', 'normal_large', 5, 'maple'),
    Tree('evergreen_ash', 'random', 'round', 14, 'ash'),
    Tree('mountain_fir', 'random', 'fir_large', 5, 'douglas_fir'),
    Tree('scrub_hickory', 'random', 'blackwood_large', 10, 'hickory'),
    Tree('kapok', 'random', 'kapok_large', 6, 'kapok'),
    Tree('weeping_maple', 'random', 'willow_large', 14, 'maple'),
    Tree('live_oak', 'random', 'normal_large', 5, 'oak'),
    Tree('stone_pine', 'random', 'stone_pine', 19, 'pine'),
    Tree('sitka_spruce', 'random', 'fir_large', 5, 'spruce'),
    Tree('mountain_ash', 'random', 'aspen', 16, 'eucalyptus'),
    Tree('baobab', 'random', 'old_baobab', 7, 'baobab'),
    Tree('tualang', 'random', 'emergent', 6, 'tualang'),
    Tree('bald_cypress', 'random', 'fir_large', 5, 'cypress')
]

DEAD_TREES = [
    Tree('gum_arabic', 'random', 'dead_small', 6, 'acacia'),
    Tree('acacia_koa', 'random', 'dead_jungle', 4, 'acacia'),
    Tree('evergreen_ash', 'random', 'dead_small', 6, 'ash'),
    Tree('mulga_blackwood', 'random', 'dead_small', 6, 'blackwood'),
    Tree('mountain_fir', 'random', 'dead_tall', 6, 'douglas_fir'),
    Tree('scrub_hickory', 'random', 'dead_small', 6, 'hickory'),
    Tree('bigleaf_maple', 'random', 'dead_small', 6, 'maple'),
    Tree('weeping_maple', 'random', 'dead_small', 6, 'maple'),
    Tree('live_oak', 'random', 'dead_small', 6, 'oak'),
    Tree('black_oak', 'random', 'dead_small', 6, 'oak'),
    Tree('stone_pine', 'random', 'dead_tall', 6, 'pine'),
    Tree('red_pine_', 'random', 'dead_tall', 6, 'pine'),
    Tree('giant_rosewood', 'random', 'dead_jungle', 4, 'rosewood'),
    Tree('coast_spruce', 'random', 'dead_tall', 6, 'spruce'),
    Tree('sitka_spruce', 'random', 'dead_tall', 6, 'spruce'),
    Tree('atlas_cedar', 'random', 'dead_small', 6, 'white_cedar'),
    Tree('weeping_willow', 'random', 'dead_small', 6, 'willow'),
    Tree('rainbow_eucalyptus', 'random', 'dead_jungle', 4, 'rainbow_eucalyptus'),
    Tree('eucalyptus', 'random', 'dead_tall', 6, 'eucalyptus'),
    Tree('mountain_ash', 'random', 'dead_tall', 6, 'eucalyptus'),
    Tree('hevea', 'random', 'dead_tall', 6, 'hevea'),
    Tree('mahogany', 'random', 'dead_jungle', 4, 'mahogany'),
    Tree('tualang', 'random', 'dead_tall', 6, 'tualang'),
    Tree('teak', 'random', 'dead_tall', 6, 'teak'),
    Tree('cypress', 'random', 'dead_tall', 6, 'cypress'),
    Tree('weeping_cypress', 'random', 'dead_small', 6, 'cypress'),
    Tree('redcedar', 'random', 'dead_tall', 6, 'cypress'),
    Tree('bald_cypress', 'random', 'dead_tall', 6, 'cypress'),
    Tree('baobab', 'random', 'dead_small', 6, 'baobab'),
    Tree('fig', 'random', 'dead_jungle', 4, 'fig')
]


class Count:  # global mutable variables that doesn't require using the word "global" :)
    SKIPPED = 0
    NEW = 0
    MODIFIED = 0
    ERRORS = 0


def main():
    print('Verifying tree structures')
    verify_center_trunk('acacia', 35)
    verify_center_trunk('aspen', 16)
    verify_center_trunk('jungle', 17)
    verify_center_trunk('dead_jungle', 4)
    verify_center_trunk('dead_small', 6)
    verify_center_trunk('dead_tall', 6)
    verify_center_trunk('canopy', 15)
    verify_center_trunk('african_oak', 15)
    verify_center_trunk('emergent', 6)
    verify_center_trunk('atlas', 17)
    verify_center_trunk('baobab', 11)
    verify_center_trunk('blackwood_large', 10)
    verify_center_trunk('blackwood', 10)
    verify_center_trunk('conifer', 9)
    verify_center_trunk('fir_large', 5)
    verify_center_trunk('fir', 9)
    verify_center_trunk('fluffyconifer', 10)
    verify_center_trunk('kapok_large', 6)
    verify_center_trunk('nootka', 11)
    verify_center_trunk('normal_large', 5)
    verify_center_trunk('old_baobab', 7)
    verify_center_trunk('red_pine', 12)
    verify_center_trunk('round', 23)
    verify_center_trunk('slender', 14)
    verify_center_trunk('stone_pine', 19)
    verify_center_trunk('tall_branches', 18)
    verify_center_trunk('willow_large', 14)
    verify_center_trunk('willow', 7)

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
    leaf = tree.log
    if tree.feature == 'random':
        for i in range(1, 1 + tree.count):
            make_tree_structure(tree.variant + str(i), tree.name, str(i), result, leaf)
    elif tree.feature == 'overlay':
        make_tree_structure(tree.variant, tree.name, 'base', result, leaf)
        make_tree_structure(tree.variant + '_overlay', tree.name, 'overlay', result, leaf)
    elif tree.feature == 'stacked':
        for j, c in zip(range(1, 1 + len(tree.count)), tree.count):
            for i in range(1, 1 + c):
                make_tree_structure('%s_layer%d_%d' % (tree.variant, j, i), tree.name, 'layer%d_%d' % (j, i), result, leaf)


def make_tree_structure(template: str, wood: str, dest: str, wood_dir: str, log: str):
    f = nbt.load('%s/%s.nbt' % (TEMPLATES_DIR, template))
    wood_prefix = 'tfc'
    if wood == 'baobab' or wood == 'eucalyptus' or wood == 'rainbow_eucalyptus' or wood == 'hevea' or wood == 'mahogany' or wood == 'tualang' or wood == 'teak' or wood == 'cypress' or wood == 'fig' or wood == 'mountain_ash' or wood == 'redcedar' or wood == 'weeping_cypress' or wood == 'bald_cypress':
        wood_prefix = 'afc'

    for block in f['palette']:
        if block['Name'] == 'minecraft:oak_log':
            block['Name'] = StringTag('%s:wood/log/%s' % (wood_prefix, log))
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_wood':
            block['Name'] = StringTag('%s:wood/wood/%s' % (wood_prefix, log))
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = StringTag('afc:wood/leaves/%s' % wood)
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
