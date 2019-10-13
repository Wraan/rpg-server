package com.rpg.config.startup.essentialData;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class EquipmentData implements ApplicationRunner {
    private Logger LOGGER = LogManager.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
