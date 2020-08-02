package com.oktaliem;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;


public class ManageDockerUnitTest {
    private static DockerClient dockerClient;

    @BeforeClass
    public void runDocker() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    @Test
    public void getListOfRunningDockerContainers() {
        List<Container> containers = dockerClient.listContainersCmd().exec();
        for (Container container : containers) {
            System.out.println("===============START================");
            System.out.println("All information: " + container);
            System.out.println("Id: " + container.getId());
            System.out.println("Name: " + Arrays.toString(container.getNames()));
            System.out.println("Command: " + container.getCommand());
            System.out.println("Image: " + container.getImage());
            System.out.println("Image Id: " + container.getImageId());
            System.out.println("Status: " + container.getStatus());
            System.out.println("Created: " + container.getCreated());
            System.out.println("Host Config: " + container.getHostConfig());
            System.out.println("Label: " + container.getLabels());
            System.out.println("Network Settings: " + container.getNetworkSettings());
            System.out.println("Ports: " + Arrays.toString(container.getPorts()));
            System.out.println("Size Root Fs: " + container.getSizeRootFs());
            System.out.println("Size Rw: " + container.getSizeRw());
            System.out.println("===============STOP==================");
            System.out.println("  ");
        }
    }

    @Test
    public void getListOfExitedDockerContainers() {
        List<Container> containers = dockerClient.listContainersCmd()
                                                  .withShowSize(true)
                                                  .withShowAll(true)
                                                  .withStatusFilter("exited")
                                                  .exec();
        for (Container container : containers) {
            System.out.println("===============START================");
            System.out.println("All information: " + container);
            System.out.println("Id: " + container.getId());
            System.out.println("Name: " + Arrays.toString(container.getNames()));
            System.out.println("Command: " + container.getCommand());
            System.out.println("Image: " + container.getImage());
            System.out.println("Image Id: " + container.getImageId());
            System.out.println("Status: " + container.getStatus());
            System.out.println("Created: " + container.getCreated());
            System.out.println("Host Config: " + container.getHostConfig());
            System.out.println("Label: " + container.getLabels());
            System.out.println("Network Settings: " + container.getNetworkSettings());
            System.out.println("Ports: " + Arrays.toString(container.getPorts()));
            System.out.println("Size Root Fs: " + container.getSizeRootFs());
            System.out.println("Size Rw: " + container.getSizeRw());
            System.out.println("===============STOP==================");
            System.out.println("  ");
        }
    }

    @Test
    public void checkListOfDockerImages() {
        List<Image> images = dockerClient.listImagesCmd().exec();
        for (Image image : images) {
            System.out.println("===============START================");
            System.out.println("All information: " + image);
            System.out.println("Id: " + image.getId());
            System.out.println("Parent Id: " + image.getParentId());
            System.out.println("Created: " + image.getCreated());
            System.out.println("Repo Tags: " + Arrays.toString(image.getRepoTags()));
            System.out.println("Size: " + image.getSize());
            System.out.println("Virtual Size: " + image.getVirtualSize());
            System.out.println("===============STOP==================");
            System.out.println("  ");
        }
    }

    @Test
    public void inspectContainer() {
        String containerId = "c3ee94af4113e97a5c00344b6157c3d8a12cf53d9133da3452206fbd00a1f32d";
        InspectContainerResponse inspect = dockerClient.inspectContainerCmd(containerId).exec();
        System.out.println(Arrays.toString(inspect.getArgs()));
        System.out.println(inspect.getMounts());
        System.out.println(Arrays.toString(inspect.getVolumes()));
        System.out.println(inspect.getExecIds());
        System.out.println(inspect.getMountLabel());
        System.out.println(inspect.getNetworkSettings());
        System.out.println(inspect.getProcessLabel());
        System.out.println(inspect.getSizeRootFs());
        System.out.println(inspect.getConfig());
        System.out.println(inspect.getCreated());
        System.out.println(inspect.getDriver());
        System.out.println(inspect.getExecDriver());
//        System.out.println(inspect.getGraphDriver());
        System.out.println(inspect.getHostConfig());
        System.out.println(inspect.getHostnamePath());
        System.out.println(inspect.getHostsPath());
        System.out.println(inspect.getId());
        System.out.println(inspect.getImageId());
        System.out.println(inspect.getLogPath());
        System.out.println(inspect.getName());
//        System.out.println(inspect.getNode());
        System.out.println(inspect.getPath());
//        System.out.println(inspect.getPlatform());
        System.out.println(inspect.getResolvConfPath());
        System.out.println(inspect.getRestartCount());
        System.out.println(inspect.getState());
        System.out.println(Arrays.toString(inspect.getVolumesRW()));
    }

    @Test
    public void stopContainer() {
        String image = "portainer/portainer";
        List<Container> containers = dockerClient.listContainersCmd().exec();
        for (Container value : containers) {
            if (value.getImage().equals(image)) {
                System.out.println("Id-nya adalah:" + value.getId());
                dockerClient.stopContainerCmd(value.getId()).exec();
                System.out.println("Stop Container with Id: " + value.getId());
            } else {
                System.out.println("Coba lagi");
            }
        }
    }

    @Test
    public void startContainer() {
        String image = "portainer/portainer";
        List<Container> containers = dockerClient.listContainersCmd()
                .withShowSize(true)
                .withShowAll(true)
                .withStatusFilter("exited")
                .exec();
        for (Container container : containers) {
            if (container.getImage().equals(image)) {
                System.out.println("Id-nya adalah:" + container.getId());
                dockerClient.startContainerCmd(container.getId());
                System.out.println("Start Container with Id: " + container.getId());
                break;
            } else {
                System.out.println("Coba lagi gan.........");
            }
        }
    }

    @Test
    public void createZaleniumContainer() throws InterruptedException {
        CreateContainerResponse container
                = dockerClient.createContainerCmd("dosel/zalenium")
                .withName("zalenium")
                .withExposedPorts(ExposedPort.tcp(4444),ExposedPort.tcp(4444))
                .withBinds(Bind.parse("/tmp/videos:/home/seluser/videos"),Bind.parse("/var/run/docker.sock:/var/run/docker.sock"))
                .withPrivileged(true)
                .withRestartPolicy(RestartPolicy.noRestart())
                .withAttachStdout(true).withAttachStderr(true)
                .withCmd("start")
                .exec();
        System.out.println(container.getId());
        Thread.sleep(5000);
        dockerClient.startContainerCmd(container.getId());
    }


    @AfterClass
    public void stopDocker() {

    }
}
