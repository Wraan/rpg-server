package com.rpg.repository.dnd.character;

import com.rpg.model.dnd.character.CharacterAbilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterAbilitiesRepository extends JpaRepository<CharacterAbilities, Long> {
}
