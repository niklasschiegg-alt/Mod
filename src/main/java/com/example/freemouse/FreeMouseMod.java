package com.example.freemouse;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

/**
 * Erlaubt es, die Maus per Tastendruck "freizugeben": der Cursor wird sichtbar
 * und frei beweglich, ohne dass sich die Blickrichtung des Charakters ändert
 * und ohne dass das Spiel (z.B. durch ein Menü) pausiert wird.
 *
 * Die Taste ist über das normale Minecraft-Keybinding-System registriert und
 * kann daher ganz normal unter Einstellungen -> Steuerung geändert werden.
 * Standardmäßig ist sie nicht belegt (KEY_UNKNOWN), damit sie keine andere
 * Taste überschreibt.
 */
public class FreeMouseMod implements ClientModInitializer {

    public static KeyMapping toggleKey;

    /** true = Maus ist aktuell "frei", Kamera reagiert nicht auf Mausbewegung */
    public static volatile boolean freeMouseActive = false;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.freemouse.toggle",
                InputConstants.Type.KEYSYM,
                InputConstants.UNKNOWN.getValue(), // kein Standardwert -> Spieler bindet selbst
                "category.freemouse"
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
            // Flag zuerst setzen, damit der Mixin die eigentliche Freigabe nicht blockiert
            freeMouseActive = true;
            client.mouseHandler.releaseMouse();
        } else {
            // Flag zuerst zurücksetzen, damit grabMouse() nicht vom Mixin abgefangen wird
            freeMouseActive = false;
            client.mouseHandler.grabMouse();
        }
    }
}
