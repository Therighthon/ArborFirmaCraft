#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

from enum import Enum, auto

from mcresources import ResourceManager, utils
from mcresources.type_definitions import ResourceIdentifier

from constants import *


class Size(Enum):
    tiny = auto()
    very_small = auto()
    small = auto()
    normal = auto()
    large = auto()
    very_large = auto()
    huge = auto()


class Weight(Enum):
    very_light = auto()
    light = auto()
    medium = auto()
    heavy = auto()
    very_heavy = auto()


def generate(rm: ResourceManager):
    # === Supports ===

    rm.data(('tfc', 'supports', 'horizontal_support_beam'), {
        'ingredient': ['afc:wood/horizontal_support/%s' % wood for wood in WOODS],
        'support_up': 2,
        'support_down': 2,
        'support_horizontal': 4
    })

    # Fuels

    for wood, wood_data in WOODS.items():
        fuel_item(rm, wood + '_log', ['afc:wood/log/' + wood, 'afc:wood/wood/' + wood, 'afc:wood/stripped_wood/' + wood, 'afc:wood/stripped_log/' + wood], wood_data.duration, wood_data.temp, 0.6 if wood == 'pine' else 0.95)

    # New
    for wood, wood_data in UNIQUE_LOGS.items():
        fuel_item(rm, wood + '_log', ['afc:wood/log/' + wood, 'afc:wood/wood/' + wood], wood_data.duration, wood_data.temp, 0.6 if wood == 'pine' else 0.95)

    rm.item_tag('axles', *['afc:wood/axle/%s' % w for w in WOODS], *['afc:wood/encased_axle/%s' % w for w in WOODS])
    rm.item_tag('gear_boxes', *['afc:wood/gear_box/%s' % w for w in WOODS])
    rm.item_tag('clutches', *['afc:wood/clutch/%s' % w for w in WOODS])
    rm.item_tag('water_wheels', *['afc:wood/water_wheel/%s' % w for w in WOODS])

    for wood in WOODS.keys():
        def item(_variant: str) -> str:
            return 'afc:wood/%s/%s' % (_variant, wood)

        def plank(_variant: str) -> str:
            return 'afc:wood/planks/%s_%s' % (wood, _variant)

        def ancient(_variant: str) -> str:
            return 'afc:wood/%s/ancient_%s' % (_variant, wood)

        print(wood)

        rm.item_tag('tfc:lumber', item('lumber'))
        block_and_item_tag(rm, 'tfc:twigs', item('twig'))
        block_and_item_tag(rm, 'tfc:looms', plank('loom'))
        block_and_item_tag(rm, 'tfc:sluices', item('sluice'))
        block_and_item_tag(rm, 'tfc:workbenches', plank('workbench'))
        block_and_item_tag(rm, 'tfc:bookshelves', plank('bookshelf'))
        block_and_item_tag(rm, 'tfc:lecterns', item('lectern'))
        block_and_item_tag(rm, 'tfc:barrels', item('barrel'))
        block_and_item_tag(rm, 'tfc:fallen_leaves', item('fallen_leaves'))
        block_and_item_tag(rm, 'tfc:tool_racks', plank('tool_rack'))
        rm.block_and_item_tag('sewing_tables', item('sewing_table'))
        rm.block_and_item_tag('jar_shelves', item('jar_shelf'))

        rm.item_tag('minecraft:boats', item('boat'))
        block_and_item_tag(rm, 'minecraft:wooden_buttons', plank('button'))
        block_and_item_tag(rm, 'minecraft:wooden_fences', plank('fence'), plank('log_fence'))
        block_and_item_tag(rm, 'minecraft:wooden_slabs', plank('slab'))
        block_and_item_tag(rm, 'minecraft:wooden_stairs', plank('stairs'))
        block_and_item_tag(rm, 'minecraft:wooden_doors', plank('door'))
        block_and_item_tag(rm, 'minecraft:wooden_trapdoors', plank('trapdoor'))
        block_and_item_tag(rm, 'minecraft:wooden_pressure_plates', plank('pressure_plate'))
        block_and_item_tag(rm, 'minecraft:logs', '#afc:%s_logs' % wood)
        block_and_item_tag(rm, 'minecraft:leaves', item('leaves'))
        block_and_item_tag(rm, 'minecraft:planks', item('planks'))

        block_and_item_tag(rm, 'forge:chests/wooden', item('chest'), item('trapped_chest'))
        block_and_item_tag(rm, 'forge:fence_gates/wooden', plank('fence_gate'))
        block_and_item_tag(rm, 'forge:stripped_logs', item('stripped_log'), item('stripped_wood'))
        if wood in 'cypress':
            block_and_item_tag(rm, '%s_logs' % wood, item('log'), item('wood'), item('stripped_log'), item('stripped_wood'), ancient('log'), ancient('wood'), '#afc:redcedar_logs')
        elif wood in 'eucalyptus':
            block_and_item_tag(rm, '%s_logs' % wood, item('log'), item('wood'), item('stripped_log'), item('stripped_wood'), ancient('log'), ancient('wood'), '#afc:rainbow_eucalyptus_logs')
        elif wood in 'fig':
            block_and_item_tag(rm, '%s_logs' % wood, item('log'), item('wood'), item('stripped_log'), item('stripped_wood'), ancient('log'), ancient('wood'), '#afc:rubber_fig_logs')
        else:
            block_and_item_tag(rm, '%s_logs' % wood, item('log'), item('wood'), item('stripped_log'), item('stripped_wood'), ancient('log'), ancient('wood'))

        rm.block_tag('lit_by_dropped_torch', item('fallen_leaves'))
        rm.block_tag('converts_to_humus', item('fallen_leaves'))

        if wood in TANNIN_WOOD_TYPES:
            rm.item_tag('makes_tannin', item('log'), item('wood'))

    # New
    for wood in TREE_VARIANTS.keys():
        def item(_variant: str) -> str:
            return 'afc:wood/%s/%s' % (_variant, wood)

        block_and_item_tag(rm, 'minecraft:leaves', item('leaves'))
        block_and_item_tag(rm, 'tfc:fallen_leaves', item('fallen_leaves'))



    # New
    for wood in UNIQUE_LOGS.keys():
        def item(_variant: str) -> str:
            return 'afc:wood/%s/%s' % (_variant, wood)
        block_and_item_tag(rm, 'minecraft:logs', '#afc:%s_logs' % wood)
        block_and_item_tag(rm, 'tfc:twigs', item('twig'))





    # for plant in PLANTS.keys():
    #     block_and_item_tag(rm, 'plants', 'tfc:plant/%s' % plant)
    # for plant in UNIQUE_PLANTS:
    #     rm.block_tag('plants', 'tfc:plant/%s' % plant)
    #     if 'plant' not in plant:
    #         rm.item_tag('plants', 'tfc:plant/%s' % plant)

    # ==========
    # BLOCK TAGS
    # ==========

    # TODO: Migrate to Java datagen
    rm.block_tag('logs_that_log', '#minecraft:logs')
    rm.block_tag('scraping_surface', '#minecraft:logs')

    rm.block_tag('minecraft:mineable/hoe', '#tfc:mineable_with_sharp_tool')
    rm.block_tag('tfc:mineable_with_knife', '#tfc:mineable_with_sharp_tool')
    rm.block_tag('tfc:mineable_with_scythe', '#tfc:mineable_with_sharp_tool')
    rm.block_tag('tfc:mineable_with_hammer', '#tfc:mineable_with_blunt_tool')
    rm.item_tag('tfc:sharp_tools', '#tfc:hoes', '#tfc:knives', '#tfc:scythes')

    rm.block_tag('forge:needs_wood_tool')
    rm.block_tag('forge:needs_netherite_tool')


    rm.block_tag('minecraft:mineable/axe', *[
        *['afc:wood/%s/%s' % (variant, wood) for variant in ('log', 'stripped_log', 'wood', 'stripped_wood', 'planks', 'twig', 'vertical_support', 'horizontal_support', 'sluice', 'chest', 'trapped_chest', 'barrel', 'lectern', 'scribing_table', 'sewing_table', 'jar_shelf', 'axle', 'encased_axle', 'bladed_axle', 'clutch', 'gear_box', 'windmill', 'water_wheel') for wood in WOODS.keys()],
        *['afc:wood/planks/%s_%s' % (wood, variant) for variant in ('bookshelf', 'door', 'trapdoor', 'fence', 'log_fence', 'fence_gate', 'button', 'pressure_plate', 'slab', 'stairs', 'tool_rack', 'workbench', 'sign') for wood in WOODS.keys()]
    ])
    rm.block_tag('tfc:mineable_with_sharp_tool', *[
        *['afc:wood/%s/%s' % (variant, wood) for variant in ('leaves', 'sapling', 'fallen_leaves') for wood in WOODS.keys()]
    ])
    rm.block_tag('tfc:mineable_with_blunt_tool',
                 *['afc:wood/%s/%s' % (variant, wood) for variant in ('log', 'stripped_log', 'wood', 'stripped_wood') for wood in WOODS.keys()]
                 )
    rm.flush()

