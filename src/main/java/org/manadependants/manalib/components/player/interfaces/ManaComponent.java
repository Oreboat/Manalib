package org.manadependants.manalib.components.player.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ManaComponent extends Component {
    float getMaxMana();
    void setMaxMana(float value);

    float getTotalMana();
    void setTotalMana(float value);
    void consumeMana(float amount);

    float getAdaptedDensity();
    void setAdaptedDensity(float amount);
    void addAdaptiveDensity(float density, float chunkDensity, float manaAdaptability);

    float getManaAdaptability();
    void setManaAdaptability(float value);

    float getManaStrength();
    void setManaStrength(float value);

    int getManaClarity();
    void setManaClarity(int value);

    void regenMana(float dimensionalManaDensity);

}
