package com.seciii.oasis.blImpl.paper;

import com.seciii.oasis.bl.paper.SearchService;
import com.seciii.oasis.data.conference.ConferenceDetailMapper;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.data.paper.KeywordMapper;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.Author;
import com.seciii.oasis.po.Institution;
import com.seciii.oasis.po.Keyword;
import com.seciii.oasis.po.Paper;
import com.seciii.oasis.vo.PaperVO;
import com.seciii.oasis.vo.ResponseVO;
import com.seciii.oasis.vo.ResultList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private ConferenceDetailMapper conferenceDetailMapper;

    @Override
    public ResponseVO searchPaper(int type, String content, int page) {
        List<Paper> res = new ArrayList<>();
        String pubtitle = "";
        if(type == 0){//标题
            res = paperMapper.selectPaperByTitle(content, (page-1)*10, page*10);
        }else if(type == 1){//作者
            List<String> authorList = new ArrayList<>();
            if(content == null){content = "";}
            authorList.add(content.trim());
            List<String> keywordList = new ArrayList<>();
            keywordList.add("");
            List<String> institutionList = new ArrayList<>();
            institutionList.add("");
            res = paperMapper.combineSelect("", "0000", "9999", "", pubtitle, authorList, keywordList, institutionList, (page-1)*10, page*10);
        }else if(type == 2){//机构
            List<String> authorList = new ArrayList<>();
            authorList.add("");
            List<String> keywordList = new ArrayList<>();
            keywordList.add("");
            List<String> institutionList = new ArrayList<>();
            if(content == null){content = "";}
            institutionList.add(content.trim());
            res = paperMapper.combineSelect("", "0000", "9999", "", pubtitle, authorList, keywordList, institutionList, (page-1)*10, page*10);
        }else if(type == 3){//会议
            if(content==null){content="";}
            res = paperMapper.selectPaperByPubTitle(content, (page-1)*10, page*10);
        }else if(type == 4){//关键字
            List<String> authorList = new ArrayList<>();
            authorList.add("");
            List<String> keywordList = new ArrayList<>();
            if(content == null){content = "";}
            keywordList.add(content.trim());
            List<String> institutionList = new ArrayList<>();
            institutionList.add("");
            res = paperMapper.combineSelect("", "0000", "9999", "", pubtitle, authorList, keywordList, institutionList, (page-1)*10, page*10);
        }else{
            return ResponseVO.buildFailure("No such type");
        }

        if(res == null || res.size()==0){
            return ResponseVO.buildFailure("没有找到符合条件的结果");
        }else{
            ArrayList<PaperVO> pvoList = new ArrayList<>();
            for(Paper p : res){
                pvoList.add(new PaperVO(p));
            }
            return ResponseVO.buildSuccess(new ResultList(pvoList));
        }
    }

    @Override
    public ResponseVO combineSearch(String title, String startyear, String endyear, String doi, String pubtitle, String authors, String keywords, String institutions, int page) {
        /*if(authors.contains(";")){authors = authors.replace(';', '%');}
        if(institutions.contains(";")){institutions = institutions.replace(';', '%');}
        if(keywords.contains(";")){
            keywords = keywords.replace(';', '%');
        }else {if(keywords.contains(",")){
            keywords = keywords.replace(',', '%');}
        }*/

        if(doi == null){doi = "";}
        if(title == null){title = "";}
        if(authors == null){authors = "";}
        if(keywords == null || keywords.equals(",")){keywords = "";}
        if(institutions == null || institutions.equals(",")){institutions = "";}
        /*System.out.println(title);
        System.out.println(startyear);
        System.out.println(endyear);
        System.out.println(doi);
        System.out.println(authors);
        System.out.println(keywords);
        System.out.println(institutions);
        System.out.println(page);*/
        List<String> authorList = Arrays.asList(authors.split(";\\s*"));
        List<String> keywordList = Arrays.asList(keywords.split(";\\s*"));
        List<String> institutionList = Arrays.asList(institutions.split(";\\s*"));

        if(authorList.size() == 0){authorList = new ArrayList<>(); authorList.add("");}
        if(keywordList.size() == 0){keywordList = new ArrayList<>(); keywordList.add("");}
        if(institutionList.size() == 0){institutionList = new ArrayList<>(); institutionList.add("");}
        if(startyear.equals("")){
            startyear = "0000";
        }
        if(endyear.equals("")){
            endyear = "9999";
        }

        List<Paper> res = paperMapper.combineSelect(title, startyear, endyear, doi, pubtitle, authorList, keywordList, institutionList, (page-1)*10, page*10);
        if(res.size()==0){
            return ResponseVO.buildFailure("没有找到符合条件的结果");
        }else{
            ArrayList<PaperVO> pvoList = new ArrayList<>();
            for(Paper p : res){
                pvoList.add(new PaperVO(p));
            }
            return ResponseVO.buildSuccess(new ResultList(pvoList));
        }

    }

    @Override
    public ResponseVO searchPaperByPid(int pid) {
        Paper paper = paperMapper.selectPaperByPid(pid);
        if(paper == null){
            return ResponseVO.buildFailure("No such paper");
        }else{
            PaperVO pvo = new PaperVO(paper);
            List<Integer> aidList = authorMapper.selectAidByPid(pid);
            String[] inameList = paper.getInstitutions().split(";");
            String[] knameList = paper.getKeywords().split(";|,");
            List<Integer> iidList = new ArrayList<>();
            List<Integer> kidList = new ArrayList<>();
            for(String iname : inameList){
                //System.out.println(iname);
                int iid = institutionMapper.selectIidByExactName(iname.trim());
                iidList.add(iid);
            }
            for(String kname : knameList){
                int kid = keywordMapper.selectKeywordByExactKname(kname.trim());
                kidList.add(kid);
            }
            int cid = conferenceDetailMapper.selectCidByPid(pid);
            pvo.setAidList(aidList);
            pvo.setIidList(iidList);
            pvo.setKidList(kidList);
            pvo.setCid(cid);
            return ResponseVO.buildSuccess(pvo);
        }
    }
}
