package com.blckroot.cmd;

interface OptionContract {
    String getLongestName();
    String getShortestName();
    String getDescription();
    String getParameterLabel();
    Object getValue();
    void setParameterLabel(String parameterLabel);
    void setValue(Object value);
}
