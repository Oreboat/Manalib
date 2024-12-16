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
import org.manadependants.manalib.logic.maths.GaussianCalc;

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
        if(manaComponent.getMaxMana() < 500 && !player.getUuidAsString().contains("2910d9ff-1255-4f50-b574-517ec81e42aa") && !player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setMaxMana(500 + random.nextInt(41) * 50);
        } else if (player.getUuidAsString().contains("2910d9ff-1255-4f50-b574-517ec81e42aa")) {
            manaComponent.setMaxMana(0);
        } else if(player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")){
            manaComponent.setMaxMana(5000);
        }
        manaComponent.setTotalMana(manaComponent.getMaxMana()); // Start at max mana
        if(manaComponent.getManaAdaptability() < 0.01 && player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setManaAdaptability(0.01f + random.nextFloat() * (1.0f - 0.001f));
        } else if (player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setManaAdaptability(5f);
        }
        if(manaComponent.getManaStrength() < 0.01 && player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setManaStrength(0.01f + random.nextFloat() * (1.0f - 0.01f));
        } else if (player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setManaStrength(2);
        }
        if(manaComponent.getManaClarity() <= 0 && !player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setManaClarity(random.nextInt(100) + 1);
        } else if (player.getUuidAsString().contains("8ee54b35-adce-4c61-8846-a4e698915406")) {
            manaComponent.setManaClarity(150);
        }
    }

    public static void initBloodPurity(PlayerEntity player){
        ManaComponent manaComponent = MANA_COMPONENT.get(player);
        BloodManaComp bloodManaComp = BLOOD_COMPONENT.get(player);

        double mana = (double) manaComponent.getMaxMana();
        double kills = (double) bloodManaComp.getPlayerKills();

        double sigma = GaussianCalc.calculateSigma(mana, kills);

        double randomValue = GaussianCalc.generateRandomGaussian(70, sigma);
        if(player.getUuidAsString().contains("2910d9ff-1255-4f50-b574-517ec81e42aa")){
            bloodManaComp.setBloodPurity(7);
        }if(player.getUuidAsString().contains("5de5299b-83c1-4fe4-9c47-b8aae4fed6b1")){
            bloodManaComp.setBloodPurity(141);
        }else {
            bloodManaComp.setBloodPurity(randomValue);
        }
    }
}
