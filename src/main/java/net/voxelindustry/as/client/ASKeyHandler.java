package net.voxelindustry.as.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.voxelindustry.as.client.gui.GuiSpellPrompt;
import net.voxelindustry.as.common.ArcanumScripta;
import org.lwjgl.input.Keyboard;
import org.yggard.brokkgui.wrapper.impl.BrokkGuiManager;

public class ASKeyHandler
{
    public static final KeyBinding keySpellPrompt = new KeyBinding("key.spellprompt.desc", Keyboard.KEY_R,
            ArcanumScripta.MODNAME);

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (keySpellPrompt.isPressed())
            BrokkGuiManager.openBrokkGuiScreen(new GuiSpellPrompt());
    }
}
