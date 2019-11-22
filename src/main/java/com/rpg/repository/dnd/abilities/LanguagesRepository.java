package com.rpg.repository.dnd.abilities;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.abilities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Long> {
    List<Language> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Language> findByScenario(Scenario scenario);
    List<Language> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Language> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Language> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
