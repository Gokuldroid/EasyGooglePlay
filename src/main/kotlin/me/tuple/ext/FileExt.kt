package me.tuple.ext

import com.android.utils.FileUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

/**
 * Created by Gokul.
 */

fun File?.hasFile(fileName: String): Boolean {
    this?.apply {
        return File(this, fileName).exists()
    }
    return false
}

fun File.readAsJSON(): JSONObject {
    return JSONObject(this.readLines().joinToString(separator = ""))
}

fun File.readAsJSONArray(): JSONArray {
    return JSONArray(this.readLines().joinToString(separator = ""))
}

fun moveFile(source: File, destination: File) {
    if (!destination.parentFile.exists()) {
        destination.mkdirs()
    }
    FileUtils.copyFile(source, destination)
}