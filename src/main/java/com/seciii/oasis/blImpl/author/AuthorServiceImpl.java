package com.seciii.oasis.blImpl.author;

import com.seciii.oasis.bl.author.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorDetailMapper authorDetailMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private ConferenceDetailMapper conferenceDetailMapper;
    @Autowired
    private PaperMapper paperMapper;

    /**
     * 用于简单搜索，以作者为条件时，向用户推荐姓名相近的学者。
     * 先进行精准匹配，然后进行模糊匹配（按活跃度排序）
     * @param name
     * @return
     */
    @Override
    public ResponseVO searchAuthorByName(String name) {
        Author exact = authorMapper.selectAuthorByExactName(name);
        List<Author> authorList = authorMapper.selectAuthorByName(name);
        if(authorList != null && authorList.size()!=0){
            ArrayList<AuthorVO> authorVOList = new ArrayList<>();
            if(exact!=null){
                authorVOList.add(new AuthorVO(exact));
            }
            for(Author a : authorList){
                if(exact!=null){
                    if(exact.getAid()!=a.getAid()){
                        authorVOList.add(new AuthorVO(a));
                    }
                }
                else{
                    authorVOList.add(new AuthorVO(a));
                }
            }
            //前端不展示，就不加入机构名的设置
            /*for(AuthorVO v : authorVOList){
                List<Integer> iidList = authorDetailMapper.searchInstitutionByAid(v.getAid());
                ArrayList<String> institutionList = new ArrayList<>();
                for(int i : iidList){
                    String iName = institutionMapper.selectInstitutionByIid(i).getIname();
                    institutionList.add(iName);
                }
                v.setInstitutions(institutionList);
            }*/
            AuthorVOList res = new AuthorVOList();
            res.setAuthorVOList(authorVOList);
            return ResponseVO.buildSuccess(res);
        }else{
            return ResponseVO.buildFailure("Author Not Found");
        }
    }

    /**
     * 根据给定的aid返回作者信息，用于作者详情界面，显示作者的简单介绍
     * @param aid
     * @return
     */
    @Override
    public ResponseVO searchAuthorInfo(int aid) {
        Author author = authorMapper.selectAuthorByAid(aid);
        if(author != null){
            AuthorVO authorVO = new AuthorVO(author);
            List<Integer> iidList = authorDetailMapper.searchInstitutionByAid(authorVO.getAid());
            ArrayList<String> institutionList = new ArrayList<>();
            for(int i : iidList){
                String iName = institutionMapper.selectInstitutionByIid(i).getIname();
                institutionList.add(iName);
            }
            authorVO.setInstitutions(institutionList);
            authorVO.setIidList(iidList);
            return ResponseVO.buildSuccess(authorVO);
        }else{
            return ResponseVO.buildFailure("Author Not Found");
        }
    }

    /**
     * 根据给定的aid，返回作者的研究方向，用于词云的展示
     * 逻辑是搜索作者所著论文的关键字
     * @param aid
     * @return
     */
    @Override
    public ResponseVO searchKeyword(int aid) {
        List<Integer> kidList = authorDetailMapper.searchKeywordByAid(aid);
        List<KeywordVO> keywordVOList = new ArrayList<>();
        for(int i : kidList){
            Keyword k = keywordMapper.selectKeywordByKid(i);
            keywordVOList.add(new KeywordVO(k));
        }
        KeywordVOList res = new KeywordVOList();
        res.setKeywordVOList(keywordVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找作者的合作关系，逻辑是学者所写论文的共同作者，用于关系图的展示
     * 以作者之间的合作次数作为连线的权重
     * @param aid
     * @return
     */
    @Override
    public ResponseVO searchCollaborate(int aid) {
        List<AuthorCol> colList = authorDetailMapper.searchCollaboration(aid);
        AuthorRelation res = new AuthorRelation();
        Author author = authorMapper.selectAuthorByAid(aid);
        if(author==null){return ResponseVO.buildFailure("No Such Author");}
        res.setAuthor(setAuthorVO(author));
        List<AuthorVO> authorVOList = new ArrayList<>();
        List<Integer> colCount = new ArrayList<>();
        if(colList != null && colList.size()!=0){
            for(AuthorCol ac : colList){
                Author a = authorMapper.selectAuthorByAid(ac.getAid());
                authorVOList.add(setAuthorVO(a));
                colCount.add(ac.getCollaborCount());
            }
        }
        res.setCollaboration(authorVOList);
        res.setRelevance(colCount);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 返回作者参与过的会议列表
     * @param aid
     * @return
     */
    @Override
    public ResponseVO searchConference(int aid) {
        List<Integer> cidList = authorDetailMapper.searchConferenceByAid(aid);
        ConferenceVOList res = new ConferenceVOList();
        List<ConferenceVO> conferenceVOList = new ArrayList<>();
        if(cidList!=null){
            for(int cid : cidList){
                Conference c = conferenceDetailMapper.selectConferenceByCid(cid);
                conferenceVOList.add(new ConferenceVO(c));
            }
        }
        res.setConferenceVOList(conferenceVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 返回作者所著论文
     * @param aid
     * @return
     */
    @Override
    public ResponseVO searchPaper(int aid) {
        List<Integer> pidList = authorDetailMapper.searchPaperByAid(aid);
        PaperVOList res = new PaperVOList();
        List<PaperVO> paperVOList = new ArrayList<>();
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
