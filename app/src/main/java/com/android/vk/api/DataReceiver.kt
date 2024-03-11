package com.android.vk.api

import com.android.vk.data.Product
import com.android.vk.data.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private val BASE_URL = "https://dummyjson.com/"
private lateinit var productService: ApiService

suspend fun fetchData(skip:Int,limit:Int): List<Product> = withContext(Dispatchers.IO) {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    productService = retrofit.create(ApiService::class.java)

    try {
        val response = productService.getProducts(skip = skip, limit = limit).execute()
        if (response.isSuccessful) {
            val productResponse: ProductResponse? = response.body()
            return@withContext productResponse?.products ?: emptyList()
        } else {
            throw IOException("Error: ${response.code()}")
        }
    } catch (e: IOException) {
        throw IOException("Failed to fetch data: ${e.message}", e)
    }
}