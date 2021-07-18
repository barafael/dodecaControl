package com.barafael.dodecaControl.parser;

import java.util.Arrays;
import java.util.List;

public class CommandList {
    public CommandList(String... actions) {
        this.actions = Arrays.asList(actions);
    }

    List<String> actions;

    public List<String> getCommands() {
        return actions;
    }
}
