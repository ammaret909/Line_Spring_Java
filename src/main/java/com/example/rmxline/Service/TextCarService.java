package com.example.rmxline.Service;

import com.example.rmxline.MainFunction.MainFunction;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.FlexContainer;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
public class TextCarService {
    @Autowired
    LineMessagingClient lineMessagingClient;
    @Autowired
    MainFunction mainFunction;

    public void getCar(MessageEvent<TextMessageContent> event) {
        List<QuickReplyItem> quickReplyItems = Arrays.asList(
                QuickReplyItem.builder().action(new MessageAction("1 TOYOTA", "1 TOYOTA")).build(),
                QuickReplyItem.builder().action(new MessageAction("2 BMW", "2 BMW")).build(),
                QuickReplyItem.builder().action(new MessageAction("3 MG", "3 MG")).build()
        );

        QuickReply quickReply = QuickReply.items(quickReplyItems);

        TextMessage replyMessage = TextMessage.builder()
                .text("""
             1 TOYOTA
             2 BMW
             3 MG""")
                .quickReply(quickReply)
                .build();

        ReplyMessage reply = new ReplyMessage(event.getReplyToken(), replyMessage);
        lineMessagingClient.replyMessage(reply);

//        String response = ("""
//             1 TOYOTA
//             2 BMW
//             3 MG""");
//        mainFunction.replyText(event.getReplyToken(), response);


    }

    public void getCarFlex(MessageEvent<TextMessageContent> event) {

    }
}
