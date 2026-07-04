package com.example.freemouse.mixin;

import com.example.freemouse.FreeMouseMod;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Verhindert zwei Dinge, solange FreeMouseMod.freeMouseActive == true ist:
 *
 * 1) turnPlayer() - dreht normalerweise die Kamera/den Charakter anhand der
 *    Mausbewegung. Wird abgebrochen, damit sich der Charakter beim freien
 *    Bewegen der Maus NICHT mitdreht.
 *
 * 2) grabMouse() - fängt den Cursor normalerweise wieder ein (z.B. bei einem
 *    Klick ins Spielfenster). Wird abgebrochen, damit die Maus so lange frei
 *    bleibt, bis der Spieler die Taste erneut drückt (die dann selbst
 *    grabMouse() aufruft, nachdem sie das Flag zurückgesetzt hat).
 *
 * Hinweis: Sollte "turnPlayer" oder "grabMouse" in deiner 26.1.2-Kopie anders
 * heißen (offizielle Mappings können sich in Details unterscheiden), einfach
 * per "Go to definition" in der IDE in MouseHandler.class nachsehen und den
 * Methodennamen hier anpassen.
 */
@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    private void freemouse$cancelTurn(CallbackInfo ci) {
        if (FreeMouseMod.freeMouseActive) {
            ci.cancel();
        }
    }

    @Inject(method = "grabMouse", at = @At("HEAD"), cancellable = true)
    private void freemouse$cancelGrab(CallbackInfo ci) {
        if (FreeMouseMod.freeMouseActive) {
            ci.cancel();
        }
    }
}
