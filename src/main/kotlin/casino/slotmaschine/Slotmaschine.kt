package casino.slotmaschine

import bank.konten.User
import casino.*
import casino.cardgames.Cardganes
import globals.*
import klassen.Meldungen


class Slotmaschine(name: String) : Spiel(name) {

    var winnings = 0

    var symbols: MutableList<String> = mutableListOf(
        pikSymbol,
        karoSymbol,
        herzSymbol,
        kreuzSymbol,
        pikSymbol,
        karoSymbol,
        herzSymbol,
        kreuzSymbol,
        pikSymbol,
        karoSymbol,
        herzSymbol,
        kreuzSymbol
    )

    var lines: MutableList<MutableList<Symbol>> = mutableListOf(
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )

    /**
     * Zeigt das Menü der Slotmaschine an und ermöglicht es dem Benutzer, mit Mindesteinsatz oder einem individuellen Einsatz zu spielen.
     * @param user Der Benutzer, der spielt.
     */
    fun slotmaschineMenu(user: User) {
        // *Zeige visuelle Platzhalter und den Titel des Slotmaschinen-Spiels
        placeholder(6)
        spacerGreenLong()
        println("\t\t\t\t\t\t Slotmaschinen Spiel")
        spacerGreenLong()
        // *Zeige die Möglichkeit an, mit dem Mindesteinsatz zu spielen
        println("Du hast die Möglichkeit mit dem Mindesteinsatz ${greenColor + mineinsatz } €$resetColor zu spielen")
        spacerGreenLong()
        println("Möchtest du mit dem Mindesteinsatz spielen?")
        println("1 = Ja | 2 = Nein | 3 = Abbrechen")
        // *Lies die Benutzereingabe
        var inputUser = readln()

        // *Überprüfe, ob die Benutzereingabe eine gültige Zahl ist
        if (inputNumberCheck(inputUser)) {
            // *Verarbeite die Benutzereingabe
            when (inputUser) {
                1.toString() -> {
                    // *Spiele mit Mindesteinsatz
                    var slotmaschine = casino.slotmaschine.Slotmaschine("SlotmaschineMinBet").minBetPlay(user = user)
                }
                2.toString() -> {
                    // *Spiele mit individuellem Einsatz
                    var slotmaschine = casino.slotmaschine.Slotmaschine("SlotmaschineCustomBet").customBetPlay(user = user)
                }
                3.toString() ->{
                    casinoInstanz.mainMenuCasino(user)
                }
            }
        } else {
            // *Zeige eine Fehlermeldung, wenn die Benutzereingabe ungültig ist
            Meldungen("Du hast hier nur zwei Menüoptionen. Bitte wähle 1 oder 2 aus, um fortzufahren.").errorMessage()
        }
    }

    /**
     * Setzt den Spielzustand zurück.
     * Löscht alle Linien und setzt die Gewinnsumme auf 0 zurück.
     */
    private fun resetPlay() {
        // *Lösche den Inhalt jeder Linie
        lines.forEach { it.clear() }
        // *Setze die Gewinnsumme auf 0 zurück
        winnings = 0
    }

    /**
     * Überprüft auf horizontale Übereinstimmungen auf den Linien und aktualisiert die Gewinne und den Kontostand.
     *
     * @param bet Der Einsatz für das Spiel.
     * @param user Der Benutzer, dessen Spiel überprüft wird.
     */
    private fun checkHorizontal(bet: Int, user: User) {
        // *Versuche, den Kontostand des Benutzers im Casino abzurufen
        val casinoValue = bankInstanz.Kundenliste[user.id].getKontoById(2)

        if (casinoValue != null) {
            // *Iteriere über jede Linie
            for (line in lines) {
                // *Überprüfe, ob die Symbole auf der Linie übereinstimmen
                if (line[0].matches(line[1]) && line[1].matches(line[2])) {
                    // *Markiere die Symbole als Treffer
                    line[0].hit()
                    line[1].hit()
                    line[2].hit()
                    // *Aktualisiere die Gewinnsumme und den Kontostand
                    winnings += bet * 3
                    casinoValue.kontoStand += winnings
                    dailyProfit += winnings - bet
                }

                // *Überprüfe, ob ein Vollbild erreicht wurde
                if (line[0].isHit && line[1].isHit && line[2].isHit) {
                    // *Erhöhe den Kontostand des Benutzers um den Jackpot-Betrag und setze den Jackpot zurück
                    casinoValue.kontoStand += jackpot
                    jackpot = 0
                }
            }
        }
    }

