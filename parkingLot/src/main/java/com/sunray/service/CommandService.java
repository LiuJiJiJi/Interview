package com.sunray.service;

/**
 * @author liuji
 * @date 2020-12-18 16:42
 * @description command service
 */
public abstract class CommandService {
   public abstract void validate(String[] params) throws Exception;
   public abstract void run(String[] params) throws Exception;
   public abstract String[] getParamsTemplate() throws Exception;

   public void start(String[] params) throws Exception{
      this.validate(params);
      this.run(params);
   }
}
