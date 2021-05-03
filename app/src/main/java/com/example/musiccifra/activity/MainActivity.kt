package com.example.musiccifra.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Environment.*
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musiccifra.R
import com.example.musiccifra.Util.ResourcesUtil
import com.example.musiccifra.adapter.AllMusicAdapter
import com.example.musiccifra.adapter.MyViewPagerAdapter
import com.example.musiccifra.fragment.*
import com.example.musiccifra.model.Music
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


//Kotlin Top constants:

//Folder containing the PDF musics files
const val PATHMUSICS = "PJFPDFMusics"

// Any code to get permission
const val READ_STORAGE_PERMISSION_REQUEST_CODE = 103

// TAG to print logs
const val TAG = "layon.f" //like: MusicApp

//TODO create recycle list from all tabs
//TODO recycler view search https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview
//TODO add the favorite music list feature
//TODO feature to download files from generic official music
//TODO get the list of music out of UIThread
//TODO a setting panel
//TODO add support for Portugues e Espanhol
//TODO open pdf on click
//TODO apply styles on Material AutoComplete
//TODO rework the autocomplete: It is like be good, the texts are overriding. Maybe is better to hide the recycler view before.
//TODO remove the action bar to use a Toolbar in order to hide when scroll up the screen in recycler view

class MainActivity : AppCompatActivity() {

    lateinit var musics: MutableList<Music>
    lateinit var allMusicAdapter: AllMusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermissionForReadExtertalStorage()){
            //if the read external storage permission is not granted, so we need to request to de user:
            requestPermissionForReadExtertalStorage()
        }

        //configure auto complete
        Log.d("layon.f", "getMusicNamesAvailable()")
        musics = ResourcesUtil.getMusicNamesAvailable()
        Log.d("layon.f", "musics = $musics")

        //Populate the Global Adapter
        allMusicAdapter = AllMusicAdapter(musics)

        //configute the music tabs
        setupTabs()

        //add listener on fab button
        fab.setOnClickListener{ view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()


            // TODO try user some of ACTION_PICK, ACTION_CHOOSER, ACTION_GET_CONTENT or ACTION_SEARCH to pick the pdf files uri and add to my music list
            //example https://stackoverflow.com/questions/51762094/choose-pdf-or-image-files-from-google-drive-intent
            //example https://stackoverflow.com/questions/32232412/android-select-pdf-using-intent-on-api-18
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("application/pdf");
            //intent.setDataAndType(Uri.fromFile(file), "application/pdf")
            //intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }

    private fun setupTabs(){
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OneFragment(), "Todas")
        adapter.addFragment(TwoFragment(), "Favoritas")
        adapter.addFragment(ThreeFragment(), "Últimas")
        viewPager.adapter = adapter
        my_tablayout.setupWithViewPager(viewPager)
    }

    //Util function to check if the app has the permission
    fun checkPermissionForReadExtertalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result: Int = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    // function to request the permission to de user
    fun requestPermissionForReadExtertalStorage() {
        try {
            Log.d("layon.f", "requestPermissionForReadExtertalStorage()")
            ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_REQUEST_CODE
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var item = menu?.findItem(R.id.item_search)
        if (isDarkModeOn()) {
         item?.setIcon(R.drawable.ic_baseline_search_24_darkmode)
        }
        var searchView : SearchView = item?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                allMusicAdapter.myFilter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                allMusicAdapter.myFilter.filter(newText)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    //method to check if dark mode is on
    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}