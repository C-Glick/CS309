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
        userService.doesUserExist(userName);
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
        //check that user exists
        userService.doesUserExist(userName);
        return dailyStatsDatabase.findTopByUserNameAndDateOrderByIdDesc(userName, date);
    }

    /**
     * Returns a list of all daily sta  ts form the last month
     * for the given user.
     * 
     * @param userName user name of user to find stats for.
     * @return A list of DailyStats object
     */
    public List<DailyStats> getLastMonthUserDailyStats(String userName){
        //check that user exists
        userService.doesUserExist(userName);
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return dailyStatsDatabase.findByUserNameAndDateGreaterThanOrderByDateAsc(userName, oneMonthAgo);
    }

    /**
     * Add stats to the database.
     * Required fields: userName
     * If an entry already exists for this user today,
     * it will be overwritten by this new entry.
     * 
     * @param stats The new stats to add
     * @return the resulting statistics after adding to the database
     * may contain additional details.
     */
    public DailyStats addDailyStats(DailyStats stats){
        //check that user exists
        userService.doesUserExist(stats.getUserName());
        
        //check if an entry already exists for this user today
        Optional<DailyStats> oldEntry = getUserDailyStatsByDate(stats.getUserName(), LocalDate.now());
        if(oldEntry.isEmpty()){
            dailyStatsDatabase.save(stats);
            return stats;
        }else{
            DailyStats oldEntryStats = oldEntry.get();
            oldEntryStats.copyFrom(stats);
            dailyStatsDatabase.save(oldEntryStats);
            return oldEntryStats;
        }
    }

}
