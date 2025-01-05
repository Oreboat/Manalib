package org.manadependants.manalib.logic.extra;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.manadependants.manalib.classes.Spell;
import org.manadependants.manalib.components.EntityComponentRegistry;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;
import org.manadependants.manalib.components.player.interfaces.PlayerCooldownComponent;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;
import org.manadependants.manalib.data.SpellEffect;

public class Casting {

    public void cast(PlayerEntity player, Spell spell, double chargeTime) {
        PlayerCooldownComponent cooldownComponent = getCooldownComponent(player);
        BlockPos blockPos = player.getBlockPos();
        Chunk chunk = player.getWorld().getChunk(blockPos);
        SpellEffect effect = spell.getEffect();

        if (cooldownComponent.isOnCooldown(spell.getID())) {
            player.sendMessage(Text.literal("Spell is on cooldown!Time Remaining: " + cooldownComponent.getRemainingCooldown(spell.getID())/20 + " seconds"), true);
            return;
        }
        player.sendMessage(Text.literal("Casting: " + spell.getName()), true);
        if (player instanceof ManaComponent mana && mana.getTotalMana() >= spell.getManaCost()) {
            if(chunk instanceof ManaChunkAmbientGen chunkAmbientGen && chunkAmbientGen.getCurrentMana() >= spell.getManaCost()) {
                mana.consumeMana(spell.getManaCost());
                chunkAmbientGen.consumeChunkMana(spell.getManaCost());
                // Apply the casting delay
                effect.apply(player, chargeTime);
                cooldownComponent.setCooldown(spell.getID(), spell.getCooldownTicks());
            }
        }
    }

    private PlayerCooldownComponent getCooldownComponent(PlayerEntity player) {
        return player.getComponent(EntityComponentRegistry.COOLDOWN_COMP);
    }
}
