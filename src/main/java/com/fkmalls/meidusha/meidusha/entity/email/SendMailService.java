package com.fkmalls.meidusha.meidusha.entity.email;

/**
 * dengjinming
 * 2022/9/6
 */
public interface SendMailService {

    /**
     * 简单文本邮件
     *
     * @param mailRequest
     * @return
     */
    boolean sendSimpleMail(MailRequest mailRequest);


    /**
     * Html格式邮件,可带附件
     *
     * @param mailRequest
     * @return
     */
    boolean sendHtmlMail(MailRequest mailRequest);
}

