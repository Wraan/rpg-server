package com.rpg.controller.dnd;

import com.rpg.model.dnd.equipment.*;
import com.rpg.repository.dnd.equipment.*;
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

    @GetMapping("/armor/{id}")
    public Armor getArmorById(@PathVariable("id") long id) {
        return armorsRepository.findById(id).get();
    }

    @GetMapping("/armor")
    public List<Armor> getArmorsByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return armorsRepository.findByNameIgnoreCaseContaining(name.get());
        return armorsRepository.findAll();
    }

    @GetMapping("/gear/{id}")
    public Gear getGearById(@PathVariable("id") long id) {
        return gearRepository.findById(id).get();
    }

    @GetMapping("/gear")
    public List<Gear> getGearByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return gearRepository.findByNameIgnoreCaseContaining(name.get());
        return gearRepository.findAll();
    }

    @GetMapping("/tool/{id}")
    public Tool getToolById(@PathVariable("id") long id) {
        return toolsRepository.findById(id).get();
    }

    @GetMapping("/tool")
    public List<Tool> getToolsByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return toolsRepository.findByNameIgnoreCaseContaining(name.get());
        return toolsRepository.findAll();
    }

    @GetMapping("/vehicle/{id}")
    public Vehicle getVehicleById(@PathVariable("id") long id) {
        return vehiclesRepository.findById(id).get();
    }

    @GetMapping("/vehicle")
    public List<Vehicle> getVehiclesByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return vehiclesRepository.findByNameIgnoreCaseContaining(name.get());
        return vehiclesRepository.findAll();
    }

    @GetMapping("/weapon/{id}")
    public Weapon getWeaponById(@PathVariable("id") long id) {
        return weaponsRepository.findById(id).get();
    }

    @GetMapping("/weapon")
    public List<Weapon> getWeaponsByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return weaponsRepository.findByNameIgnoreCaseContaining(name.get());
        return weaponsRepository.findAll();
    }
}
