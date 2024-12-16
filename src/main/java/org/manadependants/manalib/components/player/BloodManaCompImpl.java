package org.manadependants.manalib.components.player;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.manadependants.manalib.components.player.interfaces.BloodManaComp;

public class BloodManaCompImpl implements BloodManaComp, AutoSyncedComponent {
    private double bloodPurity;
    private float bloodMana;
    private boolean isBloodMage;
    private boolean isUnsafe;
    private int playerKills;


    @Override
    public double getBloodPurity() {
        return bloodPurity;
    }

    @Override
    public void setBloodPurity(double amount) {
        this.bloodPurity = amount;

    }

    @Override
    public float getBloodManaValue() {
        return bloodMana;
    }

    @Override
    public void setBloodManaValue(float amount) {
        this.bloodMana = amount;

    }

    @Override
    public boolean isBloodMage() {
        return isBloodMage;
    }

    @Override
    public void setBloodMage(boolean binary) {
        this.isBloodMage = binary;

    }

    @Override
    public boolean unsafeBloodMagic() {
        return isUnsafe;
    }

    @Override
    public void setUnsafe(boolean binary) {
        this.isUnsafe = binary;
    }

    @Override
    public int getPlayerKills() {
        return playerKills;
    }

    @Override
    public void setPlayerKills(int amount) {
        this.playerKills = amount;
    }

    @Override
    public void regenBloodMana(float amount, boolean isBloodMage, float bloodPurity) {

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
