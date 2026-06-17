package dev.atyrnal.ungratefulvillagers.events;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.nbt.NbtOps;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class VillagerDeathHandler {

    @SubscribeEvent
    @SuppressWarnings("resource")
    public static void onVillagerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (!(event.getSource().getEntity() instanceof Zombie zombie)) return;
        if (villager.getVillagerXp() <= 0) return;
        if (!(villager.level() instanceof ServerLevel level)) return;
        if (!EventHooks.canLivingConvert(villager, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) return;

        ZombieVillager zombievillager = villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
        if (zombievillager != null) {
            zombievillager.finalizeSpawn(
                    level,
                    level.getCurrentDifficultyAt(zombievillager.blockPosition()),
                    MobSpawnType.CONVERSION,
                    new Zombie.ZombieGroupData(false, true)
            );
            zombievillager.setVillagerData(villager.getVillagerData());
            zombievillager.setGossips(villager.getGossips().store(NbtOps.INSTANCE));
            zombievillager.setTradeOffers(villager.getOffers().copy());
            zombievillager.setVillagerXp(villager.getVillagerXp());
            EventHooks.onLivingConvert(villager, zombievillager);
            if (!zombie.isSilent()) {
                level.levelEvent(null, 1026, zombie.blockPosition(), 0);
            }
            event.setCanceled(true);
        }
    }
}