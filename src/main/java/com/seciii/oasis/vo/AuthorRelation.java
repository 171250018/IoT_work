package com.seciii.oasis.vo;

import java.util.List;

public class AuthorRelation {

    private AuthorVO author;
    private List<AuthorVO> collaboration;
    private List<Integer> relevance;

    public List<AuthorVO> getCollaboration() {
        return collaboration;
    }

    public void setCollaboration(List<AuthorVO> collaboration) {
        this.collaboration = collaboration;
    }

    public List<Integer> getRelevance() {
        return relevance;
    }

    public void setRelevance(List<Integer> relevance) {
        this.relevance = relevance;
    }

    public AuthorVO getAuthor() {

        return author;
    }

    public void setAuthor(AuthorVO author) {
        this.author = author;
    }
}
