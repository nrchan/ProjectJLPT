package com.example.nr.projectjlpt

data class Verb(
    val stemKanji : String?,
    val stemKana : String?,
    val stemRomaji : String?,
    val meaning : String?,
    val group : String?
) {
    fun dict() = when(group) {
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
        "くる" -> "来る"
        else -> ""
    }
    fun dictRomaji() = when(group) {
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
        "くる" -> "kuru"
        else -> ""
    }

    fun neg() = when(group) {
        "う" -> "わない"
        "つ" -> "たない"
        "る" -> "らない"
        "く" -> "かない"
        "ぐ" -> "がない"
        "ぬ" -> "なない"
        "ぶ" -> "ばない"
        "む" -> "まない"
        "す" -> "さない"
        "いる" -> "ない"
        "える" -> "ない"
        "する" -> "しない"
        "くる" -> "来ない"
        else -> ""
    }
    fun negRomaji() = when(group) {
        "う" -> "wanai"
        "つ" -> "tanai"
        "る" -> "ranai"
        "く" -> "kanai"
        "ぐ" -> "ganai"
        "ぬ" -> "nanai"
        "ぶ" -> "banai"
        "む" -> "manai"
        "す" -> "sanai"
        "いる" -> "nai"
        "える" -> "nai"
        "する" -> "shinai"
        "くる" -> "konai"
        else -> ""
    }

    fun masu() = when(group) {
        "う" -> "います"
        "つ" -> "ちます"
        "る" -> "ります"
        "く" -> "きます"
        "ぐ" -> "ぎます"
        "ぬ" -> "にます"
        "ぶ" -> "びます"
        "む" -> "みます"
        "す" -> "します"
        "いる" -> "ます"
        "える" -> "ます"
        "する" -> "します"
        "くる" -> "来ます"
        else -> ""
    }
    fun masuRomaji() = when(group) {
        "う" -> "imasu"
        "つ" -> "chimasu"
        "る" -> "rimasu"
        "く" -> "kimasu"
        "ぐ" -> "gimasu"
        "ぬ" -> "nimasu"
        "ぶ" -> "bimasu"
        "む" -> "mimasu"
        "す" -> "shimasu"
        "いる" -> "masu"
        "える" -> "masu"
        "する" -> "shimasu"
        "くる" -> "kimasu"
        else -> ""
    }

    fun te() = when(group) {
        "う" -> "って"
        "つ" -> "って"
        "る" -> "って"
        "く" -> "いて"
        "ぐ" -> "いで"
        "ぬ" -> "んで"
        "ぶ" -> "んで"
        "む" -> "んで"
        "す" -> "して"
        "いる" -> "て"
        "える" -> "て"
        "する" -> "して"
        "くる" -> "来て"
        else -> ""
    }
    fun teRomaji() = when(group) {
        "う" -> "tte"
        "つ" -> "tte"
        "る" -> "tte"
        "く" -> "ite"
        "ぐ" -> "ide"
        "ぬ" -> "nde"
        "ぶ" -> "nde"
        "む" -> "nde"
        "す" -> "shite"
        "いる" -> "te"
        "える" -> "te"
        "する" -> "shite"
        "くる" -> "kite"
        else -> ""
    }

    fun ta() = when(group) {
        "う" -> "った"
        "つ" -> "った"
        "る" -> "った"
        "く" -> "いた"
        "ぐ" -> "いだ"
        "ぬ" -> "んだ"
        "ぶ" -> "んだ"
        "む" -> "んだ"
        "す" -> "しだ"
        "いる" -> "た"
        "える" -> "た"
        "する" -> "した"
        "くる" -> "来た"
        else -> ""
    }
    fun taRomaji() = when(group) {
        "う" -> "tta"
        "つ" -> "tta"
        "る" -> "tta"
        "く" -> "ita"
        "ぐ" -> "ida"
        "ぬ" -> "nda"
        "ぶ" -> "nda"
        "む" -> "nda"
        "す" -> "shita"
        "いる" -> "ta"
        "える" -> "ta"
        "する" -> "shita"
        "くる" -> "kita"
        else -> ""
    }

    fun pot() = when(group) {
        "う" -> "える"
        "つ" -> "てる"
        "る" -> "れる"
        "く" -> "ける"
        "ぐ" -> "げる"
        "ぬ" -> "ねる"
        "ぶ" -> "べる"
        "む" -> "める"
        "す" -> "せる"
        "いる" -> "られる"
        "える" -> "られる"
        "する" -> "できる"
        "くる" -> "来られる"
        else -> ""
    }
    fun potRomaji() = when(group) {
        "う" -> "eru"
        "つ" -> "teru"
        "る" -> "reru"
        "く" -> "keru"
        "ぐ" -> "geru"
        "ぬ" -> "neru"
        "ぶ" -> "beru"
        "む" -> "meru"
        "す" -> "seru"
        "いる" -> "rareru"
        "える" -> "rareru"
        "する" -> "dekiru"
        "くる" -> "korareru"
        else -> ""
    }

    fun con() = when(group) {
        "う" -> "えば"
        "つ" -> "てば"
        "る" -> "れば"
        "く" -> "けば"
        "ぐ" -> "げば"
        "ぬ" -> "ねば"
        "ぶ" -> "べば"
        "む" -> "めば"
        "す" -> "せば"
        "いる" -> "れば"
        "える" -> "れば"
        "する" -> "すれば"
        "くる" -> "来れば"
        else -> ""
    }
    fun conRomaji() = when(group) {
        "う" -> "eba"
        "つ" -> "teba"
        "る" -> "reba"
        "く" -> "keba"
        "ぐ" -> "geba"
        "ぬ" -> "neba"
        "ぶ" -> "beba"
        "む" -> "meba"
        "す" -> "seba"
        "いる" -> "reba"
        "える" -> "reba"
        "する" -> "sureba"
        "くる" -> "kureba"
        else -> ""
    }

    fun vol() = when(group) {
        "う" -> "おう"
        "つ" -> "とう"
        "る" -> "ろう"
        "く" -> "こう"
        "ぐ" -> "ごう"
        "ぬ" -> "のう"
        "ぶ" -> "ぼう"
        "む" -> "もう"
        "す" -> "そう"
        "いる" -> "よう"
        "える" -> "よう"
        "する" -> "しよう"
        "くる" -> "来よう"
        else -> ""
    }
    fun volRomaji() = when(group) {
        "う" -> "ou"
        "つ" -> "tou"
        "る" -> "rou"
        "く" -> "kou"
        "ぐ" -> "gou"
        "ぬ" -> "nou"
        "ぶ" -> "bou"
        "む" -> "mou"
        "す" -> "sou"
        "いる" -> "you"
        "える" -> "you"
        "する" -> "shiyou"
        "くる" -> "koyou"
        else -> ""
    }

    fun proh() = when(group) {
        "う" -> "うな"
        "つ" -> "つな"
        "る" -> "るな"
        "く" -> "くな"
        "ぐ" -> "ぐな"
        "ぬ" -> "ぬな"
        "ぶ" -> "ぶな"
        "む" -> "むな"
        "す" -> "すな"
        "いる" -> "るな"
        "える" -> "るな"
        "する" -> "するな"
        "くる" -> "来るな"
        else -> ""
    }
    fun prohRomaji() = when(group) {
        "う" -> "una"
        "つ" -> "tsuna"
        "る" -> "runa"
        "く" -> "kuna"
        "ぐ" -> "guna"
        "ぬ" -> "nuna"
        "ぶ" -> "buna"
        "む" -> "muna"
        "す" -> "suna"
        "いる" -> "runa"
        "える" -> "runa"
        "する" -> "suruna"
        "くる" -> "kuruna"
        else -> ""
    }

    fun imp() = when(group) {
        "う" -> "え"
        "つ" -> "て"
        "る" -> "れ"
        "く" -> "け"
        "ぐ" -> "げ"
        "ぬ" -> "ね"
        "ぶ" -> "べ"
        "む" -> "め"
        "す" -> "せ"
        "いる" -> "ろ"
        "える" -> "ろ"
        "する" -> "しろ"
        "くる" -> "来い"
        else -> ""
    }
    fun impRomaji() = when(group) {
        "う" -> "e"
        "つ" -> "te"
        "る" -> "re"
        "く" -> "ke"
        "ぐ" -> "ge"
        "ぬ" -> "ne"
        "ぶ" -> "be"
        "む" -> "me"
        "す" -> "se"
        "いる" -> "ro"
        "える" -> "ro"
        "する" -> "shiro"
        "くる" -> "koi"
        else -> ""
    }

    fun cau() = when(group) {
        "う" -> "わせる"
        "つ" -> "たせる"
        "る" -> "らせる"
        "く" -> "かせる"
        "ぐ" -> "がせる"
        "ぬ" -> "なせる"
        "ぶ" -> "ばせる"
        "む" -> "ませる"
        "す" -> "させる"
        "いる" -> "させる"
        "える" -> "させる"
        "する" -> "させる"
        "くる" -> "来させる"
        else -> ""
    }
    fun cauRomaji() = when(group) {
        "う" -> "waseru"
        "つ" -> "tasery"
        "る" -> "raseru"
        "く" -> "kaseru"
        "ぐ" -> "gaseru"
        "ぬ" -> "naseru"
        "ぶ" -> "baseru"
        "む" -> "maseru"
        "す" -> "saseru"
        "いる" -> "saseru"
        "える" -> "saseru"
        "する" -> "saseru"
        "くる" -> "kosaseru"
        else -> ""
    }

    fun pas() = when(group) {
        "う" -> "われる"
        "つ" -> "たれる"
        "る" -> "られる"
        "く" -> "かれる"
        "ぐ" -> "がれる"
        "ぬ" -> "なれる"
        "ぶ" -> "ばれる"
        "む" -> "まれる"
        "す" -> "される"
        "いる" -> "られる"
        "える" -> "られる"
        "する" -> "される"
        "くる" -> "来られる"
        else -> ""
    }
    fun pasRomaji() = when(group) {
        "う" -> "wareru"
        "つ" -> "tareru"
        "る" -> "rareru"
        "く" -> "kareru"
        "ぐ" -> "gareru"
        "ぬ" -> "nareru"
        "ぶ" -> "bareru"
        "む" -> "mareru"
        "す" -> "sareru"
        "いる" -> "rareru"
        "える" -> "rareru"
        "する" -> "sareru"
        "くる" -> "korareru"
        else -> ""
    }

    fun caupas() = when(group) {
        "う" -> "わせられる"
        "つ" -> "たせられる"
        "る" -> "らせられる"
        "く" -> "かせられる"
        "ぐ" -> "がせられる"
        "ぬ" -> "なせられる"
        "ぶ" -> "ばせられる"
        "む" -> "ませられる"
        "す" -> "させられる"
        "いる" -> "させられる"
        "える" -> "させられる"
        "する" -> "させられる"
        "くる" -> "来させられる"
        else -> ""
    }
    fun caupasRomaji() = when(group) {
        "う" -> "waserareru"
        "つ" -> "taserareru"
        "る" -> "raserareru"
        "く" -> "kaserareru"
        "ぐ" -> "gaserareru"
        "ぬ" -> "naserareru"
        "ぶ" -> "baserareru"
        "む" -> "maserareru"
        "す" -> "saserareru"
        "いる" -> "saserareru"
        "える" -> "saserareru"
        "する" -> "saserareru"
        "くる" -> "kosaserareru"
        else -> ""
    }

    fun presentPlainA() = JA() + dict()

    fun presentPlainN() = JA() + neg()

    fun presentPoliteA() = JA() + masu()

    fun presentPoliteN() = presentPoliteA().replace(Regex("ます$"), "ません")

    fun pastPlainA() = JA() + ta()

    fun pastPlainN() = presentPlainN().replace(Regex("ない$"), "なかった")

    fun pastPoliteA() = presentPoliteA().replace(Regex("ます$"), "ました")

    fun pastPoliteN() = presentPoliteA().replace(Regex("ます$"), "ませんでした")

    fun JA() = when {
        group == "くる" -> ""
        stemKanji.isNullOrBlank() -> stemKana
        else -> stemKanji
    }

    fun RO() = if (group != "くる") stemRomaji else null
}