package com.sunray.service.impl;

import com.sunray.common.config.CommonBeanConfig;
import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatusCommandService extends CommandService<List<ParkSlot>> {

    private final ParkSlotRepository parkSlotRepository = CommonBeanConfig.parkSlotRepository;
    private final ParkHistoryRepository parkHistoryRepository = CommonBeanConfig.parkHistoryRepository;

    private final String[] paramsTemplate = {"status"};
    private final String paramsTemplateString = String.join(" ", Arrays.asList(paramsTemplate));
    private final String tipMessageTemplate = "\nCommand '${paramsString}' is invalid \nTry: ${paramsTemplateString} \n";


    @Override
    public void validate(String[] params) throws Exception{
        String paramsString = String.join(" ", Arrays.asList(params));
        String tipMessage = tipMessageTemplate.replace("${paramsString}", paramsString).replace("${paramsTemplateString}", paramsTemplateString);

        if (params.length != 1) {
            throw new ValidationException(tipMessage);
        }

    }

    @Override
    public List<ParkSlot> run(String[] params) throws Exception{
        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        parkSlots = parkSlots.stream().filter(x -> x.getCarNumber() != null).collect(Collectors.toList());

        String message = "park ${carNumber} ${carColor}";
        if (parkSlots.size() == 0) {
            System.out.println("No car is parking in slot.");
        }
        parkSlots.forEach(x -> {
            String pintMessage = message
                    .replace("${carNumber}", x.getCarNumber())
                    .replace("${carColor}", x.getCarColor());
            System.out.println(pintMessage);
        });

        return parkSlots;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
