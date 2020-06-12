package com.seciii.oasis.controller.institution;

import com.seciii.oasis.bl.institution.InstitutionService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @RequestMapping(value = "/searchByName")
    public ResponseVO searchByName(@RequestParam("name")String name){
        return institutionService.searchInstitutionByName(name);
    }

    @RequestMapping(value = "/institutionInfo")
    public ResponseVO searchInstitutionInfo(@RequestParam("iid")int iid){
        return institutionService.searchInstitutionInfo(iid);
    }

    @RequestMapping(value = "/authorList")
    public ResponseVO searchAuthor(@RequestParam("iid")int iid){
        return institutionService.searchAuthorList(iid);
    }

    @RequestMapping(value = "/paperList")
    public ResponseVO searchPaper(@RequestParam("iid")int iid){
        return institutionService.searchPaper(iid);
    }

    @RequestMapping(value = "/keyword")
    public ResponseVO searchKeyword(@RequestParam("iid")int iid){
        return institutionService.searchKeyword(iid);
    }

    @RequestMapping(value = "/collaborate")
    public ResponseVO searchCollaborate(@RequestParam("iid")int iid){
        return institutionService.searchCollaborate(iid);
    }
}
