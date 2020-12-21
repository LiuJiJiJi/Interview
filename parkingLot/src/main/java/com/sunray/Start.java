package com.sunray;

import com.sunray.common.expection.SunrayException;
import com.sunray.service.CommandService;
import com.sunray.service.impl.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Start {

    public static void main(String[] args) {
        System.out.println("██████╗  █████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗     ██╗      ██████╗ ████████╗");
        System.out.println("██╔══██╗██╔══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝     ██║     ██╔═══██╗╚══██╔══╝");
        System.out.println("██████╔╝███████║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗    ██║     ██║   ██║   ██║   ");
        System.out.println("██╔═══╝ ██╔══██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║    ██║     ██║   ██║   ██║   ");
        System.out.println("██║     ██║  ██║██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝    ███████╗╚██████╔╝   ██║   ");
        System.out.println("╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚══════╝ ╚═════╝    ╚═╝   ");

        Map<String, CommandService> commandServiceMap = new ConcurrentHashMap<>();
        commandServiceMap.put("create_parking_lot", new CreateParkingLotCommandService());
        commandServiceMap.put("park", new ParkCommandService());
        commandServiceMap.put("leave", new LeaveParkCommandService());
        commandServiceMap.put("status", new StatusCommandService());
        commandServiceMap.put("exit", new ExitCommandService());
        commandServiceMap.put("registration_numbers_for_cars_with_colour", new ResigtrationNumbersForCarsWithColourCommandService());
        commandServiceMap.put("slot_numbers_for_cars_with_colour", new SlotNumbersForCarsWithColourCommandService());
        commandServiceMap.put("slot_number_for_registration_number", new SlotNumberForRegistrationNumberCommandService());

        StringBuffer helpMessage = new StringBuffer();
        for (Map.Entry<String, CommandService> entry: commandServiceMap.entrySet()) {

            helpMessage.append("\t")
                    .append(entry.getKey())
                    .append(": ")
                    .append(RandomStringUtils.random(60 - entry.getKey().length(), " "))
                    .append(entry.getValue().getParamsTemplateString())
                    .append("\n");
        }

        System.out.println(helpMessage);

        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String inputValue = input.nextLine();
            if (inputValue.trim().length() < 1) {
                continue;
            }
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
    }

}
