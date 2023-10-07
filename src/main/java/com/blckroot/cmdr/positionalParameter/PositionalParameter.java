package com.blckroot.cmdr.positionalParameter;

public class PositionalParameter implements PositionalParameterContract {
    private final String label;
    private final String description;

    public PositionalParameter(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
