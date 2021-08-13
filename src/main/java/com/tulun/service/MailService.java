package com.tulun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender; //javax.mail.internet.MimeMessage;里提供的

    //发送方配置 （即Application里的信息）
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            //发送邮件是否是富文本（即带有附件、图片、html）
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);  //发送方
            messageHelper.setTo("18729366236@163.com"); //接收方
            messageHelper.setSubject("测试邮件"); //发送主题
            messageHelper.setText("测试内容发送");            //邮箱主体

            //发送操作
            javaMailSender.send(mimeMessage);
            System.out.println("发送邮件成功");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败");
        }
    }
}
