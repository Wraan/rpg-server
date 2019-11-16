package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeaturesRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Feature> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Feature> findByScenarioAndVisible(Scenario scenario, boolean visible);
    List<Feature> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Feature> findByNameAndScenario(String name, Scenario scenario);
}
