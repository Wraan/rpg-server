package com.rpg.controller.dnd;

import com.rpg.dto.dnd.types.*;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.TypesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/magicSchool/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public MagicSchoolResponse getMagicSchoolById(@PathVariable("id") long id) {
        return dtoConverter.magicSchoolToResponse(typesService.findMagicSchoolById(id));
    }

    @GetMapping("/magicSchool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ConditionResponse getConditionById(@PathVariable("id") long id) {
        return dtoConverter.conditionToResponse(typesService.findConditionById(id));
    }

    @GetMapping("/condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public DamageTypeResponse getDamageTypeById(@PathVariable("id") long id) {
        return dtoConverter.damageTypeToResponse(typesService.findDamageTypeById(id));
    }

    @GetMapping("/damageType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public WeaponPropertyResponse getWeaponPropertyById(@PathVariable("id") long id) {
        return dtoConverter.weaponPropertyToResponse(typesService.findWeaponPropertyById(id));
    }

    @GetMapping("/weaponProperty")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomCondition(@RequestBody ConditionDto conditionDto){
        try {
            typesService.save(conditionDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/magicSchool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomMagicSchool(@RequestBody MagicSchoolDto magicSchoolDto){
        try {
            typesService.save(magicSchoolDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/damageType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomDamageType(@RequestBody DamageTypeDto damageTypeDto){
        try {
            typesService.save(damageTypeDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/weaponProperty")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomWeaponProperty(@RequestBody WeaponPropertyDto weaponPropertyDto){
        try {
            typesService.save(weaponPropertyDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
