package com.bus.lscdensity;

import com.bus.lscdensity.Initial.Init;
import com.bus.lscdensity.dockerjava.dockerservice.impl.ContainsServiceImpl;
import com.bus.lscdensity.dockerjava.utils.DockerClientConnect;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dastinia
 * @version 1.0
 * @date 2022/12/5
 */
@SpringBootTest
public class lscdensityTests {

    @Autowired
    private ContainsServiceImpl containsService;

    @Test
    public void ContainsServiceTest() {
        DockerClient modelDClient = DockerClientConnect.connectDocker("10.1.1.214", 7788);

        List<String> entryPoint = new ArrayList<String>() {
            {
                add("/bin/bash");
                add("-c");
                add("source ~/.bashrc && bash /root/aibus/bus.sh && tail -f /dev/null");
            }
        };
        List<Bind> bindList = new ArrayList<Bind>() {
            {
                add(new Bind("/root/aibus/buslog", new Volume("/root/aibus/buslog")));
                add(new Bind("/tmp", new Volume("/tmp")));
            }
        };
        CreateContainerResponse modelServiceResponse = containsService.createContainer(modelDClient, "test", "busmodelservice:1.0", new ArrayList<>(), bindList,0L,0L, new ArrayList<Integer>(){{add(0);}}, entryPoint);

        System.out.println(modelServiceResponse.getId());
        containsService.startContainer(modelDClient, modelServiceResponse.getId());
    }

    @Autowired
    Init init;

    @Test
    public void InitTest() {
        for (int i = 0; i < 10; ++i) {
            init.run("2022120102030001");
        }
    }
}
