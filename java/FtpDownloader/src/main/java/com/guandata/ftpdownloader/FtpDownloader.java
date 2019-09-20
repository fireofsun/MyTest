package com.guandata.ftpdownloader;

import com.jcraft.jsch.SftpException;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.Properties;

public class FtpDownloader {
    public static void main(String[] args){
        Logger log = LoggerFactory.getLogger(FtpDownloader.class);
        FtpDownloader fd = new FtpDownloader();

        if(args.length == 0){
            fd.runSftpDownloader(new LocalDateTime());
        } else if(args.length == 1 && (args[0].equals("-h")|| args[0].equals("--help"))){
            String  helpContent =
                    "FtpDownloader 使用方法：\r\n"+
                            "-h --help : 显示此帮助内容 \r\n"+
                            "无任何参数: 抽取时间戳为昨天的文件 \r\n"+
                            "一个参数, 且格式为 yyyyMMdd 的字符串: 抽取时间戳为指定时间直到昨天的文件, 包含指定日期, 包含昨天\r\n"+
                            "两个参数, 格式均为 yyyyMMdd : 前者为start 后者为end, 抽取该指定时间段内的文件, 包含start和end";
            log.info(helpContent);
        } else if(args.length == 1){
            LocalDateTime startTime = LocalDateTime.parse(args[0]);
            LocalDateTime today = new LocalDateTime();
            while(!startTime.isAfter(today)){
                fd.runSftpDownloader(startTime);
                startTime = startTime.plusDays(1);
            }
        } else if(args.length == 2){
            LocalDateTime startTime = LocalDateTime.parse(args[0]);
            LocalDateTime endTime = LocalDateTime.parse(args[1]);
            while(!startTime.isAfter(endTime)){
                fd.runSftpDownloader(startTime);
                startTime = startTime.plusDays(1);
            }
        }

        /*FtpDownloader fd = new FtpDownloader();
        //fd.runSftpDownloader();
        fd.runSftpDownloader();*/

    }

    public void runFtpDownloader() {

        InputStream inStream = null;
        try {
            inStream = new FileInputStream(new File("./userConfig.properties"));
            Properties prop = new Properties();
            prop.load(inStream);
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            String fromDir = prop.getProperty("fromDir");
            String toDir = prop.getProperty("toDir");
            String filenamesStr = prop.getProperty("filenames");
            String[] filenames = filenamesStr.split(",");

            FTPUtil ftpUtil = new FTPUtil(username,password,host,Integer.parseInt(port),"UTF-8");
            ftpUtil.connect();
            ftpUtil.download("/home/testuser/aab", "./ttssbb");
            ftpUtil.disconnect();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void runSftpDownloader(LocalDateTime time){
        try {
            InputStream inStream = new FileInputStream(new File("./userConfig.properties"));
            Properties prop = new Properties();
            prop.load(inStream);
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            String host = prop.getProperty("host");
            String port = prop.getProperty("port");
            String fromDir = prop.getProperty("fromDir");
            String toDir = prop.getProperty("toDir");
            String filenamesStr = prop.getProperty("filenames");
            String[] filenames = filenamesStr.split(",");

            SFTPUtil sftpUtil = new SFTPUtil(username, password, host, Integer.parseInt(port));
            Logger log = LoggerFactory.getLogger(sftpUtil.getClass());

            sftpUtil.login();
            for(String filename : filenames){
                String trimFilename = filename.trim();

                String shortName = trimFilename.substring(trimFilename.lastIndexOf("/")+1);
                // shortName为路径最后的部分，也就是这个文件名公共的部分，作为外边的文件夹名称，因此补上toDir路径后在补上后缀csv才是完整的路径
                String pathToSaveFile = toDir + shortName+"/"+shortName + "_" + time.toString("yyyyMMdd") + ".xlsx";
                // 这里的trimFilename是包含了路径在内的filename，但除去了文件名中的timestamp和后缀的csv，只有公共字符串部分，所以在这里补上
                // 该客户时间戳为yyyyMMdd的格式，所以需要转化
                String timedFilename = trimFilename +"_"+ time.toString("yyyyMMdd")+".xlsx";
                //timedFilename = trimFilename;
                log.info("[ftpDownloader "+ new Date() +"] fetching file " +timedFilename + " and saving to " + pathToSaveFile);
                try {
                    sftpUtil.downloadSingleFile(timedFilename, pathToSaveFile);
                } catch (SftpException e) {
                    e.printStackTrace();
                }
            }
            sftpUtil.logout();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDateString(){
        LocalDateTime startTime = new LocalDateTime().minusDays(1);
        return startTime.toString("yyyyMMdd");
    }
}
