package pl.marcinkwasniak.test.repository

/**
 * Created by marcin.kwasniak on 04/04/2019
 */

interface SourcesDirectory {

    enum class Type { Local, Remote, Merged, None }

    fun getSource(type: Type): SongSource
}

class AppSourcesDirectory(
    private val localSource: SongSource,
    private val remoteSource: SongSource,
    private val mergedSource: SongSource,
    private val noneSource: SongSource
) : SourcesDirectory {
    override fun getSource(type: SourcesDirectory.Type): SongSource =
        when (type) {
            SourcesDirectory.Type.Local -> localSource
            SourcesDirectory.Type.Remote -> remoteSource
            SourcesDirectory.Type.Merged -> mergedSource
            SourcesDirectory.Type.None -> noneSource
        }
}