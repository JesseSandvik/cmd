package com.blckroot.cmd;

interface PositionalParameterContract {
    String getLabel();
    String getDescription();
    Object getValue();
    void setValue(Object value);
}
