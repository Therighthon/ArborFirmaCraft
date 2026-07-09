#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

from typing import Dict, List, Set, NamedTuple, Sequence, Optional, Literal, Tuple, Any

class Plant(NamedTuple):
    clay: bool
    min_temp: float
    max_temp: float
    min_rain: float
    max_rain: float
    type: str

class Wood(NamedTuple):
    temp: float
    duration: int
    evergreen: bool
    flower_model: str

class Species(NamedTuple):
    wood: str
    evergreen: bool
    flower_model: str

class TreeVariant(NamedTuple):
    wood: str

HORIZONTAL_DIRECTIONS: List[str] = ['east', 'west', 'north', 'south']

SIGN_METALS: List[str] = [
    'bismuth_bronze',
    'black_bronze',
    'bronze',
    'copper',
    'wrought_iron',
    'steel',
    'black_steel',
    'blue_steel',
    'red_steel'
]

AFC_WOODS: Dict[str, Wood] = {
    'cypress': Wood(650, 1000, True, 'cones'),
    'tualang': Wood(696, 1300, False, 'leaves'),
    'hevea': Wood(700, 1800, False, 'leaves'),
    'teak': Wood(720, 1750, False, 'bare'),
    'eucalyptus': Wood(720, 2100, False, 'leaves'),
    'baobab': Wood(707, 1000, False, 'hanging'),
    'fig': Wood(715, 1900, False, 'leaves'),
    'mahogany': Wood(790, 1600, False, 'leaves'),
    'ironwood': Wood(800, 1400, False, 'leaves'),
    'ipe': Wood(710, 1700, False, 'bare'),
    'araucaria': Wood(690, 1700, False, 'cones'),
    'beech': Wood(750, 1800, False, 'sparse'),
    'ginkgo': Wood(720, 1800, False, 'sparse'),
    'mahoe': Wood(730, 1700, False, 'random')
}

TFC_WOODS: dict[str, Wood] = {
    'acacia': Wood(650, 1000, False, 'bare'),
    'ash': Wood(696, 1250, False, 'sparse'),
    'aspen': Wood(611, 1000, False, 'bare'),
    'birch': Wood(652, 1750, False, 'bare'),
    'blackwood': Wood(720, 1750, False, 'leaves'),
    'chestnut': Wood(651, 1500, False, 'random'),
    'douglas_fir': Wood(707, 1500, True, 'cones'),
    'hickory': Wood(762, 2000, False, 'sparse'),
    'kapok': Wood(645, 1000, False, 'bare'),
    'mangrove': Wood(655, 1000, False, 'leaves'),
    'maple': Wood(745, 2000, False, 'bare'),
    'oak': Wood(728, 2250, False, 'sparse'),
    'palm': Wood(730, 1250, False, 'leaves'),
    'pine': Wood(627, 1250, True, 'cones'),
    'rosewood': Wood(640, 1500, False, 'bare'),
    'sequoia': Wood(612, 1750, True, 'cones'),
    'spruce': Wood(608, 1500, True, 'cones'),
    'sycamore': Wood(653, 1750, False, 'sparse'),
    'white_cedar': Wood(625, 1500, True, 'leaves'),
    'willow': Wood(603, 1000, False, 'bare')
}

UNMODIFIED_TFC_WOODS = {
    # 'acacia',
    'ash',
    'aspen',
    'birch',
    'blackwood',
    'chestnut',
    'douglas_fir',
    'hickory',
    # 'kapok',
    'mangrove',
    'maple',
    'oak',
    'palm',
    # 'pine',
    'rosewood',
    # 'sequoia',
    # 'spruce',
    'sycamore',
    'white_cedar',
    'willow'
}

AFC_LOG_TYPES = {
    'baobab',
    'eucalyptus',
    'rainbow_eucalyptus',
    'hevea',
    'mahogany',
    'tualang',
    'teak',
    'cypress',
    'fig',
    'black_oak' ,
    'redcedar',
    'gum_arabic',
    'ipe',
    'ironwood',
    'poplar',
    'rubber_fig',
    'araucaria',
    'kauri',
    'beech',
    'ginkgo',
    'mahoe'
}

UNIQUE_LOGS: Dict[str, Wood] = {
    'rainbow_eucalyptus': Wood(720, 2100, False, 'null'),
    'redcedar': Wood(650, 1000, True, 'null'),
    'gum_arabic': Wood(650, 1000, False, 'null'),
    'black_oak': Wood(728, 1800, False, 'null'),
    'poplar': Wood(620, 1200, False, 'null'),
    'rubber_fig': Wood(715, 1900, False, 'null'),
    'kauri': Wood(690, 1700, True, 'null'),
}

