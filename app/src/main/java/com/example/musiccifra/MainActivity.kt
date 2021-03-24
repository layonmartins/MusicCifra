package com.example.musiccifra

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.*
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musiccifra.adapter.MyViewPagerAdapter
import com.example.musiccifra.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

//Kotlin Top constants:

//Folder containing the PDF musics files
const val PATHMUSICS = "PJFPDFMusics"

// Any code to get permission
const val READ_STORAGE_PERMISSION_REQUEST_CODE = 103

// TAG to print logs
const val TAG = "layon.f" //like: MusicApp

//TODO create recycle list from all tabs
//TODO create a static class ResourceUtils that get the list of music from sdcard/Download/PATHMUSICS
//TODO open the music pdf from uri
//TODO add the favorite music list feature
//TODO feature to download files from generic official music
//TODO get the list of music out of UIThread
//TODO implement the fragment
//TODO a setting panel
//TODO add support for Portugues e Espanhol

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermissionForReadExtertalStorage()){
            //if the read external storage permission is not granted, so we need to request to de user:
            requestPermissionForReadExtertalStorage()
        }

        val musics = getMusicNamesAvailable()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, musics)
        etText.setAdapter(adapter)

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
            val result: Int = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    // function to request the permission to de user
    fun requestPermissionForReadExtertalStorage() {
        try {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //function to get the music list names in sdcard/Download/PATHMUSICS
    fun getMusicNamesAvailable(): List<String> {
        var musicsList = listOf<String>("Musica 1", "Musica 2", "Musica 3")
        //Check if sdcard is mounted or not
        if(getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d(TAG, "The getExternalStorageState() is mounted")
            // Accessing the downloads folder
            val folder = getExternalStoragePublicDirectory("$DIRECTORY_DOWNLOADS/$PATHMUSICS")
            if(folder.isDirectory){
                //get the list of files inside the folder
                val musicList = folder.listFiles()
                if(musicList != null) {
                    musicsList = musicList.map {it.name}
                    //TODO: this for is only to test, remove this in feature
                    for(music in musicsList) {
                        //Log.d(TAG, "music: $music")
                    }
                } else {
                    Log.d(TAG, "musicList == null")
                }
            } else {
                Log.d(TAG, "$PATHMUSICS is NOT a directory")
            }
        } else {
            Log.d(TAG, "The getExternalStorageState() is NOT mounted")
        }
        return musicsList
    }

}