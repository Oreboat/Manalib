package org.manadependants.manalib.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.manadependants.manalib.Manalib;
import org.manadependants.manalib.components.player.BloodManaCompImpl;
import org.manadependants.manalib.components.player.interfaces.BloodManaComp;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;
import org.manadependants.manalib.components.player.ManaComponentImpl;

import java.util.Random;

public class EntityComponentRegistry implements EntityComponentInitializer {
    public static final ComponentKey<ManaComponent> MANA_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(
            new Identifier(Manalib.MODID, "mana"),
            ManaComponent.class
    );
    public static final ComponentKey<BloodManaComp> BLOOD_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(
            new Identifier(Manalib.MODID, "blood"),
            BloodManaComp.class
    );

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry){
        registry.registerForPlayers(MANA_COMPONENT, player -> new ManaComponentImpl(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(BLOOD_COMPONENT, player -> new BloodManaCompImpl(), RespawnCopyStrategy.ALWAYS_COPY);
    }

    public static void initializeManaStats(PlayerEntity player) {
        ManaComponent manaComponent = MANA_COMPONENT.get(player);
        Random random = new Random();
        if(manaComponent.getMaxMana() < 500) {
            manaComponent.setMaxMana(500 + random.nextInt(41) * 50);
        }// 500 to 2500, increments of 50
        manaComponent.setTotalMana(manaComponent.getMaxMana()); // Start at max mana
        manaComponent.setManaAdaptability(random.nextFloat()); // 0 to 1
        manaComponent.setManaStrength(0.01f + random.nextFloat() * (1.0f - 0.01f)); // 0.01 to 1
        manaComponent.setManaClarity(random.nextInt(100) + 1); // 1 to 100
    }

    public static void initBloodPurity(PlayerEntity player){
        ManaComponent manaComponent = MANA_COMPONENT.get(player);
        BloodManaComp bloodManaComp = BLOOD_COMPONENT.get(player);

        if(bloodManaComp.getBloodPurity() == 0){

        }
    }
}
