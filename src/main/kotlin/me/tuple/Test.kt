package me.tuple

/**
 * Created by gokul-4192.
 */
fun main(args: Array<String>) {
    val publishConfig = Config(
            jsonKeyPath = "E:\\intellij\\easy google play\\temp\\play_client_sec.json",
            track = PublishTrack.ALPHA,
            apkFilePath = "E:\\intellij\\easy google play\\temp\\app-release.apk",
            packageName = "tuple.me.vlcremote")

    PublishApk.publish(publishConfig)
}