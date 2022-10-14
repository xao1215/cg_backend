package com.project.cg_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.project.cg_backend.EnergyAssetTimeseries;

import java.sql.Timestamp;
import java.util.List;

public interface EnergyAssetTimeseriesRepository extends CrudRepository<EnergyAssetTimeseries, Integer> {
    @Query("SELECT eat FROM EnergyAssetTimeseries AS eat WHERE eat.timestamp >= :from AND eat.timestamp <= :to")
    List<EnergyAssetTimeseries> findByTimePeriod(Timestamp from, Timestamp to);
}
