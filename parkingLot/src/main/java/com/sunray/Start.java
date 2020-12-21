package com.sunray;

import com.sunray.common.expection.SunrayException;
import com.sunray.service.CommandService;
import com.sunray.service.impl.CreateParkingLotCommandService;
import com.sunray.service.impl.ExitCommandService;
import com.sunray.service.impl.LeaveParkCommandService;
import com.sunray.service.impl.ParkCommandService;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Start {

    public static void main(String[] args) {
        System.out.println("=====================Parking Lot Start=====================");

        Map<String, CommandService> commandServiceMap = new ConcurrentHashMap<>();
        commandServiceMap.put("create_parking_lot", new CreateParkingLotCommandService());
        commandServiceMap.put("park", new ParkCommandService());
        commandServiceMap.put("leave", new LeaveParkCommandService());
        commandServiceMap.put("exit", new ExitCommandService());

        StringBuffer helpMessage = new StringBuffer();
        for (Map.Entry<String, CommandService> entry: commandServiceMap.entrySet()) {
            helpMessage.append("\t")
                    .append(entry.getKey())
                    .append(": \t")
                    .append(entry.getValue().getParamsTemplateString())
                    .append("\n");
        }

        do {
            Scanner input = new Scanner(System.in);
            if (input.hasNext()) {

                String inputValue = input.nextLine();
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
                } catch (SunrayException | AssertionError e ) {
                    System.err.println(e.getMessage());
                } catch (Exception e) {
                    System.err.println("Unknown Exception; Please contract developer to fix this issue!");
                    e.printStackTrace();
                }

            }
        } while (true);
    }

}
