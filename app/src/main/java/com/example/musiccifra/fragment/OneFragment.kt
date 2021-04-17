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

        //if the music is empty
        if ((activity as MainActivity).musics.isNotEmpty()) {
            //disable the img
            imgEmpty.visibility = View.GONE
            //get the recyclerView
            val recyclerView = recyclerViewOne
            //set the adapter
            recyclerView.adapter = (activity as MainActivity).allMusicAdapter
            //set layout manager
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            //set divider
            val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                    layoutManager.orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        } else {
            imgEmpty.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance() = OneFragment()
    }
}