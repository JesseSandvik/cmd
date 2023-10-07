package com.blckroot.cmdr.positionalParameter;

interface PositionalParameterContract {
    String getLabel();
    String getDescription();
    Object getValue();
    void setValue(Object value);
}
