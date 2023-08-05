package com.sina.network.services

import com.sina.core.network.model.product_list.ProductLstDTOItem
import com.sina.core.network.params.ServerParams.CONSUMER_KEY
import com.sina.core.network.params.ServerParams.CONSUMER_SECRET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkerApi {
    @GET("wp-json/wc/v3/products")
    suspend fun getNewProductList(
        @Query("after") after: String,
        @Query("consumer_key") consumer_key: String = CONSUMER_KEY,
        @Query("consumer_secret") consumer_secret: String = CONSUMER_SECRET,
    ): Response<List<ProductLstDTOItem>>
}