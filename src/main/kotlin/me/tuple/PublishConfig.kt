package me.tuple

/**
 * Created by gokul.
 */

val MIME_TYPE_APK = "application/vnd.android.package-archive"

data class Config(
        var serviceAccountEmail: String? = null,
        val jsonKeyPath: String? = null,
        val p2kKeyPath: String? = null,
        val track: PublishTrack,
        val userFraction: Double = 0.1,
        val apkFilePath: String,
        val packageName: String,
        val whatsNew:String? =null
)

enum class PublishTrack(val trackString: String) {
    ALPHA("alpha"),
    BETA("beta"),
    ROLL_OUT("rollout"),
    PRODUCTION("production");

    override fun toString(): String {
        return trackString
    }
}