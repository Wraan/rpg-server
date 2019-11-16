package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProficienciesRepository extends JpaRepository<Proficiency, Long> {
    List<Proficiency> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Proficiency> findByScenario(Scenario scenario);
    List<Proficiency> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Proficiency> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Proficiency> findByNameAndScenario(String name, Scenario scenario);
}
