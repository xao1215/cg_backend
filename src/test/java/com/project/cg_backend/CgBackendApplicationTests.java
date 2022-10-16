package com.project.cg_backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CgBackendApplicationTests {

    @Autowired
    private EnergyAssetTimeseriesController controller;

    @Autowired
    CacheManager cacheManager;
    @Autowired
    private EnergyAssetTimeseriesRepository repo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
        assertThat(repo).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void repositorySave() {
        EnergyAssetTimeseries eat = new EnergyAssetTimeseries();
        eat.setAssetId(UUID.randomUUID());eat.setVoltage(10f);eat.setActivePower(10f);eat.setTimestamp(new Timestamp(0));

        eat = repo.save(eat);
        assertThat(eat.getId()).isGreaterThan(0);
        repo.deleteById(eat.getId());
    }

    @Test
    public void repositoryDelete() {
        EnergyAssetTimeseries eat = new EnergyAssetTimeseries();
        eat.setAssetId(UUID.randomUUID());eat.setVoltage(10f);eat.setActivePower(10f);eat.setTimestamp(new Timestamp(0));
        eat = repo.save(eat);

        repo.deleteById(eat.getId());
        eat = repo.findById(eat.getId()).orElse(null);
        assertThat(eat).isNull();
    }

    @Test
    public void controllerGet() throws Exception {
        EnergyAssetTimeseries eat = new EnergyAssetTimeseries();
        eat.setAssetId(UUID.randomUUID());eat.setVoltage(10f);eat.setActivePower(10f);eat.setTimestamp(new Timestamp(0));
        eat = repo.save(eat);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/eat/" + eat.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)))
                .andExpect(jsonPath("$.id").value(eat.getId()));

        repo.deleteById(eat.getId());
    }

    @Test
    public void controllerLatest() throws Exception {
        UUID rand = UUID.randomUUID();
        Integer id;

        EnergyAssetTimeseries eat = new EnergyAssetTimeseries();
        eat.setAssetId(rand);eat.setVoltage(10f);eat.setActivePower(10f);eat.setTimestamp(new Timestamp(1));
        eat = repo.save(eat);
        id = eat.getId();

        eat = new EnergyAssetTimeseries();
        eat.setAssetId(rand);eat.setVoltage(10f);eat.setActivePower(10f);eat.setTimestamp(new Timestamp(10));
        eat = repo.save(eat);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/eat/latest/" + eat.getAssetId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)));

        assertThat( ((EnergyAssetTimeseries) cacheManager.getCache("latest").get(eat.getAssetId()).get()).getTimestamp().equals(new Timestamp(10)) ).isTrue();

        repo.deleteById(eat.getId());
        repo.deleteById(id);
    }


}
