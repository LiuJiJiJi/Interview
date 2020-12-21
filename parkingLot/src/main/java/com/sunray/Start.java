package com.sunray;

import com.sunray.common.expection.SunrayException;
import com.sunray.common.util.GenerateUtil;
import com.sunray.service.CommandService;
import com.sunray.service.impl.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Start {

    private static Map<String, CommandService> commandServiceMap = new ConcurrentHashMap<>();
    private static String helpMessage = "";

    public static void main(String[] args) {
        System.out.println("██████╗  █████╗ ██████╗ ██╗  ██╗██╗███╗   ██╗ ██████╗     ██╗      ██████╗ ████████╗");
        System.out.println("██╔══██╗██╔══██╗██╔══██╗██║ ██╔╝██║████╗  ██║██╔════╝     ██║     ██╔═══██╗╚══██╔══╝");
        System.out.println("██████╔╝███████║██████╔╝█████╔╝ ██║██╔██╗ ██║██║  ███╗    ██║     ██║   ██║   ██║   ");
        System.out.println("██╔═══╝ ██╔══██║██╔══██╗██╔═██╗ ██║██║╚██╗██║██║   ██║    ██║     ██║   ██║   ██║   ");
        System.out.println("██║     ██║  ██║██║  ██║██║  ██╗██║██║ ╚████║╚██████╔╝    ███████╗╚██████╔╝   ██║   ");
        System.out.println("╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚══════╝ ╚═════╝    ╚═╝   ");
        System.out.println("args:" + Arrays.toString(args));

        commandServiceMap.put("create_parking_lot", new CreateParkingLotCommandService());
        commandServiceMap.put("park", new ParkCommandService());
        commandServiceMap.put("leave", new LeaveParkCommandService());
        commandServiceMap.put("status", new StatusCommandService());
        commandServiceMap.put("exit", new ExitCommandService());
        commandServiceMap.put("registration_numbers_for_cars_with_colour", new ResigtrationNumbersForCarsWithColourCommandService());
        commandServiceMap.put("slot_numbers_for_cars_with_colour", new SlotNumbersForCarsWithColourCommandService());
        commandServiceMap.put("slot_number_for_registration_number", new SlotNumberForRegistrationNumberCommandService());

        StringBuffer helpMessageSB = new StringBuffer();
        for (Map.Entry<String, CommandService> entry: commandServiceMap.entrySet()) {

            helpMessageSB.append("\t")
                    .append(entry.getKey())
                    .append(": ")
                    .append(GenerateUtil.multipleSpaces(60 - entry.getKey().length()))
                    .append(entry.getValue().getParamsTemplateString())
                    .append("\n");
        }
        helpMessage = helpMessageSB.toString();
        System.out.println(helpMessage);


        // get input form file
        getInputFromFileAndStart(args);

        // listen for user input
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String inputValue = input.nextLine();
            if (inputValue.trim().length() < 1) {
                continue;
            }
            commandServiceStart(inputValue);
        }
    }

    private static void getInputFromFileAndStart(String[] args) {
        HashMap<String, String> argMap = new HashMap<>();
        Arrays.stream(args).forEach(x -> {
            String[] values = x.trim().split("=");
            if (values.length == 2) {
                argMap.put(values[0], values[1]);
            }
        });
        String inputFilePath = argMap.get("inputFilePath");
        if (inputFilePath == null) {
            return;
        }
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.err.println("Can't read this file: \"" + inputFilePath + "\"");
            return;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            String inputValue = null;
            int line = 1;
            while ((inputValue = reader.readLine()) != null) {
                System.out.println(inputValue);
                commandServiceStart(inputValue);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static void commandServiceStart(String inputValue) {
        String[] params = inputValue.trim().split(" ");
        String firstParam = params[0];

        CommandService commandService = commandServiceMap.get(firstParam);
        if (commandService == null) {
            System.out.println("\"" + firstParam + "\" is support command; please input command as follow：");
            System.out.println(helpMessage);
            return;
        }

        try {
            commandService.start(params);
            if ("exit".equals(firstParam)) {
                System.exit(1);
            }
        } catch (SunrayException | AssertionError e ) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unknown Exception; Please contract developer to fix this issue!");
            e.printStackTrace();
        }
    }

}
