package com.rpg.controller.dnd;

import com.rpg.dto.dnd.abilities.*;
import com.rpg.exception.PrivilageException;
import com.rpg.exception.ScenarioDoesNotExistException;
import com.rpg.exception.ScenarioException;
import com.rpg.exception.UserDoesNotExistException;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.*;
import com.rpg.model.security.User;
import com.rpg.service.application.ScenarioService;
import com.rpg.service.converter.DndDtoConverter;
import com.rpg.service.dnd.AbilitiesService;
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
public class AbilitiesController {

    @Autowired private UserService userService;
    @Autowired private ScenarioService scenarioService;

    @Autowired private AbilitiesService abilitiesService;
    @Autowired private DndDtoConverter dtoConverter;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @GetMapping("/scenario/{scenarioKey}/feature")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getFeaturesByName(@RequestParam(value = "name") Optional<String> name,
                                              @PathVariable("scenarioKey") String scenarioKey,
                                              Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.featuresToResponse(
                        abilitiesService.findFeaturesByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.featuresToResponse(
                        abilitiesService.findFeaturesByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.featuresToResponse(
                        abilitiesService.findFeaturesByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.featuresToResponse(
                        abilitiesService.findFeaturesByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/feature")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomFeature(@RequestBody FeatureDto featureDto,
                                             @PathVariable("scenarioKey") String scenarioKey,
                                             Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            abilitiesService.add(featureDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/feature/{featureName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteFeature(@PathVariable("scenarioKey") String scenarioKey,
                                          @PathVariable("featureName") String featureName,
                                          Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Feature feature = abilitiesService.findFeatureByNameAndScenario(featureName, scenario);
            if(feature == null || feature.getScenario() == null)
                throw new NotFoundException("Feature not found or does not belong to current scenario");

            abilitiesService.delete(feature);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/feature")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchFeature(@RequestBody FeatureDto featureDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Feature feature = abilitiesService.findFeatureByNameAndScenario(featureDto.getName(), scenario);
            if(feature == null || feature.getScenario() == null)
                throw new NotFoundException("Feature not found or does not belong to current scenario");

            abilitiesService.patchValues(feature, featureDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/language")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getLanguagesByName(@RequestParam(value = "name") Optional<String> name,
                                            @PathVariable("scenarioKey") String scenarioKey,
                                            Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.languagesToResponse(
                        abilitiesService.findLanguagesByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.languagesToResponse(
                        abilitiesService.findLanguagesByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.languagesToResponse(
                        abilitiesService.findLanguagesByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.languagesToResponse(
                        abilitiesService.findLanguagesByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/language")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomLanguage(@RequestBody LanguageDto languageDto,
                                           @PathVariable("scenarioKey") String scenarioKey,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            abilitiesService.add(languageDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/language/{languageName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteLanguage(@PathVariable("scenarioKey") String scenarioKey,
                                        @PathVariable("languageName") String languageName,
                                        Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Language language = abilitiesService.findLanguageByNameAndScenario(languageName, scenario);
            if(language == null || language.getScenario() == null)
                throw new NotFoundException("Language not found or does not belong to current scenario");

            abilitiesService.delete(language);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/language")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchLanguage(@RequestBody LanguageDto languageDto,
                                       @PathVariable("scenarioKey") String scenarioKey,
                                       Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Language language = abilitiesService.findLanguageByNameAndScenario(languageDto.getName(), scenario);
            if(language == null || language.getScenario() == null)
                throw new NotFoundException("Language not found or does not belong to current scenario");

            abilitiesService.patchValues(language, languageDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/proficiency")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getProficienciesByName(@RequestParam(value = "name") Optional<String> name,
                                             @PathVariable("scenarioKey") String scenarioKey,
                                             Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.proficienciesToResponse(
                        abilitiesService.findProficienciesByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.proficienciesToResponse(
                        abilitiesService.findProficienciesByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.proficienciesToResponse(
                        abilitiesService.findProficienciesByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.proficienciesToResponse(
                        abilitiesService.findProficienciesByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/proficiency")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomProficiency(@RequestBody ProficiencyDto proficiencyDto,
                                            @PathVariable("scenarioKey") String scenarioKey,
                                            Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            abilitiesService.add(proficiencyDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/proficiency/{proficiencyName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteProficiency(@PathVariable("scenarioKey") String scenarioKey,
                                         @PathVariable("proficiencyName") String proficiencyName,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Proficiency proficiency = abilitiesService.findProficiencyByNameAndScenario(proficiencyName, scenario);
            if(proficiency == null || proficiency.getScenario() == null)
                throw new NotFoundException("Proficiency not found or does not belong to current scenario");

            abilitiesService.delete(proficiency);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/proficiency")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchProficiency(@RequestBody ProficiencyDto proficiencyDto,
                                        @PathVariable("scenarioKey") String scenarioKey,
                                        Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Proficiency proficiency = abilitiesService.findProficiencyByNameAndScenario(proficiencyDto.getName(), scenario);
            if(proficiency == null || proficiency.getScenario() == null)
                throw new NotFoundException("Proficiency not found or does not belong to current scenario");

            abilitiesService.patchValues(proficiency, proficiencyDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/skill")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getSkillsByName(@RequestParam(value = "name") Optional<String> name,
                                                 @PathVariable("scenarioKey") String scenarioKey,
                                                 Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.skillsToResponse(
                        abilitiesService.findSkillsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.skillsToResponse(
                        abilitiesService.findSkillsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.skillsToResponse(
                        abilitiesService.findSkillsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.skillsToResponse(
                        abilitiesService.findSkillsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/skill")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomSkill(@RequestBody SkillDto skillDto,
                                               @PathVariable("scenarioKey") String scenarioKey,
                                               Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            abilitiesService.add(skillDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/skill/{skillName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteSkill(@PathVariable("scenarioKey") String scenarioKey,
                                            @PathVariable("skillName") String skillName,
                                            Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Skill skill = abilitiesService.findSkillByNameAndScenario(skillName, scenario);
            if(skill == null || skill.getScenario() == null)
                throw new NotFoundException("Skill not found or does not belong to current scenario");

            abilitiesService.delete(skill);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/skill")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchProficiency(@RequestBody SkillDto skillDto,
                                           @PathVariable("scenarioKey") String scenarioKey,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Skill skill = abilitiesService.findSkillByNameAndScenario(skillDto.getName(), scenario);
            if(skill == null || skill.getScenario() == null)
                throw new NotFoundException("Skill not found or does not belong to current scenario");

            abilitiesService.patchValues(skill, skillDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/scenario/{scenarioKey}/spell")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getSpellsByName(@RequestParam(value = "name") Optional<String> name,
                                          @PathVariable("scenarioKey") String scenarioKey,
                                          Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.spellsToResponse(
                        abilitiesService.findSpellsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.spellsToResponse(
                        abilitiesService.findSpellsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.spellsToResponse(
                        abilitiesService.findSpellsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.spellsToResponse(
                        abilitiesService.findSpellsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/spell")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomSpell(@RequestBody SpellDto spellDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            abilitiesService.add(spellDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/spell/{spellName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteSpell(@PathVariable("scenarioKey") String scenarioKey,
                                      @PathVariable("spellName") String spellName,
                                      Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Spell spell = abilitiesService.findSpellByNameAndScenario(spellName, scenario);
            if(spell == null || spell.getScenario() == null)
                throw new NotFoundException("Spell not found or does not belong to current scenario");

            abilitiesService.delete(spell);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/spell")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchSpell(@RequestBody SpellDto spellDto,
                                           @PathVariable("scenarioKey") String scenarioKey,
                                           Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Spell spell = abilitiesService.findSpellByNameAndScenario(spellDto.getName(), scenario);
            if(spell == null || spell.getScenario() == null)
                throw new NotFoundException("Spell not found or does not belong to current scenario");

            abilitiesService.patchValues(spell, spellDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/scenario/{scenarioKey}/trait")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity getTraitsByName(@RequestParam(value = "name") Optional<String> name,
                                          @PathVariable("scenarioKey") String scenarioKey,
                                          Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try{
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserPlayerOrGameMasterInScenario(user, scenario))
                throw new UserDoesNotExistException("User is not a player in that scenario");

            if (name.isPresent() && scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.traitsToResponse(
                        abilitiesService.findTraitsByNameContainingAndScenario(name.get(), scenario)));
            else if (scenarioService.isUserGameMasterInScenario(user, scenario))
                return ResponseEntity.ok().body(dtoConverter.traitsToResponse(
                        abilitiesService.findTraitsByScenario(scenario)));
            else if (name.isPresent())
                return ResponseEntity.ok().body(dtoConverter.traitsToResponse(
                        abilitiesService.findTraitsByNameContainingAndScenarioAndVisible(name.get(), scenario, true)));
            else
                return ResponseEntity.ok().body(dtoConverter.traitsToResponse(
                        abilitiesService.findTraitsByScenarioAndVisible(scenario, true)));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/scenario/{scenarioKey}/trait")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity addCustomTrait(@RequestBody TraitDto traitDto,
                                         @PathVariable("scenarioKey") String scenarioKey,
                                         Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            abilitiesService.add(traitDto, gm, scenario);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/scenario/{scenarioKey}/trait/{traitName}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity deleteTrait(@PathVariable("scenarioKey") String scenarioKey,
                                      @PathVariable("traitName") String traitName,
                                      Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Trait trait = abilitiesService.findTraitByNameAndScenario(traitName, scenario);
            if(trait == null || trait.getScenario() == null)
                throw new NotFoundException("Trait not found or does not belong to current scenario");

            abilitiesService.delete(trait);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/scenario/{scenarioKey}/trait")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer access_token", required = true, dataType = "String",
                    paramType = "header", defaultValue="Bearer access-token")
    })
    public ResponseEntity patchTrait(@RequestBody TraitDto traitDto,
                                     @PathVariable("scenarioKey") String scenarioKey,
                                     Principal principal){
        User gm = userService.findByUsername(principal.getName());
        Scenario scenario = scenarioService.findByScenarioKey(scenarioKey);
        try {
            if(scenario == null) throw new ScenarioDoesNotExistException("Scenario does not exist");
            if(!scenarioService.isUserGameMasterInScenario(gm, scenario))
                throw new PrivilageException("Only GameMaster can modify or add items, types and abilities in scenario");
            Trait trait = abilitiesService.findTraitByNameAndScenario(traitDto.getName(), scenario);
            if(trait == null || trait.getScenario() == null)
                throw new NotFoundException("Trait not found or does not belong to current scenario");

            abilitiesService.patchValues(trait, traitDto);
            return ResponseEntity.ok().body("OK");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
