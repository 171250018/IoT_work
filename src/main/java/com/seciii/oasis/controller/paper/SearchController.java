package com.seciii.oasis.controller.paper;

import com.seciii.oasis.bl.paper.SearchService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseVO getSearchResult(@RequestParam("type")int type,
                                      @RequestParam("content")String content,
                                      @RequestParam("page")int page){
        return searchService.searchPaper(type, content, page);
    }

    @RequestMapping(value = "/cbsearch", method = RequestMethod.GET)
    public ResponseVO getCombineSearchResult(@RequestParam("title")String title,
                                             @RequestParam("startyear")String startyear,
                                             @RequestParam("endyear")String endyear,
                                             @RequestParam("doi")String doi,
                                             @RequestParam("pubtitle")String pubtitle,
                                             @RequestParam("author")String author,
                                             @RequestParam("keyword")String keyword,
                                             @RequestParam("institution")String institution,
                                             @RequestParam("page")int page){
        return searchService.combineSearch(title, startyear, endyear, doi, pubtitle, author, keyword, institution, page);
    }

    @RequestMapping(value = "/getpaperdetail")
    public ResponseVO getPaperDetail(@RequestParam("pid")int pid){
        return searchService.searchPaperByPid(pid);
    }


    @RequestMapping(value = "/test")
    public ResponseVO doTest(){
        return searchService.searchPaperByPid(1227);
    }

}
