package com.seciii.oasis.controller.route;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ViewController {
    @RequestMapping(value = "/")
    public String getIndex(){return "index";}

    @RequestMapping(value = "/result")
    public String getResult(){return "searchresult";}
//    public String getResult(@RequestParam("type")int type,
//                            @RequestParam("content")String content,
//                            @RequestParam("page")int page){return "searchresult";}

    @RequestMapping(value = "/combine")
    public String getCombine(){return "combinesearch";}

    @RequestMapping(value = "/cbresult")

    public String getCbresult(){return "combinesearchresult";}

    @RequestMapping(value = "/upload")
    public String getUpload(){return "upload";}

    @RequestMapping(value = "/statistics")
    public String getStatistics(){return "statistics";}

    @RequestMapping(value = "/paper")
    public String getPaper(@RequestParam("pid")int pid){return "paperdetail";}

    @RequestMapping(value = "/author")
    public String getAuthor(@RequestParam("aid")int aid){return "authordetail";}

    @RequestMapping(value = "/conference")
    public String getConference(@RequestParam("cid")int cid){return "conferencedetail";}

    @RequestMapping(value = "/institution")
    public String getInstitution(@RequestParam("iid")int iid){return "institutiondetail";}

    @RequestMapping(value = "/keyword")
    public String getKeyword(@RequestParam("kid")int kid){return "keyworddetail";}

    @RequestMapping(value = "/wordcloud")
    public String getCloudword(@RequestParam("id")int id,@RequestParam("type")String type){return "wordclouddetail";}

    @RequestMapping(value = "/graph")
    public String getGraph(@RequestParam("id")int id,@RequestParam("type")String type){return "graphdetail";}

    @RequestMapping(value = "/topiccluster")
    public String getTopicCluster(){return "topiccluster";}

    @RequestMapping(value = "/ranking")
    public String getRanking(){return "ranking";}
}
