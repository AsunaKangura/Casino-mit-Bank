package bank

import bank.konten.*
import globals.*
import klassen.Meldungen

// * =========================================================
// * ================ Klasse Casinobank ======================
// * =========================================================
// * Current Version 2.4
// * =========================================================
// * erstellt durch: AsunaKangura
// * =========================================================
// * Sprache : Kotlin
// * =========================================================
// * 1.0 = Kundenliste wurde erstellt, mit dem Datentype User aus der Klasse User
// * 1.1 = Der initial Block wurde definiert und der Default Admin wurde angelegt
// * 1.2 = Die Funktion "loginCheck" überprüft die Benutzeranmeldeinformationen, indem sie den Benutzernamen und das Passwort mit den gespeicherten Daten vergleicht. Sie gibt einen booleschen Wert zurück, der angibt, ob die Anmeldeinformationen übereinstimmen oder nicht.
// * 1.3 = Die Funktion "Login" ist für die Benutzeranmeldung verantwortlich
// * 1.4 = Die Funktion "Register" ist für die Benutzerregistrierung verantwortlich
// * 1.5 = Die Funktion "userNameCheck" wird verwendet, um einen Benutzernamen einzugeben und zu validieren.
// * 1.6 = Die Funktion "passwordCheck" wird verwendet, um ein Passwort einzugeben und zu validieren.
// * 1.7 = Die Funktion "inputpinCheck" wird verwendet, um ein Pin einzugeben und zu validieren.
// * 1.8 = Die Funktion "payOutMenu" wird verwendet, um ein Auszahlungsmenü zu haben, von wo man etwas abheben möchte
// * 1.9 = Die Funktion "payInMenu" wird verwendet, um ein Einzahlungsmenü zu haben, von wo man etwas einzahlen möchte
// * 2.0 = Die Funktion "transferMenu" wird verwendet, um ein Überweisungsmenü zu haben, von welchem Konto man was überweisen möchte
// * 2.1 = Die Funktion "transferGiroKonto" wird verwendet, um von dem GiroKonto zu den beiden anderen Konten zu überweisen
// * 2.2 = Die Funktion "transferSparBuch" wird verwendet, um von dem SparBuch zu den beiden anderen Konten zu überweisen
// * 2.3 = Die Funktion "transferCasinoKonto" wird verwendet, um von dem CasinoKonto zu den beiden anderen Konten zu überweisen
// * 2.4 = Die Funktion "mainMenuBank" wird verwendet, um Das Hauptmenü der Bank darzustellen


class Casinobank() {

    var Kundenliste: MutableList<User> = mutableListOf()

    init {
        val admin = User(0, "admin", "admin", 1234)
        admin.addKonto(GiroKonto(0, 1000, 5000))
        admin.addKonto(SparBuch(1, 2000))
        admin.addKonto(CasinoKonto(2, 1500000))
        Kundenliste.add(admin)
    }

    /** Die Funktion "loginCheck" überprüft die Benutzeranmeldeinformationen, indem sie den Benutzernamen und das Passwort mit den gespeicherten Daten vergleicht. Sie gibt einen booleschen Wert zurück, der angibt, ob die Anmeldeinformationen übereinstimmen oder nicht.
     * @return Boolean
     * */
    private fun loginCheck(username: String, password: String) : Boolean {
        // *Die Funktion "find" durchsucht die Liste "Kundenliste" nach einem Element, das die Bedingung erfüllt, die in den geschweiften Klammern angegeben ist.
        // *In diesem Fall wird die Funktion "checkUser" für jedes Element in der Liste aufgerufen, um zu prüfen, ob der Benutzername und das Passwort übereinstimmen.
        // *Wenn ein Element gefunden wird, für das "checkUser" true zurückgibt, wird dieses Element in der Variable "matchedUser" gespeichert, andernfalls wird "null" zurückgegeben.
        val matchedUser = Kundenliste.find { it.checkUser(username, password) }

        // *Die Variable "matchedUser" wird nun überprüft, ob sie "null" ist oder nicht.
        // *Wenn "matchedUser" nicht "null" ist, bedeutet dies, dass ein Benutzer gefunden wurde, der übereinstimmt.
        // *In diesem Fall wird "true" zurückgegeben, um anzuzeigen, dass die Anmeldeinformationen gültig sind.
        // *Andernfalls, wenn "matchedUser" "null" ist, wurde kein Benutzer gefunden, der den Anmeldeinformationen entspricht, und daher wird "false" zurückgegeben.
        return matchedUser != null
    }

