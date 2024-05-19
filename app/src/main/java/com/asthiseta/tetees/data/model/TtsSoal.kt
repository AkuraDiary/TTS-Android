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


class TSilang {
    private var tts: Array<Array<String?>>
    private var rows = 10
    private var cols = 10
    private val DEFAULT: String? = null
    private var wKode: Array<Array<String?>>
    private var wClue: Array<Array<String?>>
    private var wOrie: Array<Array<TtsOrientation?>>
    private var size = -1

    constructor() {
        tts = Array(rows) {
            arrayOfNulls(
                cols
            )
        }
        wKode = Array(rows) {
            arrayOfNulls(
                cols
            )
        }
        wClue = Array(rows) {
            arrayOfNulls(
                cols
            )
        }
        wOrie = Array(rows) {
            arrayOfNulls(
                cols
            )

        //    BooleanArray(cols)
        }

        for (y in tts.indices) {
            for (x in tts[y].indices) {
                tts[y][x] = " "
                wKode[y][x] = DEFAULT
                wClue[y][x] = DEFAULT
                wOrie[y][x] = TtsOrientation.HORIZONTAL//null != null

                size++
            }
        }
    }

    constructor(mrows: Int, mCols: Int) {
        rows = mrows
        cols = mCols

        tts = Array(rows) {
            arrayOfNulls(
                cols
            )
        }
        wKode = Array(rows) {
            arrayOfNulls(
                cols
            )
        }
        wClue = Array(rows) {
            arrayOfNulls(
                cols
            )
        }
        wOrie = Array(rows) {
           arrayOfNulls(cols)
        }

        for (y in tts.indices) {
            for (x in tts[y].indices) {
                tts[y][x] = " "
                wKode[y][x] = DEFAULT
                wClue[y][x] = DEFAULT
                wOrie[y][x] = TtsOrientation.VERTICAL //null != null

                size++
            }
        }
    }

    fun add(s: String, cl: String, row: Int, colm: Int, b: TtsOrientation): Boolean {
        var j = 0
        if (b == TtsOrientation.HORIZONTAL) {
            while (j < s.length) {
                if (wKode[row - 1][colm - 1 + j] != null) {
                    wKode[row - 1][colm - 1 + j] = wKode[row - 1][colm - 1 + j] + ":" + s
                    wClue[row - 1][colm - 1 + j] = wClue[row - 1][colm - 1 + j] + ";" + cl
                } else {
                    wKode[row - 1][colm - 1 + j] = s
                    wClue[row - 1][colm - 1 + j] = cl
                }
                tts[row - 1][colm - 1 + j] = s[j].toString()
                wOrie[row - 1][colm - 1 + j] = TtsOrientation.HORIZONTAL//true
                j++
            }
        } else {
            while (j < s.length) {
                if (wKode[row - 1 + j][colm - 1] != null) {
                    wKode[row - 1 + j][colm - 1] = wKode[row - 1 + j][colm - 1] + ":" + s
                    wClue[row - 1 + j][colm - 1] = wClue[row - 1 + j][colm - 1] + ";" + cl
                } else {
                    wKode[row - 1 + j][colm - 1] = s
                    wClue[row - 1 + j][colm - 1] = cl
                }
                tts[row - 1 + j][colm - 1] = s[j].toString()
                wOrie[row - 1 + j][colm - 1] = TtsOrientation.VERTICAL//false
                j++
            }
        }
        return true
    }

    fun hTts(row: Int, cols: Int): String? {
        return tts[row][cols]
    }

    fun hKode(row: Int, cols: Int): String? {
        return wKode[row][cols]
    }

    fun hClue(row: Int, cols: Int): String? {
        return wClue[row][cols]
    }

    fun hOrie(row: Int, cols: Int): TtsOrientation? {
        return wOrie[row][cols]
    }

    fun size(): Int {
        return size
    }

    companion object {
        const val TAG: String = "TSilang"
        val MENDATAR: TtsOrientation = TtsOrientation.HORIZONTAL
        val MENURUN: TtsOrientation = TtsOrientation.VERTICAL
        const val KOTAK_12X12: Int = 12
        const val KOTAK_10X10: Int = 10
    }
}
