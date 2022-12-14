package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.dto.*
import com.example.myapplication.features.login.domain.model.AuthData
import com.example.myapplication.features.profile.domain.model.UserProfile
import retrofit2.http.*

interface WebApi {

    @Headers("Content-Type:text/json")
    @POST("/api/LogIn")
    suspend fun signIn(@Body userCredentialDto: UserCredentialDto): AuthData

    @Headers("Content-Type:text/json")
    @GET("/api/LogIn/SignOut")
    suspend fun signOut()

    @Headers("Content-Type:text/json")
    @POST("/api/LogIn/Verify")
    suspend fun verify(@Header("sessionToken") token: String): AuthVerificationDto

    @Headers("Content-Type:text/json")
    @POST("/api/Fetch/UserProfile/{userId}")
    suspend fun getProfile(@Path("userId") userId: Int, @Header("sessionToken") token: String): UserProfile

    @Headers("Content-Type:text/json")
    @GET("/api/Fetch/PostsOfUser/{userId}")
    suspend fun getPostsByUserId(@Path("userId") userId: Int,
                                 @Header("sessionToken") token: String,
                                 @Query("length") length: Int,
                                 @Query("lastId") lastId: Int = -1,
                                 @Query("olderFirst") olderFirst: Boolean = false): FeedDto

    @Headers("Content-Type:text/json")
    @GET("/api/Fetch/Post/{postId}")
    suspend fun getPostById(@Path("postId") postId: Int,
                            @Header("sessionToken") token: String): PostDataDto

    @Headers("Content-Type:text/json")
    @GET("/api/Fetch/Post/{postId}/Comments")
    suspend fun getCommentsOfPostById(@Path("postId") postId: Int,
                                      @Header("sessionToken") token: String,
                                      @Query("lastId") lastId: Int = -1,
                                      @Query("take") take: Int = 10): CommentFeedDto
}