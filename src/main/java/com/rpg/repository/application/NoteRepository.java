package com.rpg.repository.application;

import com.rpg.model.application.Note;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository  extends JpaRepository<Note, Long> {
    List<Note> findByUserAndScenario(User user, Scenario scenario);
    Note findByIdAndUserAndScenario(long id, User user, Scenario scenario);
    void deleteByScenario(Scenario scenario);



}
