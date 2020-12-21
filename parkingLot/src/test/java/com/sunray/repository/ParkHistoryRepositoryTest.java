package com.sunray.repository;

import com.sunray.common.constant.ColorEnum;
import com.sunray.common.constant.DBConstant;
import com.sunray.common.util.GenerateUtil;
import com.sunray.entity.modal.ParkHistory;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.redis.RedisParkHistoryRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ParkHistoryRepositoryTest {

    private ParkHistoryRepository parkHistoryRepository;

    @Before
    public void before() {
        switch (DBConstant.DB_TYPE) {
            case REDIS:
                parkHistoryRepository = new RedisParkHistoryRepository();
                break;
            case MYSQL:
                break;
            case MONGODB:
                break;
            default:
                parkHistoryRepository = new RedisParkHistoryRepository();
                break;
        }

    }

    @Test
    public void getAllTest() {
        List<ParkHistory> parkHistories = parkHistoryRepository.getAll();
        assertNotNull(parkHistories);
    }

    @Test
    public void getTest() {
        String slotNumber = "1";
        String carNumber = GenerateUtil.singaporeCarnumber();
        String carColor = ColorEnum.WHITE.value;
        ParkSlot parkSlot = new ParkSlot(slotNumber, carNumber, carColor);

        ParkHistory parkHistory = new ParkHistory();
        parkHistory.setCarNuber(parkSlot.getCarNumber());
        parkHistory.setSlotNumber(parkSlot.getNumber());
        parkHistory.setCarColor(parkSlot.getCarColor());
        parkHistory.setEnterTime(new Date());
        parkHistoryRepository.create(parkHistory);

        List<ParkHistory> parkHistories = parkHistoryRepository.getByCarNumber(carNumber);
        assertTrue(parkHistories.size() >= 1);

        parkHistories = parkHistoryRepository.getBySlotNumber(slotNumber);
        assertTrue(parkHistories.size() >= 1);

        parkHistory = parkHistoryRepository.getLastHistoryByCarNumber(carNumber);
        assertNotNull(parkHistory);
    }

    @Test
    public void createTest() {
        String slotNumber = "1";
        String carNumber = GenerateUtil.singaporeCarnumber();
        String carColor = ColorEnum.WHITE.value;
        ParkSlot parkSlot = new ParkSlot(slotNumber, carNumber, carColor);

        ParkHistory parkHistory = new ParkHistory();
        parkHistory.setCarNuber(parkSlot.getCarNumber());
        parkHistory.setSlotNumber(parkSlot.getNumber());
        parkHistory.setCarColor(parkSlot.getCarColor());
        parkHistory.setEnterTime(new Date());
        parkHistory = parkHistoryRepository.create(parkHistory);

        assertEquals(slotNumber, parkHistory.getSlotNumber());
        assertEquals(carNumber, parkHistory.getCarNuber());
        assertEquals(carColor, parkHistory.getCarColor());

    }

    @Test
    public void updateTest() throws Exception{
        String slotNumber = "3";
        String carNumber = GenerateUtil.singaporeCarnumber();
        String carColor = ColorEnum.WHITE.value;
        ParkSlot parkSlot = new ParkSlot(slotNumber, carNumber, carColor);

        ParkHistory parkHistory = new ParkHistory();
        parkHistory.setCarNuber(parkSlot.getCarNumber());
        parkHistory.setSlotNumber(parkSlot.getNumber());
        parkHistory.setCarColor(parkSlot.getCarColor());
        parkHistory.setEnterTime(new Date());
        parkHistory = parkHistoryRepository.create(parkHistory);

        Thread.sleep(2000L);
        Date leaveTime = new Date();
        parkHistory.setLeaveTime(leaveTime);
        Long parkSecond = (parkHistory.getLeaveTime().getTime() - parkHistory.getEnterTime().getTime())/1000;
        parkHistory.setParkSecond(parkSecond);
        parkHistory.setCost(10.0);
        parkHistory.setDiscount(10.0);
        parkHistory.setFinalCost(parkHistory.getCost() - parkHistory.getDiscount());
        parkHistoryRepository.update(parkHistory);


        parkHistory = parkHistoryRepository.getLastHistoryByCarNumber(carNumber);
        assertNotNull(parkHistory);
        assertEquals(slotNumber, parkHistory.getSlotNumber());
        assertEquals(carNumber, parkHistory.getCarNuber());
        assertEquals(carColor, parkHistory.getCarColor());
        assertEquals("10.0", parkHistory.getCost().toString());
        assertEquals("10.0", parkHistory.getDiscount().toString());
        assertEquals("0.0", parkHistory.getFinalCost().toString());
        assertEquals(parkSecond, parkHistory.getParkSecond());
        assertEquals(leaveTime.getTime()/1000, parkHistory.getLeaveTime().getTime()/1000);
    }

}
