package com.example.quantumnewsapp.ApiUtilities;

import com.example.quantumnewsapp.Model.ParentNewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {

    @GET("top-headlines")
    Call<ParentNewsModel> getNews(
            @Query("country") String country,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey,
            @Query("page") int page
    );
}
