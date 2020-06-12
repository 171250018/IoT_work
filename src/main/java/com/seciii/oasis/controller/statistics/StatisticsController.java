package com.seciii.oasis.controller.statistics;

import com.seciii.oasis.bl.statistics.StatisticsService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/statistics/paperrefrank", method = RequestMethod.GET)
    public ResponseVO getPaperRefRank(){
        return statisticsService.getMostRefPapers();
    }

    @RequestMapping(value = "/statistics/institutionrank", method = RequestMethod.GET)
    public ResponseVO getInstitutionCountRank(){
        return statisticsService.getInstitutionsPapers();
    }
}
