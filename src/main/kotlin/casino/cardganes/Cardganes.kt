package casino.cardgames

import bank.konten.*
import globals.*
import klassen.Meldungen
import casino.cardgames.card.*
import casino.cardgames.*

open class Cardganes(){

    /**
     * Zeigt das Hauptmenü der Card Games und ermöglicht dem Benutzer die Auswahl des gewünschten Spiels.
     * @param user Der Benutzer, der das Menü aufruft.
     */
    fun mainMenu(user: User) {
        var aktiv = true // *Aktivitätsflag für die Schleife
        // *Die Schleife läuft so lange, wie das Hauptmenü aktiv ist
        while (aktiv) {
            placeholder(4)
            spacerGreenLong()
            println("\t\t\t\t\t  Hauptmenü Card Games")
            spacerGreenLong()
            println("Du hast nun folgende Menüauswahl")
            println("1 = Höher oder Tiefer | 2 = Abbrechen")

            // *Lese die Benutzereingabe ein und überprüfe, ob es eine gültige Zahl ist
            var inputUser = readln()
            if (inputNumberCheck(inputUser)) {
                when (inputUser) {
                    1.toString() -> {
                        // *Starte das "Höher oder Tiefer" Spiel und speichere den Rückgabewert in der Variablen
                        var highOrLower = casino.cardgames.HoeherTiefer("Höher oder tiefer").gameMenu(user)
                    }

                    2.toString() -> {
                        aktiv = false // *Beende das Hauptmenü
                        break
                    }
                }
            } else {
                // *Zeige eine Fehlermeldung, wenn die Benutzereingabe keine gültige Zahl ist
                Meldungen("Du hast hier nur zwei Menüoptionen. Bitte wähle 1 oder 2 aus, um fortzufahren.").errorMessage()
            }
        }
    }


}
