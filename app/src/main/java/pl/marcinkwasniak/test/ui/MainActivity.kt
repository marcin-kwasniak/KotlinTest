package pl.marcinkwasniak.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.marcinkwasniak.test.R
import pl.marcinkwasniak.test.setupAuthorFab
import pl.marcinkwasniak.test.ui.search.SearchSongFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupAuthorFab(fab)
        if (savedInstanceState == null){
            init()
        }
    }

    private fun init() = supportFragmentManager
        .beginTransaction()
        .replace(R.id.fragment_container, SearchSongFragment())
        .commit()
}
