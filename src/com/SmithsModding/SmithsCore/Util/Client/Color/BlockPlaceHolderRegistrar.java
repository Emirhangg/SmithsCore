package com.SmithsModding.SmithsCore.Util.Client.Color;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/**
 * The IIconRegistrar used to retrieve IIcons in form of PlaceHolders from Blocks for Color Sampling.
 *
 * Created by Orion
 * Created on 15.06.2015
 * 13:13
 * <p/>
 * Copyrighted according to Project specific license
 */
public class BlockPlaceHolderRegistrar implements IIconRegister {
    @Override
    public IIcon registerIcon(String pTextureLocation) {
        return new BlockIconPlaceHolder(new ResourceLocation(pTextureLocation));
    }
}
