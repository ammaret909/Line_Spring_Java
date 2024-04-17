package com.example.rmxline.MainFunction;

import com.example.rmxline.DTOModel.DecodeRCCDTO;
import com.example.rmxline.Model.RCCModel;
import com.example.rmxline.Repository.RCCRepository;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MainFunction {
    @Autowired
    LineMessagingClient lineMessagingClient;
    @Autowired
    RCCRepository rccRepository;

    public void replyText(@NonNull String replyToken, @NonNull String message) {
        if(replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken is not empty");
        }

        if(message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "...";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String createRcc() {

        RCCModel rccModel = new RCCModel();
        LocalDateTime timeDate = LocalDateTime.now();

        String year = Integer.toHexString(timeDate.getYear()).toUpperCase();
        String month = Integer.toHexString(timeDate.getMonthValue()).toUpperCase();
        String day = encodeDay(timeDate.getDayOfMonth());
        String hour = encodeHour(timeDate.getHour());
        String minute = checkMinute(timeDate.getMinute());
        String second = checkSecond(timeDate.getSecond());
        String preRcc = year + month + day + hour + minute + second;

        RCCModel rccModelDefault = new RCCModel();
        rccModelDefault.setId(1L);
        rccModelDefault.setCheckRcc("100");

        RCCModel rccModelCheck = rccRepository.findById(1L).orElse(rccModelDefault);
        String lastTwoCharacters = rccModelCheck.getCheckRcc().substring(0,rccModelCheck.getCheckRcc().length() - 2);
        if(rccModelCheck == null || !preRcc.equals(lastTwoCharacters)) {
            rccModel.setId(1L);
            String special = "AA";
            String finalRcc = preRcc + special;
            rccModel.setCheckRcc(finalRcc);
            rccRepository.save(rccModel);
            return finalRcc;
        }
        rccModel.setId(1L);
        String special = CreateSpecial(rccModelCheck.getCheckRcc());
        String finalRcc = preRcc + special;
        rccModel.setCheckRcc(finalRcc);
        rccRepository.save(rccModel);
        return finalRcc;
    }

    private String encodeDay(int Day){
        int checkDay = Day - 9;
        if(checkDay >= 1){
            StringBuilder result = new StringBuilder();
            while (checkDay > 0) {
                int remainder = (checkDay - 1) % 26;
                char letter = (char) ('A' + remainder);
                result.insert(0, letter);
                checkDay = (checkDay - 1) / 26;
            }
            return String.valueOf(result);
        }
        return String.valueOf(Day);
    }

    private String encodeHour(int hour){
        StringBuilder result = new StringBuilder();
        while (hour > 0) {
            int remainder = (hour - 1) % 26;
            char letter = (char) ('A' + remainder);
            result.insert(0, letter);
            hour = (hour - 1) / 26;
        }
        return String.valueOf(result);
    }

    private String checkMinute(int minute){
        if(minute <= 9){
            String finalMinute = "0" + minute;
            return finalMinute;
        }
        return String.valueOf(minute);
    }

    private String checkSecond(int second){
        if(second <= 9){
            String finalSecond = "0" + second;
            return finalSecond;
        }
        return String.valueOf(second);
    }

    private String CreateSpecial(String checkRcc){

        String specail = checkRcc.substring(10);

        int resultSpecail = 0;
        int base = 1;
        for (int i = specail.length() - 1; i >= 0; i--) {
            char c = specail.charAt(i);
            resultSpecail += (c - 'A' + 1) * base;
            base *= 26;
        }

        StringBuilder result = new StringBuilder();
        int newResultSpecail = resultSpecail + 1;
        while (newResultSpecail > 0) {
            int remainder = (newResultSpecail - 1) % 26;
            char letter = (char) ('A' + remainder);
            result.insert(0, letter);
            newResultSpecail = (newResultSpecail - 1) / 26;
        }
        return result.toString();

    }

    public String decodeRCC(DecodeRCCDTO decodeRCCDTO) {
        String rcc = decodeRCCDTO.getRCC();
        String year = String.valueOf(Integer.parseInt(rcc.substring(0,3),16));
        String month = String.valueOf(decodeMonth(Integer.parseInt(rcc.substring(3,4),16)));
        String day = String.valueOf(decodeDay(rcc.substring(4,5)));
        String hour = String.valueOf(decodeHour(rcc.substring(5,6)));
        String minute = rcc.substring(6,8);
        String second = rcc.substring(8,10);
        String time = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second;
//        TimeRCCDTOout timeRCCDTOout = new TimeRCCDTOout();
//        timeRCCDTOout.setTime(time);
        return time;
    }

    public String decodeRCCGetString(String rcc) {
        String year = String.valueOf(Integer.parseInt(rcc.substring(0,3),16));
        String month = String.valueOf(decodeMonth(Integer.parseInt(rcc.substring(3,4),16)));
        String day = String.valueOf(decodeDay(rcc.substring(4,5)));
        String hour = String.valueOf(decodeHour(rcc.substring(5,6)));
        String minute = rcc.substring(6,8);
        String second = rcc.substring(8,10);
        String time = day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second;
        return time;
    }

    private String decodeMonth(int month){
        if(month <= 9){
            return "0" + month;
        }
        return String.valueOf(month);
    }

    private String decodeDay(String day) {
        if (Character.isLetter(day.charAt(0))) {
            char convertToChar = day.charAt(0) ;
            int number = convertToChar - 'A' + 1;
            int finalNumber = number + 9;
            return String.valueOf(finalNumber);
        } else {
            return "0" + day;
        }
    }

    private String decodeHour(String hour) {
        char convertToChar = hour.charAt(0) ;
        int hourTime = convertToChar - 'A' + 1;
        if(hourTime <= 9) {
            return "0" + hourTime;
        }
        return String.valueOf(hourTime);
    }
}
