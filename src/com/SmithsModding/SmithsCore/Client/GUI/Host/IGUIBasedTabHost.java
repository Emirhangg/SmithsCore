package com.SmithsModding.SmithsCore.Client.GUI.Host;

import com.SmithsModding.SmithsCore.Client.GUI.Tabs.Core.IGUITab;

/**
 * Created by marcf on 1/15/2016.
 */
public interface IGUIBasedTabHost extends IGUIBasedComponentHost
{

    /**
     * M<ethod called by the gui system to intialize this tab host.
     *
     * @param host The host for the tabs.
     */
    void registerTabs(IGUIBasedTabHost host);

    /**
     * Method used to register a new Tab to this Host.
     * Should be called from the registerTabs method to handle sub component init properly.
     *
     * @param tab The new Tab to register.
     */
    void registerNewTab(IGUITab tab);

}