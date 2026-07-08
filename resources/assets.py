#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

import itertools
import os

from mcresources import ResourceManager, ItemContext, utils, block_states, BlockContext, atlases
from mcresources.type_definitions import ResourceIdentifier, Json, JsonObject

from constants import *


def generate(rm: ResourceManager, tfc_rm: ResourceManager, fl_assets_rm, fl_data_rm: ResourceManager):

    rm.lang("afc.creative_tab.arborfirmacraft", "ArborFirmaCraft")
    rm.lang("item.afc.rubber_bar", "Rubber Bar")
    rm.lang("item.afc.maple_sugar", "Maple Sugar")
    rm.lang("item.afc.birch_sugar", "Birch Sugar")

    rm.lang("item.afc.bucket.maple_sap", "Maple Sap Bucket")
    rm.lang("item.afc.bucket.maple_sap_concentrate", "Maple Sap Concentrate Bucket")
    rm.lang("item.afc.bucket.maple_syrup", "Maple Syrup Bucket")
    rm.lang("item.afc.bucket.birch_sap", "Birch Sap Bucket")
    rm.lang("item.afc.bucket.birch_sap_concentrate", "Birch Sap Concentrate Bucket")
    rm.lang("item.afc.bucket.birch_syrup", "Birch Syrup Bucket")
    rm.lang("item.afc.bucket.latex", "Latex Bucket")

    rm.lang("block.afc.tree_tap", "Tree Tap")

    rm.lang("fluid.afc.maple_sap", "Maple Sap")
    rm.lang("fluid.afc.maple_sap_concentrate", "Maple Sap Concentrate")
    rm.lang("fluid.afc.maple_syrup", "Maple Syrup")
    rm.lang("fluid.afc.birch_sap", "Birch Sap")
    rm.lang("fluid.afc.birch_sap_concentrate", "Birch Sap Concentrate")
    rm.lang("fluid.afc.birch_syrup", "Birch Syrup")
    rm.lang("fluid.afc.latex", "Latex")

    for variant in TREE_VARIANTS.keys():
        # Leaves
        block = rm.blockstate(('wood', 'leaves', variant), model='afc:block/wood/leaves/%s_dynamic' % variant)

        if TREE_VARIANTS[variant].flower_model != 'random':
            block = rm.blockstate(('wood', 'leaves', variant), model='afc:block/wood/leaves/%s_dynamic' % variant).with_lang(lang('%s leaves', variant))
        elif variant == 'hardy_chestnut':
            block = blank_blockstate(rm, ('wood', 'leaves', variant), {"multipart":[{"apply":[{"model":"afc:block/wood/leaves/hardy_chestnut_empty"}]},{"apply":[{"model":"afc:block/wood/leaves/hardy_chestnut_dynamic_0","weight":8},{"model":"afc:block/wood/leaves/hardy_chestnut_dynamic_1","weight":5},{"model":"afc:block/wood/leaves/hardy_chestnut_dynamic_2","weight":5},{"model":"afc:block/wood/leaves/hardy_chestnut_dynamic_3","weight":5},{"model":"afc:block/wood/leaves/hardy_chestnut_dynamic_4","weight":5},{"model":"afc:block/wood/leaves/hardy_chestnut_dynamic_5","weight":12}]}]}).with_lang(lang('%s leaves', variant))

        # Dynamic Models
        if TREE_VARIANTS[variant].flower_model != 'random':
            rm.custom_block_model('wood/leaves/%s_dynamic' % variant, 'tfc:leaves', {
                'dense_leaves': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % variant},
                'sparse_leaves': {'parent': 'afc:block/wood/leaves/sparse_leaves/%s' % variant},
                'bare': {'parent': 'afc:block/wood/leaves/bare/%s' % variant},
                'blooming': {'parent': 'afc:block/wood/leaves/blooming/%s' % variant}
            })
        elif variant == 'hardy_chestnut':
            # Hardy Chestnut has random blooming models
            for i in range(5):
                rm.custom_block_model('wood/leaves/%s_dynamic_%s' % (variant, i), 'tfc:leaves', {
                    'dense_leaves': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % variant},
                    'sparse_leaves': {'parent': 'afc:block/wood/leaves/sparse_leaves/%s' % variant},
                    'bare': {'parent': 'afc:block/wood/leaves/bare/%s' % variant},
                    'blooming': {'parent': 'afc:block/wood/leaves/blooming/%s_%s' % (variant, i)}
                })
            # Include one blooming model where it just shows the normal dense leaf model
            rm.custom_block_model('wood/leaves/%s_dynamic_%s' % (variant, 5), 'tfc:leaves', {
                'dense_leaves': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % variant},
                'sparse_leaves': {'parent': 'afc:block/wood/leaves/sparse_leaves/%s' % variant},
                'bare': {'parent': 'afc:block/wood/leaves/bare/%s' % variant},
                'blooming': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % variant}
            })

        rm.block_model('wood/leaves/dense_leaves/%s' % variant, 'afc:block/wood/leaves/dense_leaves/%s' % variant, parent='block/leaves')
        rm.block_model('wood/leaves/sparse_leaves/%s' % variant, textures={
            'leaves': 'afc:block/wood/leaves/sparse_leaves/%s' % variant,
            'cross': 'afc:block/wood/leaves/bare/%s' % variant
        }, parent='tfc:block/sparse_leaves')
        if variant == 'jaggery_palm':
            rm.block_model('wood/leaves/bare/%s' % variant, {'all': 'afc:block/wood/leaves/bare/%s' % variant}, parent='block/cube_all')
        else:
            rm.block_model('wood/leaves/bare/%s' % variant, {'cross': 'afc:block/wood/leaves/bare/%s' % variant}, parent='block/cross')

        # Blooming -
        if TREE_VARIANTS[variant].flower_model == 'bare':
            # No tint, no leaves, branches below
            rm.block_model('wood/leaves/blooming/%s' % variant, textures={
                'leaves': 'afc:block/wood/leaves/blooming/%s' % variant,
                'cross': 'afc:block/wood/leaves/bare/%s' % variant
            }, parent='tfc:block/blooming_branches')
        elif TREE_VARIANTS[variant].flower_model == 'sparse':
            # Tinted sparse leaves, flowers over
            rm.block_model('wood/leaves/blooming/%s' % variant, textures={
                'leaves': 'afc:block/wood/leaves/sparse_leaves/%s' % variant,
                'overlay': 'afc:block/wood/leaves/blooming/%s' % variant
            }, parent='tfc:block/blooming_leaves')
        elif TREE_VARIANTS[variant].flower_model == 'leaves':
            # Tinted leaves, flowers over
            rm.block_model('wood/leaves/blooming/%s' % variant, textures={
                'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % variant,
                'overlay': 'afc:block/wood/leaves/blooming/%s' % variant
            }, parent='tfc:block/blooming_leaves')
        elif TREE_VARIANTS[variant].flower_model == 'cones':
            # Tinted leaves, flowers as cross under
            rm.block_model('wood/leaves/blooming/%s' % variant, textures={
                'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % variant,
                'cross': 'afc:block/wood/leaves/blooming/%s' % variant
            }, parent='tfc:block/sparse_leaves')
        elif variant == 'hardy_chestnut':
            for i in range(5):
                rm.block_model('wood/leaves/blooming/%s_%s' % (variant, i), textures={
                    'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % variant,
                    'overlay': 'afc:block/wood/leaves/blooming/%s_%s' % (variant, i)
                }, parent='tfc:block/side_blooming_leaves')

        rm.item_model(('wood', 'leaves', variant), parent='afc:block/wood/leaves/dense_leaves/%s' % variant)

        block.with_lang(lang('%s leaves', variant))

        # Sapling
        block = rm.blockstate(('wood', 'sapling', variant), 'afc:block/wood/sapling/%s' % variant)
        block.with_block_model({'cross': 'afc:block/wood/sapling/%s' % variant}, 'block/cross')
        rm.item_model(('wood', 'sapling', variant), 'afc:block/wood/sapling/%s' % variant)
        block.with_lang(lang('%s sapling', variant))

        flower_pot_cross(rm, '%s sapling' % variant, 'afc:wood/potted_sapling/%s' % variant, 'wood/potted_sapling/%s' % variant, 'afc:block/wood/sapling/%s' % variant)
        block.with_lang(lang('potted %s sapling', variant))

        # Fallen Leaves
        rm.blockstate(('wood', 'fallen_leaves', variant), variants=dict((('layers=%d' % i), {'model': 'afc:block/wood/fallen_leaves/%s_height%d' % (variant, i * 2) if i != 8 else 'afc:block/wood/leaves/dense_leaves/%s' % variant}) for i in range(1, 1 + 8))).with_lang(lang('fallen %s leaves', variant))
        tex = {'all': 'afc:block/wood/leaves/dense_leaves/%s' % variant}
        # Replace this with any AFC leaves that should have different top textures
        if variant in ('mangrove', 'willow'):
            tex['top'] = 'afc:block/wood/leaves/dense_leaves/%s_top' % variant
        for i in range(1, 8):
            rm.block_model(('wood', 'fallen_leaves', '%s_height%s' % (variant, i * 2)), tex, parent='tfc:block/groundcover/fallen_leaves_height%s' % (i * 2))
        rm.item_model(('wood', 'fallen_leaves', variant), 'tfc:item/groundcover/fallen_leaves')
        block.with_lang(lang('%s sapling', variant))

    # Wood Blocks
    for wood in AFC_WOODS.keys():
        default_species = DEFAULT_SPECIES[wood]

        # Logs
        for variant in ('log', 'stripped_log', 'wood', 'stripped_wood'):
            block = rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': 'afc:block/wood/%s/%s' % (variant, wood)},
                'axis=z': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90},
                'axis=x': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90, 'y': 90}
            }, use_default_model=False)

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

        # Firmalife
        block = fl_assets_rm.blockstate('afc:wood/food_shelf/%s' % wood, variants=four_rotations('afc:block/wood/food_shelf/%s_dynamic' % wood, (270, 180, None, 90)))
        block.with_lang(lang('%s food shelf', wood))
        fl_assets_rm.item_model('afc:wood/food_shelf/%s' % wood, parent='afc:block/wood/food_shelf/%s' % wood, no_textures=True)
        fl_assets_rm.custom_block_model('afc:wood/food_shelf/%s_dynamic' % wood, 'firmalife:food_shelf', {'base': {'parent': 'afc:block/wood/food_shelf/%s' % wood}})
        fl_assets_rm.block_model('afc:wood/food_shelf/%s' % wood, parent='firmalife:block/food_shelf_base', textures={'wood': 'afc:block/wood/planks/%s' % wood})

        block = fl_assets_rm.blockstate('afc:wood/hanger/%s' % wood, model='afc:block/wood/hanger/%s_dynamic' % wood)
        block.with_lang(lang('%s hanger' % wood))
        fl_assets_rm.custom_block_model('afc:wood/hanger/%s_dynamic' % wood, 'firmalife:hanger', {'base': {'parent': 'afc:block/wood/hanger/%s' % wood}})
        fl_assets_rm.item_model('afc:wood/hanger/%s' % wood, parent='afc:block/wood/hanger/%s' % wood, no_textures=True)
        fl_assets_rm.block_model('afc:wood/hanger/%s' % wood, parent='firmalife:block/hanger_base', textures={'wood': 'afc:block/wood/planks/%s' % wood, 'string': 'minecraft:block/white_wool'})

        block = fl_assets_rm.blockstate('afc:wood/jarbnet/%s' % wood, variants={
            **four_rotations('afc:block/wood/jarbnet/%s_dynamic' % wood, (90, None, 180, 270), suffix=',open=true'),
            **four_rotations('afc:block/wood/jarbnet/%s_shut_dynamic' % wood, (90, None, 180, 270), suffix=',open=false'),
        })
        block.with_lang(lang('%s jarbnet', wood))
        fl_assets_rm.item_model('afc:wood/jarbnet/%s' % wood, parent='afc:block/wood/jarbnet/%s' % wood, no_textures=True)
        textures = {'planks': 'afc:block/wood/planks/%s' % wood, 'sheet': 'afc:block/wood/sheet/%s' % wood, 'log': 'afc:block/wood/log/%s' % wood}
        fl_assets_rm.block_model('afc:wood/jarbnet/%s' % wood, parent='firmalife:block/jarbnet', textures=textures)
        fl_assets_rm.block_model('afc:wood/jarbnet/%s_shut' % wood, parent='firmalife:block/jarbnet_shut', textures=textures)
        fl_assets_rm.custom_block_model('afc:wood/jarbnet/%s_dynamic' % wood, 'firmalife:jarbnet', {'base': {'parent': 'afc:block/wood/jarbnet/%s' % wood}})
        fl_assets_rm.custom_block_model('afc:wood/jarbnet/%s_shut_dynamic' % wood, 'firmalife:jarbnet', {'base': {'parent': 'afc:block/wood/jarbnet/%s_shut' % wood}})

        tex = {
            '0': f'afc:block/wood/big_barrel/{wood}_3_side',
            '1': f'afc:block/wood/big_barrel/{wood}_0',
            '2': f'afc:block/wood/big_barrel/{wood}_0_side',
            '3': f'afc:block/wood/big_barrel/{wood}_1',
            '4': f'afc:block/wood/big_barrel/{wood}_1_side',
            '5': f'afc:block/wood/big_barrel/{wood}_2',
            '6': f'afc:block/wood/big_barrel/{wood}_2_side',
            '7': f'afc:block/wood/big_barrel/{wood}_3',
            '8': f'afc:block/wood/big_barrel/{wood}_3_top',
            '9': f'afc:block/wood/big_barrel/{wood}_0_top',
            '10': f'afc:block/wood/big_barrel/{wood}_1_top',
            '11': f'afc:block/wood/big_barrel/{wood}_2_top',
            '12': f'tfc:block/wood/log/{wood}',
        }
        for i in range(1, 8):
            fl_assets_rm.block_model('wood/big_barrel/%s_%s' % (wood, i), parent='firmalife:block/big_barrel_%s' % i, textures=tex)
        fl_assets_rm.block_model('wood/big_barrel/%s_0_unsealed' % wood, parent='firmalife:block/big_barrel_0_unsealed', textures=tex)
        fl_assets_rm.block_model('wood/big_barrel/%s_0_sealed' % wood, parent='firmalife:block/big_barrel_0_sealed', textures=tex)
        fl_assets_rm.block_model('wood/big_barrel/%s_item' % wood, parent='firmalife:block/big_barrel_item', textures=tex)
        fl_assets_rm.item_model('wood/keg/%s' % wood, parent='afc:block/wood/big_barrel/%s_item' % wood, no_textures=True)
        block = fl_assets_rm.blockstate('wood/keg_sub/%s' % wood, variants=dict(
            ('barrel_part=%s,facing=%s' % (i, f), {'model': 'afc:block/wood/big_barrel/%s_%s' % (wood, i), 'y': y if y != 0 else None})
            for f, y in (('east', 90), ('north', 0), ('south', 180), ('west', 270)) for i in range(1, 8)
        )).with_lang(lang('%s keg' % wood))
        fl_assets_rm.blockstate('wood/keg/%s' % wood, variants={
            **four_rotations('afc:block/wood/big_barrel/%s_0_unsealed' % wood, (90, None, 180, 270), suffix=',sealed=false'),
            **four_rotations('afc:block/wood/big_barrel/%s_0_sealed' % wood, (90, None, 180, 270), suffix=',sealed=true')
        }).with_lang(lang('%s keg', wood))

        fl_assets_rm.blockstate('wood/stomping_barrel/%s' % wood).with_block_model({'0': 'afc:block/wood/sheet/%s' % wood}, 'firmalife:block/stomping_barrel').with_lang(lang('%s stomping barrel', wood))
        fl_assets_rm.item_model('wood/stomping_barrel/%s' % wood, parent='afc:block/wood/stomping_barrel/%s' % wood, no_textures=True)

        fl_assets_rm.blockstate('wood/barrel_press/%s' % wood).with_block_model({'0': 'afc:block/wood/sheet/%s' % wood}, 'firmalife:block/barrel_press').with_lang(lang('%s barrel press', wood))
        fl_assets_rm.item_model('wood/barrel_press/%s' % wood, parent='afc:block/wood/barrel_press/%s' % wood, no_textures=True)

        block = fl_assets_rm.blockstate('wood/wine_shelf/%s' % wood, variants=four_rotations('afc:block/wood/wine_shelf/%s_dynamic' % wood, (90, None, 180, 270)))
        block.with_block_model({'0': 'afc:block/wood/planks/%s' % wood, '2': 'afc:block/wood/sheet/%s' % wood, '3': 'afc:block/wood/stripped_log/%s' % wood}, 'firmalife:block/wine_shelf')
        block.with_lang(lang('%s wine shelf', wood))
        fl_assets_rm.item_model('wood/wine_shelf/%s' % wood, parent='afc:block/wood/wine_shelf/%s' % wood, no_textures=True)
        fl_assets_rm.custom_block_model('afc:wood/wine_shelf/%s_dynamic' % wood, 'firmalife:wine_shelf', {'base': {'parent': 'afc:block/wood/wine_shelf/%s' % wood}})

        # Groundcover
        block = rm.blockstate(('wood', 'twig', wood), variants={"": four_ways('afc:block/wood/twig/%s' % wood)}, use_default_model=False)
        block.with_lang(lang('%s twig', wood))

        block.with_block_model({'side': 'afc:block/wood/log/%s' % wood, 'top': 'afc:block/wood/log_top/%s' % wood}, parent='tfc:block/groundcover/twig')
        rm.item_model('wood/twig/%s' % wood, 'afc:item/wood/twig/%s' % wood, parent='item/handheld_rod')

        block = rm.blockstate(('wood', 'fallen_leaves', wood), variants=dict((('layers=%d' % i), {'model': 'afc:block/wood/fallen_leaves/%s_height%d' % (wood, i * 2) if i != 8 else 'afc:block/wood/leaves/dense_leaves/%s' % wood}) for i in range(1, 1 + 8))).with_lang(lang('fallen %s leaves', default_species))
        tex = {'all': 'afc:block/wood/leaves/dense_leaves/%s' % wood}
        #Leaving this in, in case we want to use it for other stuff
        if wood in ('mangrove', 'willow'):
            tex['top'] = 'afc:block/wood/leaves/dense_leaves/%s_top' % wood
        for i in range(1, 8):
            rm.block_model(('wood', 'fallen_leaves', '%s_height%s' % (wood, i * 2)), tex, parent='tfc:block/groundcover/fallen_leaves_height%s' % (i * 2))
        rm.item_model(('wood', 'fallen_leaves', wood), 'tfc:item/groundcover/fallen_leaves')

        # Leaves
        if AFC_WOODS[wood].flower_model != 'random':
            block = rm.blockstate(('wood', 'leaves', wood), model='afc:block/wood/leaves/%s_dynamic' % wood)
        elif wood == 'mahoe':
            block = blank_blockstate(rm, ('wood', 'leaves', wood), {"multipart":[{"apply":[{"model":"afc:block/wood/leaves/mahoe_empty"}]},{"apply":[{"model":"afc:block/wood/leaves/mahoe_dynamic_0","weight":8},{"model":"afc:block/wood/leaves/mahoe_dynamic_1","weight":5},{"model":"afc:block/wood/leaves/mahoe_dynamic_2","weight":5},{"model":"afc:block/wood/leaves/mahoe_dynamic_3","weight":5},{"model":"afc:block/wood/leaves/mahoe_dynamic_4","weight":5},{"model":"afc:block/wood/leaves/mahoe_dynamic_5","weight":12}]}]}).with_lang(lang('%s leaves', wood))


        # Dynamic Models
        if AFC_WOODS[wood].flower_model != 'random':
            rm.custom_block_model('wood/leaves/%s_dynamic' % wood, 'tfc:leaves', {
                'dense_leaves': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % wood},
                'sparse_leaves': {'parent': 'afc:block/wood/leaves/sparse_leaves/%s' % wood},
                'bare': {'parent': 'afc:block/wood/leaves/bare/%s' % wood},
                'blooming': {'parent': 'afc:block/wood/leaves/blooming/%s' % wood}
            })
        elif wood == 'mahoe':
        # Mahoe has random blooming models
            for i in range(5):
                rm.custom_block_model('wood/leaves/%s_dynamic_%s' % (wood, i), 'tfc:leaves', {
                    'dense_leaves': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % wood},
                    'sparse_leaves': {'parent': 'afc:block/wood/leaves/sparse_leaves/%s' % wood},
                    'bare': {'parent': 'afc:block/wood/leaves/bare/%s' % wood},
                    'blooming': {'parent': 'afc:block/wood/leaves/blooming/%s_%s' % (wood, i)}
                })
            # Include one blooming model where it just shows the normal dense leaf model
            rm.custom_block_model('wood/leaves/%s_dynamic_%s' % (wood, 5), 'tfc:leaves', {
                'dense_leaves': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % wood},
                'sparse_leaves': {'parent': 'afc:block/wood/leaves/sparse_leaves/%s' % wood},
                'bare': {'parent': 'afc:block/wood/leaves/bare/%s' % wood},
                'blooming': {'parent': 'afc:block/wood/leaves/dense_leaves/%s' % wood}
            })

        rm.block_model('wood/leaves/dense_leaves/%s' % wood, 'afc:block/wood/leaves/dense_leaves/%s' % wood, parent='block/leaves')
        rm.block_model('wood/leaves/sparse_leaves/%s' % wood, textures={
            'leaves': 'afc:block/wood/leaves/sparse_leaves/%s' % wood,
            'cross': 'afc:block/wood/leaves/bare/%s' % wood
        }, parent='tfc:block/sparse_leaves')
        if wood == 'jaggery_palm':
            rm.block_model('wood/leaves/bare/%s' % wood, {'all': 'afc:block/wood/leaves/bare/%s' % wood}, parent='block/cube_all')
        else:
            rm.block_model('wood/leaves/bare/%s' % wood, {'cross': 'afc:block/wood/leaves/bare/%s' % wood}, parent='block/cross')

        # Blooming -
        if AFC_WOODS[wood].flower_model == 'bare':
            # No tint, no leaves, branches below
            rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                'leaves': 'afc:block/wood/leaves/blooming/%s' % wood,
                'cross': 'afc:block/wood/leaves/bare/%s' % wood
            }, parent='tfc:block/blooming_branches')
        elif AFC_WOODS[wood].flower_model == 'sparse':
            # Tinted sparse leaves, flowers over
            if wood == 'palm' or wood == 'willow' or wood == 'mangrove':
                rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                    'side': 'afc:block/wood/leaves/sparse_leaves/%s' % wood,
                    'end': 'afc:block/wood/leaves/sparse_leaves/%s_top' % wood,
                    'overlay': 'afc:block/wood/leaves/blooming/%s' % wood
                }, parent='tfc:block/blooming_leaves_column')
            else:
                rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                    'leaves': 'afc:block/wood/leaves/sparse_leaves/%s' % wood,
                    'overlay': 'afc:block/wood/leaves/blooming/%s' % wood
                }, parent='tfc:block/blooming_leaves')
        elif AFC_WOODS[wood].flower_model == 'leaves':
            # Tinted leaves, flowers over
            rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                    'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % wood,
                    'overlay': 'afc:block/wood/leaves/blooming/%s' % wood
                }, parent='tfc:block/blooming_leaves')
        elif AFC_WOODS[wood].flower_model == 'sides':
            # Tinted leaves, flowers over
            rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                    'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % wood,
                    'overlay': 'afc:block/wood/leaves/blooming/%s' % wood
                }, parent='tfc:block/side_blooming_leaves')
        elif AFC_WOODS[wood].flower_model == 'hanging':
            # Tinted leaves, flowers in a cross half-below
            rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % wood,
                'cross': 'afc:block/wood/leaves/blooming/%s' % wood
            }, parent='afc:block/hanging_blooming_leaves')
        elif AFC_WOODS[wood].flower_model == 'cones':
            # Tinted leaves, flowers as cross under
            if wood == 'palm' or wood == 'willow' or wood == 'mangrove':
                rm.block_model('wood/leaves/blooming/%s' % wood, {
                    'side': 'afc:block/wood/leaves/dense_leaves/%s' % wood,
                    'end': 'afc:block/wood/leaves/dense_leaves/%s_top' % wood,
                    'cross': 'afc:block/wood/leaves/blooming/%s' % wood
                }, parent='tfc:block/sparse_leaves_column')
            else:
                rm.block_model('wood/leaves/blooming/%s' % wood, textures={
                    'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % wood,
                    'cross': 'afc:block/wood/leaves/blooming/%s' % wood
                }, parent='tfc:block/sparse_leaves')
        elif wood == 'mahoe':
            for i in range(5):
                rm.block_model('wood/leaves/blooming/%s_%s' % (wood, i), textures={
                    'leaves': 'afc:block/wood/leaves/dense_leaves/%s' % wood,
                    'overlay': 'afc:block/wood/leaves/blooming/%s_%s' % (wood, i)
                }, parent='tfc:block/blooming_leaves')

        rm.item_model(('wood', 'leaves', wood), parent='afc:block/wood/leaves/dense_leaves/%s' % wood)

        block.with_lang(lang('%s leaves', wood))

        # Sapling
        block = rm.blockstate(('wood', 'sapling', wood), 'afc:block/wood/sapling/%s' % wood)
        block.with_block_model({'cross': 'afc:block/wood/sapling/%s' % wood}, 'block/cross')
        rm.item_model(('wood', 'sapling', wood), 'afc:block/wood/sapling/%s' % wood)

        flower_pot_cross(rm, '%s sapling' % default_species, 'afc:wood/potted_sapling/%s' % wood, 'wood/potted_sapling/%s' % wood, 'afc:block/wood/sapling/%s' % wood)

        # Signs + Hanging Signs
        rm.item_model(('wood', 'sign', wood), 'afc:item/wood/sign/%s' % wood, 'tfc:item/wood/sign_head_overlay%s' % ('_white' if wood in ('ironwood', 'mahogany') else ''))
        for metal in SIGN_METALS:
            rm.item_model(('wood', 'hanging_sign', metal, wood), 'afc:item/wood/hanging_sign/head_%s' % wood, 'tfc:item/wood/hanging_sign_head_overlay%s' % ('_white' if wood in ('ironwood', 'mahogany') else ''), 'tfc:item/metal/hanging_sign/%s' % metal)
            metal_name = str.title(metal)
            wood_name = str.title(wood)
            rm.lang("block.afc.wood.planks.hanging_sign.%s.%s" % (metal, wood), "%s %s Hanging Sign" % (metal_name.replace("_", " "), wood_name.replace("_", " ")))

        rm.block_model('wood/sign/%s_particle' % wood, {
            'particle': 'afc:block/wood/planks/%s' % wood
        }, parent=None)

        for variant in ('sign', 'wall_sign'):
            block = rm.blockstate(('wood', variant, wood), model='afc:block/wood/sign/%s_particle' % wood)
            block.with_lang(lang('%s %s', wood, variant))

        for metal in SIGN_METALS:
            for variant in ('hanging_sign', 'wall_hanging_sign'):
                block = rm.blockstate(('wood', variant, metal, wood), model='afc:block/wood/sign/%s_particle' % wood)
                block.with_lang(lang('%s %s %s', metal, wood, variant))

        # Planks and variant blocks
        block = rm.block(('wood', 'planks', wood))
        block.with_blockstate()
        block.with_block_model()
        block.with_item_model()
        block.with_lang(lang('%s planks', wood))
        block.make_slab()
        block.make_stairs()

        # Pressure Plate
        block = rm.block('wood/pressure_plate/%s' % wood)
        block.make_pressure_plate('', 'afc:block/wood/planks/%s' % wood)
        block.with_lang(lang('%s pressure plate', wood))

        # Button
        block = rm.block('wood/button/%s' % wood)
        block.make_button('', 'afc:block/wood/planks/%s' % wood)
        block.with_lang(lang('%s button', wood))

        # Doors
        block = rm.blockstate('wood/door/%s' % wood, variants=door_blockstate('afc:block/wood/door/%s' % wood))
        rm.item_model('afc:wood/door/%s' % wood, 'afc:item/wood/door/%s' % wood)
        block.with_lang(lang('%s door', wood))

        for model in ('bottom_left', 'bottom_left_open', 'bottom_right', 'bottom_right_open', 'top_left', 'top_left_open', 'top_right', 'top_right_open'):
            rm.block_model('afc:wood/door/%s_%s' % (wood, model), {
                'top': 'afc:block/wood/door/%s_top' % wood,
                'bottom': 'afc:block/wood/door/%s_bottom' % wood
            }, parent='block/door_%s' % model)

        # Trapdoor
        block = rm.block('wood/trapdoor/%s' % wood)
        block.make_trapdoor('', 'afc:block/wood/trapdoor/%s' % wood)
        block.with_lang(lang('%s trapdoor', wood))

        # Fences, Log Fences, Fence Gates
        block = rm.block('wood/fence/%s' % wood)
        block.make_fence('', 'afc:block/wood/planks/%s' % wood)
        block.with_lang(lang('%s fence', wood))

        block = rm.block('wood/fence_gate/%s' % wood)
        block.make_fence_gate('', 'afc:block/wood/planks/%s' % wood)
        block.with_lang(lang('%s fence gate', wood))

        # Log Fences - need to copy `make_fence()` because we have separate textures for post and side
        block = rm.blockstate_multipart('wood/log_fence/%s' % wood, *block_states.fence_multipart('afc:block/wood/log_fence/%s_post' % wood, 'afc:block/wood/log_fence/%s_side' % wood))
        block.with_lang(lang('%s log fence', wood))
        rm.block_model('wood/log_fence/%s_post' % wood, textures={'texture': 'afc:block/wood/log/' + wood}, parent='block/fence_post')
        rm.block_model('wood/log_fence/%s_side' % wood, textures={'texture': 'afc:block/wood/planks/' + wood}, parent='block/fence_side')
        rm.block_model('wood/log_fence/%s_inventory' % wood, textures={
            'log': 'afc:block/wood/log/' + wood,
            'planks': 'afc:block/wood/planks/' + wood
        }, parent='tfc:block/wood/log_fence/inventory')
        rm.item_model('wood/log_fence/%s' % wood, parent='afc:block/wood/log_fence/%s_inventory' % wood, no_textures=True)


        # Tool Rack
        block = rm.blockstate('afc:wood/tool_rack/%s' % wood, model='afc:block/wood/tool_rack/%s' % wood, variants=four_rotations('afc:block/wood/tool_rack/%s' % wood, (270, 180, None, 90)))
        block.with_block_model(textures={
            'texture': 'afc:block/wood/planks/%s' % wood,
            'particle': 'afc:block/wood/planks/%s' % wood
        }, parent='tfc:block/tool_rack')
        block.with_lang(lang('%s Tool Rack', wood))
        block.with_item_model()

        # Loom
        block = rm.blockstate('afc:wood/loom/%s' % wood, model='afc:block/wood/loom/%s' % wood, variants=four_rotations('afc:block/wood/loom/%s' % wood, (270, 180, None, 90)))
        block.with_block_model(textures={
            'texture': 'afc:block/wood/planks/%s' % wood,
            'particle': 'afc:block/wood/planks/%s' % wood
        }, parent='tfc:block/loom')
        block.with_item_model()
        block.with_lang(lang('%s loom', wood))
        # Bookshelf
        faces = (('east', 90), ('north', None), ('west', 270), ('south', 180))
        parts = [
                    ({'facing': face}, {'model': 'afc:block/wood/bookshelf/%s' % wood, 'y': y, 'uvlock': True})
                    for face, y in faces
                ] + [
                    ({'AND': [{'facing': face}, {f'slot_{i}_occupied': is_occupied}]}, {'model': f'afc:block/wood/bookshelf/{wood}_{occupation}_{slot_type}', 'y': y})
                    for face, y in faces
                    for slot_type, i in (('top_right', 2), ('bottom_mid', 4), ('top_left', 0), ('bottom_right', 5), ('bottom_left', 3), ('top_mid', 1))
                    for occupation, is_occupied in (('empty', 'false'), ('occupied', 'true'))
                ]

        block = rm.blockstate_multipart(('wood', 'bookshelf', wood), *parts)
        block.with_lang(lang('%s bookshelf', wood))
        rm.block_model(('wood', 'bookshelf', wood), {
            'top': 'afc:block/wood/bookshelf/%s_top' % wood,
            'side': 'afc:block/wood/bookshelf/%s_side' % wood
        }, parent='minecraft:block/chiseled_bookshelf')
        rm.block_model('wood/bookshelf/%s_inventory' % wood, {
            'top': 'afc:block/wood/bookshelf/%s_top' % wood,
            'side': 'afc:block/wood/bookshelf/%s_side' % wood,
            'front': 'afc:block/wood/bookshelf/%s_empty' % wood
        }, parent='minecraft:block/chiseled_bookshelf_inventory')
        rm.item_model('afc:wood/bookshelf/%s' % wood, parent='afc:block/wood/bookshelf/%s_inventory' % wood, no_textures=True)

        for slot in ('bottom_left', 'bottom_mid', 'bottom_right', 'top_left', 'top_mid', 'top_right'):
            for occupancy in ('empty', 'occupied'):
                rm.block_model(f'wood/bookshelf/{wood}_{occupancy}_{slot}', {
                    'texture': f'afc:block/wood/bookshelf/{wood}_{occupancy}'
                }, parent=f'minecraft:block/chiseled_bookshelf_{occupancy}_slot_{slot}')

        # Workbench
        block = rm.blockstate(('wood', 'workbench', wood)).with_block_model(parent='minecraft:block/cube', textures={
            'particle': 'afc:block/wood/workbench/%s_front' % wood,
            'north': 'afc:block/wood/workbench/%s_front' % wood,
            'south': 'afc:block/wood/workbench/%s_side' % wood,
            'east': 'afc:block/wood/workbench/%s_side' % wood,
            'west': 'afc:block/wood/workbench/%s_front' % wood,
            'up': 'afc:block/wood/workbench/%s_top' % wood,
            'down': 'afc:block/wood/planks/%s' % wood
        })
        block.with_item_model()
        block.with_lang(lang('%s Workbench', wood))

        # Doors
        rm.item_model('afc:wood/planks/%s_door' % wood, 'afc:item/wood/planks/%s_door' % wood)

        # Log Fences
        log_fence_namespace = 'afc:wood/planks/' + wood + '_log_fence'
        rm.blockstate_multipart(log_fence_namespace, *block_states.fence_multipart('afc:block/wood/planks/' + wood + '_log_fence_post', 'afc:block/wood/planks/' + wood + '_log_fence_side'))
        rm.block_model(log_fence_namespace + '_post', textures={'texture': 'afc:block/wood/log/' + wood}, parent='block/fence_post')
        rm.block_model(log_fence_namespace + '_side', textures={'texture': 'afc:block/wood/planks/' + wood}, parent='block/fence_side')
        rm.block_model(log_fence_namespace + '_inventory', textures={'log': 'afc:block/wood/log/' + wood, 'planks': 'afc:block/wood/planks/' + wood}, parent='tfc:block/log_fence_inventory')
        rm.item_model('afc:wood/planks/' + wood + '_log_fence', parent='afc:block/wood/planks/' + wood + '_log_fence_inventory', no_textures=True)

        # Support Beams
        texture = 'afc:block/wood/sheet/%s' % wood
        connection = 'afc:block/wood/support/%s_connection' % wood
        rm.blockstate_multipart(('wood', 'vertical_support', wood),
                                {'model': 'afc:block/wood/support/%s_vertical' % wood},
                                ({'north': True}, {'model': connection, 'y': 270}),
                                ({'east': True}, {'model': connection}),
                                ({'south': True}, {'model': connection, 'y': 90}),
                                ({'west': True}, {'model': connection, 'y': 180}),
                                ).with_lang(lang('%s Support', wood))
        rm.blockstate_multipart(('wood', 'horizontal_support', wood),
                                {'model': 'afc:block/wood/support/%s_horizontal' % wood},
                                ({'north': True}, {'model': connection, 'y': 270}),
                                ({'east': True}, {'model': connection}),
                                ({'south': True}, {'model': connection, 'y': 90}),
                                ({'west': True}, {'model': connection, 'y': 180}),
                                ).with_lang(lang('%s Support', wood))

        rm.block_model('afc:wood/support/%s_inventory' % wood, textures={'texture': texture}, parent='tfc:block/wood/support/inventory')
        rm.block_model('afc:wood/support/%s_vertical' % wood, textures={'texture': texture, 'particle': texture}, parent='tfc:block/wood/support/vertical')
        rm.block_model('afc:wood/support/%s_connection' % wood, textures={'texture': texture, 'particle': texture}, parent='tfc:block/wood/support/connection')
        rm.block_model('afc:wood/support/%s_horizontal' % wood, textures={'texture': texture, 'particle': texture}, parent='tfc:block/wood/support/horizontal')
        rm.item_model(('wood', 'support', wood), no_textures=True, parent='afc:block/wood/support/%s_inventory' % wood).with_lang(lang('%s Support', wood))

        for chest in ('chest', 'trapped_chest'):
            rm.blockstate(('wood', chest, wood), model='afc:block/wood/%s/%s' % (chest, wood)).with_lang(lang('%s %s', wood, chest))
            rm.block_model(('wood', chest, wood), textures={'particle': 'afc:block/wood/planks/%s' % wood}, parent=None)
            rm.item_model(('wood', chest, wood), {'particle': 'afc:block/wood/planks/%s' % wood}, parent='minecraft:item/chest')

        rm.block_model('wood/sluice/%s_upper' % wood, textures={'texture': 'afc:block/wood/sheet/%s' % wood}, parent='tfc:block/sluice_upper')
        rm.block_model('wood/sluice/%s_lower' % wood, textures={'texture': 'afc:block/wood/sheet/%s' % wood}, parent='tfc:block/sluice_lower')
        rm.blockstate(('wood', 'sluice', wood), variants={**four_rotations('afc:block/wood/sluice/%s_upper' % wood, (90, 0, 180, 270), suffix=',upper=true'), **four_rotations('afc:block/wood/sluice/%s_lower' % wood, (90, 0, 180, 270), suffix=',upper=false')}).with_lang(lang('%s sluice', wood))
        rm.item_model(('wood', 'sluice', wood), parent='afc:block/wood/sluice/%s_lower' % wood, no_textures=True)

        # Crate
        block = rm.blockstate(('wood', 'crate', wood)).with_block_model().with_lang(lang('%s crate', wood)).with_block_loot('afc:wood/crate/%s' % wood).with_item_model()

        # Barrels
        texture = 'afc:block/wood/planks/%s' % wood
        textures = {'particle': texture, 'planks': texture, 'sheet': 'afc:block/wood/sheet/%s' % wood}

        faces = (('up', 0), ('east', 0), ('west', 180), ('south', 90), ('north', 270))
        seals = (('true', 'barrel_sealed'), ('false', 'barrel'))
        racks = (('true', '_rack'), ('false', ''))
        block = rm.blockstate(('wood', 'barrel', wood), variants=dict((
                                                                          'facing=%s,rack=%s,sealed=%s' % (face, rack, is_seal), {'model': 'afc:block/wood/%s/%s%s%s' % (seal_type, wood, '_side' if face != 'up' else '', suffix if face != 'up' else ''), 'y': yrot if yrot != 0 else None}
                                                                      ) for face, yrot in faces for rack, suffix in racks for is_seal, seal_type in seals))

        rm.item_model(('wood', 'barrel', wood), no_textures=True, parent='afc:block/wood/barrel/%s' % wood, overrides=[override('afc:block/wood/barrel_sealed/%s' % wood, 'tfc:sealed')])
        block.with_block_model(textures, 'tfc:block/barrel')
        rm.block_model(('wood', 'barrel', wood + '_side'), textures, 'tfc:block/barrel_side')
        rm.block_model(('wood', 'barrel', wood + '_side_rack'), textures, 'tfc:block/barrel_side_rack')
        rm.block_model(('wood', 'barrel_sealed', wood + '_side_rack'), textures, 'tfc:block/barrel_side_sealed_rack')
        rm.block_model(('wood', 'barrel_sealed', wood), textures, 'tfc:block/barrel_sealed')
        rm.block_model(('wood', 'barrel_sealed', wood + '_side'), textures, 'tfc:block/barrel_side_sealed')
        block.with_lang(lang('%s barrel', wood))

        # Lecterns
        block = rm.blockstate('afc:wood/lectern/%s' % wood, variants=four_rotations('afc:block/wood/lectern/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'bottom': 'afc:block/wood/planks/%s' % wood, 'base': 'afc:block/wood/lectern/%s/base' % wood, 'front': 'afc:block/wood/lectern/%s/front' % wood, 'sides': 'afc:block/wood/lectern/%s/sides' % wood, 'top': 'afc:block/wood/lectern/%s/top' % wood, 'particle': 'afc:block/wood/lectern/%s/sides' % wood}, parent='minecraft:block/lectern')
        block.with_item_model().with_lang(lang("%s lectern" % wood))
        # Scribing Table
        block = rm.blockstate('afc:wood/scribing_table/%s' % wood, variants=four_rotations('afc:block/wood/scribing_table/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'top': 'afc:block/wood/scribing_table/%s' % wood, 'leg': 'afc:block/wood/log/%s' % wood, 'side' : 'afc:block/wood/planks/%s' % wood, 'misc': 'tfc:block/wood/scribing_table/scribing_paraphernalia', 'particle': 'afc:block/wood/planks/%s' % wood}, parent='tfc:block/scribing_table')
        block.with_item_model().with_lang(lang("%s scribing table" % wood))
        # Sewing Table
        block = rm.blockstate('wood/sewing_table/%s' % wood, variants=four_rotations('afc:block/wood/sewing_table/%s' % wood, (90, None, 180, 270))).with_item_model()
        rm.block_model(('wood', 'sewing_table', wood), {'0': 'afc:block/wood/log/%s' % wood, '1': 'afc:block/wood/planks/%s' % wood}, 'tfc:block/sewing_table')
        block.with_lang(lang('%s sewing table', wood))

        # Shelf
        block = rm.blockstate('wood/shelf/%s' % wood, variants=four_rotations('afc:block/wood/shelf/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={
            '0': 'afc:block/wood/planks/%s' % wood
        }, parent='tfc:block/wood/shelf')
        block.with_item_model()
        block.with_lang(lang('%s shelf', wood))

        # Axle
        block = rm.blockstate('afc:wood/axle/%s' % wood, 'tfc:block/empty')
        block.with_lang(lang('%s axle', wood))
        block.with_block_model({'wood': 'afc:block/wood/sheet/%s' % wood}, 'tfc:block/axle')
        rm.item_model('afc:wood/axle/%s' % wood, no_textures=True, parent='afc:block/wood/axle/%s' % wood)

        # Bladed Axle
        block = rm.blockstate('afc:wood/bladed_axle/%s' % wood, 'tfc:block/empty')
        block.with_lang(lang('%s bladed axle', wood))
        block.with_block_model({'wood': 'afc:block/wood/sheet/%s' % wood}, 'tfc:block/bladed_axle')
        rm.item_model('afc:wood/bladed_axle/%s' % wood, no_textures=True, parent='afc:block/wood/bladed_axle/%s' % wood)

        # Encased Axle
        block = rm.blockstate(('wood', 'encased_axle', wood), variants={
            'axis=x': {'model': 'afc:block/wood/encased_axle/%s' % wood, 'x': 90, 'y': 90},
            'axis=y': {'model': 'afc:block/wood/encased_axle/%s' % wood},
            'axis=z': {'model': 'afc:block/wood/encased_axle/%s' % wood, 'x': 90},
        })
        block.with_lang(lang('%s encased axle', wood))
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

        # Water Wheel
        block = rm.blockstate('afc:wood/water_wheel/%s' % wood)
        block.with_block_model({'particle': 'afc:block/wood/planks/%s' % wood}, parent=None)
        block.with_lang(lang('%s water wheel', wood))
        rm.item_model('afc:wood/water_wheel/%s' % wood, 'afc:item/wood/water_wheel/%s' % wood)


        # Lang
        for variant in ('slab', 'stairs'):
            rm.lang('block.afc.wood.planks.' + wood + '_' + variant, lang('%s %s', wood, variant))
        for variant in ('sapling', 'leaves'):
            rm.lang('block.afc.wood.' + variant + '.' + wood, lang('%s %s', default_species, variant))


    for wood in UNIQUE_LOGS.keys():
        # Twigs
        block = rm.blockstate(('wood', 'twig', wood), variants={"": four_ways('afc:block/wood/twig/%s' % wood)}, use_default_model=False)
        block.with_lang(lang('%s twig', wood))
        rm.item_model('wood/twig/%s' % wood, 'afc:item/wood/twig/%s' % wood, parent='item/handheld_rod')
        prefix = 'afc'
        if wood == 'rainbow_eucalyptus':
            planks = 'eucalyptus'
        elif wood == 'black_oak':
            planks = 'oak'
            prefix = 'tfc'
        elif wood == 'poplar':
            planks = 'aspen'
            prefix = 'tfc'
        elif wood == 'redcedar':
            planks = 'cypress'
        elif wood == 'kauri':
            planks = 'araucaria'
        elif wood == 'rubber_fig':
            planks = 'fig'
        elif wood == 'gum_arabic':
            planks = 'acacia'
            prefix = 'tfc'
        else:
            planks = wood

        block.with_block_model({'side': 'afc:block/wood/log/%s' % wood, 'top': 'afc:block/wood/log_top/%s' % wood}, parent='tfc:block/groundcover/twig')
        for variant in ('log', 'wood'):
            block = rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': 'afc:block/wood/%s/%s' % (variant, wood)},
                'axis=z': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90},
                'axis=x': {'model': 'afc:block/wood/%s/%s' % (variant, wood), 'x': 90, 'y': 90}
            }, use_default_model=False)

            end = 'afc:block/wood/%s/%s' % (variant.replace('log', 'log_top').replace('wood', 'log'), wood)
            side = '%s:block/wood/%s/%s' % ('afc', variant.replace('wood', 'log'), wood) # Always use AFC for side textures
            block.with_block_model({'end': end, 'side': side}, parent='block/cube_column')
            rm.item_model(('wood', variant, wood), 'afc:item/wood/%s/%s' % (variant, wood))
            block.with_lang(lang('%s %s', wood, variant))

        # Log Fences
        block = rm.blockstate_multipart('wood/log_fence/%s' % wood, *block_states.fence_multipart('afc:block/wood/log_fence/%s_post' % wood, 'afc:block/wood/log_fence/%s_side' % wood))
        block.with_lang(lang('%s log fence', wood))
        rm.block_model('wood/log_fence/%s_post' % wood, textures={'texture': 'afc:block/wood/log/' + wood}, parent='block/fence_post')
        rm.block_model('wood/log_fence/%s_side' % wood, textures={'texture': '%s:block/wood/planks/%s' % (prefix, planks)}, parent='block/fence_side')
        rm.block_model('wood/log_fence/%s_inventory' % wood, textures={
            'log': 'afc:block/wood/log/' + wood,
            'planks': '%s:block/wood/planks/%s' % (prefix, planks)
        }, parent='tfc:block/wood/log_fence/inventory')
        rm.item_model('wood/log_fence/%s' % wood, parent='afc:block/wood/log_fence/%s_inventory' % wood, no_textures=True)

        log_fence_namespace = 'afc:wood/planks/' + wood + '_log_fence'
        rm.blockstate_multipart(log_fence_namespace, *block_states.fence_multipart('%s:block/wood/planks/%s' % (prefix, planks) + '_log_fence_post', '%s:block/wood/planks/%s' % (prefix, planks) + '_log_fence_side'))
        rm.block_model(log_fence_namespace + '_post', textures={'texture': 'afc:block/wood/log/' + wood}, parent='block/fence_post')
        rm.block_model(log_fence_namespace + '_side', textures={'texture': '%s:block/wood/planks/%s' % (prefix, planks)}, parent='block/fence_side')
        rm.block_model(log_fence_namespace + '_inventory', textures={'log': 'afc:block/wood/log/' + wood, 'planks': '%s:block/wood/planks/%s' % (prefix, planks)}, parent='tfc:block/log_fence_inventory')
        rm.item_model('%s:wood/planks/%s' % (prefix, planks) + '_log_fence', parent='afc:block/wood/planks/' + wood + '_log_fence_inventory', no_textures=True)


    for wood in ANCIENT_LOGS.keys():
        base_wood = wood.replace('ancient_', '')
        if base_wood in TFC_WOODS.keys():
            mod_id = 'tfc'
        else:
            mod_id = 'afc'
        for variant in ('log', 'wood'):
            rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': '%s:block/wood/%s/%s' % (mod_id, variant, base_wood)},
                'axis=z': {'model': '%s:block/wood/%s/%s' % (mod_id, variant, base_wood), 'x': 90},
                'axis=x': {'model': '%s:block/wood/%s/%s' % (mod_id, variant, base_wood), 'x': 90, 'y': 90}
            }, use_default_model=False).with_lang(lang('%s %s', wood, variant))
            wood_name = wood.replace("ancient_", "")
            rm.item_model(('wood', variant, wood), '%s:item/wood/%s/%s' % (mod_id, variant, wood_name))

    rm.blockstate('light', variants={'level=%s' % i: {'model': 'minecraft:block/light_%s' % i if i >= 10 else 'minecraft:block/light_0%s' % i} for i in range(0, 15 + 1)}).with_lang(lang('Light'))
    rm.item_model('light', no_textures=True, parent='minecraft:item/light')

    # TODO: See about re-enabling someday
    # rm.atlas('minecraft:blocks',
    #          atlases.palette(
    #              key='afc:color_palettes/wood/planks/palette',
    #              textures=['tfc:block/wood/planks/%s' % v for v in ('bookshelf_top', 'bookshelf_side')],
    #              permutations=dict((wood, 'afc:color_palettes/wood/planks/%s' % wood) for wood in AFC_WOODS.keys())
    #          ),
    #          atlases.palette(
    #              key='afc:color_palettes/wood/planks/palette',
    #              textures=['tfc:item/wood/%s' % v for v in ('twig', 'lumber', 'chest_minecart_cover', 'stripped_log', 'sign_head', 'hanging_sign_head', 'water_wheel')],
    #              permutations=dict((wood, 'afc:color_palettes/wood/plank_items/%s' % wood) for wood in AFC_WOODS.keys())
    #          ),
    #          atlases.palette(
    #              key='afc:color_palettes/wood/planks/palette',
    #              textures=['tfc:item/wood/boat'],
    #              permutations=dict((wood, 'afc:color_palettes/wood/plank_items/%s' % wood) for wood in AFC_WOODS.keys())
    #          )
    # )



def flower_pot_cross(rm: ResourceManager, lang_name: str, name: str, model: str, texture: str):
    rm.blockstate(name, model='afc:block/%s' % model).with_lang(lang('potted %s', lang_name))
    rm.block_model(model, parent='minecraft:block/flower_pot_cross', textures={'plant': texture, 'dirt': 'tfc:block/dirt/entisol'})

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

def override(model: str, name: str, value: float = 1.0):
    return {'predicate': {name: value}, 'model': model}

def blank_blockstate(self, name_parts: ResourceIdentifier, text: Json) -> BlockContext:
    """
    Creates a blockstate file
    :param name_parts: the resource location, including path elements.
    :param text: the contents of the file
    """
    res = utils.resource_location(self.domain, name_parts)
    self.write(('assets', res.domain, 'blockstates', res.path), text)
    return BlockContext(self, res)

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