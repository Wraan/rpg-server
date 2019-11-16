package com.rpg.config.startup.essentialData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpg.dto.dnd.equipment.ArmorJson;
import com.rpg.dto.dnd.equipment.WeaponJson;
import com.rpg.model.dnd.equipment.*;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.repository.dnd.equipment.*;
import com.rpg.repository.dnd.types.DamageTypesRepository;
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
import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class EquipmentData implements ApplicationRunner {
    private Logger LOGGER = LogManager.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired private ArmorsRepository armorsRepository;
    @Autowired private GearRepository gearRepository;
    @Autowired private ToolsRepository toolsRepository;
    @Autowired private VehiclesRepository vehiclesRepository;
    @Autowired private WeaponsRepository weaponsRepository;

    @Autowired private DamageTypesRepository damageTypesRepository;
    @Autowired private WeaponPropertiesRepository weaponPropertiesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (armorsRepository.count() == 0) populateArmors();
        if (weaponsRepository.count() == 0) populateWeapons();
        if (gearRepository.count() == 0) populateGear();
        if (toolsRepository.count() == 0) populateToolss();
        if (vehiclesRepository.count() == 0) populateVehicles();
    }

    private void populateArmors(){
        TypeReference<List<ArmorJson>> typeReference = new TypeReference<List<ArmorJson>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/equipment/Armors.json");
        try {
            List<ArmorJson> list = mapper.readValue(inputStream,typeReference);
            List<Armor> armors = new ArrayList<>();

            list.forEach(it -> {
                ArmorClass armorClass = new ArmorClass(it.getArmorClass().getBase(), it.getArmorClass().isDexBonus(),
                        it.getArmorClass().getMaxBonus());
                armors.add(new Armor(it.getName(), armorClass, it.getStrMinimum(), it.isStealthDisadvantage(),
                        it.getWeight(), it.getCost()));
            });

            armors.forEach(it -> it.setVisible(true));
            armorsRepository.saveAll(armors);
            LOGGER.info("Armors table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Armors: " + e.getMessage());
        }
    }

    private void populateWeapons(){
        TypeReference<List<WeaponJson>> typeReference = new TypeReference<List<WeaponJson>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/equipment/Weapons.json");
        try {
            List<WeaponJson> list = mapper.readValue(inputStream,typeReference);
            List<Weapon> weapons = new ArrayList<>();

            list.forEach(it -> {
                DamageType damageType = damageTypesRepository.findById(it.getDamageType().getId()).get();
                List<WeaponProperty> properties = new ArrayList<>();
                it.getProperties().forEach(property ->
                    properties.add(weaponPropertiesRepository.findById(property.getId()).get()));

                weapons.add(new Weapon(it.getName(), it.getCategory(), it.getWeaponRange(), it.getDamageDice(),
                        it.getDamageBonus(), damageType, it.getNormalRange(), it.getLongRange(),
                        it.getNormalThrowRange(), it.getLongThrowRange(), properties, it.getWeight(), it.getCost()));
            });

            weapons.forEach(it -> it.setVisible(true));
            weaponsRepository.saveAll(weapons);
            LOGGER.info("Weapons table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Weapons: " + e.getMessage());
        }
    }

    private void populateGear(){
        TypeReference<List<Gear>> typeReference = new TypeReference<List<Gear>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/equipment/Gear.json");
        try {
            List<Gear> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            gearRepository.saveAll(list);
            LOGGER.info("Gear table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Gear: " + e.getMessage());
        }
    }

    private void populateToolss(){
        TypeReference<List<Tool>> typeReference = new TypeReference<List<Tool>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/equipment/Tools.json");
        try {
            List<Tool> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            toolsRepository.saveAll(list);
            LOGGER.info("Tools table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Tools: " + e.getMessage());
        }
    }

    private void populateVehicles(){
        TypeReference<List<Vehicle>> typeReference = new TypeReference<List<Vehicle>>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/equipment/Vehicles.json");
        try {
            List<Vehicle> list = mapper.readValue(inputStream,typeReference);
            list.forEach(it -> it.setVisible(true));
            vehiclesRepository.saveAll(list);
            LOGGER.info("Vehicles table has been populated with essential data.");
        } catch (IOException e){
            LOGGER.info("Unable to populate Vehicles: " + e.getMessage());
        }
    }
}
