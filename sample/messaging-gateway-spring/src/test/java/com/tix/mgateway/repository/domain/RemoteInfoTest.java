package com.tix.mgateway.repository.domain;

import com.tix.mgateway.repository.ServerConfigRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive")
class RemoteInfoTest {


    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Test
    void insertData() {
        List<RemoteInfo> remoteInfo = Arrays.asList(
                new RemoteInfo("mobitel", "test", "test", Arrays.asList(
                        new ServerInfo("mobitel_S1", "mobitel", 20, "http://localhost:9090/mobitel_S1"),
                        new ServerInfo("mobitel_S2", "mobitel", 30, "http://localhost:9090/mobitel_S2"),
                        new ServerInfo("mobitel_S3", "mobitel", 50, "http://localhost:9090/mobitel_S3")
                )),
                new RemoteInfo("dialog", "test", "test", Arrays.asList(
                        new ServerInfo("dialog_S1", "dialog", 20, "http://localhost:9090/dialog_S1"),
                        new ServerInfo("dialog_S2", "dialog", 30, "http://localhost:9090/dialog_S2"),
                        new ServerInfo("dialog_S3", "dialog", 50, "http://localhost:9090/dialog_S3")
                )),
                new RemoteInfo("etisalat", "test", "test", Arrays.asList(
                        new ServerInfo("etisalat_S1", "etisalat", 20, "http://localhost:9090/etisalat_S1")
                )),
                new RemoteInfo("hutch", "test", "test", Arrays.asList(
                        new ServerInfo("hutch_S1", "hutch", 20, "http://localhost:9090/hutch_S1")
                )),
                new RemoteInfo("airtel", "test", "test", Arrays.asList(
                        new ServerInfo("airtel_S1", "airtel", 20, "http://localhost:9090/airtel_S1")
                )));
        serverConfigRepository.saveAll(remoteInfo);
    }

    @Test
    void findAll() {

        System.out.println(serverConfigRepository.findAll());
        Optional<RemoteInfo> mobitel = serverConfigRepository.findByRemoteId("mobitel");
        System.out.println(mobitel);
    }

    @Test
    void deleteAll() {
        serverConfigRepository.deleteAll();
    }
}