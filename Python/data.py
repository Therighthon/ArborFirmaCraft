#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

from enum import Enum, auto

from mcresources import ResourceManager, utils, loot_tables
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


class Category(Enum):
    fruit = auto()
    vegetable = auto()
    grain = auto()
    bread = auto()
    dairy = auto()
    meat = auto()
    cooked_meat = auto()
    other = auto()


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





    # for plant in PLANTS.keys():
    #     block_and_item_tag(rm, 'plants', 'tfc:plant/%s' % plant)
    # for plant in UNIQUE_PLANTS:
    #     rm.block_tag('plants', 'tfc:plant/%s' % plant)
    #     if 'plant' not in plant:
    #         rm.item_tag('plants', 'tfc:plant/%s' % plant)

    # ==========
    # BLOCK TAGS
    # ==========

    rm.block_tag('logs_that_log', '#minecraft:logs')
    rm.block_tag('scraping_surface', '#minecraft:logs')
    # for plant, data in PLANTS.items():  # Plants
    #     block_and_item_tag(rm, 'plants', 'tfc:plant/%s' % plant)
    #     if data.type in ('standard', 'short_grass', 'dry', 'grass_water', 'water'):
    #         rm.block_tag('single_block_replaceable', 'tfc:plant/%s' % plant)
    #     if data.type in ('standard', 'tall_plant', 'short_grass', 'tall_grass', 'creeping'):
    #         rm.block_tag('can_be_snow_piled', 'tfc:plant/%s' % plant)
    #     if data.type in ('emergent', 'emergent_fresh', 'floating', 'floating_fresh', 'creeping'):
    #         rm.block_tag('can_be_ice_piled', 'tfc:plant/%s' % plant)

    rm.block_tag('minecraft:mineable/hoe', '#tfc:mineable_with_sharp_tool')
    rm.block_tag('tfc:mineable_with_knife', '#tfc:mineable_with_sharp_tool')
    rm.block_tag('tfc:mineable_with_scythe', '#tfc:mineable_with_sharp_tool')
    rm.block_tag('tfc:mineable_with_hammer', '#tfc:mineable_with_blunt_tool')
    rm.item_tag('tfc:sharp_tools', '#tfc:hoes', '#tfc:knives', '#tfc:scythes')

    rm.block_tag('forge:needs_wood_tool')
    rm.block_tag('forge:needs_netherite_tool')


    rm.block_tag('minecraft:mineable/axe', *[
        *['afc:wood/%s/%s' % (variant, wood) for variant in ('log', 'stripped_log', 'wood', 'stripped_wood', 'planks', 'twig', 'vertical_support', 'horizontal_support', 'sluice', 'chest', 'trapped_chest') for wood in WOODS.keys()],
        *['afc:wood/planks/%s_%s' % (wood, variant) for variant in ('bookshelf', 'door', 'trapdoor', 'fence', 'log_fence', 'fence_gate', 'button', 'pressure_plate', 'slab', 'stairs', 'tool_rack', 'workbench', 'sign') for wood in WOODS.keys()]
    ])
    rm.block_tag('tfc:mineable_with_sharp_tool', *[
        *['afc:wood/%s/%s' % (variant, wood) for variant in ('leaves', 'sapling', 'fallen_leaves') for wood in WOODS.keys()]
    ])
    rm.block_tag('tfc:mineable_with_blunt_tool',
                 *['afc:wood/%s/%s' % (variant, wood) for variant in ('log', 'stripped_log', 'wood', 'stripped_wood') for wood in WOODS.keys()]
                 )
    rm.flush()

    # ==========
    # FLUID TAGS
    # ==========

    # Water
    # Any = Includes block waters like flowing, river water. These should never be present in fluid tanks
    # Fresh = only fresh
    # Infinite = infinite waters (fresh + salt)
    # Any = all waters (fresh, salt, spring)

    rm.fluid_tag('any_fresh_water', 'minecraft:water', 'minecraft:flowing_water', 'tfc:river_water')
    rm.fluid_tag('any_infinite_water', '#tfc:any_fresh_water', 'tfc:salt_water', 'tfc:flowing_salt_water')
    rm.fluid_tag('any_water', '#tfc:any_infinite_water', 'tfc:spring_water', 'tfc:flowing_spring_water')

    rm.fluid_tag('fresh_water', 'minecraft:water')
    rm.fluid_tag('infinite_water', '#tfc:fresh_water', 'tfc:salt_water')
    rm.fluid_tag('water', '#tfc:infinite_water', 'tfc:spring_water')

    # Categories
    # None of these use the word 'Any' and such should not include flowing fluids
    rm.fluid_tag('milks', 'minecraft:milk')
    rm.fluid_tag('alcohols', *ALCOHOLS)
    rm.fluid_tag('dyes', *['tfc:%s_dye' % dye for dye in COLORS])
    rm.fluid_tag('ingredients', *SIMPLE_FLUIDS, '#tfc:drinkables', '#tfc:dyes')
    rm.fluid_tag('scribing_ink', 'tfc:black_dye')

    rm.fluid_tag('drinkables', '#tfc:infinite_water', '#tfc:alcohols', '#tfc:milks')
    rm.fluid_tag('any_drinkables', '#tfc:drinkables', '#tfc:any_infinite_water')

    rm.fluid_tag('molten_metals', *['tfc:metal/%s' % metal for metal in METALS.keys()])

    # Applications
    rm.fluid_tag('hydrating', '#tfc:any_fresh_water')
    rm.fluid_tag('mixable', '#minecraft:water')

    rm.fluid_tag('usable_in_pot', '#tfc:ingredients')
    rm.fluid_tag('usable_in_jug', '#tfc:drinkables')
    rm.fluid_tag('usable_in_wooden_bucket', '#tfc:ingredients')
    rm.fluid_tag('usable_in_red_steel_bucket', '#tfc:ingredients')
    rm.fluid_tag('usable_in_blue_steel_bucket', 'minecraft:lava', '#tfc:molten_metals')
    rm.fluid_tag('usable_in_barrel', '#tfc:ingredients')
    rm.fluid_tag('usable_in_sluice', '#tfc:any_infinite_water')
    rm.fluid_tag('usable_in_ingot_mold', '#tfc:molten_metals')
    rm.fluid_tag('usable_in_tool_head_mold', 'tfc:metal/copper', 'tfc:metal/bismuth_bronze', 'tfc:metal/black_bronze', 'tfc:metal/bronze')

    # Required in order for fluids to have fluid-like properties
    rm.fluid_tag('minecraft:lava', '#tfc:molten_metals')
    rm.fluid_tag('minecraft:water', *['#tfc:%s' % fluid_type for fluid_type in (
        'salt_water',
        'spring_water',
        *SIMPLE_FLUIDS,
        *ALCOHOLS,
        *['%s_dye' % c for c in COLORS]
    )], 'tfc:river_water')


    # Item Sizes

    # item_size(rm, 'logs', '#minecraft:logs', Size.very_large, Weight.medium)
    # item_size(rm, 'tool_racks', '#tfc:tool_racks', Size.large, Weight.very_heavy)
    # item_size(rm, 'chests', '#forge:chests', Size.large, Weight.light)
    # item_size(rm, 'slabs', '#minecraft:slabs', Size.small, Weight.very_light)
    # item_size(rm, 'vessels', '#tfc:vessels', Size.normal, Weight.very_heavy)
    # item_size(rm, 'large_vessels', '#tfc:large_vessels', Size.huge, Weight.heavy)
    # item_size(rm, 'doors', '#minecraft:doors', Size.very_large, Weight.heavy)
    # item_size(rm, 'mortar', '#tfc:mortar', Size.tiny, Weight.very_light)
    # item_size(rm, 'stick_bunch', 'tfc:stick_bunch', Size.normal, Weight.light)
    # item_size(rm, 'stick_bundle', 'tfc:stick_bundle', Size.very_large, Weight.medium)
    # item_size(rm, 'jute_fiber', 'tfc:jute_fiber', Size.small, Weight.very_light)
    # item_size(rm, 'burlap_cloth', 'tfc:burlap_cloth', Size.small, Weight.very_light)
    # item_size(rm, 'straw', 'tfc:straw', Size.small, Weight.very_light)
    # item_size(rm, 'wool', 'tfc:wool', Size.small, Weight.light)
    # item_size(rm, 'wool_cloth', 'tfc:wool_cloth', Size.small, Weight.light)
    # item_size(rm, 'silk_cloth', 'tfc:silk_cloth', Size.small, Weight.light)
    # item_size(rm, 'alabaster_brick', 'tfc:alabaster_brick', Size.small, Weight.light)
    # item_size(rm, 'glue', 'tfc:glue', Size.tiny, Weight.light)
    # item_size(rm, 'brass_mechanisms', 'tfc:brass_mechanisms', Size.normal, Weight.light)
    # item_size(rm, 'wrought_iron_grill', 'tfc:wrought_iron_grill', Size.large, Weight.heavy)
    # item_size(rm, 'dyes', '#tfc:dyes', Size.tiny, Weight.light)
    # item_size(rm, 'foods', '#tfc:foods', Size.small, Weight.light)
    # item_size(rm, 'plants', '#tfc:plants', Size.tiny, Weight.very_light)
    # # item_size(rm, 'jute', 'tfc:jute', Size.small, Weight.very_light)
    # # item_size(rm, 'bloomery', 'tfc:bloomery', Size.large, Weight.very_heavy)
    # item_size(rm, 'sluice', '#tfc:sluices', Size.very_large, Weight.very_heavy)
    # # item_size(rm, 'lamps', '#tfc:lamps', Size.normal, Weight.medium)
    # item_size(rm, 'signs', '#minecraft:signs', Size.very_small, Weight.heavy)
    # # item_size(rm, 'soups', '#tfc:soups', Size.very_small, Weight.medium)
    # # item_size(rm, 'salads', '#tfc:salads', Size.very_small, Weight.medium)
    # # item_size(rm, 'buckets', '#tfc:buckets', Size.large, Weight.very_heavy)
    # # item_size(rm, 'anvils', '#tfc:anvils', Size.huge, Weight.very_heavy)
    # item_size(rm, 'minecarts', '#tfc:minecarts', Size.very_large, Weight.heavy)
    # item_size(rm, 'boats', '#minecraft:boats', Size.very_large, Weight.heavy)
    # item_size(rm, 'looms', '#tfc:looms', Size.large, Weight.very_heavy)
    # item_size(rm, 'ingots', '#forge:ingots', Size.large, Weight.medium)
    # item_size(rm, 'double_ingots', '#forge:double_ingots', Size.large, Weight.heavy)
    # item_size(rm, 'sheets', '#forge:sheets', Size.large, Weight.medium)
    # item_size(rm, 'double_sheets', '#forge:double_sheets', Size.large, Weight.heavy)
    # item_size(rm, 'rods', '#forge:rods', Size.normal, Weight.light)
    # item_size(rm, 'tuyeres', '#tfc:tuyeres', Size.large, Weight.heavy)
    # item_size(rm, 'trapdoors', '#tfc:trapdoors', Size.large, Weight.heavy)
    # item_size(rm, 'small_tools', ['#tfc:chisels', '#tfc:knives', '#tfc:shears'], Size.large, Weight.medium)
    # item_size(rm, 'large_tools', ['#forge:fishing_rods', '#tfc:pickaxes', '#tfc:propicks', '#tfc:axes', '#tfc:shovels', '#tfc:hoes', '#tfc:hammers', '#tfc:saws', '#tfc:javelins', '#tfc:swords', '#tfc:maces', '#tfc:scythes'], Size.very_large, Weight.very_heavy)
    # item_size(rm, 'ore_pieces', '#tfc:ore_pieces', Size.small, Weight.medium)
    # item_size(rm, 'small_ore_pieces', '#tfc:small_ore_pieces', Size.small, Weight.light)


