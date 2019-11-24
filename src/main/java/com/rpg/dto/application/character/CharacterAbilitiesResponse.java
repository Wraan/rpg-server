package com.rpg.dto.application.character;

import java.util.Set;

public class CharacterAbilitiesResponse {
    private Set<String> features;
    private Set<String> traits;
    private Set<String> languages;
    private Set<String> proficiencies;

    public CharacterAbilitiesResponse() {
    }

    public CharacterAbilitiesResponse(Set<String> features, Set<String> traits, Set<String> languages, Set<String> proficiencies) {
        this.features = features;
        this.traits = traits;
        this.languages = languages;
        this.proficiencies = proficiencies;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }

    public Set<String> getTraits() {
        return traits;
    }

    public void setTraits(Set<String> traits) {
        this.traits = traits;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public Set<String> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(Set<String> proficiencies) {
        this.proficiencies = proficiencies;
    }
}
