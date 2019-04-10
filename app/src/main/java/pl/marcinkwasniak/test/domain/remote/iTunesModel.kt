package pl.marcinkwasniak.test.domain.remote

/**
 * Created by marcin.kwasniak on 01/04/2019
 */

data class RemoteResponse(val results: List<Result>)

data class Result(val artistName: String = "", val trackName: String = "", val releaseDate: String = "")