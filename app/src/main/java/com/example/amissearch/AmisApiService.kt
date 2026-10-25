package com.example.amissearch

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AmisApiService {
    @POST("amis/search")
    suspend fun searchAmis(
        @Body request: AmisRequest
    ): Response<AmisResponse>
}
