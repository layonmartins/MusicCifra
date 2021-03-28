package com.example.musiccifra.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.musiccifra.R
import com.example.musiccifra.activity.PdfViewActivity
import com.example.musiccifra.model.Music

class AllMusicAdapter(private val dataSet: Array<Music>) :
        RecyclerView.Adapter<AllMusicAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val name: TextView
        val uri: TextView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.name)
            name.setOnClickListener(this@ViewHolder)
            uri = view.findViewById(R.id.uri)
            Log.d("layon.f", "init class ViewHolder")
        }

        override fun onClick(v: View?) {
            Log.d("layon.f", "OnClick position: $adapterPosition")
            val context = v?.context
            val intent = Intent(context, PdfViewActivity::class.java)
            //URI should be like: content://0@media/external/file/2792
            Log.d("layon.f", "onClick name: ${name.text} uri: ${uri.text}")
            intent.putExtra("URI", uri.text.toString())
            context?.startActivity(intent)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.music_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.text = dataSet[position].name
        viewHolder.uri.text = dataSet[position].uri.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}