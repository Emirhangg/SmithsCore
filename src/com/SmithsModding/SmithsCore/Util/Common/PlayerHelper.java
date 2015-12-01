package com.SmithsModding.SmithsCore.Util.Common;

import com.SmithsModding.SmithsCore.Common.Player.Management.PlayerManager;
import com.SmithsModding.SmithsCore.SmithsCore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;

/**
 * Created by Orion
 * Created on 22.11.2015
 * 22:56
 * <p/>
 * Copyrighted according to Project specific license
 */
public class PlayerHelper {

    /**
     * Iterates over al connected players to find one with the given ID, or returns null if none is found.
     *
     * @param pID The ID to search for.
     * @return A Instance of EntityPlayer with that UniqueID or null if none matches.
     */
    public EntityPlayer getPlayerFromID(UUID pID) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            if (PlayerManager.getInstance().getServerSidedJoinedMap().containsKey(pID)) {
                return PlayerManager.getInstance().getServerSidedJoinedMap().get(pID);
            }

            SmithsCore.getLogger().info("[PlayerHelper] Falling back on ServerConfiguration Manager - No Player found in PlayerManager!");
        }

        for (Object tPlayerObject : FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList) {
            EntityPlayer tPlayer = (EntityPlayer) tPlayerObject;

            if (tPlayer.getUniqueID().equals(pID))
                return tPlayer;
        }

        return null;
    }
}