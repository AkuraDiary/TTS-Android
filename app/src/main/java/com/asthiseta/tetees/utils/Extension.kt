package com.asthiseta.tetees.utils

import com.google.gson.Gson
import java.lang.reflect.Type

object Extension {
    fun <T> JsonToObject(string: String?, type: Type?): T {
        return Gson().fromJson(string, type)
    }
}