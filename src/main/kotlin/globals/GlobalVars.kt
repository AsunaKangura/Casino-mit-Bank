package globals

import casino.cardgames.card.Card

// *====================================================
// *==== Variablen für die Menü,- Kontrollstruktur =====
// *====================================================

var programmAktiv = false

var programmStart = true
var casinoAktiv = false
var bankAktiv = false
var slotmaschineAktiv = false
var dailyResult = false
var payOutAktiv = false
var payInAktiv = false
var transferAktiv = false
var slotWin = false
var moneyEmpty = false
var userIsLogin = ""

// * Register
var userNameCheck = false
var passwordCheck = false
var pinCheck = false

// *====================================================
// *====== Variablen für farblichen Darstellung ========
// *====================================================
var greenColor = "\u001B[32m"
var redColor = "\u001B[31m"
var yellow = "\u001B[33m"
var resetColor = "\u001B[0m"
val blueColor = "\u001B[34m"

// *====================================================
// *======= Alle Info für das Spiel casino.slotmaschine.Slotmaschine =======
// *====================================================
val pikSymbol = "\u2660"
val karoSymbol = "\u2666"
val herzSymbol = "\u2665"
val kreuzSymbol = "\u2663"
// *====================================================
// *=============== Globale Objekte ====================
// *====================================================
var casinoInstanz = casino.Casino()
var bankInstanz = bank.Casinobank()

var bet = 0
var dailyProfit = 0
var dailyLoss = 0

var line1: List<Char> = listOf()
var line2: List<Char> = listOf()
var line3: List<Char> = listOf()

var lineCheck1: Boolean = false
var lineCheck2: Boolean = false
var lineCheck3: Boolean = false
var crossCheck: Int = 0


// *====================================================
// *=========== Hier findet man alle Menüs =============
// *====================================================
