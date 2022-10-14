package com.project.cg_backend;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class EnergyAssetTimeseries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer assetId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp timestamp;

    private Float activePower;

    private Float voltage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Float getActivePower() {
        return activePower;
    }

    public void setActivePower(Float activePower) {
        this.activePower = activePower;
    }

    public Float getVoltage() {
        return voltage;
    }

    public void setVoltage(Float voltage) {
        this.voltage = voltage;
    }

}
