package com.patrick.Runners.instagram;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateCookies {

  private static final Logger LOG = LoggerFactory.getLogger(GenerateCookies.class.getName());


  public static void RunCypress() throws IOException, InterruptedException {

    LOG.info("Starting Cypress");

    ProcessBuilder pb = new ProcessBuilder("cypress","run", "--spec" ,"cypress/integration/instagram.js");
    pb.directory(new File("/Users/patrick.hanley/Documents/tutorials/cypress-202"));

    Process p = pb.start();

    TimeUnit.SECONDS.sleep(20);

    LOG.info("Cypress is completed");

  }
}
