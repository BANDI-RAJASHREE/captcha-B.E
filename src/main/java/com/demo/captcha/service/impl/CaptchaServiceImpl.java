package com.demo.captcha.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.demo.captcha.service.CaptchaService;
import com.google.code.kaptcha.Producer;

@Service
public class CaptchaServiceImpl implements CaptchaService{
	private final Map<String,String>captchaStore=new ConcurrentHashMap<>();
	private final Producer kaptchaProducer;
	
	public CaptchaServiceImpl(Producer kaptchaProducer)
	{
		this.kaptchaProducer=kaptchaProducer;
	}
	
	@Override
	public Map<String,String>generateCaptcha()
	{
		String text=kaptchaProducer.createText();
		BufferedImage image=kaptchaProducer.createImage(text);
		
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		
		try {
			ImageIO.write(image, "png", outputStream);
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error creating captcha image",e);
		}
		
		String base64Image=Base64.getEncoder().encodeToString(outputStream.toByteArray());
		String captchaId=UUID.randomUUID().toString();
		captchaStore.put(captchaId, text);
		
		return Map.of("captchaId",captchaId,
				"image","data:/image/png;base64,"+base64Image
				);
	}
	
	@Override
    public boolean validateCaptcha(String captchaId, String answer) {
        String correctAnswer = captchaStore.get(captchaId);
        boolean isValid = correctAnswer != null && correctAnswer.equalsIgnoreCase(answer);
        if (isValid) {
            captchaStore.remove(captchaId);
        }
        return isValid;
    }

}
