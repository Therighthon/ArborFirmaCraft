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

@SuppressWarnings("UnnecessaryLocalVariable")
public class TreeTappingCategory extends BaseRecipeCategory<TreeTapRecipe>
{

    private static final int WIDTH = 184;
    private static final int HEIGHT = 60;

    public TreeTappingCategory(RecipeType<TreeTapRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(WIDTH, HEIGHT), new ItemStack(AFCBlocks.TREE_TAP.get()));

    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull TreeTapRecipe recipe, @NotNull IFocusGroup focuses)
    {
        int itemX = 7;
        int itemY = HEIGHT / 2 - 5;

        addInputSlot(builder, recipe, itemX, itemY - 15);
        addInputSlot(builder, recipe, itemX, itemY);
        addInputSlot(builder, recipe, itemX, itemY + 15);

        int fluidX = WIDTH / 2 - 10;
        int fluidY = itemY;
        builder.addSlot(RecipeIngredientRole.OUTPUT, fluidX, fluidY)
            .addFluidStack(recipe.getOutput().getFluid(), 1000)
            .setBackground(slot, -1, -1);
    }

    private void addInputSlot(IRecipeLayoutBuilder builder, TreeTapRecipe recipe, int x, int y)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, x, y)
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
    }

    @Override
    public void draw(TreeTapRecipe recipe, @NotNull IRecipeSlotsView recipeSlots, @NotNull GuiGraphics graphics, double mouseX, double mouseY)
    {
        var font = Minecraft.getInstance().font;

        // Temperature Range.
        int tempX = 30;
        int tempY = HEIGHT - 10;
        Component rangeComponent = Component.empty()
            .append(Component.literal(String.format("%.1f°C", recipe.getMinTemp())).withStyle(ChatFormatting.DARK_AQUA))
            .append(Component.literal(" - ").withStyle(ChatFormatting.DARK_GRAY))
            .append(Component.literal(String.format("%.1f°C", recipe.getMaxTemp())).withStyle(ChatFormatting.DARK_RED));

        Component tempRange = Component.translatable("tfc.tooltip.climate_current_temp", rangeComponent)
            .withStyle(ChatFormatting.DARK_GRAY);

        graphics.drawString(font, tempRange, tempX, tempY, 0xFF404040, false);

        // Arrow.
        int arrowX = tempX + 10;
        int arrowY = HEIGHT / 2 - 5;
        arrow.draw(graphics, arrowX, arrowY);
        arrowAnimated.draw(graphics, arrowX, arrowY);

        // Season Condition.
        int seasonX = arrowX + 10;
        int seasonY = 5;
        if (recipe.springOnly())
        {
            Component springOnly = Component.translatable("tfc.tooltip.calendar_season",
                    Component.translatable(String.format("%s", "tfc.enum.season.april")).withStyle(ChatFormatting.DARK_GREEN)
            ).withStyle(ChatFormatting.DARK_GRAY);
            graphics.drawString(font, springOnly, seasonX, seasonY, 0xFF404040, false);
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
        int renderX = 15;
        int renderY = HEIGHT / 2;
        int renderZ = 100;
        float renderRotationX = -15.5f;
        float renderRotationY = 45f;
        int renderScale = 16;

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(renderX, renderY, renderZ);
        poseStack.mulPose(Axis.XP.rotationDegrees(renderRotationX));
        poseStack.mulPose(Axis.YP.rotationDegrees(renderRotationY));
        poseStack.scale(renderScale, -renderScale, renderScale);

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

    private void renderBlock(GuiGraphics graphics, PoseStack poseStack, BlockState state, RenderType type)
    {
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, graphics.bufferSource(), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, type);
    }
}
