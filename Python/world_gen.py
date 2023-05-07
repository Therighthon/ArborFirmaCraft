# Handles generation of all world gen objects

from typing import Union, get_args

from mcresources import ResourceManager, utils
from mcresources.type_definitions import ResourceIdentifier, JsonObject, Json, VerticalAnchor

from constants import *


def generate(rm: ResourceManager):

    # Trees / Forests
    forest_config(rm, 90, 275, 16, 40, 'acacia', True)
    forest_config(rm, 90, 275, 16, 40, 'gum_arabic', True)
    forest_config(rm, 350, 500, 24, 40, 'acacia_koa', True)
    forest_config(rm, 60, 240, -2, 10, 'ash', True)
    forest_config(rm, 80, 265, 10, 20, 'evergreen_ash', True)
    forest_config(rm, 350, 500, -18, 6, 'aspen', True)
    forest_config(rm, 125, 310, -12, 8, 'birch', True)
    forest_config(rm, 35, 150, 16, 40, 'blackwood', True)
    forest_config(rm, 85, 285, 12, 22, 'mulga_blackwood', True)
    forest_config(rm, 150, 340, -4, 12, 'chestnut', True)
    forest_config(rm, 305, 500, -12, 4, 'douglas_fir', True)
    forest_config(rm, 220, 345, -6, 0, 'mountain_fir', True)
    forest_config(rm, 210, 400, 0, 12, 'hickory', True)
    forest_config(rm, 375, 480, 14, 20, 'scrub_hickory', True)
    forest_config(rm, 360, 500, 20, 40, 'kapok', True)
    forest_config(rm, 260, 360, -8, 10, 'maple', True)
    forest_config(rm, 405, 500, -6, 12, 'bigleaf_maple', True)
    forest_config(rm, 240, 320, -2, 8, 'weeping_maple', True)
    forest_config(rm, 390, 500, 12, 20, 'oak', True)
    forest_config(rm, 150, 330, 10, 22, 'live_oak', True)
    forest_config(rm, 210, 320, -6, 10, 'oak', True)
    forest_config(rm, 200, 405, 16, 40, 'palm', True)
    forest_config(rm, 60, 270, -18, -4, 'pine', True)
    forest_config(rm, 140, 290, 6, 14, 'stone_pine', True)
    forest_config(rm, 185, 320, -10, 4, 'red_pine_', True)
    forest_config(rm, 245, 360, 12, 40, 'rosewood', True)
    forest_config(rm, 340, 440, 16, 24, 'giant_rosewood', True)
    forest_config(rm, 320, 500, -2, 10, 'sequoia', True)
    forest_config(rm, 110, 320, -20, -4, 'spruce', True)
    forest_config(rm, 320, 390, -14, 4, 'coast_spruce', True)
    forest_config(rm, 370, 500, -16, -6, 'sitka_spruce', True)
    forest_config(rm, 330, 480, -8, 14, 'sycamore', True)
    forest_config(rm, 100, 220, -16, 4, 'white_cedar', True)
    forest_config(rm, 165, 500, 8, 22, 'atlas_cedar', True)
    forest_config(rm, 330, 500, -4, 14, 'willow', True)
    forest_config(rm, 355, 500, 14, 22, 'weeping_willow', True)
    forest_config(rm, 300, 450, 16, 40, 'rainbow_eucalptus', True)
    forest_config(rm, 170, 325, 10, 24, 'eucalyptus', True)
    forest_config(rm, 420, 500, 10, 20, 'mountain_ash', True)
    forest_config(rm, 30, 190, 14, 28, 'baobab', True)
    forest_config(rm, 390, 500, 24, 40, 'hevea', True)
    forest_config(rm, 300, 430, 20, 40, 'mahogany', True)
    forest_config(rm, 360, 500, 22, 40, 'tualang', True)
    forest_config(rm, 215, 330, 22, 40, 'teak', True)
    forest_config(rm, 100, 260, 2, 14, 'slender_cypress', True)
    forest_config(rm, 290, 375, 6, 16, 'weeping_cypress', True)
    forest_config(rm, 410, 500, -10, 2, 'redcedar', True)
    forest_config(rm, 360, 500, 4, 18, 'bald_cypress', True)
    forest_config(rm, 340, 500, 18, 40, 'fig', True)
    # flat: acacia, ash, chestnut, maple, sequoia, spruce, willow

    configured_placed_feature(rm, 'forest', 'tfc:forest', {
        'entries': '#tfc:forest_trees',
        'types': {
            'none': {
                'per_chunk_chance': 0
            },
            'sparse': {
                'tree_count': uniform_int(1, 3),
                'groundcover_count': 10,
                'per_chunk_chance': 0.08,
                'bush_count': 0,
                'has_spoiler_old_growth': True
            },
            'edge': {
                'tree_count': 2,
                'groundcover_count': 15
            },
            'normal': {
                'tree_count': 5,
                'groundcover_count': 30,
                'has_spoiler_old_growth': True
            },
            'old_growth': {
                'tree_count': 7,
                'groundcover_count': 40,
                'allows_old_growth': True
            }
        }
    })

    configured_feature_tag(rm, 'forest_trees', *['tfc:tree/%s_entry' % tree for tree in WOODS.keys()])

    configured_placed_feature(rm, ('tree', 'acacia'), 'tfc:random_tree', random_config('acacia', 35, place=tree_placement_config(1, 3)))
    configured_placed_feature(rm, ('tree', 'acacia_large'), 'tfc:random_tree', random_config('acacia', 6, 2, '_large', place=tree_placement_config(2, 5)))
    configured_placed_feature(rm, ('tree', 'acacia_dead'), 'tfc:random_tree', random_config('acacia', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'ash'), 'tfc:overlay_tree', overlay_config('ash', 3, 5, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'ash_large'), 'tfc:random_tree', random_config('ash', 5, 2, '_large', place=tree_placement_config(2, 5)))
    configured_placed_feature(rm, ('tree', 'ash_dead'), 'tfc:random_tree', random_config('ash', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'aspen'), 'tfc:random_tree', random_config('aspen', 16, trunk=[3, 5, 1], place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'aspen_dead'), 'tfc:random_tree', random_config('aspen', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'birch'), 'tfc:random_tree', random_config('birch', 16, trunk=[2, 3, 1], place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'birch_dead'), 'tfc:random_tree', random_config('birch', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'blackwood'), 'tfc:random_tree', random_config('blackwood', 10, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'blackwood_large'), 'tfc:random_tree', random_config('blackwood', 10, 1, '_large', place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'blackwood_dead'), 'tfc:random_tree', random_config('blackwood', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    #configured_placed_feature(rm, ('tree', 'chestnut'), 'tfc:overlay_tree', overlay_config('chestnut', 2, 4, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'chestnut_dead'), 'tfc:random_tree', random_config('chestnut', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    #configured_placed_feature(rm, ('tree', 'douglas_fir'), 'tfc:random_tree', random_config('douglas_fir', 9, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'douglas_fir_large'), 'tfc:random_tree', random_config('douglas_fir', 5, 2, '_large', place=tree_placement_config(1, 9)))
    configured_placed_feature(rm, ('tree', 'douglas_fir_dead'), 'tfc:random_tree', random_config('douglas_fir', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    #configured_placed_feature(rm, ('tree', 'hickory'), 'tfc:random_tree', random_config('hickory', 9, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'hickory_large'), 'tfc:random_tree', random_config('hickory', 5, 2, '_large', place=tree_placement_config(1, 9)))
    configured_placed_feature(rm, ('tree', 'hickory_dead'), 'tfc:random_tree', random_config('hickory', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'maple'), 'tfc:overlay_tree', overlay_config('maple', 2, 4, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'maple_large'), 'tfc:random_tree', random_config('maple', 5, 2, '_large', place=tree_placement_config(2, 5)))
    configured_placed_feature(rm, ('tree', 'maple_dead'), 'tfc:random_tree', random_config('maple', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'oak'), 'tfc:overlay_tree', overlay_config('oak', 3, 5, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'oak_dead'), 'tfc:random_tree', random_config('oak', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'palm'), 'tfc:random_tree', random_config('palm', 7, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'palm_dead'), 'tfc:random_tree', random_config('palm', 3, 1, '_dead', place=tree_placement_config(2, 3, True)))
    configured_placed_feature(rm, ('tree', 'pine'), 'tfc:random_tree', random_config('pine', 9, place=tree_placement_config(1, 3)))
    configured_placed_feature(rm, ('tree', 'pine_large'), 'tfc:random_tree', random_config('pine', 5, 2, '_large', place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'pine_dead'), 'tfc:random_tree', random_config('pine', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    #configured_placed_feature(rm, ('tree', 'rosewood'), 'tfc:overlay_tree', overlay_config('rosewood', 1, 3, place=tree_placement_config(1, 9)))
    configured_placed_feature(rm, ('tree', 'rosewood_dead'), 'tfc:random_tree', random_config('rosewood', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'sequoia'), 'tfc:random_tree', random_config('sequoia', 7, place=tree_placement_config(1, 3)))
    configured_placed_feature(rm, ('tree', 'sequoia_large'), 'tfc:stacked_tree', stacked_config('sequoia', 8, 16, 2, [(2, 3, 3), (1, 2, 3), (1, 1, 3)], 2, '_large', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'sequoia_dead'), 'tfc:random_tree', random_config('sequoia', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'spruce'), 'tfc:random_tree', random_config('spruce', 7, place=tree_placement_config(1, 3)))
    configured_placed_feature(rm, ('tree', 'spruce_large'), 'tfc:stacked_tree', stacked_config('spruce', 5, 9, 2, [(2, 3, 3), (1, 2, 3), (1, 1, 3)], 2, '_large', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'spruce_dead'), 'tfc:random_tree', random_config('spruce', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    #configured_placed_feature(rm, ('tree', 'sycamore'), 'tfc:overlay_tree', overlay_config('sycamore', 2, 5, place=tree_placement_config(1, 5, True)))
    configured_placed_feature(rm, ('tree', 'sycamore_large'), 'tfc:random_tree', random_config('sycamore', 5, 2, '_large', place=tree_placement_config(2, 5, True)))
    configured_placed_feature(rm, ('tree', 'sycamore_dead'), 'tfc:random_tree', random_config('sycamore', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'white_cedar'), 'tfc:overlay_tree', overlay_config('white_cedar', 2, 4, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'white_cedar_large'), 'tfc:overlay_tree', overlay_config('white_cedar', 2, 5, 1, 1, '_large', place=tree_placement_config(1, 9)))
    configured_placed_feature(rm, ('tree', 'white_cedar_dead'), 'tfc:random_tree', random_config('white_cedar', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'willow'), 'tfc:random_tree', random_config('willow', 7, place=tree_placement_config(1, 3, True)))
    configured_placed_feature(rm, ('tree', 'willow_large'), 'tfc:random_tree', random_config('willow', 14, 1, '_large', place=tree_placement_config(2, 3, True)))
    configured_placed_feature(rm, ('tree', 'willow_dead'), 'tfc:random_tree', random_config('willow', 3, 1, '_dead', place=tree_placement_config(2, 3, True)))

    configured_placed_feature(rm, ('tree', 'gum_arabic'), 'tfc:random_tree', random_config('gum_arabic', 35, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'gum_arabic_dead'), 'tfc:random_tree', random_config('gum_arabic', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'acacia_koa'), 'tfc:random_tree', random_config('acacia_koa', 15, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'acacia_koa_dead'), 'tfc:random_tree', random_config('acacia_koa', 4, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'ash'), 'tfc:random_tree', random_config('ash', 14, 1, place=tree_placement_config(1, 0, True)))
    
    configured_placed_feature(rm, ('tree', 'evergreen_ash_dead'), 'tfc:random_tree', random_config('evergreen_ash', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'evergreen_ash_large'), 'tfc:random_tree', random_config('evergreen_ash', 14, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'mulga_blackwood'), 'tfc:random_tree', random_config('mulga_blackwood', 35, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'mulga_blackwood_dead'), 'tfc:random_tree', random_config('mulga_blackwood', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'chestnut'), 'tfc:random_tree', random_config('chestnut', 14, 1, place=tree_placement_config(1, 0, )))
    configured_placed_feature(rm, ('tree', 'douglas_fir'), 'tfc:random_tree', random_config('douglas_fir', 10, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'mountain_fir'), 'tfc:random_tree', random_config('mountain_fir', 9, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'mountain_fir_dead'), 'tfc:random_tree', random_config('mountain_fir', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'mountain_fir_large'), 'tfc:random_tree', random_config('mountain_fir', 5, 1, '_large', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'hickory'), 'tfc:random_tree', random_config('hickory', 14, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'scrub_hickory'), 'tfc:random_tree', random_config('scrub_hickory', 10, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'scrub_hickory_dead'), 'tfc:random_tree', random_config('scrub_hickory', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'scrub_hickory_large'), 'tfc:random_tree', random_config('scrub_hickory', 10, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'kapok'), 'tfc:random_tree', random_config('kapok', 15, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'kapok_large'), 'tfc:random_tree', random_config('kapok', 6, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'bigleaf_maple'), 'tfc:random_tree', random_config('bigleaf_maple', 14, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'bigleaf_maple_dead'), 'tfc:random_tree', random_config('bigleaf_maple', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'weeping_maple'), 'tfc:random_tree', random_config('weeping_maple', 7, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'weeping_maple_dead'), 'tfc:random_tree', random_config('weeping_maple', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'weeping_maple_large'), 'tfc:random_tree', random_config('weeping_maple', 14, 1, '_large', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'live_oak'), 'tfc:random_tree', random_config('live_oak', 14, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'live_oak_dead'), 'tfc:random_tree', random_config('live_oak', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'live_oak_large'), 'tfc:random_tree', random_config('live_oak', 5, 2, '_large', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'black_oak'), 'tfc:random_tree', random_config('black_oak', 15, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'black_oak_dead'), 'tfc:random_tree', random_config('black_oak', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'stone_pine'), 'tfc:random_tree', random_config('stone_pine', 19, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'stone_pine_dead'), 'tfc:random_tree', random_config('stone_pine', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'stone_pine_large'), 'tfc:random_tree', random_config('stone_pine', 19, 1, '_large', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'red_pine_'), 'tfc:random_tree', random_config('red_pine_', 12, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'red_pine__dead'), 'tfc:random_tree', random_config('red_pine_', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'rosewood'), 'tfc:random_tree', random_config('rosewood', 18, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'giant_rosewood'), 'tfc:random_tree', random_config('giant_rosewood', 15, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'giant_rosewood_dead'), 'tfc:random_tree', random_config('giant_rosewood', 4, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'coast_spruce'), 'tfc:random_tree', random_config('coast_spruce', 9, 1, place=tree_placement_config(1, 3, True)))
    configured_placed_feature(rm, ('tree', 'coast_spruce_dead'), 'tfc:random_tree', random_config('coast_spruce', 6, 1, '_dead', place=tree_placement_config(1, 3, True)))
    configured_placed_feature(rm, ('tree', 'sitka_spruce'), 'tfc:random_tree', random_config('sitka_spruce', 9, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'sitka_spruce_dead'), 'tfc:random_tree', random_config('sitka_spruce', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'sitka_spruce_large'), 'tfc:random_tree', random_config('sitka_spruce', 5, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'sycamore'), 'tfc:random_tree', random_config('sycamore', 14, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'atlas_cedar'), 'tfc:random_tree', random_config('atlas_cedar', 17, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'atlas_cedar_dead'), 'tfc:random_tree', random_config('atlas_cedar', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'weeping_willow'), 'tfc:random_tree', random_config('weeping_willow', 14, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'weeping_willow_dead'), 'tfc:random_tree', random_config('weeping_willow', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'rainbow_eucalptus'), 'tfc:random_tree', random_config('rainbow_eucalptus', 15, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'rainbow_eucalptus_dead'), 'tfc:random_tree', random_config('rainbow_eucalptus', 4, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'eucalyptus'), 'tfc:random_tree', random_config('eucalyptus', 18, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'eucalyptus_dead'), 'tfc:random_tree', random_config('eucalyptus', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'mountain_ash'), 'tfc:random_tree', random_config('mountain_ash', 16, 1, place=tree_placement_config(1, 8, False)))
    configured_placed_feature(rm, ('tree', 'mountain_ash_dead'), 'tfc:random_tree', random_config('mountain_ash', 6, 1, '_dead', place=tree_placement_config(1, 8, False)))
    configured_placed_feature(rm, ('tree', 'mountain_ash_large'), 'tfc:random_tree', random_config('mountain_ash', 16, 1, '_large', place=tree_placement_config(1, 8, False)))
    configured_placed_feature(rm, ('tree', 'baobab'), 'tfc:random_tree', random_config('baobab', 11, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'baobab_large'), 'tfc:random_tree', random_config('baobab', 7, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'hevea'), 'tfc:random_tree', random_config('hevea', 18, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'hevea_dead'), 'tfc:random_tree', random_config('hevea', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'mahogany'), 'tfc:random_tree', random_config('mahogany', 17, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'mahogany_dead'), 'tfc:random_tree', random_config('mahogany', 4, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'tualang'), 'tfc:random_tree', random_config('tualang', 6, 1, place=tree_placement_config(1, 11, True)))
    configured_placed_feature(rm, ('tree', 'tualang_dead'), 'tfc:random_tree', random_config('tualang', 6, 1, '_dead', place=tree_placement_config(1, 11, True)))
    configured_placed_feature(rm, ('tree', 'tualang_large'), 'tfc:random_tree', random_config('tualang', 6, 1, '_large', place=tree_placement_config(1, 11, True)))
    configured_placed_feature(rm, ('tree', 'teak'), 'tfc:random_tree', random_config('teak', 17, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'teak_dead'), 'tfc:random_tree', random_config('teak', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'slender_cypress'), 'tfc:random_tree', random_config('slender_cypress', 14, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'slender_cypress_dead'), 'tfc:random_tree', random_config('slender_cypress', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'weeping_cypress'), 'tfc:random_tree', random_config('weeping_cypress', 11, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'weeping_cypress_dead'), 'tfc:random_tree', random_config('weeping_cypress', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'redcedar'), 'tfc:random_tree', random_config('redcedar', 10, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'redcedar_dead'), 'tfc:random_tree', random_config('redcedar', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'bald_cypress'), 'tfc:random_tree', random_config('bald_cypress', 9, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'bald_cypress_dead'), 'tfc:random_tree', random_config('bald_cypress', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'bald_cypress_large'), 'tfc:random_tree', random_config('bald_cypress', 5, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'fig'), 'tfc:random_tree', random_config('fig', 6, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'fig_dead'), 'tfc:random_tree', random_config('fig', 4, 1, '_dead', place=tree_placement_config(1, 2, True)))

    # Ore Veins
    # for vein_name, vein in ORE_VEINS.items():
    #     rocks = expand_rocks(vein.rocks, vein_name)
    #     ore = ORES[vein.ore]  # standard ore
    #     if ore.graded:  # graded ore vein
    #         configured_placed_feature(rm, ('vein', vein_name), 'tfc:%s_vein' % vein.type, {
    #             'rarity': vein.rarity,
    #             'min_y': utils.vertical_anchor(vein.min_y, 'absolute'),
    #             'max_y': utils.vertical_anchor(vein.max_y, 'absolute'),
    #             'size': vein.size,
    #             'density': vein_density(vein.density),
    #             'blocks': [{
    #                 'replace': ['tfc:rock/raw/%s' % rock],
    #                 'with': vein_ore_blocks(vein, rock)
    #             } for rock in rocks],
    #             'indicator': {
    #                 'rarity': 12,
    #                 'blocks': [{
    #                     'block': 'tfc:ore/small_%s' % vein.ore
    #                 }]
    #             },
    #             'random_name': vein_name,
    #             'biomes': vein.biomes
    #         })
    #     else:  # non-graded ore vein (mineral)
    #         vein_config = {
    #             'rarity': vein.rarity,
    #             'min_y': utils.vertical_anchor(vein.min_y, 'absolute'),
    #             'max_y': utils.vertical_anchor(vein.max_y, 'absolute'),
    #             'size': vein.size,
    #             'density': vein_density(vein.density),
    #             'blocks': [{
    #                 'replace': ['tfc:rock/raw/%s' % rock],
    #                 'with': [{'block': 'tfc:ore/%s/%s' % (vein.ore, rock)}]
    #             } for rock in rocks],
    #             'random_name': vein_name,
    #             'biomes': vein.biomes
    #         }
    #         if vein.type == 'pipe':
    #             vein_config['min_skew'] = 5
    #             vein_config['max_skew'] = 13
    #             vein_config['min_slant'] = 0
    #             vein_config['max_slant'] = 2
    #         configured_placed_feature(rm, ('vein', vein_name), 'tfc:%s_vein' % vein.type, vein_config)
    #
    # configured_placed_feature(rm, ('vein', 'gravel'), 'tfc:disc_vein', {
    #     'rarity': 30,
    #     'min_y': utils.vertical_anchor(-64, 'absolute'),
    #     'max_y': utils.vertical_anchor(100, 'absolute'),
    #     'size': 44,
    #     'height': 2,
    #     'density': 0.98,
    #     'blocks': [{
    #         'replace': ['tfc:rock/raw/%s' % rock],
    #         'with': [{'block': 'tfc:rock/gravel/%s' % rock}]
    #     } for rock in ROCKS.keys()],
    #     'random_name': 'tfc:vein/gravel'
    # })
    #
    # for rock, data in ROCKS.items():
    #     if data.category == 'igneous_intrusive':
    #         configured_placed_feature(rm, ('vein', '%s_dike' % rock), 'tfc:pipe_vein', {
    #             'rarity': 220,
    #             'min_y': utils.vertical_anchor(-64, 'absolute'),
    #             'max_y': utils.vertical_anchor(180, 'absolute'),
    #             'size': 150,
    #             'density': 0.98,
    #             'blocks': [{
    #                 'replace': ['tfc:rock/raw/%s' % rock_in],
    #                 'with': [{'block': 'tfc:rock/raw/%s' % rock}]
    #             } for rock_in in ROCKS.keys()] + [{
    #                 'replace': ['tfc:rock/gravel/%s' % rock_in],
    #                 'with': [{'block': 'tfc:rock/raw/%s' % rock}]
    #             } for rock_in in ROCKS.keys()] + [{
    #                 'replace': ['tfc:rock/hardened/%s' % rock_in],
    #                 'with': [{'block': 'tfc:rock/raw/%s' % rock}]
    #             } for rock_in in ROCKS.keys()],
    #             'random_name': '%s_dike' % rock,
    #             'radius': 4,
    #             'minSkew': 7,
    #             'maxSkew': 20,
    #             'minSlant': 2,
    #             'maxSlant': 5
    #         })
    #
    # rm.configured_feature('cave_vegetation', 'tfc:cave_vegetation', {
    #     'blocks': [{
    #         'replace': ['tfc:rock/raw/%s' % rock],
    #         'with': [
    #             {'block': 'tfc:rock/mossy_cobble/%s' % rock, 'weight': 8},
    #             {'block': 'tfc:rock/cobble/%s' % rock, 'weight': 2}
    #         ]
    #     } for rock in ROCKS.keys()]
    # })
    # rm.placed_feature('cave_vegetation', 'tfc:cave_vegetation', decorate_climate(16, 32, 150, 470, fuzzy=True), decorate_carving_mask(15, 100), decorate_chance(0.01))
    #
    # rm.configured_feature('hanging_roots', 'minecraft:simple_block', {'to_place': simple_state_provider('minecraft:hanging_roots[waterlogged=False]')})
    # rm.placed_feature('hanging_roots', 'tfc:hanging_roots', decorate_air_or_empty_fluid(), decorate_would_survive('minecraft:hanging_roots[waterlogged=False]'))
    # rm.configured_feature('hanging_roots_patch', 'minecraft:vegetation_patch', {
    #     'type': 'minecraft:vegetation_patch',
    #     'vegetation_chance': 0.08,
    #     'xz_radius': uniform_int(4, 7),
    #     'extra_edge_column_chance': 0.3,
    #     'extra_bottom_block_chance': 0.0,
    #     'vertical_range': 5,
    #     'vegetation_feature': 'tfc:hanging_roots',
    #     'surface': 'ceiling',
    #     'depth': uniform_int(1, 2),
    #     'replaceable': '#minecraft:base_stone_overworld',
    #     'ground_state': simple_state_provider('tfc:rooted_dirt/silt')
    # })
    # rm.placed_feature('hanging_roots_patch', 'tfc:hanging_roots_patch', decorate_count(10), decorate_square(), decorate_range(40, 72), decorate_scanner('up', 12), decorate_random_offset(0, -1), decorate_climate(min_rain=300, min_temp=0), decorate_biome())
    #
    # # Plants
    # configured_plant_patch_feature(rm, ('plant', 'allium'), plant_config('tfc:plant/allium[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-10, -2, 150, 400, min_forest='edge', max_forest='normal'))
    # configured_plant_patch_feature(rm, ('plant', 'badderlocks'), plant_config('tfc:plant/badderlocks[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-18, 2, 150, 500))
    # configured_plant_patch_feature(rm, ('plant', 'barrel_cactus'), plant_config('tfc:plant/barrel_cactus[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(4, 18, 0, 85))
    # configured_plant_patch_feature(rm, ('plant', 'black_orchid'), plant_config('tfc:plant/black_orchid[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(14, 40, 290, 410))
    # configured_plant_patch_feature(rm, ('plant', 'blood_lily'), plant_config('tfc:plant/blood_lily[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(8, 18, 200, 500, min_forest='normal', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'blue_orchid'), plant_config('tfc:plant/blue_orchid[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(10, 40, 250, 390))
    # configured_plant_patch_feature(rm, ('plant', 'butterfly_milkweed'), plant_config('tfc:plant/butterfly_milkweed[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-16, 18, 75, 300))
    # configured_plant_patch_feature(rm, ('plant', 'calendula'), plant_config('tfc:plant/calendula[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(4, 22, 130, 300))
    # configured_plant_patch_feature(rm, ('plant', 'cattail'), plant_config('tfc:plant/cattail[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-16, 22, 150, 500))
    # configured_plant_patch_feature(rm, ('plant', 'coontail'), plant_config('tfc:plant/coontail[age=1,stage=1,fluid=empty]', 1, 15, 100, water_plant=True), decorate_chance(2), decorate_square(), decorate_climate(2, 18, 250, 500))
    # configured_plant_patch_feature(rm, ('plant', 'dandelion'), plant_config('tfc:plant/dandelion[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-22, 40, 120, 400))
    # configured_plant_patch_feature(rm, ('plant', 'dead_bush'), plant_config('tfc:plant/dead_bush[age=1,stage=1]', 1, 15, 10), decorate_chance(5), decorate_square(), decorate_climate(-12, 40, 0, 120))
    # configured_noise_plant_feature(rm, ('plant', 'duckweed'), plant_config('tfc:plant/duckweed[age=1,stage=1]', 1, 7, 100), decorate_square(), decorate_climate(-18, 2, 0, 500))
    # configured_plant_patch_feature(rm, ('plant', 'eel_grass'), plant_config('tfc:plant/eel_grass[age=1,stage=1,fluid=empty]', 1, 15, 100, water_plant=True), decorate_chance(2), decorate_square(), decorate_climate(6, 40, 200, 500))
    # configured_plant_patch_feature(rm, ('plant', 'field_horsetail'), plant_config('tfc:plant/field_horsetail[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-12, 20, 300, 500))
    # configured_plant_patch_feature(rm, ('plant', 'foxglove'), plant_config('tfc:plant/foxglove[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(-8, 16, 150, 300, min_forest='none', max_forest='normal'))
    # configured_plant_patch_feature(rm, ('plant', 'grape_hyacinth'), plant_config('tfc:plant/grape_hyacinth[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-10, 10, 150, 250))
    # configured_plant_patch_feature(rm, ('plant', 'gutweed'), plant_config('tfc:plant/gutweed[age=1,stage=1,fluid=empty]', 1, 10, 10, water_plant=True), decorate_chance(4), decorate_square(), decorate_climate(-6, 18, 100, 500))
    # configured_plant_patch_feature(rm, ('plant', 'guzmania'), plant_config('tfc:plant/guzmania[age=1,stage=1,facing=north]', 6, 5), decorate_chance(5), decorate_square(), decorate_climate(20, 40, 290, 480))
    # configured_plant_patch_feature(rm, ('plant', 'houstonia'), plant_config('tfc:plant/houstonia[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-12, 10, 150, 500))
    # configured_plant_patch_feature(rm, ('plant', 'labrador_tea'), plant_config('tfc:plant/labrador_tea[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-18, 0, 200, 380))
    # configured_plant_patch_feature(rm, ('plant', 'lady_fern'), plant_config('tfc:plant/lady_fern[age=1,stage=1]', 1, 10, 15), decorate_chance(5), decorate_square(), decorate_climate(-10, 8, 200, 500, min_forest='edge', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'licorice_fern'), plant_config('tfc:plant/licorice_fern[age=1,stage=1,facing=north]', 6, 5), decorate_chance(5), decorate_square(), decorate_climate(2, 10, 300, 400))
    # configured_plant_patch_feature(rm, ('plant', 'laminaria'), plant_config('tfc:plant/laminaria[age=1,stage=1,fluid=empty]', 1, 10, 10, water_plant=True), decorate_chance(4), decorate_square(), decorate_climate(-24, -2, 100, 500))
    # configured_noise_plant_feature(rm, ('plant', 'lotus'), plant_config('tfc:plant/lotus[age=1,stage=1]', 1, 7, 100), decorate_square(), decorate_climate(-4, 18, 0, 500))
    # configured_plant_patch_feature(rm, ('plant', 'marigold'), plant_config('tfc:plant/marigold[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-8, 18, 50, 390))
    # configured_plant_patch_feature(rm, ('plant', 'meads_milkweed'), plant_config('tfc:plant/meads_milkweed[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-10, 2, 130, 380))
    # configured_plant_patch_feature(rm, ('plant', 'milfoil'), plant_config('tfc:plant/milfoil[age=1,stage=1,fluid=empty]', 1, 10, 10, water_plant=True), decorate_chance(4), decorate_square(), decorate_climate(-14, 22, 250, 500))
    # configured_plant_patch_feature(rm, ('plant', 'morning_glory'), plant_config('tfc:plant/morning_glory[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 6), decorate_chance(15), decorate_square(), decorate_climate(-11, 19, 300, 500))
    # configured_plant_patch_feature(rm, ('plant', 'nasturtium'), plant_config('tfc:plant/nasturtium[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(6, 22, 150, 380))
    # configured_plant_patch_feature(rm, ('plant', 'ostrich_fern'), plant_config('tfc:plant/ostrich_fern[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(8), decorate_square(), decorate_climate(-14, 6, 290, 470, min_forest='edge', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'oxeye_daisy'), plant_config('tfc:plant/oxeye_daisy[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-14, 10, 120, 300, min_forest='none', max_forest='edge'))
    # configured_noise_plant_feature(rm, ('plant', 'pistia'), plant_config('tfc:plant/pistia[age=1,stage=1]', 1, 7, 100), decorate_chance(5), decorate_square(), decorate_climate(6, 26, 0, 400))
    # configured_plant_patch_feature(rm, ('plant', 'poppy'), plant_config('tfc:plant/poppy[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-12, 14, 150, 250))
    # configured_plant_patch_feature(rm, ('plant', 'primrose'), plant_config('tfc:plant/primrose[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-8, 10, 150, 300, min_forest='edge', max_forest='normal'))
    # configured_plant_patch_feature(rm, ('plant', 'pulsatilla'), plant_config('tfc:plant/pulsatilla[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-10, 2, 50, 200))
    # configured_plant_patch_feature(rm, ('plant', 'rose'), plant_config('tfc:plant/rose[age=1,stage=1,part=lower]', 1, 15, 128, True, tall_plant=True), decorate_square(), decorate_climate(-5, 20, 150, 300))
    # configured_plant_patch_feature(rm, ('plant', 'sacred_datura'), plant_config('tfc:plant/sacred_datura[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(4, 18, 75, 150))
    # configured_plant_patch_feature(rm, ('plant', 'sago'), plant_config('tfc:plant/sago[age=1,stage=1,fluid=empty]', 1, 10, 10, water_plant=True), decorate_chance(4), decorate_square(), decorate_climate(-18, 18, 200, 500))
    # configured_plant_patch_feature(rm, ('plant', 'sagebrush'), plant_config('tfc:plant/sagebrush[age=1,stage=1]', 1, 15, 10), decorate_chance(5), decorate_square(), decorate_climate(-10, 14, 0, 120))
    # configured_plant_patch_feature(rm, ('plant', 'sapphire_tower'), plant_config('tfc:plant/sapphire_tower[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(10, 22, 75, 200, min_forest='none', max_forest='sparse'))
    # configured_noise_plant_feature(rm, ('plant', 'sargassum'), plant_config('tfc:plant/sargassum[age=1,stage=1]', 1, 7, 100), decorate_square(), decorate_climate(-10, 16, 0, 500))
    # configured_plant_patch_feature(rm, ('plant', 'sword_fern'), plant_config('tfc:plant/sword_fern[age=1,stage=1]', 1, 10, 15), decorate_chance(5), decorate_square(), decorate_climate(-12, 12, 100, 500, min_forest='sparse', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'snapdragon_pink'), plant_config('tfc:plant/snapdragon_pink[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(16, 24, 150, 300, min_forest='none', max_forest='sparse'))
    # configured_plant_patch_feature(rm, ('plant', 'snapdragon_red'), plant_config('tfc:plant/snapdragon_red[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(12, 20, 150, 300, min_forest='none', max_forest='sparse'))
    # configured_plant_patch_feature(rm, ('plant', 'snapdragon_white'), plant_config('tfc:plant/snapdragon_white[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(8, 16, 150, 300, min_forest='none', max_forest='sparse'))
    # configured_plant_patch_feature(rm, ('plant', 'snapdragon_yellow'), plant_config('tfc:plant/snapdragon_yellow[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(6, 24, 150, 300, min_forest='none', max_forest='sparse'))
    # configured_plant_patch_feature(rm, ('plant', 'strelitzia'), plant_config('tfc:plant/strelitzia[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(14, 26, 50, 300))
    # configured_plant_patch_feature(rm, ('plant', 'toquilla_palm'), plant_config('tfc:plant/toquilla_palm[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(16, 40, 250, 500))
    # configured_plant_patch_feature(rm, ('plant', 'trillium'), plant_config('tfc:plant/trillium[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-10, 8, 250, 500, min_forest='normal', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'tropical_milkweed'), plant_config('tfc:plant/tropical_milkweed[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(8, 24, 120, 300, min_forest='sparse', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'tulip_orange'), plant_config('tfc:plant/tulip_orange[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(2, 10, 200, 400, min_forest='none', max_forest='edge'))
    # configured_plant_patch_feature(rm, ('plant', 'tulip_pink'), plant_config('tfc:plant/tulip_pink[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-6, 2, 200, 400, min_forest='none', max_forest='edge'))
    # configured_plant_patch_feature(rm, ('plant', 'tulip_red'), plant_config('tfc:plant/tulip_red[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(0, 4, 200, 400, min_forest='none', max_forest='edge'))
    # configured_plant_patch_feature(rm, ('plant', 'tulip_white'), plant_config('tfc:plant/tulip_white[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(-12, -4, 200, 400, min_forest='none', max_forest='edge'))
    # configured_plant_patch_feature(rm, ('plant', 'turtle_grass'), plant_config('tfc:plant/turtle_grass[age=1,stage=1,fluid=empty]', 1, 15, 128, water_plant=True), decorate_chance(1), decorate_square(), decorate_climate(14, 40, 240, 500))
    # configured_plant_patch_feature(rm, ('plant', 'vriesea'), plant_config('tfc:plant/vriesea[age=1,stage=1,facing=north]', 6, 5), decorate_chance(5), decorate_square(), decorate_climate(14, 40, 200, 400))
    # configured_noise_plant_feature(rm, ('plant', 'water_lily'), plant_config('tfc:plant/water_lily[age=1,stage=1]', 1, 7, 100), decorate_square(), decorate_climate(-12, 40, 0, 500))
    # configured_plant_patch_feature(rm, ('plant', 'yucca'), plant_config('tfc:plant/yucca[age=1,stage=1]', 1, 15, 10), decorate_chance(5), decorate_square(), decorate_climate(-4, 22, 0, 75))
    # configured_plant_patch_feature(rm, ('plant', 'switchgrass'), plant_config('tfc:plant/switchgrass[age=1,stage=1,part=lower]', 1, 15, tall_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-6, 22, 110, 390))
    # configured_plant_patch_feature(rm, ('plant', 'tall_fescue_grass'), plant_config('tfc:plant/tall_fescue_grass[age=1,stage=1,part=lower]', 1, 15, tall_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-10, 10, 280, 430))
    # configured_plant_patch_feature(rm, ('plant', 'arrowhead'), plant_config('tfc:plant/arrowhead[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-10, 22, 180, 500))
    # configured_plant_patch_feature(rm, ('plant', 'bur_reed'), plant_config('tfc:plant/bur_reed[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-16, 4, 250, 400))
    # configured_plant_patch_feature(rm, ('plant', 'water_taro'), plant_config('tfc:plant/water_taro[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(12, 40, 260, 500))
    # configured_plant_patch_feature(rm, ('plant', 'phragmite'), plant_config('tfc:plant/phragmite[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-6, 18, 50, 250))
    # configured_plant_patch_feature(rm, ('plant', 'pickerelweed'), plant_config('tfc:plant/pickerelweed[age=1,stage=1,fluid=empty,part=lower]', 1, 7, 100, emergent_plant=True), decorate_chance(2), decorate_square(), decorate_climate(-14, 16, 200, 500))
    # configured_plant_patch_feature(rm, ('plant', 'red_sealing_wax_palm'), plant_config('tfc:plant/red_sealing_wax_palm[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(10), decorate_square(), decorate_climate(18, 40, 280, 500, min_forest='edge', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'hibiscus'), plant_config('tfc:plant/hibiscus[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(10, 24, 260, 450, min_forest='edge', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'heliconia'), plant_config('tfc:plant/heliconia[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(14, 40, 320, 500))
    # configured_plant_patch_feature(rm, ('plant', 'blue_ginger'), plant_config('tfc:plant/blue_ginger[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(16, 26, 300, 450))
    # configured_plant_patch_feature(rm, ('plant', 'king_fern'), plant_config('tfc:plant/king_fern[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(18, 40, 350, 500, min_forest='normal', max_forest='old_growth'))
    # configured_plant_patch_feature(rm, ('plant', 'kangaroo_paw'), plant_config('tfc:plant/kangaroo_paw[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(14, 40, 100, 300))
    # configured_plant_patch_feature(rm, ('plant', 'lilac'), plant_config('tfc:plant/lilac[age=1,stage=1,part=lower]', 1, 15, 10, tall_plant=True), decorate_chance(5), decorate_square(), decorate_climate(-10, 6, 150, 300))
    # configured_plant_patch_feature(rm, ('plant', 'silver_spurflower'), plant_config('tfc:plant/silver_spurflower[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(14, 24, 230, 400, min_forest='sparse', max_forest='edge'))
    # configured_plant_patch_feature(rm, ('plant', 'desert_flame'), plant_config('tfc:plant/desert_flame[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(0, 20, 40, 170, min_forest='none', max_forest='sparse'))
    # configured_plant_patch_feature(rm, ('plant', 'anthurium'), plant_config('tfc:plant/anthurium[age=1,stage=1]', 1, 10, 10), decorate_chance(5), decorate_square(), decorate_climate(12, 40, 290, 500))
    # configured_plant_patch_feature(rm, ('plant', 'reindeer_lichen'), plant_config('tfc:plant/reindeer_lichen[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 6), decorate_chance(15), decorate_square(), decorate_climate(-24, -8, 50, 470))
    # configured_plant_patch_feature(rm, ('plant', 'moss'), plant_config('tfc:plant/moss[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 6), decorate_chance(15), decorate_square(), decorate_climate(-7, 30, 250, 450))
    # configured_plant_patch_feature(rm, ('plant', 'morning_glory'), plant_config('tfc:plant/morning_glory[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 6), decorate_chance(15), decorate_square(), decorate_climate(-11, 19, 300, 500))
    #
    # configured_placed_feature(rm, ('plant', 'hanging_vines'), 'tfc:weeping_vines', tall_plant_config('tfc:plant/hanging_vines_plant', 'tfc:plant/hanging_vines', 90, 10, 14, 21), decorate_heightmap('world_surface_wg'), decorate_square(), decorate_climate(16, 32, 150, 470, True, fuzzy=True), decorate_air_or_empty_fluid())
    # configured_placed_feature(rm, ('plant', 'hanging_vines_cave'), 'tfc:weeping_vines', tall_plant_config('tfc:plant/hanging_vines_plant', 'tfc:plant/hanging_vines', 90, 10, 14, 22), decorate_carving_mask(30, 100), decorate_chance(0.003), decorate_climate(16, 32, 150, 470, True, fuzzy=True), decorate_air_or_empty_fluid())
    # configured_placed_feature(rm, ('plant', 'spanish_moss'), 'tfc:weeping_vines', tall_plant_config('tfc:plant/spanish_moss_plant', 'tfc:plant/spanish_moss', 90, 10, 14, 21), decorate_heightmap('world_surface_wg'), decorate_square(), decorate_climate(8, 22, 400, 500, True, fuzzy=True), decorate_air_or_empty_fluid())
    # configured_placed_feature(rm, ('plant', 'liana'), 'tfc:weeping_vines', tall_plant_config('tfc:plant/liana_plant', 'tfc:plant/liana', 40, 10, 8, 16), decorate_carving_mask(30, 100), decorate_chance(0.003), decorate_climate(16, 32, 150, 470, True, fuzzy=True))
    # configured_placed_feature(rm, ('plant', 'tree_fern'), 'tfc:twisting_vines', tall_plant_config('tfc:plant/tree_fern_plant', 'tfc:plant/tree_fern', 8, 7, 2, 6), decorate_heightmap('world_surface_wg'), decorate_chance(5), decorate_square(), decorate_climate(19, 50, 300, 500), decorate_air_or_empty_fluid())
    # configured_placed_feature(rm, ('plant', 'arundo'), 'tfc:twisting_vines', tall_plant_config('tfc:plant/arundo_plant', 'tfc:plant/arundo', 70, 7, 5, 8), decorate_heightmap('world_surface_wg'), decorate_chance(3), decorate_square(), decorate_climate(5, 22, 150, 500), ('tfc:near_water', {'radius': 6}), decorate_air_or_empty_fluid())
    # configured_placed_feature(rm, ('plant', 'dry_phragmite'), 'tfc:twisting_vines', tall_plant_config('tfc:plant/dry_phragmite_plant', 'tfc:plant/dry_phragmite', 70, 7, 3, 5), decorate_range(62, 64), decorate_square(), decorate_climate(-5, 30, 100, 370, min_forest='sparse'), decorate_replaceable())
    #
    # configured_placed_feature(rm, ('plant', 'winged_kelp'), 'tfc:kelp', tall_plant_config('tfc:plant/winged_kelp_plant', 'tfc:plant/winged_kelp', 64, 12, 14, 21), decorate_heightmap('ocean_floor_wg'), decorate_square(), decorate_chance(2), decorate_climate(-15, 15, 0, 450, fuzzy=True), decorate_air_or_empty_fluid())
    # configured_placed_feature(rm, ('plant', 'leafy_kelp'), 'tfc:kelp', tall_plant_config('tfc:plant/leafy_kelp_plant', 'tfc:plant/leafy_kelp', 64, 12, 14, 21), decorate_heightmap('ocean_floor_wg'), decorate_square(), decorate_chance(2), decorate_climate(-20, 20, 0, 500, fuzzy=True), decorate_air_or_empty_fluid())
    #
    # configured_patch_feature(rm, ('plant', 'giant_kelp'), patch_config('tfc:plant/giant_kelp_flower[age=0,fluid=empty]', 2, 10, 20, water='salt', custom_feature='tfc:kelp_tree', custom_config={'block': 'tfc:plant/giant_kelp_flower'}), decorate_square(), decorate_climate(-18, 18, 0, 500, fuzzy=True))
    #
    # configured_placed_feature(rm, ('plant', 'ivy'), 'tfc:vines', {'state': utils.block_state('tfc:plant/ivy[up=False,north=False,east=False,south=False,west=False]')}, decorate_count(127), decorate_square(), decorate_range(48, 128), decorate_replaceable(), decorate_climate(-4, 14, 90, 450, True, fuzzy=True))
    # configured_placed_feature(rm, ('plant', 'jungle_vines'), 'tfc:vines', {'state': utils.block_state('tfc:plant/jungle_vines[up=False,north=False,east=False,south=False,west=False]')}, decorate_count(127), decorate_square(), decorate_range(48, 128), decorate_replaceable(), decorate_climate(16, 32, 150, 470, True, fuzzy=True))
    #
    # # Grass-Type / Basic Plants
    # configured_plant_patch_feature(rm, ('plant', 'fountain_grass'), plant_config('tfc:plant/fountain_grass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(0, 26, 75, 150))
    # configured_plant_patch_feature(rm, ('plant', 'manatee_grass'), plant_config('tfc:plant/manatee_grass[age=1,stage=1]', 1, 15, 64, water_plant=True), decorate_square(), decorate_climate(12, 40, 250, 500))
    # configured_plant_patch_feature(rm, ('plant', 'orchard_grass'), plant_config('tfc:plant/orchard_grass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(-30, 10, 75, 300))
    # configured_plant_patch_feature(rm, ('plant', 'ryegrass'), plant_config('tfc:plant/ryegrass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(-24, 40, 150, 320))
    # configured_plant_patch_feature(rm, ('plant', 'scutch_grass'), plant_config('tfc:plant/scutch_grass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(0, 40, 150, 500))
    # configured_plant_patch_feature(rm, ('plant', 'star_grass'), plant_config('tfc:plant/star_grass[age=1,stage=1]', 1, 15, 64, water_plant=True), decorate_square(), decorate_climate(2, 40, 50, 260))
    # configured_plant_patch_feature(rm, ('plant', 'timothy_grass'), plant_config('tfc:plant/timothy_grass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(-22, 16, 289, 500))
    # configured_plant_patch_feature(rm, ('plant', 'bromegrass'), plant_config('tfc:plant/bromegrass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(4, 20, 140, 360))
    # configured_plant_patch_feature(rm, ('plant', 'bluegrass'), plant_config('tfc:plant/bluegrass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(-4, 12, 110, 280))
    # configured_plant_patch_feature(rm, ('plant', 'raddia_grass'), plant_config('tfc:plant/raddia_grass[age=1,stage=1]', 1, 15, 64), decorate_square(), decorate_climate(18, 40, 330, 500))
    #
    # # Covers
    # configured_noise_plant_feature(rm, ('plant', 'moss_cover'), plant_config('tfc:plant/moss[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 7, 100), decorate_climate(18, 35, 340, 500, True, fuzzy=True), decorate_square(), water=False)
    # configured_noise_plant_feature(rm, ('plant', 'morning_glory_cover'), plant_config('tfc:plant/morning_glory[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 7, 100), decorate_climate(9, 13, 160, 230, True, fuzzy=True), decorate_square(), water=False)
    # configured_noise_plant_feature(rm, ('plant', 'reindeer_lichen_cover'), plant_config('tfc:plant/reindeer_lichen[age=1,stage=1,up=False,down=True,north=False,east=False,west=False,south=False]', 1, 7, 100), decorate_climate(-20, -10, 220, 310, True, fuzzy=True), decorate_square(), water=False)
    #
    # # Clay Indicator Plants
    # # These piggyback on the clay disc feature, and so have limited decorators
    # configured_plant_patch_feature(rm, ('plant', 'athyrium_fern'), plant_config('tfc:plant/athyrium_fern[age=1,stage=1]', 1, 6, 16, requires_clay=True), decorate_climate(-10, 14, 270, 500))
    # configured_plant_patch_feature(rm, ('plant', 'canna'), plant_config('tfc:plant/canna[age=1,stage=1]', 1, 6, 16, requires_clay=True), decorate_climate(10, 40, 270, 500))
    # configured_plant_patch_feature(rm, ('plant', 'goldenrod'), plant_config('tfc:plant/goldenrod[age=1,stage=1]', 1, 6, 16, requires_clay=True), decorate_climate(-16, 6, 75, 310))
    # configured_plant_patch_feature(rm, ('plant', 'pampas_grass'), plant_config('tfc:plant/pampas_grass[age=1,stage=1,part=lower]', 1, 6, 16, requires_clay=True, tall_plant=True), decorate_climate(12, 40, 0, 300))
    # configured_plant_patch_feature(rm, ('plant', 'perovskia'), plant_config('tfc:plant/perovskia[age=1,stage=1]', 1, 6, 16, requires_clay=True), decorate_climate(-6, 12, 0, 270))
    # configured_noise_plant_feature(rm, ('plant', 'water_canna'), plant_config('tfc:plant/water_canna[age=1,stage=1]', 1, 6, 16, requires_clay=True), decorate_climate(0, 36, 150, 500))
    #
    # # Crops
    # for crop, crop_data in CROPS.items():
    #     name_parts = ('plant', 'wild_crop', crop)
    #     name = 'tfc:wild_crop/%s' % crop
    #     heightmap: Heightmap = 'world_surface_wg'
    #     replaceable = decorate_replaceable()
    #
    #     if crop_data.type == 'double' or crop_data.type == 'double_stick':
    #         feature = 'tfc:tall_wild_crop', {'block': name}
    #         name += '[part=bottom]'
    #     elif crop == 'rice':  # waterlogged
    #         feature = 'tfc:block_with_fluid', {'to_place': simple_state_provider(name)}
    #         heightmap = 'ocean_floor_wg'
    #         replaceable = decorate_shallow(1)
    #     elif crop_data.type == 'spreading':
    #         feature = 'tfc:spreading_crop', {'block': name}
    #     else:
    #         feature = 'simple_block', {'to_place': simple_state_provider(name)}
    #
    #     res = utils.resource_location(rm.domain, name_parts)
    #     patch_feature = res.join() + '_patch'
    #     singular_feature = utils.resource_location(rm.domain, name_parts)
    #
    #     placed_feature_tag(rm, 'feature/crops', patch_feature)
    #
    #     rm.configured_feature(patch_feature, 'minecraft:random_patch', {'tries': 6, 'xz_spread': 5, 'y_spread': 1, 'feature': singular_feature.join()})
    #     rm.configured_feature(singular_feature, *feature)
    #     rm.placed_feature(patch_feature, patch_feature, decorate_chance(80), decorate_square(), decorate_climate(crop_data.min_temp, crop_data.max_temp, crop_data.min_rain, crop_data.max_rain, min_forest=crop_data.min_forest, max_forest=crop_data.max_forest))
    #     rm.placed_feature(singular_feature, singular_feature, decorate_heightmap(heightmap), replaceable, decorate_would_survive(name))
    #
    # for berry, info in BERRIES.items():
    #     if info.type == 'spreading':
    #         configured_placed_feature(rm, ('plant', berry + '_bush'), 'tfc:spreading_bush', {'block': 'tfc:plant/%s_bush' % berry}, decorate_climate(info.min_temp, info.max_temp, info.min_rain, info.max_rain, min_forest=info.min_forest, max_forest=info.max_forest), decorate_heightmap('world_surface_wg'), decorate_square(), decorate_chance(22))
    #         placed_feature_tag(rm, 'feature/berry_bushes', 'tfc:plant/%s_bush' % berry)
    #     else:
    #         bush_block = 'tfc:plant/%s_bush[lifecycle=healthy,stage=0%s]' % (berry, ',fluid=empty' if info.type == 'waterlogged' else '')
    #         configured_patch_feature(rm, ('plant', berry + '_bush'), patch_config(bush_block, 1, 4, 4, 'fresh' if info.type == 'waterlogged' else False), decorate_climate(info.min_temp, info.max_temp, info.min_rain, info.max_rain, min_forest=info.min_forest, max_forest=info.max_forest), decorate_square(), decorate_chance(30), biome_check=False)
    #         placed_feature_tag(rm, 'feature/berry_bushes', 'tfc:plant/%s_bush_patch' % berry)
    #
    # for fruit, info in FRUITS.items():
    #     config = {
    #         'min_temperature': info.min_temp,
    #         'max_temperature': info.max_temp,
    #         'min_rainfall': info.min_rain,
    #         'max_rainfall': info.max_rain,
    #         'max_forest': 'normal'
    #     }
    #     feature = 'tfc:fruit_trees'
    #     state = 'tfc:plant/%s_growing_branch' % fruit
    #     if fruit == 'banana':
    #         feature = 'tfc:bananas'
    #         state = 'tfc:plant/banana_plant'
    #     configured_placed_feature(rm, ('plant', fruit), feature, {'state': state}, ('tfc:climate', config), decorate_heightmap('world_surface_wg'), decorate_square(), decorate_chance(50))
    #
    #     placed_feature_tag(rm, 'feature/fruit_trees', 'tfc:plant/%s' % fruit, 'tfc:plant/%s' % fruit)
    #
    # configured_placed_feature(rm, 'bamboo', 'minecraft:bamboo', {'probability': 0.2}, decorate_chance(30), decorate_climate(18, 28, 300, 500, True, fuzzy=True), ('minecraft:noise_based_count', {
    #     'noise_to_count_ratio': 160,
    #     'noise_factor': 80.0,
    #     'noise_offset': 0.3
    # }), decorate_square(), decorate_heightmap('world_surface_wg'))
    #
    # for coral in ('tree', 'mushroom', 'claw'):
    #     configured_placed_feature(rm, 'coral_%s' % coral, 'tfc:coral_%s' % coral, {})
    #     placed_feature_tag(rm, 'feature/corals', 'tfc:coral_%s' % coral)
    #
    # configured_placed_feature(rm, 'coral_reef', 'minecraft:simple_random_selector', {
    #     'features': '#tfc:feature/corals'
    # }, ('minecraft:noise_based_count', {
    #     'noise_to_count_ratio': 20,
    #     'noise_factor': 200,
    #     'noise_offset': 1
    # }), decorate_square(), decorate_climate(min_temp=12, max_temp=50, fuzzy=True), decorate_heightmap('ocean_floor_wg'))
    #
    # # Groundcover
    # configured_patch_feature(rm, 'driftwood', patch_config('tfc:groundcover/driftwood[fluid=empty]', 1, 15, 5, True), decorate_chance(6), decorate_square(), decorate_climate(-10, 50, 200, 500))
    # configured_patch_feature(rm, 'clam', patch_config('tfc:groundcover/clam[fluid=empty]', 1, 15, 5, 'salt'), decorate_chance(6), decorate_square(), decorate_climate(-50, 22, 10, 450))
    # configured_patch_feature(rm, 'mollusk', patch_config('tfc:groundcover/mollusk[fluid=empty]', 1, 15, 5, 'salt'), decorate_chance(6), decorate_square(), decorate_climate(-10, 30, 150, 500))
    # configured_patch_feature(rm, 'mussel', patch_config('tfc:groundcover/mussel[fluid=empty]', 1, 15, 5, 'salt'), decorate_chance(6), decorate_square(), decorate_climate(10, 50, 100, 500))
    #
    # configured_patch_feature(rm, 'sticks_shore', patch_config('tfc:groundcover/stick[fluid=empty]', 1, 15, 25, True), decorate_chance(2), decorate_square(), decorate_climate(-50, 50, 100, 500))
    # configured_patch_feature(rm, 'seaweed', patch_config('tfc:groundcover/seaweed[fluid=empty]', 1, 15, 10, True), decorate_chance(5), decorate_square(), decorate_climate(-20, 50, 150, 500))
    #
    # Forest Only
    configured_patch_feature(rm, 'sticks_forest', patch_config('tfc:groundcover/stick[fluid=empty]', 1, 15, 20), decorate_chance(3), decorate_square(), decorate_climate(-20, 50, 70, 500, True), biome_check=False)
    configured_patch_feature(rm, 'pinecone', patch_config('tfc:groundcover/pinecone[fluid=empty]', 1, 15, 10), decorate_chance(5), decorate_square(), decorate_climate(-14, 0, 60, 320, True), biome_check=False)
    configured_patch_feature(rm, 'humus', patch_config('tfc:groundcover/humus[fluid=empty]', 1, 5, 100), decorate_chance(5), decorate_square(), decorate_climate(8, 20, 180, 420, True, fuzzy=True), biome_check=False)
    configured_patch_feature(rm, 'salt_lick', patch_config('tfc:groundcover/salt_lick[fluid=empty]', 1, 5, 100), decorate_chance(110), decorate_square(), decorate_climate(5, 33, 100, 500, True), biome_check=False)
    configured_patch_feature(rm, 'dead_grass', patch_config('tfc:groundcover/dead_grass[fluid=empty]', 1, 5, 100), decorate_chance(70), decorate_square(), decorate_climate(10, 20, 0, 150, True, fuzzy=True), biome_check=False)
    configured_patch_feature(rm, 'rotten_flesh', patch_config('tfc:groundcover/rotten_flesh[fluid=empty]', 1, 10, 10), decorate_chance(100), decorate_square(), decorate_climate(-30, 30, 0, 400), biome_check=False)
    configured_patch_feature(rm, 'guano', patch_config('tfc:groundcover/guano[fluid=empty]', 1, 10, 10), decorate_chance(200), decorate_square(), decorate_climate(-30, 10, 100, 500), decorate_on_top_of('forge:gravel'), biome_check=False)

    # Loose Rocks - Both Surface + Underground
    configured_placed_feature(rm, 'loose_rock', 'tfc:loose_rock', {}, decorate_heightmap('ocean_floor_wg'))
    configured_placed_feature(rm, 'surface_loose_rocks', 'minecraft:random_patch', {'tries': 8, 'xz_spread': 7, 'y_spread': 1, 'feature': 'tfc:loose_rock'}, decorate_square())

    # Underground decoration
    configured_placed_feature(rm, 'underground_loose_rocks', 'tfc:loose_rock', decorate_carving_mask(), decorate_chance(0.05), decorate_range(-59, 63))
    configured_patch_feature(rm, 'underground_guano', patch_config('tfc:groundcover/guano[fluid=empty]', 5, 5, 60), decorate_chance(3), decorate_square(), decorate_range(40, 100), extra_singular_decorators=[decorate_underground()])
    rm.configured_feature('geode', 'tfc:geode', {'outer': 'tfc:rock/hardened/basalt', 'middle': 'tfc:rock/raw/quartzite', 'inner': [
        {'data': 'tfc:ore/amethyst/quartzite', 'weight': 1}, {'data': 'tfc:rock/raw/quartzite', 'weight': 5}
    ]})
    rm.placed_feature('geode', 'tfc:geode', decorate_chance(500), decorate_square(), decorate_range(-48, 32), decorate_biome())


def configured_placed_feature(rm: ResourceManager, name_parts: ResourceIdentifier, feature: Optional[ResourceIdentifier] = None, config: JsonObject = None, *placements: Json):
    res = utils.resource_location(rm.domain, name_parts)
    if feature is None:
        feature = res
    rm.configured_feature(res, feature, config)
    rm.placed_feature(res, res, *placements)


def tall_plant_config(state1: str, state2: str, tries: int, radius: int, min_height: int, max_height: int) -> Json:
    return {
        'body': state1,
        'head': state2,
        'tries': tries,
        'radius': radius,
        'min_height': min_height,
        'max_height': max_height
    }


def vine_config(state: str, tries: int, radius: int, min_height: int, max_height: int) -> Json:
    return {
        'state': state,
        'tries': tries,
        'radius': radius,
        'min_height': min_height,
        'max_height': max_height
    }


class PlantConfig(NamedTuple):
    block: str
    y_spread: int
    xz_spread: int
    tries: int
    requires_clay: bool
    water_plant: bool
    emergent_plant: bool
    tall_plant: bool


def plant_config(block: str, y_spread: int, xz_spread: int, tries: int = None, requires_clay: bool = False, water_plant: bool = False, emergent_plant: bool = False, tall_plant: bool = False) -> PlantConfig:
    return PlantConfig(block, y_spread, xz_spread, tries, requires_clay, water_plant, emergent_plant, tall_plant)


def configured_plant_patch_feature(rm: ResourceManager, name_parts: ResourceIdentifier, config: PlantConfig, *patch_decorators: Json):
    state_provider = {
        'type': 'tfc:random_property',
        'state': utils.block_state(config.block), 'property': 'age'
    }
    feature = 'simple_block', {'to_place': state_provider}
    heightmap: Heightmap = 'world_surface_wg'
    would_survive = decorate_would_survive(config.block)

    if config.water_plant or config.emergent_plant:
        heightmap = 'ocean_floor_wg'
        would_survive = decorate_would_survive_with_fluid(config.block)

    if config.water_plant:
        feature = 'tfc:block_with_fluid', feature[1]
    if config.emergent_plant:
        feature = 'tfc:emergent_plant', {'block': utils.block_state(config.block)['Name']}
    if config.tall_plant:
        feature = 'tfc:tall_plant', {'block': utils.block_state(config.block)['Name']}

    res = utils.resource_location(rm.domain, name_parts)
    patch_feature = res.join() + '_patch'
    singular_feature = utils.resource_location(rm.domain, name_parts)
    predicate = decorate_air_or_empty_fluid() if not config.requires_clay else decorate_replaceable()

    rm.configured_feature(patch_feature, 'minecraft:random_patch', {
        'tries': config.tries,
        'xz_spread': config.xz_spread,
        'y_spread': config.y_spread,
        'feature': singular_feature.join()
    })
    rm.configured_feature(singular_feature, *feature)
    rm.placed_feature(patch_feature, patch_feature, *patch_decorators)
    rm.placed_feature(singular_feature, singular_feature, decorate_heightmap(heightmap), predicate, would_survive)


class PatchConfig(NamedTuple):
    block: str
    y_spread: int
    xz_spread: int
    tries: int
    any_water: bool
    salt_water: bool
    custom_feature: str
    custom_config: Json


def patch_config(block: str, y_spread: int, xz_spread: int, tries: int = 64, water: Union[bool, Literal['salt']] = False, custom_feature: Optional[str] = None, custom_config: Json = None) -> PatchConfig:
    return PatchConfig(block, y_spread, xz_spread, tries, water == 'salt' or water == True, water == 'salt', custom_feature, custom_config)

def configured_patch_feature(rm: ResourceManager, name_parts: ResourceIdentifier, patch: PatchConfig, *patch_decorators: Json, extra_singular_decorators: Optional[List[Json]] = None, biome_check: bool = True):
    feature = 'minecraft:simple_block'
    config = {'to_place': {'type': 'minecraft:simple_state_provider', 'state': utils.block_state(patch.block)}}
    singular_decorators = []

    if patch.any_water:
        feature = 'tfc:block_with_fluid'
        if patch.salt_water:
            singular_decorators.append(decorate_matching_blocks('tfc:fluid/salt_water'))
        else:
            singular_decorators.append(decorate_air_or_empty_fluid())
    else:
        singular_decorators.append(decorate_replaceable())

    if patch.custom_feature is not None:
        assert patch.custom_config
        feature = patch.custom_feature
        config = patch.custom_config

    heightmap: Heightmap = 'world_surface_wg'
    if patch.any_water:
        heightmap = 'ocean_floor_wg'
        singular_decorators.append(decorate_would_survive_with_fluid(patch.block))
    else:
        singular_decorators.append(decorate_would_survive(patch.block))

    if extra_singular_decorators is not None:
        singular_decorators += extra_singular_decorators
    if biome_check:
        patch_decorators = [*patch_decorators, decorate_biome()]

    res = utils.resource_location(rm.domain, name_parts)
    patch_feature = res.join() + '_patch'
    singular_feature = utils.resource_location(rm.domain, name_parts)

    rm.configured_feature(patch_feature, 'minecraft:random_patch', {
        'tries': patch.tries,
        'xz_spread': patch.xz_spread,
        'y_spread': patch.y_spread,
        'feature': singular_feature.join()
    })
    rm.configured_feature(singular_feature, feature, config)
    rm.placed_feature(patch_feature, patch_feature, *patch_decorators)
    rm.placed_feature(singular_feature, singular_feature, decorate_heightmap(heightmap), *singular_decorators)


def configured_noise_plant_feature(rm: ResourceManager, name_parts: ResourceIdentifier, config: PlantConfig, *patch_decorators: Json, water: bool = True):
    res = utils.resource_location(rm.domain, name_parts)
    patch_feature = res.join() + '_patch'
    singular_feature = utils.resource_location(rm.domain, name_parts)
    placed_decorators = [decorate_heightmap('world_surface_wg'), decorate_air_or_empty_fluid(), decorate_would_survive(config.block)]
    if water:
        placed_decorators.append(decorate_shallow())

    rm.configured_feature(singular_feature, 'minecraft:simple_block', {
        'to_place': {
            'seed': 2345,
            'noise': normal_noise(-3, 1.0),
            'scale': 1.0,
            'states': [utils.block_state(config.block)],
            'variety': [1, 1],
            'slow_noise': normal_noise(-10, 1.0),
            'slow_scale': 1.0,
            'type': 'minecraft:dual_noise_provider'
        }
    })
    rm.configured_feature(patch_feature, 'minecraft:random_patch', {
        'tries': config.tries,
        'xz_spread': config.xz_spread,
        'y_spread': config.y_spread,
        'feature': singular_feature.join()
    })
    rm.placed_feature(patch_feature, patch_feature, *patch_decorators)
    rm.placed_feature(singular_feature, singular_feature, *placed_decorators)


def normal_noise(first_octave: int, amplitude: float):
    return {'firstOctave': first_octave, 'amplitudes': [amplitude]}


def simple_state_provider(name: str) -> Dict[str, Any]:
    return {'type': 'minecraft:simple_state_provider', 'state': utils.block_state(name)}


# Vein Helper Functions

def vein_ore_blocks(vein: Vein, rock: str) -> List[Dict[str, Any]]:
    ore_blocks = [{
        'weight': vein.poor,
        'block': 'tfc:ore/poor_%s/%s' % (vein.ore, rock)
    }, {
        'weight': vein.normal,
        'block': 'tfc:ore/normal_%s/%s' % (vein.ore, rock)
    }, {
        'weight': vein.rich,
        'block': 'tfc:ore/rich_%s/%s' % (vein.ore, rock)
    }]
    if vein.spoiler_ore is not None and rock in vein.spoiler_rocks:
        p = vein.spoiler_rarity * 0.01  # as a percentage of the overall vein
        ore_blocks.append({
            'weight': int(100 * p / (1 - p)),
            'block': 'tfc:ore/%s/%s' % (vein.spoiler_ore, rock)
        })
    elif vein.deposits:
        ore_blocks.append({
            'weight': 10,
            'block': 'tfc:deposit/%s/%s' % (vein.ore, rock)
        })
    return ore_blocks

def vein_density(density: int) -> float:
    assert 0 <= density <= 100, 'Invalid density: %s' % str(density)
    return round(density * 0.01, 2)


# Tree Helper Functions

def forest_config(rm: ResourceManager, min_rain: float, max_rain: float, min_temp: float, max_temp: float, tree: str, old_growth: bool, old_growth_chance: int = None, spoiler_chance: int = None):
    cfg = {
        'min_rain': min_rain,
        'max_rain': max_rain,
        'min_temp': min_temp,
        'max_temp': max_temp,
        'groundcover': [{'block': 'tfc:wood/twig/%s' % tree}],
        'normal_tree': 'tfc:tree/%s' % tree,
        'dead_tree': 'tfc:tree/%s_dead' % tree,
        'old_growth_chance': old_growth_chance,
        'spoiler_old_growth_chance': spoiler_chance,
    }
    if tree != 'palm':
        cfg['groundcover'] += [{'block': 'tfc:wood/fallen_leaves/%s' % tree}]
    if tree not in ('acacia', 'willow', 'gum_arabic'):
        cfg['fallen_log'] = 'tfc:wood/log/%s' % tree
    else:
        cfg['fallen_tree_chance'] = 0
    if tree not in ('palm', 'rosewood', 'sycamore'):
        cfg['bush_log'] = utils.block_state('tfc:wood/wood/%s[natural=True,axis=y]' % tree)
        cfg['bush_leaves'] = 'tfc:wood/leaves/%s' % tree
    if old_growth:
        cfg['old_growth_tree'] = 'tfc:tree/%s_large' % tree
    rm.configured_feature('tree/%s_entry' % tree, 'tfc:forest_entry', cfg)


def overlay_config(tree: str, min_height: int, max_height: int, width: int = 1, radius: int = 1, suffix: str = '', place = None):
    block = 'tfc:wood/log/%s[axis=y,natural=True]' % tree
    tree += suffix
    return {
        'base': 'tfc:%s/base' % tree,
        'overlay': 'tfc:%s/overlay' % tree,
        'trunk': trunk_config(block, min_height, max_height, width),
        'radius': radius,
        'placement': place
    }


def random_config(tree: str, structure_count: int, radius: int = 1, suffix: str = '', trunk: List = None, place=None):
    block = 'tfc:wood/log/%s[axis=y,natural=True]' % tree
    tree += suffix
    cfg = {
        'structures': ['tfc:%s/%d' % (tree, i) for i in range(1, 1 + structure_count)],
        'radius': radius,
        'placement': place
    }
    if trunk is not None:
        cfg['trunk'] = trunk_config(block, *trunk)
    return cfg


def stacked_config(tree: str, min_height: int, max_height: int, width: int, layers: List[Tuple[int, int, int]], radius: int = 1, suffix: str = '', place: Json = None) -> JsonObject:
    # layers consists of each layer, which is a (min_count, max_count, total_templates)
    block = 'tfc:wood/log/%s[axis=y,natural=True]' % tree
    tree += suffix
    return {
        'trunk': trunk_config(block, min_height, max_height, width),
        'layers': [{
            'templates': ['tfc:%s/layer%d_%d' % (tree, 1 + i, j) for j in range(1, 1 + layer[2])],
            'min_count': layer[0],
            'max_count': layer[1]
        } for i, layer in enumerate(layers)],
        'radius': radius,
        'placement': place
    }


def trunk_config(block: str, min_height: int, max_height: int, width: int) -> JsonObject:
    return {
        'state': utils.block_state(block),
        'min_height': min_height,
        'max_height': max_height,
        'width': width
    }


def tree_placement_config(width: int, height: int, allow_submerged: bool = False) -> JsonObject:
    return {
        'width': width,
        'height': height,
        'allow_submerged': allow_submerged
    }


Heightmap = Literal['motion_blocking', 'motion_blocking_no_leaves', 'ocean_floor', 'ocean_floor_wg', 'world_surface', 'world_surface_wg']
HeightProviderType = Literal['constant', 'uniform', 'biased_to_bottom', 'very_biased_to_bottom', 'trapezoid', 'weighted_list']


# Decorators / Placements

def decorate_square() -> Json:
    return 'minecraft:in_square'


def decorate_biome() -> Json:
    return 'tfc:biome'


def decorate_chance(rarity_or_probability: Union[int, float]) -> Json:
    return {'type': 'minecraft:rarity_filter', 'chance': round(1 / rarity_or_probability) if isinstance(rarity_or_probability, float) else rarity_or_probability}


def decorate_count(count: int) -> Json:
    return {'type': 'minecraft:count', 'count': count}


def decorate_shallow(depth: int = 5) -> Json:
    return {'type': 'tfc:shallow_water', 'max_depth': depth}

def decorate_flat_enough(flatness: float = None, radius: int = None, max_depth: int = None):
    return {'type': 'tfc:flat_enough', 'flatness': flatness, 'radius': radius, 'max_depth': max_depth}

def decorate_underground() -> Json:
    return 'tfc:underground'

def decorate_heightmap(heightmap: Heightmap) -> Json:
    assert heightmap in get_args(Heightmap)
    return 'minecraft:heightmap', {'heightmap': heightmap.upper()}


def decorate_range(min_y: VerticalAnchor, max_y: VerticalAnchor, bias: HeightProviderType = 'uniform') -> Json:
    return {
        'type': 'minecraft:height_range',
        'height': height_provider(min_y, max_y, bias)
    }


def decorate_carving_mask(min_y: Optional[VerticalAnchor] = None, max_y: Optional[VerticalAnchor] = None) -> Json:
    return {
        'type': 'tfc:carving_mask',
        'step': 'air',
        'min_y': utils.as_vertical_anchor(min_y) if min_y is not None else None,
        'max_y': utils.as_vertical_anchor(max_y) if max_y is not None else None
    }


def decorate_climate(min_temp: Optional[float] = None, max_temp: Optional[float] = None, min_rain: Optional[float] = None, max_rain: Optional[float] = None, needs_forest: Optional[bool] = False, fuzzy: Optional[bool] = None, min_forest: Optional[str] = None, max_forest: Optional[str] = None) -> Json:
    return {
        'type': 'tfc:climate',
        'min_temperature': min_temp,
        'max_temperature': max_temp,
        'min_rainfall': min_rain,
        'max_rainfall': max_rain,
        'min_forest': 'normal' if needs_forest else min_forest,
        'max_forest': max_forest,
        'fuzzy': fuzzy
    }


def decorate_scanner(direction: str, max_steps: int) -> Json:
    return {
        'type': 'minecraft:environment_scan',
        'max_steps': max_steps,
        'direction_of_search': direction,
        'target_condition': {'type': 'minecraft:solid'},
        'allowed_search_condition': {'type': 'minecraft:matching_blocks', 'blocks': ['minecraft:air']}
    }

def decorate_on_top_of(tag: str) -> Json:
    return {
        'type': 'tfc:on_top',
        'predicate': {
            'type': 'minecraft:matching_block_tag',
            'tag': tag
        }
    }


def decorate_random_offset(xz: int, y: int) -> Json:
    return {'xz_spread': xz, 'y_spread': y, 'type': 'minecraft:random_offset'}


def decorate_matching_blocks(*blocks: str) -> Json:
    return decorate_block_predicate({
        'type': 'matching_blocks',
        'blocks': list(blocks)
    })


def decorate_would_survive(block: str) -> Json:
    return decorate_block_predicate({
        'type': 'would_survive',
        'state': utils.block_state(block)
    })


def decorate_would_survive_with_fluid(block: str) -> Json:
    return decorate_block_predicate({
        'type': 'tfc:would_survive_with_fluid',
        'state': utils.block_state(block)
    })

def decorate_replaceable() -> Json:
    return decorate_block_predicate({'type': 'tfc:replaceable'})

def decorate_air_or_empty_fluid() -> Json:
    return decorate_block_predicate({'type': 'tfc:air_or_empty_fluid'})


def decorate_block_predicate(predicate: Json) -> Json:
    return {
        'type': 'block_predicate_filter',
        'predicate': predicate
    }


# Value Providers

def uniform_float(min_inclusive: float, max_exclusive: float) -> Dict[str, Any]:
    return {
        'type': 'uniform',
        'value': {
            'min_inclusive': min_inclusive,
            'max_exclusive': max_exclusive
        }
    }


def uniform_int(min_inclusive: int, max_inclusive: int) -> Dict[str, Any]:
    return {
        'type': 'uniform',
        'value': {
            'min_inclusive': min_inclusive,
            'max_inclusive': max_inclusive
        }
    }


def trapezoid_float(min_value: float, max_value: float, plateau: float) -> Dict[str, Any]:
    return {
        'type': 'trapezoid',
        'value': {
            'min': min_value,
            'max': max_value,
            'plateau': plateau
        }
    }


def height_provider(min_y: VerticalAnchor, max_y: VerticalAnchor, height_type: HeightProviderType = 'uniform') -> Dict[str, Any]:
    assert height_type in get_args(HeightProviderType)
    return {
        'type': height_type,
        'min_inclusive': utils.as_vertical_anchor(min_y),
        'max_inclusive': utils.as_vertical_anchor(max_y)
    }


def biome(rm: ResourceManager, name: str, category: str, boulders: bool = False, spawnable: bool = True, ocean_features: Union[bool, Literal['both']] = False, lake_features: Union[bool, Literal['default']] = 'default', volcano_features: bool = False, reef_features: bool = False, hot_spring_features: Union[bool, Literal['empty']] = False):
    spawners = {}
    soil_discs = []
    large_features = []
    surface_decorations = []
    costs = {}

    if ocean_features == 'both':  # Both applies both ocean + land features. True or False applies only one
        land_features = True
        ocean_features = True
    else:
        land_features = not ocean_features
    if lake_features == 'default':  # Default = Lakes are on all non-ocean biomes. True/False to force either way
        lake_features = not ocean_features

    if boulders:
        large_features.append('#tfc:feature/boulders')

    # Oceans
    if ocean_features:
        large_features.append('#tfc:feature/icebergs')
        surface_decorations.append('#tfc:feature/ocean_plants')

        if name == 'shore':
            surface_decorations.append('#tfc:feature/shore_decorations')
            spawners['creature'] = [entity for entity in SHORE_CREATURES.values()]
        else:
            surface_decorations.append('#tfc:feature/ocean_decorations')

        spawners['water_ambient'] = [entity for entity in OCEAN_AMBIENT.values()]
        spawners['water_creature'] = [entity for entity in OCEAN_CREATURES.values()]
        spawners['underground_water_creature'] = [entity for entity in UNDERGROUND_WATER_CREATURES.values()]
        costs['tfc:octopoteuthis'] = {'energy_budget': 0.12, 'charge': 1.0}

    if category == 'river':
        spawners['water_ambient'] = [entity for entity in LAKE_AMBIENT.values()]
        soil_discs.append('#tfc:feature/ore_deposits')

    if category in ('river', 'lake', 'swamp'):
        surface_decorations.append('tfc:plant/dry_phragmite')

    if name == 'deep_ocean_trench':
        large_features.append('tfc:lava_hot_spring')

    if 'lake' in name:
        spawners['water_creature'] = [entity for entity in LAKE_CREATURES.values()]
    spawners['monster'] = [entity for entity in VANILLA_MONSTERS.values()]

    if reef_features:
        large_features.append('tfc:coral_reef')

    # Continental / Land Features
    if land_features:
        soil_discs.append('#tfc:feature/soil_discs')
        large_features += ['tfc:forest', 'tfc:bamboo', 'tfc:cave_vegetation']
        surface_decorations.append('#tfc:feature/land_plants')
        spawners['creature'] = [entity for entity in LAND_CREATURES.values()]

    if volcano_features:
        large_features.append('#tfc:feature/volcanoes')

    if hot_spring_features:  # can be True, 'empty'
        if hot_spring_features == 'empty':
            large_features.append('tfc:random_empty_hot_spring')
        else:
            large_features.append('tfc:random_active_hot_spring')

    # Feature Tags
    # We don't directly use vanilla's generation step, but we line this up *approximately* with it, so that mods that add features add them in roughly the right location
    feature_tags = [
        '#tfc:in_biome/erosion',  # Raw Generation
        '#tfc:in_biome/all_lakes' if lake_features else '#tfc:in_biome/underground_lakes',  # Lakes
        '#tfc:in_biome/soil_discs/%s' % name,  # Local Modifications
        '#tfc:in_biome/underground_structures',  # Underground Structures
        '#tfc:in_biome/surface_structures',  # Surface Structures
        '#tfc:in_biome/strongholds',  # Strongholds
        '#tfc:in_biome/veins',  # Underground Ores
        '#tfc:in_biome/underground_decoration',  # Underground Decoration
        '#tfc:in_biome/large_features/%s' % name,  # Fluid Springs (we co-opt this as they likely won't interfere and it's in the right order)
        '#tfc:in_biome/surface_decoration/%s' % name,  # Vegetal Decoration
        '#tfc:in_biome/top_layer_modification'  # Top Layer Modification
    ]

    placed_feature_118_hack(rm, ('in_biome/soil_discs', name), *soil_discs)
    placed_feature_118_hack(rm, ('in_biome/large_features', name), *large_features)
    placed_feature_118_hack(rm, ('in_biome/surface_decoration', name), *surface_decorations)

    if volcano_features:
        biome_tag(rm, 'is_volcanic', name)
    if 'lake' in name:
        biome_tag(rm, 'is_lake', name)
    if 'river' in name:
        biome_tag(rm, 'is_river', name)

    # todo: remove in 1.19
    feature_tags = [
        [tag[1:]]
        for tag in feature_tags
    ]

    rm.lang('biome.tfc.%s' % name, lang(name))
    rm.biome(
        name_parts=name,
        precipitation='rain',  # Hardcode to rain to make some mixins redundant since they do a == rain check.
        category=category,
        temperature=0.5,
        downfall=0.5,
        effects={
            'fog_color': 0xC0D8FF,
            'sky_color': 0x84E6FF,
            'water_color': 0x3F76E4,
            'water_fog_color': 0x050533
        },
        spawners=spawners,
        air_carvers=['tfc:cave', 'tfc:canyon'],
        water_carvers=[],
        features=feature_tags,
        player_spawn_friendly=spawnable,
        creature_spawn_probability=0.08,
        spawn_costs=costs
    )


# Tags

def placed_feature_tag(rm: ResourceManager, name_parts: ResourceIdentifier, *values: ResourceIdentifier):
    return rm.tag(name_parts, 'worldgen/placed_feature', *values)


def placed_feature_118_hack(rm, name_parts: ResourceIdentifier, *values: ResourceIdentifier):
    placed_feature_tag(rm, name_parts, *values)
    configured_placed_feature(rm, name_parts, 'tfc:multiple', {'features': '#' + utils.resource_location(rm.domain, name_parts).join()})


def configured_feature_tag(rm: ResourceManager, name_parts: ResourceIdentifier, *values: ResourceIdentifier):
    return rm.tag(name_parts, 'worldgen/configured_feature', *values)


def biome_tag(rm: ResourceManager, name_parts: ResourceIdentifier, *values: ResourceIdentifier):
    return rm.tag(name_parts, 'worldgen/biome', *values)


def expand_rocks(rocks_list: List[str], path: Optional[str] = None) -> List[str]:
    rocks = []
    for rock_spec in rocks_list:
        if rock_spec in ROCKS:
            rocks.append(rock_spec)
        elif rock_spec in ROCK_CATEGORIES:
            rocks += [r for r, d in ROCKS.items() if d.category == rock_spec]
        else:
            raise RuntimeError('Unknown rock or rock category specification: %s at %s' % (rock_spec, path if path is not None else '??'))
    return rocks


def join_not_empty(c: str, *elements: str) -> str:
    return c.join((item for item in elements if item != ''))


def count_weighted_list(*pairs: Tuple[Any, int]) -> List[Any]:
    return [item for item, count in pairs for _ in range(count)]