package com.example.freemouse;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class FreeMouseMod implements ClientModInitializer {

    private static final KeyMapping.Category CATEGORY =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath("freemouse", "main"));
    
    public static KeyMapping toggleKey;

    public static volatile boolean freeMouseActive = false;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.freemouse.toggle",
                InputConstants.Type.KEYSYM,
                InputConstants.UNKNOWN.getValue(),
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) {
                setFreeMouse(client, !freeMouseActive);
            }
        });
    }

    public static void setFreeMouse(Minecraft client, boolean enabled) {
        if (client == null || client.mouseHandler == null) {
            return;
        }

        if (enabled) {
            freeMouseActive = true;
            client.mouseHandler.releaseMouse();
        } else {
            freeMouseActive = false;
            client.mouseHandler.grabMouse();
        }
    }
}
