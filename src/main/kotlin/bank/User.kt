package bank.konten

class User(var id: Int, var username: String, var password: String, var userpin: Int) {

    private var konten: MutableList<Konten> = mutableListOf()

    private var accountPin: MutableMap<Int, Int> = mutableMapOf()

    /** Hier will ich überprüfen, ob es den User validate ist
     * @return Boolean ob es den User gibt
     */
    fun checkUser(username: String, password: String): Boolean {
        // *Vergleicht den übergebenen Benutzernamen und das Passwort mit den gespeicherten Werten und gibt true zurück, wenn sie übereinstimmen, andernfalls false
        return this.username.equals(username) && this.password.equals(password)
    }

    /** Hier wird überprüft, ob es das Konto gibt
     * @return Gibt das Konto zurück oder NULL, wenn kein Konto gefunden wurde
     */
    fun getKontoById(id: Int): Konten? {
        // *Durchsucht die Liste der Konten nach einem Konto mit der angegebenen ID und gibt das entsprechende Konto zurück, wenn gefunden, ansonsten null
        return konten.find { it.kontenID == id }
    }

    // * Hier mir kann man ein neues Konto für den User erstellt werden
    fun addKonto(konto: Konten){
        konten.add(konto)
    }
    // * Hiermit wird ein Konto aus der Liste konten gelöscht.
    fun removeKonto(konto: Konten){
        konten.remove(konto)
    }

    fun updateKonto(user: User, konto: Konten){
        konten[user.id] = konto
    }

    fun addAccountPin (accountid: Int, pin: Int){
        accountPin.put(accountid,pin)
    }

}