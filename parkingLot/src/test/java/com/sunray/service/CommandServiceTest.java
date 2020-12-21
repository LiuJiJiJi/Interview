package com.sunray.service;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.expection.NoParkSlotException;
import com.sunray.common.util.GenerateUtil;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.service.impl.CreateParkingLotCommandService;
import com.sunray.service.impl.ParkCommandService;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class CommandServiceTest {

    private ParkCommandService parkCommandService = new ParkCommandService();
    private CreateParkingLotCommandService createParkingLotCommandService = new CreateParkingLotCommandService();

    @Test
    public void CreateParkingLotTest() throws Exception{
        String firstParam = "create_parking_lot";
        String number = "10";
        String[] params = {firstParam, number};
        List<ParkSlot> parkSlots = createParkingLotCommandService.start(params);

        assertNotNull(parkSlots);
        assertEquals(number, parkSlots.size() + "");
    }

    @Test
    public void parkCommandTest(){
        String firstParam = "park";
        String carnumber = GenerateUtil.singaporeCarnumber();
        String color = ColorEnum.BLACK.value;
        String[] params = {firstParam, carnumber, color};
        try {
            ParkSlot parkSlot = parkCommandService.start(params);
            assertNotNull(parkSlot);
            assertEquals(carnumber, parkSlot.getCarNumber());
            assertEquals(color, parkSlot.getCarColor());
        } catch (Exception e) {
            assertTrue(e instanceof NoParkSlotException);
        }

    }




}
