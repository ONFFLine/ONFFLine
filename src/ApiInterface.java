import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("custom.php")
    Call<Void> updateCustom(@Field("uid") String userId, @Field("hairShape") String hairShape, @Field("hairColor") String hairColor, @Field("eyeColor") String eyeColor,
                            @Field("skinColor") String skinColor);


    @FormUrlEncoded
    @POST("createroom.php")
    Call<String> createRoom(@Field("uid") String userId);

    @FormUrlEncoded
    @POST("getPartList.php")
    Call<String> getPartList(@Field("rid") int roomId);
}
