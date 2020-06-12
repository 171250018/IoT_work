package com.seciii.oasis.blImpl.author;

import com.seciii.oasis.bl.author.AuthorService;
import com.seciii.oasis.bl.paper.SearchService;
import com.seciii.oasis.po.Author;
import com.seciii.oasis.po.Conference;
import com.seciii.oasis.po.Keyword;
import com.seciii.oasis.po.Paper;
import com.seciii.oasis.vo.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthorServiceImplTest {

    @Resource
    private AuthorService authorService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void searchAuthorByNameSuccess() {
        //完整
        List<AuthorVO> x= ((AuthorVOList) authorService.searchAuthorByName("H. Zhang").getContent()).getAuthorVOList();
        assertEquals(1,x.size());
        assertEquals(2765,x.get(0).getAid());
        assertEquals("H. Zhang",x.get(0).getAname());
        /*assertEquals(13,x.get(0).getInstitutions().size());
        assertEquals("Tsinghua University",x.get(0).getInstitutions().get(0));
        assertEquals("The University of Newcastle",x.get(0).getInstitutions().get(1));
        assertEquals("Ohio State Univ., Columbus, OH, USA",x.get(0).getInstitutions().get(4));
        assertEquals("Key Lab. of High Confidence Software Technol., Peking Univ., Beijing, China",x.get(0).getInstitutions().get(5));
        assertEquals("Univ. of Newcastle, Newcastle, NSW, Australia",x.get(0).getInstitutions().get(8));
        assertEquals("The University of Newcastle, Australia",x.get(0).getInstitutions().get(9));
        assertEquals("Univ. of Newcastle, Callaghan, NSW, Australia",x.get(0).getInstitutions().get(12));*/
        assertEquals(21,x.get(0).getPaperCount());
        assertEquals(177,x.get(0).getPaperCited());

        //不完整
        x= ((AuthorVOList) authorService.searchAuthorByName("Zhang").getContent()).getAuthorVOList();
        assertEquals(22,x.size());
        assertEquals(3977,x.get(14).getAid());
        assertEquals("L. L. Zhang",x.get(14).getAname());
        /*assertEquals(1,x.get(14).getInstitutions().size());
        assertEquals("Wayne State University",x.get(14).getInstitutions().get(0));*/
        assertEquals(1,x.get(14).getPaperCount());
        assertEquals(4,x.get(14).getPaperCited());

        assertEquals(3841,x.get(17).getAid());
        assertEquals("S. J. Zhang",x.get(17).getAname());
        /*assertEquals(3,x.get(17).getInstitutions().size());
        assertEquals("Microsoft Research",x.get(17).getInstitutions().get(0));
        assertEquals("Microsoft Research Asia, Beijing, China",x.get(17).getInstitutions().get(1));
        assertEquals("Microsoft Res., Beijing, China",x.get(17).getInstitutions().get(2));*/
        assertEquals(1,x.get(17).getPaperCount());
        assertEquals(1,x.get(17).getPaperCited());

        assertEquals(3036,x.get(12).getAid());
        assertEquals("D. Zhang",x.get(12).getAname());
        /*assertEquals(1,x.get(12).getInstitutions().size());
        assertEquals("Computer Science & Engineering, University of Washington, USA",x.get(12).getInstitutions().get(0));*/
        assertEquals(5,x.get(12).getPaperCount());
        assertEquals(92,x.get(12).getPaperCited());

        assertEquals(3881,x.get(16).getAid());
        assertEquals("B. Zhang",x.get(16).getAname());
        /*assertEquals("State Key Laboratory of Computer Science Institute of Software, Chinese Academy of Sciences, Beijing, China",x.get(16).getInstitutions().get(0));
        assertEquals("Purdue University, USA",x.get(16).getInstitutions().get(1));*/
        assertEquals(2,x.get(16).getPaperCount());
        assertEquals(3,x.get(16).getPaperCited());
    }

    @Test
    public void searchAuthorByNameError(){
        //List<AuthorVO> x= ((AuthorVOList) authorService.searchAuthorByName("PIG").getContent()).getAuthorVOList();
        //assertEquals(0,x.size());
        String y=authorService.searchAuthorByName("PIG").getMessage();
        assertEquals("Author Not Found",y);
    }

    @Test
    public void searchAuthorInfoSuccess() {
        AuthorVO x= (AuthorVO) authorService.searchAuthorInfo(3000).getContent();
        assertEquals(3000,x.getAid());
        assertEquals("T. Pfitzer",x.getAname());
        assertEquals(1,x.getPaperCount());
        assertEquals(0,x.getPaperCited());
        assertEquals(1,x.getInstitutions().size());
        assertEquals("Robert Bosch Automotive Steering GmbH",x.getInstitutions().get(0));
        assertEquals(0.0,x.getActivity(),0.01);
    }

    @Test
    public void searchAuthorInfoError(){
        assertFalse(authorService.searchAuthorInfo(1).getSuccess());
        String x=authorService.searchAuthorInfo(1).getMessage();
        assertEquals(x,"Author Not Found");
    }

    @Test
    public void searchKeywordSuccess() {
        List<KeywordVO> x= ((KeywordVOList) authorService.searchKeyword(3000).getContent()).getKeywordVOList();
        assertEquals(4,x.size());
        assertEquals(2957,x.get(0).getKid());
        assertEquals("search-based testing",x.get(0).getKname());
        assertEquals(1,x.get(0).getActivity(),0.1);
        assertEquals(1,x.get(0).getPaperCount());
        assertEquals("automated driving",x.get(1).getKname());
        assertEquals("automated test generation",x.get(2).getKname());
        assertEquals("experience paper",x.get(3).getKname());
    }

    @Test
    public void searchKeywordError(){
        assertTrue(authorService.searchKeyword(1).getSuccess());
        List<KeywordVO> x= ((KeywordVOList) authorService.searchKeyword(1).getContent()).getKeywordVOList();
        assertEquals(0,x.size());
    }

    @Test
    public void searchCollaborateSuccess() {
        AuthorRelation x= (AuthorRelation) authorService.searchCollaborate(3000).getContent();
        assertEquals(3000,x.getAuthor().getAid());
        assertEquals("T. Pfitzer",x.getAuthor().getAname());
        assertEquals(5,x.getCollaboration().size());
        assertEquals("C. Gladisch",x.getCollaboration().get(0).getAname());
        assertEquals("T. Heinz",x.getCollaboration().get(1).getAname());
        assertEquals("C. Heinzemann",x.getCollaboration().get(2).getAname());
        assertEquals("J. Oehlerking",x.getCollaboration().get(3).getAname());
        assertEquals("A. von Vietinghoff",x.getCollaboration().get(4).getAname());
        assertEquals(5,x.getRelevance().size());
        for(int i : x.getRelevance()){
            assertEquals(1,i);
        }
    }

    @Test
    public void searchCollaborateError(){
        //assertTrue(authorService.searchCollaborate(1).getSuccess());
        String y=authorService.searchCollaborate(1).getMessage();
        assertEquals("No Such Author",y);
    }

    @Test
    public void searchConferenceSuccess() {
        List<ConferenceVO> x= ((ConferenceVOList) authorService.searchConference(3000).getContent()).getConferenceVOList();
        assertEquals(1,x.size());
        assertEquals("2019 34th IEEE/ACM International Conference on Automated Software Engineering (ASE)",x.get(0).getCname());
        assertEquals(2,x.get(0).getCid());
        assertEquals(154,x.get(0).getPaperCount());
    }

    @Test
    public void searchConferenceError(){
        assertTrue(authorService.searchKeyword(1).getSuccess());
        List<ConferenceVO> x= ((ConferenceVOList) authorService.searchConference(1).getContent()).getConferenceVOList();
        assertEquals(0,x.size());
    }

    @Test
    public void searchPaperSuccess() {
        List<PaperVO> x= ((PaperVOList) authorService.searchPaper(3000).getContent()).getPaperVOList();
        assertEquals(1,x.size());
        assertEquals("Experience Paper: Search-Based Testing in Automated Driving Control Applications",x.get(0).getDoctitle());
        assertEquals(1320,x.get(0).getPid());
        //assertEquals(2,x.get(0).getCid());
        //assertEquals(6,x.get(0).getAidList().size());
        //assertEquals(6,x.get(0).getIidList().size());
        //assertEquals(4,x.get(0).getKidList().size());

        x= ((PaperVOList) authorService.searchPaper(2747).getContent()).getPaperVOList();
        assertEquals(10,x.size());
        assertEquals("A Practical Guide to Select Quality Indicators for Assessing Pareto-Based Search Algorithms in Search-Based Software Engineering",x.get(0).getDoctitle());
        assertEquals(2430,x.get(0).getPid());
        assertEquals(65,x.get(0).getReference());
        /*assertEquals(1915,x.get(3).getPid());
        assertEquals(45,x.get(3).getReference());
        assertEquals(1229,x.get(6).getPid());
        assertEquals(14,x.get(6).getReference());
        assertEquals(1391,x.get(9).getPid());
        assertEquals(29,x.get(9).getReference());*/
    }

    @Test
    public void searchPaperError(){
        assertTrue(authorService.searchPaper(1).getSuccess());
        List<PaperVO> x= ((PaperVOList) authorService.searchPaper(1).getContent()).getPaperVOList();
        assertEquals(0,x.size());
    }
}