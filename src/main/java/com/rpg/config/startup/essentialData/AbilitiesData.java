package com.rpg.config.startup.essentialData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.dto.dnd.SpellJson;
import com.rpg.model.dnd.abilities.*;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.repository.dnd.abilities.*;
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
import java.util.ArrayList;
import java.util.List;

@Component
@Order(3)
public class AbilitiesData implements ApplicationRunner {
    private Logger LOGGER = LogManager.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired private SkillsRepository skillsRepository;
    @Autowired private TraitsRepository traitsRepository;
    @Autowired private FeaturesRepository featuresRepository;
    @Autowired private ProficienciesRepository proficienciesRepository;
    @Autowired private LanguagesRepository languagesRepository;
    @Autowired private SpellsRepository spellsRepository;

    @Autowired private MagicSchoolsRepository magicSchoolsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (skillsRepository.count() == 0) populateSkills();
        if (traitsRepository.count() == 0) populateTraits();
        if (featuresRepository.count() == 0 ) populateFeatures();
        if (proficienciesRepository.count() == 0) populateProficiencies();
        if (languagesRepository.count() == 0 ) populateLanguages();
        if (spellsRepository.count() == 0) populateSpells();

    }

    private void populateSkills(){
        TypeReference<List<Skill>> typeReference = new TypeReference<List<Skill>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/abilities/Skills.json");
        try {
            List<Skill> list = mapper.readValue(inputStream,typeReference);
            skillsRepository.saveAll(list);
            LOGGER.info("Skills table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Skills: " + e.getMessage());
        }
    }

    private void populateTraits(){
        TypeReference<List<Trait>> typeReference = new TypeReference<List<Trait>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/abilities/Traits.json");
        try {
            List<Trait> list = mapper.readValue(inputStream,typeReference);
            traitsRepository.saveAll(list);
            LOGGER.info("Traits table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Traits: " + e.getMessage());
        }
    }

    private void populateFeatures(){
        TypeReference<List<Feature>> typeReference = new TypeReference<List<Feature>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/abilities/Features.json");
        try {
            List<Feature> list = mapper.readValue(inputStream,typeReference);
            featuresRepository.saveAll(list);
            LOGGER.info("Features table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Features: " + e.getMessage());
        }
    }

    private void populateSpells(){
        TypeReference<List<SpellJson>> typeReference = new TypeReference<List<SpellJson>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/abilities/Spells.json");
        try {
            List<SpellJson> list = mapper.readValue(inputStream,typeReference);
            List<Spell> spells = new ArrayList<>();

            list.forEach(it -> {
                MagicSchool ms = magicSchoolsRepository.findById(it.getSchool().getId()).get();
                spells.add(new Spell(it.getId(), it.getName(), it.getDescription(), it.getHigherLevels(),
                        it.getLevel(), it.getRange(), it.getComponents(), it.getMaterial(), it.isRitual(),
                        it.getDuration(), it.isConcentration(), it.getCastingTime(), ms));
            });

            spellsRepository.saveAll(spells);
            LOGGER.info("Spells table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Spells: " + e.getMessage());
        }
    }

    private void populateLanguages(){
        TypeReference<List<Language>> typeReference = new TypeReference<List<Language>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/abilities/Languages.json");
        try {
            List<Language> list = mapper.readValue(inputStream,typeReference);
            languagesRepository.saveAll(list);
            LOGGER.info("Languages table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Languages: " + e.getMessage());
        }
    }

    private void populateProficiencies(){
        TypeReference<List<Proficiency>> typeReference = new TypeReference<List<Proficiency>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/abilities/Proficiencies.json");
        try {
            List<Proficiency> list = mapper.readValue(inputStream,typeReference);
            proficienciesRepository.saveAll(list);
            LOGGER.info("Proficiencies table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Proficiencies: " + e.getMessage());
        }
    }


}
