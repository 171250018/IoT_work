package com.seciii.oasis.blImpl.keyword;

import com.seciii.oasis.bl.keyword.KeywordService;
import com.seciii.oasis.data.author.AuthorDetailMapper;
import com.seciii.oasis.data.conference.ConferenceDetailMapper;
import com.seciii.oasis.data.keyword.KeywordDetailMapper;
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
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private KeywordDetailMapper keywordDetailMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private AuthorDetailMapper authorDetailMapper;
    @Autowired
    private ConferenceDetailMapper conferenceDetailMapper;

    /**
     * 返回关键词的基本信息，包括论文量、总引用量和活跃度
     * @param kid
     * @return
     */
    @Override
    public ResponseVO searchKeywordInfo(int kid){
        Keyword keyword=keywordMapper.selectKeywordByKid(kid);
        if(keyword!=null){
            KeywordVO keywordVO=new KeywordVO(keyword);
            return ResponseVO.buildSuccess(keywordVO);
        }
        else{
            return ResponseVO.buildFailure("Keyword Not Found");
        }
    }

    /**
     * 返回关键词之间的联系，在同一篇论文中出现的关键词都视为有联系
     * @param kid
     * @return
     */
    @Override
    public ResponseVO searchRelation(int kid){
        Keyword keyword=keywordMapper.selectKeywordByKid(kid);
        if(keyword!=null){
            KeywordRelation keywordRelation=new KeywordRelation();
            //keyword
            KeywordVO keywordVO=new KeywordVO(keyword);
            //collaboration&relevance
            List<KeywordCol> keywordColList=keywordDetailMapper.searchCollaboration(kid);
            List<KeywordVO> collaboration=new ArrayList<KeywordVO>();
            List<Integer> relevance=new ArrayList<Integer>();
            if(keywordColList!=null){
                for(KeywordCol kc : keywordColList){
                    KeywordVO kv=new KeywordVO(keywordMapper.selectKeywordByKid(kc.getKid()));
                    collaboration.add(kv);
                    relevance.add(kc.getCollaborCount());
                }
            }
            keywordRelation.setKeyword(keywordVO);
            keywordRelation.setCollaboration(collaboration);
            keywordRelation.setRelevance(relevance);
            return ResponseVO.buildSuccess(keywordRelation);
        }
        else{
            return ResponseVO.buildFailure("Keyword Not Exist");
        }
    }

    /**
     * 寻找含有该关键词的论文列表
     * @param kid
     * @return
     */
    @Override
    public ResponseVO searchPaper(int kid){
        List<Integer> pids=keywordDetailMapper.selectPaperByKid(kid);
        PaperVOList res=new PaperVOList();
        List<PaperVO> paperVOList=new ArrayList<PaperVO>();
        if(pids!=null){
            for(int pid:pids){
                Paper p=paperMapper.selectPaperByPid(pid);
                paperVOList.add(setPaperVO(p));
            }
        }
        res.setPaperVOList(paperVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找在研究领域发表过论文的学者
     * @param kid
     * @return
     */
    @Override
    public ResponseVO searchAuthorList(int kid){
        List<Integer> aids=keywordDetailMapper.selectAuthorByKid(kid);
        AuthorVOList res=new AuthorVOList();
        List<AuthorVO> authorVOList=new ArrayList<AuthorVO>();
        if(aids!=null){
            for(int aid:aids){
                Author a=authorMapper.selectAuthorByAid(aid);
                AuthorVO av=new AuthorVO(a);
                List<Integer> iids=authorDetailMapper.searchInstitutionByAid(a.getAid());
                ArrayList<String> is=new ArrayList<String>();
                for(int iid:iids){
                    is.add(institutionMapper.selectInstitutionByIid(iid).getIname());
                }
                av.setInstitutions(is);
                authorVOList.add(av);
            }
        }
        res.setAuthorVOList(authorVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找在该研究领域发表论文的机构
     * @param kid
     * @return
     */
    @Override
    public ResponseVO searchInstitutionList(int kid){
        List<Integer> iids=keywordDetailMapper.selectInstitutionByKid(kid);
        InstitutionVOList res=new InstitutionVOList();
        List<InstitutionVO> institutionVOList=new ArrayList<InstitutionVO>();
        if(iids!=null){
            for(int iid:iids){
                Institution i=institutionMapper.selectInstitutionByIid(iid);
                InstitutionVO iv=new InstitutionVO(i);
                institutionVOList.add(iv);
            }
        }
        res.setInstitutionVOList(institutionVOList);
        return ResponseVO.buildSuccess(res);
    }

    private PaperVO setPaperVO(Paper paper){
        PaperVO paperVO=new PaperVO(paper);
        List<Integer> aidList=authorMapper.selectAidByPid(paperVO.getPid());
        List<Integer> iidList=institutionMapper.selectIidByPid(paperVO.getPid());
        List<Integer> kidList=keywordMapper.selectKidByPid(paperVO.getPid());
        int cid=conferenceDetailMapper.selectCidByPid(paperVO.getPid());
        paperVO.setAidList(aidList);
        paperVO.setIidList(iidList);
        paperVO.setKidList(kidList);
        paperVO.setCid(cid);
        return paperVO;
    }

}
