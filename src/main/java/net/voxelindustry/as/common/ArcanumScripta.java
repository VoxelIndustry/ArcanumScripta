package net.voxelindustry.as.common;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ArcanumScripta.MODID, version = ArcanumScripta.VERSION, name = ArcanumScripta.MODNAME)
public class ArcanumScripta
{
    public static final String MODID   = "arcanumscripta";
    public static final String MODNAME = "Arcanum Scripta";
    public static final String VERSION = "0.1.0";

    @SidedProxy(clientSide = "net.voxelindustry.as.client.ClientProxy",
            serverSide = "net.voxelindustry.as.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }
}
