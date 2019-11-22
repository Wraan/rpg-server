package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByNameIgnoreCaseContainingAndScenario(String name, Scenario scenario);
    List<Vehicle> findByScenario(Scenario scenario);
    List<Vehicle> findByNameIgnoreCaseContainingAndScenarioAndVisible(String name, Scenario scenario, boolean visible);
    List<Vehicle> findByScenarioAndVisible(Scenario scenario, boolean visible);
    boolean existsByNameAndScenario(String name, Scenario scenario);
    Optional<Vehicle> findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
