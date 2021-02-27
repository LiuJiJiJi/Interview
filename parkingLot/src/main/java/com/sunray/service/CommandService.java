package com.sunray.service;

/**
 * @author liuji
 * @date 2020-12-18 16:42
 * @description command service
 */
public abstract class CommandService<T> {
    public abstract void validate(String[] params) throws Exception;

    public abstract T run(String[] params) throws Exception;

    public abstract String getParamsTemplateString();

    public T start(String[] params) throws Exception {
        this.validate(params);
        return this.run(params);
    }
}
