package org.omaradev.kmp

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow

import org.omaradev.kmp.data.model.DummyPhotos

class HomeRepository(
    private val httpClient :HttpClient
) {

    private suspend fun getImagesApi(): DummyPhotos {
        return httpClient.get("https://dummyjson.com/carts").body()
    }

    fun getImages() = flow{
        emit(getImagesApi())
    }
}