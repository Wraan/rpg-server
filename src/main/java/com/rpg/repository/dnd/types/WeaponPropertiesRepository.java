package com.rpg.repository.dnd.types;

import com.rpg.model.dnd.equipment.WeaponProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponPropertiesRepository extends JpaRepository<WeaponProperty, Long> {

}
