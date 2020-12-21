package com.sunray.service.impl;

import com.sunray.common.constant.DBConstant;
import com.sunray.common.expection.ValidationException;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.repository.redis.RedisParkHistoryRepository;
import com.sunray.repository.redis.RedisParkSlotRepository;
import com.sunray.service.CommandService;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class ExitCommandService extends CommandService<List<ParkSlot>> {

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

    private final String[] paramsTemplate = {"exit"};
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
        parkSlotRepository.deleteAll();
        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        Assert.assertEquals(0, parkSlots.size());
        System.exit(1);
        return parkSlotRepository.getAll();
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}