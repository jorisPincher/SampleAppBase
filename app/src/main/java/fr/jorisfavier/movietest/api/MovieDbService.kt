package fr.jorisfavier.movietest.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import fr.jorisfavier.movietest.models.dto.ChangedMovieDTO
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import fr.jorisfavier.movietest.models.dto.ResultDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface MovieDbService {
    @GET("movie/changes")
    fun lastChangedMovies(@Query("end_date") endDate: Date?,
                          @Query("start_date") startDate: Date?,
                          @Query("page") page: Int?) : Call<ResultDTO<ChangedMovieDTO>>
    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: Int,
                       @Query("language") language: String?): Call<MovieDetailDTO>

    companion object {
        val apiKey = "<APIKey>"
        val imageBaseUrl = "https://image.tmdb.org/t/p/original/"

        fun create(): MovieDbService {
            //We create an interceptor to add an api key to each
            //call for this API
            val interceptor = Interceptor { chain ->
                val newUrl = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", apiKey).build()
                val requestBuilder = chain.request().newBuilder()
                    .url(newUrl)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .build()

            return retrofit.create(MovieDbService::class.java)
        }
    }
}