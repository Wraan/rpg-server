package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeaturesRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByNameIgnoreCaseContaining(String name);
    List<Feature> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Feature> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