    /** Diese Funktion ist für die Benutzeranmeldung verantwortlich.
     *
     * */
    fun login() {
        // *Benutzername-Eingabeaufforderung
        print("Bitte Benutzername eingeben: ")
        val userName = readln() // *Liest den eingegebenen Benutzernamen

        // *Passwort-Eingabeaufforderung
        print("Bitte Passwort eingeben: ")
        val userPassword = readln() // *Liest das eingegebene Passwort

        // *Überprüfen, ob der Benutzername und das Passwort korrekt sind, indem die Funktion loginCheck() der Bankinstanz aufgerufen wird
        val isUserExists = bankInstanz.loginCheck(userName, userPassword)

        if (isUserExists) {
            // *Wenn der Benutzer existiert, wird der entsprechende Benutzer in der Kundenliste der Bankinstanz gesucht
            val isUser = bankInstanz.Kundenliste.find { it.checkUser(userName, userPassword) }

            if (isUser != null) {
                programmStart = false
                programmAktiv = true
                // *Wenn der Benutzer in der Kundenliste gefunden wurde, wird das Hauptmenü mit dem gefundenen Benutzer aufgerufen
                mainMenu(isUser)
            }
        } else {
            // *Wenn der Benutzer nicht existiert, wird eine Fehlermeldung ausgegeben
            Meldungen("Dieser Benutzer existiert nicht.").errorMessage()
        }
    }

    /** Diese Funktion ist für die Benutzerregistrierung verantwortlich */
    fun register() {
        // *Überprüfe den Benutzernamen des Benutzers
        var userName = userNameCheck()

        // *Überprüfe das Benutzerpasswort
        var userPassword = passwordCheck()

        // *Überprüfe die Benutzereingabe für die PIN und konvertiere sie in Integer
        var userPin = inputpinCheck().toInt()

        // *Sucht nach einem Benutzer in der Bankinstanz mit dem angegebenen Benutzernamen und Passwort
        var isUserExists = bankInstanz.Kundenliste.find { it.checkUser(userName, userPassword) }

        // *Wenn der Benutzer nicht existiert, registriere ihn
        if (isUserExists == null) {
            // *Erstelle einen neuen registrierten Benutzer mit eindeutiger ID, Benutzername, Passwort und PIN
            var registeredUser = User(bankInstanz.Kundenliste.lastIndex + 1, userName, userPassword, userPin)

            // *Füge dem registrierten Benutzer verschiedene Konten hinzu (Girokonto, Sparbuch, Casino-Konto) mit Standardwerten
            registeredUser.addKonto(GiroKonto(0, 0, 0))
            registeredUser.addKonto(SparBuch(1, 0))
            registeredUser.addKonto(CasinoKonto(2, 0))

            // *Füge den neuen Benutzer der Kundenliste der Bankinstanz hinzu
            Kundenliste.add(registeredUser)
        }else{
            Meldungen("Es existiert bereits ein User mit diesen Informationen").errorMessage()
        }
    }



