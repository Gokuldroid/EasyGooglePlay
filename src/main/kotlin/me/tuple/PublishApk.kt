package me.tuple

import com.google.api.client.http.FileContent
import com.google.api.services.androidpublisher.model.ApkListing
import com.google.api.services.androidpublisher.model.Track
import java.io.File
import java.util.*


/**
 * Created by gokul-4192.
 */
object PublishApk {
    fun publish(publishConfig: Config) {

        val api = AuthHelper.getPublisherApi(publishConfig)

        val edits = api.edits()

        val editRequest = edits
                .insert(publishConfig.packageName, null)
        val edit = editRequest.execute()
        val editId = edit.id
        println((String.format("Created edit with id: %s", editId)))


        val apkFile = FileContent(MIME_TYPE_APK, File(publishConfig.apkFilePath))

        val uploadRequest = edits
                .apks()
                .upload(publishConfig.packageName,
                        editId,
                        apkFile)
        val apk = uploadRequest.execute()
        println((String.format("Version code %d has been uploaded",
                apk.versionCode)))
        publishConfig.whatsNew?.apply {
            val newApkListing = ApkListing().setRecentChanges(publishConfig.whatsNew)
            edits.apklistings()
                    .update(publishConfig.packageName, editId, apk.versionCode, "en", newApkListing)
                    .execute()
        }
        val apkVersionCodes = ArrayList<Int>()
        apkVersionCodes.add(apk.versionCode)
        val updateTrackRequest = edits
                .tracks()
                .update(publishConfig.packageName,
                        editId,
                        publishConfig.track.toString(),
                        Track().setVersionCodes(Collections.singletonList(apk.versionCode)))
        val updatedTrack = updateTrackRequest.execute()
        println(String.format("Track %s has been updated.", updatedTrack.track))

        // Commit changes for edit.
        val commitRequest = edits.commit(publishConfig.packageName, editId)
        val appEdit = commitRequest.execute()
        println(String.format("App edit with id %s has been comitted", appEdit.id))

    }
}