package com.rpg.model.application;

import javax.persistence.*;

@Entity
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
