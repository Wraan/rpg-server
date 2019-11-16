package com.rpg.config.startup.essentialData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.model.dnd.types.Condition;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.repository.dnd.types.ConditionsRepository;
import com.rpg.repository.dnd.types.DamageTypesRepository;
import com.rpg.repository.dnd.types.MagicSchoolsRepository;
import com.rpg.repository.dnd.types.WeaponPropertiesRepository;
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
    @Autowired private ConditionsRepository conditionsRepository;
    @Autowired private DamageTypesRepository damageTypesRepository;
    @Autowired private WeaponPropertiesRepository weaponPropertiesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (magicSchoolsRepository.count() == 0) populateMagicSchools();
        if (conditionsRepository.count() == 0) populateConditions();
        if (damageTypesRepository.count() == 0) populateDamageTypes();
        if (weaponPropertiesRepository.count() == 0) populateWeaponProperties();
    }

    private void populateMagicSchools(){
        TypeReference<List<MagicSchool>> typeReference = new TypeReference<List<MagicSchool>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/types/Magic-Schools.json");
        try {
            List<MagicSchool> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            magicSchoolsRepository.saveAll(list);
            LOGGER.info("MagicSchools table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate MagicSchools: " + e.getMessage());
        }
    }

    private void populateConditions(){
        TypeReference<List<Condition>> typeReference = new TypeReference<List<Condition>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/types/Conditions.json");
        try {
            List<Condition> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            conditionsRepository.saveAll(list);
            LOGGER.info("Conditions table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Conditions: " + e.getMessage());
        }
    }

    private void populateDamageTypes(){
        TypeReference<List<DamageType>> typeReference = new TypeReference<List<DamageType>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/types/Damage-Types.json");
        try {
            List<DamageType> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            damageTypesRepository.saveAll(list);
            LOGGER.info("DamageTypes table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate DamageTypes: " + e.getMessage());
        }
    }

    private void populateWeaponProperties(){
        TypeReference<List<WeaponProperty>> typeReference = new TypeReference<List<WeaponProperty>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/types/Weapon-Properties.json");
        try {
            List<WeaponProperty> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            weaponPropertiesRepository.saveAll(list);
            LOGGER.info("WeaponProperties table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate WeaponProperties: " + e.getMessage());
        }
    }
}
