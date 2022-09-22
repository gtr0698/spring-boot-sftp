package dev.simplesolution.sftp;

import dev.simplesolution.sftp.service.TestSftpFileTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSftpApplication {
	@Autowired
	private static TestSftpFileTransfer testSftpFileTransfer;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootSftpApplication.class, args);
		testSftpFileTransfer.run(args);
	}

}
