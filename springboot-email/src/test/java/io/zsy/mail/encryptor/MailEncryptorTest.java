package io.zsy.mail.encryptor;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author shuaiyin.zhang
 * @description
 * @date 2020/08/17
 */
@SpringBootTest
public class MailEncryptorTest {

	@Resource
	private StringEncryptor encryptor;

	@Test
	public void context() {
		String password = "12346";
		String encryptPwd = encryptor.encrypt(password);
		System.out.println("加密: " + encryptPwd);
		System.out.println("解密: " + encryptor.decrypt(encryptPwd));
	}
}
