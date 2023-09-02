package casino.cardgames

import bank.konten.User
import casino.Spiel
import casino.cardgames.card.Card
import casino.cardgames.card.Symbol
import casino.cardgames.card.Wert
import casino.slotmaschine.Slotmaschine
import globals.*
import klassen.Meldungen

class HoeherTiefer(name: String) : Spiel(name) {

    val gameDeck: MutableList<Card> = mutableListOf()

    /**
     * Initialisiert das Spieldeck der Karten für das Kartenspiel.
     * Fügt alle möglichen Kombinationen von Symbolen und Werten zu `gameDeck` hinzu.
     */
    init {
        // *Durchlaufe alle möglichen Kombinationen von Symbolen und Werten
        for (symbol in Symbol.entries) {
            for (wert in Wert.entries) {
                // *Füge eine Karte mit dem aktuellen Symbol und Wert zum Spieldeck hinzu
                gameDeck.add(Card(symbol, wert))
            }
        }
    }

    /**
     * Zeigt das Hauptmenü des "Höher oder Tiefer" Spiels und ermöglicht dem Benutzer die Auswahl des Spielmodus.
     * @param user Der Benutzer, der das Spiel spielt.
     */
    fun gameMenu(user: User) {
        // *Zeige das Hauptmenü des Spiels
        placeholder(6)
        spacerGreenLong()
        println("\t\t\t\t\t Höher oder Tiefer Spiel")
        spacerGreenLong()
        println("Du hast die Möglichkeit mit dem Mindesteinsatz ${greenColor + mineinsatz} €$resetColor zu spielen")
        spacerGreenLong()
        println("Möchtest du mit dem Mindesteinsatz spielen?")
        println("1 = Ja | 2 = Nein | 3 = Abbrechen")

        // *Lese die Benutzereingabe ein und überprüfe, ob es eine gültige Zahl ist
        val inputUser = readln()
        if (inputNumberCheck(inputUser)) {
            when (inputUser) {
                1.toString() -> {
                    minBetPlay(user) // *Starte das Spiel mit Mindesteinsatz
                }

                2.toString() -> {
                    customBetPlay(user) // *Starte das Spiel mit individuellem Einsatz
                }
                3.toString() -> {
                    casinoInstanz.mainMenuCasino(user)
                }
            }
        } else {
            // *Zeige eine Fehlermeldung, wenn die Benutzereingabe keine gültige Zahl ist
            Meldungen("Du hast hier nur 2 Menüoptionen. Bitte wähle 1 oder 2 aus, um fortzufahren.").errorMessage()
        }
    }

    /**
     * Mischt das übergebene Kartendeck zufällig.
     * @param deck Das zu mischende Kartendeck.
     */
    fun mischen(deck: MutableList<Card>) {
        // *Verwendet die shuffle() Funktion, um das Deck zufällig zu mischen.
        deck.shuffle()
    }

    /**
     * Zieht eine Karte vom angegebenen Kartendeck.
     * @param deck Das Kartendeck, von dem eine Karte gezogen werden soll.
     * @return Die gezogene Karte.
     */
    fun eineKarteZiehen(deck: MutableList<Card>): Card {
        // *Speichert die erste Karte im Deck
        var karte = deck.first()
        // *Entfernt die gezogene Karte aus dem Deck
        deck.remove(deck.first())
        // *Gibt die gezogene Karte zurück
        return karte
    }

