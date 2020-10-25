package com.tc_4.carbon_counter.databases;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tc_4.carbon_counter.models.DailyStats;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStatsDatabase extends JpaRepository<DailyStats, Long> {
    List<DailyStats> findByUsername(String userName);

    Optional<DailyStats> findTopByUsernameAndDateOrderByIdDesc(String userName, LocalDate date);

    List<DailyStats> findByUsernameAndDateGreaterThanOrderByDateAsc(String userName, LocalDate date);

}
