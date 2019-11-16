package com.rpg.controller.dnd;

import com.rpg.dto.dnd.types.*;
import com.rpg.exception.PrivilageException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.ScenarioException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.Condition;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.TypesService;
import com.rpg.service.security.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TypesController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;

    @Autowired private TypesService typesService;
    @Autowired private DndDtoConverter dtoConverter;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/scenario/{scenarioKey}/condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getConditionsByName(@RequestParam(value = "name") Optional<String> name,
                                              @PathVariable("scenarioKey") String scenarioKey,
                                              Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.conditionsToResponse(
                        typesService.findConditionsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.conditionsToResponse(
                        typesService.findConditionsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.conditionsToResponse(
                        typesService.findConditionsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.conditionsToResponse(
                        typesService.findConditionsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomCondition(@RequestBody ConditionDto conditionDto,
                                             @PathVariable("scenarioKey") String scenarioKey,
                                             Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            typesService.add(conditionDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/condition/{conditionName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteCondition(@PathVariable("scenarioKey") String scenarioKey,
                                          @PathVariable("conditionName") String conditionName,
                                          Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Condition condition = typesService.findConditionByNameAndScenario(conditionName, scenario);
            if(condition == null || condition.getScenario() == null)
                throw new NotFoundException("Condition not found or does not belong to current scenario");

            typesService.delete(condition);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchCondition(@RequestBody ConditionDto conditionDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Condition condition = typesService.findConditionByNameAndScenario(conditionDto.getName(), scenario);
            if(condition == null || condition.getScenario() == null)
                throw new NotFoundException("Condition not found or does not belong to current scenario");

            typesService.patchValues(condition, conditionDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/damageType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getDamageTypesByName(@RequestParam(value = "name") Optional<String> name,
                                              @PathVariable("scenarioKey") String scenarioKey,
                                              Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.damageTypesToResponse(
                        typesService.findDamageTypesByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.damageTypesToResponse(
                        typesService.findDamageTypesByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.damageTypesToResponse(
                        typesService.findDamageTypesByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.damageTypesToResponse(
                        typesService.findDamageTypesByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/damageType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomDamageType(@RequestBody DamageTypeDto damageTypeDto,
                                             @PathVariable("scenarioKey") String scenarioKey,
                                             Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            typesService.add(damageTypeDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/damageType/{damageTypeName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteDamageType(@PathVariable("scenarioKey") String scenarioKey,
                                          @PathVariable("damageTypeName") String damageTypeName,
                                          Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            DamageType damageType = typesService.findDamageTypeByNameAndScenario(damageTypeName, scenario);
            if(damageType == null || damageType.getScenario() == null)
                throw new NotFoundException("DamageType not found or does not belong to current scenario");

            typesService.delete(damageType);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/damageType")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchDamageType(@RequestBody DamageTypeDto damageTypeDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            DamageType damageType = typesService.findDamageTypeByNameAndScenario(damageTypeDto.getName(), scenario);
            if(damageType == null || damageType.getScenario() == null)
                throw new NotFoundException("DamageType not found or does not belong to current scenario");

            typesService.patchValues(damageType, damageTypeDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/magicSchool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getMagicSchoolsByName(@RequestParam(value = "name") Optional<String> name,
                                               @PathVariable("scenarioKey") String scenarioKey,
                                               Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.magicSchoolsToResponse(
                        typesService.findMagicSchoolsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.magicSchoolsToResponse(
                        typesService.findMagicSchoolsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.magicSchoolsToResponse(
                        typesService.findMagicSchoolsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.magicSchoolsToResponse(
                        typesService.findMagicSchoolsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/magicSchool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomMagicSchool(@RequestBody MagicSchoolDto magicSchoolDto,
                                              @PathVariable("scenarioKey") String scenarioKey,
                                              Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            typesService.add(magicSchoolDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/magicSchool/{magicSchoolName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteMagicSchool(@PathVariable("scenarioKey") String scenarioKey,
                                           @PathVariable("magicSchoolName") String magicSchoolName,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            MagicSchool magicSchool = typesService.findMagicSchoolByNameAndScenario(magicSchoolName, scenario);
            if(magicSchool == null || magicSchool.getScenario() == null)
                throw new NotFoundException("MagicSchool not found or does not belong to current scenario");

            typesService.delete(magicSchool);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/magicSchool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchMagicSchool(@RequestBody MagicSchoolDto magicSchoolDto,
                                          @PathVariable("scenarioKey") String scenarioKey,
                                          Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            MagicSchool magicSchool = typesService.findMagicSchoolByNameAndScenario(magicSchoolDto.getName(), scenario);
            if(magicSchool == null || magicSchool.getScenario() == null)
                throw new NotFoundException("MagicSchool not found or does not belong to current scenario");

            typesService.patchValues(magicSchool, magicSchoolDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/weaponProperty")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getWeaponPropertiesByName(@RequestParam(value = "name") Optional<String> name,
                                                @PathVariable("scenarioKey") String scenarioKey,
                                                Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.weaponPropertiesToResponse(
                        typesService.findWeaponPropertiesByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.weaponPropertiesToResponse(
                        typesService.findWeaponPropertiesByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.weaponPropertiesToResponse(
                        typesService.findWeaponPropertiesByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.weaponPropertiesToResponse(
                        typesService.findWeaponPropertiesByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/weaponProperty")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomWeaponProperty(@RequestBody WeaponPropertyDto weaponPropertyDto,
                                               @PathVariable("scenarioKey") String scenarioKey,
                                               Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            typesService.add(weaponPropertyDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/weaponProperty/{weaponPropertyName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteWeaponProperty(@PathVariable("scenarioKey") String scenarioKey,
                                            @PathVariable("weaponPropertyName") String weaponPropertyName,
                                            Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            WeaponProperty weaponProperty = typesService.findWeaponPropertyByNameAndScenario(weaponPropertyName, scenario);
            if(weaponProperty == null || weaponProperty.getScenario() == null)
                throw new NotFoundException("WeaponProperty not found or does not belong to current scenario");

            typesService.delete(weaponProperty);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/weaponProperty")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchWeaponProperty(@RequestBody WeaponPropertyDto weaponPropertyDto,
                                           @PathVariable("scenarioKey") String scenarioKey,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            WeaponProperty weaponProperty = typesService.findWeaponPropertyByNameAndScenario(weaponPropertyDto.getName(), scenario);
            if(weaponProperty == null || weaponProperty.getScenario() == null)
                throw new NotFoundException("WeaponProperty not found or does not belong to current scenario");

            typesService.patchValues(weaponProperty, weaponPropertyDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
