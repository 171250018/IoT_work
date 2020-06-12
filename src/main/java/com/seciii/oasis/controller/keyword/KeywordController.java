package com.seciii.oasis.controller.keyword;

import com.seciii.oasis.bl.keyword.KeywordService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/keyword")
public class KeywordController {

    @Autowired
    private KeywordService keywordService;

    @RequestMapping(value = "/keywordInfo")
    public ResponseVO searchKeywordInfo(@RequestParam("kid")int kid){
        return keywordService.searchKeywordInfo(kid);
    }

    @RequestMapping(value = "/relation")
    public ResponseVO searchKeywordRelation(@RequestParam("kid")int kid){
        return keywordService.searchRelation(kid);
    }

    @RequestMapping(value = "/paperList")
    public ResponseVO searchPaper(@RequestParam("kid")int kid){
        return keywordService.searchPaper(kid);
    }

    @RequestMapping(value = "/authorList")
    public ResponseVO searchAuthor(@RequestParam("kid")int kid){
        return keywordService.searchAuthorList(kid);
    }

    @RequestMapping(value = "/institutionList")
    public ResponseVO searchInstitution(@RequestParam("kid")int kid){
        return keywordService.searchInstitutionList(kid);
    }

}
