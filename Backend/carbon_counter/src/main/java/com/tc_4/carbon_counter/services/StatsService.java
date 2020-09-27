package com.tc_4.carbon_counter.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tc_4.carbon_counter.databases.DailyStatsDatabase;
import com.tc_4.carbon_counter.exceptions.UserNotFoundException;
import com.tc_4.carbon_counter.models.DailyStats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to handle the logic when dealing with
 * statistics objects. Is made to be called from 
 * controllers.
 */
@Service
public class StatsService {
    
    @Autowired
    private DailyStatsDatabase dailyStatsDatabase;

    @Autowired
    private UserService userService;

    /**
     * Return the all daily statistics for the given user name.
     * 
     * @param userName user name of the user to find stats for.
     * @return  a list of DailyStats object
     * @throws UserNotFoundException
     */
    public List<DailyStats> getUserDailyStats(String userName){
        //check that user exists
        userService.getUser(userName);
        return dailyStatsDatabase.findByUserName(userName);
    }

    /**
     * Return the user's stats that correspond to the date given
     * 
     * @param userName user name of user to find stats for.
     * @param date the date the stats were submitted 
     * @return returns 0 or 1 dailyStats entry
     */
    public Optional<DailyStats> getUserDailyStatsByDate(String userName, LocalDate date){
        return dailyStatsDatabase.findTopByUserNameAndDateOrderByIdDesc(userName, date);
    }

    /**
     * Returns a list of all daily stats form the last month
     * for the given user.
     * 
     * @param userName user name of user to find stats for.
     * @return A list of DailyStats object
     */
    public List<DailyStats> getLastMonthUserDailyStats(String userName){
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return dailyStatsDatabase.findByUserNameAndDateGreaterThanOrderByDateAsc(userName, oneMonthAgo);
    }

    /**
     * Add stats to the database.
     * Required fields: userName
     * @param stats The new stats to add
     * @return the resulting statistics after adding to the database
     * may contain additional details.
     */
    public DailyStats addDailyStats(DailyStats stats){
        //TODO: if an entry already exists with this date and username, overwrite it
        dailyStatsDatabase.save(stats);
        return stats;
    }

}
