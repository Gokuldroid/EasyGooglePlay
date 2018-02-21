package me.tuple.playstore

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import java.io.File
import java.util.*

/**
 * Created by Gokul.
 */
object AuthHelper {

    val APP_NAME = "easy_google_play"

    private val httpTransport by lazy {
        GoogleNetHttpTransport.newTrustedTransport()
    }

    private val jsonFactory by lazy {
        JacksonFactory.getDefaultInstance()
    }

    private fun getCredentialFromJson(jsonPath: String): GoogleCredential {
        val serviceAccountStream = File(jsonPath).inputStream()
        val credential = GoogleCredential.fromStream(serviceAccountStream, httpTransport, jsonFactory)
        return credential.createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER))
    }

    private fun setHttpTimeout(requestInitializer: HttpRequestInitializer): HttpRequestInitializer {
        return HttpRequestInitializer { request: HttpRequest ->
            apply {
                requestInitializer.initialize(request)
                request.connectTimeout = 3 * 60000
                request.readTimeout = 3 * 60000
            }
        }
    }

    fun getPublisherApi(publishConfig: PublishConfig): AndroidPublisher {
        if (publishConfig.jsonKeyPath != null) {
            return AndroidPublisher.Builder(httpTransport, jsonFactory, setHttpTimeout(getCredentialFromJson(publishConfig.jsonKeyPath!!)))
                    .setApplicationName(APP_NAME)
                    .build()
        } else {
            throw UnsupportedOperationException("Operation not supported yet")
        }
    }
}
