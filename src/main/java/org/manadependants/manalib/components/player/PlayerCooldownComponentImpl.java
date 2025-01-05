package org.manadependants.manalib.components.player;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.manadependants.manalib.components.player.interfaces.PlayerCooldownComponent;

import java.util.HashMap;
import java.util.Map;

public class PlayerCooldownComponentImpl implements PlayerCooldownComponent, AutoSyncedComponent {
    private final Map<String, Long> cooldowns = new HashMap<>();


    @Override
    public void setCooldown(String spellId, int cooldownTicks) {
        cooldowns.put(spellId, System.currentTimeMillis() + (cooldownTicks * 50L));
    }

    @Override
    public boolean isOnCooldown(String spellId) {
        return cooldowns.containsKey(spellId) && cooldowns.get(spellId) > System.currentTimeMillis();
    }

    @Override
    public int getRemainingCooldown(String spellId) {
        return isOnCooldown(spellId) ? (int) ((cooldowns.get(spellId) - System.currentTimeMillis()) / 50) : 0;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {

    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {

    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return AutoSyncedComponent.super.shouldSyncWith(player);
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        AutoSyncedComponent.super.applySyncPacket(buf);
    }
}
