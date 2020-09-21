package com.tc_4.carbon_counter.databases;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.tc_4.carbon_counter.models.DailyStats;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyStatsDatabase extends JpaRepository<DailyStats, Long> {
    List<DailyStats> findByUserName(String userName);

    Optional<DailyStats> findByUserNameAndDate(String userName, Date date);
}
