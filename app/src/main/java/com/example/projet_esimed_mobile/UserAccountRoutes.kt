package com.example.projet_esimed_mobile



import retrofit2.Call
import retrofit2.http.*


interface UserAccountRoutes {

    @FormUrlEncoded
    @POST("/auth/login")

     fun postauthenticate(
        @Field("email") email : String ,
        @Field("motDePasse")motDePasse: String
    ) : Call<AuthenticationResult>

    @FormUrlEncoded
    @POST("/users/")
     fun inscription (  @Field("nom") nom : String ,
                        @Field("prenom")prenom: String,
                        @Field("email") email : String ,
                        @Field("motDePasse")motDePasse: String,
                        @Field("pseudo") pseudo : String ): Call<AuthenticationResult>

    @FormUrlEncoded
    @POST("/group/create")
    fun createGroupe ( @Field("nomGroupe") nomGroupe: String): Call<AuthenticationResult>

    @FormUrlEncoded
    @POST("/group/usergroup")
    fun addUserInGroup (  @Field("GroupId") GroupeId : String,
                          @Field("UserId")UserId : String): Call<AuthenticationResult>

    @FormUrlEncoded
    @POST("/cours/createcours")
    fun createCours ( @Path("nomCours") nomCours: String): Call<AuthenticationResult>

    @GET("/users/searchby/{pseudo}")
    fun findUserByPseudo(@Path("pseudo") pseudo: String, @Header("Authorization")token : String): Call<GetResult>

    @GET("/users/")
    fun users (): Call<List<GetResult>>

  /*  @GET("/api/searchtypes/{Id}/filters")
    fun getFilterList(
        @Path("Id") customerId: Long,
        @Query("Type") responseType: String?,
        @Query("SearchText") searchText: String?*/


    @GET("/users/")
    fun findUserById (@Query("id") id: String): Call<GetResult>



    @GET("/group/{id}")
    fun findGroupById (@Header("Authorization") token : String,@Path("id") id: String): Call<GetResultGroup>

    @GET("/group/{nomGroupe}")
    fun findGroupByName (@Path("nomGroupe") nomGroupe: String,@Header("Authorization") token : String): Call<GetResultGroup>

    @GET("/group/usergroup")
    fun getGroupeNamesOfUser (@Header("Authorization") token : String,@Query("UserId") UserId: String): Call<GetResult>
}

