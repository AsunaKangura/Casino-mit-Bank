package casino.cardgames.card

import casino.cardgames.card.*
import globals.*

data class Card(val symbol: Symbol, val wert: Wert){

    // Wert hinzufügen
    override fun toString(): String {
        return if (symbol.cardsuit == herzSymbol || symbol.cardsuit == karoSymbol) {
            "$redColor ${symbol.cardsuit} - ${wert.german}  $resetColor"
        } else {
            "$blueColor ${symbol.cardsuit} - ${wert.german} $resetColor"
        }
    }


}

