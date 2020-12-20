package com.sunray.repository;

import com.sunray.entity.modal.ParkSlot;

import java.util.List;

public interface ParkSlotRepository {
    List<ParkSlot> search(ParkSlot parkSlot);
    ParkSlot create(ParkSlot parkSlot);
    ParkSlot update(ParkSlot parkSlot);
    int delete(ParkSlot parkSlot);
}
