package com.rpg.controller.dnd;

import com.rpg.dto.dnd.abilities.*;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.AbilitiesService;
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
public class AbilitiesController {

    @Autowired private AbilitiesService abilitiesService;
    @Autowired private DndDtoConverter dtoConverter;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/spell/{id}")
    public SpellResponse getSpellById(@PathVariable("id") long id) {
        return dtoConverter.spellToResponse(abilitiesService.findSpellById(id));
    }

    @GetMapping("/spell")
    public List<SpellResponse> getSpellsByName(@RequestParam(value = "name") Optional<String> name,
                                               @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.spellsToResponse(abilitiesService.findSpellsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.spellsToResponse(abilitiesService.findSpellsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.spellsToResponse(abilitiesService.findSpellsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.spellsToResponse(abilitiesService.findSpells());
    }

    @GetMapping("/skill/{id}")
    public SkillResponse getSkillById(@PathVariable("id") long id) {
        return dtoConverter.skillToResponse(abilitiesService.findSkillById(id));
    }

    @GetMapping("/skill")
    public List<SkillResponse> getSkills(@RequestParam(value = "name") Optional<String> name,
                                 @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.skillsToResponse(abilitiesService.findSkillsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.skillsToResponse(abilitiesService.findSkillsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.skillsToResponse(abilitiesService.findSkillsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.skillsToResponse(abilitiesService.findSkills());
    }

    @GetMapping("/feature/{id}")
    public FeatureResponse getFeatureById(@PathVariable("id") long id) {
        return dtoConverter.featureToResponse(abilitiesService.findFeatureById(id));
    }

    @GetMapping("/feature")
    public List<FeatureResponse> getFeatures(@RequestParam(value = "name") Optional<String> name,
                                             @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.featuresToResponse(abilitiesService.findFeaturesByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.featuresToResponse(abilitiesService.findFeaturesByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.featuresToResponse(abilitiesService.findFeaturesByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.featuresToResponse(abilitiesService.findFeatures());
    }

    @GetMapping("/language/{id}")
    public LanguageResponse getLanguageById(@PathVariable("id") long id) {
        return dtoConverter.languageToResponse(abilitiesService.findLanguageById(id));
    }

    @GetMapping("/language")
    public List<LanguageResponse> getLanguages(@RequestParam(value = "name") Optional<String> name,
                                               @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.languagesToResponse(abilitiesService.findLanguagesByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.languagesToResponse(abilitiesService.findLanguagesByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.languagesToResponse(abilitiesService.findLanguagesByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.languagesToResponse(abilitiesService.findLanguages());
    }

    @GetMapping("/proficiency/{id}")
    public ProficiencyResponse getProficiencyById(@PathVariable("id") long id) {
        return dtoConverter.proficiencyToResponse(abilitiesService.findProficiencyById(id));
    }

    @GetMapping("/proficiency")
    public List<ProficiencyResponse> getProficiencies(@RequestParam(value = "name") Optional<String> name,
                                                      @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.proficienciesToResponse(abilitiesService.findProficienciesByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.proficienciesToResponse(abilitiesService.findProficienciesByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.proficienciesToResponse(abilitiesService.findProficienciesByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.proficienciesToResponse(abilitiesService.findProficiencies());
    }

    @GetMapping("/trait/{id}")
    public TraitResponse getTraitById(@PathVariable("id") long id) {
        return dtoConverter.traitToResponse(abilitiesService.findTraitById(id));
    }

    @GetMapping("/trait")
    public List<TraitResponse> getTraits(@RequestParam(value = "name") Optional<String> name,
                                 @RequestParam(value = "scenarioKey") Optional<String> scenarioKey) {
        if(name.isPresent() && scenarioKey.isPresent())
            return dtoConverter.traitsToResponse(abilitiesService.findTraitsByNameContainingAndScenarioKey(name.get(), scenarioKey.get().toUpperCase()));
        else if(name.isPresent())
            return dtoConverter.traitsToResponse(abilitiesService.findTraitsByNameContaining(name.get()));
        else if(scenarioKey.isPresent())
            return dtoConverter.traitsToResponse(abilitiesService.findTraitsByScenarioKey(scenarioKey.get().toUpperCase()));

        return dtoConverter.traitsToResponse(abilitiesService.findTraits());
    }

    @PostMapping("/feature")
    public ResponseEntity addCustomFeature(@RequestBody FeatureDto dto){
        try {
            abilitiesService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/language")
    public ResponseEntity addCustomLanguage(@RequestBody LanguageDto dto){
        try {
            abilitiesService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/proficiency")
    public ResponseEntity addCustomProficiency(@RequestBody ProficiencyDto dto){
        try {
            abilitiesService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/skill")
    public ResponseEntity addCustomSkill(@RequestBody SkillDto dto){
        try {
            abilitiesService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/spell")
    public ResponseEntity addCustomSpell(@RequestBody SpellDto dto){
        try {
            abilitiesService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/trait")
    public ResponseEntity addCustomTrait(@RequestBody TraitDto dto){
        try {
            abilitiesService.save(dto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