def climate_config(min_temp: Optional[float] = None, max_temp: Optional[float] = None, min_rain: Optional[float] = None, max_rain: Optional[float] = None, needs_forest: Optional[bool] = False, fuzzy: Optional[bool] = None, min_forest: Optional[str] = None, max_forest: Optional[str] = None) -> Dict[str, Any]:
    return {
        'min_temperature': min_temp,
        'max_temperature': max_temp,
        'min_rainfall': min_rain,
        'max_rainfall': max_rain,
        'min_forest': 'normal' if needs_forest else min_forest,
        'max_forest': max_forest,
        'fuzzy': fuzzy
    }


def item_size(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, size: Size, weight: Weight):
    rm.data(('tfc', 'item_sizes', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'size': size.name,
        'weight': weight.name
    })


def item_heat(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, heat_capacity: float, melt_temperature: Optional[float] = None, mb: Optional[int] = None):
    if melt_temperature is not None:
        forging_temperature = round(melt_temperature * 0.6)
        welding_temperature = round(melt_temperature * 0.8)
    else:
        forging_temperature = welding_temperature = None
    if mb is not None:
        # Interpret heat capacity as a specific heat capacity - so we need to scale by the mB present. Baseline is 100 mB (an ingot)
        # Higher mB = higher heat capacity = heats and cools slower = consumes proportionally more fuel
        heat_capacity = round(10 * heat_capacity * mb) / 1000
    rm.data(('tfc', 'item_heats', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'heat_capacity': heat_capacity,
        'forging_temperature': forging_temperature,
        'welding_temperature': welding_temperature
    })


def fuel_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, duration: int, temperature: float, purity: float = None):
    rm.data(('tfc', 'fuels', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'duration': duration,
        'temperature': temperature,
        'purity': purity,
    })


def climate_range(rm: ResourceManager, name_parts: utils.ResourceIdentifier, hydration: Tuple[int, int, int] = None, temperature: Tuple[float, float, float] = None):
    data = {}
    if hydration is not None:
        data.update({'min_hydration': hydration[0], 'max_hydration': hydration[1], 'hydration_wiggle_range': hydration[2]})
    if temperature is not None:
        data.update({'min_temperature': temperature[0], 'max_temperature': temperature[1], 'temperature_wiggle_range': temperature[2]})
    rm.data(('tfc', 'climate_ranges', name_parts), data)


def hydration_from_rainfall(rainfall: float) -> int:
    return int(rainfall) * 60 // 500


def block_and_item_tag(rm: ResourceManager, name_parts: utils.ResourceIdentifier, *values: utils.ResourceIdentifier, replace: bool = False):
    rm.block_tag(name_parts, *values, replace=replace)
    rm.item_tag(name_parts, *values, replace=replace)
