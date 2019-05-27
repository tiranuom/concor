package com.tix.ussd;

import com.tix.ussd.domain.MenuStructure;
import com.tix.ussd.domain.UssdSession;

import java.util.HashMap;

public class MenuStructureResolver {

    private MenuStructure defaultMenuStructure = new MenuStructure(
            new MenuStructure.Menu(
                    "",
                    "Level 1",
                    new HashMap<String, MenuStructure.Menu>() {{
                        put("1", new MenuStructure.Menu(
                                        "",
                                        "Level 2: 1",
                                        new HashMap<String, MenuStructure.Menu>() {{
                                            put("1", new MenuStructure.Menu(
                                                            "",
                                                            "Level 2: 1: 1",
                                                            new HashMap<String, MenuStructure.Menu>() {{

                                                            }}
                                                    )
                                            );
                                        }}
                                )
                        );
                        put("2", new MenuStructure.Menu(
                                        "",
                                        "Level 2: 2: 1",
                                        new HashMap<String, MenuStructure.Menu>() {{
                                            put("1", new MenuStructure.Menu(
                                                            "",
                                                            "Level 2: 2: 1",
                                                            new HashMap<String, MenuStructure.Menu>() {{

                                                            }}
                                                    )
                                            );
                                        }}
                                )
                        );
                    }}
            )
    );

    public void apply(UssdSession ussdSession) {
        MenuStructure menuStructure = ussdSession.getMenuStructure();
        if (menuStructure == null) {
            ussdSession.setMenuStructure(defaultMenuStructure);
        }
    }
}
