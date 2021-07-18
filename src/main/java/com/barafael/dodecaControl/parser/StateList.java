package com.barafael.dodecaControl.parser;

import java.util.Arrays;
import java.util.List;

public class StateList {
    public StateList(String... states) {
        this.states = Arrays.asList(states);
    }

    List<String> states;
}
