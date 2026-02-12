package com.iridiumposting.arsingressum.client.jei;

import com.hollingsworth.arsnouveau.client.jei.MultiInputCategory;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import com.iridiumposting.arsingressum.common.recipe.AttuneForkRecipe;
import com.iridiumposting.arsingressum.setup.registry.AddonItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AttuneForkRecipeCategory implements IRecipeCategory<AttuneForkRecipe> {
    public IDrawable background;
    public IDrawable icon;

    protected Vec2 point = new Vec2(48,13);
    protected Vec2 center = new Vec2(48,45);

    public AttuneForkRecipeCategory(IGuiHelper helper) {
        background = helper.createBlankDrawable(114,108);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.IMBUEMENT_BLOCK));
    }

    @Override
    public RecipeType<AttuneForkRecipe> getRecipeType() {
        return ModPlugin.ATTUNE_FORK_RECIPE_TYPE;
    }

    public Component getTitle() {
        return Component.translatable("ars_ingressum.attuning.planar_attuning");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AttuneForkRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.CATALYST, (int) center.x, (int) center.y).addIngredients(Ingredient.of(AddonItemRegistry.VACANT_TUNING_FORK.get()));

        List<Ingredient> pedestalItems = recipe.pedestalItems();
        double angleBetweenEach = 360.0 / pedestalItems.size();
        for (Ingredient item : pedestalItems) {
            builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y).addIngredients(item);
            point = MultiInputCategory.rotatePointAbout(point, center, angleBetweenEach);
        }

        ItemStack tuningFork = new ItemStack(AddonItemRegistry.ATTUNED_TUNING_FORK.get());
        tuningFork.set(TuningForkData.DIMENSION, recipe.dimension());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 10).addIngredient(VanillaTypes.ITEM_STACK, tuningFork);
    }

    @Override
    public void draw(AttuneForkRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font renderer = Minecraft.getInstance().font;
        Component name = Component.translatable("dimension." + recipe.dimension().location().getNamespace() + "." + recipe.dimension().location().getPath());

        guiGraphics.drawString(renderer, name, 0, 100, 10, false);
    }
}
