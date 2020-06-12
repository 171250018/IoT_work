package com.seciii.oasis.blImpl.institution;

import com.seciii.oasis.bl.institution.InstitutionService;
import com.seciii.oasis.data.author.AuthorDetailMapper;
import com.seciii.oasis.data.institution.InstitutionDetailMapper;
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
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionDetailMapper institutionDetailMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private AuthorDetailMapper authorDetailMapper;
    @Autowired
    private PaperMapper paperMapper;

    /**
     * 用于简单搜索，以机构为条件时，向用户推荐名字相近的机构，按照活跃度排序
     * @param name
     * @return
     */
    @Override
    public ResponseVO searchInstitutionByName(String name) {
        InstitutionVOList res = new InstitutionVOList();
        List<InstitutionVO> institutionVOList = new ArrayList<>();
        List<Institution> institutionList = institutionMapper.selectInstitutionByIname(name);
        if(institutionList != null && institutionList.size()!=0){
            for(Institution i : institutionList){
                institutionVOList.add(new InstitutionVO(i));
            }
        }
        res.setInstitutionVOList(institutionVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 用于机构详情页面，返回机构的基本信息，包括机构名、机构论文量、论文总引用量和活跃度
     * @param iid
     * @return
     */
    @Override
    public ResponseVO searchInstitutionInfo(int iid) {
        Institution res = institutionMapper.selectInstitutionByIid(iid);
        if(res != null){
            return ResponseVO.buildSuccess(new InstitutionVO(res));
        }else{
            return ResponseVO.buildFailure("No Such Institution");
        }
    }

    /**
     * 寻找机构发表的论文涉及的研究方向（关键字）
     * @param iid
     * @return
     */
    @Override
    public ResponseVO searchKeyword(int iid) {
        List<Integer> kidList = institutionDetailMapper.searchKeywordByIid(iid);
        KeywordVOList res = new KeywordVOList();
        List<KeywordVO> keywordVOList = new ArrayList<>();
        if(kidList != null){
            for(int kid : kidList){
                Keyword k = keywordMapper.selectKeywordByKid(kid);
                keywordVOList.add(new KeywordVO(k));
            }
        }
        res.setKeywordVOList(keywordVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找机构下的作者信息
     * @param iid
     * @return
     */
    @Override
    public ResponseVO searchAuthorList(int iid) {
        List<Integer> aidList = institutionDetailMapper.searchAuthorByIid(iid);
        AuthorVOList res = new AuthorVOList();
        List<AuthorVO> authorVOList = new ArrayList<>();
        if(aidList != null){
            for(int aid : aidList){
                Author a = authorMapper.selectAuthorByAid(aid);
                authorVOList.add(setAuthorVO(a));
            }
        }
        res.setAuthorVOList(authorVOList);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 查询机构之间的合作关系，逻辑是不同机构的学者合作的论文视为两个机构的一次合作
     * @param iid
     * @return
     */
    @Override
    public ResponseVO searchCollaborate(int iid) {
        List<InstitutionCol> colList = institutionDetailMapper.searchCollaboration(iid);
        InstitutionRelation res = new InstitutionRelation();
        Institution institution = institutionMapper.selectInstitutionByIid(iid);
        if(institution==null){return ResponseVO.buildFailure("No Such Institution");}
        res.setInstitution(new InstitutionVO(institution));
        List<InstitutionVO> institutionVOList = new ArrayList<>();
        List<Integer> colCount = new ArrayList<>();
        if(colList != null){
            for(InstitutionCol ic : colList){
                Institution i = institutionMapper.selectInstitutionByIid(ic.getIid());
                institutionVOList.add(new InstitutionVO(i));
                colCount.add(ic.getCollaborCount());
            }
        }
        res.setCollaboration(institutionVOList);
        res.setRelevance(colCount);
        return ResponseVO.buildSuccess(res);
    }

    /**
     * 寻找机构发表论文的列表
     * @param iid
     * @return
     */
    @Override
    public ResponseVO searchPaper(int iid) {
        List<Integer> pidList = institutionDetailMapper.searchPaperByIid(iid);
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
