package org.manadependants.manalib.components.player.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface BloodManaComp extends Component {
    float getBloodPurity();
    void setBloodPurity(float amount);

    float getBloodManaValue();
    void setBloodManaValue(float amount);

    boolean isBloodMage();
    void setBloodMage(boolean binary);

    boolean unsafeBloodMagic();
    void setUnsafe(boolean binary);

    int getPlayerKills();
    void setPlayerKills(int amount);

    void regenBloodMana(float amount, boolean isBloodMage, float bloodPurity);
}
