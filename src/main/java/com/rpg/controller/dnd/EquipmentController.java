package com.rpg.controller.dnd;

import com.rpg.dto.dnd.equipment.*;
import com.rpg.model.dnd.equipment.*;
import com.rpg.repository.dnd.equipment.*;
import com.rpg.service.DndDtoConverter;
import com.rpg.service.dnd.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/armor/{id}")
    public ArmorResponse getArmorById(@PathVariable("id") long id) {
        return dtoConverter.armorToResponse(equipmentService.findArmorById(id));
    }

    @GetMapping("/armor")
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
    public GearResponse getGearById(@PathVariable("id") long id) {
        return dtoConverter.gearToResponse(equipmentService.findGearById(id));
    }

    @GetMapping("/gear")
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
    public ToolResponse getToolById(@PathVariable("id") long id) {
        return dtoConverter.toolToResponse(equipmentService.findToolById(id));
    }

    @GetMapping("/tool")
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
    public VehicleResponse getVehicleById(@PathVariable("id") long id) {
        return dtoConverter.vehicleToResponse(equipmentService.findVehicleById(id));
    }

    @GetMapping("/vehicle")
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
    public WeaponResponse getWeaponById(@PathVariable("id") long id) {
        return dtoConverter.weaponToResponse(equipmentService.findWeaponById(id));
    }

    @GetMapping("/weapon")
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
}