    /** Diese Funktion wird verwendet, um einen Benutzernamen einzugeben und zu validieren. Die Validierung erfolgt mir Regex
     * @return username als String
     * */
    private fun userNameCheck(): String {
        // *Die Variable "returnValue" wird verwendet, um den eingegebenen und validierten Benutzernamen zu speichern.
        var returnValue = ""

        // *Diese Schleife wird ausgeführt, bis ein gültiger Benutzername eingegeben wird.
        while (!userNameCheck) {
            // *Der Benutzer wird aufgefordert, einen Benutzernamen festzulegen.
            println("Nun musst du deinen Benutzernamen festlegen")

            // *Die Eingabe des Benutzers wird eingelesen und in der Variablen "inputUser" gespeichert.
            var inputUser = readln()

            // *Überprüfung, ob der eingegebene Benutzername mindestens einen Buchstaben (a-z oder A-Z) enthält. Die Regex "[a-zA-Z]" sucht nach einem beliebigen Zeichen im Bereich von a-z oder A-Z in der Eingabe.
            if (inputUser.contains(Regex("A-Z][a-z"))) {
                // *Wenn der Benutzername den Kriterien entspricht, wird eine Erfolgsmeldung ausgegeben.
                Meldungen("Der Benutzername entspricht den Kriterien").successMessage()
                // *Der eingegebene Wert wird der Variable "returnValue" zugewiesen.
                returnValue = inputUser
                // *Die Variable "userNameCheck" wird auf "true" gesetzt, um die Schleife zu beenden.
                userNameCheck = true
            } else {
                // *Wenn der Benutzername keine Buchstaben enthält, wird eine Fehlermeldung ausgegeben.
                Meldungen("Du darfst nur Buchstaben eingeben").errorMessage()
            }
        }

        // *Der validierte Benutzername wird in Kleinbuchstaben umgewandelt und zurückgegeben.
        return returnValue.lowercase()
    }

    /** Diese Funktion wird verwendet, um ein Passwort einzugeben und zu validieren. Die Validierung erfolgt mir Regex
     * @return password als String
     * */
    private fun passwordCheck(): String {
        // *Die Variable "returnValue" wird verwendet, um das eingegebene und validierte Passwort zu speichern.
        var returnValue = ""

        // *Diese Schleife wird ausgeführt, bis ein gültiges Passwort eingegeben wird.
        while (!passwordCheck) {
            // *Der Benutzer wird aufgefordert, ein Passwort festzulegen.
            println("Nun musst du dein Passwort festlegen")

            // *Die Eingabe des Benutzers wird eingelesen und in der Variablen "inputPassword" gespeichert.
            var inputPassword = readln()

            // *Überprüfung, ob das eingegebene Passwort mindestens einen Buchstaben (a-z oder A-Z) enthält.
            // *Die Regex "[a-zA-Z]" sucht nach einem beliebigen Zeichen im Bereich von a-z oder A-Z in der Eingabe.
            if (inputPassword.contains(Regex("[a-zA-Z]"))) {
                // *Wenn das Passwort den Kriterien entspricht, wird eine Erfolgsmeldung ausgegeben.
                Meldungen("Das Passwort entspricht den Kriterien").successMessage()
                // *Der eingegebene Wert wird der Variable "returnValue" zugewiesen.
                returnValue = inputPassword
                // *Die Variable "passwordCheck" wird auf "true" gesetzt, um die Schleife zu beenden.
                passwordCheck = true
            } else {
                // *Wenn das Passwort keine Buchstaben enthält, wird eine Fehlermeldung ausgegeben.
                Meldungen("Du darfst nur Buchstaben eingeben").errorMessage()
            }
        }

        // *Das validierte Passwort wird in Kleinbuchstaben umgewandelt und zurückgegeben.
        return returnValue.lowercase()
    }

