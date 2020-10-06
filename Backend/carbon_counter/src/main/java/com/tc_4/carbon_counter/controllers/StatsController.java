package com.tc_4.carbon_counter.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tc_4.carbon_counter.models.DailyStats;
import com.tc_4.carbon_counter.services.StatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The statistics controller provides an API to handle commands
 * related to adding, removing, and viewing a user's statistics.
 * This controller deals with all mappings beginning with /stats/
 * 
 * @see StatsService
 * 
 * @author Colton Glick
 * @author Andrew Pester
 */
@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    /**
     * Returns an array containing the daily stats for the specified user
     * or an empty array if no stats are found for the user
     * 
     * @param userName pass as a path variable
     * @return A JSON Array
     */
    @GetMapping("/stats/{userName}")
    public List<DailyStats> getUserDailyStats(@PathVariable String userName){
       return statsService.getUserDailyStats(userName);
    }

     /**
     * Returns the daily stats for the specified user on the specified day
     * 
     * @param userName Pass as a path variable
     * @param date Enter date in the request body json in the format "date":"yyyy-mm-dd"
     * @return The most recent Daily stats entry for that day or null if none exists
     */
    @GetMapping("/stats/onDate/{userName}")
    public Optional<DailyStats> getUserDailyStatsByDate(@PathVariable String userName, @RequestBody Map<String, String> date){
        //Using a Map to get the date as a String(returns the whole JSON otherwise), and using Date.valueOf()
        //to convert to a date object
        return statsService.getUserDailyStatsByDate(userName, LocalDate.parse(date.get("date")));
    }

    /**
     * Returns the daily stats that have been entered for today, or 
     * null if no stats have been entered yet.
     * 
     * @param userName Provide as a path variable
     * @return a JSON object if a stats entry exists, if not returns null
     */
    @GetMapping("/stats/today/{userName}")
    public Optional<DailyStats> getUserDailyStatsToday(@PathVariable String userName){
        return statsService.getUserDailyStatsByDate(userName, LocalDate.now());
    }


    /**
     * Returns an array of daily stats for dates that are grater than 
     * the current date - 1 month.
     * EX: If today is 2020-10-31, would return everything on 2020-09-30
     * and after
     * 
     * @param userName pass as a path variable
     * @return A JSON array of the daily stats from the last month
     */
    @GetMapping("/stats/lastMonth/{userName}")
    public List<DailyStats> getLastMonthUserDailyStats(@PathVariable String userName){        
        return statsService.getLastMonthUserDailyStats(userName);
    }


    /**
     * Add a daily statistic for the specified user in the json body.
     * Required fields: userName.
     * If an entry already exists for this user today,
     * it will be overwritten by this new entry.
     * 
     * @param dailyStats Pass the statistics via the JSON body, the only required
     *      field is the userName to assign the stats to.
     * @return The daily stats object if it was successfully saved to the database.
     */
    @PostMapping("/stats/addDaily")
    public DailyStats addDailyStats(@RequestBody DailyStats dailyStats){
        return statsService.addDailyStats(dailyStats);
    }
    
}
