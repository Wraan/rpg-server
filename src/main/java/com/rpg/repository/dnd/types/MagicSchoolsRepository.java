package com.rpg.repository.dnd.types;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.types.MagicSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MagicSchoolsRepository  extends JpaRepository<MagicSchool, Long> {
    List<MagicSchool> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<MagicSchool> findByScenario(Scenario scenario);
    List<MagicSchool> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<MagicSchool> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<MagicSchool> findByNameAndScenario(String name, Scenario scenario);
}
