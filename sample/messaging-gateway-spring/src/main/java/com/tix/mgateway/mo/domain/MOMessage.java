package com.tix.mgateway.mo.domain;

public class MOMessage {

    private Session session;
    private String msisdn;
    private String key;

    public MOMessage(String msisdn, String key) {
        this.msisdn = msisdn;
        this.key = key;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getKey() {
        return key;
    }
}
