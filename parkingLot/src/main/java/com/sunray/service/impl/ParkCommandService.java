package com.sunray.service.impl;

import com.sunray.common.expection.ValidationException;
import com.sunray.service.CommandService;
import java.util.Arrays;

public class ParkCommandService extends CommandService {

    private final String[] paramsTemplate = {"park", "AA-00-AA-0001",  "White"};
    private final String paramsTemplateString = String.join(" ", Arrays.asList(paramsTemplate));
    private final String tipMessageTemplate = "\nCommand '${paramsString}' is invalid \nTry: ${paramsTemplateString} \n";


    @Override
    public void validate(String[] params) throws Exception{
        String paramsString = String.join(" ", Arrays.asList(params));
        String tipMessage = tipMessageTemplate.replace("${paramsString}", paramsString).replace("${paramsTemplateString}", paramsTemplateString);

        if (params.length != 3) {
            throw new ValidationException(tipMessage);
        }
    }

    @Override
    public void run(String[] params) throws Exception{
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
