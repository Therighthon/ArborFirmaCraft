import os

leaves = open("tfc_woods.txt", 'r').read().split("\n")

leaf_items = ["leaves", "sapling", "potted_sapling", "log", "wood"]
unique_log_items = ["log", "wood"]
change_woods = ["douglas_fir", "white_cedar"]

wood_items = open("wood_items.txt", 'r').read().split("\n")
wood_blocks = open("wood_blocks.txt", 'r').read().split("\n")
wood_plank_items = open("wood_plank_items.txt", 'r').read().split("\n")


string = """{
"""

for leaf in leaves:
    for item in leaf_items:
        string = string + """  "block.afc.wood.%s.%s": "%s %s",
""" % (item, leaf, leaf.replace("_", " ").title(), item.replace("_", " ").title())


for wood in change_woods:
    for item in wood_items:
        string = string + """  "item.afc.wood.%s.%s": "%s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

    for item in wood_blocks:
        string = string + """  "block.afc.wood.%s.%s": "%s %s",
""" % (item, wood, wood.replace("_", " ").title(), item.replace("_", " ").title())

    for item in wood_plank_items:
        string = string + """  "block.afc.wood.planks.%s_%s": "%s %s",
""" % (wood, item, wood.replace("_", " ").title(), item.replace("_", " ").title())


string = string + """}"""

with open("tfc_en_uk.json", 'w') as file:
    file.write(string)
    file.close()