    /**
     * Überprüft auf diagonale Übereinstimmungen auf den Linien und aktualisiert die Gewinne und den Kontostand.
     * @param bet Der Einsatz für das Spiel.
     * @param user Der Benutzer, dessen Spiel überprüft wird.
     */
    private fun checkDiagonal(bet: Int, user: User) {
        // *Versuche, den Kontostand des Benutzers im Casino abzurufen
        val casinoValue = bankInstanz.Kundenliste[user.id]?.getKontoById(2)

        if (casinoValue != null) {
            // *Überprüfe die erste diagonale Linie von links oben nach rechts unten
            if (lines[0][0].matches(lines[1][1]) && lines[1][1].matches(lines[2][2])) {
                // *Markiere die Symbole als Treffer
                lines[0][0].hit()
                lines[1][1].hit()
                lines[2][2].hit()
                // *Aktualisiere die Gewinnsumme und den Kontostand
                winnings += bet * 3
                casinoValue.kontoStand += winnings
                dailyProfit += winnings - bet
            }

            // *Überprüfe die zweite diagonale Linie von rechts oben nach links unten
            if (lines[0][2].matches(lines[1][1]) && lines[1][1].matches(lines[2][0])) {
                // *Markiere die Symbole als Treffer
                lines[0][2].hit()
                lines[1][1].hit()
                lines[2][0].hit()
                // *Aktualisiere die Gewinnsumme und den Kontostand
                winnings += bet * 3
                casinoValue.kontoStand += winnings
                dailyProfit += winnings - bet
            }
        }
    }

    /**
     * Führt das Slotmaschinen-Spiel mit dem Mindesteinsatz für den Benutzer durch.
     * @param user Der Benutzer, der spielt.
     */
    private fun minBetPlay(user: User) {

        // *Versuche, den Kontostand des Benutzers im Casino abzurufen
        val casinoValue = bankInstanz.Kundenliste[user.id]?.getKontoById(2)

        // *Die Schleife läuft so lange, wie die Slotmaschine aktiv ist
        while (slotmaschineAktiv) {
            if (casinoValue != null) {
                // *Zeige visuelle Platzhalter und den Titel des Slotmaschinen-Mindesteinsatz-Spiels
                placeholder(6)
                spacerGreenLong()
                println("\t\t\t\t Slotmaschine Mindesteinsatz Spiel")
                spacerGreenLong()
                // *Zeige den verfügbaren Kontostand des Benutzers im Casino
                println("Du hast auf deinem CasinoKonto: ${greenColor + casinoValue.kontoStand.toInt()} €$resetColor zur Verfügung!")
                spacerGreenLong()

                // *Überprüfe, ob der Mindesteinsatz das Casino-Konto überschreitet
                if (mineinsatz > casinoValue.kontoStand) {
                    // *Zeige eine Fehlermeldung und Anweisungen, um das Spiel zu beenden oder fortzufahren
                    Meldungen("Betrag zu hoch!. Du hast nicht genug Geld zur Verfügung!").errorMessage()
                    println("Zum Beenden ENTER drücken")
                    val input = readln()
                    if (input == "") {
                        mineinsatz = 0 // *Setze den Wetteinsatz auf 0, um eine erneute Eingabe zu ermöglichen
                        moneyEmpty = true
                        casinoInstanz.mainMenuCasino(user) // *Gehe zurück zum Hauptmenü des Casinos
                    }
                }

                // *Wenn ausreichend Geld vorhanden ist
                if (!moneyEmpty) {
                    // *Reduziere den Kontostand des Benutzers um den Mindesteinsatz
                    casinoValue.kontoStand -= mineinsatz
                    print("Drücke Enter, um an der Slotmaschine zu drehen!")
                    readln()
                    // *Erzeuge eine Slotmaschinen-Instanz und starte das Spiel
                    var slotmaschine = Slotmaschine("Slotmaschine")
                    repeat(4) {
                        slotmaschine.showSlotsLines()
                        repeat(19) {
                            Thread.sleep(100)
                            print("-")
                        }
                        placeholder(1)
                    }
                    slotmaschine.playAktiv(mineinsatz, user) // *Starte das Slotmaschinen-Spiel
                    // *Zeige das Menü für die Fortsetzung des Spiels oder das Beenden des Spiels
                    println("\nTreffe eine Auswahl")
                    println("1 = Weiter spielen | 2 = Spiel beenden")
                    var inputUser = readln()
                    if (inputNumberCheck(inputUser)) {
                        when (inputUser) {
                            2.toString() -> {
                                slotmaschine.slotmaschineMenu(user)
                            }
                        }
                    }else {
                        Meldungen("Falsche Eingabe.").errorMessage()
                    }
                }
            }
        }
    }

