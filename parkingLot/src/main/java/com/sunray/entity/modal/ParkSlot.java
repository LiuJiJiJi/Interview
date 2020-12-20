package com.sunray.entity.modal;

import javax.validation.constraints.NotNull;

public class ParkSlot {
    private Integer id;
    @NotNull(message = "Car number cannot be null")
    private String carNumber;
    @NotNull(message = "Color cannot be null")
    private String color;

    public ParkSlot() {
    }

    public ParkSlot(Integer id, String carNumber, String color) {
        this.id = id;
        this.carNumber = carNumber;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
