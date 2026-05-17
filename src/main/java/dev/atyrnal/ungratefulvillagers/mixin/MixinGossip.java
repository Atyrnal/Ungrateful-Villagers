package dev.atyrnal.ungratefulvillagers.mixin;

import net.minecraft.world.entity.ai.gossip.GossipType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GossipType.class)
public class MixinGossip {
    @Mutable @Shadow @Final public int max;
    @Mutable @Shadow @Final public int decayPerTransfer;

	@Inject(at = @At("RETURN"), method = "<init>")
	private void init(String enumName, int ordinal, String id, int weight, int max, int decayPerDay, int decayPerTransfer, CallbackInfo ci) {
		if (id.equals("major_positive")) { //Restore old stats (Max 20 -> 100)
            this.max = 100;
            this.decayPerTransfer = 100;
        } else if (id.equals("minor_positive")) { //Restore old stats (Max 25 -> 200)
            this.max = 200;
        }
	}
}