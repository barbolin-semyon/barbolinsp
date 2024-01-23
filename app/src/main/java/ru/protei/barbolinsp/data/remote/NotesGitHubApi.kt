package ru.protei.barbolinsp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NotesGitHubApi {
    @GET("issues")
    suspend fun getList(@Query("direction") directionSort: String): Response<List<GitHubIssue>>

    @POST("issues")
    suspend fun add(@Body issue: GitHubIssue): Response<GitHubIssue>

    @PATCH("issues/{number}")
    suspend fun update(
        @Path("number") number: Long,
        @Body issue: GitHubIssue
    ): Response<GitHubIssue>

    @PATCH("issues/{number}")
    suspend fun close(@Path("number") number: Long, @Body hashMap: HashMap<String, String>):Response<Unit>
}