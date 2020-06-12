package com.seciii.oasis.blImpl.paper;

import com.seciii.oasis.data.author.AuthorDetailMapper;
import com.seciii.oasis.data.institution.InstitutionDetailMapper;
import com.seciii.oasis.data.keyword.KeywordDetailMapper;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.po.AuthorPaperDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityCalculation {

    @Autowired
    private AuthorDetailMapper authorDetailMapper;

    @Autowired
    private InstitutionDetailMapper institutionDetailMapper;

    @Autowired
    private KeywordDetailMapper keywordDetailMapper;

    public double calAuthorActivity(int aid){
        List<AuthorPaperDetail> authorPaperDetails=authorDetailMapper.searchAuthorPaperDetail(aid);
        double res=0;
        for(int i=0;i<authorPaperDetails.size();i++){
            AuthorPaperDetail a=authorPaperDetails.get(i);
            res=res+a.getReference()*(1/(Math.pow(2,a.getAuthorRank())));
        }
        return res;
    }

    public double calSingleAuthorActivity(int rank, int reference){
        return reference * (1/(Math.pow(2, rank)));
    }

    public int calInstitutionActivity(int iid){
        int res=institutionDetailMapper.searchPaperByIid(iid).size();
        return res;
    }

    public int calKeywordActivity(int kid){
        int res=keywordDetailMapper.selectPaperByKid(kid).size();
        return res;
    }
}
