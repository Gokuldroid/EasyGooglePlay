package me.tuple.playstore

/**
 * Created by gokul.
 */

const val MIME_TYPE_APK = "application/vnd.android.package-archive"

open class PublishConfig(
        var serviceAccountEmail: String? = null,
        var jsonKeyPath: String? = null,
        var p2kKeyPath: String? = null,
        var track: String = "alpha",
        var apkFilePath: String? = null,
        var packageName: String? = null,
        var whatsNew: String? = null,
        var mappingFile: String? = null,
        var rolloutPercent: Double = 0.1
) {
    constructor() : this(
            track = PublishTrack.ALPHA.toString(),
            rolloutPercent = PublishTrack.ALPHA.rolloutPercent)

    override fun toString(): String {
        return "PublishConfig(serviceAccountEmail=$serviceAccountEmail, jsonKeyPath=$jsonKeyPath, p2kKeyPath=$p2kKeyPath, track=$track, apkFilePath=$apkFilePath, packageName=$packageName, whatsNew=$whatsNew)"
    }


}

enum class PublishTrack(private val trackString: String, public var rolloutPercent: Double = 0.1) {
    ALPHA("alpha"),
    BETA("beta"),
    ROLL_OUT("rollout"),
    PRODUCTION("production");

    override fun toString(): String {
        return trackString
    }
}