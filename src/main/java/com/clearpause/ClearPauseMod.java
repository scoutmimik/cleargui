package com.clearpause;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
    modid = ClearPauseMod.MODID, 
    name = ClearPauseMod.NAME, 
    version = ClearPauseMod.VERSION, 
    clientSideOnly = true
)
public class ClearPauseMod {
    public static final String MODID = "clearpause";
    public static final String NAME = "Clear Pause Menu";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.gui instanceof GuiIngameMenu) {
            // Cancelne vykreslenie pozadia aj celeho default screenu
            event.setCanceled(true);

            // Rucne vykresli tlacidla, aby ostali funkcne bez tmaveho gradientu
            for (GuiButton button : event.gui.buttonList) {
                button.drawButton(event.gui.mc, event.mouseX, event.mouseY);
            }
        }
    }
}
