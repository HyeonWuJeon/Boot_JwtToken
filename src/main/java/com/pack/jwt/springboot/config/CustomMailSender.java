//package com.pack.jwt.springboot.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//
//@Component
//public class CustomMailSender {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
////    private JavaMailSender javaMailSender = new JavaMailSenderImpl();
////
//    private SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//
//    public void sendMail() throws MessagingException{
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        //메일 제목 설정
//        helper.setSubject("스프링 부트 메일 전송");
//        //수신자 설정
//        helper.setTo("yusa2@naver.com");
//        //템플릿에 전달할 데이터 설정
//        Context context = new Context();
//        context.setVariable("test_key", "test_value");
//        //메일 내용 설정 : 템플릿 프로세스
//        String html = templateEngine.process("mail-template",context);
//        helper.setText(html, true);
//
//        //메일 보내기
//        javaMailSender.send(message);
//    }
//
//}
//