    /** Funktion `inputpinCheck` zur Überprüfung und Eingabe des Pins. Die Validierung erfolgt mir Regex
     * @return pin als String
     * */
    private fun inputpinCheck(): String {
        var returnValue = "" // *Variable, die den eingegebenen Pin speichert

        // *Schleife, die so lange ausgeführt wird, bis `pinCheck` auf true gesetzt wird
        while (!pinCheck) {
            println("Nun musst du deinen Pin festlegen")
            val inputPin = readLine() ?: "" // *readLine() verwendet, um Benutzereingabe einzulesen

            // *Überprüft, ob der eingegebene Pin den angegebenen Kriterien entspricht
            if (inputPin.matches(Regex("[1-9][0-9]{3}"))){
                // *Wenn der Pin den Kriterien entspricht, wird eine Erfolgsmeldung ausgegeben
                Meldungen("Der Pin entspricht den Kriterien").successMessage()
                returnValue = inputPin // *Speichert die gültige Eingabe in der Variable `returnValue`
                pinCheck = true // *Setzt `pinCheck` auf true, um die Schleife zu beenden
            } else {
                // *Wenn der Pin nicht den Kriterien entspricht, wird eine Fehlermeldung ausgegeben
                Meldungen("Fehler: Der Pin darf nicht mit einer 0 beginnen\nDer Pin darf nur aus Zahlen bestehen\nDie Zahlen von 0 bis 9 sind erlaubt\nDer Pin darf nur 4 Stellen lang sein").errorMessage()
            }
        }

        return returnValue // *Gibt den eingegebenen Pin zurück
    }




    /** Dieses Menü ist dafür da um eine Übersicht der Kontostände zu bekommen und dann zu entscheiden von welchem Konto man sich was auszahlen lassen möchte
     * @return Unit
     */
    private fun payOutMenu(user: User) {
        var payOutAktiv = true
        // *Schleife, die das Menü aktiv hält, solange payOutAktiv wahr ist
        while (payOutAktiv) {
            // *Die Konten des Benutzers werden anhand ihrer IDs abgerufen
            val GiroKonto = Kundenliste[user.id].getKontoById(0)
            val SparBuch = Kundenliste[user.id].getKontoById(1)
            val CasinoKonto = Kundenliste[user.id].getKontoById(2)

            // *Platzhalter und Designelemente für das Menü
            placeholder(1)
            spacerGreenLong()
            println("\t\t\t\t\t\tHauptmenü Auszahlung")
            spacerGreenLong()

            // *Anzeige der Kontostände der verschiedenen Konten
            println("GiroKonto: \t\t\t" + GiroKonto?.kontoStand + " €")
            println("Sparbuch: \t\t\t" + SparBuch?.kontoStand + " €")
            println("CasinoKonto: \t\t" + CasinoKonto?.kontoStand + " €")
            spacerGreenLong()

            // *Menüoptionen für die verschiedenen Konten und Abbrechen
            println("1 = GiroKonto | 2 = SparBuch | 3 = CasinoKonto | 4 = Abbrechen")
            // *Benutzereingabe wird erfasst
            val inputUser = readln()

            // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist
            if (inputNumberCheck(inputUser)) {
                // *Auswertung der Benutzereingabe mithilfe eines when-Ausdrucks
                when (inputUser) {
                    1.toString() -> {
                        // *Prüfen, ob das Girokonto existiert, und Auszahlung durchführen
                        if (GiroKonto != null) {
                            GiroKonto.payout()
                        }
                    }
                    2.toString() -> {
                        // *Prüfen, ob das Sparbuch existiert, und Auszahlung durchführen
                        if (SparBuch != null){
                            SparBuch.payout()
                        }
                    }
                    3.toString() -> {
                        // *Prüfen, ob das Casino-Konto existiert, und Auszahlung durchführen
                        if (CasinoKonto != null){
                            CasinoKonto.payout()
                        }
                    }
                    4.toString() -> {
                        // *Abbruch des Menüs durch Setzen von payOutAktiv auf false
                        payOutAktiv = false
                    }
                    else -> {
                        // *Meldung bei ungültiger Benutzereingabe
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 4").infoMessage()
                    }
                }
            } else {
                // *Fehlermeldung bei Eingabe, die keine Zahl ist
                Meldungen("Du darfst nur Zahlen eingeben").errorMessage()
            }
        }
    }

