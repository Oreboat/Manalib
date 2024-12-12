package org.manadependants.manalib.components.server.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface ManaChunkAmbientGen extends Component, AutoSyncedComponent {
    float getCurrentMana();
    void setCurrentMana(float mana);

    float getMaxMana();
    void setMaxMana(float mana);

    boolean isLeylineChunk();
    void setLeylineChunk(boolean leyline);

    void regenManaOverTime();
    void consumeChunkMana(float amount);
}