# Climate Ranges

    # for berry, data in BERRIES.items():
    #     climate_range(rm, 'plant/%s_bush' % berry, temperature=(data.min_temp, data.max_temp, 0), hydration=(hydration_from_rainfall(data.min_rain), 100, 0))
    #
    # for fruit, data in FRUITS.items():
    #     climate_range(rm, 'plant/%s_tree' % fruit, hydration=(hydration_from_rainfall(data.min_rain), 100, 0), temperature=(data.min_temp - 7, data.max_temp + 7, 0))
    #
    # for crop, data in CROPS.items():
    #     climate_range(rm, 'crop/%s' % crop, hydration=(data.min_hydration, data.max_hydration, 0), temperature=(data.min_temp, data.max_temp, 5))

    # Fertilizer
    # rm.data(('tfc', 'fertilizers', 'sylvite'), fertilizer('tfc:powder/sylvite', k=0.5))
    # rm.data(('tfc', 'fertilizers', 'wood_ash'), fertilizer('tfc:powder/wood_ash', p=0.1, k=0.3))
    # rm.data(('tfc', 'fertilizers', 'guano'), fertilizer('tfc:groundcover/guano', n=0.8, p=0.5, k=0.1))
    # rm.data(('tfc', 'fertilizers', 'saltpeter'), fertilizer('tfc:powder/saltpeter', n=0.1, k=0.4))
    # rm.data(('tfc', 'fertilizers', 'bone_meal'), fertilizer('minecraft:bone_meal', p=0.1))
    # rm.data(('tfc', 'fertilizers', 'compost'), fertilizer('tfc:compost', n=0.4, p=0.2, k=0.4))
    # rm.data(('tfc', 'fertilizers', 'pure_nitrogen'), fertilizer('tfc:pure_nitrogen', n=0.1))
    # rm.data(('tfc', 'fertilizers', 'pure_phosphorus'), fertilizer('tfc:pure_phosphorus', p=0.1))
    # rm.data(('tfc', 'fertilizers', 'pure_potassium'), fertilizer('tfc:pure_potassium', k=0.1))




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


