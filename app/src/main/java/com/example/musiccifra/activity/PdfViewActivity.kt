package com.example.musiccifra.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.musiccifra.R
import kotlinx.android.synthetic.main.activity_pdf_view.*

class PdfViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        //get URI
        val myUri = intent.getStringExtra("URI")
        Log.d("layon.f", "Open PdfViewActivity URI: $myUri")
        showPdfFromURI(Uri.parse(myUri))
    }

    private fun showPdfFromURI(uri: Uri?) {
        pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }
}