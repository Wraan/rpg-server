package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Long> {
    List<Language> findByNameIgnoreCaseContaining(String name);
}
