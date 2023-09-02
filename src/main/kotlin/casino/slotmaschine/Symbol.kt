package casino.slotmaschine

import globals.*

class Symbol(private var icon: String) {

    var isHit: Boolean = false

    /**
     * Markiert das Symbol als "Treffer".
     * Ändert den internen Zustand des Symbols auf "Treffer".
     */
    fun hit() {
        // *Setze den Zustand des Symbols auf "Treffer"
        isHit = true
    }

    /**
     * Überprüft, ob das Symbol dieser Instanz mit dem angegebenen Symbol übereinstimmt.
     *
     * @param symbol Das Symbol, mit dem verglichen werden soll.
     * @return true, wenn die Symbole übereinstimmen, ansonsten false.
     */
    fun matches(symbol: Symbol): Boolean {
        // *Vergleiche das Symbol dieser Instanz mit dem Symbol des angegebenen Symbols
        return this.icon == symbol.icon
    }

    /**
     * Gibt eine formatierte Zeichenfolge zurück, die das Symbol repräsentiert.
     * @return Die formatierte Zeichenfolge, die das Symbol darstellt.
     */
    override fun toString(): String {
        // Überprüfe, ob das Symbol ein Treffer ist
        return if (isHit) {
            // Wenn ja, gebe das Symbol in grüner Farbe formatiert zurück
            "$greenColor $icon $resetColor"
        } else {
            // Andernfalls, gebe das Symbol in roter Farbe formatiert zurück
            "$redColor $icon $resetColor"
        }
    }
}



