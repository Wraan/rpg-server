package com.rpg.repository.dnd.equipment;

import com.rpg.model.dnd.equipment.Gear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearRepository extends JpaRepository<Gear, Long> {
    List<Gear> findByNameIgnoreCaseContaining(String name);
}
