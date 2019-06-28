package com.example.nr.projectjlpt

class GrammarPoint(val pattern: String?,val kana: String?,val romaji: String?,val meaning: String?,val level: Int?,val explanation: String?,val usage: String?) {

    fun printUsage() : String?{
        return usage?.replace("\\n", System.getProperty("line.separator")!!)
    }

    fun printExplanation() : String?{
        return explanation?.replace("\\n", System.getProperty("line.separator")!!)
    }
}