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

WOODS: Dict[str, Wood] = {
    'cypress': Wood(650, 1000),
    'tualang': Wood(696, 1300),
    'hevea': Wood(700, 1800),
    'teak': Wood(720, 1750),
    'eucalyptus': Wood(720, 2100),
    'baobab': Wood(707, 1000),
    'fig': Wood(715, 1900),
    'mahogany': Wood(790, 1600),
    'ironwood': Wood(800, 1400),
    'ipe': Wood(710, 1700),
    'araucaria': Wood(690, 1700),
    'beech': Wood(750, 1800),
    'ginkgo': Wood(720, 1800),
    'mahoe': Wood(730, 1700)
}

TFC_WOODS: Dict[str, Wood] = {
    'acacia': Wood(650, 1000),
    'ash': Wood(696, 1250),
    'aspen': Wood(620, 1200),
    'birch': Wood(652, 1750),
    'blackwood': Wood(720, 1750),
    'chestnut': Wood(651, 1500),
    'douglas_fir': Wood(707, 1500),
    'hickory': Wood(762, 2000),
    'kapok': Wood(645, 1000),
    'mangrove': Wood(750, 2500),
    'maple': Wood(745, 2000),
    'oak': Wood(728, 1800),
    'palm': Wood(730, 1250),
    'pine': Wood(627, 1250),
    'rosewood': Wood(640, 1500),
    'sequoia': Wood(612, 1750),
    'spruce': Wood(608, 1500),
    'sycamore': Wood(653, 1750),
    'white_cedar': Wood(625, 1500),
    'willow': Wood(603, 1000)
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
    'rainbow_eucalyptus': Wood(720, 2100),
    'redcedar': Wood(650, 1000),
    'gum_arabic': Wood(650, 1000),
    'black_oak': Wood(728, 1800),
    'poplar': Wood(620, 1200),
    'rubber_fig': Wood(715, 1900),
    'kauri': Wood(690, 1700),
}

ANCIENT_LOGS: Dict[str, Wood] = {
    'ancient_acacia': Wood(650, 1000),
    'ancient_ash': Wood(696, 1250),
    'ancient_aspen': Wood(611, 1000),
    'ancient_birch': Wood(652, 1750),
    'ancient_blackwood': Wood(720, 1750),
    'ancient_chestnut': Wood(651, 1500),
    'ancient_douglas_fir': Wood(707, 1500),
    'ancient_hickory': Wood(762, 2000),
    'ancient_kapok': Wood(645, 1000),
    'ancient_maple': Wood(745, 2000),
    'ancient_oak': Wood(728, 2250),
    'ancient_palm': Wood(730, 1250),
    'ancient_pine': Wood(627, 1250),
    'ancient_rosewood': Wood(640, 1500),
    'ancient_sequoia': Wood(612, 1750),
    'ancient_spruce': Wood(608, 1500),
    'ancient_sycamore': Wood(653, 1750),
    'ancient_white_cedar': Wood(625, 1500),
    'ancient_willow': Wood(603, 1000),
    'ancient_cypress': Wood(650, 1000),
    'ancient_tualang': Wood(696, 1300),
    'ancient_hevea': Wood(700, 1800),
    'ancient_teak': Wood(720, 1750),
    'ancient_eucalyptus': Wood(720, 2100),
    'ancient_baobab': Wood(707, 1000),
    'ancient_fig': Wood(715, 1900),
    'ancient_mahogany': Wood(790, 1600),
    'ancient_ironwood': Wood(800, 1400),
    'ancient_ipe': Wood(710, 1700),
    'ancient_rainbow_eucalyptus': Wood(650, 1000),
    'ancient_redcedar': Wood(700, 1100),
    'ancient_gum_arabic': Wood(650, 1100),
    'ancient_black_oak': Wood(700, 800),
    'ancient_poplar': Wood(620, 1200),
    'ancient_rubber_fig': Wood(700, 1300),
    'ancient_araucaria': Wood(690, 1700),
    'ancient_kauri': Wood(690, 1700),
    'ancient_beech': Wood(750, 1800),
    'ancient_ginkgo': Wood(720, 1800),
    'ancient_mahoe': Wood(730, 1700)
}

TREE_VARIANTS: Dict[str, str] = {
    'gum_arabic': 'acacia',
    'acacia_koa': 'acacia',
    'mpingo_blackwood': 'blackwood',
    'mountain_fir': 'douglas_fir',
    'balsam_fir': 'douglas_fir',
    'scrub_hickory': 'hickory',
    'bigleaf_maple': 'maple',
    'weeping_maple': 'maple',
    'black_oak': 'oak',
    'live_oak': 'oak',
    'stone_pine': 'pine',
    'red_pine': 'pine',
    'tamarack': 'pine',
    'giant_rosewood': 'rosewood',
    'coast_spruce': 'spruce',
    'sitka_spruce': 'spruce',
    'black_spruce': 'spruce',
    'atlas_cedar': 'white_cedar',
    'weeping_willow': 'willow',
    'rainbow_eucalyptus': 'eucalyptus',
    'mountain_ash': 'eucalyptus',
    'weeping_cypress': 'cypress',
    'redcedar': 'cypress',
    'bald_cypress': 'cypress',
    'rubber_fig': 'fig',
    'small_leaf_mahogany': 'mahogany',
    'sapele_mahogany': 'mahogany',
    'red_silk_cotton': 'kapok',
    'coast_redwood': 'sequoia',

    'poplar': 'aspen',
    'iroko_teak': 'teak',
    'flame_of_the_forest': 'teak',
    'lebombo_ironwood': 'ironwood',
    'horsetail_ironwood': 'ironwood',
    'jaggery_palm': 'palm',

    'kauri': 'araucaria',
    'columnar_araucaria': 'araucaria',
    'parana': 'araucaria',
    'huangshan_pine': 'pine',
    'dawn_redwood': 'sequoia',
    'hardy_chestnut': 'chestnut',
    'rauli_beech': 'beech',
    'black_beech': 'beech',
    'chinquapin': 'beech',
    'juniper': 'cypress',
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
