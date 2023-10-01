package com.blckroot.commander.command;

import java.util.LinkedHashMap;
import java.util.Map;

class ParsedCommand extends Command {
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

    public void setPositionalParameterValueToParameterPosition(Integer position, Object value) {
        positionalParameterValuesToParameterPositions.put(position, value);
    }
    public void setOptionValueToOptionLongName(String name, Object value) {
        optionValuesToOptionLongNames.put(name, value);
    }
}
