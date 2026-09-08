package com.clearpause;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.List;

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

    private static final String[] BUTTON_LIST_FIELD = new String[]{"buttonList", "field_146292_n"};

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.gui != null && event.gui.mc != null && event.gui.mc.theWorld != null) {
            
            String className = event.gui.getClass().getName();

            // Kontrola hlavnych menu
            boolean isStandardMenu = (event.gui instanceof GuiIngameMenu) 
                                  || (event.gui instanceof GuiOptions) 
                                  || (event.gui instanceof GuiVideoSettings)
                                  || (event.gui instanceof GuiScreenOptionsSounds);

            // Kontrola stabilnych pod-menu cez nazvy tried
            boolean isSubMenu = className.contains("Customiz")
                             || className.contains("GuiOption") 
                             || className.contains("ScreenOptions")
                             || className.contains("GuiDetailSettings")
                             || className.contains("GuiQualitySettings")
                             || className.contains("GuiPerformanceSettings")
                             || className.contains("GuiOtherSettings")
                             || className.contains("GuiAnimation");

            if (isStandardMenu || isSubMenu) {
                event.setCanceled(true);

                try {
                    List<GuiButton> buttons = ReflectionHelper.getPrivateValue(GuiScreen.class, event.gui, BUTTON_LIST_FIELD);
                    
                    if (buttons != null) {
                        for (GuiButton button : buttons) {
                            if (button.visible) {
                                button.drawButton(event.gui.mc, event.mouseX, event.mouseY);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
