package com.rpg.service.dnd;

import com.rpg.dto.dnd.character.AttackDto;
import com.rpg.dto.dnd.character.equipment.CurrencyDto;
import com.rpg.dto.dnd.character.equipment.EquipmentAmountDto;
import com.rpg.dto.dnd.equipment.*;
import com.rpg.exception.NameExistsInScenarioException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.TypeDoesNotExist;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.character.equipment.*;
import com.rpg.model.dnd.character.equipment.Currency;
import com.rpg.model.dnd.equipment.*;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.model.security.User;
import com.rpg.repository.dnd.equipment.*;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.application.ScenarioService;
import com.sun.media.sound.InvalidDataException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    @Autowired private ArmorsRepository armorsRepository;
    @Autowired private GearRepository gearRepository;
    @Autowired private ToolsRepository toolsRepository;
    @Autowired private VehiclesRepository vehiclesRepository;
    @Autowired private WeaponsRepository weaponsRepository;

    @Autowired private TypesService typesService;

    @Autowired private DndDtoConverter dtoConverter;
    @Autowired private ScenarioService scenarioService;

    public Armor add(ArmorDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(armorsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Armor armor = dtoConverter.fromDto(dto, gm, scenario);
        return armorsRepository.save(armor);
    }

    public List<Armor> findArmorsByNameContainingAndScenario(String name, Scenario scenario){
        List<Armor> list = new ArrayList<>(
                armorsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(armorsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Armor> findArmorsByScenario(Scenario scenario){
        List<Armor> list = new ArrayList<>(
                armorsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(armorsRepository.findByScenario(scenario));
        return list;
    }

    public Armor findArmorByNameAndScenario(String name, Scenario scenario) {
        Armor armor = armorsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (armor == null) return armorsRepository.findByNameAndScenario(name, null).orElse(null);
        return armor;
    }

    public List<Armor> findArmorsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Armor> list = new ArrayList<>(
                armorsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(armorsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Armor> findArmorsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Armor> list = new ArrayList<>(
                armorsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(armorsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Armor armor, ArmorDto dto) {
        if(dto.getArmorClass() != null){
            armor.getArmorClass().setBase(dto.getArmorClass().getBase());
            armor.getArmorClass().setDexBonus(dto.getArmorClass().isDexBonus());
            armor.getArmorClass().setMaxBonus(dto.getArmorClass().getMaxBonus());
        }
        else
            armor.setArmorClass(null);
        armor.setStrMinimum(dto.getStrMinimum());
        armor.setStealthDisadvantage(dto.isStealthDisadvantage());
        armor.setWeight(dto.getWeight());
        armor.setCost(dto.getCost());
        armor.setVisible(dto.isVisible());
        armorsRepository.save(armor);
    }

    public void delete(Armor armor) {
        armorsRepository.delete(armor);
    }

    public Gear add(GearDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(gearRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Gear gear = dtoConverter.fromDto(dto, gm, scenario);
        return gearRepository.save(gear);
    }

    public List<Gear> findGearByNameContainingAndScenario(String name, Scenario scenario){
        List<Gear> list = new ArrayList<>(
                gearRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(gearRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Gear> findGearByScenario(Scenario scenario){
        List<Gear> list = new ArrayList<>(
                gearRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(gearRepository.findByScenario(scenario));
        return list;
    }

    public Gear findGearByNameAndScenario(String name, Scenario scenario) {
        Gear gear = gearRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (gear == null) return gearRepository.findByNameAndScenario(name, null).orElse(null);
        return gear;
    }

    public List<Gear> findGearByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Gear> list = new ArrayList<>(
                gearRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(gearRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Gear> findGearByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Gear> list = new ArrayList<>(
                gearRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(gearRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Gear gear, GearDto dto) {
        gear.setDescription(dto.getDescription());
        gear.setWeight(dto.getWeight());
        gear.setCost(dto.getCost());
        gear.setVisible(dto.isVisible());
        gearRepository.save(gear);
    }

    public void delete(Gear gear) {
        gearRepository.delete(gear);
    }

    public Tool add(ToolDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(toolsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Tool tool = dtoConverter.fromDto(dto, gm, scenario);
        return toolsRepository.save(tool);
    }

    public List<Tool> findToolsByNameContainingAndScenario(String name, Scenario scenario){
        List<Tool> list = new ArrayList<>(
                toolsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(toolsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Tool> findToolsByScenario(Scenario scenario){
        List<Tool> list = new ArrayList<>(
                toolsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(toolsRepository.findByScenario(scenario));
        return list;
    }

    public Tool findToolByNameAndScenario(String name, Scenario scenario) {
        Tool tool = toolsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (tool == null) return toolsRepository.findByNameAndScenario(name, null).orElse(null);
        return tool;
    }

    public List<Tool> findToolsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Tool> list = new ArrayList<>(
                toolsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(toolsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Tool> findToolsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Tool> list = new ArrayList<>(
                toolsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(toolsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Tool tool, ToolDto dto) {
        tool.setDescription(dto.getDescription());
        tool.setCategory(dto.getCategory());
        tool.setWeight(dto.getWeight());
        tool.setCost(dto.getCost());
        tool.setVisible(dto.isVisible());
        toolsRepository.save(tool);
    }

    public void delete(Tool tool) {
        toolsRepository.delete(tool);
    }

    public Vehicle add(VehicleDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(vehiclesRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Vehicle vehicle = dtoConverter.fromDto(dto, gm, scenario);
        return vehiclesRepository.save(vehicle);
    }

    public List<Vehicle> findVehiclesByNameContainingAndScenario(String name, Scenario scenario){
        List<Vehicle> list = new ArrayList<>(
                vehiclesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(vehiclesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Vehicle> findVehiclesByScenario(Scenario scenario){
        List<Vehicle> list = new ArrayList<>(
                vehiclesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(vehiclesRepository.findByScenario(scenario));
        return list;
    }

    public Vehicle findVehicleByNameAndScenario(String name, Scenario scenario) {
        Vehicle vehicle = vehiclesRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (vehicle == null) return vehiclesRepository.findByNameAndScenario(name, null).orElse(null);
        return vehicle;
    }

    public List<Vehicle> findVehiclesByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Vehicle> list = new ArrayList<>(
                vehiclesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(vehiclesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Vehicle> findVehiclesByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Vehicle> list = new ArrayList<>(
                vehiclesRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(vehiclesRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Vehicle vehicle, VehicleDto dto) {
        vehicle.setDescription(dto.getDescription());
        vehicle.setWeight(dto.getWeight());
        vehicle.setCost(dto.getCost());
        vehicle.setVisible(dto.isVisible());
        vehiclesRepository.save(vehicle);
    }

    public void delete(Vehicle vehicle) {
        vehiclesRepository.delete(vehicle);
    }

    public Weapon add(WeaponDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(weaponsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Weapon weapon = dtoConverter.fromDto(dto, gm, scenario);
        if(weapon.getDamageType() == null)
            throw new TypeDoesNotExist("DamageType does not exist");
        for(WeaponProperty property : weapon.getProperties())
            if(property == null) throw new TypeDoesNotExist("WeaponProperty does not exist");
        return weaponsRepository.save(weapon);
    }

    public List<Weapon> findWeaponsByNameContainingAndScenario(String name, Scenario scenario){
        List<Weapon> list = new ArrayList<>(
                weaponsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(weaponsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Weapon> findWeaponsByScenario(Scenario scenario){
        List<Weapon> list = new ArrayList<>(
                weaponsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(weaponsRepository.findByScenario(scenario));
        return list;
    }

    public Weapon findWeaponByNameAndScenario(String name, Scenario scenario) {
        Weapon weapon = weaponsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (weapon == null) return weaponsRepository.findByNameAndScenario(name, null).orElse(null);
        return weapon;
    }

    public List<Weapon> findWeaponsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Weapon> list = new ArrayList<>(
                weaponsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(weaponsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Weapon> findWeaponsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Weapon> list = new ArrayList<>(
                weaponsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(weaponsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Weapon weapon, WeaponDto dto) throws Exception {
        DamageType damageType =
                typesService.findDamageTypeByNameAndScenario(dto.getDamageType(), weapon.getScenario());
        if(damageType == null)
            throw new TypeDoesNotExist("DamageType does not exist");
        weapon.setDamageType(damageType);
        List<WeaponProperty> properties = new ArrayList<>();
        for (String it: dto.getProperties()){
            WeaponProperty property = typesService.findWeaponPropertyByNameAndScenario(it, weapon.getScenario());
            if (property == null) throw new TypeDoesNotExist("WeaponProperty does not exist");
            properties.add(property);
        }
        weapon.setProperties(properties);
        weapon.setCategory(dto.getCategory());
        weapon.setWeaponRange(dto.getWeaponRange());
        weapon.setDamageDice(dto.getDamageDice());
        weapon.setDamageBonus(dto.getDamageBonus());
        weapon.setNormalRange(dto.getNormalRange());
        weapon.setLongRange(dto.getLongRange());
        weapon.setNormalThrowRange(dto.getNormalThrowRange());
        weapon.setLongThrowRange(dto.getLongThrowRange());
        weapon.setWeight(dto.getWeight());
        weapon.setCost(dto.getCost());
        weapon.setVisible(dto.isVisible());
        weaponsRepository.save(weapon);
    }

    public void delete(Weapon weapon) {
        weaponsRepository.delete(weapon);
    }

    public void deleteByScenario(Scenario scenario) {
        armorsRepository.deleteByScenario(scenario);
        gearRepository.deleteByScenario(scenario);
        toolsRepository.deleteByScenario(scenario);
        vehiclesRepository.deleteByScenario(scenario);
        weaponsRepository.deleteByScenario(scenario);
    }

    public CurrencyDto convertCurrency(Currency currency) {
        return new CurrencyDto(currency.getCp(), currency.getSp(), currency.getEp(), currency.getGp(),
                currency.getPp());
    }

    public List<EquipmentAmountDto> getEQAmountFromArmors(List<ArmorAmount> armors) {
        List<EquipmentAmountDto> out = new ArrayList<>();
        for(ArmorAmount it : armors){
            out.add(new EquipmentAmountDto(it.getArmor().getName(), it.getAmount()));
        }
        return out;
    }

    public List<EquipmentAmountDto> getEQAmountFromGear(List<GearAmount> gear) {
        List<EquipmentAmountDto> out = new ArrayList<>();
        for(GearAmount it : gear){
            out.add(new EquipmentAmountDto(it.getGear().getName(), it.getAmount()));
        }
        return out;
    }

    public List<EquipmentAmountDto> getEQAmountFromWeapons(List<WeaponAmount> weapons) {
        List<EquipmentAmountDto> out = new ArrayList<>();
        for(WeaponAmount it : weapons){
            out.add(new EquipmentAmountDto(it.getWeapon().getName(), it.getAmount()));
        }
        return out;
    }

    public List<EquipmentAmountDto> getEQAmountFromVehicles(List<VehicleAmount> vehicles) {
        List<EquipmentAmountDto> out = new ArrayList<>();
        for(VehicleAmount it : vehicles){
            out.add(new EquipmentAmountDto(it.getVehicle().getName(), it.getAmount()));
        }
        return out;
    }

    public List<EquipmentAmountDto> getEQAmountFromTools(List<ToolAmount> tools) {
        List<EquipmentAmountDto> out = new ArrayList<>();
        for(ToolAmount it : tools){
            out.add(new EquipmentAmountDto(it.getTool().getName(), it.getAmount()));
        }
        return out;
    }

    public Set<Attack> createAttacks(List<AttackDto> attacks, CharacterEquipment characterEquipment, Scenario scenario) throws Exception {
        Set<Attack> createdAttacks = new HashSet<>();
        for(AttackDto attack: attacks){
            DamageType damageType = typesService.findDamageTypeByNameAndScenario(attack.getType(), scenario);
            if(Objects.isNull(damageType))
                throw new TypeDoesNotExist("DamageType does not exist");

            createdAttacks.add(new Attack(attack.getName(), attack.getBonus(), attack.getDamage(), damageType, characterEquipment));
        }
        return createdAttacks;
    }

    public List<ArmorAmount> getArmorsFromEQAmounts(List<EquipmentAmountDto> armors, CharacterEquipment characterEquipment,
                                                    Scenario scenario) throws Exception {
        armors.forEach(it -> {
            if(it.getAmount() <= 0) throw new ValueException("Amount cannot be lower or equal 0");
        });
        Set<String> eqNames = armors.stream().map(EquipmentAmountDto::getName).collect(Collectors.toSet());
        Set<Armor> out = armorsRepository.findByNameInAndScenario(eqNames, scenario);
        out.addAll(armorsRepository.findByNameInAndScenario(eqNames, null));
        Set<String> outNames = out.stream().map(Armor::getName).collect(Collectors.toSet());
        Map<String, Armor> outMap = out.stream().collect(Collectors.toMap(Armor::getName, Function.identity()));
        for(String it : eqNames){
            if (!outNames.contains(it))
                throw new InvalidDataException("Armor " + it + " does not exist");
        }

        return armors.stream()
                .map(it -> new ArmorAmount(outMap.get(it.getName()), characterEquipment, it.getAmount()))
                .collect(Collectors.toList());
    }

    public List<GearAmount> getGearFromEQAmounts(List<EquipmentAmountDto> gear, CharacterEquipment characterEquipment,
                                                 Scenario scenario) throws Exception {
        gear.forEach(it -> {
            if(it.getAmount() <= 0) throw new ValueException("Amount cannot be lower or equal 0");
        });
        Set<String> eqNames = gear.stream().map(EquipmentAmountDto::getName).collect(Collectors.toSet());
        Set<Gear> out = gearRepository.findByNameInAndScenario(eqNames, scenario);
        out.addAll(gearRepository.findByNameInAndScenario(eqNames, null));
        Set<String> outNames = out.stream().map(Gear::getName).collect(Collectors.toSet());
        Map<String, Gear> outMap = out.stream().collect(Collectors.toMap(Gear::getName, Function.identity()));
        for(String it : eqNames){
            if (!outNames.contains(it))
                throw new InvalidDataException("Gear " + it + " does not exist");
        }
        return gear.stream()
                .map(it -> new GearAmount(outMap.get(it.getName()), characterEquipment, it.getAmount()))
                .collect(Collectors.toList());
    }

    public List<WeaponAmount> getWeaponsFromEQAmounts(List<EquipmentAmountDto> weapons, CharacterEquipment characterEquipment,
                                                      Scenario scenario) throws Exception {
        weapons.forEach(it -> {
            if(it.getAmount() <= 0) throw new ValueException("Amount cannot be lower or equal 0");
        });
        Set<String> eqNames = weapons.stream().map(EquipmentAmountDto::getName).collect(Collectors.toSet());
        Set<Weapon> out = weaponsRepository.findByNameInAndScenario(eqNames, scenario);
        out.addAll(weaponsRepository.findByNameInAndScenario(eqNames, null));
        Set<String> outNames = out.stream().map(Weapon::getName).collect(Collectors.toSet());
        Map<String, Weapon> outMap = out.stream().collect(Collectors.toMap(Weapon::getName, Function.identity()));
        for(String it : eqNames){
            if (!outNames.contains(it))
                throw new InvalidDataException("Weapon " + it + " does not exist");
        }
        return weapons.stream()
                .map(it -> new WeaponAmount(outMap.get(it.getName()), characterEquipment, it.getAmount()))
                .collect(Collectors.toList());
    }

    public List<VehicleAmount> getVehiclesFromEQAmounts(List<EquipmentAmountDto> vehicles, CharacterEquipment characterEquipment,
                                                        Scenario scenario) throws Exception {
        vehicles.forEach(it -> {
            if(it.getAmount() <= 0) throw new ValueException("Amount cannot be lower or equal 0");
        });
        Set<String> eqNames = vehicles.stream().map(EquipmentAmountDto::getName).collect(Collectors.toSet());
        Set<Vehicle> out = vehiclesRepository.findByNameInAndScenario(eqNames, scenario);
        out.addAll(vehiclesRepository.findByNameInAndScenario(eqNames, null));
        Set<String> outNames = out.stream().map(Vehicle::getName).collect(Collectors.toSet());
        Map<String, Vehicle> outMap = out.stream().collect(Collectors.toMap(Vehicle::getName, Function.identity()));
        for(String it : eqNames){
            if (!outNames.contains(it))
                throw new InvalidDataException("Vehicle " + it + " does not exist");
        }
        return vehicles.stream()
                .map(it -> new VehicleAmount(outMap.get(it.getName()), characterEquipment, it.getAmount()))
                .collect(Collectors.toList());
    }

    public List<ToolAmount> getToolsFromEQAmounts(List<EquipmentAmountDto> tools, CharacterEquipment characterEquipment,
                                                  Scenario scenario) throws Exception {
        tools.forEach(it -> {
            if(it.getAmount() <= 0) throw new ValueException("Amount cannot be lower or equal 0");
        });
        Set<String> eqNames = tools.stream().map(EquipmentAmountDto::getName).collect(Collectors.toSet());
        Set<Tool> out = toolsRepository.findByNameInAndScenario(eqNames, scenario);
        out.addAll(toolsRepository.findByNameInAndScenario(eqNames, null));
        Set<String> outNames = out.stream().map(Tool::getName).collect(Collectors.toSet());
        Map<String, Tool> outMap = out.stream().collect(Collectors.toMap(Tool::getName, Function.identity()));
        for(String it : eqNames){
            if (!outNames.contains(it))
                throw new InvalidDataException("Tool " + it + " does not exist");
        }
        return tools.stream()
                .map(it -> new ToolAmount(outMap.get(it.getName()), characterEquipment, it.getAmount()))
                .collect(Collectors.toList());
    }

    public Currency createCurrency(CurrencyDto currency) {
        return new Currency(currency.getCp(), currency.getSp(), currency.getEp(), currency.getGp(),
                currency.getPp());
    }
}
