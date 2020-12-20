package com.sunray.repository;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.constant.DBConstant;
import com.sunray.common.util.GenerateUtil;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.redis.RedisParkSlotRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ParkSlotRepositoryTest {

    private ParkSlotRepository parkSlotRepository;

    @Before
    public void before() {
        switch (DBConstant.DB_TYPE) {
            case REDIS:
                parkSlotRepository = new RedisParkSlotRepository();
                break;
            case MYSQL:
                break;
            case MONGODB:
                break;
            default:
                parkSlotRepository = new RedisParkSlotRepository();
                break;
        }

    }

    @Test
    public void searchTest() {
        ParkSlot search = new ParkSlot();
        List<ParkSlot> parkSlots = parkSlotRepository.getAll(search);

        assertNotNull(parkSlots);
    }

    @Test
    public void createTest() {
        for (int i = 1; i < 7; i++) {
            ParkSlot parkSlot = new ParkSlot(i + "", GenerateUtil.carNumber(), ColorEnum.WHITE.value);
            parkSlotRepository.create(parkSlot);
        }

        ParkSlot parkSlot = new ParkSlot();
        List<ParkSlot> parkSlots = parkSlotRepository.getAll(parkSlot);
        assertEquals(6, parkSlots.size());
    }

    @Test
    public void updateTest() {
        String slotNumber = "8";
        String carNumber = GenerateUtil.carNumber();
        String carColor = ColorEnum.RED.value;

        String newCarNumber = "KA-01-HH-9999";
        String newColor = ColorEnum.purple.value;;

        ParkSlot parkSlot = new ParkSlot( slotNumber, carNumber, carColor);
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
        List<ParkSlot> parkSlots = parkSlotRepository.getAll(search);
        parkSlots.forEach((x) -> {
            parkSlotRepository.deleteBySlotNumber(x.getNumber());
        });

        parkSlots = parkSlotRepository.getAll(search);
        assertEquals(0, parkSlots.size());

    }

}
