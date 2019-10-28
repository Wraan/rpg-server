package com.rpg.controller.dnd;

import com.rpg.dto.dnd.types.*;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TypesController {

    @Autowired private TypesService typesService;
    @Autowired private DndDtoConverter dtoConverter;

    @GetMapping("/magicSchool/{id}")
    public MagicSchoolResponse getMagicSchoolById(@PathVariable("id") long id) {
        return dtoConverter.magicSchoolToResponse(typesService.findMagicSchoolById(id));
    }

    @GetMapping("/magicSchool")
    public List<MagicSchoolResponse> getMagicSchoolsByName(@RequestParam(value = "name") Optional<String> name,
                                                           @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.magicSchoolsToResponse(typesService.findMagicSchoolsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.magicSchoolsToResponse(typesService.findMagicSchoolsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.magicSchoolsToResponse(typesService.findMagicSchoolsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.magicSchoolsToResponse(typesService.findMagicSchools());
    }

    @GetMapping("/condition/{id}")
    public ConditionResponse getConditionById(@PathVariable("id") long id) {
        return dtoConverter.conditionToResponse(typesService.findConditionById(id));
    }

    @GetMapping("/condition")
    public List<ConditionResponse> getConditionsByName(@RequestParam(value = "name") Optional<String> name,
                                                       @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.conditionsToResponse(typesService.findConditionsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.conditionsToResponse(typesService.findConditionsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.conditionsToResponse(typesService.findConditionsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.conditionsToResponse(typesService.findConditions());
    }

    @GetMapping("/damageType/{id}")
    public DamageTypeResponse getDamageTypeById(@PathVariable("id") long id) {
        return dtoConverter.damageTypeToResponse(typesService.findDamageTypeById(id));
    }

    @GetMapping("/damageType")
    public List<DamageTypeResponse> getDamageTypesByName(@RequestParam(value = "name") Optional<String> name,
                                                 @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.damageTypesToResponse(typesService.findDamageTypesByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.damageTypesToResponse(typesService.findDamageTypesByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.damageTypesToResponse(typesService.findDamageTypesByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.damageTypesToResponse(typesService.findDamageTypes());
    }

    @GetMapping("/weaponProperty/{id}")
    public WeaponPropertyResponse getWeaponPropertyById(@PathVariable("id") long id) {
        return dtoConverter.weaponPropertyToResponse(typesService.findWeaponPropertyById(id));
    }

    @GetMapping("/weaponProperty")
    public List<WeaponPropertyResponse> getWeaponPropertiesByName(@RequestParam(value = "name") Optional<String> name,
                                                                  @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.weaponPropertiesToResponse(typesService.findWeaponPropertiesByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.weaponPropertiesToResponse(typesService.findWeaponPropertiesByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.weaponPropertiesToResponse(typesService.findWeaponPropertiesByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.weaponPropertiesToResponse(typesService.findWeaponProperties());
    }

    @PostMapping("/condition")
    public ResponseEntity<String> addCustomCondition(@RequestBody ConditionDto conditionDto){
        try {
            typesService.save(conditionDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/magicSchool")
    public ResponseEntity<String> addCustomMagicSchool(@RequestBody MagicSchoolDto magicSchoolDto){
        try {
            typesService.save(magicSchoolDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/damageType")
    public ResponseEntity<String> addCustomDamageType(@RequestBody DamageTypeDto damageTypeDto){
        try {
            typesService.save(damageTypeDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/weaponProperty")
    public ResponseEntity<String> addCustomWeaponProperty(@RequestBody WeaponPropertyDto weaponPropertyDto){
        try {
            typesService.save(weaponPropertyDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
