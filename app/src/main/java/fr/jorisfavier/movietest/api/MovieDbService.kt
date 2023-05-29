package fr.jorisfavier.movietest.api

import fr.jorisfavier.movietest.models.dto.ChangedMovieDTO
import fr.jorisfavier.movietest.models.dto.MovieDetailDTO
import fr.jorisfavier.movietest.models.dto.ResultDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface MovieDbService {
    @GET("movie/changes")
    suspend fun lastChangedMovies(
        @Query("end_date") endDate: Date?,
        @Query("start_date") startDate: Date?,
        @Query("page") page: Int?
    ): ResultDTO<ChangedMovieDTO>

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query("language") language: String?
    ): MovieDetailDTO

    companion object {
        private const val apiKey = "9d7df10f66289578b3d1eb93b8f024cb"
        const val imageBaseUrl = "https://image.tmdb.org/t/p/original/"

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
                    GsonConverterFactory.create()
                )
                .baseUrl("https://api.themoviedb.org/3/")
                .client(client)
                .build()

            return retrofit.create(MovieDbService::class.java)
        }
    }
}