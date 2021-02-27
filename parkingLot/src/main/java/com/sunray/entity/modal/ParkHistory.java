package com.sunray.entity.modal;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class ParkHistory {
    /**
     * id rule： timestamp_carNuber
     */
    private String id;
    private String slotNumber;
    private String carColor;
    private String carNuber;
    private Double cost;
    private Double discount;
    private Double finalCost;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date enterTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date leaveTime;
    private Long parkSecond;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getCarNuber() {
        return carNuber;
    }

    public void setCarNuber(String carNuber) {
        this.carNuber = carNuber;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Long getParkSecond() {
        return parkSecond;
    }

    public void setParkSecond(Long parkSecond) {
        this.parkSecond = parkSecond;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }
}
