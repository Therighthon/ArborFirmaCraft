# Handles generation of all world gen objects

from typing import Union, get_args

from mcresources import ResourceManager, utils
from mcresources.type_definitions import ResourceIdentifier, JsonObject, Json, VerticalAnchor

from constants import *


def generate(rm: ResourceManager):

    # Trees / Forests
    forest_config(rm, 90, 275, 16, 40, 'acacia', 'acacia', True)
    forest_config(rm, 90, 275, 16, 40, 'gum_arabic', 'gum_arabic', False)
    forest_config(rm, 350, 500, 24, 40, 'acacia_koa', 'acacia', False)
    forest_config(rm, 60, 240, -2, 10, 'ash', 'ash', True)
    forest_config(rm, 80, 265, 10, 20, 'evergreen_ash', 'ash', True)
    forest_config(rm, 350, 500, -18, 6, 'aspen', 'aspen', False)
    forest_config(rm, 125, 310, -12, 8, 'birch', 'birch', False)
    forest_config(rm, 35, 150, 16, 40, 'blackwood', 'blackwood', True)
    forest_config(rm, 85, 285, 12, 22, 'mpingo_blackwood', 'blackwood', False)
    forest_config(rm, 150, 340, -4, 12, 'chestnut', 'chestnut', False)
    forest_config(rm, 305, 500, -4, 4, 'douglas_fir', 'douglas_fir', True)
    forest_config(rm, 220, 345, -6, 0, 'mountain_fir', 'douglas_fir', True)
    forest_config(rm, 210, 500, -16, -2, 'balsam_fir', 'douglas_fir', True)
    forest_config(rm, 210, 400, 0, 12, 'hickory', 'hickory', False)
    forest_config(rm, 375, 480, 14, 20, 'scrub_hickory', 'hickory', True)
    forest_config(rm, 360, 500, 20, 40, 'kapok', 'kapok', True)
    forest_config(rm, 260, 360, -8, 10, 'maple', 'maple', True)
    forest_config(rm, 405, 500, -6, 12, 'bigleaf_maple', 'maple', False)
    forest_config(rm, 240, 320, -2, 8, 'weeping_maple', 'maple', True)
    forest_config(rm, 390, 500, 12, 20, 'live_oak', 'oak', True)
    forest_config(rm, 150, 330, 10, 22, 'black_oak', 'black_oak', False)
    forest_config(rm, 210, 320, -6, 10, 'oak', 'oak', False)
    forest_config(rm, 200, 405, 16, 40, 'palm', 'palm', False)
    forest_config(rm, 60, 270, -18, -4, 'pine', 'pine', True)
    forest_config(rm, 140, 290, 6, 14, 'stone_pine', 'pine', True)
    forest_config(rm, 185, 320, -10, 4, 'red_pine', 'pine', False)
    forest_config(rm, 150, 500, -18, -8, 'tamarack', 'pine', True)
    forest_config(rm, 245, 360, 12, 40, 'rosewood', 'rosewood', False)
    forest_config(rm, 340, 440, 16, 24, 'giant_rosewood', 'rosewood', False)
    forest_config(rm, 320, 500, -2, 10, 'coast_redwood', 'sequoia', True)
    forest_config(rm, 320, 500, -2, 10, 'sequoia', 'sequoia', True)
    forest_config(rm, 330, 500, -20, -4, 'spruce', 'spruce', True)
    forest_config(rm, 320, 390, -14, 4, 'coast_spruce', 'spruce', False)
    forest_config(rm, 370, 500, -8, 0, 'sitka_spruce', 'spruce', True)
    forest_config(rm, 220, 360, -20, -8, 'black_spruce', 'spruce', True)
    forest_config(rm, 330, 480, -8, 14, 'sycamore', 'sycamore', True)
    forest_config(rm, 100, 220, -16, 4, 'white_cedar', 'white_cedar', True)
    forest_config(rm, 165, 450, 8, 22, 'atlas_cedar', 'white_cedar', False)
    forest_config(rm, 330, 500, -4, 14, 'willow', 'willow', True)
    forest_config(rm, 355, 500, 14, 22, 'weeping_willow', 'willow', False)
    forest_config(rm, 300, 450, 16, 40, 'rainbow_eucalyptus', 'rainbow_eucalyptus', False)
    forest_config(rm, 170, 325, 10, 24, 'eucalyptus', 'eucalyptus', False)
    forest_config(rm, 420, 500, 10, 20, 'mountain_ash', 'eucalyptus', True)
    forest_config(rm, 30, 190, 14, 28, 'baobab', 'baobab', True)
    forest_config(rm, 390, 500, 24, 40, 'hevea', 'hevea', False)
    forest_config(rm, 300, 430, 20, 40, 'mahogany', 'mahogany', False)
    forest_config(rm, 360, 500, 22, 40, 'tualang', 'tualang', True)
    forest_config(rm, 215, 330, 22, 40, 'teak', 'teak', False)
    forest_config(rm, 100, 260, 2, 14, 'cypress', 'cypress', False)
    forest_config(rm, 290, 375, 6, 16, 'weeping_cypress', 'cypress', False)
    forest_config(rm, 410, 500, -10, 2, 'redcedar', 'redcedar', False)
    forest_config(rm, 360, 500, 4, 18, 'bald_cypress', 'cypress', True)
    forest_config(rm, 340, 500, 18, 40, 'fig', 'fig', False)
    # flat: acacia, ash, chestnut, maple, sequoia, spruce, willow

    # configured_placed_feature(rm, 'forest', 'tfc:forest', {
    #     'entries': '#tfc:forest_trees',
    #     'types': {
    #         'none': {
    #             'per_chunk_chance': 0
    #         },
    #         'sparse': {
    #             'tree_count': uniform_int(1, 3),
    #             'groundcover_count': 10,
    #             'per_chunk_chance': 0.08,
    #             'bush_count': 0,
    #             'has_spoiler_old_growth': True
    #         },
    #         'edge': {
    #             'tree_count': 2,
    #             'groundcover_count': 15
    #         },
    #         'normal': {
    #             'tree_count': 5,
    #             'groundcover_count': 30,
    #             'has_spoiler_old_growth': True
    #         },
    #         'old_growth': {
    #             'tree_count': 7,
    #             'groundcover_count': 40,
    #             'allows_old_growth': True
    #         }
    #     }
    # })

    configured_feature_tag(rm, 'forest_trees', *['tfc:tree/%s_entry' % tree for tree in WOODS.keys()])

    configured_placed_feature(rm, ('tree', 'evergreen_ash'), 'tfc:overlay_tree', overlay_config('evergreen_ash', 4, 6, basic_wood='ash', place=tree_placement_config(1, 5)))

    configured_placed_feature(rm, ('tree', 'acacia'), 'tfc:random_tree', random_config('acacia', 35, place=tree_placement_config(2, 3)))
    configured_placed_feature(rm, ('tree', 'acacia_large'), 'tfc:random_tree', random_config('acacia', 6, 2, '_large', place=tree_placement_config(2, 5)))
    configured_placed_feature(rm, ('tree', 'acacia_dead'), 'tfc:random_tree', random_config('acacia', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'ash'), 'tfc:overlay_tree', overlay_config('ash', 3, 5, basic_wood='ash', place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'ash_large'), 'tfc:random_tree', random_config('ash', 5, 2, '_large', place=tree_placement_config(2, 5)))
    configured_placed_feature(rm, ('tree', 'ash_dead'), 'tfc:random_tree', random_config('ash', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'aspen'), 'tfc:random_tree', random_config('aspen', 16, trunk=[3, 5, 1, 'aspen'], place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'aspen_dead'), 'tfc:random_tree', random_config('aspen', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'birch'), 'tfc:random_tree', random_config('birch', 16, trunk=[2, 3, 1, 'birch'], place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'birch_dead'), 'tfc:random_tree', random_config('birch', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'blackwood'), 'tfc:random_tree', random_config('blackwood', 10, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'blackwood_large'), 'tfc:random_tree', random_config('blackwood', 10, 1, '_large', place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'blackwood_dead'), 'tfc:random_tree', random_config('blackwood', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    # configured_placed_feature(rm, ('tree', 'chestnut'), 'tfc:overlay_tree', overlay_config('chestnut', 2, 4, place=tree_placement_config(1, 5)))
    # configured_placed_feature(rm, ('tree', 'chestnut_dead'), 'tfc:random_tree', random_config('chestnut', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    # configured_placed_feature(rm, ('tree', 'douglas_fir'), 'tfc:random_tree', random_config('douglas_fir', 9, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'douglas_fir_large'), 'tfc:stacked_tree', stacked_config('douglas_fir', 8, 12, 2, [(2, 3, 3), (1, 1, 3), (1, 1, 3)], 2, '_large', basic_wood= 'douglas_fir', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'douglas_fir_dead'), 'tfc:random_tree', random_config('douglas_fir', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    # configured_placed_feature(rm, ('tree', 'hickory'), 'tfc:random_tree', random_config('hickory', 9, place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'hickory_large'), 'tfc:random_tree', random_config('hickory', 5, 2, '_large', place=tree_placement_config(1, 9)))
    configured_placed_feature(rm, ('tree', 'hickory_dead'), 'tfc:random_tree', random_config('hickory', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'maple'), 'tfc:overlay_tree', overlay_config('maple', 2, 4, basic_wood= 'maple', place=tree_placement_config(1, 5)))
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
    configured_placed_feature(rm, ('tree', 'sequoia_large'), 'tfc:stacked_tree', stacked_config('sequoia', 0, 1, 5, [(1, 1, 7), (2, 3, 8), (3, 4, 8), (1, 1, 4)], 2, '_large', basic_wood= 'sequoia', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'sequoia_dead'), 'tfc:random_tree', random_config('sequoia', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'spruce'), 'tfc:random_tree', random_config('spruce', 7, place=tree_placement_config(1, 3)))
    configured_placed_feature(rm, ('tree', 'spruce_large'), 'tfc:stacked_tree', stacked_config('spruce', 4, 7, 2, [(2, 3, 3), (1, 2, 3), (1, 1, 3)], 2, '_large', basic_wood= 'spruce', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'spruce_dead'), 'tfc:random_tree', random_config('spruce', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    #configured_placed_feature(rm, ('tree', 'sycamore'), 'tfc:overlay_tree', overlay_config('sycamore', 2, 5, place=tree_placement_config(1, 5, True)))
    configured_placed_feature(rm, ('tree', 'sycamore_large'), 'tfc:random_tree', random_config('sycamore', 5, 2, '_large', place=tree_placement_config(2, 5, True)))
    configured_placed_feature(rm, ('tree', 'sycamore_dead'), 'tfc:random_tree', random_config('sycamore', 6, 1, '_dead', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'white_cedar'), 'tfc:overlay_tree', overlay_config('white_cedar', 2, 4, basic_wood= 'white_cedar', place=tree_placement_config(1, 5)))
    configured_placed_feature(rm, ('tree', 'white_cedar_large'), 'tfc:overlay_tree', overlay_config('white_cedar', 2, 5, 1, 1, '_large', basic_wood= 'white_cedar', place=tree_placement_config(1, 9)))
    configured_placed_feature(rm, ('tree', 'white_cedar_dead'), 'tfc:random_tree', random_config('white_cedar', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'willow'), 'tfc:random_tree', random_config('willow', 7, place=tree_placement_config(1, 3, True)))
    configured_placed_feature(rm, ('tree', 'willow_large'), 'tfc:random_tree', random_config('willow', 14, 1, '_large', place=tree_placement_config(2, 3, True)))
    configured_placed_feature(rm, ('tree', 'willow_dead'), 'tfc:random_tree', random_config('willow', 3, 1, '_dead', place=tree_placement_config(2, 3, True)))

    configured_placed_feature(rm, ('tree', 'gum_arabic'), 'tfc:random_tree', random_config('gum_arabic', 35, 1, place=tree_placement_config(2, 3, False)))
    configured_placed_feature(rm, ('tree', 'gum_arabic_dead'), 'tfc:random_tree', random_config('gum_arabic', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'acacia_koa'), 'tfc:random_tree', random_config('acacia_koa', 15, 1, place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'acacia_koa_dead'), 'tfc:random_tree', random_config('acacia_koa', 4, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'ash'), 'tfc:random_tree', random_config('ash', 14, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'coast_redwood'), 'tfc:random_tree', random_config('coast_redwood', 7, place=tree_placement_config(1, 3)))
    configured_placed_feature(rm, ('tree', 'coast_redwood_large'), 'tfc:stacked_tree', stacked_config('coast_redwood', 8, 16, 2, [(3, 4, 3), (1, 2, 3), (1, 1, 3)], 2, '_large', basic_wood= 'sequoia', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'coast_redwood_dead'), 'tfc:random_tree', random_config('coast_redwood', 6, 1, '_dead', place=tree_placement_config(1, 9, True)))
    configured_placed_feature(rm, ('tree', 'evergreen_ash_dead'), 'tfc:random_tree', random_config('evergreen_ash', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'evergreen_ash_large'), 'tfc:random_tree', random_config('evergreen_ash', 14, 1, '_large', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'mpingo_blackwood'), 'tfc:random_tree', random_config('mpingo_blackwood', 35, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'mpingo_blackwood_dead'), 'tfc:random_tree', random_config('mpingo_blackwood', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'chestnut'), 'tfc:random_tree', random_config('chestnut', 14, 1, place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'douglas_fir'), 'tfc:random_tree', random_config('douglas_fir', 10, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'mountain_fir'), 'tfc:random_tree', random_config('mountain_fir', 9, 1, place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'mountain_fir_dead'), 'tfc:random_tree', random_config('mountain_fir', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'mountain_fir_large'), 'tfc:random_tree', random_config('mountain_fir', 5, 1, '_large', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'balsam_fir'), 'tfc:random_tree', random_config('balsam_fir', 9, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'balsam_fir_dead'), 'tfc:random_tree', random_config('balsam_fir', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'balsam_fir_large'), 'tfc:random_tree', random_config('balsam_fir', 11, 1, '_large', place=tree_placement_config(1, 1, False)))
    configured_placed_feature(rm, ('tree', 'hickory'), 'tfc:random_tree', random_config('hickory', 14, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'scrub_hickory'), 'tfc:random_tree', random_config('scrub_hickory', 10, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'scrub_hickory_dead'), 'tfc:random_tree', random_config('scrub_hickory', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'scrub_hickory_large'), 'tfc:random_tree', random_config('scrub_hickory', 10, 1, '_large', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'kapok'), 'tfc:random_tree', random_config('kapok', 15, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'kapok_large'), 'tfc:random_tree', random_config('kapok', 6, 2, '_large', place=tree_placement_config(2, 15, True)))
    configured_placed_feature(rm, ('tree', 'bigleaf_maple'), 'tfc:random_tree', random_config('bigleaf_maple', 14, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'bigleaf_maple_dead'), 'tfc:random_tree', random_config('bigleaf_maple', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'weeping_maple'), 'tfc:random_tree', random_config('weeping_maple', 10, 1, place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'weeping_maple_dead'), 'tfc:random_tree', random_config('weeping_maple', 6, 1, '_dead', place=tree_placement_config(1, 0, False)))
    configured_placed_feature(rm, ('tree', 'weeping_maple_large'), 'tfc:random_tree', random_config('weeping_maple', 7, 1, '_large', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'live_oak'), 'tfc:random_tree', random_config('live_oak', 14, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'live_oak_dead'), 'tfc:random_tree', random_config('live_oak', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'live_oak_large'), 'tfc:random_tree', random_config('live_oak', 5, 2, '_large', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'black_oak'), 'tfc:random_tree', random_config('black_oak', 15, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'black_oak_dead'), 'tfc:random_tree', random_config('black_oak', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'stone_pine'), 'tfc:random_tree', random_config('stone_pine', 19, 1, trunk=[3, 6, 1, 'pine'], place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'stone_pine_dead'), 'tfc:random_tree', random_config('stone_pine', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'stone_pine_large'), 'tfc:random_tree', random_config('stone_pine', 19, 1, '_large', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'red_pine'), 'tfc:random_tree', random_config('red_pine', 12, 1, place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'red_pine_dead'), 'tfc:random_tree', random_config('red_pine', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'tamarack'), 'tfc:random_tree', random_config('tamarack', 11, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'tamarack_dead'), 'tfc:random_tree', random_config('tamarack', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'tamarack_large'), 'tfc:random_tree', random_config('tamarack', 11, 1, '_large', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'rosewood'), 'tfc:random_tree', random_config('rosewood', 18, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'giant_rosewood'), 'tfc:random_tree', random_config('giant_rosewood', 15, 1, place=tree_placement_config(2, 3, True)))
    configured_placed_feature(rm, ('tree', 'giant_rosewood_dead'), 'tfc:random_tree', random_config('giant_rosewood', 4, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'coast_spruce'), 'tfc:random_tree', random_config('coast_spruce', 9, 1, trunk=[3, 7, 1, 'spruce'], place=tree_placement_config(1, 4, True)))
    configured_placed_feature(rm, ('tree', 'coast_spruce_dead'), 'tfc:random_tree', random_config('coast_spruce', 6, 1, '_dead', place=tree_placement_config(1, 3, True)))
    configured_placed_feature(rm, ('tree', 'sitka_spruce'), 'tfc:random_tree', random_config('sitka_spruce', 9, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'sitka_spruce_dead'), 'tfc:random_tree', random_config('sitka_spruce', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'sitka_spruce_large'), 'tfc:random_tree', random_config('sitka_spruce', 5, 1, '_large', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'black_spruce'), 'tfc:random_tree', random_config('black_spruce', 11, 1, place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'black_spruce_dead'), 'tfc:random_tree', random_config('black_spruce', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'black_spruce_large'), 'tfc:random_tree', random_config('black_spruce', 11, 1, '_large', place=tree_placement_config(1, 6, True)))
    configured_placed_feature(rm, ('tree', 'sycamore'), 'tfc:random_tree', random_config('sycamore', 14, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'atlas_cedar'), 'tfc:random_tree', random_config('atlas_cedar', 17, 1, place=tree_placement_config(2, 3, False)))
    configured_placed_feature(rm, ('tree', 'atlas_cedar_dead'), 'tfc:random_tree', random_config('atlas_cedar', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'weeping_willow'), 'tfc:random_tree', random_config('weeping_willow', 14, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'weeping_willow_dead'), 'tfc:random_tree', random_config('weeping_willow', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'rainbow_eucalyptus'), 'tfc:random_tree', random_config('rainbow_eucalyptus', 15, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'rainbow_eucalyptus_dead'), 'tfc:random_tree', random_config('rainbow_eucalyptus', 4, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'eucalyptus'), 'tfc:random_tree', random_config('eucalyptus', 18, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'eucalyptus_dead'), 'tfc:random_tree', random_config('eucalyptus', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'mountain_ash'), 'tfc:random_tree', random_config('mountain_ash', 18, 1, trunk=[4, 7, 1, 'eucalyptus'], place=tree_placement_config(1, 8, False)))
    configured_placed_feature(rm, ('tree', 'mountain_ash_dead'), 'tfc:random_tree', random_config('mountain_ash', 6, 1, '_dead', place=tree_placement_config(1, 8, False)))
    configured_placed_feature(rm, ('tree', 'mountain_ash_large'), 'tfc:stacked_tree', stacked_config('mountain_ash', 5, 8, 2, [(1, 2, 12), (1, 2, 10), (1, 1, 4), (1, 1, 5)], 2, '_large', basic_wood= 'eucalyptus', place=tree_placement_config(2, 7)))
    configured_placed_feature(rm, ('tree', 'baobab'), 'tfc:random_tree', random_config('baobab', 11, 10, trunk=[2, 3, 2, 'baobab'], place=tree_placement_config(3, 12, True)))
    configured_placed_feature(rm, ('tree', 'baobab_dead'), 'tfc:random_tree', random_config('baobab', 6, 1, '_dead', place=tree_placement_config(1, 0, True)))
    configured_placed_feature(rm, ('tree', 'baobab_large'), 'tfc:random_tree', random_config('baobab', 7, 1, '_large', trunk=[3, 5, 3, 'baobab'], place=tree_placement_config(5, 12, True)))
    configured_placed_feature(rm, ('tree', 'hevea'), 'tfc:random_tree', random_config('hevea', 18, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'hevea_dead'), 'tfc:random_tree', random_config('hevea', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'mahogany'), 'tfc:random_tree', random_config('mahogany', 17, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'mahogany_dead'), 'tfc:random_tree', random_config('mahogany', 4, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'tualang'), 'tfc:random_tree', random_config('tualang', 6, 1, trunk=[13, 18, 1, 'tualang'], place=tree_placement_config(1, 14, True)))
    configured_placed_feature(rm, ('tree', 'tualang_dead'), 'tfc:random_tree', random_config('tualang', 6, 1, '_dead', place=tree_placement_config(1, 5, True)))
    configured_placed_feature(rm, ('tree', 'tualang_large'), 'tfc:random_tree', random_config('tualang', 6, 1, '_large', trunk=[19, 24, 1, 'tualang'], place=tree_placement_config(1, 21, True)))
    configured_placed_feature(rm, ('tree', 'teak'), 'tfc:random_tree', random_config('teak', 17, 1, place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'teak_dead'), 'tfc:random_tree', random_config('teak', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'cypress'), 'tfc:random_tree', random_config('cypress', 14, 1, place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'cypress_dead'), 'tfc:random_tree', random_config('cypress', 6, 1, '_dead', place=tree_placement_config(1, 2, False)))
    configured_placed_feature(rm, ('tree', 'weeping_cypress'), 'tfc:random_tree', random_config('weeping_cypress', 11, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'weeping_cypress_dead'), 'tfc:random_tree', random_config('weeping_cypress', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'redcedar'), 'tfc:random_tree', random_config('redcedar', 10, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'redcedar_dead'), 'tfc:random_tree', random_config('redcedar', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'bald_cypress'), 'tfc:random_tree', random_config('bald_cypress', 9, 1, place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'bald_cypress_dead'), 'tfc:random_tree', random_config('bald_cypress', 6, 1, '_dead', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'bald_cypress_large'), 'tfc:random_tree', random_config('bald_cypress', 5, 1, '_large', place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'fig'), 'tfc:random_tree', random_config('fig', 6, 1, trunk=[3, 5, 1, 'fig'], place=tree_placement_config(1, 2, True)))
    configured_placed_feature(rm, ('tree', 'fig_dead'), 'tfc:random_tree', random_config('fig', 4, 1, '_dead', place=tree_placement_config(1, 2, True)))


def configured_placed_feature(rm: ResourceManager, name_parts: ResourceIdentifier, feature: Optional[ResourceIdentifier] = None, config: JsonObject = None, *placements: Json):
    res = utils.resource_location('tfc', name_parts)
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

def forest_config(rm: ResourceManager, min_rain: float, max_rain: float, min_temp: float, max_temp: float, tree: str, basic_wood: str, old_growth: bool, old_growth_chance: int = None, spoiler_chance: int = None):

    wood_prefix = 'tfc'
    if basic_wood == 'baobab' or basic_wood == 'eucalyptus' or basic_wood == 'rainbow_eucalyptus' or basic_wood == 'hevea' or basic_wood == 'mahogany' or basic_wood == 'tualang' or basic_wood == 'teak' or basic_wood == 'cypress' or basic_wood == 'fig' or basic_wood == 'black_oak'  or basic_wood == 'redcedar' or basic_wood == 'gum_arabic':
        wood_prefix = 'afc'

    leaf_prefix = 'afc'
    if basic_wood == tree:
        leaf_prefix = wood_prefix

    #adv_wood is used for groundcover items, Unique Logs don't have associated groundcover
    adv_wood = basic_wood
    if adv_wood == 'rainbow_eucalyptus':
        adv_wood = 'eucalyptus'

    cfg = {
        'min_rain': min_rain,
        'max_rain': max_rain,
        'min_temp': min_temp,
        'max_temp': max_temp,
        'groundcover': [{'block': '%s:wood/twig/%s' % (wood_prefix, adv_wood)}],
        'normal_tree': 'tfc:tree/%s' % tree,
        'dead_tree': 'tfc:tree/%s_dead' % tree,
        'old_growth_chance': old_growth_chance,
        'spoiler_old_growth_chance': spoiler_chance,
    }

    if tree != 'palm':
        cfg['groundcover'] += [{'block': '%s:wood/fallen_leaves/%s' % (wood_prefix, adv_wood)}]
    if tree not in ('acacia', 'willow', 'gum_arabic'):
        cfg['fallen_log'] = '%s:wood/log/%s' % (wood_prefix, basic_wood)
    else:
        cfg['fallen_tree_chance'] = 0
    if tree not in ('palm', 'rosewood', 'sycamore'):
        cfg['bush_log'] = utils.block_state('%s:wood/wood/%s[natural=true,axis=y]' % (wood_prefix, basic_wood))
        cfg['bush_leaves'] = '%s:wood/leaves/%s' % (leaf_prefix, tree)
    if old_growth:
        cfg['old_growth_tree'] = 'tfc:tree/%s_large' % tree
    rm.configured_feature('tree/%s_entry' % tree, 'tfc:forest_entry', cfg)


def overlay_config(tree: str, min_height: int, max_height: int, width: int = 1, radius: int = 1, suffix: str = '', basic_wood: str = 'oak', place = None):
    wood_prefix = 'tfc'
    if basic_wood == 'baobab' or basic_wood == 'eucalyptus' or basic_wood == 'rainbow_eucalyptus' or basic_wood == 'hevea' or basic_wood == 'mahogany' or basic_wood == 'tualang' or basic_wood == 'teak' or basic_wood == 'cypress' or basic_wood == 'fig' or basic_wood == 'black_oak'  or basic_wood == 'redcedar' or basic_wood == 'gum_arabic':
        wood_prefix = 'afc'

    block = '%s:wood/log/%s[axis=y,natural=true]' % (wood_prefix, basic_wood)
    tree += suffix
    return {
        'base': 'tfc:%s/base' % tree,
        'overlay': 'tfc:%s/overlay' % tree,
        'trunk': trunk_config(block, min_height, max_height, width),
        'radius': radius,
        'placement': place
    }


def random_config(tree: str, structure_count: int, radius: int = 1, suffix: str = '', trunk: List = None, place=None):
    wood_prefix = 'tfc'
    basic_wood = 'oak'
    if trunk is not None:
        basic_wood = trunk[3]
    if basic_wood == 'baobab' or basic_wood == 'eucalyptus' or basic_wood == 'rainbow_eucalyptus' or basic_wood == 'hevea' or basic_wood == 'mahogany' or basic_wood == 'tualang' or basic_wood == 'teak' or basic_wood == 'cypress' or basic_wood == 'fig' or basic_wood == 'black_oak'  or basic_wood == 'redcedar' or basic_wood == 'gum_arabic':
        wood_prefix = 'afc'

    block = '%s:wood/log/%s[axis=y,natural=true]' % (wood_prefix, basic_wood)
    tree += suffix
    cfg = {
        'structures': ['tfc:%s/%d' % (tree, i) for i in range(1, 1 + structure_count)],
        'radius': radius,
        'placement': place
    }
    if trunk is not None:
        cfg['trunk'] = trunk_config(block, trunk[0], trunk[1], trunk[2])
    return cfg


def stacked_config(tree: str, min_height: int, max_height: int, width: int, layers: List[Tuple[int, int, int]], radius: int = 1, suffix: str = '', basic_wood: str = 'oak', place: Json = None) -> JsonObject:
    # layers consists of each layer, which is a (min_count, max_count, total_templates)
    wood_prefix = 'tfc'
    if basic_wood == 'baobab' or basic_wood == 'eucalyptus' or basic_wood == 'rainbow_eucalyptus' or basic_wood == 'hevea' or basic_wood == 'mahogany' or basic_wood == 'tualang' or basic_wood == 'teak' or basic_wood == 'cypress' or basic_wood == 'fig' or basic_wood == 'black_oak'  or basic_wood == 'redcedar' or basic_wood == 'gum_arabic':
        wood_prefix = 'afc'

    block = '%s:wood/log/%s[axis=y,natural=true]' % (wood_prefix, basic_wood)
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