ANCIENT_LOGS: Dict[str, Wood] = {
    'ancient_acacia': Wood(650, 1000, False, 'null'),
    'ancient_ash': Wood(696, 1250, False, 'null'),
    'ancient_aspen': Wood(611, 1000, False, 'null'),
    'ancient_birch': Wood(652, 1750, False, 'null'),
    'ancient_blackwood': Wood(720, 1750, False, 'null'),
    'ancient_chestnut': Wood(651, 1500, False, 'null'),
    'ancient_douglas_fir': Wood(707, 1500, False, 'null'),
    'ancient_hickory': Wood(762, 2000, False, 'null'),
    'ancient_kapok': Wood(645, 1000, False, 'null'),
    'ancient_maple': Wood(745, 2000, False, 'null'),
    'ancient_mangrove': Wood(750, 2500, False, 'null'),
    'ancient_oak': Wood(728, 2250, False, 'null'),
    'ancient_palm': Wood(730, 1250, False, 'null'),
    'ancient_pine': Wood(627, 1250, False, 'null'),
    'ancient_rosewood': Wood(640, 1500, False, 'null'),
    'ancient_sequoia': Wood(612, 1750, False, 'null'),
    'ancient_spruce': Wood(608, 1500, False, 'null'),
    'ancient_sycamore': Wood(653, 1750, False, 'null'),
    'ancient_white_cedar': Wood(625, 1500, False, 'null'),
    'ancient_willow': Wood(603, 1000, False, 'null'),
    'ancient_cypress': Wood(650, 1000, False, 'null'),
    'ancient_tualang': Wood(696, 1300, False, 'null'),
    'ancient_hevea': Wood(700, 1800, False, 'null'),
    'ancient_teak': Wood(720, 1750, False, 'null'),
    'ancient_eucalyptus': Wood(720, 2100, False, 'null'),
    'ancient_baobab': Wood(707, 1000, False, 'null'),
    'ancient_fig': Wood(715, 1900, False, 'null'),
    'ancient_mahogany': Wood(790, 1600, False, 'null'),
    'ancient_ironwood': Wood(800, 1400, False, 'null'),
    'ancient_ipe': Wood(710, 1700, False, 'null'),
    'ancient_rainbow_eucalyptus': Wood(650, 1000, False, 'null'),
    'ancient_redcedar': Wood(700, 1100, False, 'null'),
    'ancient_gum_arabic': Wood(650, 1100, False, 'null'),
    'ancient_black_oak': Wood(700, 800, False, 'null'),
    'ancient_poplar': Wood(620, 1200, False, 'null'),
    'ancient_rubber_fig': Wood(700, 1300, False, 'null'),
    'ancient_araucaria': Wood(690, 1700, False, 'null'),
    'ancient_kauri': Wood(690, 1700, False, 'null'),
    'ancient_beech': Wood(750, 1800, False, 'null'),
    'ancient_ginkgo': Wood(720, 1800, False, 'null'),
    'ancient_mahoe': Wood(730, 1700, False, 'null')
}

DEFAULT_SPECIES: Dict[str, str] = {
    'cypress': "slender_cypress",
    'tualang': "tualang",
    'hevea': "hevea",
    'teak': "true_teak",
    'eucalyptus': "blue_gum",
    'baobab': "baobab",
    'fig': "rusty_fig",
    'mahogany': "big_leaf_mahogany",
    'ironwood': "giant_ironwood",
    'ipe': "yellow_ipe",
    'araucaria': "monkey_puzzle",
    'beech': "silver_beech",
    'ginkgo': "ginkgo",
    'mahoe': "blue_mahoe"
}

TREE_VARIANTS: Dict[str, Species] = {
    'gum_arabic': Species('acacia', False, 'sparse'),
    'acacia_koa': Species('acacia', False, 'leaves'),
    'mpingo_blackwood': Species('blackwood', False, 'sparse'),
    'mountain_fir': Species('douglas_fir', True, 'cones'),
    'balsam_fir': Species('douglas_fir', True, 'cones'),
    'scrub_hickory': Species('hickory', False, 'bare'),
    'bigleaf_maple': Species('maple', False, 'sparse'),
    'weeping_maple': Species('maple', False, 'bare'),
    'black_oak': Species('oak', False, 'sparse'),
    'live_oak': Species('oak', False, 'sparse'),
    'stone_pine': Species('pine', True, 'cones'),
    'red_pine': Species('pine', True, 'cones'),
    'tamarack': Species('pine', False, 'sparse'),
    'giant_rosewood': Species('rosewood', False, 'bare'),
    'coast_spruce': Species('spruce', True, 'cones'),
    'sitka_spruce': Species('spruce', True, 'cones'),
    'black_spruce': Species('spruce', True, 'cones'),
    'atlas_cedar': Species('white_cedar', True, 'cones'),
    'weeping_willow': Species('willow', False, 'bare'),
    'rainbow_eucalyptus': Species('eucalyptus', False, 'leaves'),
    'mountain_ash': Species('eucalyptus', False, 'leaves'),
    'weeping_cypress': Species('cypress', True, 'cones'),
    'redcedar': Species('cypress', True, 'cones'),
    'bald_cypress': Species('cypress', False, 'cones'),
    'rubber_fig': Species('fig', False, 'leaves'),
    'small_leaf_mahogany': Species('mahogany', False, 'sparse'),
    'sapele_mahogany': Species('mahogany', False, 'leaves'),
    'red_silk_cotton': Species('kapok', False, 'bare'),
    'coast_redwood': Species('sequoia', True, 'cones'),

    'poplar': Species('aspen', False, 'bare'),
    'iroko_teak': Species('teak', False, 'leaves'),
    'flame_of_the_forest': Species('teak', False, 'bare'),
    'lebombo_ironwood': Species('ironwood', False, 'sparse'),
    'horsetail_ironwood': Species('ironwood', False, 'leaves'),
    'jaggery_palm': Species('palm', False, 'leaves'),

    'kauri': Species('araucaria', True, 'cones'),
    'columnar_araucaria': Species('araucaria', True, 'cones'),
    'parana': Species('araucaria', True, 'cones'),
    'huangshan_pine': Species('pine', True, 'cones'),
    'dawn_redwood': Species('sequoia', False, 'cones'),
    'hardy_chestnut': Species('chestnut', False, 'random'),
    'rauli_beech': Species('beech', False, 'leaves'),
    'black_beech': Species('beech', False, 'leaves'),
    'chinquapin': Species('beech', False, 'leaves'),
    'juniper': Species('cypress', True, 'cones'),
}

