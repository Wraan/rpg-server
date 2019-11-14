package com.rpg.controller.dnd;

import com.rpg.dto.dnd.types.*;
import com.rpg.exception.PrivilageException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.Condition;
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

            if(name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.conditionsToResponse(
                        typesService.findConditionsByNameContainingAndScenario(name.get(), scenario)));
            else
                return ResponseEntity.ok().body(dtoConverter.conditionsToResponse(
                        typesService.findConditionsByScenario(scenario)));

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
            Condition condition = typesService.findConditionByName(conditionName, scenario);
            if(condition == null)
                throw new NotFoundException("Condition not found");

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
            Condition condition = typesService.findConditionByName(conditionDto.getName(), scenario);
            if(condition == null)
                throw new NotFoundException("Condition not found");

            typesService.patchValues(condition, conditionDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
