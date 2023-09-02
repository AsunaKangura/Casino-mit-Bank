package casino.cardgames.card

enum class Wert(val cardValue: Int , val german: String){
    TWO(2,"2"),
    THREE(3,"3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7,"7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(10, "Bube"),
    QUEEN(10,"Dame"),
    KING(10, "König"),
    ACE(11,"Ass")
}

