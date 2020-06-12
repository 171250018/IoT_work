package com.seciii.oasis.vo;

import com.seciii.oasis.po.Paper;

import java.util.ArrayList;
import java.util.List;

public class PaperVO {
    private int pid = 0;
    private String doctitle = "";
    private String authors = "";
    private String institutions = "";
    private String pubtitle = "";
    private String datx = "";
    private String pubyear = "";
    private String volume = "";
    private String issue = "";
    private String startpage = "";
    private String endpage = "";
    private String abstracts = "";//避免关键字，abstract后面加了s
    private String issn = "";
    private String isbns = "";
    private String doi = "";
    private String fundinfo = "";
    private String pdflink = "";
    private String keywords = "";
    private String ieeeterms = "";
    private String inspeccterms = "";
    private String inspecnterms = "";
    private String meshterms = "";
    private int articlecite = 0;
    private int reference = 0;
    private String license = "";
    private String onlinedate = "";
    private String issuedate = "";
    private String meetingdate = "";
    private String publisher = "";
    private String docidentifier = "";
    private List<Integer> aidList = new ArrayList<>();
    private List<Integer> iidList = new ArrayList<>();
    private List<Integer> kidList = new ArrayList<>();
    private int cid = 0;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Integer> getAidList() {
        return aidList;
    }

    public void setAidList(List<Integer> aidList) {
        this.aidList = aidList;
    }

    public List<Integer> getIidList() {
        return iidList;
    }

    public void setIidList(List<Integer> iidList) {
        this.iidList = iidList;
    }

    public List<Integer> getKidList() {
        return kidList;
    }

    public void setKidList(List<Integer> kidList) {
        this.kidList = kidList;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDoctitle() {
        return doctitle;
    }

    public void setDoctitle(String doctitle) {
        this.doctitle = doctitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getInstitutions() {
        return institutions;
    }

    public void setInstitutions(String institutions) {
        this.institutions = institutions;
    }

    public String getPubtitle() {
        return pubtitle;
    }

    public void setPubtitle(String pubtitle) {
        this.pubtitle = pubtitle;
    }

    public String getDatx() {
        return datx;
    }

    public void setDatx(String datx) {
        this.datx = datx;
    }

    public String getPubyear() {
        return pubyear;
    }

    public void setPubyear(String pubyear) {
        this.pubyear = pubyear;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getStartpage() {
        return startpage;
    }

    public void setStartpage(String startpage) {
        this.startpage = startpage;
    }

    public String getEndpage() {
        return endpage;
    }

    public void setEndpage(String endpage) {
        this.endpage = endpage;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getIsbns() {
        return isbns;
    }

    public void setIsbns(String isbns) {
        this.isbns = isbns;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getFundinfo() {
        return fundinfo;
    }

    public void setFundinfo(String fundinfo) {
        this.fundinfo = fundinfo;
    }

    public String getPdflink() {
        return pdflink;
    }

    public void setPdflink(String pdflink) {
        this.pdflink = pdflink;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIeeeterms() {
        return ieeeterms;
    }

    public void setIeeeterms(String ieeeterms) {
        this.ieeeterms = ieeeterms;
    }

    public String getInspeccterms() {
        return inspeccterms;
    }

    public void setInspeccterms(String inspeccterms) {
        this.inspeccterms = inspeccterms;
    }

    public String getInspecnterms() {
        return inspecnterms;
    }

    public void setInspecnterms(String inspecnterms) {
        this.inspecnterms = inspecnterms;
    }

    public String getMeshterms() {
        return meshterms;
    }

    public void setMeshterms(String meshterms) {
        this.meshterms = meshterms;
    }

    public int getArticlecite() {
        return articlecite;
    }

    public void setArticlecite(int articlecite) {
        this.articlecite = articlecite;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOnlinedate() {
        return onlinedate;
    }

    public void setOnlinedate(String onlinedate) {
        this.onlinedate = onlinedate;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getMeetingdate() {
        return meetingdate;
    }

    public void setMeetingdate(String meetingdate) {
        this.meetingdate = meetingdate;
    }

    public String getDocidentifier() {
        return docidentifier;
    }

    public void setDocidentifier(String docidentifier) {
        this.docidentifier = docidentifier;
    }

    public PaperVO(Paper paper) {
        this.pid = paper.getPid();
        this.doctitle = paper.getDoctitle();
        this.authors = paper.getAuthors();
        this.institutions = paper.getInstitutions();
        this.pubtitle = paper.getPubtitle();
        this.datx = paper.getDatx();
        this.pubyear = paper.getPubyear();
        this.volume = paper.getVolume();
        this.issue = paper.getIssue();
        this.startpage = paper.getStartpage();
        this.endpage = paper.getEndpage();
        this.abstracts = paper.getAbstracts();
        this.issn = paper.getIssn();
        this.isbns = paper.getIsbns();
        this.doi = paper.getDoi();
        this.fundinfo = paper.getFundinfo();
        this.pdflink = paper.getPdflink();
        this.keywords = paper.getKeywords();
        this.ieeeterms = paper.getIeeeterms();
        this.inspeccterms = paper.getInspeccterms();
        this.inspecnterms = paper.getInspecnterms();
        this.meshterms = paper.getMeshterms();
        this.articlecite = paper.getArticlecite();
        this.reference = paper.getReference();
        this.license = paper.getLicense();
        this.onlinedate = paper.getOnlinedate();
        this.issuedate = paper.getIssuedate();
        this.meetingdate = paper.getMeetingdate();
        this.publisher = paper.getPublisher();
        this.docidentifier = paper.getDocidentifier();
    }
}
