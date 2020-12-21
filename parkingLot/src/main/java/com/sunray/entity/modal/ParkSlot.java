package com.sunray.entity.modal;

import javax.validation.constraints.NotNull;

public class ParkSlot {

    /**
     * @Description: number rule： 1, 2, 3, 4 ,5 ,6 .....
     */
    private Long id;
    /**
     * @Description: number rule：
     *   rule one: 1, 2, 3, 4 ,5 ,6 .....
     *   rule two: 1A, 1B, 2A, 2B, 3A, 3B .....
     */
    @NotNull(message = "slot id cannot be null")
    private String number;
    @NotNull(message = "Car number cannot be null")
    private String carNumber;
    @NotNull(message = "Color cannot be null")
    private String carColor;

    public ParkSlot() {
    }

    public ParkSlot(String number) {
        this.number = number;
    }

    public ParkSlot(String number, String carNumber, String carColor) {
        this.number = number;
        this.carNumber = carNumber;
        this.carColor = carColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
}
