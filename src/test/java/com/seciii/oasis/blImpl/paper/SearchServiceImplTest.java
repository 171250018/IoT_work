package com.seciii.oasis.blImpl.paper;

import com.seciii.oasis.bl.paper.SearchService;
import com.seciii.oasis.data.paper.AuthorMapper;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.Author;
import com.seciii.oasis.po.Paper;
import com.seciii.oasis.vo.PaperVO;
import com.seciii.oasis.vo.ResponseVO;
import com.seciii.oasis.vo.ResultList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SearchServiceImplTest {

    @Resource
    private PaperMapper paperMapper;
    @Resource
    private SearchService searchService;

    //private Paper p1=new Paper();
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
    public void searchPaperTitle() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(0,"Happy?",1).getContent()).getResultlist();
        assertEquals(3,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Not Happy?",x.get(1).getDoctitle());
        assertEquals("Why Not Happy?",x.get(2).getDoctitle());

        x= ((ResultList) searchService.searchPaper(0,"Not Happy?",1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Not Happy?",x.get(0).getDoctitle());
        assertEquals("Why Not Happy?",x.get(1).getDoctitle());

        x= ((ResultList) searchService.searchPaper(0,"appy?",1).getContent()).getResultlist();
        assertEquals(3,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Not Happy?",x.get(1).getDoctitle());
        assertEquals("Why Not Happy?",x.get(2).getDoctitle());
    }

    @Test
    public void searchPaperTitleError() {
        String x=searchService.searchPaper(0,"Bappy",1).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByAuthor() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(1,"R.",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1238,x.get(0).getPid());
        assertEquals(1242,x.get(1).getPid());
        assertEquals(1251,x.get(2).getPid());
        assertEquals(1253,x.get(3).getPid());
        assertEquals(1265,x.get(4).getPid());
        assertEquals(1266,x.get(5).getPid());
        assertEquals(1272,x.get(6).getPid());
        assertEquals(1303,x.get(7).getPid());
        assertEquals(1316,x.get(8).getPid());
        assertEquals(1324,x.get(9).getPid());
        //assertEquals(41,x.get(7).getPid());


        x= ((ResultList) searchService.searchPaper(1,"Zhang",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1230,x.get(0).getPid());
        assertEquals(1236,x.get(1).getPid());
        assertEquals(1245,x.get(2).getPid());
        assertEquals(1258,x.get(3).getPid());
        assertEquals(1282,x.get(4).getPid());
        assertEquals(1323,x.get(5).getPid());
        assertEquals(1332,x.get(6).getPid());
        assertEquals(1341,x.get(7).getPid());
        assertEquals(1342,x.get(8).getPid());
        assertEquals(1356,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(1,"X. Chen",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1229,x.get(0).getPid());
        assertEquals(1234,x.get(1).getPid());
        assertEquals(1322,x.get(2).getPid());
        assertEquals(1341,x.get(3).getPid());
        assertEquals(1547,x.get(4).getPid());
        assertEquals(1566,x.get(5).getPid());
        assertEquals(1679,x.get(6).getPid());
        assertEquals(1763,x.get(7).getPid());
        assertEquals(1787,x.get(8).getPid());
        assertEquals(1954,x.get(9).getPid());
    }

    @Test
    public void searchPaperByAuthorError() {
        String x=searchService.searchPaper(1,"67",1).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByAuthorError2() {
        String x=searchService.searchPaper(1,"67",2).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByAuthorAll() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(1,null,1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1234,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(1,null,2).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1235,x.get(0).getPid());
        assertEquals(1244,x.get(9).getPid());
    }

    @Test
    public void searchPaperByInstitution() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(2,"Peking University",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1228,x.get(0).getPid());
        assertEquals(1229,x.get(1).getPid());
        assertEquals(1337,x.get(7).getPid());
        assertEquals(1341,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(2,"Peking University",2).getContent()).getResultlist();
        assertEquals(3,x.size());
        assertEquals(1357,x.get(0).getPid());
        assertEquals(1400,x.get(1).getPid());
        assertEquals(1748,x.get(2).getPid());

        x= ((ResultList) searchService.searchPaper(2,"Shenzhen",1).getContent()).getResultlist();
        assertEquals(8,x.size());
        assertEquals(1241,x.get(0).getPid());
        assertEquals(1367,x.get(1).getPid());
        assertEquals(1838,x.get(2).getPid());
        assertEquals(1853,x.get(3).getPid());
        assertEquals(1874,x.get(4).getPid());
        assertEquals(2178,x.get(5).getPid());
        assertEquals(2285,x.get(6).getPid());
        assertEquals(2468,x.get(7).getPid());

        x= ((ResultList) searchService.searchPaper(2,"inica",1).getContent()).getResultlist();
        assertEquals(1,x.size());
        assertEquals(1367,x.get(0).getPid());
    }

    @Test
    public void searchPaperByInstitutionError() {
        String x=searchService.searchPaper(2,"NJU",1).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByInstitutionError2() {
        String x=searchService.searchPaper(2,"67",2).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByInstitutionAll() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(2,null,1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1226,x.get(1).getPid());
        assertEquals(1231,x.get(6).getPid());
        assertEquals(1234,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(2,null,2).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1235,x.get(0).getPid());
        assertEquals(1236,x.get(1).getPid());
        assertEquals(1241,x.get(6).getPid());
        assertEquals(1244,x.get(9).getPid());
    }

    @Test
    public void searchPaperByPubTitle() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(3,"2013",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1406,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(3,"(ASE)",13).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1345,x.get(0).getPid());
        assertEquals(1346,x.get(1).getPid());
        assertEquals(1349,x.get(4).getPid());
        assertEquals(1350,x.get(5).getPid());
        assertEquals(1354,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(3,"2013 28th IEEE/ACM International Conference on Automated Software Engineering (ASE)",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1286,x.get(1).getPid());
        assertEquals(1288,x.get(2).getPid());
        assertEquals(1294,x.get(3).getPid());
        assertEquals(1298,x.get(4).getPid());
        assertEquals(1299,x.get(5).getPid());
        assertEquals(1301,x.get(6).getPid());
        assertEquals(1303,x.get(7).getPid());
        assertEquals(1405,x.get(8).getPid());
        assertEquals(1406,x.get(9).getPid());
    }

    @Test
    public void searchPaperByPubtitleError() {
        String x=searchService.searchPaper(3,"13th",1).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByPubtitleAll() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(3,"",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1226,x.get(1).getPid());
        assertEquals(1231,x.get(6).getPid());
        assertEquals(1234,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(3,null,1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1226,x.get(1).getPid());
        assertEquals(1231,x.get(6).getPid());
        assertEquals(1234,x.get(9).getPid());
    }

    @Test
    public void searchPaperByKeyword() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(4,"deep learning",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1228,x.get(0).getPid());
        assertEquals(1229,x.get(1).getPid());
        assertEquals(1230,x.get(2).getPid());
        assertEquals(1257,x.get(3).getPid());
        assertEquals(1263,x.get(4).getPid());
        assertEquals(1271,x.get(5).getPid());
        assertEquals(1318,x.get(6).getPid());
        assertEquals(1319,x.get(7).getPid());
        assertEquals(1332,x.get(8).getPid());
        assertEquals(1354,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(4,"learning",2).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1317,x.get(0).getPid());
        assertEquals(1318,x.get(1).getPid());
        assertEquals(1332,x.get(4).getPid());
        assertEquals(1354,x.get(5).getPid());
        assertEquals(1377,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(4,"Build Failures",1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals(1233,x.get(0).getPid());
        assertEquals(1275,x.get(1).getPid());

        x= ((ResultList) searchService.searchPaper(4,"comprehension;deep",1).getContent()).getResultlist();
        assertEquals(1,x.size());
        assertEquals(1228,x.get(0).getPid());
    }

    @Test
    public void searchPaperByKeyWordError() {
        String x=searchService.searchPaper(4,"ABCD",1).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void searchPaperByKeyWordAll() {
        ArrayList<PaperVO> x= ((ResultList) searchService.searchPaper(4,null,1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1226,x.get(1).getPid());
        assertEquals(1231,x.get(6).getPid());
        assertEquals(1234,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(4,null,2).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1235,x.get(0).getPid());
        assertEquals(1236,x.get(1).getPid());
        assertEquals(1241,x.get(6).getPid());
        assertEquals(1244,x.get(9).getPid());

        x= ((ResultList) searchService.searchPaper(4,"",1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1226,x.get(1).getPid());
        assertEquals(1231,x.get(6).getPid());
        assertEquals(1234,x.get(9).getPid());
    }

    @Test
    public void searchPaperError() {
        String x=searchService.searchPaper(7,"Bappy",1).getMessage();
        assertEquals("No such type",x);
    }

    @Test
    public void combineSearchSimple() { //单项

        //date
        ArrayList<PaperVO> x= ((ResultList) searchService.combineSearch("","2013", "2017","","", "","", "", 1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1284,x.get(1).getPid());
        assertEquals(1287,x.get(4).getPid());
        assertEquals(1288,x.get(5).getPid());
        assertEquals(1292,x.get(9).getPid());

        //title
        x= ((ResultList) searchService.combineSearch("Happy","", "","", "","","", "", 1).getContent()).getResultlist();
        assertEquals(4,x.size());
        assertEquals(2270,x.get(0).getPid());
        assertEquals("Why Happy?",x.get(1).getDoctitle());
        assertEquals("Not Happy?",x.get(2).getDoctitle());
        assertEquals("Why Not Happy?",x.get(3).getDoctitle());

        //doi
        x= ((ResultList) searchService.combineSearch("", "","","1.1/f.1.1", "","","", "", 1).getContent()).getResultlist();
        assertEquals(1,x.size());
        assertEquals("Why Not Happy?",x.get(0).getDoctitle());

        //pubtitle
        x= ((ResultList) searchService.combineSearch("", "207","209","", "2018","","", "", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Why Not Happy?",x.get(0).getDoctitle());
        assertEquals("Why Not?",x.get(1).getDoctitle());

        //author
        x= ((ResultList) searchService.combineSearch("", "","","", "","C;A","", "", 1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1227,x.get(0).getPid());
        assertEquals(1229,x.get(1).getPid());
        assertEquals(1239,x.get(4).getPid());
        assertEquals(1243,x.get(5).getPid());
        assertEquals(1253,x.get(9).getPid());

        String y=searchService.combineSearch("", "","","", "","C;PIG","", "", 1).getMessage();
        assertEquals("没有找到符合条件的结果",y);

        y=searchService.combineSearch("", "","","", "","piq","", "", 1).getMessage();
        assertEquals("没有找到符合条件的结果",y);

        //keyword
        x= ((ResultList) searchService.combineSearch("", "","","", "","","ccc;bbb", "", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Not Happy?",x.get(1).getDoctitle());

        y=searchService.combineSearch("", "","","", "","","ccc;bbb;ttt", "", 1).getMessage();
        assertEquals("没有找到符合条件的结果",y);

        y=searchService.combineSearch("", "","","", "","","ttt", "", 1).getMessage();
        assertEquals("没有找到符合条件的结果",y);

        //institution
        x= ((ResultList) searchService.combineSearch("", "","","", "","","", "cc;bb", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Why Not?",x.get(1).getDoctitle());

        y=searchService.combineSearch("", "","","", "","","", "cc;bb;tt", 1).getMessage();
        assertEquals("没有找到符合条件的结果",y);

        y=searchService.combineSearch("", "","","", "","","", "vv", 1).getMessage();
        assertEquals("没有找到符合条件的结果",y);

        //page
        x= ((ResultList) searchService.combineSearch("", "","","", "","","", "", 1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals("Big problems in industry (panel)",x.get(0).getDoctitle());
        assertEquals(1234,x.get(9).getPid());
        assertEquals("Better Development of Safety Critical Systems: Chinese High Speed Railway System Development Experience Report",x.get(9).getDoctitle());

        x= ((ResultList) searchService.combineSearch("", "","","", "","","", "", 2).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1235,x.get(0).getPid());
        assertEquals("User Preference Aware Multimedia Pricing Model using Game Theory and Prospect Theory for Wireless Communications",x.get(0).getDoctitle());
        assertEquals(1244,x.get(9).getPid());
        assertEquals("Automatic Generation of Graphical User Interface Prototypes from Unrestricted Natural Language Requirements",x.get(9).getDoctitle());

        y=searchService.combineSearch("", "","","", "","","", "", 200).getMessage();
        assertEquals("没有找到符合条件的结果",y);

    }

    @Test
    public void combineSearchSuccess1() {
        ArrayList<PaperVO> x= ((ResultList) searchService.combineSearch("Happy","209", "209","", "","","", "", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Not Happy?",x.get(1).getDoctitle());

        x= ((ResultList) searchService.combineSearch("Not", "208","209","", "","","", "", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Not Happy?",x.get(0).getDoctitle());
        assertEquals("Why Not Happy?",x.get(1).getDoctitle());
    }

    @Test
    public void combineSearchSuccess2() {
        ArrayList<PaperVO> x= ((ResultList) searchService.combineSearch("Happy","207", "209","", "","A;B","", "", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Not Happy?",x.get(1).getDoctitle());

        x= ((ResultList) searchService.combineSearch("Happy","207", "209","", "","","", "dd;bb", 1).getContent()).getResultlist();
        assertEquals(2,x.size());
        assertEquals("Why Happy?",x.get(0).getDoctitle());
        assertEquals("Not Happy?",x.get(1).getDoctitle());

        x= ((ResultList) searchService.combineSearch("","207", "209","", "","","rrr", "dd;bb", 1).getContent()).getResultlist();
        assertEquals(1,x.size());
        assertEquals("Not Happy?",x.get(0).getDoctitle());

        x= ((ResultList) searchService.combineSearch("Happy","207", "209","", "","A;B","mmm", "bb", 1).getContent()).getResultlist();
        assertEquals(1,x.size());
        assertEquals("Not Happy?",x.get(0).getDoctitle());
    }

    @Test
    public void combineSearchError() {
        String x=searchService.combineSearch("","2017", "2019","", "","A;B","rrr", "jjjjj", 1).getMessage();
        assertEquals("没有找到符合条件的结果",x);

        x=searchService.combineSearch("Happy","2017", "2019","", "","A;B","mmm", "cc;dd", 1).getMessage();
        assertEquals("没有找到符合条件的结果",x);

        x=searchService.combineSearch("Happy","2017", "2019","", "","A;B","mmm", "bb", 2).getMessage();
        assertEquals("没有找到符合条件的结果",x);
    }

    @Test
    public void combineSearchStrange() {
        ArrayList<PaperVO> x= ((ResultList) searchService.combineSearch("", "","","", "",";;;;;","", "", 1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1234,x.get(9).getPid());

        x= ((ResultList) searchService.combineSearch("", "","","", "","",";;;;;", "", 1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1234,x.get(9).getPid());

        x= ((ResultList) searchService.combineSearch("", "","","", "","","", ";;;", 1).getContent()).getResultlist();
        assertEquals(10,x.size());
        assertEquals(1225,x.get(0).getPid());
        assertEquals(1234,x.get(9).getPid());
    }

    @Test
    public void searchPaperByPidSuccess() {
        PaperVO x=(PaperVO) searchService.searchPaperByPid(1791).getContent();
        assertEquals("3rd FME Workshop on Formal Methods in Software Engineering (FormaliSE 2015)",x.getDoctitle());
    }

    @Test
    public void searchPaperByPidError() {
        String x=searchService.searchPaperByPid(1).getMessage();
        assertEquals("No such paper",x);
    }

}