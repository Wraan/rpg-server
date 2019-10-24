package com.rpg.service.dnd;

import com.rpg.dto.dnd.abilities.*;
import com.rpg.exception.NameExistsInScenarioException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.TypeDoesNotExist;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.*;
import com.rpg.repository.dnd.abilities.*;
import com.rpg.service.DndDtoConverter;
import com.rpg.service.application.ScenarioService;
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

    @Autowired private DndDtoConverter dtoConverter;
    @Autowired private ScenarioService scenarioService;


    public Feature save(FeatureDto dto) throws Exception {
        Feature it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(featuresRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return featuresRepository.save(it);
    }

    public List<Feature> findFeaturesByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Feature> list = new ArrayList<>(
                featuresRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(featuresRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Feature> findFeaturesByNameContaining(String name){
        return featuresRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Feature> findFeaturesByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Feature> list = new ArrayList<>(
                featuresRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(featuresRepository.findByScenario(scenario));
        return list;
    }

    public List<Feature> findFeatures(){
        return featuresRepository.findByScenario(null);
    }
    public Feature findFeatureById(long id){
        return featuresRepository.findById(id).orElse(null);
    }

    public Language save(LanguageDto dto) throws Exception {
        Language it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(languagesRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return languagesRepository.save(it);
    }

    public List<Language> findLanguagesByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Language> list = new ArrayList<>(
                languagesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(languagesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Language> findLanguagesByNameContaining(String name){
        return languagesRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Language> findLanguagesByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Language> list = new ArrayList<>(
                languagesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(languagesRepository.findByScenario(scenario));
        return list;
    }

    public List<Language> findLanguages(){
        return languagesRepository.findByScenario(null);
    }
    public Language findLanguageById(long id){
        return languagesRepository.findById(id).orElse(null);
    }

    public Proficiency save(ProficiencyDto dto) throws Exception {
        Proficiency it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(proficienciesRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return proficienciesRepository.save(it);
    }

    public List<Proficiency> findProficienciesByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Proficiency> list = new ArrayList<>(
                proficienciesRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(proficienciesRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Proficiency> findProficienciesByNameContaining(String name){
        return proficienciesRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Proficiency> findProficienciesByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Proficiency> list = new ArrayList<>(
                proficienciesRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(proficienciesRepository.findByScenario(scenario));
        return list;
    }

    public List<Proficiency> findProficiencies(){
        return proficienciesRepository.findByScenario(null);
    }
    public Proficiency findProficiencyById(long id){
        return proficienciesRepository.findById(id).orElse(null);
    }

    public Skill save(SkillDto dto) throws Exception {
        Skill it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(skillsRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return skillsRepository.save(it);
    }

    public List<Skill> findSkillsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Skill> list = new ArrayList<>(
                skillsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(skillsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Skill> findSkillsByNameContaining(String name){
        return skillsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Skill> findSkillsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Skill> list = new ArrayList<>(
                skillsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(skillsRepository.findByScenario(scenario));
        return list;
    }

    public List<Skill> findSkills(){
        return skillsRepository.findByScenario(null);
    }
    public Skill findSkillById(long id){
        return skillsRepository.findById(id).orElse(null);
    }

    public Spell save(SpellDto dto) throws Exception {
        Spell it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");
        if(it.getMagicSchool() == null)
            throw new TypeDoesNotExist("MagicSchool does not exist");

        if(spellsRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return spellsRepository.save(it);
    }

    public List<Spell> findSpellsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Spell> list = new ArrayList<>(
                spellsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(spellsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Spell> findSpellsByNameContaining(String name){
        return spellsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Spell> findSpellsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Spell> list = new ArrayList<>(
                spellsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(spellsRepository.findByScenario(scenario));
        return list;
    }

    public List<Spell> findSpells(){
        return spellsRepository.findByScenario(null);
    }
    public Spell findSpellById(long id){
        return spellsRepository.findById(id).orElse(null);
    }

    public Trait save(TraitDto dto) throws Exception {
        Trait it = dtoConverter.fromDto(dto);
        if(it.getCreator() == null)
            throw new UserDoesNotExistException("User does not exist");
        if(it.getScenario() == null)
            throw new ScenarioDoesNotExistException("Scenario does not exist");

        if(traitsRepository.existsByNameAndScenario(it.getName(), it.getScenario()))
            throw new NameExistsInScenarioException("Name already exists in that scenario. Must be unique");
        else return traitsRepository.save(it);
    }

    public List<Trait> findTraitsByNameContainingAndScenarioKey(String name, String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Trait> list = new ArrayList<>(
                traitsRepository.findByNameIgnoreCaseContainingAndScenario(name, null));
        if(scenario != null)
            list.addAll(traitsRepository.findByNameIgnoreCaseContainingAndScenario(name, scenario));
        return list;
    }

    public List<Trait> findTraitsByNameContaining(String name){
        return traitsRepository.findByNameIgnoreCaseContainingAndScenario(name, null);
    }

    public List<Trait> findTraitsByScenarioKey(String scenarioKey){
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        List<Trait> list = new ArrayList<>(
                traitsRepository.findByScenario(null));
        if(scenario != null)
            list.addAll(traitsRepository.findByScenario(scenario));
        return list;
    }

    public List<Trait> findTraits(){
        return traitsRepository.findByScenario(null);
    }
    public Trait findTraitById(long id){
        return traitsRepository.findById(id).orElse(null);
    }
}
