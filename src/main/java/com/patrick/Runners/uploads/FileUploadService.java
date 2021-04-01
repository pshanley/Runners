package com.patrick.Runners.uploads;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ServletContext servletContext;


  public void TeamFileUpload(MultipartFile file, String team) throws IOException {
    String absolutePath = servletContext.getRealPath("/uploads/");
    file.transferTo(new File(absolutePath + team));

  }
}
