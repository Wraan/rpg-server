package com.rpg.model.dnd.character;

import com.rpg.model.dnd.abilities.Feature;
import com.rpg.model.dnd.abilities.Language;
import com.rpg.model.dnd.abilities.Proficiency;
import com.rpg.model.dnd.abilities.Trait;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "character_abilities")
public class CharacterAbilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_features", joinColumns = {@JoinColumn(name = "character_abilities_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "feature_id", referencedColumnName = "id")})
    private Set<Feature> features;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_traits", joinColumns = {@JoinColumn(name = "character_abilities_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "trait_id", referencedColumnName = "id")})
    private Set<Trait> traits;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_languages", joinColumns = {@JoinColumn(name = "character_abilities_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "language_id", referencedColumnName = "id")})
    private Set<Language> languages;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "character_proficiencies", joinColumns = {@JoinColumn(name = "character_abilities_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "proficiency_id", referencedColumnName = "id")})
    private Set<Proficiency> proficiencies;

    public CharacterAbilities() {
    }

    public CharacterAbilities(Set<Feature> features, Set<Trait> traits, Set<Language> languages, Set<Proficiency> proficiencies) {
        this.features = features;
        this.traits = traits;
        this.languages = languages;
        this.proficiencies = proficiencies;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Set<Trait> getTraits() {
        return traits;
    }

    public void setTraits(Set<Trait> traits) {
        this.traits = traits;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<Proficiency> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(Set<Proficiency> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
