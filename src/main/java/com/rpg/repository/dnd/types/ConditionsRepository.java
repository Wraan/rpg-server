package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConditionsRepository extends JpaRepository<Condition, Long> {
    List<Condition> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Condition> findByScenario(Scenario scenario);
    List<Condition> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Condition> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Condition> findByNameAndScenario(String name, Scenario scenario);

}
