package com.therighthon.afc.compat.jade;

import com.therighthon.afc.common.blocks.TapBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(value = "afc")
public class AFCJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TapBlockEntityProvider.INSTANCE, TapBlock.class);
    }
} 