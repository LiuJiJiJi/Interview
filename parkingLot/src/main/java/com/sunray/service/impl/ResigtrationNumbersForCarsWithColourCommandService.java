package com.sunray.service.impl;

import com.sunray.common.config.CommonBeanConfig;
import com.sunray.common.constant.ColorEnum;
import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResigtrationNumbersForCarsWithColourCommandService extends CommandService<List<ParkSlot>> {

    private final ParkSlotRepository parkSlotRepository = CommonBeanConfig.parkSlotRepository;
    private final ParkHistoryRepository parkHistoryRepository = CommonBeanConfig.parkHistoryRepository;

    private final String[] paramsTemplate = {"registration_numbers_for_cars_with_colour", ColorEnum.WHITE.value};
    private final String paramsTemplateString = String.join(" ", Arrays.asList(paramsTemplate));
    private final String tipMessageTemplate = "\nCommand '${paramsString}' is invalid \nTry: ${paramsTemplateString} \n";


    @Override
    public void validate(String[] params) throws Exception {
        String paramsString = String.join(" ", Arrays.asList(params));
        String tipMessage = tipMessageTemplate.replace("${paramsString}", paramsString).replace("${paramsTemplateString}", paramsTemplateString);

        if (params.length != 2) {
            throw new ValidationException(tipMessage);
        }

    }

    @Override
    public List<ParkSlot> run(String[] params) throws Exception {
        String carColor = params[1];

        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        parkSlots = parkSlots.stream().filter(x -> x.getCarNumber() != null && carColor.equals(x.getCarColor())).collect(Collectors.toList());

        if (parkSlots.size() == 0) {
            System.out.println("Not found \"" + carColor + "\" color car car parking in slot.");
        }

        String carNumbers = parkSlots.stream().map(ParkSlot::getCarNumber).collect(Collectors.joining(", "));
        System.out.println(carNumbers);

        return parkSlots;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
