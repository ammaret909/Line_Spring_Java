package com.example.rmxline.Config;

import com.linecorp.bot.client.LineMessagingClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineConfig {
    @Value("${line.bot.channel-token}")
    private String channelToken;

    @Bean
    public LineMessagingClient lineMessagingClient() {
        return LineMessagingClient.builder(channelToken).build();
    }
}
