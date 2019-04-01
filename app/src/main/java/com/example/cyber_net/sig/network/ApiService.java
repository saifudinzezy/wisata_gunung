package com.example.cyber_net.sig.network;

import com.example.cyber_net.sig.model.response.ResponseAdmin;
import com.example.cyber_net.sig.model.response.ResponseDelete;
import com.example.cyber_net.sig.model.response.ResponseInsert;
import com.example.cyber_net.sig.model.response.ResponseManfaat;
import com.example.cyber_net.sig.model.response.ResponsePos;
import com.example.cyber_net.sig.model.response.ResponseSafety;
import com.example.cyber_net.sig.model.response.ResponseView;
import com.example.cyber_net.sig.model.response.ResponseWisata;
import com.example.cyber_net.sig.model.cuaca.ResponseWeather;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    //TODO jika error lang.NullPointerException
    // at ListLaporan$1.onResponse(ListLaporan.java:63)
    //cek di getnya
    //get semua data
    @GET("api/read/getwisata.php")
    Call<ResponseWisata> getWisata();

    @GET("api/read/manfaat.php")
    Call<ResponseManfaat> getManfaat();

    @GET("api/read/safety.php")
    Call<ResponseSafety> getSafety();

    //https://api.openweathermap.org/data/2.5/weather?q=kesesirejo&units=metric&appid=cbfdb21fa1793c10b14b6b6d00fbef03
    @GET("weather")
    Call<ResponseWeather> getWeather(@Query("q") String city, @Query("units") String units, @Query("appid") String api);

    //https://api.openweathermap.org/data/2.5/weather?lat=-7.026253&lon=109.581192&units=metric&appid=cbfdb21fa1793c10b14b6b6d00fbef03
    @GET("weather")
    Call<ResponseWeather> getWeatherKoordinat(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String units, @Query("appid") String api);

    @FormUrlEncoded
    @POST("api/read/galeri2.php")
    Call<ResponseView> getView(@Field("id_wisata") String id);

    @FormUrlEncoded
    @POST("api/read/pos.php")
    Call<ResponsePos> getPosPendakian(@Field("id_wisata") String id);

    //login admin
    @FormUrlEncoded
    @POST("api/read/login.php")
    Call<ResponseAdmin> signInUser(@Field("nama") String nama,
                                   @Field("password") String password);

    //delete
    @FormUrlEncoded
    @POST("api/delete/delete.php")
    Call<ResponseDelete> delete(@Field("tabel") String tabel, @Field("cari") String cari, @Field("id_data") String idData);

    //insert
    @Multipart
    @POST("api/create/manfaat.php")
    Call<ResponseInsert> insertData(@Part("id_admin") int id_admin,
                                    @Part MultipartBody.Part image,
                                    @Part("judul") String judul,
                                    @Part("deskripsi") String deskripsi,
                                    @Part("tanggal") String tanggal);

    @Multipart
    @POST("api/create/safety.php")
    Call<ResponseInsert> insertSafety(@Part("id_admin") int id_admin,
                                      @Part MultipartBody.Part image,
                                      @Part("judul") String judul,
                                      @Part("deskripsi") String deskripsi,
                                      @Part("tanggal") String tanggal);

    @Multipart
    @POST("api/create/wisata.php")
    Call<ResponseInsert> insertWisata(@Part("judul") String judul,
                                      @Part MultipartBody.Part image,
                                      @Part("lokasi") String lokasi,
                                      @Part("deskripsi") String deskripsi,
                                      @Part("latitude") String latitude,
                                      @Part("longitude") String longitude,
                                      @Part("ketinggian") String ketinggian,
                                      @Part("fasilitas") String fasilitas,
                                      @Part("tipe_tanah") String tipe_tanah,
                                      @Part("tipe_gunung") String tipe_gunung);

    @Multipart
    @POST("api/create/galeri.php")
    Call<ResponseInsert> insertGaleri(@Part("id_wisata") int id_wisata,
                                      @Part MultipartBody.Part image,
                                      @Part("nama") String nama);

    @FormUrlEncoded
    @POST("api/create/pos.php")
    Call<ResponseInsert> insertPos(@Field("id") String id,
                                      @Field("nama") String nama,
                                      @Field("latitude") String latitude,
                                      @Field("longitude") String longitude);

    //update manfaat
    @Multipart
    @POST("api/create/updatemanfaat.php")
    Call<ResponseInsert> updateManfaat1(@Part("id") int id,
                                        @Part MultipartBody.Part image,
                                        @Part("judul") String judul,
                                        @Part("deskripsi") String deskripsi);

    @FormUrlEncoded
    @POST("api/create/updatemanfaat.php")
    Call<ResponseInsert> updateManfaat2(@Field("id") int id,
                                        @Field("judul") String judul,
                                        @Field("deskripsi") String deskripsi);

    //update safety
    @Multipart
    @POST("api/create/updatesafety.php")
    Call<ResponseInsert> updateSafety1(@Part("id") int id,
                                       @Part MultipartBody.Part image,
                                       @Part("judul") String judul,
                                       @Part("deskripsi") String deskripsi);

    @FormUrlEncoded
    @POST("api/create/updatesafety.php")
    Call<ResponseInsert> updateSafety2(@Field("id") int id,
                                       @Field("judul") String judul,
                                       @Field("deskripsi") String deskripsi);

    //update wisata
    @Multipart
    @POST("api/create/updatewisata.php")
    Call<ResponseInsert> updateWisata1(@Part("id") int id,
                                       @Part MultipartBody.Part image,
                                       @Part("judul") String judul,
                                       @Part("lokasi") String lokasi,
                                       @Part("deskripsi") String deskripsi,
                                       @Part("latitude") String latitude,
                                       @Part("longitude") String longitude,
                                       @Part("ketinggian") String ketinggian,
                                       @Part("fasilitas") String fasilitas,
                                       @Part("tipe_tanah") String tipe_tanah,
                                       @Part("tipe_gunung") String tipe_gunung);

    @FormUrlEncoded
    @POST("api/create/updatewisata.php")
    Call<ResponseInsert> updateWisata2(@Field("id") int id,
                                       @Field("judul") String judul,
                                       @Field("lokasi") String lokasi,
                                       @Field("deskripsi") String deskripsi,
                                       @Field("latitude") String latitude,
                                       @Field("longitude") String longitude,
                                       @Field("ketinggian") String ketinggian,
                                       @Field("fasilitas") String fasilitas,
                                       @Field("tipe_tanah") String tipe_tanah,
                                       @Field("tipe_gunung") String tipe_gunung);

    //update galeri
    @Multipart
    @POST("api/create/updategaleri.php")
    Call<ResponseInsert> updateGaleri1(@Part("id") int id,
                                       @Part MultipartBody.Part image,
                                       @Part("nama") String nama);

    @FormUrlEncoded
    @POST("api/create/updategaleri.php")
    Call<ResponseInsert> updateGaleri2(@Field("id") int id,
                                       @Field("nama") String nama);

    @FormUrlEncoded
    @POST("api/create/updatepos.php")
    Call<ResponseInsert> updatePos(@Field("id") String id,
                                   @Field("nama") String nama,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);
}