package com.rpg.repository.application;

import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    Optional<Scenario> findByScenarioKey(String key);
    List<Scenario> findByGameMaster(User user);
}
