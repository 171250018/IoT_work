package com.seciii.oasis.blImpl.paper;

import com.csvreader.CsvReader;
import com.seciii.oasis.bl.paper.UploadService;
import com.seciii.oasis.data.conference.ConferenceDetailMapper;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.data.paper.KeywordMapper;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.*;
import com.seciii.oasis.vo.ResponseVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private InstitutionMapper institutionMapper;
    @Autowired
    private KeywordMapper keywordMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private ConferenceDetailMapper conferenceDetailMapper;

    private ActivityCalculation activityCalculation = new ActivityCalculation();


    @Override
    public ResponseVO uploadCsv(String filePath) {
        CsvReader reader = null;
        try{
            reader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));
            reader.readRecord();
            String[] headers = reader.getValues();
            int it=0;

            if(headers.length==29 && headers[0].equals("Document Title")){
                while (reader.readRecord()){
                    if(it%10==0){
                        System.out.println(it);
                    }
                    it++;
                    Paper paper = new Paper();
                    paper.setDoctitle(reader.get(0));
                    paper.setAuthors(reader.get(1));
                    if(reader.get(1)=="" || reader.get(1)==null){//识别是不是论文
                        continue;
                    }
                    paper.setInstitutions(reader.get(2));
                    paper.setPubtitle(reader.get(3));
                    paper.setDatx(reader.get(4));
                    paper.setPubyear(reader.get(5));
                    paper.setVolume(reader.get(6));
                    paper.setIssue(reader.get(7));
                    paper.setStartpage(reader.get(8));
                    paper.setEndpage(reader.get(9));
                    paper.setAbstracts(reader.get(10));
                    paper.setIssn(reader.get(11));
                    paper.setIsbns(reader.get(12));
                    paper.setDoi(reader.get(13));
                    paper.setFundinfo(reader.get(14));
                    paper.setPdflink(reader.get(15));
                    paper.setKeywords(reader.get(16));
                    paper.setIeeeterms(reader.get(17));
                    paper.setInspeccterms(reader.get(18));
                    paper.setInspecnterms(reader.get(19));
                    paper.setMeshterms(reader.get(20));
                    if(reader.get(21) != ""){
                        paper.setArticlecite(Integer.parseInt(reader.get(21)));
                    }
                    if(reader.get(22) != ""){
                        paper.setReference(Integer.parseInt(reader.get(22)));
                    }
                    paper.setLicense(reader.get(23));
                    paper.setOnlinedate(reader.get(24));
                    paper.setIssuedate(reader.get(25));
                    paper.setMeetingdate(reader.get(26));
                    paper.setPublisher(reader.get(27));
                    paper.setDocidentifier(reader.get(28));

                    /*Paper tmp = paperMapper.selectPaperByDoi(paper.getDoi());
                    if(tmp == null && paper.getDoi()!=""){
                        paperMapper.insertPaper(paper);
                    }*/
                    paperMapper.insertPaper(paper);

                    //读取作者
                    String[] authorList = paper.getAuthors().split(";");
                    String[] institutionList = paper.getInstitutions().split(";");
                    String[] keywordList;
                    if(paper.getKeywords() == null || paper.getKeywords().equals("")){
                        keywordList = paper.getIeeeterms().split(";|,");
                    }else{
                        keywordList = paper.getKeywords().split(";|,");
                    }



                    ArrayList<Integer> aidList = new ArrayList<>();
                    ArrayList<Integer> iidList = new ArrayList<>();

                    int rank = 0;

                    for(String authorName : authorList){
                        rank += 1;
                        //List<Author> al = authorMapper.selectAuthorByName(authorName.trim());
                        Author al = authorMapper.selectAuthorByExactName(authorName.trim());
                        if(al==null){
                            Author newAuthor = new Author();
                            newAuthor.setAname(authorName.trim());
                            newAuthor.setPaperamount(1);
                            newAuthor.setPapercited(paper.getArticlecite());
                            //活跃度
                            newAuthor.setActivity(activityCalculation.calSingleAuthorActivity(rank, paper.getArticlecite()));
                            authorMapper.insertAuthor(newAuthor);
                            authorMapper.insertAidPid(newAuthor.getAid(), paper.getPid(), rank);
                            aidList.add(newAuthor.getAid());
                        }else{
                            Author a = al;
                            int aid = a.getAid();
                            authorMapper.insertAidPid(aid, paper.getPid(), rank);
                            a.setPaperamount(a.getPaperamount() + 1);
                            a.setPapercited(a.getPapercited() + paper.getArticlecite());
                            a.setActivity(a.getActivity() + activityCalculation.calSingleAuthorActivity(rank, paper.getArticlecite()));
                            aidList.add(aid);
                            authorMapper.updateAuthor(a);
                        }

                    }
                    //读取机构
                    for(String institutionName : institutionList){
                        //List<Institution> ll = institutionMapper.selectInstitutionByIname(institutionName.trim());
                        Institution ll = institutionMapper.selectInstitutionByExactName(institutionName.trim());
                        if(ll==null){
                            Institution newInstitution = new Institution();
                            newInstitution.setIname(institutionName.trim());
                            newInstitution.setActivity(1);
                            newInstitution.setPaperamount(1);
                            newInstitution.setPapercited(paper.getArticlecite());
                            institutionMapper.insertInstitution(newInstitution);
                            institutionMapper.insertIidPid(newInstitution.getIid(), paper.getPid());
                            iidList.add(newInstitution.getIid());
                        }else{
                            Institution i = ll;
                            int iid = i.getIid();
                            int t = paperMapper.selectByIidPid(iid, paper.getPid());
                            iidList.add(iid);
                            if(t != 0){
                                continue;
                            }else{
                                institutionMapper.insertIidPid(iid, paper.getPid());
                                i.setPapercited(i.getPapercited() + paper.getArticlecite());
                                i.setPaperamount(i.getPaperamount() + 1);
                                i.setActivity(i.getActivity() + 1);
                                institutionMapper.updateInstitution(i);
                            }

                        }
                    }

                    //读取关键词
                    for(String keywordName : keywordList){
                        //List<Keyword> kl = keywordMapper.selectKeywordByKname(keywordName.trim());
                        Keyword kl = keywordMapper.selectKeywordPOByExactKname(keywordName.trim());
                        if(kl == null){
                            Keyword newKeyword = new Keyword();
                            newKeyword.setKname(keywordName.trim());
                            newKeyword.setPaperamount(1);
                            newKeyword.setActivity(1);
                            keywordMapper.insertKeyword(newKeyword);
                            keywordMapper.insertKidPid(newKeyword.getKid(), paper.getPid());
                        }else{
                            Keyword k = kl;
                            int kid = k.getKid();
                            k.setPaperamount(k.getPaperamount()+1);
                            k.setActivity(k.getActivity()+1);
                            keywordMapper.insertKidPid(kid, paper.getPid());
                            keywordMapper.updateKeyword(k);
                        }
                    }

                    //读取作者-机构对

                    for(int i=0; i<aidList.size(); i++){
                        int t = authorMapper.selectByAidIid(aidList.get(i), iidList.get(i));
                        if(t == 0){
                            authorMapper.insertAidIid(aidList.get(i), iidList.get(i));
                        }
                    }

                    //读取会议

                    String conferenceName = paper.getPubtitle();
                    Conference c = conferenceDetailMapper.selectConferenceByName(conferenceName);
                    if(c == null){
                        c = new Conference();
                        c.setCname(conferenceName);
                        c.setPaperamount(1);
                        conferenceDetailMapper.insertConference(c);
                        conferenceDetailMapper.insertCidPid(c.getCid(), paper.getPid());
                    }else{
                        c.setPaperamount(c.getPaperamount()+1);
                        conferenceDetailMapper.updateConference(c);
                        conferenceDetailMapper.insertCidPid(c.getCid(), paper.getPid());
                    }

                }
            }else{
                return ResponseVO.buildFailure("csv格式错误");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("找不到文件");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("IO错误");
        }finally {
            if(null != reader){
                reader.close();
            }
        }
        return ResponseVO.buildSuccess("上传成功");
    }
}
