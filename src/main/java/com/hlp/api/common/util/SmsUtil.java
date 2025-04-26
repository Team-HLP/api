package com.hlp.api.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;

@Component
public class SmsUtil {

    @Value("${coolsms.api.key}")
    private String apiKey;
    @Value("${coolsms.api.secret}")
    private String apiSecretKey;
    @Value("${coolsms.api.phone-number}")
    private String phoneNumber;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }
	
    public void sendOne(String to, String verificationCode) {
        Message message = new Message();
        message.setFrom(phoneNumber);
        message.setTo(to);
        message.setText("[HLP] 아래의 인증번호를 입력해주세요\n" + verificationCode);

        this.messageService.sendOne(new SingleMessageSendingRequest(message));
    }
}
