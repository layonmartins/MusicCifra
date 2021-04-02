package com.example.musiccifra.model

import java.net.URI

data class Music(
        val name: String,
        val uri: URI,
        var favorite: Boolean
) {
    override fun toString(): String = name
}