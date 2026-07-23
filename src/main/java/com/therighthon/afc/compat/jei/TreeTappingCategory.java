package com.therighthon.afc.compat.jei;

import java.util.List;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.recipe.TreeTapRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientRenderer;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

import net.dries007.tfc.compat.jei.category.BaseRecipeCategory;
import org.jetbrains.annotations.NotNull;

public class TreeTappingCategory extends BaseRecipeCategory<TreeTapRecipe>
{

    public TreeTappingCategory(RecipeType<TreeTapRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(118, 50), new ItemStack(AFCBlocks.TREE_TAP.get()));

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TreeTapRecipe recipe, @NotNull IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 6, 5)
            .addIngredients(collapse(recipe.getBlockIngredient()))
            .setCustomRenderer(VanillaTypes.ITEM_STACK, new IIngredientRenderer<>() {

                @Override
                public void render(@NotNull GuiGraphics graphics, @NotNull ItemStack ingredient) {
                }

                @Override
                public @NotNull List<Component> getTooltip(@NotNull ItemStack ingredient, @NotNull TooltipFlag tooltipFlag) {
                    return ingredient.isEmpty() ? List.of() : ingredient.getTooltipLines(Minecraft.getInstance().player, tooltipFlag);
                }
            })
            .setBackground(slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addFluidStack(recipe.getOutput().getFluid(), 1000)
            .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(TreeTapRecipe recipe, @NotNull IRecipeSlotsView recipeSlots, @NotNull GuiGraphics graphics, double mouseX, double mouseY)
    {
        arrow.draw(graphics, 48, 3);

        var font = Minecraft.getInstance().font;

        Component tempRange = Component.translatable("tooltip.afc.tap.temp_range")
            .withStyle(ChatFormatting.WHITE)
            .append(Component.literal(String.format("%.1f°C", recipe.getMinTemp())).withStyle(ChatFormatting.AQUA))
            .append(Component.literal(" - ").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(String.format("%.1f°C", recipe.getMaxTemp())).withStyle(ChatFormatting.GOLD));

        graphics.drawString(font, tempRange, 6, 28, 0xFF404040, true);

        if (recipe.springOnly())
        {
            Component springOnly = Component.translatable("tfc.tooltip.calendar_season",
                    Component.translatable(String.format("%s", "tfc.enum.season.april")).withStyle(ChatFormatting.GREEN)
            ).withStyle(ChatFormatting.WHITE);
            graphics.drawString(font, springOnly, 6, 40, 0xFF404040, true);
        }

        recipeSlots.getSlotViews().get(0).getDisplayedIngredient(VanillaTypes.ITEM_STACK).ifPresent(stack -> {
            if (stack.getItem() instanceof BlockItem blockItem)
            {
                BlockState state = blockItem.getBlock().defaultBlockState();
                PoseStack poseStack = graphics.pose();
                poseStack.pushPose();
                poseStack.translate(15, 15, 100);
                poseStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
                poseStack.mulPose(Axis.YP.rotationDegrees(22.5f));
                int scale = 16;
                poseStack.scale(scale, -scale, scale);
                poseStack.translate(-0.5, -0.5, -0.5);

                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, graphics.bufferSource(), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());

                poseStack.popPose();
            }
        });
    }
}
