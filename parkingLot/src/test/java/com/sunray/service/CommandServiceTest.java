package com.sunray.service;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.expection.NoParkSlotException;
import com.sunray.common.util.GenerateUtil;
import com.sunray.entity.modal.ParkHistory;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.service.impl.CreateParkingLotCommandService;
import com.sunray.service.impl.LeaveParkCommandService;
import com.sunray.service.impl.ParkCommandService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CommandServiceTest {

    private ParkCommandService parkCommandService = new ParkCommandService();
    private CreateParkingLotCommandService createParkingLotCommandService = new CreateParkingLotCommandService();
    private LeaveParkCommandService leaveParkCommandService = new LeaveParkCommandService();

    @Test
    public void CreateParkingLotTest() throws Exception{
        String firstParam = "create_parking_lot";
        String number = "10";
        String[] params = {firstParam, number};
        List<ParkSlot> parkSlots = createParkingLotCommandService.start(params);

        assertNotNull(parkSlots);
        assertEquals(number, parkSlots.size() + "");
    }

    public ParkSlot parkCommand(){
        String firstParam = "park";
        String carnumber = GenerateUtil.singaporeCarnumber();
        String color = ColorEnum.BLACK.value;
        String[] params = {firstParam, carnumber, color};
        try {
            ParkSlot parkSlot = parkCommandService.start(params);
            assertNotNull(parkSlot);
            assertEquals(carnumber, parkSlot.getCarNumber());
            assertEquals(color, parkSlot.getCarColor());
            return parkSlot;
        } catch (Exception e) {
            assertTrue(e instanceof NoParkSlotException);
        }
        return null;
    }

    @Test
    public void parkCommandTest(){
        parkCommand();
    }

    @Test
    public void leaveParkCommandTest() throws Exception{
        ParkSlot parkSlot = parkCommand();
        if (parkSlot == null) {
            System.out.println("Park Failed; no park slot");
            return;
        }

        String firstParam = "leave";
        String slotNumber = parkSlot.getNumber();
        String[] params = {firstParam, slotNumber};

        ParkHistory parkHistory = leaveParkCommandService.start(params);
        assertNotNull(parkHistory);
        assertEquals(parkSlot.getCarNumber(), parkHistory.getCarNuber());
        assertEquals(parkSlot.getNumber(), parkHistory.getSlotNumber());
        assertNotNull(parkHistory.getFinalCost());
    }



}
