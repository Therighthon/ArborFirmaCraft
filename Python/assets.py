#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

import itertools
import os

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables

from constants import *


def generate(rm: ResourceManager):


    # Plants
    # for plant, plant_data in PLANTS.items():
    #     rm.lang('block.afc.plant.%s' % plant, lang(plant))
    #     p = 'afc:plant/%s' % plant
    #     lower_only = loot_tables.block_state_property(p + '[part=lower]')
    #     if plant_data.type == 'short_grass':
    #         rm.block_loot(p, ({
    #             'name': p,
    #             'conditions': [loot_tables.match_tag('forge:shears')],
    #         }, {
    #             'name': 'afc:straw',
    #             'conditions': [loot_tables.match_tag('afc:sharp_tools')]
    #         }))
    #     elif plant_data.type == 'tall_grass':
    #         rm.block_loot(p, ({
    #             'name': p,
    #             'conditions': [loot_tables.match_tag('forge:shears'), lower_only],
    #         }, {
    #             'name': 'afc:straw',
    #             'conditions': [loot_tables.match_tag('afc:sharp_tools')]
    #         }))
    #     elif plant in SEAWEED:
    #         rm.block_loot(p, (
    #             {'name': 'afc:groundcover/seaweed', 'conditions': [loot_tables.match_tag('afc:sharp_tools'), loot_tables.random_chance(0.3)]},
    #             {'name': p, 'conditions': [loot_tables.match_tag('forge:shears')]}
    #         ))
    #     elif plant_data.type in ('tall_plant', 'emergent', 'emergent_fresh', 'cactus'):
    #         if plant == 'cattail':
    #             rm.block_loot(p, (
    #                 {'name': 'afc:food/cattail_root', 'conditions': [loot_tables.match_tag('afc:sharp_tools'), loot_tables.random_chance(0.3), lower_only]},
    #                 {'name': p, 'conditions': [loot_tables.match_tag('forge:shears'), lower_only]}
    #             ))
    #         elif plant == 'water_taro':
    #             rm.block_loot(p, (
    #                 {'name': 'afc:food/taro_root', 'conditions': [loot_tables.match_tag('afc:sharp_tools'), loot_tables.random_chance(0.3), lower_only]},
    #                 {'name': p, 'conditions': [loot_tables.match_tag('forge:shears'), lower_only]}
    #             ))
    #         else:
    #             rm.block_loot(p, {'name': p, 'conditions': [loot_tables.match_tag('afc:sharp_tools'), lower_only]})
    #     else:
    #         rm.block_loot(p, {'name': p, 'conditions': [loot_tables.match_tag('afc:sharp_tools')]})
    # # todo this is a mess
    # for plant in ('hanging_vines', 'jungle_vines', 'ivy', 'liana', 'tree_fern', 'arundo', 'spanish_moss'):
    #     rm.lang('block.afc.plant.%s' % plant, lang(plant))
    # for plant in ('tree_fern', 'arundo', 'winged_kelp', 'leafy_kelp', 'giant_kelp_flower', 'dry_phragmite'):
    #     rm.lang('block.afc.plant.%s' % plant, lang(plant))
    #     rm.block_loot('afc:plant/%s' % plant, 'afc:plant/%s' % plant)
    # for plant in ('tree_fern', 'arundo', 'winged_kelp', 'leafy_kelp', 'giant_kelp', 'hanging_vines', 'spanish_moss', 'liana', 'dry_phragmite'):
    #     rm.lang('block.afc.plant.%s_plant' % plant, lang(plant))
    # for plant in ('hanging_vines', 'ivy', 'jungle_vines', 'liana', 'spanish_moss'):
    #     rm.block_loot('afc:plant/%s' % plant, {'name': 'afc:plant/%s' % plant, 'conditions': [loot_tables.match_tag('afc:sharp_tools')]})
    #
    # for plant, texture in FLOWERPOT_CROSS_PLANTS.items():
    #     plant_folder = plant
    #     if 'tulip' in plant:
    #         plant_folder = 'tulip'
    #     elif 'snapdragon' in plant:
    #         plant_folder = 'snapdragon'
    #     flower_pot_cross(rm, plant, 'afc:plant/potted/%s' % plant, 'plant/flowerpot/%s' % plant, 'afc:block/plant/%s/%s' % (plant_folder, texture), 'afc:plant/%s' % plant)
    # for plant in MISC_POTTED_PLANTS:
    #     rm.blockstate('plant/potted/%s' % plant, model='afc:block/plant/flowerpot/%s' % plant).with_lang(lang('potted %s', plant)).with_tag('minecraft:flower_pots').with_block_loot('afc:plant/%s' % plant, 'minecraft:flower_pot')
    #     for plant, stages in SIMPLE_TALL_PLANTS.items():
    #         for i in range(0, stages):
    #             for part in ('lower', 'upper'):
    #                 rm.block_model('plant/%s_%s_%s' % (plant, part, i), parent='minecraft:block/cross', textures={'cross': 'afc:block/plant/%s/%s_%s' % (plant, i, part)})
    #     rm.blockstate('plant/%s' % plant, variants=dict(('stage=%d,part=%s' % (i, part), {'model': 'afc:block/plant/%s_%s_%s' % (plant, part, i)}) for i in range(0, stages) for part in ('lower', 'upper')))
    # for plant, stages in SIMPLE_STAGE_PLANTS.items():
    #     rm.blockstate('plant/%s' % plant, variants=dict({'stage=%d' % i: {'model': 'afc:block/plant/%s_%s' % (plant, i)} for i in range(0, stages)}))
    # for plant in MODEL_PLANTS:
    #     rm.blockstate('plant/%s' % plant, model='afc:block/plant/%s' % plant)
    # for plant in SEAGRASS:
    #     rm.blockstate('plant/%s' % plant, variants=dict({'age=%s' % i: {'model': 'afc:block/plant/%s_%s' % (plant, i)} for i in range(0, 4)}))
    #     for i in range(0, 4):
    #         rm.block_model('plant/%s_%s' % (plant, i), parent='minecraft:block/template_seagrass', textures={'texture': 'afc:block/plant/%s/%s' % (plant, i)})
    # rm.blockstate('plant/dead_bush', variants={"": [{'model': 'afc:block/plant/dead_bush_large'}, *[{'model': 'afc:block/plant/dead_bush%s' % i} for i in range(0, 7)]]}, use_default_model=False)
    # for i in range(0, 7):
    #     rm.block_model('plant/dead_bush%s' % i, parent='minecraft:block/cross', textures={'cross': 'afc:block/plant/dead_bush/dead_bush%s' % i})
    #
    # rm.block('sea_pickle').with_lang(lang('sea pickle')).with_block_loot([{
    #     'name': 'afc:sea_pickle',
    #     'conditions': loot_tables.block_state_property('afc:sea_pickle[pickles=%d]' % i),
    #     'functions': [loot_tables.set_count(i)]
    # } for i in (1, 2, 3, 4)])
    #
    # for plant in ('duckweed', 'lotus', 'sargassum', 'water_lily'):
    #     rm.block_model('plant/%s' % plant, parent='afc:block/plant/template_floating_tinted', textures={'pad': 'afc:block/plant/%s/%s' % (plant, plant)})

    # Wood Blocks
    for wood in WOODS.keys():
        # Logs
        for variant in ('log', 'stripped_log', 'wood', 'stripped_wood'):
            block = rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': 'afc:block/wood/%s/%s' % (variant, wood)},
                'axis=z': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90},
                'axis=x': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90, 'y': 90}
            }, use_default_model=False)

            stick_with_hammer = {
                'name': 'minecraft:stick',
                'conditions': [loot_tables.match_tag('afc:hammers')],
                'functions': [loot_tables.set_count(1, 4)]
            }
            if variant == 'wood' or variant == 'stripped_wood':
                block.with_block_loot((
                    stick_with_hammer,
                    {  # wood blocks will only drop themselves if non-natural
                        'name': 'afc:wood/%s/%s' % (variant, wood),
                        'conditions': loot_tables.block_state_property('afc:wood/%s/%s[natural=false]' % (variant, wood))
                    },
                    'afc:wood/%s/%s' % (variant.replace('wood', 'log'), wood)
                ))
            else:
                block.with_block_loot((
                    stick_with_hammer,
                    'afc:wood/%s/%s' % (variant, wood)  # logs drop themselves always
                ))

            rm.item_model(('wood', variant, wood), 'afc:item/wood/%s/%s' % (variant, wood))

            end = 'afc:block/wood/%s/%s' % (variant.replace('log', 'log_top').replace('wood', 'log'), wood)
            side = 'afc:block/wood/%s/%s' % (variant.replace('wood', 'log'), wood)
            block.with_block_model({'end': end, 'side': side}, parent='block/cube_column')
            if 'stripped' in variant:
                block.with_lang(lang(variant.replace('_', ' ' + wood + ' ')))
            else:
                block.with_lang(lang('%s %s', wood, variant))
        for item_type in ('lumber', 'sign', 'chest_minecart', 'boat'):
            rm.item_model(('wood', item_type, wood)).with_lang(lang('%s %s', wood, item_type))
        rm.item_tag('minecraft:signs', 'afc:wood/sign/' + wood)
        rm.item_tag('afc:minecarts', 'afc:wood/chest_minecart/' + wood)

        # Groundcover
        for variant in ('twig', 'fallen_leaves'):
            block = rm.blockstate('wood/%s/%s' % (variant, wood), variants={"": four_ways('afc:block/wood/%s/%s' % (variant, wood))}, use_default_model=False)
            block.with_lang(lang('%s %s', wood, variant)).with_tag('afc:single_block_replaceable')

            if variant == 'twig':
                block.with_block_model({'side': 'afc:block/wood/log/%s' % wood, 'top': 'afc:block/wood/log_top/%s' % wood}, parent='tfc:block/groundcover/%s' % variant)
                rm.item_model('wood/%s/%s' % (variant, wood), 'afc:item/wood/twig/%s' % wood)
                block.with_block_loot('afc:wood/twig/%s' % wood)
            elif variant == 'fallen_leaves':
                block.with_block_model('afc:block/wood/leaves/%s' % wood, parent='tfc:block/groundcover/%s' % variant)
                rm.item_model('wood/%s/%s' % (variant, wood), 'afc:item/groundcover/fallen_leaves')
                block.with_block_loot('afc:wood/%s/%s' % (variant, wood))
            else:
                block.with_item_model()

            block.with_tag('can_be_snow_piled')

        # Leaves
        block = rm.blockstate(('wood', 'leaves', wood), model='afc:block/wood/leaves/%s' % wood)
        block.with_block_model('afc:block/wood/leaves/%s' % wood, parent='block/leaves')
        block.with_item_model()
        block.with_tag('minecraft:leaves')
        block.with_block_loot(({
            'name': 'afc:wood/leaves/%s' % wood,
            'conditions': [loot_tables.or_condition(loot_tables.match_tag('forge:shears'), loot_tables.silk_touch())]
        }, {
            'name': 'afc:wood/sapling/%s' % wood,
            'conditions': ['minecraft:survives_explosion', loot_tables.random_chance(TREE_SAPLING_DROP_CHANCES[wood])] #Delete this bit to run for now, will fix itself when you run Generate trees.py because it will calc the sapling drop chances
        }), ({
            'name': 'minecraft:stick',
            'conditions': [loot_tables.match_tag('afc:sharp_tools'), loot_tables.random_chance(0.2)],
            'functions': [loot_tables.set_count(1, 2)]
        }, {
            'name': 'minecraft:stick',
            'conditions': [loot_tables.random_chance(0.05)],
            'functions': [loot_tables.set_count(1, 2)]
        }))

        # Sapling
        block = rm.blockstate(('wood', 'sapling', wood), 'afc:block/wood/sapling/%s' % wood)
        block.with_block_model({'cross': 'afc:block/wood/sapling/%s' % wood}, 'block/cross')
        block.with_block_loot('afc:wood/sapling/%s' % wood)
        rm.item_model(('wood', 'sapling', wood), 'afc:block/wood/sapling/%s' % wood)

        flower_pot_cross(rm, '%s sapling' % wood, 'afc:wood/potted_sapling/%s' % wood, 'wood/potted_sapling/%s' % wood, 'afc:block/wood/sapling/%s' % wood, 'afc:wood/sapling/%s' % wood)

        # Planks and variant blocks
        block = rm.block(('wood', 'planks', wood))
        block.with_blockstate()
        block.with_block_model()
        block.with_item_model()
        block.with_block_loot('afc:wood/planks/%s' % wood)
        block.with_lang(lang('%s planks', wood))
        block.make_slab()
        block.make_stairs()
        block.make_button()
        block.make_door()
        block.make_pressure_plate()
        block.make_trapdoor()
        block.make_fence()
        block.make_fence_gate()

        for block_type in ('button', 'fence', 'fence_gate', 'pressure_plate', 'stairs', 'trapdoor'):
            rm.block_loot('wood/planks/%s_%s' % (wood, block_type), 'afc:wood/planks/%s_%s' % (wood, block_type))
        slab_loot(rm, 'afc:wood/planks/%s_slab' % wood)

        # Tool Rack
        rack_namespace = 'afc:wood/planks/%s_tool_rack' % wood
        block = rm.blockstate(rack_namespace, model='afc:block/wood/planks/%s_tool_rack' % wood, variants=four_rotations('afc:block/wood/planks/%s_tool_rack' % wood, (270, 180, None, 90)))
        block.with_block_model(textures={'texture': 'afc:block/wood/planks/%s' % wood, 'particle': 'afc:block/wood/planks/%s' % wood}, parent='tfc:block/tool_rack')
        block.with_lang(lang('%s Tool Rack', wood)).with_block_loot(rack_namespace).with_item_model()

        # Loom
        block = rm.blockstate('afc:wood/planks/%s_loom' % wood, model='afc:block/wood/planks/%s_loom' % wood, variants=four_rotations('afc:block/wood/planks/%s_loom' % wood, (270, 180, None, 90)))
        block.with_block_model(textures={'texture': 'afc:block/wood/planks/%s' % wood, 'particle': 'afc:block/wood/planks/%s' % wood}, parent='tfc:block/loom')
        block.with_item_model().with_lang(lang('%s loom', wood)).with_block_loot('afc:wood/planks/%s_loom' % wood).with_tag('minecraft:mineable/axe')

        # Bookshelf
        block = rm.blockstate('afc:wood/planks/%s_bookshelf' % wood, variants=dict(
            ('books_stored=%s,facing=%s' % (i, f), {'model': 'afc:block/wood/planks/%s_bookshelf_%s' % (wood, i), 'y': r})
            for i in range(0, 7) for f, r in (('east', 90), ('north', None), ('south', 180), ('west', 270))
        ), use_default_model=False)
        for i in range(0, 7):
            rm.block_model('afc:wood/planks/%s_bookshelf_%s' % (wood, i), parent='block/cube_column', textures={'north': 'afc:block/wood/planks/%s_bookshelf_stage%s' % (wood, i), 'side': 'afc:block/wood/planks/%s_bookshelf_side' % wood, 'end': 'afc:block/wood/planks/%s_bookshelf_top' % wood})
        block.with_lang(lang('%s bookshelf', wood)).with_block_loot('afc:wood/planks/%s_bookshelf' % wood)
        rm.item_model('afc:wood/planks/%s_bookshelf' % wood, parent='tfc:block/wood/planks/%s_bookshelf_0' % wood, no_textures=True)

        # Workbench
        rm.blockstate(('wood', 'planks', '%s_workbench' % wood)).with_block_model(parent='minecraft:block/cube', textures={
            'particle': 'afc:block/wood/planks/%s_workbench_front' % wood,
            'north': 'afc:block/wood/planks/%s_workbench_front' % wood,
            'south': 'afc:block/wood/planks/%s_workbench_side' % wood,
            'east': 'afc:block/wood/planks/%s_workbench_side' % wood,
            'west': 'afc:block/wood/planks/%s_workbench_front' % wood,
            'up': 'afc:block/wood/planks/%s_workbench_top' % wood,
            'down': 'afc:block/wood/planks/%s' % wood
        }).with_item_model().with_lang(lang('%s Workbench', wood)).with_tag('afc:workbenches').with_block_loot('afc:wood/planks/%s_workbench' % wood)

        # Doors
        rm.item_model('afc:wood/planks/%s_door' % wood, 'afc:item/wood/planks/%s_door' % wood)
        rm.block_loot('wood/planks/%s_door' % wood, {'name': 'afc:wood/planks/%s_door' % wood, 'conditions': [loot_tables.block_state_property('afc:wood/planks/%s_door[half=lower]' % wood)]})

        # Log Fences
        log_fence_namespace = 'afc:wood/planks/' + wood + '_log_fence'
        rm.blockstate_multipart(log_fence_namespace, *block_states.fence_multipart('afc:block/wood/planks/' + wood + '_log_fence_post', 'afc:block/wood/planks/' + wood + '_log_fence_side'))
        rm.block_model(log_fence_namespace + '_post', textures={'texture': 'afc:block/wood/log/' + wood}, parent='block/fence_post')
        rm.block_model(log_fence_namespace + '_side', textures={'texture': 'afc:block/wood/planks/' + wood}, parent='block/fence_side')
        rm.block_model(log_fence_namespace + '_inventory', textures={'log': 'afc:block/wood/log/' + wood, 'planks': 'afc:block/wood/planks/' + wood}, parent='tfc:block/log_fence_inventory')
        rm.item_model('afc:wood/planks/' + wood + '_log_fence', parent='tfc:block/wood/planks/' + wood + '_log_fence_inventory', no_textures=True)
        rm.block_loot(log_fence_namespace, log_fence_namespace)

        texture = 'afc:block/wood/sheet/%s' % wood
        connection = 'afc:block/wood/support/%s_connection' % wood
        rm.blockstate_multipart(('wood', 'vertical_support', wood),
            {'model': 'afc:block/wood/support/%s_vertical' % wood},
            ({'north': True}, {'model': connection, 'y': 270}),
            ({'east': True}, {'model': connection}),
            ({'south': True}, {'model': connection, 'y': 90}),
            ({'west': True}, {'model': connection, 'y': 180}),
        ).with_tag('afc:support_beam').with_lang(lang('%s Support', wood)).with_block_loot('afc:wood/support/' + wood)
        rm.blockstate_multipart(('wood', 'horizontal_support', wood),
            {'model': 'afc:block/wood/support/%s_horizontal' % wood},
            ({'north': True}, {'model': connection, 'y': 270}),
            ({'east': True}, {'model': connection}),
            ({'south': True}, {'model': connection, 'y': 90}),
            ({'west': True}, {'model': connection, 'y': 180}),
        ).with_tag('afc:support_beam').with_lang(lang('%s Support', wood)).with_block_loot('afc:wood/support/' + wood)

        rm.block_model('afc:wood/support/%s_inventory' % wood, textures={'texture': texture}, parent='tfc:block/wood/support/inventory')
        rm.block_model('afc:wood/support/%s_vertical' % wood, textures={'texture': texture, 'particle': texture}, parent='tfc:block/wood/support/vertical')
        rm.block_model('afc:wood/support/%s_connection' % wood, textures={'texture': texture, 'particle': texture}, parent='tfc:block/wood/support/connection')
        rm.block_model('afc:wood/support/%s_horizontal' % wood, textures={'texture': texture, 'particle': texture}, parent='tfc:block/wood/support/horizontal')
        rm.item_model(('wood', 'support', wood), no_textures=True, parent='tfc:block/wood/support/%s_inventory' % wood).with_lang(lang('%s Support', wood))

        for chest in ('chest', 'trapped_chest'):
            rm.blockstate(('wood', chest, wood), model='afc:block/wood/%s/%s' % (chest, wood)).with_lang(lang('%s %s', wood, chest)).with_tag('minecraft:features_cannot_replace').with_tag('minecraft:lava_pool_stone_cannot_replace')
            rm.block_model(('wood', chest, wood), textures={'particle': 'afc:block/wood/planks/%s' % wood}, parent=None)
            rm.item_model(('wood', chest, wood), {'particle': 'afc:block/wood/planks/%s' % wood}, parent='minecraft:item/chest')
            rm.block_loot(('wood', chest, wood), {'name': 'afc:wood/%s/%s'%(chest,wood)})

        rm.block_model('wood/sluice/%s_upper' % wood, textures={'texture': 'afc:block/wood/sheet/%s' % wood}, parent='tfc:block/sluice_upper')
        rm.block_model('wood/sluice/%s_lower' % wood, textures={'texture': 'afc:block/wood/sheet/%s' % wood}, parent='tfc:block/sluice_lower')
        block = rm.blockstate(('wood', 'sluice', wood), variants={**four_rotations('afc:block/wood/sluice/%s_upper' % wood, (90, 0, 180, 270), suffix=',upper=true'), **four_rotations('afc:block/wood/sluice/%s_lower' % wood, (90, 0, 180, 270), suffix=',upper=false')}).with_lang(lang('%s sluice', wood))
        block.with_block_loot({'name': 'afc:wood/sluice/%s' % wood, 'conditions': [loot_tables.block_state_property('afc:wood/sluice/%s[upper=true]' % wood)]})
        rm.item_model(('wood', 'sluice', wood), parent='tfc:block/wood/sluice/%s_lower' % wood, no_textures=True)

        rm.blockstate(('wood', 'planks', '%s_sign' % wood), model='afc:block/wood/planks/%s_sign' % wood).with_lang(lang('%s Sign', wood)).with_block_model({'particle': 'afc:block/wood/planks/%s' % wood}, parent=None).with_block_loot('afc:wood/sign/%s' % wood).with_tag('minecraft:standing_sings')
        rm.blockstate(('wood', 'planks', '%s_wall_sign' % wood), model='afc:block/wood/planks/%s_sign' % wood).with_lang(lang('%s Sign', wood)).with_lang(lang('%s Sign', wood)).with_tag('minecraft:wall_signs')

        # Barrels
        texture = 'afc:block/wood/planks/%s' % wood
        textures = {'particle': texture, 'planks': texture, 'sheet': 'afc:block/wood/sheet/%s' % wood, 'hoop': 'tfc:block/barrel_hoop'}
        block = rm.blockstate(('wood', 'barrel', wood), variants={
            'sealed=true': {'model': 'afc:block/wood/barrel_sealed/%s' % wood},
            'sealed=false': {'model': 'afc:block/wood/barrel/%s' % wood}
        })
        item_model_property(rm, ('wood', 'barrel', wood), [{'predicate': {'tfc:sealed': 1.0}, 'model': 'tfc:block/wood/barrel_sealed/%s' % wood}], {'parent': 'afc:block/wood/barrel/%s' % wood})
        block.with_block_model(textures, 'tfc:block/barrel')
        rm.block_model(('wood', 'barrel_sealed', wood), textures, 'tfc:block/barrel_sealed')
        block.with_lang(lang('%s barrel', wood))
        block.with_tag('tfc:barrels').with_tag('minecraft:mineable/axe')
        block.with_block_loot(({
            'name': 'afc:wood/barrel/%s' % wood,
            'functions': [loot_tables.copy_block_entity_name(), loot_tables.copy_block_entity_nbt()],
            'conditions': [loot_tables.block_state_property('afc:wood/barrel/%s[sealed=true]' % wood)]
        }, 'afc:wood/barrel/%s' % wood))

        # Lecterns
        block = rm.blockstate('afc:wood/lectern/%s' % wood, variants=four_rotations('afc:block/wood/lectern/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'bottom': 'afc:block/wood/planks/%s' % wood, 'base': 'afc:block/wood/lectern/%s/base' % wood, 'front': 'afc:block/wood/lectern/%s/front' % wood, 'sides': 'afc:block/wood/lectern/%s/sides' % wood, 'top': 'afc:block/wood/lectern/%s/top' % wood, 'particle': 'afc:block/wood/lectern/%s/sides' % wood}, parent='minecraft:block/lectern')
        block.with_item_model().with_lang(lang("%s lectern" % wood)).with_block_loot('afc:wood/lectern/%s' % wood).with_tag('minecraft:mineable/axe')
        # Scribing Table
        block = rm.blockstate('afc:wood/scribing_table/%s' % wood, variants=four_rotations('afc:block/wood/scribing_table/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'top': 'afc:block/wood/scribing_table/%s' % wood, 'leg': 'afc:block/wood/log/%s' % wood, 'side' : 'afc:block/wood/planks/%s' % wood, 'misc': 'afc:block/wood/scribing_table/scribing_paraphernalia', 'particle': 'afc:block/wood/planks/%s' % wood}, parent='tfc:block/scribing_table')
        block.with_item_model().with_lang(lang("%s scribing table" % wood)).with_block_loot('afc:wood/scribing_table/%s' % wood).with_tag('minecraft:mineable/axe')

        # Lang
        for variant in ('door', 'trapdoor', 'fence', 'log_fence', 'fence_gate', 'button', 'pressure_plate', 'slab', 'stairs'):
            rm.lang('block.afc.wood.planks.' + wood + '_' + variant, lang('%s %s', wood, variant))
        for variant in ('sapling', 'leaves'):
            rm.lang('block.afc.wood.' + variant + '.' + wood, lang('%s %s', wood, variant))

    rm.blockstate('light', variants={'level=%s' % i: {'model': 'minecraft:block/light_%s' % i if i >= 10 else 'minecraft:block/light_0%s' % i} for i in range(0, 15 + 1)}).with_lang(lang('Light'))
    rm.item_model('light', no_textures=True, parent='minecraft:item/light')



def flower_pot_cross(rm: ResourceManager, simple_name: str, name: str, model: str, texture: str, loot: str):
    rm.blockstate(name, model='afc:block/%s' % model).with_lang(lang('potted %s', simple_name)).with_tag('minecraft:flower_pots').with_block_loot(loot, 'minecraft:flower_pot')
    rm.block_model(model, parent='minecraft:block/flower_pot_cross', textures={'plant': texture, 'dirt': 'afc:block/dirt/loam'})

def item_model_property(rm: ResourceManager, name_parts: utils.ResourceIdentifier, overrides: utils.Json, data: Dict[str, Any]) -> ItemContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'assets', res.domain, 'models', 'item', res.path), {
        **data,
        'overrides': overrides
    })
    return ItemContext(rm, res)


def four_ways(model: str) -> List[Dict[str, Any]]:
    return [
        {'model': model, 'y': 90},
        {'model': model},
        {'model': model, 'y': 180},
        {'model': model, 'y': 270}
    ]


def four_rotations(model: str, rots: Tuple[Any, Any, Any, Any], suffix: str = '', prefix: str = '') -> Dict[str, Dict[str, Any]]:
    return {
        '%sfacing=east%s' % (prefix, suffix): {'model': model, 'y': rots[0]},
        '%sfacing=north%s' % (prefix, suffix): {'model': model, 'y': rots[1]},
        '%sfacing=south%s' % (prefix, suffix): {'model': model, 'y': rots[2]},
        '%sfacing=west%s' % (prefix, suffix): {'model': model, 'y': rots[3]}
    }


def crop_yield(lo: int, hi: Tuple[int, int]) -> utils.Json:
    return {
        'function': 'minecraft:set_count',
        'count': {
            'type': 'afc:crop_yield_uniform',
            'min': lo,
            'max': {
                'type': 'minecraft:uniform',
                'min': hi[0],
                'max': hi[1]
            }
        }
    }


def make_javelin(rm: ResourceManager, name_parts: str, texture: str) -> 'ItemContext':
    rm.item_model(name_parts + '_throwing', {'particle': texture}, parent='minecraft:item/trident_throwing')
    rm.item_model(name_parts + '_in_hand', {'particle': texture}, parent='minecraft:item/trident_in_hand')
    rm.item_model(name_parts + '_gui', texture)
    model = rm.domain + ':item/' + name_parts
    # todo: 1.19 rename to forge:separate_transforms due to deprecation
    return rm.custom_item_model(name_parts, 'forge:separate-perspective', {
        'gui_light': 'front',
        'overrides': [{'predicate': {'afc:throwing': 1}, 'model': model + '_throwing'}],
        'base': {'parent': model + '_in_hand'},
        'perspectives': {
            'none': {'parent': model + '_gui'},
            'fixed': {'parent': model + '_gui'},
            'ground': {'parent': model + '_gui'},
            'gui': {'parent': model + '_gui'}
        }
    })


def slab_loot(rm: ResourceManager, loot: str):
    return rm.block_loot(loot, {
        'name': loot,
        'functions': [{
            'function': 'minecraft:set_count',
            'conditions': [loot_tables.block_state_property(loot + '[type=double]')],
            'count': 2,
            'add': False
        }]
    })
