package com.sunray.repository;

import com.sunray.entity.modal.ParkHistory;

import java.util.List;

public interface ParkHistoryRepository {
    ParkHistory getLastHistoryByCarNumber(String carNumber);
    List<ParkHistory> getByCarNumber(String carNumber);
    List<ParkHistory> getBySlotNumber(String slotNumber);
    List<ParkHistory> getAll();
    ParkHistory create(ParkHistory parkHistory);
    void update(ParkHistory parkHistory);
}
