package com.barafael.dodecaControl.parser;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Optional;

public class Parser {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<Revision> parseRevision(String string) {
        String[] split = string.split(";");
        if (split.length != 3) {
            return Optional.empty();
        }
        try {
            int major = Integer.parseInt(split[0].trim());
            int minor = Integer.parseInt(split[1].trim());
            int patch = Integer.parseInt(split[2].trim());
            return Optional.of(new Revision(major, minor, patch));
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<CommandList> parseCommandlist(String string) {
        return Optional.of(new CommandList(string.split(";")));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<StateList> parseStatelist(String string) {
        return Optional.of(new StateList((string.split(";"))));
    }
}
