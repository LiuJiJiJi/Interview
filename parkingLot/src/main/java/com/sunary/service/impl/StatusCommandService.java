package com.sunary.service.impl;

import com.sunary.service.CommandService;

public class StatusCommandService implements CommandService {

    @Override
    public boolean validate(String[] params) {
        return false;
    }

    @Override
    public void run(String[] params) {
    }
}
