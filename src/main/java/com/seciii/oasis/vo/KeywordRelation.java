package com.seciii.oasis.vo;

import java.util.List;

public class KeywordRelation {
    private KeywordVO keyword;
    private List<KeywordVO> collaboration;
    private List<Integer> relevance;

    public KeywordVO getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordVO keyword) {
        this.keyword = keyword;
    }

    public List<KeywordVO> getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(List<KeywordVO> collaboration) {
        this.collaboration = collaboration;
    }

    public List<Integer> getRelevance() {
        return relevance;
    }

    public void setRelevance(List<Integer> relevance) {
        this.relevance = relevance;
    }
}
