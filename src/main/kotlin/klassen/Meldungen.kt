package klassen
// * =========================================================
// * ================ Klasse Meldungen =======================
// * =========================================================
// * Current Version 1.2
// * =========================================================
// * erstellt durch: AsunaKangura
// * =========================================================
// * Sprache : Kotlin
// * =========================================================
// * 1.0 = Funktion errorMessage erstellt und getestet
// * 1.1 = Funktion infoMessage erstellt und getestet
// * 1.2 = Funktion successMessage erstellt und getestet


class Meldungen(var messageText: String) {

    // * Hier werden die Farbwerte und die ResetFarbe definiert
    var colorErrorMessage = "\u001B[31m"   // Rot
    var colorInfoMessage = "\u001B[33m"     // Gelb
    var colorSuccessfulMessage = "\u001B[32m"     // Grün
    var colorReset = "\u001B[0m"     // Farbe zurücksetzen

    // * hier stehen die einzelnen Funktion die über die Klasse zur Verfügung stehen.
    fun errorMessage(){
        println(colorErrorMessage+messageText+colorReset)
    }

    fun infoMessage(){
        println(colorInfoMessage+messageText+colorReset)
    }

    fun successMessage(){
        println(colorSuccessfulMessage+messageText+colorReset)
    }
}