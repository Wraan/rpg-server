package com.rpg.model.dnd.character.equipment;

import com.rpg.model.dnd.equipment.Vehicle;

import javax.persistence.*;

@Entity
@Table(name = "character_vehicles")
public class VehicleAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "character_equipment_id")
    private CharacterEquipment characterEquipment;
    private int amount;

    public VehicleAmount() {
    }

    public VehicleAmount(Vehicle vehicle, CharacterEquipment characterEquipment, int amount) {
        this.vehicle = vehicle;
        this.characterEquipment = characterEquipment;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public CharacterEquipment getCharacterEquipment() {
        return characterEquipment;
    }

    public void setCharacterEquipment(CharacterEquipment characterEquipment) {
        this.characterEquipment = characterEquipment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
