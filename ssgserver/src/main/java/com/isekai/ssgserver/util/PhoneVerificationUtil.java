package com.isekai.ssgserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PhoneVerificationUtil {

	@Value("${coolsms.api.key}")
	private String APIKEY;

	@Value("${coolsms.api.secret}")
	private String SECRET_KEY;

	@Value("${coolsms.api.number}")
	private String PHONE_NUM;

	DefaultMessageService messageService;

	@PostConstruct
	public void init() {
		this.messageService = NurigoApp.INSTANCE.initialize(APIKEY, SECRET_KEY, "https://api.coolsms.co.kr");
	}

	public SingleMessageSentResponse sendSms(String to, String verificationNumber) {
		log.info(PHONE_NUM);
		Message message = new Message();
		log.info(PHONE_NUM);
		message.setFrom(PHONE_NUM);
		log.info(PHONE_NUM);
		message.setTo(to);
		message.setText("[isekai-SSG] 인증번호[" + verificationNumber + "] 를 입력해주세요.");

		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println(response);

		return response;
	}
}
