package com.rpg.service.dnd;

import com.rpg.dto.dnd.types.ConditionDto;
import com.rpg.dto.dnd.types.DamageTypeDto;
import com.rpg.dto.dnd.types.MagicSchoolDto;
import com.rpg.dto.dnd.types.WeaponPropertyDto;
import com.rpg.exception.NameExistsInScenarioException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.Condition;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.repository.dnd.types.ConditionsRepository;
import com.rpg.repository.dnd.types.DamageTypesRepository;
import com.rpg.repository.dnd.types.MagicSchoolsRepository;
import com.rpg.repository.dnd.types.WeaponPropertiesRepository;
import com.rpg.service.DndDtoConverter;
import com.rpg.service.application.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypesService {

    @Autowired private MagicSchoolsRepository magicSchoolsRepository;
    @Autowired private ConditionsRepository conditionsRepository;
    @Autowired private DamageTypesRepository damageTypesRepository;
    @Autowired private WeaponPropertiesRepository weaponPropertiesRepository;

    @Autowired private DndDtoConverter dtoConverter;
    @Autowired private ScenarioService scenarioService;

    public Condition save(ConditionDto conditionDto) throws Exception {
        Condition condition = dtoConverter.fromDto(conditionDto);
        if(condition.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(condition.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(conditionsRepository.existsByNameAndScenario(condition.getName(), condition.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return conditionsRepository.save(condition);
    }

    public List<Condition> findConditionsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<Condition> list = new ArrayList<>(
                conditionsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(conditionsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Condition> findConditionsByNameContaining(String name){
        return conditionsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Condition> findConditionsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<Condition> list = new ArrayList<>(
                conditionsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(conditionsRepository.findByScenario(scenario));
        return list;
    }

    public List<Condition> findConditions(){
        return conditionsRepository.findByScenario(null);
    }
    public Condition findConditionById(long id){
        return conditionsRepository.findById(id).orElse(null);
    }

    public DamageType save(DamageTypeDto damageTypeDto) throws Exception {
        DamageType damageType = dtoConverter.fromDto(damageTypeDto);
        if(damageType.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(damageType.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(damageTypesRepository.existsByNameAndScenario(damageType.getName(), damageType.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return damageTypesRepository.save(damageType);
    }

    public List<DamageType> findDamageTypesByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<DamageType> list = new ArrayList<>(
                damageTypesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(damageTypesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<DamageType> findDamageTypesByNameContaining(String name){
        return damageTypesRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<DamageType> findDamageTypesByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<DamageType> list = new ArrayList<>(
                damageTypesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(damageTypesRepository.findByScenario(scenario));
        return list;
    }

    public List<DamageType> findDamageTypes(){
        return damageTypesRepository.findByScenario(null);
    }
    public DamageType findDamageTypeById(long id){
        return damageTypesRepository.findById(id).orElse(null);
    }

    public MagicSchool save(MagicSchoolDto magicSchoolDto) throws Exception {
        MagicSchool magicSchool = dtoConverter.fromDto(magicSchoolDto);
        if(magicSchool.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(magicSchool.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(magicSchoolsRepository.existsByNameAndScenario(magicSchool.getName(), magicSchool.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return magicSchoolsRepository.save(magicSchool);
    }

    public List<MagicSchool> findMagicSchoolsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<MagicSchool> list = new ArrayList<>(
                magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<MagicSchool> findMagicSchoolsByNameContaining(String name){
        return magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<MagicSchool> findMagicSchoolsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<MagicSchool> list = new ArrayList<>(
                magicSchoolsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(magicSchoolsRepository.findByScenario(scenario));
        return list;
    }

    public List<MagicSchool> findMagicSchools(){
        return magicSchoolsRepository.findByScenario(null);
    }
    public MagicSchool findMagicSchoolById(long id){
        return magicSchoolsRepository.findById(id).orElse(null);
    }

    public WeaponProperty save(WeaponPropertyDto weaponPropertyDto) throws Exception {
        WeaponProperty weaponProperty = dtoConverter.fromDto(weaponPropertyDto);
        if(weaponProperty.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(weaponProperty.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(weaponPropertiesRepository.existsByNameAndScenario(weaponProperty.getName(), weaponProperty.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return weaponPropertiesRepository.save(weaponProperty);
    }

    public List<WeaponProperty> findWeaponPropertiesByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<WeaponProperty> list = new ArrayList<>(
                weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<WeaponProperty> findWeaponPropertiesByNameContaining(String name){
        return weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<WeaponProperty> findWeaponPropertiesByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByKey(scenarioKey);
        List<WeaponProperty> list = new ArrayList<>(
                weaponPropertiesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(weaponPropertiesRepository.findByScenario(scenario));
        return list;
    }

    public List<WeaponProperty> findWeaponProperties(){
        return weaponPropertiesRepository.findByScenario(null);
    }
    public WeaponProperty findWeaponPropertyById(long id){
        return weaponPropertiesRepository.findById(id).orElse(null);
    }

}
