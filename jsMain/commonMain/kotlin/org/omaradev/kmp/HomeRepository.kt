package org.omaradev.kmp

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow
import org.omaradev.kmp.api_client.httpClient
import org.omaradev.kmp.data.model.Picsum

class HomeRepository {
    private suspend fun getImagesApi():List<Picsum>{
        return httpClient.get("https://picsum.photos/v2/list").body()
    }

    fun getImages() = flow{
        emit(getImagesApi())
    }
}