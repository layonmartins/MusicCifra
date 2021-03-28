package com.example.musiccifra.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment.*
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.musiccifra.R
import com.example.musiccifra.Util.ResourcesUtil
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
//TODO create a static class ResourceUtils that get the list of music from sdcard/Download/PATHMUSICS
//TODO open the music pdf from uri
//TODO add the favorite music list feature
//TODO feature to download files from generic official music
//TODO get the list of music out of UIThread
//TODO implement the fragment
//TODO a setting panel
//TODO add support for Portugues e Espanhol
//TODO open pdf on click
//TODO apply styles on Material AutoComplete
//TODO rework the autocomplete: It is like be good, the texts are overriding. Maybe is better to hide the recycler view before.

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermissionForReadExtertalStorage()){
            //if the read external storage permission is not granted, so we need to request to de user:
            requestPermissionForReadExtertalStorage()
        }

        //configure auto complete
        val musics = ResourcesUtil.getMusicNamesAvailable()
        val adapter = ArrayAdapter<Music>(this, android.R.layout.simple_dropdown_item_1line, musics)
        etText.setAdapter(adapter)
        etText.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->

            //TODO create a util method to call the PdfViewActivity
            val intent = Intent(this, PdfViewActivity::class.java)
            //URI should be like: content://0@media/external/file/2792
            val music = adapter.getItem(position)
            Log.d("layon.f", "AutoComplite clicked name: ${music?.name} uri: ${music?.uri}")
            intent.putExtra("URI", music?.uri.toString())
            startActivity(intent)
        })


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

    fun onClick(v: View?) {
        val intent = Intent(this, PdfViewActivity::class.java)
        intent.putExtra("ViewType", "storage")
        startActivity(intent)
    }
}