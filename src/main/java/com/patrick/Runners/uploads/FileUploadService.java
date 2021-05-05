package com.patrick.Runners.uploads;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.patrick.Runners.SpringContextUtil;

@Service
public class FileUploadService {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ServletContext servletContext;

  private String currentProfile;

  public FileUploadService(){
    ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
    Environment env = applicationContext.getEnvironment();
     this.currentProfile = env.getActiveProfiles()[0];
  }


  public void FileUpload(MultipartFile file, String name) throws IOException {
    String prodFileBackupPath = "/usr/local/runners/uploads/";
    String absolutePath = servletContext.getRealPath("/uploads/");
    checkAndCreateDirectory(absolutePath);

    if(this.currentProfile.equals("prod")){
      file.transferTo(new File(prodFileBackupPath + name)); // this has to go before the upload before... getting a "file not found" tomcat exception
    }
    file.transferTo(new File(absolutePath + name));


  }

  public void checkAndCreateDirectory(String path){
    File directory = new File(path);
    if (!directory.exists()){
      directory.mkdir();
    }
  }
}
