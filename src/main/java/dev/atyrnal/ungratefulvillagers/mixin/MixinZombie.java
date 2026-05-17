package dev.atyrnal.ungratefulvillagers.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class MixinZombie {
    @Shadow public abstract boolean convertVillagerToZombieVillager(ServerLevel level, Villager villager);

    @Inject(at = @At(value = "RETURN", ordinal = 0), method = "killedEntity", cancellable = true)
    public void onKilledOther(ServerLevel level, LivingEntity entity, DamageSource source, CallbackInfoReturnable<Boolean> cir, @Local(name = "villager") Villager villager) {
        if (villager.getVillagerXp() > 0 && this.convertVillagerToZombieVillager(level, villager)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("TAIL"), method = "killedEntity", cancellable = true)
    public void onKilledOther(ServerLevel level, LivingEntity entity, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (level.getDifficulty() != Difficulty.HARD && level.getDifficulty() != Difficulty.NORMAL && entity instanceof Villager villagerEntity) {
            if (villagerEntity.getVillagerXp() > 0 && this.convertVillagerToZombieVillager(level, villagerEntity)) {
                cir.setReturnValue(false);
            }
        }
    }

}
