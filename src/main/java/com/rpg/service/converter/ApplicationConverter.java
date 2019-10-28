package com.rpg.service.converter;

import com.rpg.dto.application.ScenarioResponse;
import com.rpg.dto.dnd.equipment.VehicleResponse;
import com.rpg.model.application.Scenario;
import com.rpg.model.dnd.equipment.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationConverter {

    public List<ScenarioResponse> scenariosToResponse(List<Scenario> list){
        List<ScenarioResponse> out = new ArrayList<>();
        list.forEach(it ->{
            out.add(scenarioToResponse(it));
        });
        return out;
    }

    public ScenarioResponse scenarioToResponse(Scenario it){
        return new ScenarioResponse(it.getName(), it.getGameMaster().getUsername(), it.getScenarioKey());
    }
}
