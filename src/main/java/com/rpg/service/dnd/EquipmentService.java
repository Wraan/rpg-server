package com.rpg.service.dnd;

import com.rpg.dto.dnd.equipment.*;
import com.rpg.exception.NameExistsInScenarioException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.TypeDoesNotExist;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.*;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.repository.dnd.equipment.*;
import com.rpg.service.DndDtoConverter;
import com.rpg.service.application.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired private ArmorsRepository armorsRepository;
    @Autowired private GearRepository gearRepository;
    @Autowired private ToolsRepository toolsRepository;
    @Autowired private VehiclesRepository vehiclesRepository;
    @Autowired private WeaponsRepository weaponsRepository;

    @Autowired private DndDtoConverter dtoConverter;
    @Autowired private ScenarioService scenarioService;

    public Weapon save(WeaponDto dto) throws Exception {
        Weapon it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(it.getDamageType() == null)
            throw new TypeDoesNotExist("DamageType does not exist");
        for(WeaponProperty property : it.getProperties()){
            if(property == null) throw new TypeDoesNotExist("WeaponProperty does not exist");
        }

        if(weaponsRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return weaponsRepository.save(it);
    }

    public List<Weapon> findWeaponsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Weapon> list = new ArrayList<>(
                weaponsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(weaponsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Weapon> findWeaponsByNameContaining(String name){
        return weaponsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Weapon> findWeaponsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Weapon> list = new ArrayList<>(
                weaponsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(weaponsRepository.findByScenario(scenario));
        return list;
    }

    public List<Weapon> findWeapons(){
        return weaponsRepository.findByScenario(null);
    }
    public Weapon findWeaponById(long id){
        return weaponsRepository.findById(id).orElse(null);
    }

    public Armor save(ArmorDto dto) throws Exception {
        Armor it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(armorsRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return armorsRepository.save(it);
    }

    public List<Armor> findArmorsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Armor> list = new ArrayList<>(
                armorsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(armorsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Armor> findArmorsByNameContaining(String name){
        return armorsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Armor> findArmorsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Armor> list = new ArrayList<>(
                armorsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(armorsRepository.findByScenario(scenario));
        return list;
    }

    public List<Armor> findArmors(){
        return armorsRepository.findByScenario(null);
    }
    public Armor findArmorById(long id){
        return armorsRepository.findById(id).orElse(null);
    }

    public Gear save(GearDto dto) throws Exception {
        Gear it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(gearRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return gearRepository.save(it);
    }

    public List<Gear> findGearByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Gear> list = new ArrayList<>(
                gearRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(gearRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Gear> findGearByNameContaining(String name){
        return gearRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Gear> findGearByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Gear> list = new ArrayList<>(
                gearRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(gearRepository.findByScenario(scenario));
        return list;
    }

    public List<Gear> findGear(){
        return gearRepository.findByScenario(null);
    }
    public Gear findGearById(long id){
        return gearRepository.findById(id).orElse(null);
    }

    public Tool save(ToolDto dto) throws Exception {
        Tool it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(toolsRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return toolsRepository.save(it);
    }

    public List<Tool> findToolsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Tool> list = new ArrayList<>(
                toolsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(toolsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Tool> findToolsByNameContaining(String name){
        return toolsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Tool> findToolsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Tool> list = new ArrayList<>(
                toolsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(toolsRepository.findByScenario(scenario));
        return list;
    }

    public List<Tool> findTools(){
        return toolsRepository.findByScenario(null);
    }
    public Tool findToolById(long id){
        return toolsRepository.findById(id).orElse(null);
    }

    public Vehicle save(VehicleDto dto) throws Exception {
        Vehicle it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(vehiclesRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return vehiclesRepository.save(it);
    }

    public List<Vehicle> findVehiclesByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Vehicle> list = new ArrayList<>(
                vehiclesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(vehiclesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Vehicle> findVehiclesByNameContaining(String name){
        return vehiclesRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Vehicle> findVehiclesByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Vehicle> list = new ArrayList<>(
                vehiclesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(vehiclesRepository.findByScenario(scenario));
        return list;
    }

    public List<Vehicle> findVehicles(){
        return vehiclesRepository.findByScenario(null);
    }
    public Vehicle findVehicleById(long id){
        return vehiclesRepository.findById(id).orElse(null);
    }
}
