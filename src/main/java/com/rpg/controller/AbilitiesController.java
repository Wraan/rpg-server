package com.rpg.controller;

import com.rpg.model.dnd.abilities.Spell;
import com.rpg.repository.dnd.abilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

//    @GetMapping("/skill/{id}")
//
//    @GetMapping("/skill")

    @GetMapping("/spell/{id}")
    public Spell getSpellId(@PathVariable("id") long id) {
        return spellsRepository.findById(id).get();
    }

    @GetMapping("/spell")
    public String getSpellId(@RequestParam("name") Optional<String> name) {
        if(name.isPresent()) return "Param exists";
        return "Param does not exist";
    }

}
