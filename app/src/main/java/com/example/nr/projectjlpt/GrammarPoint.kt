package com.example.nr.projectjlpt

import androidx.room.*

@Entity
data class GrammarPoint(
    @PrimaryKey(autoGenerate = true) val gid: Int?,
    @ColumnInfo(name = "pattern") val pattern: String?,
    @ColumnInfo(name = "kana") val kana: String?,
    @ColumnInfo(name = "romaji") val romaji: String?,
    @ColumnInfo(name = "meaning") val meaning: String?,
    @ColumnInfo(name = "level") val level: Int?,
    @ColumnInfo(name = "explanation") val explanation: String?,
    @ColumnInfo(name = "usage") val usage: String?,
    @ColumnInfo(name = "example") val example: String?,
    @ColumnInfo(name = "synonym") val synonym: String?,
    @ColumnInfo(name = "antonym") val antonym: String?,
    @ColumnInfo(name = "confusing") val confusing: String?
) {

    fun printUsage() : String?{
        return usage?.replace("\\n", System.getProperty("line.separator")!!)
    }

    fun printExplanation() : String?{
        return explanation?.replace("\\n", System.getProperty("line.separator")!!)
    }

    fun printExample() : String?{
        return example?.replace("\\n", System.getProperty("line.separator")!!)
    }

    fun printSynonym() : String?{
        return synonym?.replace("\\n", System.getProperty("line.separator")!!)
    }

    fun printAntonym() : String?{
        return antonym?.replace("\\n", System.getProperty("line.separator")!!)
    }

    fun printConfusing() : String?{
        return confusing?.replace("\\n", System.getProperty("line.separator")!!)
    }
}

@Dao
interface GrammarPointDao {
    @Query("SELECT * FROM grammarpoint")
    fun getAll(): List<GrammarPoint>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg grammarpoint: GrammarPoint)

    @Update
    fun update(grammarpoint: GrammarPoint)

    @Delete
    fun delete(grammarpoint: GrammarPoint)

    @Query("DELETE FROM grammarpoint WHERE pattern = :pattern")
    fun deleteByPattern(pattern: String?)

    @Query("DELETE FROM grammarpoint")
    fun deleteAll()
}

@Database(entities = arrayOf(GrammarPoint::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun GrammarPointDao(): GrammarPointDao
}