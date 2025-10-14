package dev.atyrnals.ungratefulvillagers.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class MixinZombie {
    @Shadow public abstract boolean infectVillager(ServerWorld world, VillagerEntity villager);

    @Inject(at = @At(value = "RETURN", ordinal = 0), method = "onKilledOther", cancellable = true)
    public void onKilledOther(ServerWorld world, LivingEntity other, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir, @Local VillagerEntity villagerEntity) {
        if (villagerEntity.getExperience() > 0 && this.infectVillager(world, villagerEntity)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("TAIL"), method = "onKilledOther", cancellable = true)
    public void onKilledOther(ServerWorld world, LivingEntity other, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (world.getDifficulty() != Difficulty.HARD && world.getDifficulty() != Difficulty.NORMAL && other instanceof VillagerEntity villagerEntity) {
            if (villagerEntity.getExperience() > 0 && this.infectVillager(world, villagerEntity)) {
                cir.setReturnValue(false);
            }
        }
    }

}
