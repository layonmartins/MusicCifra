package com.example.musiccifra.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musiccifra.R
import com.example.musiccifra.activity.PdfViewActivity
import com.example.musiccifra.model.Music

class AllMusicAdapter(private val dataSet: MutableList<Music>) :
        RecyclerView.Adapter<AllMusicAdapter.ViewHolder>(), Filterable {

    //create a copy of dataSet, so dataSetAll always will have the list of all musics
    //this is need because the dataSet can be filtered by searchView
    var dataSetAll = dataSet.toMutableList()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val name: TextView
        val uri: TextView
        val favorite: ViewGroup //view that contains the ImageView
        val img_favorit: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            name = view.findViewById(R.id.name)
            name.setOnClickListener(this@ViewHolder)
            uri = view.findViewById(R.id.uri)
            img_favorit = view.findViewById(R.id.img_favorite)
            favorite = view.findViewById(R.id.favorite)
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
        val isFavorit = dataSet[position].favorite
        if(isFavorit) {
            viewHolder.img_favorit.setImageResource(R.drawable.ic_favorit_star_24)
        }
        viewHolder.favorite.setOnClickListener {
            if(dataSet[position].favorite) {
                dataSet[position].favorite = false
                viewHolder.img_favorit.setImageResource(R.drawable.ic_no_favorit_star_border_24)
            } else {
                dataSet[position].favorite = true
                viewHolder.img_favorit.setImageResource(R.drawable.ic_favorit_star_24)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    override fun getFilter(): Filter? {
        Log.d("layon.f", "getFilter()")
        return myFilter
    }

    var myFilter: Filter = object : Filter() {

        //Automatic on background thread
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            Log.d("layon.f", "performFiltering(CharSequence: $charSequence)")
            val filteredList: MutableList<Music> = ArrayList()

            //filteredList.addAll(dataSetAll)
            /*if (charSequence != null) {
                for (music in dataSetAll) { /*if (charSequence == null || charSequence.length == 0) {
                filteredList.addAll(dataSetAll)
            } else {
                for (music in dataSetAll) {
                    if (music.name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(music)
                    }
                }
            }*/
                    if (music.name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(music)
                    }
                }
            }*/

            if (charSequence == null || charSequence.length == 0) {
                filteredList.addAll(dataSetAll)
            } else {
                for (music in dataSetAll) {
                    if (music.name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        Log.d("layon.f", "filteredList.add($music)")
                        filteredList.add(music)
                    }
                }
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        //Automatic on UI thread
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            Log.d("layon.f", "publishResults(CharSequence: $charSequence, FilterResults: $filterResults)")
            for (music in filterResults.values as Collection<Music>) {
                Log.d("layon.f", "filter musics: $music")
            }
            dataSet.clear()
            dataSet.addAll(filterResults.values as Collection<Music>)
            Log.d("layon.f", "filter musics size: ${(filterResults.values as Collection<Music>).size}")
            notifyDataSetChanged()
        }
    }


}
