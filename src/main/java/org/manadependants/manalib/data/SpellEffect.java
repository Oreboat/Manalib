package org.manadependants.manalib.data;

import net.minecraft.entity.player.PlayerEntity;

public interface SpellEffect {
    /**
     * Apply the spell's effect to a player.
     *
     * @param caster The player casting the spell.
     */
    void apply(PlayerEntity caster, double chargeTime);
}
