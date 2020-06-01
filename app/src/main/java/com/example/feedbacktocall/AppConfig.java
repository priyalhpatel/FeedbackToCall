package com.example.feedbacktocall;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.Callback;
import retrofit.client.Response;

import retrofit.http.POST;
import retrofit.http.GET;

public class AppConfig {

    public interface insert{
         @FormUrlEncoded
        @POST("/feedback/insertData.php")
        Void insertData(
                @Field("Data") String et_feed,
                Callback<Response> Callback
         );


    }
}
