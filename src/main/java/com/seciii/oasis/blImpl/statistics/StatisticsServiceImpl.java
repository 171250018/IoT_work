package com.seciii.oasis.blImpl.statistics;

import com.seciii.oasis.bl.statistics.StatisticsService;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.data.statistics.StatisticsMapper;
import com.seciii.oasis.po.Institution;
import com.seciii.oasis.po.InstitutionCount;
import com.seciii.oasis.po.Paper;
import com.seciii.oasis.vo.InstitutionRank;
import com.seciii.oasis.vo.PaperRefRank;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private InstitutionMapper institutionMapper;

    @Override
    public ResponseVO getInstitutionsPapers() {
        List<InstitutionCount> iclist = statisticsMapper.selectTopPaperCountInstitutionIid();
        ArrayList<Institution> institutionList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();
        int count=0;
        for(InstitutionCount i : iclist){
            if(count>10){
                break;
            }
            Institution temp = institutionMapper.selectInstitutionByIid(i.getIid());
            if(temp.getIname().equals("NA")){
                continue;
            }else {
                count++;
            }
            institutionList.add(temp);
            countList.add(i.getCount());
        }
        InstitutionRank res = new InstitutionRank(institutionList, countList);
        return ResponseVO.buildSuccess(res);
    }

    @Override
    public ResponseVO getMostRefPapers() {
        List<Paper> paperList = statisticsMapper.selectTopRefPaper();
        ArrayList<Integer> pidList = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<Integer> refList = new ArrayList<>();
        for(Paper p : paperList){
            pidList.add(p.getPid());
            titleList.add(p.getDoctitle());
            refList.add(p.getArticlecite());
        }
        PaperRefRank res = new PaperRefRank(pidList, titleList, refList);
        return ResponseVO.buildSuccess(res);
    }
}
