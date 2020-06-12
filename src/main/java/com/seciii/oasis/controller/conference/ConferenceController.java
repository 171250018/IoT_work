package com.seciii.oasis.controller.conference;

import com.seciii.oasis.bl.conference.ConferenceService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @RequestMapping(value = "/searchByName")
    public ResponseVO searchByName(@RequestParam("name")String name){
        return conferenceService.searchConferenceByName(name);
    }

    @RequestMapping(value = "/conferenceInfo")
    public ResponseVO searchConferenceInfo(@RequestParam("cid")int cid){
        return conferenceService.searchConferenceInfo(cid);
    }

    @RequestMapping(value = "/paperList")
    public ResponseVO searchPaper(@RequestParam("cid")int cid){
        return conferenceService.searchPaper(cid);
    }

    @RequestMapping(value = "/authorList")
    public ResponseVO searchAuthor(@RequestParam("cid")int cid){
        return conferenceService.searchAuthorList(cid);
    }

    @RequestMapping(value = "/institutionList")
    public ResponseVO searchInstitution(@RequestParam("cid")int cid){
        return conferenceService.searchInstitutionList(cid);
    }

    @RequestMapping(value = "/keyword")
    public ResponseVO searchKeyword(@RequestParam("cid")int cid){
        return conferenceService.searchKeyword(cid);
    }
}
