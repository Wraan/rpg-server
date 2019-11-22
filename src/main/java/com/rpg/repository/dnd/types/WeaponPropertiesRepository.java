package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.WeaponProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponPropertiesRepository extends JpaRepository<WeaponProperty, Long> {
    List<WeaponProperty> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<WeaponProperty> findByScenario(Scenario scenario);
    List<WeaponProperty> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<WeaponProperty> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    boolean existsByNameAndScenarioIn(String name, List<Scenario> scenarios);
    Optional<WeaponProperty> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
