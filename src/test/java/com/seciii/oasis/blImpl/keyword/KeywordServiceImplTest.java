package com.seciii.oasis.blImpl.keyword;

import com.seciii.oasis.bl.keyword.KeywordService;
import com.seciii.oasis.po.Author;
import com.seciii.oasis.po.Institution;
import com.seciii.oasis.po.Keyword;
import com.seciii.oasis.vo.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.security.Key;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class KeywordServiceImplTest {

    @Resource
    private KeywordService keywordService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void searchKeywordInfoSuccess(){
        KeywordVO x= (KeywordVO) keywordService.searchKeywordInfo(2627).getContent();//deep learning
        assertEquals(2627,x.getKid());
        assertEquals("deep learning",x.getKname());
        assertEquals(21,x.getPaperCount());
        assertEquals(21,x.getActivity(),0.01);
    }

    @Test
    public void searchKeywordInfoError() {
        assertFalse(keywordService.searchKeywordInfo(1).getSuccess());
        String k=keywordService.searchKeywordInfo(1).getMessage();
        assertEquals("Keyword Not Found",k);
    }

    @Test
    public void searchRelationSuccess(){
        KeywordRelation x= (KeywordRelation) keywordService.searchRelation(2627).getContent();
        assertEquals(2627,x.getKeyword().getKid());
        assertEquals("deep learning",x.getKeyword().getKname());
        assertEquals(21,x.getKeyword().getPaperCount());
        assertEquals(21,x.getKeyword().getActivity(),0.01);
        assertEquals(59,x.getCollaboration().size());
        assertEquals("comment generation",x.getCollaboration().get(0).getKname());
        assertEquals("Mutation Testing",x.getCollaboration().get(10).getKname());
        assertEquals("Code retrieval",x.getCollaboration().get(20).getKname());
        assertEquals("code clone detection",x.getCollaboration().get(30).getKname());
        assertEquals("Recurrent Neural Network",x.getCollaboration().get(40).getKname());
        assertEquals("defect prediction",x.getCollaboration().get(50).getKname());
        assertEquals("joint embedding",x.getCollaboration().get(58).getKname());
        assertEquals(59,x.getRelevance().size());
        for(int i : x.getRelevance()){
            assertEquals(1,i);
        }
    }

    @Test
    public void searchRelationError() {
        assertFalse(keywordService.searchRelation(1).getSuccess());
        String x=keywordService.searchRelation(1).getMessage();
        assertEquals("Keyword Not Exist",x);
    }

    @Test
    public void searchPaperSuccess(){
        List<PaperVO> x= ((PaperVOList) keywordService.searchPaper(2627).getContent()).getPaperVOList();
        assertEquals(21,x.size());
        assertEquals("Retrieve and Refine: Exemplar-Based Neural Comment Generation",x.get(0).getDoctitle());
        assertEquals(1228,x.get(0).getPid());
        assertEquals(2,x.get(0).getCid());
        assertEquals(1,x.get(0).getAidList().size());
        assertEquals(1,x.get(0).getIidList().size());
        assertEquals(3,x.get(0).getKidList().size());
        assertEquals("Combining Deep Learning with Information Retrieval to Localize Buggy Files for Bug Reports (N)",x.get(10).getDoctitle());
        assertEquals("Deep Code Search",x.get(20).getDoctitle());
    }

    @Test
    public void searchPaperError() {
        assertTrue(keywordService.searchPaper(1).getSuccess());
        List<PaperVO> paperVOList= ((PaperVOList) keywordService.searchPaper(1).getContent()).getPaperVOList();
        assertEquals(0,paperVOList.size());
    }

    @Test
    public void searchAuthorListSuccess(){
        List<AuthorVO> x= ((AuthorVOList) keywordService.searchAuthorList(2627).getContent()).getAuthorVOList();
        assertEquals(82,x.size());
        assertEquals(2746,x.get(0).getAid());
        assertEquals("B. Wei",x.get(0).getAname());
        assertEquals(0.0,x.get(0).getActivity(),0.1);
        assertEquals(1,x.get(0).getPaperCount());
        assertEquals(0,x.get(0).getPaperCited());
        assertEquals(1,x.get(0).getInstitutions().size());
        assertEquals("Peking University",x.get(0).getInstitutions().get(0));

        assertEquals("Y. Liu",x.get(10).getAname());
        assertEquals("S. Kim",x.get(81).getAname());
    }

    @Test
    public void searchAuthorListError() {
        assertTrue(keywordService.searchAuthorList(1).getSuccess());
        List<AuthorVO> authorVOList= ((AuthorVOList) keywordService.searchAuthorList(1).getContent()).getAuthorVOList();
        assertEquals(0,authorVOList.size());
    }

    @Test
    public void searchInstitutionListSuccess(){
        List<InstitutionVO> x= ((InstitutionVOList) keywordService.searchInstitutionList(2627).getContent()).getInstitutionVOList();
        assertEquals(35,x.size());
        assertEquals(1435,x.get(0).getIid());
        assertEquals("Peking University",x.get(0).getIname());
        assertEquals(12.0000,x.get(0).getActivity(),0.1);
        assertEquals(12,x.get(0).getPaperCount());
        assertEquals(3,x.get(0).getPapercited());

        assertEquals("Microsoft Research",x.get(10).getIname());
        assertEquals("Technion, Israel",x.get(20).getIname());
        assertEquals("Res. Sch. of Comput. Sciecne, Australian Nat. Univ., Canberra, ACT, Australia",x.get(30).getIname());
        assertEquals("Univ. of Newcastle, Callaghan, NSW, Australia",x.get(34).getIname());
    }

    @Test
    public void searchInstitutionListError() {
        assertTrue(keywordService.searchInstitutionList(1).getSuccess());
        List<InstitutionVO> institutionVOList= ((InstitutionVOList) keywordService.searchInstitutionList(1).getContent()).getInstitutionVOList();
        assertEquals(0,institutionVOList.size());
    }
}