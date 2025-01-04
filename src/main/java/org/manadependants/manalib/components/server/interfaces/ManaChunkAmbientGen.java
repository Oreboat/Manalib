package org.manadependants.manalib.components.server.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.network.PacketByteBuf;

public interface ManaChunkAmbientGen extends Component{
    float getCurrentMana();
    void setCurrentMana(float mana);

    float getMaxMana();
    void setMaxMana(float mana);

    float getChunkDensity();
    void setChunkDensity(float amount);


    boolean isLeylineChunk();
    void setLeylineChunk(boolean leyline);

    void regenManaOverTime();
    void consumeChunkMana(float amount);

    void writeSyncPacket(PacketByteBuf buf);
}
