package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponsRepository extends JpaRepository<Weapon, Long> {
    List<Weapon> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Weapon> findByScenario(Scenario scenario);
    List<Weapon> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Weapon> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Weapon> findByNameAndScenario(String name, Scenario scenario);
    boolean existsByNameAndScenarioIn(String name, List<Scenario> scenarios);
}
