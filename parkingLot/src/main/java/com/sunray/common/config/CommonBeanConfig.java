package com.sunray.common.config;

import com.sunray.common.constant.DBConstant;
import com.sunray.repository.ParkHistoryRepository;
import com.sunray.repository.ParkSlotRepository;
import com.sunray.repository.redis.RedisParkHistoryRepository;
import com.sunray.repository.redis.RedisParkSlotRepository;

public class CommonBeanConfig {

    public static ParkSlotRepository parkSlotRepository;
    public static ParkHistoryRepository parkHistoryRepository;
    static {
        switch (DBConstant.DB_TYPE) {
            case MYSQL:
                throw new RuntimeException("MYSQL Not support");
            case MONGODB:
                throw new RuntimeException("MONGODB Not support.");
            default:
                parkSlotRepository = new RedisParkSlotRepository();
                parkHistoryRepository = new RedisParkHistoryRepository();
                break;
        }
    }

}
