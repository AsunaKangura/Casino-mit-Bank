import globals.*
import klassen.Meldungen

import kotlin.system.exitProcess

// * =========================================================
// * =========== Projekt Zock dich ab Casino =================
// * =========================================================
// * Current Version 1.0
// * =========================================================
// * erstellt durch: AsunaKangura
// * =========================================================
// * Sprache : Kotlin
// * =========================================================


fun main() {
    // *Eine Variable, die angibt, ob das Programm gestartet ist oder nicht. Hier wird sie auf 'true' gesetzt, um die Schleife zu starten.

    // *Eine Schleife, die solange läuft, wie das Programm gestartet ist.
    while (programmStart) {
        // *Aufruf der Funktion "spacerGreen()", die vermutlich einen grünen Absatz ausgibt.
        spacerGreen()

        // *Willkommensnachricht.
        println("\t Willkommen im Online \"Zock Dich ab Casino\"")

        // *Erneuter Aufruf der Funktion "spacerGreen()" für einen grünen Absatz.
        spacerGreen()

        // *Anzeige des Startmenüs.
        println("Startmenü")
        println("1 = Login | 2 = Register | 3 = Beenden")

        // *Eingabeaufforderung an den Benutzer, um eine Auswahl zu treffen.
        var inputUser = readln()

        // *Überprüfung, ob die Eingabe eine gültige Zahl ist.
        if (inputNumberCheck(inputUser)) {
            // *Wenn die Eingabe eine Zahl ist, wird sie in eine Zahl umgewandelt und verglichen.
            when (inputUser) {
                // *Wenn die Eingabe "1" ist, wird die Funktion "login()" aufgerufen.
                1.toString() -> {
                    bankInstanz.login()
                }
                // *Wenn die Eingabe "2" ist, wird die Funktion "register()" aufgerufen.
                2.toString() -> {
                    bankInstanz.register()
                }
                // *Wenn die Eingabe "3" ist, wird das Programm mit dem Exit-Code 0 beendet.
                3.toString() -> {
                    Meldungen("Du hast das Programm erfolgreich Beendet").successMessage()
                    exitProcess(0)
                }
                // *Falls die Eingabe keine der erwarteten Zahlen ist, wird eine Meldung ausgegeben.
                else -> {
                    Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
                }
            }
        } else {
            // *Falls die Eingabe keine Zahl ist, wird eine Fehlermeldung ausgegeben.
            Meldungen("Es dürfen hier nur Zahlen eingegeben werden.").errorMessage()
        }
    }
}



