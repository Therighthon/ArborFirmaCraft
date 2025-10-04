#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

import itertools
import os

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables, BlockContext, atlases
from mcresources.type_definitions import JsonObject

from constants import *


def generate(rm: ResourceManager):

    for variant in TREE_VARIANTS.keys():
        # Leaves
        block = rm.blockstate(('wood', 'leaves', variant), model='afc:block/wood/leaves/%s' % variant)
        block.with_block_model('afc:block/wood/leaves/%s' % variant, parent='block/leaves')
        block.with_item_model()
        block.with_tag('minecraft:leaves')
        block.with_block_loot(({
                                   'name': 'afc:wood/leaves/%s' % variant,
                                   'conditions': [loot_tables.any_of(loot_tables.match_tag('forge:shears'), loot_tables.silk_touch())]
                               }, {
                                   'name': 'afc:wood/sapling/%s' % variant,
                                   'conditions': ['minecraft:survives_explosion', loot_tables.random_chance(TREE_SAPLING_DROP_CHANCES[variant])] #Delete this bit to run for now, will fix itself when you run Generate trees.py because it will calc the sapling drop chances
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
        block = rm.blockstate(('wood', 'sapling', variant), 'afc:block/wood/sapling/%s' % variant)
        block.with_block_model({'cross': 'afc:block/wood/sapling/%s' % variant}, 'block/cross')
        block.with_block_loot('afc:wood/sapling/%s' % variant)
        rm.item_model(('wood', 'sapling', variant), 'afc:block/wood/sapling/%s' % variant)

        flower_pot_cross(rm, '%s sapling' % variant, 'afc:wood/potted_sapling/%s' % variant, 'wood/potted_sapling/%s' % variant, 'afc:block/wood/sapling/%s' % variant, 'afc:wood/sapling/%s' % variant)

        # Fallen Leaves
        block = rm.blockstate(('wood', 'fallen_leaves', variant), variants=dict((('layers=%d' % i), {'model': 'afc:block/wood/fallen_leaves/%s_height%d' % (variant, i * 2) if i != 8 else 'afc:block/wood/leaves/%s' % variant}) for i in range(1, 1 + 8))).with_lang(lang('fallen %s leaves', variant))
        tex = {'all': 'afc:block/wood/leaves/%s' % variant}
        # Left in but unused rn
        if variant in ('mangrove', 'willow'):
            tex['top'] = 'afc:block/wood/leaves/%s_top' % variant
        for i in range(1, 8):
            rm.block_model(('wood', 'fallen_leaves', '%s_height%s' % (variant, i * 2)), tex, parent='tfc:block/groundcover/fallen_leaves_height%s' % (i * 2))
        rm.item_model(('wood', 'fallen_leaves', variant), 'tfc:item/groundcover/fallen_leaves')
        block.with_block_loot(*[{'name': 'afc:wood/fallen_leaves/%s' % variant, 'conditions': [loot_tables.block_state_property('afc:wood/fallen_leaves/%s[layers=%s]' % (variant, i))], 'functions': [loot_tables.set_count(i)]} for i in range(1, 9)])




        block.with_tag('can_be_snow_piled')

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
                'conditions': [loot_tables.match_tag('tfc:hammers')],
                'functions': [loot_tables.set_count(1, 4)]
            }
            if variant == 'wood' or variant == 'stripped_wood':
                block.with_block_loot((
                    stick_with_hammer,
                    {  # wood blocks will only drop themselves if non-natural (aka branch_direction=none)
                        'name': 'afc:wood/%s/%s' % (variant, wood),
                        'conditions': loot_tables.block_state_property('afc:wood/%s/%s[branch_direction=none]' % (variant, wood))
                    },
                    'afc:wood/%s/%s' % (variant.replace('wood', 'log'), wood)
                ))
            else:
                block.with_block_loot((
                    stick_with_hammer,
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

        # Commented out because they will generate into the wrong directories, needs to be rewritten to write to the override pack
        # block = rm.blockstate('afc:wood/food_shelf/%s' % wood, variants=four_rotations('afc:block/wood/food_shelf/%s_dynamic' % wood, (270, 180, None, 90))).with_tag('food_shelves').with_item_tag('food_shelves')
        # block.with_block_loot('afc:wood/food_shelf/%s' % wood).with_lang(lang('%s food shelf', wood)).with_tag('minecraft:mineable/axe')
        # rm.item_model('afc:wood/food_shelf/%s' % wood, parent='afc:block/wood/food_shelf/%s' % wood, no_textures=True)
        # rm.custom_block_model('afc:wood/food_shelf/%s_dynamic' % wood, 'firmalife:food_shelf', {'base': {'parent': 'afc:block/wood/food_shelf/%s' % wood}})
        # rm.block_model('afc:wood/food_shelf/%s' % wood, parent='firmalife:block/food_shelf_base', textures={'wood': 'afc:block/wood/planks/%s' % wood})
        #
        # block = rm.blockstate('afc:wood/hanger/%s' % wood, model='afc:block/wood/hanger/%s_dynamic' % wood).with_tag('hangers').with_item_tag('hangers')
        # block.with_block_loot('afc:wood/hanger/%s' % wood).with_lang(lang('%s hanger' % wood)).with_tag('minecraft:mineable/axe')
        # rm.custom_block_model('afc:wood/hanger/%s_dynamic' % wood, 'firmalife:hanger', {'base': {'parent': 'afc:block/wood/hanger/%s' % wood}})
        # rm.item_model('afc:wood/hanger/%s' % wood, parent='afc:block/wood/hanger/%s' % wood, no_textures=True)
        # rm.block_model('afc:wood/hanger/%s' % wood, parent='firmalife:block/hanger_base', textures={'wood': 'afc:block/wood/planks/%s' % wood, 'string': 'minecraft:block/white_wool'})
        #
        # block = rm.blockstate('afc:wood/jarbnet/%s' % wood, variants={
        #     **four_rotations('afc:block/wood/jarbnet/%s_dynamic' % wood, (90, None, 180, 270), suffix=',open=true'),
        #     **four_rotations('afc:block/wood/jarbnet/%s_shut_dynamic' % wood, (90, None, 180, 270), suffix=',open=false'),
        # })
        # block.with_block_loot('afc:wood/jarbnet/%s' % wood).with_lang(lang('%s jarbnet', wood)).with_tag('minecraft:mineable/axe').with_tag('jarbnets').with_item_tag('jarbnets')
        # rm.item_model('afc:wood/jarbnet/%s' % wood, parent='afc:block/wood/jarbnet/%s' % wood, no_textures=True)
        # textures = {'planks': 'afc:block/wood/planks/%s' % wood, 'sheet': 'afc:block/wood/sheet/%s' % wood, 'log': 'afc:block/wood/log/%s' % wood}
        # rm.block_model('afc:wood/jarbnet/%s' % wood, parent='firmalife:block/jarbnet', textures=textures)
        # rm.block_model('afc:wood/jarbnet/%s_shut' % wood, parent='firmalife:block/jarbnet_shut', textures=textures)
        # rm.custom_block_model('afc:wood/jarbnet/%s_dynamic' % wood, 'firmalife:jarbnet', {'base': {'parent': 'afc:block/wood/jarbnet/%s' % wood}})
        # rm.custom_block_model('afc:wood/jarbnet/%s_shut_dynamic' % wood, 'firmalife:jarbnet', {'base': {'parent': 'afc:block/wood/jarbnet/%s_shut' % wood}})


        # Groundcover
        block = rm.blockstate(('wood', 'twig', wood), variants={"": four_ways('afc:block/wood/twig/%s' % wood)}, use_default_model=False)
        block.with_lang(lang('%s twig', wood))

        block.with_block_model({'side': 'afc:block/wood/log/%s' % wood, 'top': 'afc:block/wood/log_top/%s' % wood}, parent='tfc:block/groundcover/twig')
        rm.item_model('wood/twig/%s' % wood, 'afc:item/wood/twig/%s' % wood, parent='item/handheld_rod')
        block.with_block_loot('afc:wood/twig/%s' % wood)

        block = rm.blockstate(('wood', 'fallen_leaves', wood), variants=dict((('layers=%d' % i), {'model': 'afc:block/wood/fallen_leaves/%s_height%d' % (wood, i * 2) if i != 8 else 'afc:block/wood/leaves/%s' % wood}) for i in range(1, 1 + 8))).with_lang(lang('fallen %s leaves', wood))
        tex = {'all': 'afc:block/wood/leaves/%s' % wood}
        #Leaving this in in case we want to use it for other stuff
        if wood in ('mangrove', 'willow'):
            tex['top'] = 'afc:block/wood/leaves/%s_top' % wood
        for i in range(1, 8):
            rm.block_model(('wood', 'fallen_leaves', '%s_height%s' % (wood, i * 2)), tex, parent='tfc:block/groundcover/fallen_leaves_height%s' % (i * 2))
        rm.item_model(('wood', 'fallen_leaves', wood), 'tfc:item/groundcover/fallen_leaves')
        block.with_block_loot(*[{'name': 'afc:wood/fallen_leaves/%s' % wood, 'conditions': [loot_tables.block_state_property('afc:wood/fallen_leaves/%s[layers=%s]' % (wood, i))], 'functions': [loot_tables.set_count(i)]} for i in range(1, 9)])


        # Leaves
        block = rm.blockstate(('wood', 'leaves', wood), model='afc:block/wood/leaves/%s' % wood)
        block.with_block_model('afc:block/wood/leaves/%s' % wood, parent='block/leaves')
        block.with_item_model()
        block.with_item_model()
        block.with_tag('minecraft:leaves')
        block.with_block_loot(({
            'name': 'afc:wood/leaves/%s' % wood,
            'conditions': [loot_tables.any_of(loot_tables.match_tag('forge:shears'), loot_tables.silk_touch())]
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
        make_door(block)
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
        slot_types = (('top_right', 2), ('bottom_mid', 4), ('top_left', 0), ('bottom_right', 5), ('bottom_left', 3), ('top_mid', 1))
        faces = (('east', 90), ('north', None), ('west', 270), ('south', 180))
        occupations = (('empty', 'false'), ('occupied', 'true'))
        shelf_mp = []
        shelf_mp += [({'facing': face}, {'model': 'afc:block/wood/planks/%s_bookshelf' % wood, 'y': y, 'uvlock': True}) for face, y in faces]
        shelf_mp += [({'AND': [{'facing': face}, {f'slot_{i}_occupied': is_occupied}]}, {'model': f'afc:block/wood/planks/{wood}_bookshelf_{occupation}_{slot_type}', 'y': y}) for face, y in faces for slot_type, i in slot_types for occupation, is_occupied in occupations]
        block = rm.blockstate_multipart(('wood', 'planks', '%s_bookshelf' % wood), *shelf_mp)
        rm.block_model(('wood', 'planks', '%s_bookshelf' % wood), {'top': 'afc:block/wood/planks/%s_bookshelf_top' % wood, 'side': 'afc:block/wood/planks/%s_bookshelf_side' % wood}, parent='minecraft:block/chiseled_bookshelf')
        block.with_lang(lang('%s bookshelf', wood)).with_block_loot('afc:wood/planks/%s_bookshelf' % wood)
        rm.block_model(('wood', 'planks', '%s_bookshelf_inventory' % wood), {'top': 'afc:block/wood/planks/%s_bookshelf_top' % wood, 'side': 'afc:block/wood/planks/%s_bookshelf_side' % wood, 'front': 'afc:block/wood/planks/%s_bookshelf_empty' % wood}, parent='minecraft:block/chiseled_bookshelf_inventory')
        rm.item_model('afc:wood/planks/%s_bookshelf' % wood, parent='afc:block/wood/planks/%s_bookshelf_inventory' % wood, no_textures=True)
        for slot in ('bottom_left', 'bottom_mid', 'bottom_right', 'top_left', 'top_mid', 'top_right'):
            for occupancy in ('empty', 'occupied'):
                rm.block_model(('wood', 'planks', f'{wood}_bookshelf_{occupancy}_{slot}'), {'texture': f'afc:block/wood/planks/{wood}_bookshelf_{occupancy}'}, parent=f'minecraft:block/chiseled_bookshelf_{occupancy}_slot_{slot}')

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
        rm.item_model('afc:wood/planks/' + wood + '_log_fence', parent='afc:block/wood/planks/' + wood + '_log_fence_inventory', no_textures=True)
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
        rm.item_model(('wood', 'support', wood), no_textures=True, parent='afc:block/wood/support/%s_inventory' % wood).with_lang(lang('%s Support', wood))

        for chest in ('chest', 'trapped_chest'):
            rm.blockstate(('wood', chest, wood), model='afc:block/wood/%s/%s' % (chest, wood)).with_lang(lang('%s %s', wood, chest)).with_tag('minecraft:features_cannot_replace').with_tag('minecraft:lava_pool_stone_cannot_replace')
            rm.block_model(('wood', chest, wood), textures={'particle': 'afc:block/wood/planks/%s' % wood}, parent=None)
            rm.item_model(('wood', chest, wood), {'particle': 'afc:block/wood/planks/%s' % wood}, parent='minecraft:item/chest')
            rm.block_loot(('wood', chest, wood), {'name': 'afc:wood/%s/%s'%(chest,wood)})

        rm.block_model('wood/sluice/%s_upper' % wood, textures={'texture': 'afc:block/wood/sheet/%s' % wood}, parent='tfc:block/sluice_upper')
        rm.block_model('wood/sluice/%s_lower' % wood, textures={'texture': 'afc:block/wood/sheet/%s' % wood}, parent='tfc:block/sluice_lower')
        block = rm.blockstate(('wood', 'sluice', wood), variants={**four_rotations('afc:block/wood/sluice/%s_upper' % wood, (90, 0, 180, 270), suffix=',upper=true'), **four_rotations('afc:block/wood/sluice/%s_lower' % wood, (90, 0, 180, 270), suffix=',upper=false')}).with_lang(lang('%s sluice', wood))
        block.with_block_loot({'name': 'afc:wood/sluice/%s' % wood, 'conditions': [loot_tables.block_state_property('afc:wood/sluice/%s[upper=true]' % wood)]})
        rm.item_model(('wood', 'sluice', wood), parent='afc:block/wood/sluice/%s_lower' % wood, no_textures=True)

        rm.blockstate(('wood', 'planks', '%s_sign' % wood), model='afc:block/wood/planks/%s_sign' % wood).with_lang(lang('%s Sign', wood)).with_block_model({'particle': 'afc:block/wood/planks/%s' % wood}, parent=None).with_block_loot('afc:wood/sign/%s' % wood).with_tag('minecraft:standing_sings')
        rm.blockstate(('wood', 'planks', '%s_wall_sign' % wood), model='afc:block/wood/planks/%s_sign' % wood).with_lang(lang('%s Sign', wood)).with_lang(lang('%s Sign', wood)).with_tag('minecraft:wall_signs')
        for metal, metal_data in METALS.items():
            if 'utility' in metal_data.types:
                for variant in ('hanging_sign', 'wall_hanging_sign'):
                    rm.blockstate(('wood', 'planks', variant, metal, wood), model='afc:block/wood/planks/%s_sign_particle' % wood).with_lang(lang('%s %s %s', metal, wood, variant)).with_block_loot('afc:wood/hanging_sign/%s/%s' % (metal, wood))
        for metal, metal_data in METALS.items():
            if 'utility' in metal_data.types:
                rm.item_model(('wood', 'hanging_sign', metal, wood), 'afc:item/wood/hanging_sign/head_%s' % wood, 'tfc:item/wood/hanging_sign_head_overlay%s' % ('_white' if wood in ('mahogany', 'cypress') else ''), 'tfc:item/metal/hanging_sign/%s' % metal).with_lang(lang('%s %s hanging sign', metal, wood))

        # Barrels
        texture = 'afc:block/wood/planks/%s' % wood
        textures = {'particle': texture, 'planks': texture, 'sheet': 'afc:block/wood/sheet/%s' % wood}

        faces = (('up', 0), ('east', 0), ('west', 180), ('south', 90), ('north', 270))
        seals = (('true', 'barrel_sealed'), ('false', 'barrel'))
        racks = (('true', '_rack'), ('false', ''))
        block = rm.blockstate(('wood', 'barrel', wood), variants=dict((
                                                                          'facing=%s,rack=%s,sealed=%s' % (face, rack, is_seal), {'model': 'afc:block/wood/%s/%s%s%s' % (seal_type, wood, '_side' if face != 'up' else '', suffix if face != 'up' else ''), 'y': yrot if yrot != 0 else None}
                                                                      ) for face, yrot in faces for rack, suffix in racks for is_seal, seal_type in seals))

        item_model_property(rm, ('wood', 'barrel', wood), [{'predicate': {'tfc:sealed': 1.0}, 'model': 'afc:block/wood/barrel_sealed/%s' % wood}], {'parent': 'afc:block/wood/barrel/%s' % wood})
        block.with_block_model(textures, 'tfc:block/barrel')
        rm.block_model(('wood', 'barrel', wood + '_side'), textures, 'tfc:block/barrel_side')
        rm.block_model(('wood', 'barrel', wood + '_side_rack'), textures, 'tfc:block/barrel_side_rack')
        rm.block_model(('wood', 'barrel_sealed', wood + '_side_rack'), textures, 'tfc:block/barrel_side_sealed_rack')
        rm.block_model(('wood', 'barrel_sealed', wood), textures, 'tfc:block/barrel_sealed')
        rm.block_model(('wood', 'barrel_sealed', wood + '_side'), textures, 'tfc:block/barrel_side_sealed')
        block.with_lang(lang('%s barrel', wood))
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
        block.with_block_model(textures={'top': 'afc:block/wood/scribing_table/%s' % wood, 'leg': 'afc:block/wood/log/%s' % wood, 'side' : 'afc:block/wood/planks/%s' % wood, 'misc': 'tfc:block/wood/scribing_table/scribing_paraphernalia', 'particle': 'afc:block/wood/planks/%s' % wood}, parent='tfc:block/scribing_table')
        block.with_item_model().with_lang(lang("%s scribing table" % wood)).with_block_loot('afc:wood/scribing_table/%s' % wood).with_tag('minecraft:mineable/axe')
        # Sewing Table
        block = rm.blockstate('wood/sewing_table/%s' % wood, variants=four_rotations('afc:block/wood/sewing_table/%s' % wood, (90, None, 180, 270))).with_item_model()
        rm.block_model(('wood', 'sewing_table', wood), {'0': 'afc:block/wood/log/%s' % wood, '1': 'afc:block/wood/planks/%s' % wood}, 'tfc:block/sewing_table')
        block.with_lang(lang('%s sewing table', wood)).with_block_loot('afc:wood/sewing_table/%s' % wood)

        # Jar shelf
        block = rm.blockstate('wood/jar_shelf/%s' % wood, variants=four_rotations('afc:block/wood/jar_shelf/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'0': 'afc:block/wood/planks/%s' % wood}, parent='tfc:block/jar_shelf').with_item_model().with_lang(lang('%s jar shelf', wood)).with_block_loot('afc:wood/jar_shelf/%s' % wood)

        # Axle
        block = rm.blockstate('afc:wood/axle/%s' % wood, 'tfc:block/empty')
        block.with_lang(lang('%s axle', wood))
        block.with_block_loot('afc:wood/axle/%s' % wood)
        block.with_block_model({'wood': 'afc:block/wood/sheet/%s' % wood}, 'tfc:block/axle')
        rm.item_model('afc:wood/axle/%s' % wood, no_textures=True, parent='afc:block/wood/axle/%s' % wood)

        # Bladed Axle
        block = rm.blockstate('afc:wood/bladed_axle/%s' % wood, 'tfc:block/empty')
        block.with_lang(lang('%s bladed axle', wood))
        block.with_block_loot('afc:wood/bladed_axle/%s' % wood)
        block.with_block_model({'wood': 'afc:block/wood/sheet/%s' % wood}, 'tfc:block/bladed_axle')
        rm.item_model('afc:wood/bladed_axle/%s' % wood, no_textures=True, parent='afc:block/wood/bladed_axle/%s' % wood)

        # Encased Axle
        block = rm.blockstate(('wood', 'encased_axle', wood), variants={
            'axis=x': {'model': 'afc:block/wood/encased_axle/%s' % wood, 'x': 90, 'y': 90},
            'axis=y': {'model': 'afc:block/wood/encased_axle/%s' % wood},
            'axis=z': {'model': 'afc:block/wood/encased_axle/%s' % wood, 'x': 90},
        })
        block.with_lang(lang('%s encased axle', wood))
        block.with_block_loot('afc:wood/encased_axle/%s' % wood)
        block.with_block_model({
            'side': 'afc:block/wood/stripped_log/%s' % wood,
            'end': 'afc:block/wood/planks/%s' % wood,
            'overlay': 'tfc:block/axle_casing',
            'overlay_end': 'tfc:block/axle_casing_front',
            'particle': 'afc:block/wood/stripped_log/%s' % wood
        }, parent='tfc:block/ore_column')
        block.with_item_model()

        # Clutch
        block = rm.blockstate(('wood', 'clutch', wood), variants={
            'axis=x,powered=false': {'model': 'afc:block/wood/clutch/%s' % wood, 'x': 90, 'y': 90},
            'axis=x,powered=true': {'model': 'afc:block/wood/clutch/%s_powered' % wood, 'x': 90, 'y': 90},
            'axis=y,powered=false': {'model': 'afc:block/wood/clutch/%s' % wood},
            'axis=y,powered=true': {'model': 'afc:block/wood/clutch/%s_powered' % wood},
            'axis=z,powered=false': {'model': 'afc:block/wood/clutch/%s' % wood, 'x': 90},
            'axis=z,powered=true': {'model': 'afc:block/wood/clutch/%s_powered' % wood, 'x': 90},
        })
        block.with_lang(lang('%s clutch', wood))
        block.with_block_loot('afc:wood/clutch/%s' % wood)
        block.with_block_model({
            'side': 'afc:block/wood/stripped_log/%s' % wood,
            'end': 'afc:block/wood/planks/%s' % wood,
            'overlay': 'tfc:block/axle_casing_unpowered',
            'overlay_end': 'tfc:block/axle_casing_front',
            'particle': 'afc:block/wood/stripped_log/%s' % wood
        }, parent='tfc:block/ore_column')
        rm.block_model(('wood', 'clutch', '%s_powered' % wood), {
            'side': 'afc:block/wood/stripped_log/%s' % wood,
            'end': 'afc:block/wood/planks/%s' % wood,
            'overlay': 'tfc:block/axle_casing_powered',
            'overlay_end': 'tfc:block/axle_casing_front',
            'particle': 'afc:block/wood/stripped_log/%s' % wood
        }, parent='tfc:block/ore_column')
        block.with_item_model()

        # Gearbox
        gearbox_port = 'afc:block/wood/gear_box_port/%s' % wood
        gearbox_face = 'afc:block/wood/gear_box_face/%s' % wood

        block = rm.blockstate_multipart(
            ('wood', 'gear_box', wood),
            ({'north': True}, {'model': gearbox_port}),
            ({'north': False}, {'model': gearbox_face}),
            ({'south': True}, {'model': gearbox_port, 'y': 180}),
            ({'south': False}, {'model': gearbox_face, 'y': 180}),
            ({'east': True}, {'model': gearbox_port, 'y': 90}),
            ({'east': False}, {'model': gearbox_face, 'y': 90}),
            ({'west': True}, {'model': gearbox_port, 'y': 270}),
            ({'west': False}, {'model': gearbox_face, 'y': 270}),
            ({'down': True}, {'model': gearbox_port, 'x': 90}),
            ({'down': False}, {'model': gearbox_face, 'x': 90}),
            ({'up': True}, {'model': gearbox_port, 'x': 270}),
            ({'up': False}, {'model': gearbox_face, 'x': 270}),
        )
        block.with_lang(lang('%s gear box', wood))
        block.with_block_loot('afc:wood/gear_box/%s' % wood)

        rm.block_model(('wood', 'gear_box_port', wood), {
            'all': 'afc:block/wood/planks/%s' % wood,
            'overlay': 'tfc:block/axle_casing_front',
        }, parent='tfc:block/gear_box_port')
        rm.block_model(('wood', 'gear_box_face', wood), {
            'all': 'afc:block/wood/planks/%s' % wood,
            'overlay': 'tfc:block/axle_casing_round'
        }, parent='tfc:block/gear_box_face')

        rm.item_model(('wood', 'gear_box', wood), {
            'all': 'afc:block/wood/planks/%s' % wood,
            'overlay': 'tfc:block/axle_casing_front'
        }, parent='tfc:block/ore')

        # Windmill
        block = rm.blockstate('afc:wood/windmill/%s' % wood, 'tfc:block/empty')
        block.with_lang(lang('%s windmill', wood))
        block.with_block_loot('afc:wood/axle/%s' % wood,)

        # Water Wheel
        block = rm.blockstate('afc:wood/water_wheel/%s' % wood)
        block.with_block_model({'particle': 'afc:block/wood/planks/%s' % wood}, parent=None)
        block.with_lang(lang('%s water wheel', wood))
        block.with_block_loot('afc:wood/water_wheel/%s' % wood)
        rm.item_model('afc:wood/water_wheel/%s' % wood, 'afc:item/wood/water_wheel/%s' % wood)


        # Lang
        for variant in ('door', 'trapdoor', 'fence', 'log_fence', 'fence_gate', 'button', 'pressure_plate', 'slab', 'stairs'):
            rm.lang('block.afc.wood.planks.' + wood + '_' + variant, lang('%s %s', wood, variant))
        for variant in ('sapling', 'leaves'):
            rm.lang('block.afc.wood.' + variant + '.' + wood, lang('%s %s', wood, variant))


    for wood in UNIQUE_LOGS.keys():
        # Twigs
        block = rm.blockstate(('wood', 'twig', wood), variants={"": four_ways('afc:block/wood/twig/%s' % wood)}, use_default_model=False)
        block.with_lang(lang('%s twig', wood))
        prefix = 'afc'
        wood_or_fig = wood
        if wood == 'rainbow_eucalyptus':
            wood_top = 'eucalyptus'
        elif wood == 'black_oak':
            wood_top = 'oak'
            prefix = 'tfc'
        elif wood == 'poplar':
            wood_top = 'aspen'
            prefix = 'tfc'
        elif wood == 'redcedar':
            wood_top = 'cypress'
        elif wood == 'rubber_fig':
            wood_top = 'fig'
            wood_or_fig = 'fig'
        elif wood == 'gum_arabic':
            wood_top = 'acacia'
            prefix = 'tfc'
        else:
            wood_top = wood
        block.with_block_model({'side': 'afc:block/wood/log/%s' % wood_or_fig, 'top': '%s:block/wood/log_top/%s' % (prefix, wood_top)}, parent='tfc:block/groundcover/twig')
        rm.item_model('wood/twig/%s' % wood, 'afc:item/wood/twig/%s' % wood, parent='item/handheld_rod')
        block.with_block_loot('afc:wood/twig/%s' % wood)

        for variant in ('log', 'wood'):
            block = rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': 'afc:block/wood/%s/%s' % (variant, wood)},
                'axis=z': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90},
                'axis=x': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90, 'y': 90}
            }, use_default_model=False)

            stick_with_hammer = {
                'name': 'minecraft:stick',
                'conditions': [loot_tables.match_tag('tfc:hammers')],
                'functions': [loot_tables.set_count(1, 4)]
            }
            if variant == 'wood':
                block.with_block_loot((
                    stick_with_hammer,
                    {  # wood blocks will only drop themselves if non-natural (aka branch_direction=none)
                        'name': 'afc:wood/%s/%s' % (variant, wood),
                        'conditions': loot_tables.block_state_property('afc:wood/%s/%s[branch_direction=none]' % (variant, wood))
                    },
                    'afc:wood/%s/%s' % (variant.replace('wood', 'log'), wood)
                ))
            else:
                block.with_block_loot((
                    stick_with_hammer,
                    'afc:wood/%s/%s' % (variant, wood)  # logs drop themselves always
                ))

    for wood in ANCIENT_LOGS.keys():
        base_wood = wood.replace('ancient_', '')
        if base_wood in TFC_WOODS.keys():
            mod_id = 'tfc'
        else:
            mod_id = 'afc'
        for variant in ('log', 'wood'):
            block = rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': '%s:block/wood/%s/%s' % (mod_id, variant, base_wood)},
                'axis=z': {'model': '%s:block/wood/%s/%s' % (mod_id, variant, base_wood), 'x': 90},
                'axis=x': {'model': '%s:block/wood/%s/%s' % (mod_id, variant, base_wood), 'x': 90, 'y': 90}
            }, use_default_model=False)

            stick_with_hammer = {
                'name': 'minecraft:stick',
                'conditions': [loot_tables.match_tag('tfc:hammers')],
                'functions': [loot_tables.set_count(1, 4)]
            }
            if variant == 'wood':
                block.with_block_loot((
                    stick_with_hammer,
                    {
                        'name': '%s:wood/log/%s' % (mod_id, base_wood),
                        'conditions': loot_tables.random_chance(0.6)
                    }

                ))
            else:
                block.with_block_loot((
                    stick_with_hammer,
                    {
                        'name': '%s:wood/log/%s' % (mod_id, base_wood),
                        'conditions': loot_tables.random_chance(0.6)
                    }
                ))

    rm.blockstate('light', variants={'level=%s' % i: {'model': 'minecraft:block/light_%s' % i if i >= 10 else 'minecraft:block/light_0%s' % i} for i in range(0, 15 + 1)}).with_lang(lang('Light'))
    rm.item_model('light', no_textures=True, parent='minecraft:item/light')

    rm.atlas('minecraft:blocks',
             atlases.palette(
                 key='afc:color_palettes/wood/planks/palette',
                 textures=['tfc:block/wood/planks/%s' % v for v in ('bookshelf_top', 'bookshelf_side')],
                 permutations=dict((wood, 'afc:color_palettes/wood/planks/%s' % wood) for wood in WOODS.keys())
             ),
             atlases.palette(
                 key='afc:color_palettes/wood/planks/palette',
                 textures=['tfc:item/wood/%s' % v for v in ('twig', 'lumber', 'chest_minecart_cover', 'stripped_log', 'sign_head', 'hanging_sign_head', 'water_wheel')],
                 permutations=dict((wood, 'afc:color_palettes/wood/plank_items/%s' % wood) for wood in WOODS.keys())
             ),
             atlases.palette(
                 key='afc:color_palettes/wood/planks/palette',
                 textures=['tfc:item/wood/boat'],
                 permutations=dict((wood, 'afc:color_palettes/wood/plank_items/%s' % wood) for wood in WOODS.keys())
             )
             )



def flower_pot_cross(rm: ResourceManager, simple_name: str, name: str, model: str, texture: str, loot: str):
    rm.blockstate(name, model='afc:block/%s' % model).with_lang(lang('potted %s', simple_name)).with_tag('minecraft:flower_pots').with_block_loot(loot, 'minecraft:flower_pot')
    rm.block_model(model, parent='minecraft:block/flower_pot_cross', textures={'plant': texture, 'dirt': 'tfc:block/dirt/loam'})

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

def make_door(block_context: BlockContext, door_suffix: str = '_door', top_texture: Optional[str] = None, bottom_texture: Optional[str] = None) -> 'BlockContext':
    """
    Generates all blockstates and models required for a standard door
    """
    door = block_context.res.join() + door_suffix
    block = block_context.res.join('block/') + door_suffix
    bottom = block + '_bottom'
    top = block + '_top'

    if top_texture is None:
        top_texture = top
    if bottom_texture is None:
        bottom_texture = bottom

    block_context.rm.blockstate(door, variants=door_blockstate(block))
    for model in ('bottom_left', 'bottom_left_open', 'bottom_right', 'bottom_right_open', 'top_left', 'top_left_open', 'top_right', 'top_right_open'):
        block_context.rm.block_model(door + '_' + model, {'top': top_texture, 'bottom': bottom_texture}, parent='block/door_%s' % model)
    block_context.rm.item_model(door)
    return block_context

def door_blockstate(base: str) -> JsonObject:
    left = base + '_bottom_left'
    left_open = base + '_bottom_left_open'
    right = base + '_bottom_right'
    right_open = base + '_bottom_right_open'
    top_left = base + '_top_left'
    top_left_open = base + '_top_left_open'
    top_right = base + '_top_right'
    top_right_open = base + '_top_right_open'
    return {
        'facing=east,half=lower,hinge=left,open=false': {'model': left},
        'facing=east,half=lower,hinge=left,open=true': {'model': left_open, 'y': 90},
        'facing=east,half=lower,hinge=right,open=false': {'model': right},
        'facing=east,half=lower,hinge=right,open=true': {'model': right_open, 'y': 270},
        'facing=east,half=upper,hinge=left,open=false': {'model': top_left},
        'facing=east,half=upper,hinge=left,open=true': {'model': top_left_open, 'y': 90},
        'facing=east,half=upper,hinge=right,open=false': {'model': top_right},
        'facing=east,half=upper,hinge=right,open=true': {'model': top_right_open, 'y': 270},
        'facing=north,half=lower,hinge=left,open=false': {'model': left, 'y': 270},
        'facing=north,half=lower,hinge=left,open=true': {'model': left_open},
        'facing=north,half=lower,hinge=right,open=false': {'model': right, 'y': 270},
        'facing=north,half=lower,hinge=right,open=true': {'model': right_open, 'y': 180},
        'facing=north,half=upper,hinge=left,open=false': {'model': top_left, 'y': 270},
        'facing=north,half=upper,hinge=left,open=true': {'model': top_left_open},
        'facing=north,half=upper,hinge=right,open=false': {'model': top_right, 'y': 270},
        'facing=north,half=upper,hinge=right,open=true': {'model': top_right_open, 'y': 180},
        'facing=south,half=lower,hinge=left,open=false': {'model': left, 'y': 90},
        'facing=south,half=lower,hinge=left,open=true': {'model': left_open, 'y': 180},
        'facing=south,half=lower,hinge=right,open=false': {'model': right, 'y': 90},
        'facing=south,half=lower,hinge=right,open=true': {'model': right_open},
        'facing=south,half=upper,hinge=left,open=false': {'model': top_left, 'y': 90},
        'facing=south,half=upper,hinge=left,open=true': {'model': top_left_open, 'y': 180},
        'facing=south,half=upper,hinge=right,open=false': {'model': top_right, 'y': 90},
        'facing=south,half=upper,hinge=right,open=true': {'model': top_right_open},
        'facing=west,half=lower,hinge=left,open=false': {'model': left, 'y': 180},
        'facing=west,half=lower,hinge=left,open=true': {'model': left_open, 'y': 270},
        'facing=west,half=lower,hinge=right,open=false': {'model': right, 'y': 180},
        'facing=west,half=lower,hinge=right,open=true': {'model': right_open, 'y': 90},
        'facing=west,half=upper,hinge=left,open=false': {'model': top_left, 'y': 180},
        'facing=west,half=upper,hinge=left,open=true': {'model': top_left_open, 'y': 270},
        'facing=west,half=upper,hinge=right,open=false': {'model': top_right, 'y': 180},
        'facing=west,half=upper,hinge=right,open=true': {'model': top_right_open, 'y': 90}
    }