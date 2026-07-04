package com.example.freemouse;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

/**
 * Verhindert, dass Minecraft beim Minimieren / Fokusverlust des Fensters
 * automatisch den Pause-Screen öffnet und die Welt anhält.
 *
 * Minecraft entscheidet das intern über die Option "options.pauseOnLostFocus"
 * (im Options-Menü als "Pause on Lost Focus" sichtbar). Wir erzwingen diese
 * Option jeden Tick auf "false", damit sie sich auch nicht durch Zufall
 * (z.B. Klick im Menü, anderes Mod, Neuladen der options.txt) wieder
 * einschaltet.
 *
 * Ergebnis: Fenster minimieren -> kein Screen erscheint -> Spiel/Welt läuft
 * im Hintergrund normal weiter.
 */
public class NoAutoPauseMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(NoAutoPauseMod::forceDisable);
    }

    private static void forceDisable(Minecraft client) {
        if (client == null || client.options == null) {
            return;
        }
        if (client.options.pauseOnLostFocus) {
            client.options.pauseOnLostFocus = false;
        }
    }
}
