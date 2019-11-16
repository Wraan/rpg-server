package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToolsRepository extends JpaRepository<Tool, Long> {
    List<Tool> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Tool> findByScenario(Scenario scenario);
    List<Tool> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Tool> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Tool> findByNameAndScenario(String name, Scenario scenario);
}
