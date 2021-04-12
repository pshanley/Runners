package com.patrick.Runners.docker;


import java.util.List;
import java.util.concurrent.TimeUnit;


import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import com.patrick.Runners.SpringContextUtil;

@Service
public class DockerUtils {

  private static String cypressContainerName = "/cypress_container";
  private static String defaultProfile = "dev";

  public static void runCypressContainer() throws InterruptedException {

    ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
    Environment env = applicationContext.getEnvironment();
    String currentProfile = env.getActiveProfiles()[0]; // getActiveProfiles returns a list of profiles

    DockerClient dockerClient = createDockerClient();


    List<Container> containers = dockerClient.listContainersCmd()
        .withShowAll(true) // show stopped containers
        .exec();

    boolean createContainer = false;
    String containerId = null;

    for(Container c: containers){
      if(c.getNames()[0].equals(cypressContainerName)){
        createContainer = true; // cypress has not already run, container needs to be created
        containerId = c.getId();
      }
    }

    if(!createContainer){
      ContainerFactory containerFactory = new ContainerFactory();
      CreateContainerResponse container = containerFactory.getContainer(dockerClient,currentProfile); // only want to make one client object, do I pass it like this?
      containerId=container.getId();
    }

    dockerClient.startContainerCmd(containerId).exec();

    waitForContainerCompletion(dockerClient);

    System.out.println("ALL DONE CYPRESS IS DONE RUNNING");

  }

  public static DockerClient createDockerClient(){
    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
        .dockerHost(config.getDockerHost())
        .sslConfig(config.getSSLConfig())
        .maxConnections(100)
        .build();

    DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);

    return dockerClient;
  }

  public static void waitForContainerCompletion(DockerClient dockerClient) throws InterruptedException {
    int sleepTimeSeconds = 120;
    int waitTimeSeconds = 5;
    int numberOfRuns = sleepTimeSeconds/waitTimeSeconds;

    boolean stillRunning=false;
    for(int i = 0; i<numberOfRuns;i++) {
      List<Container> containers = dockerClient.listContainersCmd().exec();
      for (Container c : containers) {
        if (c.getNames()[0].equals(cypressContainerName)) {
          System.out.println("CYPRESS IS STILL RUNNING");
          stillRunning = true;
        }
      }
      if (!stillRunning){
        break;
      }
       stillRunning=false;

      TimeUnit.SECONDS.sleep(waitTimeSeconds);

    }

  }

}
