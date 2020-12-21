package com.sunray.repository.redis;

import com.alibaba.fastjson.JSON;
import com.sunray.common.constant.CacheKey;
import com.sunray.common.util.JedisUtil;
import com.sunray.entity.modal.ParkHistory;
import com.sunray.repository.ParkHistoryRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisParkHistoryRepository implements ParkHistoryRepository {

    private final String redisKey = CacheKey.PARK_HISTORY_KEY;

    @Override
    public ParkHistory getLastHistoryByCarNumber(String carNumber) {
        List<ParkHistory> parkHistories = getByCarNumber(carNumber);
        if (parkHistories.size() == 0) {
            return null;
        }
        return parkHistories.get(0);
    }

    @Override
    public List<ParkHistory> getBySlotNumber(String slotNumber) {
        List<ParkHistory> allParkHistories = getAll();
        List<ParkHistory> parkHistories = allParkHistories.stream().filter(x -> x.getSlotNumber().equals(slotNumber)).collect(Collectors.toList());
        return parkHistories;
    }


    @Override
    public List<ParkHistory> getByCarNumber(String carNumber) {
        List<ParkHistory> allParkHistories = getAll();
        List<ParkHistory> parkHistories = allParkHistories.stream().filter(x -> x.getCarNuber().equals(carNumber)).collect(Collectors.toList());
        return parkHistories;
    }

    @Override
    public List<ParkHistory> getAll() {
        Map<String, String> parkSlotMap = JedisUtil.hgetAll(redisKey);
        List<ParkHistory> parkHistories = parkSlotMap.values().stream().map((x) -> JSON.parseObject(x, ParkHistory.class)).collect(Collectors.toList());
        parkHistories.sort((a, b) -> b.getEnterTime().compareTo(a.getEnterTime()));
        return parkHistories;
    }

    @Override
    public ParkHistory create(ParkHistory parkHistory) {
        parkHistory.setId(parkHistory.getEnterTime().getTime() + "_" + parkHistory.getCarNuber());
        String jsonString = JSON.toJSONString(parkHistory);
        JedisUtil.hset(redisKey, parkHistory.getId(), jsonString);
        return parkHistory;
    }

    @Override
    public void update(ParkHistory parkHistory) {
        String id = parkHistory.getId();
        String jsonString = JSON.toJSONString(parkHistory);
        JedisUtil.hset(redisKey, parkHistory.getId(), jsonString);
    }
}
