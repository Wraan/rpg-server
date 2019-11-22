package com.rpg.service.dnd;

import com.rpg.dto.dnd.abilities.*;
import com.rpg.exception.NameExistsInScenarioException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.TypeDoesNotExist;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.*;
import com.rpg.model.dnd.types.MagicSchool;
import com.rpg.model.security.User;
import com.rpg.repository.dnd.abilities.*;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.DndDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbilitiesService {

    @Autowired private SkillsRepository skillsRepository;
    @Autowired private TraitsRepository traitsRepository;
    @Autowired private FeaturesRepository featuresRepository;
    @Autowired private ProficienciesRepository proficienciesRepository;
    @Autowired private LanguagesRepository languagesRepository;
    @Autowired private SpellsRepository spellsRepository;

    @Autowired private TypesService typesService;

    @Autowired private DndDtoConverter dtoConverter;
    @Autowired private ScenarioService scenarioService;

    public Feature add(FeatureDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(featuresRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Feature feature = dtoConverter.fromDto(dto, gm, scenario);
        return featuresRepository.save(feature);
    }

    public List<Feature> findFeaturesByNameContainingAndScenario(String name, Scenario scenario){
        List<Feature> list = new ArrayList<>(
                featuresRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(featuresRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Feature> findFeaturesByScenario(Scenario scenario){
        List<Feature> list = new ArrayList<>(
                featuresRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(featuresRepository.findByScenario(scenario));
        return list;
    }

    public Feature findFeatureByNameAndScenario(String name, Scenario scenario) {
        Feature feature = featuresRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (feature == null) return featuresRepository.findByNameAndScenario(name, null).orElse(null);
        return feature;
    }

    public List<Feature> findFeaturesByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Feature> list = new ArrayList<>(
                featuresRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(featuresRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Feature> findFeaturesByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Feature> list = new ArrayList<>(
                featuresRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(featuresRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Feature feature, FeatureDto dto) {
        feature.setDescription(dto.getDescription());
        feature.setVisible(dto.isVisible());
        featuresRepository.save(feature);
    }

    public void delete(Feature feature) {
        featuresRepository.delete(feature);
    }

    public Language add(LanguageDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(languagesRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Language language = dtoConverter.fromDto(dto, gm, scenario);
        return languagesRepository.save(language);
    }

    public List<Language> findLanguagesByNameContainingAndScenario(String name, Scenario scenario){
        List<Language> list = new ArrayList<>(
                languagesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(languagesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Language> findLanguagesByScenario(Scenario scenario){
        List<Language> list = new ArrayList<>(
                languagesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(languagesRepository.findByScenario(scenario));
        return list;
    }

    public Language findLanguageByNameAndScenario(String name, Scenario scenario) {
        Language language = languagesRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (language == null) return languagesRepository.findByNameAndScenario(name, null).orElse(null);
        return language;
    }

    public List<Language> findLanguagesByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Language> list = new ArrayList<>(
                languagesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(languagesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Language> findLanguagesByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Language> list = new ArrayList<>(
                languagesRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(languagesRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Language language, LanguageDto dto) {
        language.setScript(dto.getScript());
        language.setType(dto.getType());
        language.setVisible(dto.isVisible());
        languagesRepository.save(language);
    }

    public void delete(Language language) {
        languagesRepository.delete(language);
    }

    public Proficiency add(ProficiencyDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(proficienciesRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Proficiency proficiency = dtoConverter.fromDto(dto, gm, scenario);
        return proficienciesRepository.save(proficiency);
    }

    public List<Proficiency> findProficienciesByNameContainingAndScenario(String name, Scenario scenario){
        List<Proficiency> list = new ArrayList<>(
                proficienciesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(proficienciesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Proficiency> findProficienciesByScenario(Scenario scenario){
        List<Proficiency> list = new ArrayList<>(
                proficienciesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(proficienciesRepository.findByScenario(scenario));
        return list;
    }

    public Proficiency findProficiencyByNameAndScenario(String name, Scenario scenario) {
        Proficiency proficiency = proficienciesRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (proficiency == null) return proficienciesRepository.findByNameAndScenario(name, null).orElse(null);
        return proficiency;
    }

    public List<Proficiency> findProficienciesByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Proficiency> list = new ArrayList<>(
                proficienciesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(proficienciesRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Proficiency> findProficienciesByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Proficiency> list = new ArrayList<>(
                proficienciesRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(proficienciesRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Proficiency proficiency, ProficiencyDto dto) {
        proficiency.setType(dto.getType());
        proficiency.setVisible(dto.isVisible());
        proficienciesRepository.save(proficiency);
    }

    public void delete(Proficiency proficiency) {
        proficienciesRepository.delete(proficiency);
    }

    public Skill add(SkillDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(skillsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Skill skill = dtoConverter.fromDto(dto, gm, scenario);
        return skillsRepository.save(skill);
    }

    public List<Skill> findSkillsByNameContainingAndScenario(String name, Scenario scenario){
        List<Skill> list = new ArrayList<>(
                skillsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(skillsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Skill> findSkillsByScenario(Scenario scenario){
        List<Skill> list = new ArrayList<>(
                skillsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(skillsRepository.findByScenario(scenario));
        return list;
    }

    public Skill findSkillByNameAndScenario(String name, Scenario scenario) {
        Skill skill = skillsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (skill == null) return skillsRepository.findByNameAndScenario(name, null).orElse(null);
        return skill;
    }

    public List<Skill> findSkillsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Skill> list = new ArrayList<>(
                skillsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(skillsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Skill> findSkillsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Skill> list = new ArrayList<>(
                skillsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(skillsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Skill skill, SkillDto dto) {
        skill.setDescription(dto.getDescription());
        skill.setAbilityScore(dto.getAbilityScore());
        skill.setVisible(dto.isVisible());
        skillsRepository.save(skill);
    }

    public void delete(Skill skill) {
        skillsRepository.delete(skill);
    }

    public Spell add(SpellDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(spellsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Spell spell = dtoConverter.fromDto(dto, gm, scenario);
        if(spell.getMagicSchool() == null)
            throw new TypeDoesNotExist("MagicSchool does not exist");

        return spellsRepository.save(spell);
    }

    public List<Spell> findSpellsByNameContainingAndScenario(String name, Scenario scenario){
        List<Spell> list = new ArrayList<>(
                spellsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(spellsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Spell> findSpellsByScenario(Scenario scenario){
        List<Spell> list = new ArrayList<>(
                spellsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(spellsRepository.findByScenario(scenario));
        return list;
    }

    public Spell findSpellByNameAndScenario(String name, Scenario scenario) {
        Spell spell = spellsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (spell == null) return spellsRepository.findByNameAndScenario(name, null).orElse(null);
        return spell;
    }

    public List<Spell> findSpellsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Spell> list = new ArrayList<>(
                spellsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(spellsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Spell> findSpellsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Spell> list = new ArrayList<>(
                spellsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(spellsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Spell spell, SpellDto dto) throws Exception {
        MagicSchool magicSchool =
                typesService.findMagicSchoolByNameAndScenario(dto.getMagicSchool(), spell.getScenario());
        if(magicSchool == null)
            throw new TypeDoesNotExist("MagicSchool does not exist");
        spell.setMagicSchool(magicSchool);
        spell.setDescription(dto.getDescription());
        spell.setHigherLevels(dto.getHigherLevels());
        spell.setLevel(dto.getLevel());
        spell.setRange(dto.getRange());
        spell.setComponents(dto.getComponents());
        spell.setMaterial(dto.getMaterial());
        spell.setRitual(dto.isRitual());
        spell.setDuration(dto.getDuration());
        spell.setConcentration(dto.isConcentration());
        spell.setCastingTime(dto.getCastingTime());
        spell.setVisible(dto.isVisible());
        spellsRepository.save(spell);
    }

    public void delete(Spell spell) {
        spellsRepository.delete(spell);
    }

    public Trait add(TraitDto dto, User gm, Scenario scenario) throws Exception {
        if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
            throw new UserDoesNotExistException("Only GameMaster can modify or add items, types and abilities in scenario");
        if(traitsRepository.existsByNameAndScenario(dto.getName(), scenario))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");

        Trait trait = dtoConverter.fromDto(dto, gm, scenario);
        return traitsRepository.save(trait);
    }

    public List<Trait> findTraitsByNameContainingAndScenario(String name, Scenario scenario){
        List<Trait> list = new ArrayList<>(
                traitsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(traitsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Trait> findTraitsByScenario(Scenario scenario){
        List<Trait> list = new ArrayList<>(
                traitsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(traitsRepository.findByScenario(scenario));
        return list;
    }

    public Trait findTraitByNameAndScenario(String name, Scenario scenario) {
        Trait trait = traitsRepository.findByNameAndScenario(name, scenario).orElse(null);
        if (trait == null) return traitsRepository.findByNameAndScenario(name, null).orElse(null);
        return trait;
    }

    public List<Trait> findTraitsByNameContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible) {
        List<Trait> list = new ArrayList<>(
                traitsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, null, visible));
        if(scenario != null)
            list.addAll(traitsRepository.findByNameIgnoreCaseContainingAndScenarioAndVisible(name, scenario, visible));
        return list;
    }

    public List<Trait> findTraitsByScenarioAndVisible(Scenario scenario, boolean visible) {
        List<Trait> list = new ArrayList<>(
                traitsRepository.findByScenarioAndVisible(null, visible));
        if(scenario != null)
            list.addAll(traitsRepository.findByScenarioAndVisible(scenario, visible));
        return list;
    }

    public void patchValues(Trait trait, TraitDto dto) {
        trait.setDescription(dto.getDescription());
        trait.setVisible(dto.isVisible());
        traitsRepository.save(trait);
    }

    public void delete(Trait trait) {
        traitsRepository.delete(trait);
    }

    public void deleteByScenario(Scenario scenario) {
        skillsRepository.deleteByScenario(scenario);
        traitsRepository.deleteByScenario(scenario);
        featuresRepository.deleteByScenario(scenario);
        proficienciesRepository.deleteByScenario(scenario);
        languagesRepository.deleteByScenario(scenario);
        spellsRepository.deleteByScenario(scenario);
    }
}
