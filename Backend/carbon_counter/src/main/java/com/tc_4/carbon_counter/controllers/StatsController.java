package com.tc_4.carbon_counter.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tc_4.carbon_counter.databases.DailyStatsDatabase;
import com.tc_4.carbon_counter.models.DailyStats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The statistics controller handles getting and adding 
 * user statistics to and from the database.
 * @author Colton Glick
 * @author Andrew Pester
 */
@RestController
public class StatsController {

    /**
     * The daily statistics database to query
     */
    private final DailyStatsDatabase database;

    /**
     * Basic constructor, initiate the database
     * @param database
     */
    StatsController(DailyStatsDatabase database){
        this.database = database;
    }

    /**
     * {@code GET request}
     * URL Mapping: <pre> /stats/{userName} </pre>
     * 
     * Returns an array containing the daily stats for the specified user
     * or an empty array if no stats are found for the user
     * 
     * @param userName pass as a path variable
     * @return Array
     */
    @GetMapping("/stats/{userName}")
    public List<DailyStats> getUserDailyStats(@PathVariable String userName){
        return database.findByUserName(userName);
    }

     /**
     * {@code GET request}
     * URL Mapping: <pre> /stats/daily/{userName} </pre>
     * 
     * Returns the daily stats for the specified user on the specified day
     * 
     * @param userName pass as a path variable
     * @param datePayload Enter date in the request body json in the format "date":"yyyy-mm-dd"
     * @return The most recent Daily stats entry for that day or null if none exists
     */
    @GetMapping("/stats/daily/{userName}")
    public Optional<DailyStats> getUserDailyStatsByDate(@PathVariable String userName, @RequestBody Map<String, String> datePayload){
        //Using a Map to get the date as a String(returns the whole JSON otherwise), and using Date.valueOf()
        //to convert to a date object
        return database.findTopByUserNameAndDateOrderByIdDesc(userName, LocalDate.parse(datePayload.get("date")));
    }


    /**
     * {@code GET request}
     * URL Mapping: <pre> /stats/lastMonth/{userName} </pre>
     * 
     * Returns an array of daily stats for dates that are grater than 
     * the current date - 1 month.
     * EX: If today is 2020-10-31, would return everything on 2020-09-30
     * and after
     * 
     * @param userName pass as a path variable
     * @return An array of the daily stats from the last month
     */
    @GetMapping("/stats/lastMonth/{userName}")
    public List<DailyStats> getLastMonthUserDailyStats(@PathVariable String userName){
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        System.out.println(oneMonthAgo);
        
        return database.findByUserNameAndDateGreaterThanOrderByDateAsc(userName, oneMonthAgo);
    }


    /**
     * {@code POST request}
     * URL Mapping: <pre> /stats/addDaily </pre>
     * 
     * Add a daily statistic for the specified user in the json body. 
     * 
     * @param dailyStats Pass the statistics via the JSON body, the only required
     *      field is the userName to assign the stats to.
     * @return The daily stats object if it was successfully saved to the database.
     */
    @PostMapping("/stats/addDaily")
    public DailyStats addDailyStats(@RequestBody DailyStats dailyStats){
        //TODO: if an entry already exists with this date and username, overwrite it
        database.save(dailyStats);
        return dailyStats;
    }
    
}
