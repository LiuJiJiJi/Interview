package com.sunray.repository.redis;

import com.alibaba.fastjson.JSON;
import com.sunray.common.constant.CacheKey;
import com.sunray.common.util.JedisUtil;
import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkSlotRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisParkSlotRepository implements ParkSlotRepository {

    @Override
    public ParkSlot getBySlotNumber(String slotNumber) {
        String parkSlotString = JedisUtil.hget(CacheKey.PARK_SLOT_KEY, slotNumber);
        ParkSlot parkSlot = JSON.parseObject(parkSlotString, ParkSlot.class);
        return parkSlot;
    }

    @Override
    public List<ParkSlot> getAll(ParkSlot parkSlot) {
        Map<String, String> parkSlotMap = JedisUtil.hgetAll(CacheKey.PARK_SLOT_KEY);
        List<ParkSlot> parkSlots = parkSlotMap.values().stream().map((x) -> JSON.parseObject(x, ParkSlot.class)).collect(Collectors.toList());
        return parkSlots;
    }

    @Override
    public ParkSlot create(ParkSlot parkSlot) {
        String jsonString = JSON.toJSONString(parkSlot);
        JedisUtil.hset(CacheKey.PARK_SLOT_KEY, parkSlot.getNumber(), jsonString);
        return parkSlot;
    }

    @Override
    public void update(ParkSlot parkSlot) {
        String jsonString = JSON.toJSONString(parkSlot);
        JedisUtil.hset(CacheKey.PARK_SLOT_KEY, parkSlot.getNumber(), jsonString);
    }

    @Override
    public void deleteBySlotNumber(String slotNumber) {
        JedisUtil.hdel(CacheKey.PARK_SLOT_KEY, slotNumber);
    }

}
