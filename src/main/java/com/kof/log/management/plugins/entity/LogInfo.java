package com.kof.log.management.plugins.entity;

import java.io.Serializable;

public class LogInfo implements Serializable {

    private String provider;
    private String comsumer;

    private String methodName;
    private String functiongName;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getComsumer() {
        return comsumer;
    }

    public void setComsumer(String comsumer) {
        this.comsumer = comsumer;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFunctiongName() {
        return functiongName;
    }

    public void setFunctiongName(String functiongName) {
        this.functiongName = functiongName;
    }


}
