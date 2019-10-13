package com.rpg.repository.dnd.abilities;

import com.rpg.model.dnd.abilities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturesRepository extends JpaRepository<Feature, Long> {
}
