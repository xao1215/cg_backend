package com.project.cg_backend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.cg_backend.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/api")
public class EnergyAssetTimeseriesController {

    @Autowired
    private EnergyAssetTimeseriesRepository EatRepository;

    @CachePut(value = "latest", key = "#eat.assetId")
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

    @GetMapping("/eat/timeperiod/{assetId}")
    public List<EnergyAssetTimeseries> getTimePeriod(@PathVariable UUID assetId, @RequestBody TimePeriod data){
        return EatRepository.findByTimePeriod(assetId, data.from, data.to);
    }

    @Cacheable(value = "latest", key = "#assetId")
    @GetMapping("/eat/latest/{assetId}")
    public EnergyAssetTimeseries getLatest(@PathVariable UUID assetId) throws InterruptedException {
        Thread.sleep(1000);
        return EatRepository.getLatest(assetId);
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
