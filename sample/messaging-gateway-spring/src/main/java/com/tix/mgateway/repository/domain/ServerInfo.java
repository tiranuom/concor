package com.tix.mgateway.repository.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server_info")
public class ServerInfo {

    @Id
    private String serverId;
    private String remoteId;
    private int loadPercentage;
    private String url;

    public ServerInfo(String serverId, String remoteId, int loadPercentage, String url) {
        this.serverId = serverId;
        this.remoteId = remoteId;
        this.loadPercentage = loadPercentage;
        this.url = url;
    }

    public ServerInfo() {
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public int getLoadPercentage() {
        return loadPercentage;
    }

    public void setLoadPercentage(int loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    public String getUrl() {
        return url;
    }
}
