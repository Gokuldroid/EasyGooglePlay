package me.tuple.ext

import org.json.JSONObject

/**
 * Created by Gokul.
 */

fun JSONObject.increment(property: String) {
    if (has(property)) {
        val value = get(property) as Number
        when (value) {
            is Int -> put(property, value.inc())
            is Long -> put(property, value.inc())
        }
    } else {
        put(property, 1)
    }
}