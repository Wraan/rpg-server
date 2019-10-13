package com.rpg.config.essentialData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.model.dnd.Skill;
import com.rpg.repository.dnd.SkillRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class StartupDndEssentialData implements ApplicationRunner {
    private Logger LOGGER = LogManager.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(skillRepository.count() == 0) {
            LOGGER.info("Skills table is empty. Populating the table.");
            populateSkills();
        }


    }

    private void populateSkills(){
        TypeReference<List<Skill>> typeReference = new TypeReference<List<Skill>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/Skills.json");
        try {
            List<Skill> skills = mapper.readValue(inputStream,typeReference);
            skillRepository.saveAll(skills);
            LOGGER.info("Skills table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Skills: " + e.getMessage());
        }
    }


}
