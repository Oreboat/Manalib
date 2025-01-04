package org.manadependants.manalib.logic.extra;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpellTM {
    private static final Map<UUID, Task> TASKS = new HashMap<>();
    private static final Map<PlayerEntity, Task> PTASKS = new HashMap<>();

    public static void addTask(PlayerEntity player, Task task) {
        UUID playerId = player.getUuid();
        TASKS.put(playerId, task);
        PTASKS.put(player, task);
    }

    public static void tick(ServerWorld world) {
        TASKS.entrySet().removeIf(entry -> {
            Task task = entry.getValue();
            task.ticksRemaining--;

            if (task.ticksRemaining <= 0) {
                task.action.run(); // Execute the action
                return true; // Remove the task
            }

            return false; // Keep the task in the map
        });
        PTASKS.entrySet().removeIf(entry -> {
            Task task = entry.getValue();
            task.ticksRemaining--;

            if (task.ticksRemaining <= 0) {
                task.action.run(); // Execute the action
                return true; // Remove the task
            }

            return false;
        });
        PTASKS.forEach((player, task) -> {
            if (task.type.contains("cast") && task.ticksRemaining > 0) {
                task.pos = player.getBlockPos();
                player.setVelocity(0, 0, 0);
                player.setPos(task.pos.getX(), task.pos.getY(), task.pos.getZ());
                player.velocityModified = true;
            }
        });
    }

    public static class Task {
        public int ticksRemaining;
        public Runnable action;
        public String type;
        public BlockPos pos;

        public Task(String type, int ticksRemaining, Runnable action) {
            this.type = type;
            this.ticksRemaining = ticksRemaining;
            this.action = action;
        }
    }
}