def fauna(chance: int = None, distance_below_sea_level: int = None, climate: Dict[str, Any] = None, solid_ground: bool = None, max_brightness: int = None) -> Dict[str, Any]:
    return {
        'chance': chance,
        'distance_below_sea_level': distance_below_sea_level,
        'climate': climate,
        'solid_ground': solid_ground,
        'max_brightness': max_brightness
    }


# def food_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, category: Category, hunger: int, saturation: float, water: int, decay: float, fruit: Optional[float] = None, veg: Optional[float] = None, protein: Optional[float] = None, grain: Optional[float] = None, dairy: Optional[float] = None):
#     rm.item_tag('tfc:foods', ingredient)
#     rm.data(('tfc', 'food_items', name_parts), {
#         'ingredient': utils.ingredient(ingredient),
#         'hunger': hunger,
#         'saturation': saturation,
#         'water': water if water != 0 else None,
#         'decay_modifier': decay,
#         'fruit': fruit,
#         'vegetables': veg,
#         'protein': protein,
#         'grain': grain,
#         'dairy': dairy
#     })
#     rm.item_tag('foods', ingredient)
#     if category in (Category.fruit, Category.vegetable):
#         rm.item_tag('foods/%ss' % category.name.lower(), ingredient)
#     if category in (Category.meat, Category.cooked_meat):
#         rm.item_tag('foods/meats', ingredient)
#         if category == Category.cooked_meat:
#             rm.item_tag('foods/cooked_meats', ingredient)
#         else:
#             rm.item_tag('foods/raw_meats', ingredient)
#     if category == Category.dairy:
#         rm.item_tag('foods/dairy', ingredient)


# def drinkable(rm: ResourceManager, name_parts: utils.ResourceIdentifier, fluid: utils.Json, thirst: Optional[int] = None, intoxication: Optional[int] = None, effects: Optional[utils.Json] = None, food: Optional[utils.Json] = None):
#     rm.data(('tfc', 'drinkables', name_parts), {
#         'ingredient': fluid_ingredient(fluid),
#         'thirst': thirst,
#         'intoxication': intoxication,
#         'effects': effects,
#         'food': food
#     })


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
