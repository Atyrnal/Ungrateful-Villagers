package dev.atyrnal.ungratefulvillagers;

import dev.atyrnal.ungratefulvillagers.events.VillagerDeathHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod("ungratefulvillagers")
public class UngratefulVillagers {
    public UngratefulVillagers(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.addListener(VillagerDeathHandler::onVillagerDeath);
    }
}