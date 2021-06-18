package model;

import lombok.NonNull;
import model.rest.pojo.Message;
import model.rest.retrofit.MessageClient;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import util.AdminThread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {

    private final String RETROFIT_URL = "https://informatica.ieszaidinvergeles.org:10026/laraveles/ej5ChatServerChatClient/public/api/";
    private final MessageClient messageClient;
    private final Retrofit retrofit;
    private final ArrayList<Message> globalMessageList = new ArrayList<>();
    private final ArrayList<Message> privateMessageList = new ArrayList<>();
    private volatile String fullMSG;

    public Repository() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RETROFIT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        messageClient = retrofit.create(MessageClient.class);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Message -> Insert - Update - Delete
    ///////////////////////////////////////////////////////////////////////////

    public void insertMessage(Message message) {
        Call<Message> request = messageClient.insertMessage(message);

        request.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                System.out.println("Agregado con éxito");
                getAllMessages();
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                System.out.println("Insertado: " + t.getMessage() + request);
                noInternet(t);
            }
        });
    }

    public void updateCallF(long id, Message message) {
        Call<Boolean> request = messageClient.updateMessage(id, message);

        request.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                System.out.println("Modificado con éxito");
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                System.out.println(call.getClass() + "\n" + t.getMessage());
                noInternet(t);
            }
        });
    }

    public void deleteMessage(long id) {
        Call<Message> request = messageClient.deleteMessageById(id);

        request.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                System.out.println("Eliminado con éxito");
                getAllMessages();
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                System.out.println(call.getClass() + "\n" + t.getMessage());
                noInternet(t);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // List Operations
    ///////////////////////////////////////////////////////////////////////////

    public void getAllMessages() {
        Call<List<Message>> request = messageClient.getMessages();

        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.body() != null) {
                    try {
                        globalMessageList.clear();
                        globalMessageList.addAll(response.body());
                        File file = new File("BackUp.txt");
                        FileWriter fileReader = new FileWriter(file);
                        BufferedWriter writer = new BufferedWriter(fileReader);
                        writer.write(getGlobalMessageList().toString());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                noInternet(t);
            }
        });
    }

    public void deleteAll() {
        Call<List<Message>> request = messageClient.deleteAll();

        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                System.out.println("¡Todas las conversaciones eliminadas!");
                getAllMessages();
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                System.out.println(call.getClass() + "\n" + t.getMessage());
                noInternet(t);
            }
        });
    }

    public void getPrivateMessage(String userSend, String userReceiver) {
        Call<List<Message>> request = messageClient.getPrivateMessage(userSend, userReceiver);

        request.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.body() != null) {
                    System.out.println("¡Mensajes privados obtenidos!");
                    privateMessageList.addAll(response.body());
                }
                getPrivateMessage(userSend, userReceiver);
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                System.out.println(call.getClass() + "\n" + t.getMessage());
                noInternet(t);
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // Extra Operations
    ///////////////////////////////////////////////////////////////////////////

    private void noInternet(@NotNull Throwable t) {
        if (Objects.requireNonNull(t.getMessage()).contains("Unable to resolve host")) {
            System.out.println("¡No hay conexión a interner!");
        }
    }

    public StringBuilder getGlobalMessageList() {
        StringBuilder messages = new StringBuilder();
        globalMessageList.forEach(message1 -> messages.append(message1.getUserSend()).append(": ").append(message1.getUserMessage()).append("\n"));
        return messages;
    }

    public String readMessages() {
        AdminThread.threadExecutorPool.execute(() -> {
            try (BufferedReader br = new BufferedReader(new FileReader("BackUp.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                fullMSG = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return fullMSG;
    }
}
