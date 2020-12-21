package com.sunray.repository.redis;

import com.alibaba.fastjson.JSON;
import com.sunray.common.constant.CacheKey;
import com.sunray.common.util.JedisUtil;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkSlotRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisParkSlotRepository implements ParkSlotRepository {

    private final String redisKey = CacheKey.PARK_SLOT_KEY;

    @Override
    public List<ParkSlot> getFreeSlot() {
        List<ParkSlot> allParkSlots = getAll();
        List<ParkSlot> parkSlots = allParkSlots.stream().filter(x -> x.getCarNumber() == null).collect(Collectors.toList());
        return parkSlots;
    }

    @Override
    public ParkSlot getBySlotNumber(String slotNumber) {
        String parkSlotString = JedisUtil.hget(redisKey, slotNumber);
        ParkSlot parkSlot = JSON.parseObject(parkSlotString, ParkSlot.class);
        return parkSlot;
    }

    @Override
    public ParkSlot getByCarNumber(String carNumber) {
        List<ParkSlot> allParkSlots = getAll();
        List<ParkSlot> parkSlots = allParkSlots.stream().filter(x -> carNumber.equals(x.getCarNumber())).collect(Collectors.toList());
        if (parkSlots.size() < 1) {
            return null;
        }
        return parkSlots.get(0);
    }

    @Override
    public List<ParkSlot> getAll() {
        Map<String, String> parkSlotMap = JedisUtil.hgetAll(redisKey);
        List<ParkSlot> parkSlots = parkSlotMap.values().stream().map((x) -> JSON.parseObject(x, ParkSlot.class)).collect(Collectors.toList());
        parkSlots.sort(Comparator.comparing(ParkSlot::getId));
        return parkSlots;
    }

    @Override
    public ParkSlot create(ParkSlot parkSlot) {
        String jsonString = JSON.toJSONString(parkSlot);
        JedisUtil.hset(redisKey, parkSlot.getNumber(), jsonString);
        return parkSlot;
    }

    @Override
    public void update(ParkSlot parkSlot) {
        String jsonString = JSON.toJSONString(parkSlot);
        JedisUtil.hset(redisKey, parkSlot.getNumber(), jsonString);
    }

    @Override
    public void deleteBySlotNumber(String slotNumber) {
        JedisUtil.hdel(redisKey, slotNumber);
    }

    @Override
    public void deleteAll() {
        JedisUtil.delete(redisKey);
    }

}
