package com.sunray.service.impl;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.constant.DBConstant;
import com.sunray.common.expection.NoParkSlotException;
import com.sunray.common.expection.SunrayException;
import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkHistory;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.repository.redis.RedisParkHistoryRepository;
import com.sunray.repository.redis.RedisParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ParkCommandService extends CommandService<ParkSlot> {

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

        String carNumber = params[1];
        ParkSlot parkSlot = parkSlotRepository.getByCarNumber(carNumber);
        if (parkSlot != null) {
            throw new SunrayException("Your car has park on slot number: " + parkSlot.getNumber());
        }

    }

    @Override
    public ParkSlot run(String[] params) throws Exception{
        String carNumber = params[1];
        ColorEnum colorEnum = ColorEnum.getEnumByValue(params[2]);
        List<ParkSlot> freeSlot = parkSlotRepository.getFreeSlot();
        if (freeSlot.size() < 1) {
            throw new NoParkSlotException("No parking space available.");
        }

        ParkSlot parkSlot = freeSlot.get(0);
        parkSlot.setCarNumber(carNumber);
        parkSlot.setCarColor(colorEnum.value);
        parkSlot = parkSlotRepository.create(parkSlot);

        ParkHistory parkHistory = new ParkHistory();
        parkHistory.setCarColor(parkSlot.getCarColor());
        parkHistory.setSlotNumber(parkSlot.getNumber());
        parkHistory.setCarNuber(parkSlot.getCarNumber());
        parkHistory.setEnterTime(new Date());
        parkHistory = parkHistoryRepository.create(parkHistory);

        String message = "Allocated slot number: ${slotNumber}";
        message = message.replace(" ${slotNumber}", parkSlot.getNumber());
        System.out.println(message);

        return parkSlot;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
