package com.project.cg_backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cg_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @GetMapping("/eat/{id}")
    public EnergyAssetTimeseries getTimeseries(@PathVariable Integer id) {
        EnergyAssetTimeseries eat = EatRepository.findById(id).orElseThrow(()-> new NotFoundException());
        return eat;
    }

    @PutMapping("/eat/{id}")
    public EnergyAssetTimeseries updateTimeseries(@PathVariable Integer id, @RequestBody EnergyAssetTimeseries data){
        EnergyAssetTimeseries old = EatRepository.findById(id).orElseThrow(() -> new NotFoundException());

        old.setAssetId(data.getAssetId());
        old.setTimestamp(data.getTimestamp());
        old.setActivePower(data.getActivePower());
        old.setVoltage(data.getVoltage());

        return EatRepository.save(old);
    }

    @DeleteMapping("/eat/{id}")
    public ResponseEntity deleteTimeseries(@PathVariable Integer id){
        EatRepository.deleteById(id);
        return new ResponseEntity<EnergyAssetTimeseries>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/eat/timeperiod")
    public List<EnergyAssetTimeseries> getTimePeriod(@RequestBody TimePeriod data){
        return EatRepository.findByTimePeriod(data.from, data.to);
    }

    static class TimePeriod {
        @JsonProperty("from")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        Timestamp from;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
        @JsonProperty("to")
        Timestamp to;
    }

}
