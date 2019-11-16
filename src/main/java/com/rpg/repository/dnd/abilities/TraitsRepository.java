package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraitsRepository extends JpaRepository<Trait, Long> {
    List<Trait> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Trait> findByScenario(Scenario scenario);
    List<Trait> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Trait> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Trait> findByNameAndScenario(String name, Scenario scenario);
}
