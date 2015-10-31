package com.SmithsModding.SmithsCore.Common.Proxy;


import com.SmithsModding.SmithsCore.Common.Handlers.Network.CommonNetworkableEventHandler;
import com.SmithsModding.SmithsCore.Network.Event.EventNetworkManager;
import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.relauncher.Side;

import java.io.File;

/**
 * Common class used to manage code that runs on both sides of Minecraft.
 * It is the common point of entry after the Modclass receives notice of a Init-state update, on the dedicated server side,
 * through one of his eventhandlers.
 * <p/>
 * Created by Orion
 * Created on 26.10.2015
 * 12:48
 * <p/>
 * Copyrighted according to Project specific license
 */
public class CoreCommonProxy {

    /**
     * Function used to prepare mods and plugins for the Init-Phase
     * <p/>
     * The configuration handler is initialized by a different function.
     */
    public void preInit() {
        registerEventHandlers();
    }

    /**
     * Function used to initialize this mod.
     * It sets parameters used in most of its functions as common mod for SmithsModding mods.
     * Also initializes most of the Network code for the Server.
     */
    public void Init() {
        EventNetworkManager.Init();
    }

    /**
     * Function used to change behaviour of this mod based on loaded mods.
     */
    public void postInit() {

    }

    /**
     * Function used to initialize the configuration classes that are common between client and server
     *
     * @param pSuggestedConfigFile The file (or directory given that Java makes no difference between the two) that is suggested to contain configuration options for this mod.
     *                             This parameter is in normal situations populated with the suggested configuration File from the PreInit event.
     * @see File
     * @see cpw.mods.fml.common.event.FMLPreInitializationEvent
     */
    public void configInit(File pSuggestedConfigFile) {

    }

    /**
     * Function used to get the effective running side.
     * Basically indicates if elements marked with SideOnly(Side.Client) or SideOnly(Side.SERVER) are available to the JVM
     * As the Client side of this Proxy inherits from this Common one it overrides this function and returns Side.Client instead of value returned from here.
     * <p/>
     * The value returned here does not indicate if the user is running a dedicated or a internal server. It only indicated that the instance of minecraft has GUI-Capabilities or not.
     *
     * @return The effective running Side of this Proxy
     * @see cpw.mods.fml.relauncher.SideOnly
     * @see com.SmithsModding.SmithsCore.Client.Proxy.CoreClientProxy
     */
    public Side getEffectiveSide() {
        return Side.SERVER;
    }

    /**
     * Function called from preInit() to register all of the Eventhandlers used by Common code.
     */
    protected void registerEventHandlers() {
        SmithsCore.getRegistry().getCommonBus().register(new CommonNetworkableEventHandler());
    }
}
