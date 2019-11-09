package com.rpg.controller.dnd;

import com.rpg.dto.dnd.equipment.*;
import com.rpg.repository.dnd.equipment.*;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.EquipmentService;
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
public class EquipmentController {

    @Autowired private ArmorsRepository armorsRepository;
    @Autowired private GearRepository gearRepository;
    @Autowired private ToolsRepository toolsRepository;
    @Autowired private VehiclesRepository vehiclesRepository;
    @Autowired private WeaponsRepository weaponsRepository;

    @Autowired private EquipmentService equipmentService;
    @Autowired private DndDtoConverter dtoConverter;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/armor/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ArmorResponse getArmorById(@PathVariable("id") long id) {
        return dtoConverter.armorToResponse(equipmentService.findArmorById(id));
    }

    @GetMapping("/armor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public List<ArmorResponse> getArmorsByName(@RequestParam(value = "name") Optional<String> name,
                                               @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.armorsToResponse(equipmentService.findArmorsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.armorsToResponse(equipmentService.findArmorsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.armorsToResponse(equipmentService.findArmorsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.armorsToResponse(equipmentService.findArmors());
    }

    @GetMapping("/gear/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public GearResponse getGearById(@PathVariable("id") long id) {
        return dtoConverter.gearToResponse(equipmentService.findGearById(id));
    }

    @GetMapping("/gear")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public List<GearResponse> getGearByName(@RequestParam(value = "name") Optional<String> name,
                                            @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.gearToResponse(equipmentService.findGearByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.gearToResponse(equipmentService.findGearByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.gearToResponse(equipmentService.findGearByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.gearToResponse(equipmentService.findGear());
    }

    @GetMapping("/tool/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ToolResponse getToolById(@PathVariable("id") long id) {
        return dtoConverter.toolToResponse(equipmentService.findToolById(id));
    }

    @GetMapping("/tool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public List<ToolResponse> getToolsByName(@RequestParam(value = "name") Optional<String> name,
                                     @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.toolsToResponse(equipmentService.findToolsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.toolsToResponse(equipmentService.findToolsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.toolsToResponse(equipmentService.findToolsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.toolsToResponse(equipmentService.findTools());
    }

    @GetMapping("/vehicle/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public VehicleResponse getVehicleById(@PathVariable("id") long id) {
        return dtoConverter.vehicleToResponse(equipmentService.findVehicleById(id));
    }

    @GetMapping("/vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public List<VehicleResponse> getVehiclesByName(@RequestParam(value = "name") Optional<String> name,
                                                   @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.vehiclesToResponse(equipmentService.findVehiclesByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.vehiclesToResponse(equipmentService.findVehiclesByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.vehiclesToResponse(equipmentService.findVehiclesByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.vehiclesToResponse(equipmentService.findVehicles());
    }

    @GetMapping("/weapon/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public WeaponResponse getWeaponById(@PathVariable("id") long id) {
        return dtoConverter.weaponToResponse(equipmentService.findWeaponById(id));
    }

    @GetMapping("/weapon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public List<WeaponResponse> getWeaponsByName(@RequestParam(value = "name") Optional<String> name,
                                         @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.weaponsToResponse(equipmentService.findWeaponsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.weaponsToResponse(equipmentService.findWeaponsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.weaponsToResponse(equipmentService.findWeaponsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.weaponsToResponse(equipmentService.findWeapons());
    }

    @PostMapping("/armor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomArmor(@RequestBody ArmorDto dto){
        try {
            equipmentService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/gear")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomGear(@RequestBody GearDto dto){
        try {
            equipmentService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/tool")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomTool(@RequestBody ToolDto dto){
        try {
            equipmentService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/vehicle")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomVehicle(@RequestBody VehicleDto dto){
        try {
            equipmentService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/weapon")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomWeapon(@RequestBody WeaponDto dto){
        try {
            equipmentService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
