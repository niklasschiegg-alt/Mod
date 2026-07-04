# Free Mouse – Fabric Mod für Minecraft 26.1.2

Enthält zwei Features:

1. **Maus freigeben (Taste)**: Gibt die Maus per Tastendruck frei (Cursor
   sichtbar, frei beweglich), ohne das Spiel zu pausieren und ohne dass
   sich der Charakter/die Kamera mitdreht. Erneutes Drücken der Taste
   sperrt die Maus wieder normal ins Spiel.

2. **Kein Auto-Pause beim Minimieren**: Verhindert, dass Minecraft beim
   Minimieren des Fensters oder Fokusverlust automatisch einen
   Pause-Screen öffnet und die Welt anhält. Die Welt läuft im Hintergrund
   einfach normal weiter. (Technisch wird dafür einfach die vanilla
   Option "Pause on Lost Focus" dauerhaft erzwungen deaktiviert.)

## Voraussetzungen zum Bauen

- **Java 25** (JDK), z.B. von Adoptium/Temurin
- **Gradle 9.4.0+** (wird über den mitgelieferten Wrapper normalerweise
  automatisch geladen – da hier kein Internetzugriff vorhanden war, fehlt
  der Wrapper (`gradlew`) in diesem Projekt. Am einfachsten: Projekt in
  **IntelliJ IDEA 2025.3+** öffnen, dort automatisch Gradle einrichten
  lassen, oder mit lokal installiertem Gradle 9.4.0 `gradle wrapper`
  ausführen)
- Internetzugang beim ersten Bauen (lädt Minecraft, Fabric Loader,
  Fabric API und Loom herunter)

## Bauen

```bash
gradle build
```

Die fertige `.jar`-Datei liegt danach in `build/libs/`.

## Installation

1. Fabric Loader (>= 0.18.4) für Minecraft 26.1.2 installieren
   (https://fabricmc.net/use/installer/)
2. Fabric API 26.1.2 in den `mods`-Ordner legen
   (https://modrinth.com/mod/fabric-api)
3. Die gebaute `freemouse-1.0.0.jar` ebenfalls in den `mods`-Ordner legen
4. Minecraft mit dem Fabric-Profil starten

## Taste einstellen

In Minecraft unter **Einstellungen → Steuerung → Free Mouse** die
gewünschte Taste zuweisen (standardmäßig ist keine Taste belegt).

## Hinweis

Minecraft 26.1.2 ist die erste unobfuskierte Minecraft-Version und noch
sehr neu. Falls das Mixin (`MouseHandlerMixin.java`) beim Kompilieren
Fehler wirft, weil sich ein Methodenname (`turnPlayer`, `grabMouse`)
minimal geändert hat, in der IDE per "Go to definition" in die Klasse
`MouseHandler` schauen und den Namen im Mixin entsprechend anpassen –
die grundlegende Logik bleibt gleich.

Für `NoAutoPauseMod.java` gilt das Gleiche für das Feld
`options.pauseOnLostFocus` in der Klasse `Options` – sollte es anders
heißen, einfach in der IDE nachschauen und anpassen.
