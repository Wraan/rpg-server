package com.rpg.repository.dnd.character;

import com.rpg.model.dnd.character.CharacterSpells;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterSpellsRepository extends JpaRepository<CharacterSpells, Long> {
}
