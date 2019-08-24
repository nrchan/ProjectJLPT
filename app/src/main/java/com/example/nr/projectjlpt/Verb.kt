package com.example.nr.projectjlpt

data class Verb(
    val stemKanji : String?,
    val stemKana : String?,
    val stemRomaji : String?,
    val meaning : String?,
    val group : String?
) {
    fun getDict() = when(group) {
        "う" -> "う"
        "つ" -> "つ"
        "る" -> "る"
        "く" -> "く"
        "ぐ" -> "ぐ"
        "ぬ" -> "ぬ"
        "ぶ" -> "ぶ"
        "む" -> "む"
        "す" -> "す"
        "いる" -> "る"
        "える" -> "る"
        "する" -> "する"
        "くる" -> "る"
        else -> ""
    }
    fun getDictRomaji() = when(group) {
        "う" -> "u"
        "つ" -> "tsu"
        "る" -> "ru"
        "く" -> "ku"
        "ぐ" -> "gu"
        "ぬ" -> "nu"
        "ぶ" -> "bu"
        "む" -> "mu"
        "す" -> "su"
        "いる" -> "ru"
        "える" -> "ru"
        "する" -> "suru"
        "くる" -> "ru"
        else -> ""
    }
}