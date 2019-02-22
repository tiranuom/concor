package com.tix.mgateway.mo.domain;

public class MOMessage {

    private Session session;
    private String from;
    private String to;
    private String message;
    private String response;

    public MOMessage(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MOMessage{");
        sb.append("from='").append(from).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
