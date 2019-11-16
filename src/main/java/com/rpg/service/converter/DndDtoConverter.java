package com.rpg.service.converter;

import com.rpg.dto.dnd.abilities.*;
import com.rpg.dto.dnd.equipment.*;
import com.rpg.dto.dnd.types.*;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.*;
import com.rpg.model.dnd.equipment.*;
import com.rpg.model.dnd.types.Condition;
import com.rpg.model.dnd.types.DamageType;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.dnd.types.WeaponProperty;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.dnd.TypesService;
import com.rpg.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DndDtoConverter {

    @Autowired private ScenarioService scenarioService;
    @Autowired private UserService userService;

    @Lazy @Autowired private TypesService typesService;

    public Condition fromDto(ConditionDto conditionDto, User gm, Scenario scenario){
     return new Condition(conditionDto.getName(), conditionDto.getDescription(), conditionDto.isVisible(),
             gm, scenario);
    }

    public List<ConditionResponse> conditionsToResponse(List<Condition> conditions){
        List<ConditionResponse> list = new ArrayList<>();
        conditions.forEach(it ->{
            list.add(conditionToResponse(it));
        });
        return list;
    }

    public ConditionResponse conditionToResponse(Condition it){
        return new ConditionResponse(it.getId(), it.getName(), it.getDescription(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public DamageType fromDto(DamageTypeDto damageTypeDto, User gm, Scenario scenario){
        return new DamageType(damageTypeDto.getName(), damageTypeDto.getDescription(), damageTypeDto.isVisible(),
                gm, scenario);
    }

    public List<DamageTypeResponse> damageTypesToResponse(List<DamageType> damageTypes){
        List<DamageTypeResponse> list = new ArrayList<>();
        damageTypes.forEach(it ->{
            list.add(damageTypeToResponse(it));
        });
        return list;
    }

    public DamageTypeResponse damageTypeToResponse(DamageType it){
        return new DamageTypeResponse(it.getId(), it.getName(), it.getDescription(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public MagicSchool fromDto(MagicSchoolDto magicSchoolDto, User gm, Scenario scenario){
        return new MagicSchool(magicSchoolDto.getName(), magicSchoolDto.getDescription(), magicSchoolDto.isVisible(),
                gm, scenario);
    }

    public List<MagicSchoolResponse> magicSchoolsToResponse(List<MagicSchool> magicSchools){
        List<MagicSchoolResponse> list = new ArrayList<>();
        magicSchools.forEach(it ->{
            list.add(magicSchoolToResponse(it));
        });
        return list;
    }

    public MagicSchoolResponse magicSchoolToResponse(MagicSchool it){
        return new MagicSchoolResponse(it.getId(), it.getName(), it.getDescription(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public WeaponProperty fromDto(WeaponPropertyDto weaponPropertyDto, User gm, Scenario scenario){
        return new WeaponProperty(weaponPropertyDto.getName(), weaponPropertyDto.getDescription(), weaponPropertyDto.isVisible(),
                gm, scenario);
    }

    public List<WeaponPropertyResponse> weaponPropertiesToResponse(List<WeaponProperty> weaponProperties){
        List<WeaponPropertyResponse> list = new ArrayList<>();
        weaponProperties.forEach(it ->{
            list.add(weaponPropertyToResponse(it));
        });
        return list;
    }

    public WeaponPropertyResponse weaponPropertyToResponse(WeaponProperty it){
        return new WeaponPropertyResponse(it.getId(), it.getName(), it.getDescription(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Feature fromDto(FeatureDto dto, User gm, Scenario scenario){
        return new Feature(dto.getName(), dto.getDescription(), dto.isVisible(),
                gm, scenario);
    }

    public List<FeatureResponse> featuresToResponse(List<Feature> list){
        List<FeatureResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(featureToResponse(it));
        });
        return out;
    }

    public FeatureResponse featureToResponse(Feature it){
        return new FeatureResponse(it.getId(), it.getName(), it.getDescription(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Language fromDto(LanguageDto dto, User gm, Scenario scenario){
        return new Language(dto.getName(), dto.getType(), dto.getScript(),dto.isVisible(), gm, scenario);
    }

    public List<LanguageResponse> languagesToResponse(List<Language> list){
        List<LanguageResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(languageToResponse(it));
        });
        return out;
    }

    public LanguageResponse languageToResponse(Language it){
        return new LanguageResponse(it.getId(), it.getName(), it.getType(), it.getScript(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Proficiency fromDto(ProficiencyDto dto, User gm, Scenario scenario){
        return new Proficiency(dto.getName(), dto.getType(), dto.isVisible(), gm, scenario);
    }

    public List<ProficiencyResponse> proficienciesToResponse(List<Proficiency> list){
        List<ProficiencyResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(proficiencyToResponse(it));
        });
        return out;
    }

    public ProficiencyResponse proficiencyToResponse(Proficiency it){
        return new ProficiencyResponse(it.getId(), it.getName(), it.getType(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Skill fromDto(SkillDto dto, User gm, Scenario scenario){
        return new Skill(dto.getName(), dto.getDescription(), dto.getAbilityScore(), dto.isVisible(), gm, scenario);
    }

    public List<SkillResponse> skillsToResponse(List<Skill> list){
        List<SkillResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(skillToResponse(it));
        });
        return out;
    }

    public SkillResponse skillToResponse(Skill it){
        return new SkillResponse(it.getId(), it.getName(), it.getDescription(), it.getAbilityScore(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Spell fromDto(SpellDto dto, User gm, Scenario scenario){
        MagicSchool magicSchool = typesService.findMagicSchoolByNameAndScenario(dto.getMagicSchool(), scenario);
        return new Spell(dto.getName(), dto.getDescription(), dto.getHigherLevels(), dto.getLevel(),
                dto.getRange(), dto.getComponents(), dto.getMaterial(), dto.isRitual(), dto.getDuration(),
                dto.isConcentration(), dto.getCastingTime(), magicSchool, dto.isVisible(),
                gm, scenario);
    }

    public List<SpellResponse> spellsToResponse(List<Spell> list){
        List<SpellResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(spellToResponse(it));
        });
        return out;
    }

    public SpellResponse spellToResponse(Spell it){
        return new SpellResponse(it.getId(), it.getName(), it.getDescription(), it.getHigherLevels(), it.getLevel(),
                it.getRange(), it.getComponents(), it.getMaterial(), it.isRitual(), it.getDuration(),
                it.isConcentration(), it.getCastingTime(), it.getMagicSchool().getName(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Trait fromDto(TraitDto dto, User gm, Scenario scenario){
        return new Trait(dto.getName(), dto.getDescription(), dto.isVisible(), gm, scenario);
    }

    public List<TraitResponse> traitsToResponse(List<Trait> list){
        List<TraitResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(traitToResponse(it));
        });
        return out;
    }

    public TraitResponse traitToResponse(Trait it){
        return new TraitResponse(it.getId(), it.getName(), it.getDescription(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Armor fromDto(ArmorDto dto, User gm, Scenario scenario){
        return new Armor(dto.getName(), new ArmorClass(dto.getArmorClass().getBase(),
                dto.getArmorClass().isDexBonus(), dto.getArmorClass().getMaxBonus()), dto.getStrMinimum(),
                dto.isStealthDisadvantage(), dto.getWeight(), dto.getCost(), dto.isVisible(), gm, scenario);
    }

    public List<ArmorResponse> armorsToResponse(List<Armor> list){
        List<ArmorResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(armorToResponse(it));
        });
        return out;
    }

    public ArmorResponse armorToResponse(Armor it){
        return new ArmorResponse(it.getId(), it.getName(), new ArmorClassJson(it.getArmorClass().getBase(),
                it.getArmorClass().isDexBonus(), it.getArmorClass().getMaxBonus()), it.getStrMinimum(),
                it.isStealthDisadvantage(), it.getWeight(), it.getCost(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Gear fromDto(GearDto dto, User gm, Scenario scenario){
        return new Gear(dto.getName(), dto.getDescription(), dto.getWeight(), dto.getCost(), dto.isVisible(), gm, scenario);
    }

    public List<GearResponse> gearToResponse(List<Gear> list){
        List<GearResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(gearToResponse(it));
        });
        return out;
    }

    public GearResponse gearToResponse(Gear it){
        return new GearResponse(it.getId(), it.getName(), it.getDescription(),
                it.getWeight(), it.getCost(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Tool fromDto(ToolDto dto, User gm, Scenario scenario){
        return new Tool(dto.getName(), dto.getDescription(), dto.getCategory(),
                dto.getWeight(), dto.getCost(), dto.isVisible(), gm, scenario);
    }

    public List<ToolResponse> toolsToResponse(List<Tool> list){
        List<ToolResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(toolToResponse(it));
        });
        return out;
    }

    public ToolResponse toolToResponse(Tool it){
        return new ToolResponse(it.getId(), it.getName(), it.getDescription(),
                it.getCategory(), it.getWeight(), it.getCost(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Vehicle fromDto(VehicleDto dto, User gm, Scenario scenario){
        return new Vehicle(dto.getName(), dto.getDescription(),
                dto.getWeight(), dto.getCost(), dto.isVisible(), gm, scenario);
    }

    public List<VehicleResponse> vehiclesToResponse(List<Vehicle> list){
        List<VehicleResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(vehicleToResponse(it));
        });
        return out;
    }

    public VehicleResponse vehicleToResponse(Vehicle it){
        return new VehicleResponse(it.getId(), it.getName(), it.getDescription(),
                it.getWeight(), it.getCost(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

    public Weapon fromDto(WeaponDto dto, User gm, Scenario scenario){
        List<WeaponProperty> properties = new ArrayList<>();
        dto.getProperties().forEach(it -> {
            properties.add(typesService.findWeaponPropertyByNameAndScenario(it, scenario));
        });
        return new Weapon(dto.getName(), dto.getCategory(), dto.getWeaponRange(), dto.getDamageDice(),
                dto.getDamageBonus(), typesService.findDamageTypeByNameAndScenario(dto.getDamageType(), scenario),
                dto.getNormalRange(), dto.getLongRange(), dto.getNormalThrowRange(),
                dto.getLongThrowRange(), properties, dto.getWeight(), dto.getCost(), dto.isVisible(),
                gm, scenario);
    }

    public List<WeaponResponse> weaponsToResponse(List<Weapon> list){
        List<WeaponResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(weaponToResponse(it));
        });
        return out;
    }

    public WeaponResponse weaponToResponse(Weapon it){
        List<String> properties = new ArrayList<>();
        it.getProperties().forEach(i -> {
            properties.add(i.getName());
        });
        return new WeaponResponse(it.getId(), it.getName(), it.getCategory(), it.getWeaponRange(), it.getDamageDice(),
                it.getDamageBonus(), it.getDamageType().getName(),
                it.getNormalRange(), it.getLongRange(), it.getNormalThrowRange(),
                it.getLongThrowRange(), properties,
                it.getWeight(), it.getCost(), it.isVisible(),
                it.getCreator() != null ? it.getCreator().getUsername() : null,
                it.getScenario() != null ? it.getScenario().getScenarioKey() : null);
    }

}
