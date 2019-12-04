package com.rpg.repository.dnd.character;

import com.rpg.model.dnd.character.equipment.CharacterEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterEquipmentRepository extends JpaRepository<CharacterEquipment, Long> {
}
