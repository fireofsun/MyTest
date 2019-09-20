package com.guandata.ftpdownloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * FTPClient工具类
 * @author happyqing
 * @since 2016.7.20
 */
public class FTPUtil {

    private static Logger log = Logger.getLogger(FTPUtil.class);
    private FTPClient ftp;

    private String username;
    private String host;
    private String password;
    private int port;
    private String privateKey;


    /**
     * 构造基于密码认证的ftp对象
     */
    public FTPUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
    }

    /**
     * 自定义 encoding 的构造函数
     */
    public FTPUtil(String username, String password, String host, int port, String controlEncoding) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        ftp = new FTPClient();
        ftp.setControlEncoding(controlEncoding);
    }

    public void setTimeOut(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond){
        try {
            ftp.setDefaultTimeout(defaultTimeoutSecond * 1000);
            //ftp.setConnectTimeout(connectTimeoutSecond * 1000); //commons-net-3.5.jar
            ftp.setSoTimeout(connectTimeoutSecond * 1000); //commons-net-1.4.1.jar 连接后才能设置
            ftp.setDataTimeout(dataTimeoutSecond * 1000);
        } catch (SocketException e) {
            log.error("setTimeout Exception:", e);
        }
    }

    /**
     * 连接ftp服务器
     */
    public FTPClient connect() throws IOException {
        // Connect to server.
        try {
            ftp.connect(host, port);
        } catch (UnknownHostException ex) {
            throw new IOException("Can't find FTP server '" + host + "'");
        }

        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            disconnect();
            throw new IOException("Can't connect to server '" + host + "'");
        }

        if ("".equals(username)) {
            username = "anonymous";
        }

        // Login.
        if (!ftp.login(username, password)) {
            disconnect();
            throw new IOException("Can't login to server '" + host + "'");
        }

        // Set data transfer mode.
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        //ftp.setFileType(FTP.ASCII_FILE_TYPE);

        // Use passive mode to pass firewalls.
        ftp.enterLocalPassiveMode();

        return ftp;
    }

    /**
     * Disconnect from the FTP server
     *
     * @throws IOException
     *             on I/O errors
     */
    public void disconnect() throws IOException {

        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException ex) {
            }
        }
    }


    /**
     * Delete the file from the FTP server.
     *
     * @param ftpFileName
     *            server file name (with absolute path)
     * @throws IOException
     *             on I/O errors
     */
    public void deleteFile(String ftpFileName) throws IOException {
        if (!ftp.deleteFile(ftpFileName)) {
            throw new IOException("Can't remove file '" + ftpFileName + "' from FTP server.");
        }
    }

    /**
     * Upload the file to the FTP server.
     *
     * @param ftpFileName
     *            server file name (with absolute path)
     * @param localFileName
     *            local file to upload
     * @throws IOException
     *             on I/O errors
     */
    public void upload(String ftpFileName, String localFileName) throws IOException {
        // File check
        File localFile = new File(localFileName);
        if (!localFile.exists()) {
            throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
        }

        // Upload.
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(localFile));
            if (!ftp.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }

        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Download the file from the FTP server.
     *
     * @param downloadFile ftp服务器上文件（带绝对路径）
     * @param saveFile 本地文件
     * @throws IOException on I/O errors
     */
    public void download(String downloadFile, String saveFile) throws IOException {
        // Download.
        OutputStream out = null;
        try {
            // Get file info.
            FTPFile[] fileInfoArray = ftp.listFiles(downloadFile);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File " + downloadFile + " was not found on FTP server.");
            }

            // Check file size.
            FTPFile fileInfo = fileInfoArray[0];
            long size = fileInfo.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("File " + downloadFile + " is too large.");
            }

            // Download file.
            out = new BufferedOutputStream(new FileOutputStream(new File(saveFile)));
            if (!ftp.retrieveFile(downloadFile, out)) {
                throw new IOException("Error loading file " + downloadFile + " from FTP server. Check FTP permissions and path.");
            }

            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FTPUtil ftpUtil = new FTPUtil("testuser","fireofsun","172.16.54.189",21,"UTF-8");
        ftpUtil.connect();
        //ftpUtil.upload("/home/testuser/文件1.txt", "E:/image/FTPClient/FTPClient测试/文件1.txt");
        ftpUtil.download("/home/testuser/aab", "./ttssbb");
        ftpUtil.disconnect();
    }

}