package model.rest.pojo;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private long id;
    private String userMessage, userSend, userReceiver, messageDate;

    public Message(String userMessage, String userSend, String userReceiver, String messageDate) {
        this.userMessage = userMessage;
        this.userSend = userSend;
        this.userReceiver = userReceiver;
        this.messageDate = messageDate;
    }
}
