package pl.marcinkwasniak.test

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * Created by marcin.kwasniak on 04/04/2019
 */

fun setupAuthorFab(fab: FloatingActionButton) = fab
    .setOnClickListener { view ->
        Snackbar
            .make(view, R.string.author, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }