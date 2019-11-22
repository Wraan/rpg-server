package com.rpg.service.dnd;

import com.google.common.collect.Sets;
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
import java.util.Arrays;
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

    public Condition findConditionByNameAndScenario(String name, Scenario scenario) {
        Condition condition = conditionsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (condition == null) return conditionsRepository.findByNameAndScenario(name, null).orElse(null);
        return condition;
    }

    public List<Condition> findConditionsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Condition> list = new ArrayList<>(
                conditionsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(conditionsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Condition> findConditionsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Condition> list = new ArrayList<>(
                conditionsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(conditionsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Condition condition, ConditionDto conditionDto) {
        condition.setDescription(conditionDto.getDescription());
        condition.setVisible(conditionDto.isVisible());
        conditionsRepository.save(condition);
    }

    public void delete(Condition condition) {
        conditionsRepository.delete(condition);
    }

    public DamageType add(DamageTypeDto damageTypeDto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(damageTypesRepository.existsByNameAndScenario(damageTypeDto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        DamageType damageType = dtoConverter.fromDto(damageTypeDto, gm, scenario);
        return damageTypesRepository.save(damageType);
    }

    public List<DamageType> findDamageTypesByNameContainingAndScenario(String name, Scenario scenario){
        List<DamageType> list = new ArrayList<>(
                damageTypesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(damageTypesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<DamageType> findDamageTypesByScenario(Scenario scenario){
        List<DamageType> list = new ArrayList<>(
                damageTypesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(damageTypesRepository.findByScenario(scenario));
        return list;
    }

    public DamageType findDamageTypeByNameAndScenario(String name, Scenario scenario) {
        DamageType damageType = damageTypesRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (damageType == null) return damageTypesRepository.findByNameAndScenario(name, null).orElse(null);
        return damageType;
    }

    public List<DamageType> findDamageTypesByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<DamageType> list = new ArrayList<>(
                damageTypesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(damageTypesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<DamageType> findDamageTypesByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<DamageType> list = new ArrayList<>(
                damageTypesRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(damageTypesRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(DamageType damageType, DamageTypeDto dto) {
        damageType.setDescription(dto.getDescription());
        damageType.setVisible(dto.isVisible());
        damageTypesRepository.save(damageType);
    }

    public void delete(DamageType damageType) {
        damageTypesRepository.delete(damageType);
    }

    public MagicSchool add(MagicSchoolDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(magicSchoolsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        MagicSchool magicSchool = dtoConverter.fromDto(dto, gm, scenario);
        return magicSchoolsRepository.save(magicSchool);
    }

    public List<MagicSchool> findMagicSchoolsByNameContainingAndScenario(String name, Scenario scenario){
        List<MagicSchool> list = new ArrayList<>(
                magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<MagicSchool> findMagicSchoolsByScenario(Scenario scenario){
        List<MagicSchool> list = new ArrayList<>(
                magicSchoolsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(magicSchoolsRepository.findByScenario(scenario));
        return list;
    }

    public MagicSchool findMagicSchoolByNameAndScenario(String name, Scenario scenario) {
        MagicSchool magicSchool = magicSchoolsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (magicSchool == null) return magicSchoolsRepository.findByNameAndScenario(name, null).orElse(null);
        return magicSchool;
    }

    public List<MagicSchool> findMagicSchoolsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<MagicSchool> list = new ArrayList<>(
                magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(magicSchoolsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<MagicSchool> findMagicSchoolsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<MagicSchool> list = new ArrayList<>(
                magicSchoolsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(magicSchoolsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(MagicSchool magicSchool, MagicSchoolDto dto) {
        magicSchool.setDescription(dto.getDescription());
        magicSchool.setVisible(dto.isVisible());
        magicSchoolsRepository.save(magicSchool);
    }

    public void delete(MagicSchool magicSchool) {
        magicSchoolsRepository.delete(magicSchool);
    }

    public WeaponProperty add(WeaponPropertyDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(weaponPropertiesRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        WeaponProperty weaponProperty = dtoConverter.fromDto(dto, gm, scenario);
        return weaponPropertiesRepository.save(weaponProperty);
    }

    public List<WeaponProperty> findWeaponPropertiesByNameContainingAndScenario(String name, Scenario scenario){
        List<WeaponProperty> list = new ArrayList<>(
                weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<WeaponProperty> findWeaponPropertiesByScenario(Scenario scenario){
        List<WeaponProperty> list = new ArrayList<>(
                weaponPropertiesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(weaponPropertiesRepository.findByScenario(scenario));
        return list;
    }

    public WeaponProperty findWeaponPropertyByNameAndScenario(String name, Scenario scenario) {
        WeaponProperty weaponProperty = weaponPropertiesRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (weaponProperty == null) return weaponPropertiesRepository.findByNameAndScenario(name, null).orElse(null);
        return weaponProperty;
    }

    public List<WeaponProperty> findWeaponPropertiesByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<WeaponProperty> list = new ArrayList<>(
                weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(weaponPropertiesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<WeaponProperty> findWeaponPropertiesByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<WeaponProperty> list = new ArrayList<>(
                weaponPropertiesRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(weaponPropertiesRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(WeaponProperty weaponProperty, WeaponPropertyDto dto) {
        weaponProperty.setDescription(dto.getDescription());
        weaponProperty.setVisible(dto.isVisible());
        weaponPropertiesRepository.save(weaponProperty);
    }

    public void delete(WeaponProperty weaponProperty) {
        weaponPropertiesRepository.delete(weaponProperty);
    }


    public void deleteByScenario(Scenario scenario) {
        magicSchoolsRepository.deleteByScenario(scenario);
        conditionsRepository.deleteByScenario(scenario);
        damageTypesRepository.deleteByScenario(scenario);
        weaponPropertiesRepository.deleteByScenario(scenario);
    }
}