BLOCK_ENTITIES = ('log_pile', 'burning_log_pile', 'placed_item', 'pit_kiln', 'charcoal_forge', 'quern', 'scraping', 'crucible', 'bellows', 'composter', 'chest', 'trapped_chest', 'barrel', 'loom', 'sluice', 'tool_rack', 'sign', 'lamp', 'berry_bush', 'crop', 'firepit', 'pot', 'grill', 'pile', 'farmland', 'tick_counter', 'nest_box', 'bloomery', 'bloom', 'anvil', 'ingot_pile', 'sheet_pile', 'blast_furnace', 'large_vessel', 'powderkeg', 'sewing_table')
TANNIN_WOOD_TYPES = ('oak', 'birch', 'chestnut', 'douglas_fir', 'hickory', 'maple', 'sequoia')

def spawner(entity: str, weight: int = 1, min_count: int = 1, max_count: int = 4) -> Dict[str, Any]:
    return {
        'type': entity,
        'weight': weight,
        'minCount': min_count,
        'maxCount': max_count
    }

# This is here because it's used all over, and it's easier to import with all constants
def lang(key: str, *args) -> str:
    return ((key % args) if len(args) > 0 else key).replace('_', ' ').replace('/', ' ').title()

def simple_lang(key: str) -> str:
    return key.replace('_', ' ').replace('/', ' ').title()

# Automatically Generated by generate_trees.py
TREE_SAPLING_DROP_CHANCES = {
    'gum_arabic': 0.0292,
    'acacia_koa': 0.0193,
    'mpingo_blackwood': 0.0292,
    'mountain_fir': 0.0543,
    'balsam_fir': 0.0511,
    'scrub_hickory': 0.0780,
    'red_silk_cotton': 0.0089,
    'bigleaf_maple': 0.0175,
    'weeping_maple': 0.0545,
    'live_oak': 0.0175,
    'black_oak': 0.0174,
    'stone_pine': 0.0283,
    'red_pine': 0.0248,
    'tamarack': 0.0511,
    'giant_rosewood': 0.0127,
    'coast_redwood': 0.0132,
    'coast_spruce': 0.0238,
    'sitka_spruce': 0.0543,
    'black_spruce': 0.0318,
    'atlas_cedar': 0.0210,
    'weeping_willow': 0.0107,
    'rainbow_eucalyptus': 0.0145,
    'eucalyptus': 0.0193,
    'mountain_ash': 0.0140,
    'baobab': 0.0816,
    'hevea': 0.0133,
    'mahogany': 0.0115,
    'small_leaf_mahogany': 0.0175,
    'sapele_mahogany': 0.0089,
    'tualang': 0.0133,
    'teak': 0.0115,
    'cypress': 0.0795,
    'weeping_cypress': 0.0591,
    'redcedar': 0.0132,
    'bald_cypress': 0.0543,
    'fig': 0.0133,
    'rubber_fig': 0.0127,
    'ipe': 0.0115,
    'ironwood': 0.0089,
    'horsetail_ironwood': 0.0447,
    'lebombo_ironwood': 0.0472,
    'poplar': 0.0140,
    'iroko_teak': 0.0089,
    'flame_of_the_forest': 0.0428,
    'jaggery_palm': 0.0447,
    'araucaria': 0.0281,
    'kauri': 0.0240,
    'columnar_araucaria': 0.0305,
    'parana': 0.0554,
    'huangshan_pine': 0.0541,
    'dawn_redwood': 0.0248,
    'hardy_chestnut': 0.0179,
    'ginkgo': 0.0209,
    'mahoe': 0.0179,
    'beech': 0.0140,
    'rauli_beech': 0.0209,
    'black_beech': 0.0201,
    'chinquapin': 0.0350,
    'juniper': 0.0474,
}
