package com.asthiseta.tetees.data.model

data class TtsSoal(
    var tts: String,
    var clue: String,
    var row: Int,
    var colm: Int,
    var orietation: TtsOrientation
)

data class TTS(

    var tts: String? = null,
    var kode: String? = null,
    var clue: String? = null,
    var id: Int = 0,
    var up: String? = null,
    var orientation: TtsOrientation? = null
)

enum class TtsOrientation {
    HORIZONTAL,
    VERTICAL
}