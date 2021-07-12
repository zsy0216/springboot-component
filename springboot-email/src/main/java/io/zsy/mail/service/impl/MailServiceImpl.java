package io.zsy.mail.service.impl;

import io.zsy.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/17
 */
@Component
public class MailServiceImpl implements MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	@Value("${spring.mail.username}")
	private String from;

	@Resource
	private JavaMailSender mailSender;

	@Override
	public void sentSimpleMail(String to, String subject, String content) {
		logger.info("开始给 {} 发送邮件...", to);
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(content);
			mailSender.send(message);
			logger.info("发送邮件成功...发件人: {}", from);
		} catch (MailException e) {
			logger.error("发送邮件失败: {}", e.getMessage());
		}
	}

	@Override
	public void sendHtmlMail(String to, String subject, String content) {
		logger.info("开始给 {} 发送邮件...", to);
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			mailSender.send(message);
			logger.info("发送邮件成功...发件人: {}", from);
		} catch (MessagingException e) {
			logger.error("发送邮件失败: {}", e.getMessage());
		}
	}
}
