package com.rpg.config.startup.essentialData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.repository.dnd.types.MagicSchoolsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Order(1)
public class TypesData implements ApplicationRunner {
    private Logger LOGGER = LogManager.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired private MagicSchoolsRepository magicSchoolsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (magicSchoolsRepository.count() == 0) populateMagicSchools();
    }

    private void populateMagicSchools(){
        TypeReference<List<MagicSchool>> typeReference = new TypeReference<List<MagicSchool>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/types/Magic-Schools.json");
        try {
            List<MagicSchool> list = mapper.readValue(inputStream,typeReference);
            magicSchoolsRepository.saveAll(list);
            LOGGER.info("MagicSchools table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate MagicSchools: " + e.getMessage());
        }
    }
}
