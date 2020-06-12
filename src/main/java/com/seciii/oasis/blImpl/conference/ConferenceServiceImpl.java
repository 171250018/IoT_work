package com.seciii.oasis.blImpl.conference;

import com.seciii.oasis.bl.conference.ConferenceService;
import com.seciii.oasis.data.author.AuthorDetailMapper;
import com.seciii.oasis.data.conference.ConferenceDetailMapper;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.data.paper.KeywordMapper;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.*;
import com.seciii.oasis.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceDetailMapper conferenceDetailMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private AuthorDetailMapper authorDetailMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private PaperMapper paperMapper;

    /**
     * 用于简单搜索，以会议为条件进行查询时，向用户推荐名字相近的会议
     * @param name
     * @return
     */
    @Override
    public ResponseVO searchConferenceByName(String name) {
        List<Conference> conferenceList = conferenceDetailMapper.selectConferenceByAlikeName(name);
        ConferenceVOList res = new ConferenceVOList();
        List<ConferenceVO> conferenceVOList = new ArrayList<>();
        if(conferenceList != null && conferenceList.size()!=0){
            for(Conference conference : conferenceList){
                ConferenceVO vo = new ConferenceVO(conference);
                conferenceVOList.add(vo);
            }
            res.setConferenceVOList(conferenceVOList);
            return ResponseVO.buildSuccess(res);
        }else{
            return ResponseVO.buildFailure("Conference Not Found");
        }

    }

    /**
     * 用于会议页面，展示会议的基本信息，包括论文数、论文总引用量和活跃度
     * @param cid
     * @return
     */
    @Override
    public ResponseVO searchConferenceInfo(int cid) {
        Conference conference = conferenceDetailMapper.selectConferenceByCid(cid);
        if(conference != null){
            ConferenceVO vo = new ConferenceVO(conference);
            return ResponseVO.buildSuccess(vo);
        }else{
            return ResponseVO.buildFailure("Conference Not Found");
        }
    }

    /**
     * 用于搜索参与过该会议的学者
     * 逻辑是选择会议收录的论文的作者，没有建立学者-会议的表（怀疑这里不太合理
     * @param cid
     * @return
     */
    @Override
    public ResponseVO searchAuthorList(int cid) {
        List<Integer> aidList = conferenceDetailMapper.searchAuthorByCid(cid);
        if(aidList == null || aidList.size()==0){return ResponseVO.buildFailure("No Author Info");}
        AuthorVOList res = new AuthorVOList();
        List<AuthorVO> authorVOList = new ArrayList<>();
        for(int aid : aidList){
            Author a = authorMapper.selectAuthorByAid(aid);
            authorVOList.add(setAuthorVO(a));
        }
        res.setAuthorVOList(authorVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找参与会议的机构
     * @param cid
     * @return
     */
    @Override
    public ResponseVO searchInstitutionList(int cid) {
        List<Integer> iidList = conferenceDetailMapper.searchInstitutionByCid(cid);
        if(iidList == null || iidList.size()==0){return ResponseVO.buildFailure("No Institution Info");}
        InstitutionVOList res = new InstitutionVOList();
        List<InstitutionVO> institutionVOList = new ArrayList<>();
        for(int iid : iidList){
            Institution i = institutionMapper.selectInstitutionByIid(iid);
            institutionVOList.add(new InstitutionVO(i));
        }
        res.setInstitutionVOList(institutionVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找会议收录的论文的关键字（研究方向）信息
     * @param cid
     * @return
     */
    @Override
    public ResponseVO searchKeyword(int cid) {
        List<Integer> kidList = conferenceDetailMapper.searchKeywordByCid(cid);
        if(kidList == null || kidList.size()==0){return ResponseVO.buildFailure("No Keyword Info");}
        KeywordVOList res = new KeywordVOList();
        List<KeywordVO> keywordVOList = new ArrayList<>();
        for(int kid : kidList){
            Keyword k = keywordMapper.selectKeywordByKid(kid);
            keywordVOList.add(new KeywordVO(k));
        }
        res.setKeywordVOList(keywordVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找会议收录的论文
     * @param cid
     * @return
     */
    @Override
    public ResponseVO searchPaper(int cid) {
        PaperVOList res = new PaperVOList();
        List<PaperVO> paperVOList = new ArrayList<>();
        List<Integer> pidList = conferenceDetailMapper.searchPaperByCid(cid);
        if(pidList != null){
            for(int pid : pidList){
                Paper p = paperMapper.selectPaperByPid(pid);
                paperVOList.add(new PaperVO(p));
            }
        }
        res.setPaperVOList(paperVOList);
        return ResponseVO.buildSuccess(res);
    }

    private AuthorVO setAuthorVO(Author author){
        AuthorVO authorVO = new AuthorVO(author);
        List<Integer> iidList = authorDetailMapper.searchInstitutionByAid(authorVO.getAid());
        ArrayList<String> institutionList = new ArrayList<>();
        for(int i : iidList){
            String iName = institutionMapper.selectInstitutionByIid(i).getIname();
            institutionList.add(iName);
        }
        authorVO.setInstitutions(institutionList);
        return authorVO;
    }
}
