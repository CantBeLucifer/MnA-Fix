package com.lucifer.mnafix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractSelectionList.class)
public class AbstractSelectionListAccessor {
    @Shadow @Final protected Minecraft minecraft;
}
