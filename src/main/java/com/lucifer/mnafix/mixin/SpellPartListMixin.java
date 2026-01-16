package com.lucifer.mnafix.mixin;

import com.lucifer.mnafix.SpellCache;
import com.mna.api.spells.SpellCraftingContext;
import com.mna.api.spells.base.ISpellComponent;
import com.mna.gui.widgets.SpellPartList;
import com.mna.recipes.ItemAndPatternRecipe;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(value = SpellPartList.class, remap = false)
public abstract class SpellPartListMixin extends AbstractSelectionListAccessor {
    @Shadow @Final private boolean roteOnly;
    @Shadow @Final private int tier;
    @Shadow @Final private com.mna.api.capabilities.IPlayerRoteSpells rote;

    /**
     * @author Lucifer
     * @reason Overwrite expensive recipeManager.getRecipes().stream() use
     */
    @Overwrite
    private boolean validParts(ISpellComponent p) {
        if (!SpellCache.isReady())
            return false;

        if (!this.minecraft.player.isCreative() && this.roteOnly && !this.rote.isRote(p)) {
            return false;
        }
        if (!this.roteOnly && p.isSilverSpell()) {
            return false;
        }
        boolean valid = p.isCraftable(new SpellCraftingContext(this.minecraft.player));
        Recipe<?> pattern = SpellCache.get(p.getRegistryName());
        if (pattern instanceof ItemAndPatternRecipe) {
            valid &= ((ItemAndPatternRecipe)pattern).getTier() <= this.tier;
        }
        return valid;
    }
}
