package com.sunray.service;

import com.sunray.common.config.CommonBeanConfig;
import com.sunray.common.constant.ColorEnum;
import com.sunray.common.expection.NoParkSlotException;
import com.sunray.common.util.GenerateUtil;
import com.sunray.entity.modal.ParkHistory;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.service.impl.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CommandServiceTest {

    private ParkSlotRepository parkSlotRepository = CommonBeanConfig.parkSlotRepository;
    private ParkCommandService parkCommandService = new ParkCommandService();
    private CreateParkingLotCommandService createParkingLotCommandService = new CreateParkingLotCommandService();
    private LeaveParkCommandService leaveParkCommandService = new LeaveParkCommandService();
    private StatusCommandService statusCommandService = new StatusCommandService();
    private ExitCommandService exitCommandService = new ExitCommandService();
    private ResigtrationNumbersForCarsWithColourCommandService resigtrationNumbersForCarsWithColourCommandService = new ResigtrationNumbersForCarsWithColourCommandService();
    private SlotNumbersForCarsWithColourCommandService slotNumbersForCarsWithColourCommandService = new SlotNumbersForCarsWithColourCommandService();
    private SlotNumberForRegistrationNumberCommandService slotNumberForRegistrationNumberCommandService = new SlotNumberForRegistrationNumberCommandService();

    @Before
    public void before() throws Exception {
        createParkingLotCommand(6L);
    }

    @After
    public void after() throws Exception {
        exitCommand();
    }

    @Test
    public void parkCommandTest() throws Exception {
        parkCommand();
    }

    @Test
    public void leaveParkCommandTest() throws Exception {
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

    @Test
    public void statusCommandTest() throws Exception {
        ArrayList<ParkSlot> createParkSlots = new ArrayList<>();
        createParkSlots.add(parkCommand());
        createParkSlots.add(parkCommand());
        createParkSlots.add(parkCommand());
        createParkSlots.add(parkCommand());

        String firstParam = "status";
        String[] params = {firstParam};
        List<ParkSlot> parkSlots = statusCommandService.start(params);
        assertEquals(createParkSlots.size(), parkSlots.size());
        for (int i = 0; i < createParkSlots.size(); i++) {
            assertEquals(createParkSlots.get(i).getCarNumber(), parkSlots.get(i).getCarNumber());
        }
    }

    @Test
    public void resigtrationNumbersForCarsWithColourCommandTest() throws Exception {
        ParkSlot parkSlot = parkCommand();

        String firstParam = "registration_numbers_for_cars_with_colour";
        String carColor = parkSlot.getCarColor();
        String carNumber = parkSlot.getCarNumber();
        String[] params = {firstParam, carColor};

        List<ParkSlot> parkSlots = resigtrationNumbersForCarsWithColourCommandService.start(params);
        assertEquals(1, parkSlots.size());
        assertEquals(carColor, parkSlots.get(0).getCarColor());
        assertEquals(carNumber, parkSlots.get(0).getCarNumber());

    }

    @Test
    public void slotNumbersForCarsWithColourCommandTest() throws Exception {
        ParkSlot parkSlot = parkCommand();

        String firstParam = "slot_numbers_for_cars_with_colour";
        String carColor = parkSlot.getCarColor();
        String carNumber = parkSlot.getCarNumber();
        String[] params = {firstParam, carColor};

        List<ParkSlot> parkSlots = slotNumbersForCarsWithColourCommandService.start(params);
        assertEquals(1, parkSlots.size());
        assertEquals(carColor, parkSlots.get(0).getCarColor());
        assertEquals(carNumber, parkSlots.get(0).getCarNumber());

    }


    @Test
    public void slotNumberForRegistrationNumberCommandTest() throws Exception {
        ParkSlot createParkSlot = parkCommand();

        String firstParam = "slot_number_for_registration_number";
        String carColor = createParkSlot.getCarColor();
        String carNumber = createParkSlot.getCarNumber();
        String[] params = {firstParam, carNumber};

        ParkSlot parkSlot = slotNumberForRegistrationNumberCommandService.start(params);
        assertNotNull(parkSlot);
        assertEquals(carColor, parkSlot.getCarColor());
        assertEquals(carNumber, parkSlot.getCarNumber());

    }

    public void createParkingLotCommand(long number) throws Exception {
        String firstParam = "create_parking_lot";
        String[] params = {firstParam, number + ""};
        List<ParkSlot> parkSlots = createParkingLotCommandService.start(params);

        assertNotNull(parkSlots);
        assertEquals(number, parkSlots.size());
    }

    public void exitCommand() throws Exception {
        String firstParam = "exit";
        String[] params = {firstParam};
        exitCommandService.start(params);

        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        assertEquals(0, parkSlots.size());
    }

    public ParkSlot parkCommand() {
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

}
