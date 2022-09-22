package dev.simplesolution.sftp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestSftpFileTransfer implements CommandLineRunner {

    @Autowired
    private FileTransferService fileTransferService;

    private Logger logger = LoggerFactory.getLogger(TestSftpFileTransfer.class);

    @Override
    public void run(String... args) throws Exception {

        logger.info("Start upload file");
        boolean isUploaded = fileTransferService.uploadFile("C:\\Users\\guilh\\Downloads\\teste.txt", "/uec.mrooms.net/conduit/teste.txt");
        logger.info("Upload result: " + String.valueOf(isUploaded));
    }
}
