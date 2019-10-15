package com.rpg.repository.dnd.equipment;

import com.rpg.model.dnd.equipment.Armor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArmorsRepository extends JpaRepository<Armor, Long> {
    List<Armor> findByNameIgnoreCaseContaining(String name);
}
