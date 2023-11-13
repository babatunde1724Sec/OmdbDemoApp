package com.example.omdbdemoapp.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

  private const val BASE_URL = "http://www.omdbapi.com/"
  private var retrofit: Retrofit? = null

  val client: Retrofit
    get() {
      if (retrofit == null) {
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
      }
      return retrofit!!
    }
}