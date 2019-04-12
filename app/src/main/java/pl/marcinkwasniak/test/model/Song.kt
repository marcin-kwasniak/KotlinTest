package pl.marcinkwasniak.test.model

import com.google.gson.annotations.SerializedName

/**
 * Created by marcin.kwasniak on 31/03/2019
 */
data class Song(
    @SerializedName("Song Clean") val title: String,
    @SerializedName("ARTIST CLEAN") val artist: String,
    @SerializedName("Release Year") val releaseYear: String
) : Comparable<Song> {

    override fun toString(): String = title + artist + releaseYear

    override fun compareTo(other: Song): Int {
        val year1 = this.releaseYear.toIntOrNull() ?: 0
        val year2 = other.releaseYear.toIntOrNull() ?: 0
        return year1.compareTo(year2)
    }
}

fun song(block: SongBuilder.() -> Unit): Song = SongBuilder().apply(block).build()

@DslMarker
annotation class SongDsl

@SongDsl
class SongBuilder {
    var title: String = ""
    var artist: String = ""
    var releaseYear: String = ""
    fun build(): Song = Song(title, artist, releaseYear)
}