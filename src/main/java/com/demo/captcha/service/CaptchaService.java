package com.demo.captcha.service;

import java.util.Map;

public interface CaptchaService {
	Map<String,String>generateCaptcha();
	boolean validateCaptcha(String captchaId,String answer);
	

}
