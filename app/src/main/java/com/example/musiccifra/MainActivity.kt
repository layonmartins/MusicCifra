package com.example.musiccifra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val musica = listOf<String>("Musica 1","Musica 2","Musica 3","Musica 4","Musica 5","Musica 6","Musica 7")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, musica)
        etText.setAdapter(adapter)
    }
}