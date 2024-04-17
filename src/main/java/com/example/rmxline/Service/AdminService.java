package com.example.rmxline.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.example.rmxline.MainFunction.MainFunction;
import com.example.rmxline.Model.RCCModel;
import com.example.rmxline.Model.UserLineModel;
import com.example.rmxline.Repository.UserLineRepository;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.profile.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AdminService {
    @Autowired
    MainFunction mainFunction;
    @Autowired
    UserLineRepository userLineRepository;
    @Autowired
    LineMessagingClient lineMessagingClient;

    public void AdminNoti(MessageEvent<TextMessageContent> event) throws ExecutionException, InterruptedException {
        String userId = event.getSource().getUserId();
        UserLineModel userLineModel = userLineRepository.findByUserToken(userId);
        UserProfileResponse userProfileResponse = lineMessagingClient.getProfile(userId).get();
        String displayName = userProfileResponse.getDisplayName();

        if(userLineModel == null){
            UserLineModel userLineModelNew = new UserLineModel();
            userLineModelNew.setRcc(mainFunction.createRcc());
            userLineModelNew.setUserToken(userId);
            userLineModelNew.setRccTime(mainFunction.createRcc());
            userLineModelNew.setUsername(displayName);
            userLineModelNew.setReadMassage(false);
            userLineModelNew.setStatus(false);
            userLineRepository.save(userLineModelNew);
        }
        else if (userLineModel.isReadMassage() == true && userLineModel.isStatus() == false){

        }
        else {
            userLineModel.setStatus(false);
            userLineModel.setReadMassage(false);
            userLineModel.setRccTime(mainFunction.createRcc());
            userLineModel.setUsername(displayName);
            userLineRepository.save(userLineModel);
        }
    }

    public void readMassage(String userLineId) {
        UserLineModel userLineModel = userLineRepository.findByUserToken(userLineId);
        userLineModel.setStatus(true);
        userLineModel.setReadMassage(true);
        userLineRepository.save(userLineModel);
    }

    public List<UserLineModel> getAllUserNew() {
        List<UserLineModel> userLineModels = userLineRepository.findUserNew();
        return userLineModels;
    }
    public UserLineModel newMassageToProcess(String userLineId) {
        UserLineModel userLineModel = userLineRepository.findByUserToken(userLineId);
        userLineModel.setReadMassage(true);
        return userLineRepository.save(userLineModel);
    }

    public List<UserLineModel> getAllUserProcess() {
        List<UserLineModel> userLineModels = userLineRepository.findUserProcess();
        return userLineModels;
    }

    public List<UserLineModel> getAllUserFinish() {
        List<UserLineModel> userLineModels = userLineRepository.findUserFinish();
        return userLineModels;
    }

    public UserLineModel processToFinish(String userLineId) {
        UserLineModel userLineModel = userLineRepository.findByUserToken(userLineId);
        userLineModel.setStatus(true);
        return userLineRepository.save(userLineModel);
    }
}
