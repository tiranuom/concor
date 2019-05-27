package com.tix.ussd.domain;

import java.util.Map;
import java.util.stream.Collectors;

public class MenuStructure {

    private Menu menu;

    public MenuStructure(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public static class Menu {
        private String shortMessage;
        private String message;
        private Map<String, Menu> options;
        private String optionsAsMessage = "";

        public Menu(String shortMessage, String message, Map<String, Menu> options) {
            this.shortMessage = shortMessage;
            this.message = message;
            this.options = options;
        }

        public String getMessage() {
            return message + "\n" + optionsAsMessage;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setOptions(Map<String, Menu> options) {
            this.options = options;
            this.optionsAsMessage = options.entrySet().stream()
                    .map(entry -> entry.getKey() + " " + entry.getValue().shortMessage)
                    .collect(Collectors.joining("\n"));
        }

        public Map<String, Menu> getOptions() {
            return options;
        }
    }
}
