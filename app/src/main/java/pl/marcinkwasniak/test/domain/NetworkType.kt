package pl.marcinkwasniak.test.domain

import pl.marcinkwasniak.test.R

/**
 * Created by marcin.kwasniak on 03/04/2019
 */
enum class NetworkType(val stored: IntArray, val textId: Int) {
    Local(intArrayOf(0), R.string.network_local),
    Remote(intArrayOf(1), R.string.network_remote),
    Both(intArrayOf(0, 1), R.string.network_both),
    None(intArrayOf(), R.string.network_none);

    companion object {
        fun valueOf(state: IntArray): NetworkType {
            state.sort()
            return when {
                state.contentEquals(NetworkType.Local.stored) -> NetworkType.Local
                state.contentEquals(NetworkType.Remote.stored) -> NetworkType.Remote
                state.contentEquals(NetworkType.Both.stored) -> NetworkType.Both
                else -> NetworkType.None
            }
        }
    }
}