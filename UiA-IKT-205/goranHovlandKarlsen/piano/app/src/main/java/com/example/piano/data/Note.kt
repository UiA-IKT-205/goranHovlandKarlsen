package com.example.piano.data

data class Note(val value:String, val start:Long, val end:Long){

    override fun toString(): String {
        val start_ms = start / 1000000
        val end_ms = end / 1000000
        val time = end_ms - start_ms
        return "$value held $time ms --> $start_ms ms, $end_ms ms"
    }
}
