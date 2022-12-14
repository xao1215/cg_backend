package com.project.cg_backend;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface EnergyAssetTimeseriesRepository extends CrudRepository<EnergyAssetTimeseries, Integer> {
    @Query("SELECT eat FROM EnergyAssetTimeseries eat WHERE eat.assetId = :assetId AND eat.timestamp >= :from AND eat.timestamp <= :to ORDER BY eat.timestamp")
    List<EnergyAssetTimeseries> findByTimePeriod(UUID assetId, Timestamp from, Timestamp to);

    @Query("SELECT eat FROM EnergyAssetTimeseries eat WHERE eat.assetId = :assetId AND eat.timestamp = ( SELECT MAX(timestamp) FROM EnergyAssetTimeseries WHERE assetId = :assetId )")
    EnergyAssetTimeseries getLatest(UUID assetId);
}
