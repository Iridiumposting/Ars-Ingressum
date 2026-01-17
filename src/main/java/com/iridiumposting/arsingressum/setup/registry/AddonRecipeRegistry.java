package com.iridiumposting.arsingressum.setup.registry;

import com.iridiumposting.arsingressum.common.recipe.AttuneForkRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.iridiumposting.arsingressum.ArsIngressum.MODID;

public class AddonRecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);

    public static final String ATTUNE_FORK_RECIPE_ID = "planar_attuning";

    public static final DeferredHolder<RecipeType<?>, RecipeType<AttuneForkRecipe>> ATTUNE_FORK_TYPE = RECIPE_TYPES.register(ATTUNE_FORK_RECIPE_ID, ModRecipeType::new);
    public static final DeferredHolder<RecipeSerializer<?>, AttuneForkRecipe.Serializer> ATTUNE_FORK_SERIALIZER = RECIPE_SERIALIZERS.register(ATTUNE_FORK_RECIPE_ID, AttuneForkRecipe.Serializer::new);

    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return BuiltInRegistries.RECIPE_TYPE.getKey(this).toString();
        }
    }
}