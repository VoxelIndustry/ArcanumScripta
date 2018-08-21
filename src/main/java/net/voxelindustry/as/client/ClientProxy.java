package net.voxelindustry.as.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.voxelindustry.as.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        MinecraftForge.EVENT_BUS.register(new ASKeyHandler());
        ClientRegistry.registerKeyBinding(ASKeyHandler.keySpellPrompt);
    }
}
