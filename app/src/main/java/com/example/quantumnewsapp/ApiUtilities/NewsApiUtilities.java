package com.example.quantumnewsapp.ApiUtilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsApiUtilities {

    private static String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit retrofit = null;

    public static NewsApiInterface getApiInterface () {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(NewsApiInterface.class);
    }

}
