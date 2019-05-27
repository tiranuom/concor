package com.tix.ussd;

import com.tix.ussd.domain.MenuStructure;
import com.tix.ussd.domain.UssdSession;

public class MenuResolver {
    public void apply(UssdSession ussdSession) {
        MenuStructure.Menu menu = ussdSession.getMenu();
        if (menu == null) {
            menu = ussdSession.getMenuStructure().getMenu();
        } else {
            menu = menu.getOptions().get(ussdSession.getUssdMessage().getMessage());
        }
        ussdSession.setMenu(menu);
    }
}
