package com.rpg.service;

import com.rpg.dto.dnd.types.*;
import com.rpg.model.dnd.types.Condition;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DndDtoConverter {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;

    public Condition fromDto(ConditionDto conditionDto){
     return new Condition(conditionDto.getName(), conditionDto.getDescription(),
             userService.findByUsername(conditionDto.getCreatorName()),
             scenarioService.findByKey(conditionDto.getScenarioKey()));
    }

    public List<ConditionResponse> conditionsToResponse(List<Condition> conditions){
        List<ConditionResponse> list = new ArrayList<>();
        conditions.forEach(it ->{
            list.add(new ConditionResponse(it.getId(), it.getName(), it.getDescription(),
                    it.getCreator() != null ? it.getCreator().getUsername() : null,
                    it.getScenario() != null ? it.getScenario().getKey() : null));
        });
        return list;
    }

    public ConditionResponse conditionToResponse(Condition condition){
        return new ConditionResponse(condition.getId(), condition.getName(), condition.getDescription(),
                condition.getCreator().getUsername(), condition.getScenario().getKey());
    }

    public DamageType fromDto(DamageTypeDto damageTypeDto){
        return new DamageType(damageTypeDto.getName(), damageTypeDto.getDescription(),
                userService.findByUsername(damageTypeDto.getCreatorName()),
                scenarioService.findByKey(damageTypeDto.getScenarioKey()));
    }

    public List<DamageTypeResponse> damageTypesToResponse(List<DamageType> damageTypes){
        List<DamageTypeResponse> list = new ArrayList<>();
        damageTypes.forEach(it ->{
            list.add(new DamageTypeResponse(it.getId(), it.getName(), it.getDescription(),
                    it.getCreator() != null ? it.getCreator().getUsername() : null,
                    it.getScenario() != null ? it.getScenario().getKey() : null));
        });
        return list;
    }

    public DamageTypeResponse damageTypeToResponse(DamageType damageType){
        return new DamageTypeResponse(damageType.getId(), damageType.getName(), damageType.getDescription(),
                damageType.getCreator().getUsername(), damageType.getScenario().getKey());
    }

    public MagicSchool fromDto(MagicSchoolDto magicSchoolDto){
        return new MagicSchool(magicSchoolDto.getName(), magicSchoolDto.getDescription(),
                userService.findByUsername(magicSchoolDto.getCreatorName()),
                scenarioService.findByKey(magicSchoolDto.getScenarioKey()));
    }

    public List<MagicSchoolResponse> magicSchoolsToResponse(List<MagicSchool> magicSchools){
        List<MagicSchoolResponse> list = new ArrayList<>();
        magicSchools.forEach(it ->{
            list.add(new MagicSchoolResponse(it.getId(), it.getName(), it.getDescription(),
                    it.getCreator() != null ? it.getCreator().getUsername() : null,
                    it.getScenario() != null ? it.getScenario().getKey() : null));
        });
        return list;
    }

    public MagicSchoolResponse magicSchoolToResponse(MagicSchool magicSchool){
        return new MagicSchoolResponse(magicSchool.getId(), magicSchool.getName(), magicSchool.getDescription(),
                magicSchool.getCreator().getUsername(), magicSchool.getScenario().getKey());
    }

    public WeaponProperty fromDto(WeaponPropertyDto weaponPropertyDto){
        return new WeaponProperty(weaponPropertyDto.getName(), weaponPropertyDto.getDescription(),
                userService.findByUsername(weaponPropertyDto.getCreatorName()),
                scenarioService.findByKey(weaponPropertyDto.getScenarioKey()));
    }

    public List<WeaponPropertyResponse> weaponPropertiesToResponse(List<WeaponProperty> weaponProperties){
        List<WeaponPropertyResponse> list = new ArrayList<>();
        weaponProperties.forEach(it ->{
            list.add(new WeaponPropertyResponse(it.getId(), it.getName(), it.getDescription(),
                    it.getCreator() != null ? it.getCreator().getUsername() : null,
                    it.getScenario() != null ? it.getScenario().getKey() : null));
        });
        return list;
    }

    public WeaponPropertyResponse weaponPropertyToResponse(WeaponProperty weaponProperty){
        return new WeaponPropertyResponse(weaponProperty.getId(), weaponProperty.getName(), weaponProperty.getDescription(),
                weaponProperty.getCreator().getUsername(), weaponProperty.getScenario().getKey());
    }

}
