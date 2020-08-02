package com.oktaliem;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DockerClientBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


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
                dockerClient.startContainerCmd(container.getId()).exec();
                System.out.println("Start Container with Id: " + container.getId());
                break;
            } else {
                System.out.println("Coba lagi gan.........");
            }
        }
    }

    @Test
    public void createZaleniumContainer() {
        CreateContainerResponse container = dockerClient.createContainerCmd("dosel/zalenium")
                                                        .withCmd("--rm")
                                                        .withCmd("--it")
                                                        .withCmd("-d")
                                                        .withName("zalenium")
                                                        .withPortBindings(PortBinding.parse("4444:4444"))
                                                        .withBinds(Bind.parse("/var/run/docker.sock:/var/run/docker.sock"))
                                                        .withBinds(Bind.parse("/tmp/videos:/home/seluser/videos"))
                                                        .withPrivileged(true).exec();
        System.out.println(container.getId());
        dockerClient.startContainerCmd(container.getId()).exec();
    }


    @AfterClass
    public void stopDocker() {

    }
}
