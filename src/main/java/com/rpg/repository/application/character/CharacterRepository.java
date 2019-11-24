package com.rpg.repository.application.character;

import com.rpg.model.application.character.Character;
import com.rpg.model.application.Scenario;
import com.rpg.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByScenario(Scenario scenario);
    List<Character> findByOwnerAndScenario(User user, Scenario scenario);
    Character findByNameAndScenario(String name, Scenario scenario);
    void deleteByScenario(Scenario scenario);
}
