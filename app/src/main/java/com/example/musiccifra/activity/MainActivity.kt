package com.example.musiccifra.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment.*
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBar
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

    fun onClick(v: View?) {
        val intent = Intent(this, PdfViewActivity::class.java)
        intent.putExtra("ViewType", "storage")
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var item = menu?.findItem(R.id.item_search)
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
}