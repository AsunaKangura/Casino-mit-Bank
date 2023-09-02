package casino.cardgames.card

import globals.blueColor
import globals.redColor
import globals.resetColor

enum class Symbol(val cardsuit: String, var german: String){
    HERZ("\u2665", "Herz"),
    KARO("\u2666","Karo"),
    KREUZ("\u2663", "Kreuz"),
    PIK("\u2660", "Pik")
}