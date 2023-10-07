package com.blckroot.cmdr.option;

import com.blckroot.cmdr.string.StringService;

public class Option implements OptionContract {
    private final String longestName;
    private final String shortestName;
    private final String description;
    private final String parameterLabel;

    public Option(String[] names, String description, String parameterLabel) {
        StringService stringService = new StringService();
        this.longestName = stringService.getLongestStringInArray(names);
        this.shortestName = stringService.getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = parameterLabel;
    }

    public Option(String[] names, String description) {
        StringService stringService = new StringService();
        this.longestName = stringService.getLongestStringInArray(names);
        this.shortestName = stringService.getShortestStringInArray(names);
        this.description = description;
        this.parameterLabel = null;
    }

    public Option(String name, String description, String parameterLabel) {
        this.longestName = name;
        this.shortestName = null;
        this.description = description;
        this.parameterLabel = parameterLabel;
    }

    public Option(String name, String description) {
        this.longestName = name;
        this.shortestName = null;
        this.description = description;
        this.parameterLabel = null;
    }

    @Override
    public String getLongestName() {
        return longestName;
    }

    @Override
    public String getShortestName() {
        return shortestName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getParameterLabel() {
        return parameterLabel;
    }
}
