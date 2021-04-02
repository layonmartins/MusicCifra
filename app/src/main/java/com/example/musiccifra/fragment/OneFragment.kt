package com.example.musiccifra.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musiccifra.R
import com.example.musiccifra.Util.ResourcesUtil
import com.example.musiccifra.activity.MainActivity
import com.example.musiccifra.adapter.AllMusicAdapter
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

        //get the recyclerView
        val recyclerView = recyclerViewOne
        //get the list of music
        //TODO create a static class ResourceUtils that get the list of music from sdcard/Download/PATHMUSICS
        //create a example test list

        val musics = (activity as MainActivity).musics
        Log.d("layon.f", "musics: ${musics.toString()}")
        //set the adapter
        recyclerView.adapter = AllMusicAdapter(musics.toTypedArray())
        //set layout manager
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        //set divider
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    companion object {
        fun newInstance() = OneFragment()
    }
}