package io.zsy.mail.service;

import org.springframework.mail.MailException;

import javax.mail.MessagingException;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/17
 */
public interface MailService {

	/**
	 * 发送简单邮件
	 *
	 * @param to      收件人
	 * @param subject 标题
	 * @param content 内容
	 * @throws MailException e
	 */
	void sentSimpleMail(String to, String subject, String content) throws MailException;

	/**
	 * 发送 HTML 邮件
	 *
	 * @param to      收件人
	 * @param subject 标题
	 * @param content 内容
	 * @throws MessagingException e
	 */
	void sendHtmlMail(String to, String subject, String content) throws MessagingException;
}
