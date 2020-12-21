package com.sunray.repository;

import com.sunray.entity.modal.ParkSlot;

import java.util.List;

public interface ParkSlotRepository {
    ParkSlot getBySlotNumber(String slotNumber);
    List<ParkSlot> getAll();
    ParkSlot create(ParkSlot parkSlot);
    void update(ParkSlot parkSlot);
    void deleteBySlotNumber(String slotNumber);
}
