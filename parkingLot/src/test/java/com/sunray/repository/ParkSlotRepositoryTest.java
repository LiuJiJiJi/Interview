package com.sunray.repository;

import com.sunray.common.config.CommonBeanConfig;
import com.sunray.common.constant.ColorEnum;
import com.sunray.common.util.GenerateUtil;
import com.sunray.entity.modal.ParkSlot;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParkSlotRepositoryTest {

    private final ParkSlotRepository parkSlotRepository = CommonBeanConfig.parkSlotRepository;

    @After
    public void after() throws Exception {
        parkSlotRepository.deleteAll();
    }

    @Test
    public void searchTest() {
        ParkSlot search = new ParkSlot();
        List<ParkSlot> parkSlots = parkSlotRepository.getAll();

        assertNotNull(parkSlots);
    }

    @Test
    public void createTest() {
        parkSlotRepository.deleteAll();
        for (long i = 1; i < 7; i++) {
            ParkSlot parkSlot = new ParkSlot(i + "", GenerateUtil.singaporeCarnumber(), ColorEnum.WHITE.value);
            parkSlot.setId(i);
            parkSlotRepository.create(parkSlot);
        }

        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        assertEquals(6, parkSlots.size());
    }

    @Test
    public void updateTest() {
        String slotNumber = "8";
        String carNumber = GenerateUtil.singaporeCarnumber();
        String carColor = ColorEnum.RED.value;

        String newCarNumber = "KA-01-HH-9999";
        String newColor = ColorEnum.purple.value;
        ;

        ParkSlot parkSlot = new ParkSlot(slotNumber, carNumber, carColor);
        parkSlotRepository.create(parkSlot);

        parkSlot.setCarNumber(newCarNumber);
        parkSlot.setCarColor(newColor);
        parkSlotRepository.update(parkSlot);

        parkSlot = parkSlotRepository.getBySlotNumber(slotNumber);
        assertEquals(newCarNumber, parkSlot.getCarNumber());
        assertEquals(newColor, parkSlot.getCarColor());
    }

    @Test
    public void deleteAllTest() {
        ParkSlot search = new ParkSlot();
        List<ParkSlot> parkSlots = parkSlotRepository.getAll();
        parkSlots.forEach((x) -> {
            parkSlotRepository.deleteBySlotNumber(x.getNumber());
        });

        parkSlots = parkSlotRepository.getAll();
        assertEquals(0, parkSlots.size());
    }

}
