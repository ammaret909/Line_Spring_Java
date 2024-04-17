package com.example.rmxline.Controller;

import com.example.rmxline.MainFunction.MainFunction;
import com.example.rmxline.Model.UserLineModel;
import com.example.rmxline.Service.AdminService;
import com.example.rmxline.Service.TextCarService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.quickreply.QuickReply;
import com.linecorp.bot.model.message.quickreply.QuickReplyItem;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;



@LineMessageHandler
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainTextController {
    @Autowired
    TextCarService textCarService;
    @Autowired
    MainFunction mainFunction;
    @Autowired
    AdminService adminService;

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws ExecutionException, InterruptedException {
        final String originalMessageText = event.getMessage().getText();

        switch (originalMessageText) {
            case "car": {
                textCarService.getCar(event);
                break;
            }
            default:
                adminService.AdminNoti(event);
                break;
        }
        return null;
    }

    @PostMapping("/line/massage/read/{userLineId}")
    public void readMassage(@PathVariable("userLineId") String userLineId){
        adminService.readMassage(userLineId);
    }

    @GetMapping("/line/massage/new")
    public List<UserLineModel> getAllUserNew(){
        return adminService.getAllUserNew();
    }
    @PostMapping("/line/massage/new/{userLineId}")
    public UserLineModel newMassageToProcess(@PathVariable("userLineId") String userLineId){
        return adminService.newMassageToProcess(userLineId);
    }
    @GetMapping("/line/massage/process")
    public List<UserLineModel> getAllUserProcess(){
        return adminService.getAllUserProcess();
    }
    @PostMapping("/line/massage/process/{userLineId}")
    public UserLineModel processToFinish(@PathVariable("userLineId") String userLineId){
        return adminService.processToFinish(userLineId);
    }
    @GetMapping("/line/massage/finish")
    public List<UserLineModel> getAllUserFinish(){
        return adminService.getAllUserFinish();
    }

}
