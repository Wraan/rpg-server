package com.rpg.repository.application;

import com.rpg.model.application.Message;
import com.rpg.model.application.Scenario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByScenarioOrderByIdDesc(Scenario scenario, Pageable pageable);
    List<Message> findByScenario(Scenario scenario, Pageable pageable);
}