    /** Dieses Menü ist dafür da um eine Übersicht der Kontostände zu bekommen und dann zu entscheiden auf welches Konto man etwas einzahlen möchte. Hier bei ist es wichtig Einzahlungen sind nur auf dem GiroKonto und SparBuch möglich
     * @return Unit
     */
    private fun payInMenu(user: User) {
        var payInAktiv = true
        // *Schleife, die das Menü aktiv hält, solange payInAktiv wahr ist
        while (payInAktiv) {
            // *Die Konten des Benutzers werden anhand ihrer IDs abgerufen
            val GiroKonto = Kundenliste[user.id].getKontoById(0)
            val SparBuch = Kundenliste[user.id].getKontoById(1)

            // *Platzhalter und Designelemente für das Menü
            placeholder(2)
            spacerGreenLong()
            println("\t\t\t\t\t\tHauptmenü Einzahlung")
            spacerGreenLong()

            // *Anzeige der Kontostände der verschiedenen Konten
            println("GiroKonto: \t\t" + GiroKonto?.kontoStand + " €")
            println("Sparbuch: \t\t" + SparBuch?.kontoStand + " €")
            spacerGreenLong()

            // *Menüoptionen für die verschiedenen Konten und Abbrechen
            println("1 = GiroKonto | 2 = SparBuch | 3 = Abbrechen")
            // *Benutzereingabe wird erfasst
            val inputUser = readln()

            // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist
            if (inputNumberCheck(inputUser)) {
                // *Auswertung der Benutzereingabe mithilfe eines when-Ausdrucks
                when (inputUser) {
                    1.toString() -> {
                        // *Prüfen, ob das Girokonto existiert, und Einzahlung durchführen
                        if (GiroKonto != null) {
                            GiroKonto.payIn()
                        }
                    }
                    2.toString() -> {
                        // *Prüfen, ob das Sparbuch existiert, und Einzahlung durchführen
                        if (SparBuch != null) {
                            SparBuch.payIn()
                        }
                    }
                    3.toString() -> {
                        // *Abbruch des Menüs durch Setzen von payInAktiv auf false
                        payInAktiv = false
                    }
                    else -> {
                        // *Meldung bei ungültiger Benutzereingabe
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
                    }
                }
            }
        }
    }

    /** Dieses Menü ist dafür da um eine Übersicht der Kontostände zu bekommen und dann zu entscheiden von welchem Konto man etwas Überweisen möchte.
     * @return Unit
     */
    private fun transferMenu(user: User) {
        var transferAktiv = true
        // *Schleife, die das Menü aktiv hält, solange transferAktiv wahr ist
        while (transferAktiv) {
            // *Die Konten des Benutzers werden anhand ihrer IDs abgerufen
            var giroKonto = Kundenliste[user.id].getKontoById(0)
            var sparBuch = Kundenliste[user.id].getKontoById(1)
            var casinoKonto = Kundenliste[user.id].getKontoById(2)

            // *Platzhalter und Designelemente für das Menü
            spacerGreenLong()
            println("\t\t\t\tKonto Informationen von ${user.username}")
            spacerGreenLong()

            // *Anzeige der Informationen der verschiedenen Konten
            println("GiroKonto-ID: \t" + giroKonto?.kontenID + "\t\t\tKontostand: " + giroKonto?.kontoStand + " €")
            println("Sparbuch-ID: \t" + sparBuch?.kontenID + "\t\t\tKontostand: " + sparBuch?.kontoStand + " €")
            println("CasinoKonto-ID: " + casinoKonto?.kontenID + "\t\t\tKontostand: " + casinoKonto?.kontoStand + " €")
            spacerGreenLong()

            // *Menüoptionen für Überweisungen von verschiedenen Konten und Abbrechen
            println("Hauptmenü Überweisen vom:")
            println("1 = GiroKonto | 2 = SparBuch | 3 = CasinoKonto | 4 = Abbrechen")
            // *Benutzereingabe wird erfasst
            val inputUser = readln()

            // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist
            if (inputNumberCheck(inputUser)) {

                // *Auswertung der Benutzereingabe mithilfe eines when-Ausdrucks
                when (inputUser) {
                    1.toString() -> {
                        // *Prüfen, ob das Girokonto existiert, und Überweisung durchführen
                        if (giroKonto != null) {
                            transferGiroKonto(user, giroKonto)
                        }
                    }
                    2.toString() -> {
                        // *Prüfen, ob das Sparbuch existiert, und Überweisung durchführen
                        if (sparBuch != null) {
                            transferSparBuch(user, sparBuch)
                        }
                    }
                    3.toString() -> {
                        // *Prüfen, ob das Casino-Konto existiert, und Überweisung durchführen
                        if (casinoKonto != null) {
                            transferCasinoKonto(user, casinoKonto)
                        }
                    }
                    4.toString() -> {
                        // *Abbruch des Menüs durch Setzen von transferAktiv auf false
                        transferAktiv = false
                    }
                    else -> {
                        // *Meldung bei ungültiger Benutzereingabe
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 4").infoMessage()
                    }
                }
            }
        }
    }

