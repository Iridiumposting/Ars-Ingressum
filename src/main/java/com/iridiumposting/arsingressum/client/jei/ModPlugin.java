package com.iridiumposting.arsingressum.client.jei;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.iridiumposting.arsingressum.ArsIngressum;
import com.iridiumposting.arsingressum.common.recipe.AttuneForkRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ModPlugin implements IModPlugin {
    public static final RecipeType<AttuneForkRecipe> ATTUNE_FORK_RECIPE_TYPE = RecipeType.create(ArsIngressum.MODID, "planar_attuning", AttuneForkRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ArsIngressum.prefix("main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(
                new AttuneForkRecipeCategory(helper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<AttuneForkRecipe> attuneForkRecipes = new ArrayList<>();

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (RecipeHolder<?> i : manager.getRecipes()) {
            if (i.value() instanceof AttuneForkRecipe recipe) {
                attuneForkRecipes.add(recipe);
            }
        }
        registration.addRecipes(ATTUNE_FORK_RECIPE_TYPE, attuneForkRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.IMBUEMENT_BLOCK), ATTUNE_FORK_RECIPE_TYPE);
    }

}
