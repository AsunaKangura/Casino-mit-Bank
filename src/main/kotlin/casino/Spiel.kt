package casino

import globals.*

open class Spiel(var name: String, var mineinsatz: Int = 1000, var mult: Int = 2, var jackpot: Int= 1000000) {

    /** Hier wird die Anzeige des Jackpots dargestellt*/
    fun showJackpot(){
        spacerGreenLong()
        println("Der aktueller Jackpot des Casinos: ${greenColor+jackpot } €$resetColor")
        spacerGreenLong()
    }
    /** Hier die Übersicht der Gewinn und Verluste dargestellt sobald man mit einem Spiel aufhört*/
    fun sessionResultShow(){
        spacerGreenLong()
        println("Du hast heute gewonnen: " + greenColor + dailyProfit + " €" + resetColor)
        println("Du hast heute verloren: " + redColor + dailyLoss + " €" + resetColor)
        spacerGreenLong()
    }
}