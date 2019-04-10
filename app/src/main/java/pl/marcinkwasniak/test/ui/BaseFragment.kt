package pl.marcinkwasniak.test.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsMultiChoice
import com.orhanobut.hawk.Hawk
import pl.marcinkwasniak.test.R
import pl.marcinkwasniak.test.domain.NetworkType
import pl.marcinkwasniak.test.storage.Shared

/**
 * Created by marcin.kwasniak on 03/04/2019
 */
abstract class BaseFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_settings -> {
                showDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showDialog() =
        MaterialDialog(requireContext()).show {
            title(R.string.action_settings)
            listItemsMultiChoice(
                R.array.networkType, initialSelection = Hawk.get(Shared.NETWORK_TYPE_KEY, NetworkType.Local.stored)
            ) { _, indices, _ ->
                Hawk.put(Shared.NETWORK_TYPE_KEY, indices)
                onNetworkChanged(indices)
            }
        }

    abstract fun onNetworkChanged(rawType: IntArray)
}