package com.seciii.oasis.controller.author;

import com.seciii.oasis.bl.author.AuthorService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "/searchByName")
    public ResponseVO searchByName(@RequestParam("name")String name){
        return authorService.searchAuthorByName(name);
    }

    @RequestMapping(value = "/authorInfo")
    public ResponseVO searchAuthorInfo(@RequestParam("aid")int aid){
        return authorService.searchAuthorInfo(aid);
    }

    @RequestMapping(value = "/keyword")
    public ResponseVO searchAuthorKeyword(@RequestParam("aid")int aid){
        return authorService.searchKeyword(aid);
    }

    @RequestMapping(value = "/collaborate")
    public ResponseVO searchAuthorRelation(@RequestParam("aid")int aid){
        return authorService.searchCollaborate(aid);
    }

    @RequestMapping(value = "/paperList")
    public ResponseVO searchPaper(@RequestParam("aid")int aid){
        return authorService.searchPaper(aid);
    }

    @RequestMapping(value = "/conferenceList")
    public ResponseVO searchConference(@RequestParam("aid")int aid){
        return authorService.searchConference(aid);
    }

    @RequestMapping("/test")
    public ResponseVO doTest(@RequestParam("name")String name){return authorService.searchAuthorByName(name);}

}
