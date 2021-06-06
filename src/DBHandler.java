import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.POST;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBHandler {
    private final Retrofit retrofit;

    public int createRoom(UserData userData){
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> createRoomResult = api.createRoom(userData.getUserId());

        int roomId=0;
        try {
            String response = createRoomResult.execute().body();
            roomId= Integer.parseInt(response);
        } catch (IOException e) {
            System.out.println(e);
        }
        return roomId;
    }

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

    public String getParticipantsList(int roomId) {

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<String> createRoomResult = api.getPartList(roomId);

        String response = null;
        try {
            response = createRoomResult.execute().body();
        } catch (IOException e) {
            System.out.println(e);
        }

        return response;

    }

    public DBHandler() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://3.37.112.49/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}