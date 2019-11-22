package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Gear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GearRepository extends JpaRepository<Gear, Long> {
    List<Gear> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Gear> findByScenario(Scenario scenario);
    List<Gear> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Gear> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Gear> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
