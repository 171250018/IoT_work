package com.seciii.oasis.blImpl.institution;

import com.seciii.oasis.bl.institution.InstitutionService;
import com.seciii.oasis.po.Author;
import com.seciii.oasis.vo.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InstitutionServiceImplTest {
    @Resource
    private InstitutionService institutionService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void searchInstitutionByNameSuccess() {
        //完整
        List<InstitutionVO> x= ((InstitutionVOList) institutionService.searchInstitutionByName("Tsinghua University").getContent()).getInstitutionVOList();
        assertEquals(4,x.size());
        assertEquals(1434,x.get(0).getIid());
        assertEquals("Tsinghua University",x.get(0).getIname());
        assertEquals(3,x.get(0).getPaperCount());
        assertEquals(0,x.get(0).getPapercited());
        assertEquals(3,x.get(0).getActivity(),0.1);
        assertEquals("School of Software, Tsinghua University, TNLIST, KLISS, Beijing, China",x.get(1).getIname());
        assertEquals("School of Software, TNLIST, KLISS, Tsinghua University, China",x.get(2).getIname());
        assertEquals("Tsinghua University, China",x.get(3).getIname());

        //不完整
        x= ((InstitutionVOList) institutionService.searchInstitutionByName("Tsinghua").getContent()).getInstitutionVOList();
        assertEquals(6,x.size());
        assertEquals("Tsinghua University",x.get(0).getIname());
        assertEquals("School of Software, Tsinghua University, TNLIST, KLISS, Beijing, China",x.get(1).getIname());
        assertEquals("School of Software, TNLIST, KLISS, Tsinghua University, China",x.get(2).getIname());
        assertEquals("Sch. of Software, Tsinghua Univ., Beijing, China",x.get(3).getIname());
        assertEquals("Tsinghua University, China",x.get(4).getIname());
        assertEquals("Dept. of Ind. Eng., Tsinghua Univ., Beijing, China",x.get(5).getIname());
    }

    @Test
    public void searchInstitutionByNameError(){
        assertTrue(institutionService.searchInstitutionByName("PIG").getSuccess());
        List<InstitutionVO> x= ((InstitutionVOList) institutionService.searchInstitutionByName("PIG").getContent()).getInstitutionVOList();
        assertEquals(0,x.size());
    }

    @Test
    public void searchInstitutionInfoSuccess() {
        InstitutionVO x= ((InstitutionVO) institutionService.searchInstitutionInfo(1434).getContent());//Tsinghua University
        assertEquals(1434,x.getIid());
        assertEquals("Tsinghua University",x.getIname());
        assertEquals(3,x.getPaperCount());
        assertEquals(0,x.getPapercited());
        assertEquals(3.0,x.getActivity(),0.1);
    }

    @Test
    public void searchInstitutionInfoError(){
        String y=institutionService.searchInstitutionInfo(1).getMessage();
        assertEquals("No Such Institution",y);

        y=institutionService.searchInstitutionInfo(-1).getMessage();
        assertEquals("No Such Institution",y);
    }

    @Test
    public void searchKeywordSuccess() {
        List<KeywordVO> x= ((KeywordVOList) institutionService.searchKeyword(1434).getContent()).getKeywordVOList();
        assertEquals(10,x.size());
        assertEquals(2621,x.get(0).getKid());
        assertEquals("pointer analysis",x.get(0).getKname());
        assertEquals(2,x.get(0).getActivity(),0.1);
        assertEquals(2,x.get(0).getPaperCount());
        assertEquals("uninitialized pointer",x.get(1).getKname());
        assertEquals("sensitivity",x.get(2).getKname());
        assertEquals("multi-entry",x.get(3).getKname());
        assertEquals("Error Handling",x.get(4).getKname());
        assertEquals("Error Specification",x.get(5).getKname());
        assertEquals("Static Analysis",x.get(6).getKname());
        assertEquals("Fuzz Testing",x.get(7).getKname());
        assertEquals("software testing",x.get(8).getKname());
        assertEquals("Visualization",x.get(9).getKname());
    }

    @Test
    public void searchKeywordError(){
        assertTrue(institutionService.searchKeyword(1).getSuccess());
        List<KeywordVO> x= ((KeywordVOList) institutionService.searchKeyword(1).getContent()).getKeywordVOList();
        assertEquals(0,x.size());
    }

    @Test
    public void searchAuthorListSuccess() {
        List<AuthorVO> x= ((AuthorVOList) institutionService.searchAuthorList(1434).getContent()).getAuthorVOList();
        assertEquals(12,x.size());
        assertEquals(2741,x.get(0).getAid());
        assertEquals("Y. Wang",x.get(0).getAname());
        assertEquals(5.6875,x.get(0).getActivity(),0.1);
        assertEquals(9,x.get(0).getPaperCount());
        assertEquals(76,x.get(0).getPaperCited());
        assertEquals(9,x.get(0).getInstitutions().size());
        assertEquals("Tsinghua University",x.get(0).getInstitutions().get(0));
        assertEquals("Nanjing University",x.get(0).getInstitutions().get(1));
        assertEquals("Rochester Institute of Technology",x.get(0).getInstitutions().get(2));
        assertEquals("Alibaba Group",x.get(0).getInstitutions().get(3));
        assertEquals("Key Laboratory for Information System Security, Ministry of Education, China",x.get(0).getInstitutions().get(4));
        assertEquals("Ohio State Univ., Columbus, OH, USA",x.get(0).getInstitutions().get(5));
        assertEquals("University of California, Davis",x.get(0).getInstitutions().get(6));
        assertEquals("Northeastern University",x.get(0).getInstitutions().get(7));
        assertEquals("NA",x.get(0).getInstitutions().get(8));

        assertEquals("Z. Gu",x.get(6).getAname());
        assertEquals("Y. Jiang",x.get(11).getAname());
    }

    @Test
    public void searchAuthorListError(){
        assertTrue(institutionService.searchAuthorList(1).getSuccess());
        List<AuthorVO> x= ((AuthorVOList) institutionService.searchAuthorList(1).getContent()).getAuthorVOList();
        assertEquals(0,x.size());
    }

    @Test
    public void searchCollaborateSuccess() {
        InstitutionRelation x= (InstitutionRelation) institutionService.searchCollaborate(1434).getContent();
        assertEquals(1434,x.getInstitution().getIid());
        assertEquals("Tsinghua University",x.getInstitution().getIname());
        assertEquals(2,x.getCollaboration().size());
        assertEquals("Nanjing University of Aeronautics and Astronautics",x.getCollaboration().get(0).getIname());
        assertEquals("Waterloo University",x.getCollaboration().get(1).getIname());
        assertEquals(2,x.getRelevance().size());
        for(int i : x.getRelevance()){
            assertEquals(1,i);
        }
    }

    @Test
    public void searchCollaborateError(){
        String y=institutionService.searchCollaborate(1).getMessage();
        assertEquals("No Such Institution",y);
    }

    @Test
    public void searchPaperSuccess() {
        List<PaperVO> x= ((PaperVOList) institutionService.searchPaper(1434).getContent()).getPaperVOList();
        assertEquals(3,x.size());
        assertEquals("TsmartGP: A Tool for Finding Memory Defects with Pointer Analysis",x.get(0).getDoctitle());
        assertEquals(1227,x.get(0).getPid());
        //assertEquals(2,x.get(0).getCid());
        //assertEquals(5,x.get(0).getAidList().size());
        //assertEquals(5,x.get(0).getIidList().size());
        //assertEquals(4,x.get(0).getKidList().size());
        assertEquals("Ares: Inferring Error Specifications through Static Analysis",x.get(1).getDoctitle());
        assertEquals("VisFuzz: Understanding and Intervening Fuzzing with Interactive Visualization",x.get(2).getDoctitle());
    }

    @Test
    public void searchPaperError(){
        assertTrue(institutionService.searchPaper(1).getSuccess());
        List<PaperVO> x= ((PaperVOList) institutionService.searchPaper(1).getContent()).getPaperVOList();
        assertEquals(0,x.size());
    }
}