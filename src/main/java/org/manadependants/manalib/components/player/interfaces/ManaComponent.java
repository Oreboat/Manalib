package org.manadependants.manalib.components.player.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ManaComponent extends Component {
    float getMaxMana();
    void setMaxMana(float value);

    float getTotalMana();
    void setTotalMana(float value);

    float getManaAdaptability();
    void setManaAdaptability(float value);

    float getManaStrength();
    void setManaStrength(float value);

    int getManaClarity();
    void setManaClarity(int value);

    void regenMana(float dimensionalManaDensity);

}
