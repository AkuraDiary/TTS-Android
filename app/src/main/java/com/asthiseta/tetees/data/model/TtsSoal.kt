package com.asthiseta.tetees.data.model

data class TtsSoal(
    var tts: String,
    var clue: String,
    var row: Int,
    var colm: Int,
    var orietation: TtsOrientation
)

enum class TtsOrientation {
    HORIZONTAL,
    VERTICAL
}