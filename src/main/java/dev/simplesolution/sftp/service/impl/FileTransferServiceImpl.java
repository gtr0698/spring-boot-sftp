package dev.simplesolution.sftp.service.impl;

import com.jcraft.jsch.*;
import dev.simplesolution.sftp.service.FileTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class FileTransferServiceImpl implements FileTransferService {

    private Logger logger = LoggerFactory.getLogger(FileTransferServiceImpl.class);

    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Override
    public boolean uploadFile(String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp();
        try {
            channelSftp.put(localFilePath, remoteFilePath);
            return true;
        } catch(SftpException ex) {
            logger.error("Error upload file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }

        return false;
    }

    @Override
    public boolean downloadFile(String localFilePath, String remoteFilePath) {
        ChannelSftp channelSftp = createChannelSftp();
        OutputStream outputStream;
        try {
            File file = new File(localFilePath);
            outputStream = new FileOutputStream(file);
            channelSftp.get(remoteFilePath, outputStream);
            file.createNewFile();
            return true;
        } catch(SftpException | IOException ex) {
            logger.error("Error download file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }

        return false;
    }

    private ChannelSftp createChannelSftp() {
        try {
            JSch jSch = new JSch();
            Session session = jSch.getSession(username, host);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            Channel channel = session.openChannel("sftp");
            return (ChannelSftp) channel;
        } catch(JSchException ex) {
            logger.error("Create ChannelSftp error", ex);
        }

        return null;
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if( channelSftp == null)
                return;

            if(channelSftp.isConnected())
                channelSftp.disconnect();

            if(channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();

        } catch(Exception ex) {
            logger.error("SFTP disconnect error", ex);
        }
    }

}