    /**
     * Führt das Spiel "Höher oder Tiefer" mit Mindesteinsatz für den Benutzer durch.
     * @param user Der Benutzer, der spielt.
     */
    fun minBetPlay(user: User) {
        // *Versuche, den Kontostand des Benutzers im Casino abzurufen
        val casinoValue = bankInstanz.Kundenliste[user.id]?.getKontoById(2)

        // *Initialisiere Variablen für das Spiel "Höher oder Tiefer"
        var highOrLowerAktiv = true // *Spielaktivitätsflag

        var cardOnTable = eineKarteZiehen(gameDeck) // *Zufällige Karte vom Deck ziehen
        var cardOnTableValue = cardOnTable.wert.cardValue // *Wert der Karte auf dem Tisch

        // *Die Schleife läuft so lange, wie das Spiel "Höher oder Tiefer" aktiv ist
        while (highOrLowerAktiv) {
            // *Überprüfe, ob der Benutzer genügend Geld hat
            if (casinoValue != null) {
                placeholder(6)
                spacerGreenLong()
                println("\t\t\t \"Höher oder Tiefer\" Mindesteinsatz Spiel")

                // *Überprüfe, ob der Mindesteinsatz den Kontostand überschreitet
                if (mineinsatz > casinoValue.kontoStand) {
                    // *Zeige eine Fehlermeldung und Anweisungen, um das Spiel zu beenden oder fortzufahren
                    Meldungen("Betrag zu hoch!. Du hast nicht genug Geld zur Verfügung!").errorMessage()
                    println("Zum Beenden ENTER drücken")
                    val input = readln()
                    if (input == "") {
                        mineinsatz = 0 // *Setze den Wetteinsatz auf 0 für eine erneute Eingabe
                        moneyEmpty = true
                        casinoInstanz.mainMenuCasino(user) // *Gehe zurück zum Hauptmenü des Casinos
                    }
                }

                // *Wenn ausreichend Geld vorhanden ist
                if (!moneyEmpty) {
                    mischen(gameDeck) // *Mische das Kartendeck
                    spacerGreenLong()
                    if (casinoValue != null) {
                        // *Zeige den verfügbaren Kontostand des Benutzers im Casino
                        println("Du hast ${greenColor + casinoValue.kontoStand.toInt() + resetColor} € zur Verfügung!")
                    }
                    spacerGreenLong()

                    // *Zeige die aktuelle Karte auf dem Tisch und frage den Benutzer nach seiner Wahl
                    println("Ist die nächste Zahl höher oder tiefer als: $cardOnTable")
                    println("1 = höher | 2 = tiefer | 3 = Abbrechen")
                    val inputUser = readln()
                    val nextCard = eineKarteZiehen(gameDeck) // *Ziehe die nächste Karte vom Deck
                    val nextCardValue = nextCard.wert.cardValue // *Wert der nächsten Karte

                    // *Überprüfe, ob die Benutzereingabe eine gültige Zahl ist
                    if (inputNumberCheck(inputUser)) {
                        when (inputUser) {
                            1.toString() -> {
                                casinoValue.kontoStand -= mineinsatz
                                if (cardOnTableValue < nextCardValue) {
                                    println(nextCard)
                                    Meldungen("Du hast ${mineinsatz * mult} € gewonnen").successMessage()
                                    casinoValue.kontoStand += mineinsatz * mult
                                } else {
                                    println(nextCard)
                                    Meldungen("Du hast verloren").infoMessage()
                                    casinoValue.kontoStand -= mineinsatz
                                }
                                cardOnTable = nextCard
                                cardOnTableValue = nextCardValue
                            }

                            2.toString() -> {
                                casinoValue.kontoStand -= mineinsatz
                                if (cardOnTableValue > nextCardValue) {
                                    println(nextCard)
                                    Meldungen("Du hast ${mineinsatz * mult} € gewonnen").successMessage()
                                    casinoValue.kontoStand += mineinsatz * mult
                                } else {
                                    println(nextCard)
                                    Meldungen("Du hast verloren").infoMessage()
                                    casinoValue.kontoStand -= mineinsatz
                                }
                                cardOnTable = nextCard
                                cardOnTableValue = nextCardValue
                            }

                            3.toString() -> {
                                highOrLowerAktiv = false // *Beende das Spiel "Höher oder Tiefer"
                                break
                            }
                        }
                    } else {
                        Meldungen("Du darfst nur Zahlen eingeben").errorMessage()
                    }
                }
            }
            // *Zeige das Menü für die Fortsetzung des Spiels oder das Beenden des Spiels
            println("\nTreffe eine Auswahl")
            println("1 = Weiter spielen | 2 = Spiel beenden")
            var inputUser = readln()
            if (inputNumberCheck(inputUser)) {
                when (inputUser) {
                    2.toString() -> {
                        Cardganes().mainMenu(user)
                    }
                }
            }else {
                Meldungen("Falsche Eingabe.").errorMessage()
            }
        }
    }

