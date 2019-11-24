package com.rpg.repository.application.character;

import com.rpg.model.application.character.CharacterAbilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterAbilitiesRepository extends JpaRepository<CharacterAbilities, Long> {
}
