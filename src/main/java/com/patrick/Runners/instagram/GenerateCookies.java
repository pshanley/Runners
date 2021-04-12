package com.patrick.Runners.instagram;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patrick.Runners.docker.DockerUtils;

public class GenerateCookies {


  private static final Logger LOG = LoggerFactory.getLogger(GenerateCookies.class.getName());

  public static void RunCypress() throws IOException, InterruptedException {
    DockerUtils.runCypressContainer();


  }

  public static void main(String[] args) throws IOException, InterruptedException {
    RunCypress();
  }
}