    /**
     * Führt das Spiel "Höher oder Tiefer" mit eigenem Einsatz für den Benutzer durch.
     * @param user Der Benutzer, der spielt.
     */
    fun customBetPlay(user: User) {
        val casinoValue = bankInstanz.Kundenliste[user.id].getKontoById(2)
        var highOrLowerAktiv = true
        var cardOnTable = eineKarteZiehen(gameDeck)
        var cardOnTableValue = cardOnTable.wert.cardValue
        while (highOrLowerAktiv) {
            placeholder(6)
            spacerGreenLong()
            println("\t\t\t \"Höher oder Tiefer\" Mindesteinsatz Spiel")
            showJackpot()
            if (casinoValue != null) {
                // * Hier wird geprüft, ob der Mindesteinsatz nicht das KontoValue überschreitet
                print("Du hast ${greenColor + casinoValue.kontoStand.toInt()}€$resetColor zur Verfügung! Wieviel € möchtest du setzen: ")
                do {
                    try {
                        bet =
                            readln().toInt() // Der Wetteinsatz wird hier eingelesen und in eine Ganzzahl (Int) umgewandelt.
                        if (bet > casinoValue.kontoStand) {
                            Meldungen("Betrag zu hoch!. Du hast nicht genug Geld zu Verfügung!").errorMessage()
                            println("Zum Beenden ENTER drücken")
                            val input = readln()
                            if (input == "") {
                                bet = 0 // Der Wetteinsatz wird auf 0 gesetzt, um eine erneute Eingabe
                                moneyEmpty = true
                                casinoInstanz.mainMenuCasino(user)
                                break
                            }
                        }
                    } catch (e: Exception) {
                        slotmaschineAktiv = false
                        Meldungen("Ungültige Eingabe!").errorMessage()
                        bet = 0
                    }
                } while (bet == 0)
                if (!moneyEmpty) {
                    mischen(gameDeck)
                    spacerGreenLong()
                    println("Ist die nächste Zahl höher oder tiefer als : $cardOnTable")
                    println("1 = höher | 2 = tiefer | 3 = Abbrechen")
                    val inputUser = readln()
                    val nextCard = eineKarteZiehen(gameDeck)
                    val nextCardValue = nextCard.wert.cardValue
                    if (inputNumberCheck(inputUser)) {
                        when (inputUser) {
                            1.toString() -> {
                                casinoValue.kontoStand -= bet
                                if (cardOnTableValue < nextCardValue) {
                                    println(nextCard)
                                    Meldungen("Du hast ${bet * mult} €  gewonnen").successMessage()
                                    var winning = bet * mult
                                    dailyProfit += winning - bet
                                    casinoValue.kontoStand += winning
                                } else {
                                    println(nextCard)
                                    Meldungen("Du hast verloren").infoMessage()
                                    dailyLoss += bet
                                    jackpot += bet
                                    casinoValue.kontoStand -= bet
                                }
                                cardOnTable = nextCard
                                cardOnTableValue = nextCardValue
                            }

                            2.toString() -> {
                                casinoValue.kontoStand -= bet
                                if (cardOnTableValue > nextCardValue) {
                                    println(nextCard)
                                    Meldungen("Du hast ${bet * mult} € gewonnen").successMessage()
                                    var winning = bet * mult
                                    dailyProfit += winning - bet
                                    casinoValue.kontoStand += winning
                                } else {
                                    println(nextCard)
                                    Meldungen("Du hast verloren").infoMessage()
                                    dailyLoss += bet
                                    jackpot += bet
                                    casinoValue.kontoStand -= bet
                                }
                                cardOnTable = nextCard
                                cardOnTableValue = nextCardValue
                            }

                            3.toString() -> {
                                highOrLowerAktiv = false
                                break
                            }
                        }
                    } else {
                        Meldungen("Du darfst nur Zahlen eingeben").errorMessage()
                    }
                }
            }
            // *Zeige das Menü für die Fortsetzung des Spiels oder das Beenden des Spiels
            println("\nTreffe eine Auswahl")
            println("1 = Weiter spielen | 2 = Spiel beenden")
            var inputUser = readln()
            if (inputNumberCheck(inputUser)) {
                when (inputUser) {
                    2.toString() -> {
                        Cardganes().mainMenu(user)
                    }
                }
            }else {
                Meldungen("Falsche Eingabe.").errorMessage()
            }
        }
    }


}