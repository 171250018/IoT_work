package com.seciii.oasis.vo;

import com.seciii.oasis.po.Conference;

public class ConferenceVO {
    private int cid;
    private String cname;
    private int paperCount;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(int paperCount) {
        this.paperCount = paperCount;
    }

    public ConferenceVO(Conference conference){
        this.cid = conference.getCid();
        this.cname = conference.getCname();
        this.paperCount = conference.getPaperamount();
    }
}
