package com.rpg.repository.application.character;

import com.rpg.model.application.character.CharacterEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquipmentRepository extends JpaRepository<CharacterEquipment, Long> {
}
