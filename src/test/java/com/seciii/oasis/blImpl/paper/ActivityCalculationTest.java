package com.seciii.oasis.blImpl.paper;

import com.seciii.oasis.data.author.AuthorDetailMapper;
import com.seciii.oasis.data.institution.InstitutionDetailMapper;
import com.seciii.oasis.data.keyword.KeywordDetailMapper;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.InstitutionMapper;
import com.seciii.oasis.data.paper.KeywordMapper;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.blImpl.paper.ActivityCalculation;
import com.seciii.oasis.po.Institution;
import com.seciii.oasis.po.Paper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActivityCalculationTest {
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    private KeywordMapper keywordMapper;
    @Resource
    private AuthorDetailMapper authorDetailMapper;
    @Resource
    private InstitutionDetailMapper institutionDetailMapper;
    @Resource
    private KeywordDetailMapper keywordDetailMapper;
    @Resource
    private ActivityCalculation activityCalculation;

    @Before
    public void setUp() throws Exception {
        Paper p1=new Paper();
        p1.setPid(1);
        p1.setDoctitle("Why Happy?");
        p1.setAuthors("A;B;C;D");
        p1.setInstitutions("aa;bb;cc;dd");
        p1.setPubtitle("2019 3th IEEE/ACM International Conference on Automated Software Engineering (ASE)");
        p1.setDatx("");
        p1.setPubyear("209");
        p1.setStartpage("12");
        p1.setEndpage("14");
        p1.setAbstracts("The first One");
        p1.setDoi("1.1/A.1.1");
        p1.setPdflink("http://1");
        p1.setKeywords("aaa;bbb;ccc;ddd;eee");
        p1.setArticlecite(1);
        p1.setReference(2);
        p1.setDocidentifier("IEEE Conferences");
        paperMapper.insertPaper(p1);
        p1=paperMapper.selectPaperByTitle("Why Happy?",0,10).get(0);
        //authorMapper.insertAidPid(p1.getPid(),);


        Paper p2=new Paper();
        p2.setDoctitle("Not Happy?");
        p2.setAuthors("B;F;E;A");
        p2.setInstitutions("bb;ee;rr;dd");
        p2.setPubtitle("(ASE)");
        p2.setDatx("");
        p2.setPubyear("209");
        p2.setStartpage("12");
        p2.setEndpage("14");
        p2.setAbstracts("The second One");
        p2.setDoi("1.1/C.1.1");
        p2.setPdflink("http://2");
        p2.setKeywords("ccc;eee;mmm;bbb;rrr");
        p2.setArticlecite(0);
        p2.setReference(3);
        p2.setDocidentifier("IEEE Conferences");
        paperMapper.insertPaper(p2);

        Paper p3=new Paper();
        p3.setDoctitle("Why Not Happy?");
        p3.setAuthors("A");
        p3.setInstitutions("cc;dd");
        p3.setPubtitle("2018 37th IEEE/ACM International Conference on Automated Software Engineering (ASE)");
        p3.setDatx("");
        p3.setPubyear("208");
        p3.setStartpage("12");
        p3.setEndpage("14");
        p3.setAbstracts("The third One");
        p3.setDoi("1.1/f.1.1");
        p3.setPdflink("http://3");
        p3.setKeywords("mmm;aaa");
        p3.setArticlecite(3);
        p3.setReference(0);
        p3.setDocidentifier("IEEE Conferences");
        paperMapper.insertPaper(p3);

        Paper p4=new Paper();
        p4.setDoctitle("Why Not?");
        p4.setAuthors("B;C;A");
        p4.setInstitutions("cc;ff;bb");
        p4.setPubtitle("2018 37th IEEE/ACM International Conference on Automated Software Engineering (ASE)");
        p4.setDatx("");
        p4.setPubyear("207");
        p4.setStartpage("12");
        p4.setEndpage("14");
        p4.setAbstracts("The fourth One");
        p4.setDoi("1.1/q.1.1");
        p4.setPdflink("http://4");
        p4.setKeywords("mmm;vvv");
        p4.setArticlecite(12);
        p4.setReference(0);
        p4.setDocidentifier("IEEE Conferences");
        paperMapper.insertPaper(p4);


    }

    @Test
    public void calAuthorActivity() {
        double x=activityCalculation.calAuthorActivity(1);
        assertEquals(0,x,0.1);
    }

    @Test
    public void calInstitutionActivity() {
        int x=activityCalculation.calInstitutionActivity(1);
        assertEquals(0,x);
    }

    @Test
    public void calKeywordActivity() {
        int x=activityCalculation.calKeywordActivity(1);
        assertEquals(0,x);
    }
}