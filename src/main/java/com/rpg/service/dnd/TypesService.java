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
import com.rpg.model.security.User;
import com.rpg.repository.dnd.types.ConditionsRepository;
import com.rpg.repository.dnd.types.DamageTypesRepository;
import com.rpg.repository.dnd.types.MagicSchoolsRepository;
import com.rpg.repository.dnd.types.WeaponPropertiesRepository;
import com.rpg.service.converter.DndDtoConverter;
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

    public Condition add(ConditionDto conditionDto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(conditionsRepository.existsByNameAndScenario(conditionDto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Condition condition = dtoConverter.fromDto(conditionDto, gm, scenario);
        return conditionsRepository.save(condition);
    }

    public List<Condition> findConditionsByNameContainingAndScenario(String name, Scenario scenario){
        List<Condition> list = new ArrayList<>(
                conditionsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(conditionsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Condition> findConditionsByScenario(Scenario scenario){
        List<Condition> list = new ArrayList<>(
                conditionsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(conditionsRepository.findByScenario(scenario));
        return list;
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
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
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
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
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
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
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
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
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
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
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
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
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

    public MagicSchool findMagicSchoolByNameAndScenario(String name, Scenario scenario) {
        if (scenario == null)
            return magicSchoolsRepository.findByNameAndScenario(name, null).orElse(null);
        return magicSchoolsRepository.findByNameAndScenario(name, null)
                .orElse(magicSchoolsRepository.findByNameAndScenario(name, scenario).orElse(null));
    }
    public WeaponProperty findWeaponPropertyByNameAndScenario(String name, Scenario scenario) {
        if (scenario == null)
            return weaponPropertiesRepository.findByNameAndScenario(name, null).orElse(null);
        return weaponPropertiesRepository.findByNameAndScenario(name, null)
                .orElse(weaponPropertiesRepository.findByNameAndScenario(name, scenario).orElse(null));
    }

    public DamageType findDamageTypeByNameAndScenario(String name, Scenario scenario) {
        if (scenario == null)
            return damageTypesRepository.findByNameAndScenario(name, null).orElse(null);
        return damageTypesRepository.findByNameAndScenario(name, null)
                .orElse(damageTypesRepository.findByNameAndScenario(name, scenario).orElse(null));
    }

    public Condition findConditionByName(String conditionName, Scenario scenario) {
        return conditionsRepository.findByNameAndScenario(conditionName, scenario);
    }

    public void delete(Condition condition) {
        conditionsRepository.delete(condition);
    }

    public void patchValues(Condition condition, ConditionDto conditionDto) {
        condition.setDescription(conditionDto.getDescription());
        condition.setVisible(conditionDto.isVisible());
        conditionsRepository.save(condition);
    }
}
