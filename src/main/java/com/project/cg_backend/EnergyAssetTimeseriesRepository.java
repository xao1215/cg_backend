package com.project.cg_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.project.cg_backend.EnergyAssetTimeseries;

public interface EnergyAssetTimeseriesRepository extends CrudRepository<EnergyAssetTimeseries, Integer> {
}
