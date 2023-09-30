package com.blckroot.commander.command;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParsedCommand extends Command {
    private final Map<Integer, Object> positionalParameterValuesToParameterPositions = new LinkedHashMap<>();
    private final Map<String, Object> optionValuesToOptionLongNames = new LinkedHashMap<>();
    public ParsedCommand(String name) {
        super(name);
    }

    public Map<Integer, Object> getPositionalParameterValuesToParameterPositions() {
        return positionalParameterValuesToParameterPositions;
    }

    public Map<String, Object> getOptionValuesToOptionLongNames() {
        return optionValuesToOptionLongNames;
    }

    public void setPositionalParameterValueToParameterPosition(Object value, Integer position) {
        positionalParameterValuesToParameterPositions.put(position, value);
    }
    public void setOptionValueToOptionLongName(Object value, String name) {
        optionValuesToOptionLongNames.put(name, value);
    }
}
