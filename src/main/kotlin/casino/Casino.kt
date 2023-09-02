package casino

import bank.konten.User
import casino.slotmaschine.Slotmaschine
import globals.*
import casino.slotmaschine.*
import klassen.Meldungen

class Casino() {

    /** Diese Methoder stellt das Menü für das Casino dar, hier kann man dann entscheiden welches Spiel man spielen möchte. n
     *
     */
    fun mainMenuCasino(user: User) {

        // *Die Schleife läuft so lange, wie die casinoAktiv-Flag gesetzt ist
        while (casinoAktiv) {
            // *Versuche, den Kontostand des Benutzers im Casino abzurufen
            val casinoValue = bankInstanz.Kundenliste[user.id]?.getKontoById(2)

            // *Überprüfe, ob der Kontostand im Casino nicht null ist
            if (casinoValue != null) {
                // *Falls das Casino aktiv ist und das tägliche Ergebnis nicht vorhanden ist, rufe die placeholder-Funktion mit dem Parameter 4 auf
                if (casinoAktiv && !dailyResult) {
                    placeholder(4)
                }

                // *Rufe die spacerGreenLong-Funktion auf, um eine visuelle Trennung anzuzeigen
                spacerGreenLong()
                // *Gib den Titel "Zock Dich ab Casino" aus
                println("\t\t\t\t\t\tZock Dich ab Casino")
                // *Zeige eine visuelle Trennung an
                spacerGreenLong()
                // *Gib dem Benutzer eine Menüauswahl
                println("Du hast nun folgende Menüauswahl")

                // *Überprüfe, ob der Kontostand des Benutzers im Casino 0 ist
                if (casinoValue.kontoStand.toInt() == 0) {
                    // *Gib eine Fehlermeldung aus, wenn der Kontostand 0 ist
                    Meldungen("Da dein Kontostand beträgt: ${casinoValue.kontoStand.toInt()} €\nBitte das Casino verlassen und neues Geld auf dein Casino Konto einzahlen").errorMessage()
                }

                // *Zeige die Optionen des Hauptmenüs an
                println("1 = Slotmaschinen | 2 = Karten Spiele | 3 = Casino verlassen")
                // *Lese die Benutzereingabe und konvertiere sie in einen Integer
                var input = readln().toInt()

                // *Verarbeite die Benutzereingabe
                when (input) {
                    1 -> {
                        // *Setze die Slotmaschinen-Aktivitätsflag
                        slotmaschineAktiv = true
                        // *Rufe das Slotmaschinen-Menü auf
                        casino.slotmaschine.Slotmaschine("Slotmaschine").slotmaschineMenu(user = user)
                    }
                    2 -> {
                        // *Rufe das Menü für Kartenspiele auf
                        casino.cardgames.Cardganes().mainMenu(user)
                    }
                    3 -> {
                        // *Setze die Casino- und Slotmaschinen-Aktivitätsflag zurück und zeige eine Erfolgsmeldung
                        casinoAktiv = false
                        slotmaschineAktiv = false
                        dailyResult = false
                        Meldungen("\nDu hast das Casino verlassen\n").successMessage()
                    }
                }
            }
        }
    }



}