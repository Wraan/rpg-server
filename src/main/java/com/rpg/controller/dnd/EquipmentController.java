package com.rpg.controller.dnd;

import com.rpg.dto.dnd.equipment.*;
import com.rpg.exception.PrivilageException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.ScenarioException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.*;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.EquipmentService;
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
public class EquipmentController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;

    @Autowired private EquipmentService equipmentService;
    @Autowired private DndDtoConverter dtoConverter;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/scenario/{scenarioKey}/armor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getArmorsByName(@RequestParam(value = "name") Optional<String> name,
                                          @PathVariable("scenarioKey") String scenarioKey,
                                          Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.armorsToResponse(
                        equipmentService.findArmorsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.armorsToResponse(
                        equipmentService.findArmorsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.armorsToResponse(
                        equipmentService.findArmorsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.armorsToResponse(
                        equipmentService.findArmorsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/armor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomArmor(@RequestBody ArmorDto armorDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            equipmentService.add(armorDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/armor/{armorName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteArmor(@PathVariable("scenarioKey") String scenarioKey,
                                      @PathVariable("armorName") String armorName,
                                      Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Armor armor = equipmentService.findArmorByNameAndScenario(armorName, scenario);
            if(armor == null || armor.getScenario() == null)
                throw new NotFoundException("Armor not found or does not belong to current scenario");

            equipmentService.delete(armor);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/armor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchArmor(@RequestBody ArmorDto armorDto,
                                     @PathVariable("scenarioKey") String scenarioKey,
                                     Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Armor armor = equipmentService.findArmorByNameAndScenario(armorDto.getName(), scenario);
            if(armor == null || armor.getScenario() == null)
                throw new NotFoundException("Armor not found or does not belong to current scenario");

            equipmentService.patchValues(armor, armorDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/gear")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getGearByName(@RequestParam(value = "name") Optional<String> name,
                                          @PathVariable("scenarioKey") String scenarioKey,
                                          Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.gearToResponse(
                        equipmentService.findGearByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.gearToResponse(
                        equipmentService.findGearByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.gearToResponse(
                        equipmentService.findGearByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.gearToResponse(
                        equipmentService.findGearByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/gear")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomGear(@RequestBody GearDto gearDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            equipmentService.add(gearDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/gear/{gearName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteGear(@PathVariable("scenarioKey") String scenarioKey,
                                      @PathVariable("gearName") String gearName,
                                      Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Gear gear = equipmentService.findGearByNameAndScenario(gearName, scenario);
            if(gear == null || gear.getScenario() == null)
                throw new NotFoundException("Gear not found or does not belong to current scenario");

            equipmentService.delete(gear);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/gear")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchGear(@RequestBody GearDto gearDto,
                                     @PathVariable("scenarioKey") String scenarioKey,
                                     Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Gear gear = equipmentService.findGearByNameAndScenario(gearDto.getName(), scenario);
            if(gear == null || gear.getScenario() == null)
                throw new NotFoundException("Gear not found or does not belong to current scenario");

            equipmentService.patchValues(gear, gearDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/tool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getToolsByName(@RequestParam(value = "name") Optional<String> name,
                                        @PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.toolsToResponse(
                        equipmentService.findToolsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.toolsToResponse(
                        equipmentService.findToolsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.toolsToResponse(
                        equipmentService.findToolsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.toolsToResponse(
                        equipmentService.findToolsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/tool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomTool(@RequestBody ToolDto toolDto,
                                        @PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            equipmentService.add(toolDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/tool/{toolName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteTool(@PathVariable("scenarioKey") String scenarioKey,
                                     @PathVariable("toolName") String toolName,
                                     Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Tool tool = equipmentService.findToolByNameAndScenario(toolName, scenario);
            if(tool == null || tool.getScenario() == null)
                throw new NotFoundException("Tool not found or does not belong to current scenario");

            equipmentService.delete(tool);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/tool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchTool(@RequestBody ToolDto toolDto,
                                    @PathVariable("scenarioKey") String scenarioKey,
                                    Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Tool tool = equipmentService.findToolByNameAndScenario(toolDto.getName(), scenario);
            if(tool == null || tool.getScenario() == null)
                throw new NotFoundException("Tool not found or does not belong to current scenario");

            equipmentService.patchValues(tool, toolDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getVehiclesByName(@RequestParam(value = "name") Optional<String> name,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.vehiclesToResponse(
                        equipmentService.findVehiclesByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.vehiclesToResponse(
                        equipmentService.findVehiclesByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.vehiclesToResponse(
                        equipmentService.findVehiclesByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.vehiclesToResponse(
                        equipmentService.findVehiclesByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomVehicle(@RequestBody VehicleDto vehicleDto,
                                        @PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            equipmentService.add(vehicleDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/vehicle/{vehicleName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteVehicle(@PathVariable("scenarioKey") String scenarioKey,
                                     @PathVariable("vehicleName") String vehicleName,
                                     Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Vehicle vehicle = equipmentService.findVehicleByNameAndScenario(vehicleName, scenario);
            if(vehicle == null || vehicle.getScenario() == null)
                throw new NotFoundException("Vehicle not found or does not belong to current scenario");

            equipmentService.delete(vehicle);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchVehicle(@RequestBody VehicleDto vehicleDto,
                                    @PathVariable("scenarioKey") String scenarioKey,
                                    Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Vehicle vehicle = equipmentService.findVehicleByNameAndScenario(vehicleDto.getName(), scenario);
            if(vehicle == null || vehicle.getScenario() == null)
                throw new NotFoundException("Vehicle not found or does not belong to current scenario");

            equipmentService.patchValues(vehicle, vehicleDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/weapon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getWeaponsByName(@RequestParam(value = "name") Optional<String> name,
                                            @PathVariable("scenarioKey") String scenarioKey,
                                            Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.weaponsToResponse(
                        equipmentService.findWeaponsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.weaponsToResponse(
                        equipmentService.findWeaponsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.weaponsToResponse(
                        equipmentService.findWeaponsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.weaponsToResponse(
                        equipmentService.findWeaponsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/weapon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomWeapon(@RequestBody WeaponDto weaponDto,
                                           @PathVariable("scenarioKey") String scenarioKey,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            equipmentService.add(weaponDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/weapon/{weaponName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteWeapon(@PathVariable("scenarioKey") String scenarioKey,
                                        @PathVariable("weaponName") String weaponName,
                                        Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Weapon weapon = equipmentService.findWeaponByNameAndScenario(weaponName, scenario);
            if(weapon == null || weapon.getScenario() == null)
                throw new NotFoundException("Weapon not found or does not belong to current scenario");

            equipmentService.delete(weapon);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/weapon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchWeapon(@RequestBody WeaponDto weaponDto,
                                       @PathVariable("scenarioKey") String scenarioKey,
                                       Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Weapon weapon = equipmentService.findWeaponByNameAndScenario(weaponDto.getName(), scenario);
            if(weapon == null || weapon.getScenario() == null)
                throw new NotFoundException("Weapon not found or does not belong to current scenario");

            equipmentService.patchValues(weapon, weaponDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
