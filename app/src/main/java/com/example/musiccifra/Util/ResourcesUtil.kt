package com.example.musiccifra.Util

import android.os.Environment
import android.util.Log
import com.example.musiccifra.PATHMUSICS
import com.example.musiccifra.TAG

class ResourcesUtil {

    //function to get the music list names in sdcard/Download/PATHMUSICS
    public fun getMusicNamesAvailables(): List<String> {
        var musicsList = listOf<String>("Musica 1", "Musica 2", "Musica 3")
        //Check if sdcard is mounted or not
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d(TAG, "The getExternalStorageState() is mounted")
            // Accessing the downloads folder
            val folder =
                Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOWNLOADS}/$PATHMUSICS")
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