package com.sunary;

import com.sunary.service.CommandService;
import com.sunary.service.impl.ExitCommandService;
import com.sunary.service.impl.StatusCommandService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        System.out.println("=====================Parking Lot Start=====================");

        Map<String, CommandService> commandServiceMap = new HashMap<String, CommandService>();
        commandServiceMap.put("status", new StatusCommandService());
        commandServiceMap.put("exit", new ExitCommandService());

        StringBuffer helpMessage = new StringBuffer();
        for (Map.Entry<String, CommandService> entry: commandServiceMap.entrySet()) {
            helpMessage.append("       ").append(entry.getKey()).append("\n");
        }

        do {
            Scanner input = new Scanner(System.in);
            if (input.hasNext()) {

                String inputValue = input.next();
                String[] params = inputValue.trim().split(" ");
                String firstParam = params[0];

                CommandService commandService = commandServiceMap.get(firstParam);
                if (commandService == null) {
                    System.out.println(firstParam + " is support command; please input command as follow：");
                    System.out.println(helpMessage);
                    continue;
                }

                System.out.println("params:" + params.toString());
            }
        } while (true);
    }

}
