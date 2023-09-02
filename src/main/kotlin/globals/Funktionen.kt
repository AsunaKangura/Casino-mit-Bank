package globals

package globals

import bank.*
import bank.konten.*
import casino.*
import klassen.*
import kotlin.system.exitProcess

fun inputNumberCheck(input: String): Boolean {
    return input.matches(Regex("[1-9]"))
}

/** Die Funktion mainMenu nimmt einen Parameter "user" vom Typ "User" entgegen und ist verantwortlich für das Hauptmenü des Programms.
 *
 */
fun mainMenu(user: User) {

    // *Eine while-Schleife, die das Hauptprogramm aktiv hält, bis der Benutzer sich ausloggt oder das Programm beendet wird.
    while (programmAktiv) {
        // *Ausgabe des Menüs für den Benutzer.
        placeholder(7)
        spacerGreen()
        println("\t\t\t\t\t Menüauswahl")
        spacerGreen()
        println("1 = Zum Casino | 2 = Zur Bank | 3 = Logout")

        // *Benutzereingabe lesen und in der Variable "inputUser" speichern.
        var inputUser = readln()

        // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist (mithilfe der Funktion "inputNumberCheck").
        if (inputNumberCheck(inputUser)) {
            // *Die "when"-Anweisung ermöglicht das Prüfen von verschiedenen Fällen (ähnlich dem Switch-Case in anderen Programmiersprachen).
            when (inputUser) {
                // *Fall 1: Der Benutzer hat "1" eingegeben, um zum Casino zu gehen.
                1.toString() -> {
                    // *Setze die Variable "casinoAktiv" auf "true", um den Casino-Modus zu aktivieren.
                    casinoAktiv = true
                    // *Rufe die Funktion "mainMenuCasino" der "casinoInstanz" auf, um das Casino-Menü für den Benutzer anzuzeigen.
                    casinoInstanz.mainMenuCasino(user)
                }

                // *Fall 2: Der Benutzer hat "2" eingegeben, um zur Bank zu gehen.
                2.toString() -> {
                    // *Setze die Variable "bankAktiv" auf "true", um den Bank-Modus zu aktivieren.
                    bankAktiv = true
                    // *Rufe die Funktion "mainMenuBank" der "bankInstanz" auf, um das Bank-Menü für den Benutzer anzuzeigen.
                    bankInstanz.mainMenuBank(user)
                }

                // *Fall 3: Der Benutzer hat "3" eingegeben, um sich auszuloggen und zum Hauptmenü zurückzukehren.
                3.toString() -> {
                    // *Setze die Variable "userIsLogin" auf einen leeren String, um den Benutzer auszuloggen.
                    userIsLogin = ""
                    // *Setze die Variable "programmStart" auf "true", um zum Hauptmenü zurückzukehren.
                    programmStart = true
                    // *Setze die Variable "programmAktiv" auf "false", um das Hauptprogramm zu beenden.
                    programmAktiv = false
                }
            }
        }else {
            // *Rufe die Funktion "infoMessage" der Klasse "Meldungen" mit der Meldung "Es gibt hier nur Menüpunkte 1 bis 3" auf,
            // *um den Benutzer darüber zu informieren, dass nur die Menüpunkte 1 bis 3 erlaubt sind.
            Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
        }
    }
}

fun placeholder (ammount: Int){
    repeat(ammount){
        println()
    }
}

fun spacerGreen(){
    println("$greenColor=====================================================$resetColor")
}

fun spacerGreenLong(){
    println("$greenColor=====================================================================$resetColor")
}


