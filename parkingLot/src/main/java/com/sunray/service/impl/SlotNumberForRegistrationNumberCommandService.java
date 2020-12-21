package com.sunray.service.impl;

import com.sunray.common.constant.DBConstant;
import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.repository.redis.RedisParkHistoryRepository;
import com.sunray.repository.redis.RedisParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;

public class SlotNumberForRegistrationNumberCommandService extends CommandService<ParkSlot> {

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

    private final String[] paramsTemplate = {"slot_number_for_registration_number", "AA-00-AA-0001"};
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
    public ParkSlot run(String[] params) throws Exception{
        String carNumber = params[1];
        ParkSlot parkSlot = parkSlotRepository.getByCarNumber(carNumber);
        if (parkSlot == null) {
            System.err.println("Not found");
            return null;
        }
        System.out.println(parkSlot.getNumber());
        return parkSlot;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
