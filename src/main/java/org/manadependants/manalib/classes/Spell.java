package org.manadependants.manalib.classes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.manadependants.manalib.components.EntityComponentRegistry;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;
import org.manadependants.manalib.components.player.interfaces.PlayerCooldownComponent;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;
import org.manadependants.manalib.data.SpellEffect;
import org.manadependants.manalib.logic.extra.SpellTM;

public class Spell {
    private final String id;
    private final String name;
    private final String description;
    private final float manaCost;
    private final int cooldownTicks;
    private final int castingTimeTicks;
    private final SpellEffect effect;


    public Spell(String id, String name, String description, float manaCost, int cooldownTicks, int castingTimeTicks, SpellEffect effect) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldownTicks = cooldownTicks;
        this.castingTimeTicks = castingTimeTicks;
        this.effect = effect;
    }

    public void cast(PlayerEntity player, double chargeSeconds) {
        PlayerCooldownComponent cooldownComponent = getCooldownComponent(player);
        BlockPos blockPos = player.getBlockPos();
        Chunk chunk = player.getWorld().getChunk(blockPos);

        if (cooldownComponent.isOnCooldown(id)) {
            player.sendMessage(Text.literal("Spell is on cooldown!"), true);
            return;
        }
        player.sendMessage(Text.literal("Casting: " + name), true);
        if (player instanceof ManaComponent mana && mana.getTotalMana() >= manaCost) {
            if(chunk instanceof ManaChunkAmbientGen chunkAmbientGen && chunkAmbientGen.getCurrentMana() >= manaCost) {
                mana.consumeMana(manaCost);
                chunkAmbientGen.consumeChunkMana(manaCost);
                // Apply the casting delay
                SpellTM.addTask(player, new SpellTM.Task("cast", castingTimeTicks, () -> {
                    effect.apply(player, chargeSeconds);
                    cooldownComponent.setCooldown(id, cooldownTicks);
                    player.sendMessage(Text.literal("Casted " + name + "!"), true);
                }));
            }
        }
    }

    public String getName(){
        return this.name;
    }

    private PlayerCooldownComponent getCooldownComponent(PlayerEntity player) {
        return player.getComponent(EntityComponentRegistry.COOLDOWN_COMP);
    }
}
