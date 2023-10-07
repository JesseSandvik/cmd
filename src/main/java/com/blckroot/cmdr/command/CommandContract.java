package com.blckroot.cmdr.command;

import com.blckroot.cmdr.option.Option;
import com.blckroot.cmdr.positionalParameter.PositionalParameter;

import java.util.List;

interface CommandContract {
    String getName();
    String getVersion();
    String getUsageDescriptionSynopsis();
    String getUsageDescriptionFull();
    Boolean executesWithoutArguments();
    List<PositionalParameter> getPositionalParameters();
    List<Option> getOptions();
    List<Command> getSubcommands();
    Object getPositionalParameterValueByIndex(Integer index);
    Object getOptionValueByLongestName(String longestName);
    void setVersion(String version);
    void setUsageDescriptionSynopsis(String usageDescriptionSynopsis);
    void setUsageDescriptionFull(String usageDescriptionFull);
    void executesWithoutArguments(Boolean executesWithoutArguments);
    void addPositionalParameter(PositionalParameter positionalParameter);
    void addOption(Option option);
    void addSubcommand(Command subcommand);
    void setPositionalParameterIndexToValue(Integer index, Object value);
    void setOptionLongestNameToValue(String longestName, Object value);
    Integer call() throws Exception;
}
