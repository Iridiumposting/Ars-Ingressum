package com.iridiumposting.arsingressum.common.recipe;

import com.hollingsworth.arsnouveau.api.imbuement_chamber.IImbuementRecipe;
import com.hollingsworth.arsnouveau.common.block.tile.ImbuementTile;
import com.hollingsworth.arsnouveau.common.crafting.recipes.EnchantingApparatusRecipe;
import com.iridiumposting.arsingressum.common.item.TuningFork;
import com.iridiumposting.arsingressum.common.item.data.TuningForkData;
import com.iridiumposting.arsingressum.setup.registry.AddonItemRegistry;
import com.iridiumposting.arsingressum.setup.registry.AddonRecipeRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record AttuneForkRecipe(List<Ingredient> pedestalItems, ResourceKey<Level> dimension, int source) implements IImbuementRecipe {

    @Override
    public boolean matches(ImbuementTile imbuementTile, Level level) {
        ItemStack reagent = imbuementTile.stack;
        return (reagent.getItem() instanceof TuningFork) && EnchantingApparatusRecipe.doItemsMatch(imbuementTile.getPedestalItems(), pedestalItems);
    }

    @Override
    public int source() {
        return source;
    }

    @Override
    public ItemStack assemble(ImbuementTile imbuementTile, HolderLookup.Provider provider) {
        ItemStack attunedFork = new ItemStack(AddonItemRegistry.ATTUNED_TUNING_FORK.get());
        attunedFork.set(TuningForkData.DIMENSION, dimension);
        return attunedFork;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height){
        return false;
    }

    @Override
    public int getSourceCost(ImbuementTile imbuementTile) {
        return 2000;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AddonRecipeRegistry.ATTUNE_FORK_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return AddonRecipeRegistry.ATTUNE_FORK_TYPE.get();
    }

    @Override
    public Component getCraftingStartedText(ImbuementTile imbuementTile) {
        return Component.translatable("chat.ars_ingressum.attuning.attuning_started", assemble(imbuementTile, imbuementTile.getLevel().registryAccess()).getHoverName());
    }

    @Override
    public Component getCraftingText(ImbuementTile imbuementTile) {
        return Component.translatable("tooltip.ars_ingressum.attuning.attuning", assemble(imbuementTile, imbuementTile.getLevel().registryAccess()).getHoverName());
    }

    @Override
    public Component getCraftingProgressText(ImbuementTile imbuementTile, int progress) {
        return Component.translatable("tooltip.ars_ingressum.attuning.attuning_progress", progress).withStyle(ChatFormatting.GOLD);
    }

    public static class Serializer implements RecipeSerializer<AttuneForkRecipe> {
        public static final MapCodec<AttuneForkRecipe> CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.list(Ingredient.CODEC).fieldOf("pedestal_items").forGetter(AttuneForkRecipe::pedestalItems),
                        Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(AttuneForkRecipe::dimension),
                        Codec.INT.fieldOf("source").forGetter(AttuneForkRecipe::source)
        ).apply(instance, AttuneForkRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AttuneForkRecipe> STREAM_CODEC = StreamCodec.of(
                AttuneForkRecipe.Serializer::toNetwork, AttuneForkRecipe.Serializer::fromNetwork
        );

        public static void toNetwork(RegistryFriendlyByteBuf buf, AttuneForkRecipe recipe) {
            buf.writeInt(recipe.pedestalItems.size());
            for (Ingredient i : recipe.pedestalItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, i);
            }
            ResourceKey.streamCodec(Registries.DIMENSION).encode(buf, recipe.dimension);
            buf.writeInt(recipe.source);
        }

        public static AttuneForkRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int length = buffer.readInt();
            List<Ingredient> stacks = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                try {
                    stacks.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            return new AttuneForkRecipe(stacks, ResourceKey.streamCodec(Registries.DIMENSION).decode(buffer), buffer.readInt());
        }

        @Override
        public MapCodec<AttuneForkRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AttuneForkRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}