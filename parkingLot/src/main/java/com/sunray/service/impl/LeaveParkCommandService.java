package com.sunray.service.impl;

import com.sunray.common.config.CommonBeanConfig;
import com.sunray.common.constant.CommonConstant;
import com.sunray.common.expection.SunrayException;
import com.sunray.common.expection.ValidationException;
import com.sunray.common.util.ValidationUtil;
import com.sunray.entity.modal.ParkHistory;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.service.CommandService;

import java.util.Arrays;
import java.util.Date;

public class LeaveParkCommandService extends CommandService<ParkHistory> {

    private final ParkSlotRepository parkSlotRepository = CommonBeanConfig.parkSlotRepository;
    private final ParkHistoryRepository parkHistoryRepository = CommonBeanConfig.parkHistoryRepository;


    private final String[] paramsTemplate = {"leave", "2"};
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
    public ParkHistory run(String[] params){
        String slotNumber = params[1];
        ParkSlot parkSlot = parkSlotRepository.getBySlotNumber(slotNumber);
        if (parkSlot == null) {
            throw new SunrayException("Not Found this parking slot.");
        }
        if (parkSlot.getCarNumber() == null) {
            throw new SunrayException("This parking slot is no use.");
        }

        ParkHistory parkHistory = parkHistoryRepository.getLastHistoryByCarNumber(parkSlot.getCarNumber());
        if (parkHistory == null) {
            throw new SunrayException("[Data exception] Not Found park history.");
        }
        parkHistory.setLeaveTime(new Date());
        parkHistory.setCost(CommonConstant.PARK_PRICE);
        parkHistory.setDiscount(0.0);
        parkHistory.setFinalCost(parkHistory.getCost() - parkHistory.getDiscount());
        Long parkSecond = (parkHistory.getLeaveTime().getTime() - parkHistory.getEnterTime().getTime())/1000;
        parkHistory.setParkSecond(parkSecond);
        parkHistoryRepository.update(parkHistory);

        parkSlot.setCarColor(null);
        parkSlot.setCarNumber(null);
        parkSlotRepository.update(parkSlot);


        String message = "Slot number ${slotNumber} is free";
        message = message.replace("${slotNumber}", parkSlot.getNumber());
        System.out.println(message);

        return parkHistory;
    }

    @Override
    public String getParamsTemplateString() {
        return paramsTemplateString;
    }

}
