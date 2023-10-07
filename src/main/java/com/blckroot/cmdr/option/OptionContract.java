package com.blckroot.cmdr.option;

interface OptionContract {
    String getLongestName();
    String getShortestName();
    String getDescription();
    String getParameterLabel();
    Object getValue();
    void setValue(Object value);
}
