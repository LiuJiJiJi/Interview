package com.sunary.service;

/**
 * @author liuji
 * @date 2020-12-18 16:42
 * @description command service
 */
public interface CommandService {
   boolean validate(String[] params);
   void run(String[] params);
}
