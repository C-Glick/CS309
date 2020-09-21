package com.tc_4.carbon_counter.controllers;

import java.util.List;

import com.tc_4.carbon_counter.databases.DailyStatsDatabase;
import com.tc_4.carbon_counter.models.DailyStats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private final DailyStatsDatabase database;

    StatsController(DailyStatsDatabase database){
        this.database = database;
    }

    /**
     * Returns a list containing the daily stats for the specified user
     * or an empty list if no stats are found for the user
     * 
     * @param userName
     * @return List
     */
    @GetMapping("/stats/{userName}")
    public List<DailyStats> getUserDailyStats(@PathVariable String userName){
        return database.findByUserName(userName);
    }

    /**
     * Add a daily statistic for the specified user in the json body. 
     * @param dailyStats
     * @return
     */
    @PostMapping("/stats/addDaily")
    public DailyStats addDailyStats(@RequestBody DailyStats dailyStats){
        database.save(dailyStats);
        return dailyStats;
    }
    
}
