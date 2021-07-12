package io.zsy.mail.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/17
 */
@SpringBootTest
public class MailServiceTest {

	@Resource
	private MailService mailService;

	@Resource
	private TemplateEngine templateEngine;

	@Test
	public void testSendSimpleMail() {
		mailService.sentSimpleMail("springbootmail@chacuo.net", "Test temp mail", "Are you OK?");
	}

	@Test
	public void testSentHtmlMail() throws MessagingException {
		Context context = new Context();
		context.setVariable("username", "我是张三");

		String content = templateEngine.process("/mail/index", context);
		System.out.println(content);

		mailService.sendHtmlMail("springbootmail@chacuo.net", "HTML Email", content);
	}
}
