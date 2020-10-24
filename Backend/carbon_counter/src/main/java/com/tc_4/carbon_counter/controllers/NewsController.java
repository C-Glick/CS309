package com.tc_4.carbon_counter.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tc_4.carbon_counter.exceptions.CarbonFileNotFoundException;
import com.tc_4.carbon_counter.models.News;
import com.tc_4.carbon_counter.services.NewsService;


@RestController
public class NewsController {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    NewsService newsService;
    
    /**
     * Get a stored image by its name.
     * Will first search the class path of the project for the file,
     * if not found will then search the file system of the server.
     */
    @GetMapping("/image/{fileName}")
    public void getImage(HttpServletResponse response, @PathVariable String fileName) throws IOException {
        
        Resource imgFile;
        //classpath, stored within the jar file
        imgFile = resourceLoader.getResource("classpath:" + fileName);

        if(!imgFile.exists()){
            //file system, location on server to pull images from
            imgFile = resourceLoader.getResource("file:C:/Users/The_Pheonix/git/309_Project/Backend/carbon_counter/src/main/resources/" + fileName);
        }

        if(imgFile.exists()){
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        }else{
            throw new CarbonFileNotFoundException(fileName);
        }
    }

    //TODO: save images
     /**
     * Get a stored image by its name.
     * Will first search the class path of the project for the file,
     * if not found will then search the file system of the server.
     */
    @PostMapping("/image/{fileName}")
    public void saveImage(HttpServletResponse response, @PathVariable String fileName) throws IOException {
        
        Resource imgFile;
        //classpath, stored within the jar file
        imgFile = resourceLoader.getResource("classpath:" + fileName);

        if(!imgFile.exists()){
            //file system, location on server to pull images from
            imgFile = resourceLoader.getResource("file:C:/Users/The_Pheonix/git/309_Project/Backend/carbon_counter/src/main/resources/" + fileName);
        }

        if(imgFile.exists()){
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        }else{
            throw new CarbonFileNotFoundException(fileName);
        }
    }

    /**
     * Add a news item to the database.
     * Required fields: title, imageTitle, link, body
     * Optional fields: date(defaults to today)
     * 
     * @param news The news item to add to the database, passed in as a JSON object.
     * @return The news object that was just added as a JSON object.
     */
    @PostMapping("/news/add")
    public News addNews(@RequestBody News news){
        return newsService.addNews(news);
    }

    /**
     * Find a news item by its title.
     * If no item is found, returns null
     * 
     * @param title The title to search by, passed in as a path variable
     * @return A news object as a JSON object or null
     */
    @GetMapping("/news/{title}")
    public News getNewsByTitle(@PathVariable String title){
        return newsService.getNewsByTitle(title);
    }

    /**
     * Get all news objects that have a date after the date passed in.
     * List is sorted by date past to present.
     * 
     * @param date The date to search by, passed as a path variable in the from of "yyyy-MM-dd"
     * @return A list of news objects, (can be empty) as a JSON list
     */
    @GetMapping("/news/after/{date}")
    public List<News> getNewsAfterDate(@PathVariable String date){
        return newsService.getNewsAfterDate(LocalDate.parse(date));
    }

    /**
     * Get the most recent n news objects in the database.
     * If less than n items exist in the database, returns as many
     * as possible.
     * List is sorted by date past to present.
     * 
     * @param n the number of news objects to get passed in as a path variable.
     * @return A list of news objects that is up to n in size as a JSON list.
     */
    @GetMapping("/news/last/{n}")
    public List<News> getLastNNews(@PathVariable int n){
        return newsService.getLastNNews(n);
    }

    /**
     * Get all news objects in the database.
     * List is sorted by date past to present.
     * 
     * @return A list of news objects as a JSON list
     */
    @GetMapping("/news/all")
    public List<News> getAllNews(){
        return newsService.getAllNews();
    }
}
