import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.POST;

import java.net.HttpURLConnection;
import java.net.URL;

public class DBHandler {
    private final Retrofit retrofit;
    public void saveCustoms(CustomData customData) {

        UserData userData = new UserData();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<Void> customUpdateResult = api.updateCustom(userData.getUserId(),customData.getHairShape(),customData.getHairColor(), customData.getEyeColor(),customData.getSkinColor());

        customUpdateResult.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    //정상적으로 통신에 성공했을 경우
                } else {
                    //정상적으로 값을 받아오지 못한 경우
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }

    public DBHandler() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.37.112.49/")
                .build();

    }
}