package com.barafael.dodecaControl.parser;

import org.jetbrains.annotations.NotNull;

public class Revision {
    int major;
    int minor;
    int patch;

    public Revision(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    @NotNull
    @Override
    public String toString() {
        return "maj: " + major + ", min: " + minor + ", pat: " + patch;
    }
}
