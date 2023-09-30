package com.blckroot.commander.command.option;

import org.jetbrains.annotations.NotNull;

public class Option {
    private final String longName;
    private final String shortName;
    private final String description;
    private final String parameterLabel;
    private final boolean isBoolean;

    public Option(@NotNull String name, @NotNull String description, @NotNull String parameterLabel, @NotNull Boolean isBoolean) {
        this.longName = name;
        this.shortName = null;
        this.description = description;
        this.parameterLabel = parameterLabel;
        this.isBoolean = isBoolean;
    }

    public Option(@NotNull String name, @NotNull String description, @NotNull String parameterLabel) {
        this.longName = name;
        this.shortName = null;
        this.description = description;
        this.parameterLabel = parameterLabel;
        this.isBoolean = true;
    }

    public Option(@NotNull String name, @NotNull String description, Boolean isBoolean) {
        this.longName = name;
        this.shortName = null;
        this.description = description;
        this.parameterLabel = null;
        this.isBoolean = isBoolean;
    }

    public Option(@NotNull String name, @NotNull String description) {
        this.longName = name;
        this.shortName = null;
        this.description = description;
        this.parameterLabel = null;
        this.isBoolean = true;
    }

    public Option(@NotNull String[] names, @NotNull String description, @NotNull String parameterLabel, @NotNull Boolean isBoolean) {
        this.longName = getLongestStringInArray(names);
        this.shortName = getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = parameterLabel;
        this.isBoolean = isBoolean;
    }

    public Option(@NotNull String[] names, @NotNull String description, @NotNull String parameterLabel) {
        this.longName = getLongestStringInArray(names);
        this.shortName = getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = parameterLabel;
        this.isBoolean = true;
    }

    public Option(@NotNull String[] names, @NotNull String description, Boolean isBoolean) {
        this.longName = getLongestStringInArray(names);
        this.shortName = getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = null;
        this.isBoolean = isBoolean;
    }

    public Option(@NotNull String[] names, @NotNull String description) {
        this.longName = getLongestStringInArray(names);
        this.shortName = getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = null;
        this.isBoolean = true;
    }

    private String getShortestStringInArray(String[] strings) {
        if (strings.length == 1 && strings[0].trim().length() == 0) {
            return null;
        }

        if (strings.length == 1) {
            return strings[0];
        }

        String shortestString = "";
        for (String string : strings) {
            if (shortestString.length() == 0 && string.trim().length() != 0) {
                shortestString = string;
            } else {
                if (string.trim().length() < shortestString.length() && string.length() != 0) {
                    shortestString = string;
                }
            }
        }
        return shortestString;
    }

    private String getLongestStringInArray(String[] strings) {
        if (strings.length == 1) {
            return strings[0];
        }

        String longestString = "";
        for (String string : strings) {
            if (longestString.length() == 0 && string.trim().length() != 0) {
                longestString = string;
            } else {
                if (string.trim().length() > longestString.length() && string.length() != 0) {
                    longestString = string;
                }
            }
        }
        return longestString;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getParameterLabel() {
        return parameterLabel;
    }

    public boolean isBoolean() {
        return isBoolean;
    }
}
