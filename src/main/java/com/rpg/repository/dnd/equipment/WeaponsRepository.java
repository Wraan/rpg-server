package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaponsRepository extends JpaRepository<Weapon, Long> {
    List<Weapon> findByNameIgnoreCaseContaining(String name);
    List<Weapon> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Weapon> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
