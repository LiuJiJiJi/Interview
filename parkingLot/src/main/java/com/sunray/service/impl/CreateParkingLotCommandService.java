package com.sunray.service.impl;

import com.sunray.common.constant.DBConstant;
import com.sunray.common.expection.ValidationException;
import com.sunray.common.util.ValidationUtil;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.repository.redis.RedisParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;
import java.util.List;

public class CreateParkingLotCommandService extends CommandService<List<ParkSlot>> {

    private ParkSlotRepository parkSlotRepository;
    {
        switch (DBConstant.DB_TYPE) {
            case REDIS:
                parkSlotRepository = new RedisParkSlotRepository();
                break;
            default:
                parkSlotRepository = new RedisParkSlotRepository();
                break;
        }
    }

    private final String[] paramsTemplate = {"create_parking_lot", "6"};
    private final String paramsTemplateString = String.join(" ", Arrays.asList(paramsTemplate));
    private final String tipMessageTemplate = "\nCommand '${paramsString}' is invalid \nTry: ${paramsTemplateString} \n";


    @Override
    public void validate(String[] params) throws Exception{
        String paramsString = String.join(" ", Arrays.asList(params));
        String tipMessage = tipMessageTemplate.replace("${paramsString}", paramsString).replace("${paramsTemplateString}", paramsTemplateString);

        if (params.length != 2) {
            throw new ValidationException(tipMessage);
        }
        if (!ValidationUtil.isNumeric(params[1])) {
            throw new ValidationException("\nPlease inptu number; \nTry: " + paramsTemplateString);
        }

    }

    @Override
    public List<ParkSlot> run(String[] params) throws Exception{
        parkSlotRepository.deleteAll();
        Long number = Long.valueOf(params[1]);
        for (long i = 1; i < number+1; i++) {
            ParkSlot parkSlot = new ParkSlot();
            parkSlot.setId(i);
            parkSlot.setNumber(i+"");
            parkSlotRepository.create(parkSlot);
        }
        List<ParkSlot> parkSlots = parkSlotRepository.getAll();

        String message = "Created a parking lot with ${count} slots";
        message = message.replace("${count}", parkSlots.size() + "");
        System.out.println(message);

        return parkSlots;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
