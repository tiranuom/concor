package com.tix.mgateway.repository.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "remote_info")
public class RemoteInfo {

    @Id
    private String remoteId;
    private String api_key;
    private String password;

    @OneToMany(targetEntity = ServerInfo.class, mappedBy = "remoteId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ServerInfo> serverInfo;

    public RemoteInfo(String remoteId, String api_key, String password, List<ServerInfo> serverInfo) {
        this.remoteId = remoteId;
        this.api_key = api_key;
        this.password = password;
        this.serverInfo = serverInfo;
    }

    public RemoteInfo() {
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ServerInfo> getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(List<ServerInfo> serverInfo) {
        this.serverInfo = serverInfo;
    }
}
