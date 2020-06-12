package com.seciii.oasis.vo;

import java.util.List;

public class InstitutionRelation {

    private InstitutionVO institution;
    private List<InstitutionVO> collaboration;
    private List<Integer> relevance;

    public InstitutionVO getInstitution() {
        return institution;
    }

    public void setInstitution(InstitutionVO institution) {
        this.institution = institution;
    }

    public List<InstitutionVO> getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(List<InstitutionVO> collaboration) {
        this.collaboration = collaboration;
    }

    public List<Integer> getRelevance() {
        return relevance;
    }

    public void setRelevance(List<Integer> relevance) {
        this.relevance = relevance;
    }
}
