package com.rpg.repository.dnd.equipment;

import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByNameIgnoreCaseContaining(String name);
    boolean existsByNameAndScenario(String name, Scenario scenario);
}
