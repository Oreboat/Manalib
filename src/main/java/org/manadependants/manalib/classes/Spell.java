package org.manadependants.manalib.classes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.manadependants.manalib.components.EntityComponentRegistry;
import org.manadependants.manalib.components.player.interfaces.ManaComponent;
import org.manadependants.manalib.components.player.interfaces.PlayerCooldownComponent;
import org.manadependants.manalib.components.server.interfaces.ManaChunkAmbientGen;
import org.manadependants.manalib.data.SpellEffect;
import org.manadependants.manalib.logic.extra.SpellTM;

public class Spell {
    private final String id;
    private final String name;
    private final String description;
    private final float manaCost;
    private final int cooldownTicks;
    private final SpellEffect effect;


    public Spell(String id, String name, String description, float manaCost, int cooldownTicks, SpellEffect effect) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldownTicks = cooldownTicks;
        this.effect = effect;
    }

    public String getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public float getManaCost(){
        return this.manaCost;
    }
    public int getCooldownTicks(){
        return this.cooldownTicks;
    }
    public SpellEffect getEffect(){
        return this.effect;
    }
}
