package com.tix.ussd.domain;

public class UssdSession {

    private UssdMessage ussdMessage;
    private String sessionId;
    private MenuStructure menuStructure;
    private MenuStructure.Menu menu;

    public UssdSession(String sessionId) {

        this.sessionId = sessionId;
    }

    public UssdMessage getUssdMessage() {
        return ussdMessage;
    }

    public void setUssdMessage(UssdMessage ussdMessage) {
        this.ussdMessage = ussdMessage;
    }

    public MenuStructure getMenuStructure() {
        return menuStructure;
    }

    public void setMenuStructure(MenuStructure menuStructure) {
        this.menuStructure = menuStructure;
    }

    public MenuStructure.Menu getMenu() {
        return menu;
    }

    public void setMenu(MenuStructure.Menu menu) {
        this.menu = menu;
    }

    public String getMessage() {
        String message;
        if (menu != null) {
            message = menu.getMessage();
        } else {
            message = "COMPLETED";
        }
        return message;
    }
}
