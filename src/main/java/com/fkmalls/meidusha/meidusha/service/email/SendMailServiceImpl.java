package com.fkmalls.meidusha.meidusha.service.email;

import com.fkmalls.meidusha.meidusha.entity.email.MailRequest;
import com.fkmalls.meidusha.meidusha.entity.email.SendMailService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * dengjinming
 * 2022/9/6
 */
@Service
@Slf4j
public class SendMailServiceImpl implements SendMailService {

    //注入邮件工具类
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    private static final Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);

    public void checkMail(MailRequest mailRequest) {
        Assert.notNull(mailRequest,"邮件请求不能为空");
        Assert.notNull(mailRequest.getSendTo(), "邮件收件人不能为空");
        Assert.notNull(mailRequest.getSubject(), "邮件主题不能为空");
        Assert.notNull(mailRequest.getText(), "邮件收件人不能为空");
    }

    @Override
    public boolean sendSimpleMail(MailRequest mailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        checkMail(mailRequest);
        //邮件发件人
        message.setFrom(sendMailer);
        //邮件收件人 1或多个
        message.setTo(mailRequest.getSendTo().split(","));
        //邮件主题
        message.setSubject(mailRequest.getSubject());
        //邮件内容
        message.setText(mailRequest.getText());
        //邮件发送时间
        message.setSentDate(new Date());
        javaMailSender.send(message);
        logger.info("发送测试报告邮件成功:{}->{}\n邮件内容:{}",sendMailer,mailRequest.getSendTo(),mailRequest.getText());
        return true;
    }



    @Override
    public boolean sendHtmlMail(MailRequest mailRequest) {
        MimeMessage message = javaMailSender.createMimeMessage();
        checkMail(mailRequest);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            //邮件发件人
            helper.setFrom(sendMailer);
            //邮件收件人 1或多个
            helper.setTo(mailRequest.getSendTo().split(","));
            //邮件主题
            helper.setSubject(mailRequest.getSubject());
            //邮件内容
            helper.setText(mailRequest.getText(),true);
            //邮件发送时间
            helper.setSentDate(new Date());

            String filePath = mailRequest.getFilePath();
            if (StringUtils.hasText(filePath)) {
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName,file);
            }
            javaMailSender.send(message);
            logger.info("发送测试报告邮件成功:{}->{}\n邮件内容:{}",sendMailer,mailRequest.getSendTo(),mailRequest.getText());
        } catch (MessagingException e) {
            logger.error("发送邮件时发生异常！",e);
        }
        return true;
    }
}


