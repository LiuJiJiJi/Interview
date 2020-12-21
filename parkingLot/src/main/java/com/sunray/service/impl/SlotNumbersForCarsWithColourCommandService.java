package com.sunray.service.impl;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.constant.DBConstant;
import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.repository.redis.RedisParkHistoryRepository;
import com.sunray.repository.redis.RedisParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SlotNumbersForCarsWithColourCommandService extends CommandService<List<ParkSlot>> {

    private ParkSlotRepository parkSlotRepository;
    private ParkHistoryRepository parkHistoryRepository;
    {
        switch (DBConstant.DB_TYPE) {
            case REDIS:
                parkSlotRepository = new RedisParkSlotRepository();
                parkHistoryRepository = new RedisParkHistoryRepository();
                break;
            default:
                parkSlotRepository = new RedisParkSlotRepository();
                parkHistoryRepository = new RedisParkHistoryRepository();
                break;
        }
    }

    private final String[] paramsTemplate = {"slot_numbers_for_cars_with_colour", ColorEnum.WHITE.value};
    private final String paramsTemplateString = String.join(" ", Arrays.asList(paramsTemplate));
    private final String tipMessageTemplate = "\nCommand '${paramsString}' is invalid \nTry: ${paramsTemplateString} \n";


    @Override
    public void validate(String[] params) throws Exception{
        String paramsString = String.join(" ", Arrays.asList(params));
        String tipMessage = tipMessageTemplate.replace("${paramsString}", paramsString).replace("${paramsTemplateString}", paramsTemplateString);

        if (params.length != 2) {
            throw new ValidationException(tipMessage);
        }

    }

    @Override
    public List<ParkSlot> run(String[] params) throws Exception{
        String carColor = params[1];

        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        parkSlots = parkSlots.stream().filter(x -> x.getCarNumber() != null && carColor.equals(x.getCarColor())).collect(Collectors.toList());

        if (parkSlots.size() == 0) {
            System.out.println("Not found \"" + carColor + "\" color car car parking in slot.");
        }

        String carNumbers = parkSlots.stream().map(ParkSlot::getNumber).collect(Collectors.joining(", "));
        System.out.println(carNumbers);

        return parkSlots;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
