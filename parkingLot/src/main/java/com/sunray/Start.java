package com.sunray;

import com.sunray.common.expection.SunrayException;
import com.sunray.service.CommandService;
import com.sunray.service.impl.ParkCommandService;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        System.out.println("=====================Parking Lot Start=====================");

        Map<String, CommandService> commandServiceMap = new HashMap<String, CommandService>();
//        commandServiceMap.put("status", new StatusCommandService());
//        commandServiceMap.put("exit", new ExitCommandService());
        commandServiceMap.put("park", new ParkCommandService());
//        commandServiceMap.put("leave", new LeaveCommandService());

        StringBuffer helpMessage = new StringBuffer();
        for (Map.Entry<String, CommandService> entry: commandServiceMap.entrySet()) {
            helpMessage.append("    ").append(entry.getKey()).append("\n");
        }

        do {
            Scanner input = new Scanner(System.in);
            if (input.hasNext()) {

                String inputValue = input.next();
                String[] params = inputValue.trim().split(" ");
                String firstParam = params[0];

                CommandService commandService = commandServiceMap.get(firstParam);
                if (commandService == null) {
                    System.out.println("\"" + firstParam + "\" is support command; please input command as follow：");
                    System.out.println(helpMessage);
                    continue;
                }

                try {
                    commandService.start(params);
                } catch (SunrayException e) {
                    System.err.println(e.getMessage());
                } catch (Exception e) {
                    System.err.println("Unknown Exception; Please contract developer to fix this issue!");
                    e.printStackTrace();
                }

            }
        } while (true);
    }

}