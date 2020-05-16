package com.salman.projectabsensiguru.api;

import com.salman.projectabsensiguru.adapter.ItemAbsensi;
import com.salman.projectabsensiguru.adapter.ItemGuru;
import com.salman.projectabsensiguru.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("LoginAdmin")
    Call<ResponseBody> loginAdmin(@Body User user);

    @POST("LoginGuru")
    Call<ResponseBody> loginGuru(@Body User user);

    @GET("Guru")
    Call<List<ItemGuru>> getGuru();

    @GET("Guru")
    Call<List<ItemGuru>> getGuruByUsername(
            @Query("username") String username
    );

    @GET("AbsenGuru")
    Call<List<ItemAbsensi>> getAbsenByUsername(
            @Query("username") String username
    );

    @POST("AbsenGuru")
    Call<ResponseBody> absenGuru(@Body ItemAbsensi absen);

    @Multipart
    @POST("Guru")
    Call<ResponseBody> tambahGuru(
            @Part MultipartBody.Part photo,
            @PartMap Map<String, RequestBody> text);

}
