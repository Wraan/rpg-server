package com.rpg.controller;

import com.rpg.model.dnd.abilities.*;
import com.rpg.repository.dnd.abilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AbilitiesController {

    @Autowired private SkillsRepository skillsRepository;
    @Autowired private TraitsRepository traitsRepository;
    @Autowired private FeaturesRepository featuresRepository;
    @Autowired private SpellsRepository spellsRepository;
    @Autowired private ProficienciesRepository proficienciesRepository;
    @Autowired private LanguagesRepository languagesRepository;


    @GetMapping("/spell/{id}")
    public Spell getSpellById(@PathVariable("id") long id) {
        return spellsRepository.findById(id).get();
    }

    @GetMapping("/spell")
    public List<Spell> getSpellsByName(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return spellsRepository.findByNameIgnoreCaseContaining(name.get());
        return spellsRepository.findAll();
    }

    @GetMapping("/skill/{id}")
    public Skill getSkillById(@PathVariable("id") long id) {
        return skillsRepository.findById(id).get();
    }

    @GetMapping("/skill")
    public List<Skill> getSkills(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return skillsRepository.findByNameIgnoreCaseContaining(name.get());
        return skillsRepository.findAll();
    }

    @GetMapping("/feature/{id}")
    public Feature getFeatureById(@PathVariable("id") long id) {
        return featuresRepository.findById(id).get();
    }

    @GetMapping("/feature")
    public List<Feature> getFeatures(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return featuresRepository.findByNameIgnoreCaseContaining(name.get());
        return featuresRepository.findAll();
    }

    @GetMapping("/language/{id}")
    public Language getLanguageById(@PathVariable("id") long id) {
        return languagesRepository.findById(id).get();
    }

    @GetMapping("/language")
    public List<Language> getLanguages(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return languagesRepository.findByNameIgnoreCaseContaining(name.get());
        return languagesRepository.findAll();
    }

    @GetMapping("/proficiency/{id}")
    public Proficiency getProficiencyById(@PathVariable("id") long id) { return proficienciesRepository.findById(id).get();
    }

    @GetMapping("/proficiency")
    public List<Proficiency> getProficiencies(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return proficienciesRepository.findByNameIgnoreCaseContaining(name.get());
        return proficienciesRepository.findAll();
    }

    @GetMapping("/trait/{id}")
    public Trait getTraitById(@PathVariable("id") long id) {
        return traitsRepository.findById(id).get();
    }

    @GetMapping("/trait")
    public List<Trait> getTraits(@RequestParam(value = "name") Optional<String> name) {
        if(name.isPresent())
            return traitsRepository.findByNameIgnoreCaseContaining(name.get());
        return traitsRepository.findAll();
    }

}
