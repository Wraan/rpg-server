package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Armor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArmorsRepository extends JpaRepository<Armor, Long> {
    List<Armor> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Armor> findByScenario(Scenario scenario);
    List<Armor> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Armor> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Armor> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
    Set<Armor> findByNameInAndScenario(Set<String> names, Scenario scenario);
}
