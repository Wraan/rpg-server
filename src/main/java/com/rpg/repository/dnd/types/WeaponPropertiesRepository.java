package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.WeaponProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeaponPropertiesRepository extends JpaRepository<WeaponProperty, Long> {
    List<WeaponProperty> findByNameIgnoreCaseContaining(String name);
    List<WeaponProperty> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<WeaponProperty> findByScenario(Scenario scenario);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<WeaponProperty> findByNameAndScenario(String name, Scenario scenario);
}
