package bank.konten

import globals.spacerGreen
import klassen.Meldungen
import bank.*
import globals.bankInstanz

// * =========================================================
// * ================= Klasse Konten =========================
// * =========================================================
// * Current Version 1.2
// * =========================================================
// * erstellt durch: AsunaKangura
// * =========================================================
// * Sprache : Kotlin
// * =========================================================
// * 1.0 = Die Funktion "payOut" wird verwendet, um eine Auszahlung vom gewünschte Konto vorzunehmen
// * 1.1 = Die Funktion "payIn" wird verwendet, um eine Einzahlung vom gewünschte Konto vorzunehmen
// * 1.2 = Die Funktion "transfer" wird verwendet, um die Überweisung zwischen den beiden ausgewählten Konten durchzuführen


open class Konten(var kontenID: Int, var kontoStand: Int)  {

    fun payout() {
        var test = false
        while (!test) {
            spacerGreen()
            println("Dein aktueller Kontostand ist: " + this.kontoStand + " €")
            spacerGreen()
            println("Bitte geben den Betrag ein den du abheben möchtest")
            var inputUser = readln().toInt()

            if (inputUser > this.kontoStand) {
                Meldungen("Du hast nicht genug Geld auf deinem Konto").errorMessage()
            } else {
                this.kontoStand -= inputUser
                test = true
                Meldungen("Der Betrag von $inputUser € wurde abgehoben").successMessage()
            }

        }
    }

    fun payIn() {
        var test = false
        while (!test) {
            spacerGreen()
            println("Dein aktueller Kontostand ist: " + this.kontoStand + " €")
            spacerGreen()
            println("Bitte geben den Betrag ein den du einzahlen möchtest")
            var inputUser = readln().toInt()
            this.kontoStand += inputUser
            test = true
            Meldungen("Der Betrag von $inputUser € wurde eingezahlt").successMessage()
        }
    }

    fun transfer(user: User, transferKonto: Int, reciveKonto: Int) {
        var konto1 = user.getKontoById(transferKonto)
        var konto2 = user.getKontoById(reciveKonto)
        println("Wie viel geld möchtest du überweisen?")
        var inputUser = readln().toInt()
        if (konto1 != null && konto2 != null) {
            if (inputUser <= konto1.kontoStand){
                konto1.kontoStand -= inputUser
                konto2.kontoStand += inputUser
                Meldungen("Die Überweisung von $inputUser € wurde durchgeführt").successMessage()
            }else {
                Meldungen("Der Betrag ist zu hoch.").errorMessage()
            }
        }


    }

}