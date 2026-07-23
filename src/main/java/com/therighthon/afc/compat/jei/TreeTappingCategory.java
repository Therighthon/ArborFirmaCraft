package com.therighthon.afc.compat.jei;

import java.util.List;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.therighthon.afc.AFC;
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
import net.minecraft.resources.ResourceLocation;
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
    private static final int HEIGHT = 80;

    private static final ResourceLocation PROGRESS_BAR_BG = new ResourceLocation(AFC.MOD_ID, "textures/gui/jei/tapping_progress_bg.png");
    private static final ResourceLocation PROGRESS_BAR_FG = new ResourceLocation(AFC.MOD_ID, "textures/gui/jei/tapping_progress_fg.png");
    private static final int PROGRESS_WIDTH = 24;
    private static final int PROGRESS_HEIGHT = 17;

    public TreeTappingCategory(RecipeType<TreeTapRecipe> type, IGuiHelper helper)
    {
        super(type, helper, helper.createBlankDrawable(WIDTH, HEIGHT), new ItemStack(AFCBlocks.TREE_TAP.get()));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull TreeTapRecipe recipe, @NotNull IFocusGroup focuses)
    {
        int itemX = 7;
        int itemY = HEIGHT / 2 - 20;

        addInputSlot(builder, recipe, itemX, itemY - 15);
        addInputSlot(builder, recipe, itemX, itemY);
        addInputSlot(builder, recipe, itemX, itemY + 15);

        int fluidX = WIDTH / 2 + 10;
        int fluidY = HEIGHT / 4;
        builder.addSlot(RecipeIngredientRole.OUTPUT, fluidX, fluidY)
            .addFluidStack(recipe.getOutput().getFluid(), 1000)
            .setBackground(slot, -1, -1)
            .addTooltipCallback((recipeSlotView, tooltip) -> {
                tooltip.add(Component.literal(recipe.getOutput().getAmount() + " mB/s").withStyle(ChatFormatting.GOLD));
            });
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
        int tempX = 5;
        int tempY = HEIGHT - 20;
        Component rangeComponent = Component.empty()
            .append(Component.literal(String.format("%.1f°C", recipe.getMinTemp())).withStyle(ChatFormatting.DARK_AQUA))
            .append(Component.literal(" - ").withStyle(ChatFormatting.DARK_GRAY))
            .append(Component.literal(String.format("%.1f°C", recipe.getMaxTemp())).withStyle(ChatFormatting.DARK_RED));

        Component tempRange = Component.translatable("tfc.tooltip.climate_current_temp", rangeComponent)
            .withStyle(ChatFormatting.DARK_GRAY);

        graphics.drawString(font, tempRange, tempX, tempY, 0xFF404040, false);

        // Progress Bar.
        int arrowX = 40;
        int arrowY = HEIGHT / 4;
        long time = System.currentTimeMillis() % 8000;
        int animatedWidth = (int) (PROGRESS_WIDTH * 2 * (time / 8000.0f));

        graphics.blit(PROGRESS_BAR_BG, arrowX, arrowY, 0, 0, PROGRESS_WIDTH * 2, PROGRESS_HEIGHT, PROGRESS_WIDTH, PROGRESS_HEIGHT);

        if (animatedWidth > 0) {
            graphics.blit(PROGRESS_BAR_FG, arrowX, arrowY, 0, 0, animatedWidth, PROGRESS_HEIGHT, PROGRESS_WIDTH, PROGRESS_HEIGHT);
        }

        // Season Condition.
        int seasonX = tempX;
        int seasonY = tempY + 12;
        if (recipe.springOnly())
        {
            Component springOnly = Component.translatable("tfc.tooltip.calendar_season",
                    Component.translatable(String.format("%s", "tfc.enum.season.april")).withStyle(ChatFormatting.DARK_GREEN)
            ).withStyle(ChatFormatting.DARK_GRAY);
            graphics.drawString(font, springOnly, seasonX, seasonY, 0xFF404040, false);
        }

        // Fluid Rate.
        int fluidX = WIDTH / 2 + 12;
        int fluidY = HEIGHT / 4;
        if (!recipe.getOutput().isEmpty())
        {
            Component fluidRate = Component.literal(recipe.getOutput().getAmount() + " mB/s").withStyle(ChatFormatting.DARK_GRAY);
            graphics.drawString(font, fluidRate, fluidX + 20, fluidY + 4, 0xFF404040, false);
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
        int renderY = HEIGHT / 2 - 10;
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
