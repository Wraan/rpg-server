package com.rpg.controller.dnd;

import com.rpg.model.dnd.abilities.Spell;
import com.rpg.model.dnd.types.Condition;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.repository.dnd.types.ConditionsRepository;
import com.rpg.repository.dnd.types.DamageTypesRepository;
import com.rpg.repository.dnd.types.MagicSchoolsRepository;
import com.rpg.repository.dnd.types.WeaponPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TypesController {

    @Autowired private MagicSchoolsRepository magicSchoolsRepository;
    @Autowired private ConditionsRepository conditionsRepository;
    @Autowired private DamageTypesRepository damageTypesRepository;
    @Autowired private WeaponPropertiesRepository weaponPropertiesRepository;

    @GetMapping("/magicSchool/{id}")
    public MagicSchool getMagicSchoolById(@PathVariable("id") long id) {
        return magicSchoolsRepository.findById(id).get();
    }

    @GetMapping("/magicSchool")
    public List<MagicSchool> getMagicSchoolsByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return magicSchoolsRepository.findByNameIgnoreCaseContaining(name.get());
        return magicSchoolsRepository.findAll();
    }

    @GetMapping("/condition/{id}")
    public Condition getConditionById(@PathVariable("id") long id) {
        return conditionsRepository.findById(id).get();
    }

    @GetMapping("/condition")
    public List<Condition> getConditionsByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return conditionsRepository.findByNameIgnoreCaseContaining(name.get());
        return conditionsRepository.findAll();
    }

    @GetMapping("/damageType/{id}")
    public DamageType getDamageTypeById(@PathVariable("id") long id) {
        return damageTypesRepository.findById(id).get();
    }

    @GetMapping("/damageType")
    public List<DamageType> getDamageTypesByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return damageTypesRepository.findByNameIgnoreCaseContaining(name.get());
        return damageTypesRepository.findAll();
    }

    @GetMapping("/weaponProperty/{id}")
    public WeaponProperty getWeaponPropertyById(@PathVariable("id") long id) {
        return weaponPropertiesRepository.findById(id).get();
    }

    @GetMapping("/weaponProperty")
    public List<WeaponProperty> getWeaponPropertiesByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return weaponPropertiesRepository.findByNameIgnoreCaseContaining(name.get());
        return weaponPropertiesRepository.findAll();
    }
}
