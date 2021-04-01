package com.example.musiccifra.model

import java.net.URI

data class Music(
        val name: String,
        val uri: URI,
        val favorit: Boolean
) {
    override fun toString(): String = name

    //TODO override toString to return name witht pdf suffix
}