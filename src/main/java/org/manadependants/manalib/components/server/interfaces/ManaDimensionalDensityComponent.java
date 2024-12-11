package org.manadependants.manalib.components.server.interfaces;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ManaDimensionalDensityComponent extends Component {
    float getManaDensity();
    void setManaDensity(float density);

    void modifyDensityForDimension(String dimensionKey, float density);
    float getDensityForDimension(String dimensionKey);
}
