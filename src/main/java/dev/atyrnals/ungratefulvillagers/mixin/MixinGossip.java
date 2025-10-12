package dev.atyrnals.ungratefulvillagers.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageGossipType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillageGossipType.class)
public class MixinGossip {
    @Mutable @Shadow @Final public int maxValue;
    @Mutable @Shadow @Final public int shareDecrement;

	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(String string, int i, String key, int multiplier, int maxReputation, int decay, int shareDecrement, CallbackInfo ci) {
		if (key.equals("major_positive")) { //Restore old stats (Max 20 -> 100)
            this.maxValue = 100;
            this.shareDecrement = 100;
        } else if (key.equals("minor_positive")) { //Restore old stats (Max 25 -> 200)
            this.maxValue = 200;
        }
	}
}