    /** Dieses Menü ist dafür da um eine Übersicht des Kontostandes zu bekommen und dann zu entscheiden auf welches Konto man etwas Überweisen möchte.
     * @return Unit
     */
    private fun transferGiroKonto(user: User, konten: Konten) {
        // *Schleife, die das Menü aktiv hält, solange menuaktiv wahr ist
        var menuaktiv = true
        while (menuaktiv) {
            // *Platzhalter und Designelemente für das Menü
            spacerGreen()
            println("Konto Informationen von ${user.username}")
            spacerGreen()

            // *Anzeige der Informationen des ausgewählten Kontos
            println("Konto-ID: \t" + konten.kontenID + "\t\tKontostand: " + konten.kontoStand + " €")
            spacerGreen()

            // *Menüoptionen für Überweisungen vom Girokonto und Abbrechen
            println("Hauptmenü Überweisen")
            println("1 = zum Sparbuch | 2 = zum Casinokonto | 3 = Abbrechen")
            // *Benutzereingabe wird erfasst
            val inputUser = readln()

            // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist
            if (inputNumberCheck(inputUser)) {
                // *Auswertung der Benutzereingabe mithilfe eines when-Ausdrucks
                when (inputUser) {
                    1.toString() -> {
                        // *Überweisung zum Sparbuch
                        konten.transfer(user, konten.kontenID, 1)
                    }
                    2.toString() -> {
                        // *Überweisung vom Casinokonto
                        konten.transfer(user, konten.kontenID, 2)
                    }
                    3.toString() -> {
                        // *Abbruch des Menüs durch Setzen von menuaktiv auf false
                        menuaktiv = false
                    }
                    else -> {
                        // *Meldung bei ungültiger Benutzereingabe
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
                    }
                }
            }
        }
    }

