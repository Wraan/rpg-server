package com.rpg.model.application;

import com.rpg.model.application.character.Character;
import com.rpg.model.security.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "scenarios")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String scenarioKey;
    private String password;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User gameMaster;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "scenario_users",
            joinColumns = { @JoinColumn(name = "scenario_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private List<User> players;
    @Column(length = 1023)
    private String name;
    @OneToMany(mappedBy = "scenario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Character> characters;

    public Scenario() {
    }

    public Scenario(String key, String password, User gameMaster, List<User> players, String name) {
        this.scenarioKey = key;
        this.password = password;
        this.gameMaster = gameMaster;
        this.players = players;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScenarioKey() {
        return scenarioKey;
    }

    public void setScenarioKey(String scenarioKey) {
        this.scenarioKey = scenarioKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getGameMaster() {
        return gameMaster;
    }

    public void setGameMaster(User gameMaster) {
        this.gameMaster = gameMaster;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
