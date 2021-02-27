package com.sunray.repository;

import com.sunray.entity.modal.ParkSlot;

import java.util.List;

public interface ParkSlotRepository {
    List<ParkSlot> getFreeSlot();

    ParkSlot getBySlotNumber(String slotNumber);

    ParkSlot getByCarNumber(String carNumber);

    List<ParkSlot> getAll();

    ParkSlot create(ParkSlot parkSlot);

    void update(ParkSlot parkSlot);

    void deleteBySlotNumber(String slotNumber);

    void deleteAll();
}
