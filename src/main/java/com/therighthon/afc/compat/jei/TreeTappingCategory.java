package com.therighthon.afc.compat.jei;

import java.util.List;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.therighthon.afc.common.blocks.AFCBlocks;
import com.therighthon.afc.common.blocks.TapBlock;
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
import net.minecraft.core.Direction;
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

    private static final int WIDTH = 180;
    private static final int HEIGHT = 60;

    public TreeTappingCategory(RecipeType<TreeTapRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(WIDTH, HEIGHT), new ItemStack(AFCBlocks.TREE_TAP.get()));

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
            });

        builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 5)
            .addFluidStack(recipe.getOutput().getFluid(), 1000)
            .setBackground(slot, -1, -1);
    }

    @Override
    public void draw(TreeTapRecipe recipe, @NotNull IRecipeSlotsView recipeSlots, @NotNull GuiGraphics graphics, double mouseX, double mouseY)
    {
        arrow.draw(graphics, 48, 3);
        var font = Minecraft.getInstance().font;

        // Temperature Range.
        Component rangeComponent = Component.empty()
            .append(Component.literal(String.format("%.1f°C", recipe.getMinTemp())).withStyle(ChatFormatting.AQUA))
            .append(Component.literal(" - ").withStyle(ChatFormatting.WHITE))
            .append(Component.literal(String.format("%.1f°C", recipe.getMaxTemp())).withStyle(ChatFormatting.GOLD));

        Component tempRange = Component.translatable("tfc.tooltip.climate_current_temp", rangeComponent)
            .withStyle(ChatFormatting.WHITE);

        graphics.drawString(font, tempRange, 6, 28, 0xFF404040, true);

        // Season Condition.
        if (recipe.springOnly())
        {
            Component springOnly = Component.translatable("tfc.tooltip.calendar_season",
                    Component.translatable(String.format("%s", "tfc.enum.season.april")).withStyle(ChatFormatting.GREEN)
            ).withStyle(ChatFormatting.WHITE);
            graphics.drawString(font, springOnly, 6, 40, 0xFF404040, true);
        }

        // 3D Render.
        recipeSlots.getSlotViews().get(0).getDisplayedIngredient(VanillaTypes.ITEM_STACK).ifPresent(stack -> {
            if (stack.getItem() instanceof BlockItem blockItem)
            {
                renderTreeTapping(graphics, blockItem.getBlock().defaultBlockState());
            }
        });
    }

    /**
     * Render log blocks in a tower of three with a tap in the middle.
     */
    private void renderTreeTapping(GuiGraphics graphics, BlockState logState)
    {
        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(15, 25, 100);
        poseStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        poseStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = 16;
        poseStack.scale(scale, -scale, scale);

        // Logs.
        poseStack.pushPose();
        poseStack.translate(-0.5, -0.5, -0.5);
        renderBlock(graphics, poseStack, logState, RenderType.solid());
        poseStack.translate(0, 1, 0);
        renderBlock(graphics, poseStack, logState, RenderType.solid());
        poseStack.translate(0, -2, 0);
        renderBlock(graphics, poseStack, logState, RenderType.solid());
        poseStack.popPose();

        // Tap.
        BlockState tapState = AFCBlocks.TREE_TAP.get().defaultBlockState().setValue(TapBlock.FACING, Direction.SOUTH);
        poseStack.pushPose();
        poseStack.translate(-0.5, -0.5, 0.5);
        renderBlock(graphics, poseStack, tapState, RenderType.cutout());
        poseStack.popPose();

        poseStack.popPose();
    }

    /**
     * Helper method to render a blockstate display.
     */
    private void renderBlock(GuiGraphics graphics, PoseStack poseStack, BlockState state, RenderType type)
    {
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, graphics.bufferSource(), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, type);
    }
}
