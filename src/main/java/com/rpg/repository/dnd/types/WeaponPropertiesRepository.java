package com.rpg.repository.dnd.types;

import com.rpg.model.dnd.types.WeaponProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaponPropertiesRepository extends JpaRepository<WeaponProperty, Long> {
    List<WeaponProperty> findByNameIgnoreCaseContaining(String name);
}
