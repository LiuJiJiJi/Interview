package com.sunray.repository.redis;

import com.sunray.entity.modal.ParkSlot;
import com.sunray.repository.ParkSlotRepository;

import java.util.List;

public class RedisParkSlotRepository implements ParkSlotRepository {

    @Override
    public List<ParkSlot> search(ParkSlot parkSlot) {
        return null;
    }

    @Override
    public ParkSlot create(ParkSlot parkSlot) {

        return null;
    }

    @Override
    public ParkSlot update(ParkSlot parkSlot) {
        return null;
    }

    @Override
    public int delete(ParkSlot parkSlot) {
        return 0;
    }
}