    /**
     * Führt das Slotmaschinen-Spiel mit einem individuellen Einsatz für den Benutzer durch.
     * @param user Der Benutzer, der spielt.
     */
    private fun customBetPlay(user: User) {
        // *Versuche, den Kontostand des Benutzers im Casino abzurufen
        val casinoValue = bankInstanz.Kundenliste[user.id]?.getKontoById(2)

        // *Die Schleife läuft so lange, wie die Slotmaschine aktiv ist
        while (slotmaschineAktiv) {
            // *Zeige visuelle Platzhalter und den Titel des Slotmaschinen-Spiels mit individuellem Einsatz
            placeholder(4)
            spacerGreenLong()
            println("\t\t\t\t Slotmaschine Spiel freien Einsatz")
            showJackpot()

            if (casinoValue != null) {
                // *Zeige den verfügbaren Kontostand des Benutzers im Casino und frage nach dem Wetteinsatz
                print("Du hast ${greenColor+casinoValue.kontoStand.toInt()}€$resetColor zur Verfügung! Wieviel € möchtest du setzen: ")

                // *Lies den Wetteinsatz ein und überprüfe auf Gültigkeit und ausreichenden Kontostand
                do {
                    try {
                        bet = readln().toInt()
                        if (bet > casinoValue.kontoStand) {
                            // *Zeige eine Fehlermeldung und Anweisungen, um das Spiel zu beenden oder fortzufahren
                            Meldungen("Betrag zu hoch!. Du hast nicht genug Geld zur Verfügung!").errorMessage()
                            println("Zum Beenden ENTER drücken")
                            val input = readln()
                            if (input == "") {
                                bet = 0 // *Setze den Wetteinsatz auf 0 für eine erneute Eingabe
                                moneyEmpty = true
                                casinoInstanz.mainMenuCasino(user) // *Gehe zurück zum Hauptmenü des Casinos
                                break
                            }
                        }
                    } catch (e: Exception) {
                        // *Setze die Slotmaschine-Aktivitätsflag auf false und zeige eine Fehlermeldung bei ungültiger Eingabe
                        slotmaschineAktiv = false
                        Meldungen("Ungültige Eingabe!").errorMessage()
                        bet = 0
                    }
                } while (bet == 0)

                // *Wenn ausreichend Geld vorhanden ist
                if (!moneyEmpty) {
                    // *Reduziere den Kontostand des Benutzers um den individuellen Wetteinsatz
                    casinoValue.kontoStand -= bet
                    print("Drücke Enter, um an der Slotmaschine zu drehen!")
                    readln()
                    // *Erzeuge eine Slotmaschinen-Instanz und starte das Spiel
                    var slotmaschine = Slotmaschine("Slotmaschine")
                    repeat(4) {
                        slotmaschine.showSlotsLines()
                        repeat(19) {
                            Thread.sleep(100)
                            print("-")
                        }
                        placeholder(1)
                    }
                    slotmaschine.playAktiv(bet, user) // *Starte das Slotmaschinen-Spiel

                    // *Zeige das Menü für die Fortsetzung des Spiels oder das Beenden des Spiels
                    println("\nTreffe eine Auswahl")
                    println("1 = Weiter spielen | 2 = Spiel beenden")
                    var inputUser = readln()
                    if (inputNumberCheck(inputUser)) {
                        when (inputUser) {
                            2.toString() -> {
                                slotmaschine.slotmaschineMenu(user)
                            }
                        }
                    }else {
                        Meldungen("Falsche Eingabe.").errorMessage()
                    }
                }
            }
        }
    }

    /**
     * Zeigt die Symbole auf den Slotmaschinen-Linien an.
     * Fügt zufällige Symbole zu den Linien hinzu, zeigt die Linien und setzt den Spielzustand zurück.
     */
    private fun showSlotsLines() {
        // *Füge zufällige Symbole zu den Linien hinzu
        for (i in 0..2) {
            repeat(3) {
                lines[i].add(Symbol(symbols.random()))
            }
        }

        println("┌─────────────────┐")
        for(line in lines){
            // Die beiden Println in zeile 319 und 321 sind von Google Philipp
            println("| "+ line.joinToString( " | ") + " | ")
        }
        println("└─────────────────┘")

        // *Setze den Spielzustand zurück
        resetPlay()
    }

    /**
     * Führt das aktive Slotmaschinen-Spiel durch, überprüft Gewinne und Verluste und zeigt Ergebnismeldungen.
     * @param bet Der Einsatz des Benutzers für das Spiel.
     * @param user Der Benutzer, der spielt.
     */
    private fun playAktiv(bet: Int, user: User) {
        // *Füge zufällige Symbole zu den Slotmaschinen-Linien hinzu
        for (i in 0..2) {
            repeat(3) {
                lines[i].add(Symbol(symbols.random()))
            }
        }

        // *Überprüfe auf horizontale und diagonale Gewinnkombinationen
        checkHorizontal(bet, user)
        checkDiagonal(bet, user)

        println("┌─────────────────┐")
        for(line in lines){
            // Die beiden Println in zeile 319 und 321 sind von Google Philipp
            println("| "+ line.joinToString( " | ") + " | ")
        }
        println("└─────────────────┘")

        // *Überprüfe, ob Gewinne erzielt wurden oder nicht
        if (winnings == 0) {
            // *Aktualisiere täglichen Verlust und Jackpot, zeige Verlustmeldung
            dailyLoss += bet
            jackpot += bet
            Meldungen("Du hast ${winnings - bet}€ verloren").errorMessage()
        } else {
            // *Zeige Gewinnmeldung
            Meldungen("Du hast ${winnings - bet}€ gewonnen").successMessage()
        }

        // *Setze den Spielzustand zurück
        resetPlay()
    }



}


