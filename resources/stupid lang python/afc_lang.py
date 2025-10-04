import os

leaves = open("leaves.txt", 'r').read().split("\n")
unique_logs = open("unique_logs.txt", 'r').read().split("\n")
woods = open("woods.txt", 'r').read().split("\n")
tfc_woods = open("tfc_woods.txt", 'r').read().split("\n")
sign_metals = open("hanging_sign_metals.txt", 'r').read().split("\n")

wood_items = open("wood_items.txt", 'r').read().split("\n")
wood_blocks = open("wood_blocks.txt", 'r').read().split("\n")
wood_plank_items = open("wood_plank_items.txt", 'r').read().split("\n")
leaf_items = ["leaves", "sapling", "potted_sapling", "fallen_leaves"]
unique_log_items = ["log", "wood", "twig"]


string = """{
"""

for wood in woods:
    for item in wood_items:
        string = string + """  "item.afc.wood.%s.%s": "%s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

    for item in wood_blocks:
        string = string + """  "block.afc.wood.%s.%s": "%s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

    for item in wood_plank_items:
        string = string + """  "block.afc.wood.planks.%s_%s": "%s %s",
""" % (wood, item, wood.replace("_", " ").title(), item.replace("_", " ").title())


for leaf in leaves:
    for item in leaf_items:
        string = string + """  "block.afc.wood.%s.%s": "%s %s",
""" % (item, leaf, leaf.replace("_", " ").title(), item.replace("_", " ").title())

for log in unique_logs:
    for item in unique_log_items:
        string = string + """  "block.afc.wood.%s.%s": "%s %s",
""" % (item, log, log.replace("_", " ").title(), item.replace("_", " ").title())

for wood in woods:
    for item in ["log", "wood"]:
        string = string + """  "block.afc.wood.%s.ancient_%s": "Ancient %s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())
    for metal in sign_metals:
        string = string + """  "block.afc.wood.planks.hanging_sign.%s.%s": "%s %s Hanging Sign",
""" % (metal, wood, metal.replace("_", " ").title(), wood.replace("_", " ").title())

    string = string + """  "entity.afc.boat.%s": "%s Boat",
""" % (wood, wood.replace("_", " ").title())

for wood in ["black_oak", "gum_arabic", "rainbow_eucalyptus", "redcedar"]:
    for item in ["log", "wood"]:
        string = string + """  "block.afc.wood.%s.ancient_%s": "Ancient %s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

for wood in tfc_woods:
    for item in ["log", "wood"]:
        string = string + """  "block.afc.wood.%s.ancient_%s": "Ancient %s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

string = string + """  "block.afc.wood.%s.ancient_%s": "Ancient %s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

string = string + """
  "block.afc.wood.sapling.ipe": "Yellow Ipe Sapling",
  "block.afc.wood.potted_sapling.ipe": "Yellow Ipe Potted Sapling",
  "block.afc.wood.leaves.ipe": "Yellow Ipe Leaves",
  "block.afc.wood.fallen_leaves.ipe": "Yellow Ipe Fallen Leaves",
  "block.afc.wood.sapling.ironwood": "King Ironwood Sapling",
  "block.afc.wood.potted_sapling.ironwood": "King Ironwood Potted Sapling",
  "block.afc.wood.fallen_leaves.ironwood": "King Ironwood Fallen Leaves",
  "block.afc.wood.leaves.ironwood": "King Ironwood Leaves",
  "block.afc.wood.sapling.cypress": "Slender Cypress Sapling",
  "block.afc.wood.potted_sapling.cypress": "Slender Cypress Potted Sapling",
  "block.afc.wood.fallen_leaves.cypress": "Slender Cypress Fallen Leaves",
  "block.afc.wood.leaves.cypress": "Slender Cypress Leaves",
  "block.afc.wood.sapling.teak": "True Teak Sapling",
  "block.afc.wood.potted_sapling.teak": "True Teak Potted Sapling",
  "block.afc.wood.fallen_leaves.teak": "True Teak Fallen Leaves",
  "block.afc.wood.leaves.teak": "True Teak Leaves",
  "block.afc.wood.sapling.eucalyptus": "Blue Gum Sapling",
  "block.afc.wood.potted_sapling.eucalyptus": "Blue Gum Potted Sapling",
  "block.afc.wood.fallen_leaves.eucalyptus": "Blue Gum Fallen Leaves",
  "block.afc.wood.leaves.eucalyptus": "Blue Gum Leaves",
  "block.afc.wood.sapling.fig": "Rusty Fig Sapling",
  "block.afc.wood.potted_sapling.fig": "Rusty Fig Potted Sapling",
  "block.afc.wood.fallen_leaves.fig": "Rusty Fig Fallen Leaves",
  "block.afc.wood.leaves.fig": "Rusty Fig Leaves",
  "block.afc.tree_tap": "Tree Tap",
  "item.afc.rubber_bar": "Rubber Bar",
  "item.afc.maple_sugar": "Maple Sugar",
  "item.afc.birch_sugar": "Birch Sugar",
  "fluid.afc.latex": "Latex",
  "fluid.afc.maple_sap": "Maple Sap",
  "fluid.afc.maple_sap_concentrate": "Concentrated Maple Sap",
  "fluid.afc.maple_syrup": "Maple Syrup",
  "fluid.afc.birch_sap": "Birch Sap",
  "fluid.afc.birch_sap_concentrate": "Concentrated Birch Sap",
  "fluid.afc.birch_syrup": "Birch Syrup",
  "itemGroup.afc_creative_mode_tab": "ArborFirmaCraft"
}"""

with open("en_us.json", 'w') as file:
    file.write(string)
    file.close()