    /** Dieses Menü ist dafür da um eine Übersicht des Kontostandes zu bekommen und dann zu entscheiden auf welches Konto man etwas Überweisen möchte.
     * @return Unit
     */
    private fun transferSparBuch(user: User, konten: Konten) {
        // *Schleife, die das Menü aktiv hält, solange menuaktiv wahr ist
        var menuaktiv = true
        while (menuaktiv) {
            // *Platzhalter und Designelemente für das Menü
            spacerGreen()
            println("Konto Informationen von ${user.username}")
            spacerGreen()

            // *Anzeige der Informationen des ausgewählten Kontos
            println("Konto-ID: \t" + konten.kontenID + "\t\tKontostand: " + konten.kontoStand + " €")
            spacerGreen()

            // *Menüoptionen für Überweisungen zum Girokonto und Abbrechen
            println("Hauptmenü Überweisen")
            println("1 = zum GiroKonto | 2 = zum Casinokonto | 3 = Abbrechen")
            // *Benutzereingabe wird erfasst
            val inputUser = readln()

            // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist
            if (inputNumberCheck(inputUser)) {
                // *Auswertung der Benutzereingabe mithilfe eines when-Ausdrucks
                when (inputUser) {
                    1.toString() -> {
                        // *Überweisung zum Girokonto
                        konten.transfer(user, konten.kontenID, 0)
                    }
                    2.toString() -> {
                        // *Überweisung vom Casinokonto
                        konten.transfer(user, konten.kontenID, 2)
                    }
                    3.toString() -> {
                        // *Abbruch des Menüs durch Setzen von menuaktiv auf false
                        menuaktiv = false
                    }
                    else -> {
                        // *Meldung bei ungültiger Benutzereingabe
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
                    }
                }
            }
        }
    }

    /** Dieses Menü ist dafür da um eine Übersicht des Kontostandes zu bekommen und dann zu entscheiden auf welches Konto man etwas Überweisen möchte.
     * @return Unit
     */
    private fun transferCasinoKonto(user: User, konten: Konten) {
        // *Schleife, die das Menü aktiv hält, solange menuaktiv wahr ist
        var menuaktiv = true
        while (menuaktiv) {
            // *Platzhalter und Designelemente für das Menü
            spacerGreen()
            println("Konto Informationen von ${user.username}")
            spacerGreen()

            // *Anzeige der Informationen des ausgewählten Kontos
            println("Konto-ID: \t" + konten.kontenID + "\t\tKontostand: " + konten.kontoStand + " €")
            spacerGreen()

            // *Menüoptionen für Überweisungen zum Girokonto und vom Sparbuch
            println("Hauptmenü Überweisen")
            println("1 = zum GiroKonto | 2 = zum SparBuch | 3 = Abbrechen")
            // *Benutzereingabe wird erfasst
            val inputUser = readln()

            // *Überprüfung, ob die Benutzereingabe eine gültige Zahl ist
            if (inputNumberCheck(inputUser)) {
                // *Auswertung der Benutzereingabe mithilfe eines when-Ausdrucks
                when (inputUser) {
                    1.toString() -> {
                        // *Überweisung zum Girokonto
                        konten.transfer(user, konten.kontenID, 0)
                    }
                    2.toString() -> {
                        // *Überweisung vom Sparbuch
                        konten.transfer(user, konten.kontenID, 1)
                    }
                    3.toString() -> {
                        // *Abbruch des Menüs durch Setzen von menuaktiv auf false
                        menuaktiv = false
                    }
                    else -> {
                        // *Meldung bei ungültiger Benutzereingabe
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
                    }
                }
            }
        }
    }


    fun mainMenuBank(user: User) {
        var bankAktiv = true
        while (bankAktiv) {
            // * Hier wird die Hauptmenüstruktur aufgebaut
            placeholder(5)
            spacerGreenLong()
            println("\t\t\t\t\tHauptmenü der Casinobank")
            spacerGreenLong()
            println("1 = Auszahlung | 2 = Einzahlung | 3 = Überweisen | 4 = Bank verlassen")
            val inputUser = readln()
            if (inputNumberCheck(inputUser)) {
                when (inputUser) {
                    1.toString() -> {
                        payOutMenu(user)
                    }

                    2.toString() -> {
                        payInMenu(user)
                    }

                    3.toString() -> {
                        transferMenu(user)
                    }

                    4.toString() -> {
                        bankAktiv = false
                    }

                    else -> {
                        Meldungen("Es gibt hier nur Menüpunkte 1 bis 3").infoMessage()
                    }
                }
            }else{
                Meldungen("Die Eingabe war Falsch es dürfen nur Zahlen eingegeben werden").errorMessage()
            }
        }
    }
}