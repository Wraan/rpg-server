package com.rpg.repository.application.character;

import com.rpg.model.application.character.CharacterSpells;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterSpellsRepository extends JpaRepository<CharacterSpells, Long> {
}
