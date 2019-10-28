package com.rpg.repository.application;

import com.rpg.model.application.Message;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByScenario(Scenario scenario, Pageable pageable);
}
