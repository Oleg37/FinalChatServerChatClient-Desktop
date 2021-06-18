package model.rest.retrofit;

import model.rest.pojo.Message;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MessageClient {

    @GET("message")
    Call<List<Message>> getMessages();

    @GET("message/{id}")
    Call<Message> getMessageById(@Path("id") long id);

    @POST("message")
    Call<Message> insertMessage(@Body Message message);

    @PUT("message/{id}")
    Call<Boolean> updateMessage(@Path("id") long id, @Body Message message);

    @DELETE("message/{id}")
    Call<Message> deleteMessageById(@Path("id") long id);

    @GET("getPrivateMessage/{userSend}/{userReceiver}")
    Call<List<Message>> getPrivateMessage(@Path("userSend") String userSend, @Path("userReceiver") String userReceiver);

    @DELETE("destroyAllMSG")
    Call<List<Message>> deleteAll();
}
