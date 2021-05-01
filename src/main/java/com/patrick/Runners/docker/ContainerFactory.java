package com.patrick.Runners.docker;

import java.util.concurrent.TimeUnit;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Bind;

@Component
public class ContainerFactory {

  public CreateContainerResponse getContainer(DockerClient dockerClient, String profile) throws InterruptedException {

    String hostCookiePath;

    String devCookiePath = "/Users/patrick.hanley/repo/personal/Runners/cypress/cookies"; // hard coded for my computer. "docker run -v" requires absolute path for the host volume, not sure about the api
    String prodCookiePath = "/usr/local/runners/cookies"; // this isn't being mounted on my local computer, docker won't mount to the host outside of /Users... Works fine on the ec2 instance
    String cypressImageName= "pshanley323/instagram-cypress";

    if(profile.equals("dev")){
       hostCookiePath = devCookiePath;
    }else{
       hostCookiePath = prodCookiePath;
    }

    CreateContainerResponse container = dockerClient.createContainerCmd(cypressImageName) // creates (but doesn't start) container
        .withName("cypress_container") // name of container
        .withBinds(Bind.parse(hostCookiePath+ ":/app/cypress/cookies")) // deprecated, but this is what I want
        .exec();

    return container;
  }

}
