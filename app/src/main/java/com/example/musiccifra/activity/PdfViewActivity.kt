package com.example.musiccifra.activity

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.musiccifra.R
import com.github.barteksc.pdfviewer.listener.OnTapListener
import kotlinx.android.synthetic.main.activity_pdf_view.*


class PdfViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)

        //hide statusbar, navigation bar etc
        //hideSystemUI()

        //get URI
        val uri = intent.getStringExtra("URI")
        Log.d("layon.f", "Open PdfViewActivity URI: $uri")
        showPdfFromURI(Uri.parse(uri))

        //set the name of the music on action bar
        val name = intent.getStringExtra("NAME")
        setTitle(name)

        // calling the action bar
        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            // Customize the back button
            actionBar.setHomeAsUpIndicator(
                if (isDarkModeOn()) R.drawable.ic_baseline_arrow_back_ios_24_dark
                else R.drawable.ic_baseline_arrow_back_ios_24
            );
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

    }

    //method to check if dark mode is on
    private fun isDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    // this event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("layon,f", "onOptionsItemSelected")
        when (item.itemId) {
            android.R.id.home -> {
                Log.d("layon,f", "onOptionsItemSelected R.id.home")
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //listener when the user tap on screen
    var onTapListener = OnTapListener { e ->
        Log.d("layon,f", "Srceen tap")
        toogleSystemUI()
        false
    }

    //enable or disable the SystemUI fullscreen
    private fun toogleSystemUI() {
        if (isSystemUIHide) showSystemUI() else hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
       window.decorView.systemUiVisibility = (
               View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        // save the flag to be used on toogleSystemUI()
        isSystemUIHide = true
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        //fix the bug - white status bar and navigation icons when exite full screen
        if(!isDarkModeOn()){
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
        }

        // save the flag to be used on toogleSystemUI()
        isSystemUIHide = false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        recreate()
    }

    private fun showPdfFromURI(uri: Uri?) {
        pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .autoSpacing(true) // add dynamic spacing to fit each page on its own on the screen
            .nightMode(isDarkModeOn()) // toggle night mode
            .onTap(onTapListener)
            .load()
    }

    companion object {
        var isSystemUIHide = false
    }
}