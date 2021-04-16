package com.example.musiccifra.Util

import android.os.Environment
import android.util.Log
import com.example.musiccifra.activity.PATHMUSICS
import com.example.musiccifra.activity.TAG
import com.example.musiccifra.model.Music

class ResourcesUtil {

    companion object {

        //function to get the music list names in sdcard/Download/PATHMUSICS
        //and return as a list of Music
        //doesn't work on Android R
        fun getMusicNamesAvailable(): MutableList<Music> {
            var musicsList = mutableListOf<Music>()
            //Check if sdcard is mounted or not
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                Log.d(TAG, "The getExternalStorageState() is mounted")
                // Accessing the downloads folder
                val folder =
                        Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DOWNLOADS}/$PATHMUSICS")
                if(folder.isDirectory){
                    //get the list of files inside the folder
                    val fileList = folder.listFiles()
                    Log.d(TAG, "fileList: ${fileList.toString()}")
                    if(fileList != null) {
                                for(file in fileList){
                                    val music = Music(file.name.removeSuffix(".pdf"), file.toURI(), false)
                                    musicsList.add(music)
                                    Log.d(TAG, "musicsList.add($music)")
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

}