package com.rpg.service.converter;

import com.rpg.dto.dnd.character.*;
import com.rpg.dto.application.NoteResponse;
import com.rpg.dto.application.ScenarioResponse;
import com.rpg.dto.dnd.character.equipment.CharacterEquipmentResponse;
import com.rpg.dto.dnd.character.equipment.EquipmentAmountDto;
import com.rpg.model.dnd.character.*;
import com.rpg.model.application.Note;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.character.Character;
import com.rpg.model.dnd.character.equipment.Attack;
import com.rpg.model.dnd.character.equipment.CharacterEquipment;
import com.rpg.service.application.ScenarioSessionService;
import com.rpg.service.dnd.AbilitiesService;
import com.rpg.service.dnd.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationConverter {

    @Autowired private ScenarioSessionService scenarioSessionService;
    @Autowired private AbilitiesService abilitiesService;
    @Autowired private EquipmentService equipmentService;

    public List<ScenarioResponse> scenariosToResponse(List<Scenario> list){
        List<ScenarioResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(scenarioToResponse(it));
        });
        return out;
    }

    public ScenarioResponse scenarioToResponse(Scenario it){
        return new ScenarioResponse(it.getName(), it.getGameMaster().getUsername(), it.getScenarioKey(),
                scenarioSessionService.getOnlineUsernamesFromScenario(it));
    }

    public List<CharacterResponse> charactersToResponse(List<Character> list){
        List<CharacterResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(characterToResponse(it));
        });
        return out;
    }

    public CharacterResponse characterToResponse(Character it){
        return new CharacterResponse(it.getName(), it.getRace(), it.getProfession(), it.getLevel(),
                it.getBackground(), it.getExperience(), it.getAlignment(), it.getProficiency(), it.getPassivePerception(),
                it.getPassiveInsight(), it.getInitiative(), it.getSpeed(), it.getInspiration(), new AttributesDto(it.getAttributes().getStrength(),
                it.getAttributes().getDexterity(), it.getAttributes().getConstitution(), it.getAttributes().getIntelligence(),
                it.getAttributes().getWisdom(),  it.getAttributes().getCharisma()), new HealthDto(it.getHealth().getMaxHealth(),
                it.getHealth().getTemporaryHealth(), it.getHealth().getActualHealth()), new HitDicesDto(it.getHitDices().getDice(),
                it.getHitDices().getTotal(), it.getHitDices().getUsed()), characterAbilitiesToResponse(it.getAbilities()),
                characterSpellsToResponse(it.getSpells()), characterEquipmentToResponse(it.getEquipment()),
                it.getOwner() != null ? it.getOwner().getUsername() : null);
    }

    public List<NoteResponse> notesToResponse(List<Note> list) {
        List<NoteResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(noteToResponse(it));
        });
        return out;
    }

    public NoteResponse noteToResponse(Note note){
        return new NoteResponse(note.getId(), note.getName(), note.getContent());
    }

    public CharacterEquipmentResponse characterEquipmentToResponse(CharacterEquipment characterEquipment){
        List<EquipmentAmountDto> armors = equipmentService.getEQAmountFromArmors(characterEquipment.getArmors());
        List<EquipmentAmountDto> gear = equipmentService.getEQAmountFromGear(characterEquipment.getGear());
        List<EquipmentAmountDto> weapons = equipmentService.getEQAmountFromWeapons(characterEquipment.getWeapons());
        List<EquipmentAmountDto> vehicles = equipmentService.getEQAmountFromVehicles(characterEquipment.getVehicles());
        List<EquipmentAmountDto> tools = equipmentService.getEQAmountFromTools(characterEquipment.getTools());
        List<AttackDto> attacks = attacksToResponse(characterEquipment.getAttacks());

        return new CharacterEquipmentResponse(characterEquipment.getArmorClass(), armors, gear, vehicles, tools,
                weapons, attacks, equipmentService.convertCurrency(characterEquipment.getCurrency()));
    }

    public CharacterAbilitiesResponse characterAbilitiesToResponse(CharacterAbilities characterAbilities){
        Set<String> features = abilitiesService.getNamesFromFeatures(characterAbilities.getFeatures());
        Set<String> traits = abilitiesService.getNamesFromTraits(characterAbilities.getTraits());
        Set<String> languages = abilitiesService.getNamesFromLanguages(characterAbilities.getLanguages());
        Set<String> proficiencies = abilitiesService.getNamesFromProficiencies(characterAbilities.getProficiencies());
        return new CharacterAbilitiesResponse(features, traits, languages, proficiencies);
    }

    public CharacterSpellsResponse characterSpellsToResponse(CharacterSpells characterSpells){
        Set<String> spells = abilitiesService.getNamesFromSpells(characterSpells.getSpells());
        List<SpellSlotDto> spellSlots = spellSlotsToResponse(characterSpells.getSpellSlots());

        return new CharacterSpellsResponse(spells, spellSlots, characterSpells.getSpellSaveDc(), characterSpells.getBaseStat(),
                characterSpells.getSpellAttackBonus());
    }

    private List<SpellSlotDto> spellSlotsToResponse(Set<SpellSlot> spellSlots) {
        List<SpellSlotDto> list = new ArrayList<>();
        spellSlots.forEach(it ->{
            list.add(spellSlotToResponse(it));
        });
        return list;
    }

    private SpellSlotDto spellSlotToResponse(SpellSlot it) {
        return new SpellSlotDto(it.getTotal(), it.getUsed(), it.getLevel());
    }


    public List<AttackDto> attacksToResponse(List<Attack> attacks) {
        List<AttackDto> convertedAttacks = new ArrayList<>();
        attacks.forEach(attack -> {
            convertedAttacks.add(new AttackDto(attack.getName(), attack.getBonus(), attack.getDamage(), attack.getDamageType().getName()));
        });
        return convertedAttacks;
    }
}
