package com.example.musiccifra.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musiccifra.R
import com.example.musiccifra.adapter.MusicAdapter
import com.example.musiccifra.Util.ResourcesUtil
import kotlinx.android.synthetic.main.fragment_one.*

class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onResume() {
        super.onResume()

        Log.d("layon.f", "configure recyler View")
        //get the recyclerView
        val recyclerView = recyclerViewOne
        //get the list of music
        //TODO create a static class ResourceUtils that get the list of music from sdcard/Download/PATHMUSICS
        //create a test list
        val musicList = arrayOf("music name complete 1",
            "music name complete 2",
            "music name complete 3",
            "music name complete 4")

        val resourcesUtil = ResourcesUtil()
        val musics = resourcesUtil.getMusicNamesAvailables()
        //set the adapter
        recyclerView.adapter = MusicAdapter(musics.toTypedArray())
        //set layout manager
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
    }

    companion object {
        fun newInstance() = OneFragment()
    }
}