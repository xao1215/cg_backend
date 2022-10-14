package com.project.cg_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class EnergyAssetTimeseriesController {

    @Autowired
    private EnergyAssetTimeseriesRepository EatRepository;

    @PostMapping("/eat")
    public EnergyAssetTimeseries createTimeseries(@RequestBody EnergyAssetTimeseries eat) {
        return EatRepository.save(eat);
    }

    @GetMapping("/eat")
    public List<EnergyAssetTimeseries> getAllTimeseries() {
        return (List<EnergyAssetTimeseries>) EatRepository.findAll();
    }

    @GetMapping("/eat/{id}")
    public Optional<EnergyAssetTimeseries> getTimeseries(@PathVariable Integer id) {
        return EatRepository.findById(id);
    }


}
