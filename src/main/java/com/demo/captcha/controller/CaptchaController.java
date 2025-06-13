package com.demo.captcha.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.captcha.service.CaptchaService;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
	
	@Autowired
	public CaptchaService captchaService;
	
	@GetMapping("/generate")
	public ResponseEntity<Map<String,String>>generateCaptcha()
	{
		return ResponseEntity.ok(captchaService.generateCaptcha());
		
	}
	
	@PostMapping("/validate")
	public ResponseEntity<Boolean>validateCaptcha(@RequestBody Map<String,String>payload)
	{
		 String captchaId = payload.get("captchaId");
	        String answer = payload.get("answer");
	        return ResponseEntity.ok(captchaService.validateCaptcha(captchaId, answer));
		
	}
	

}
