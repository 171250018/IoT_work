package com.seciii.oasis.blImpl.conference;

import com.seciii.oasis.bl.conference.ConferenceService;
import com.seciii.oasis.po.Conference;
import com.seciii.oasis.po.Institution;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConferenceServiceImplTest {

    @Resource
    private ConferenceService conferenceService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void searchConferenceByNameSuccess() {
        //完整
        List<ConferenceVO> x= ((ConferenceVOList) conferenceService.searchConferenceByName("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)").getContent()).getConferenceVOList();
        assertEquals(1,x.size());
        assertEquals(5,x.get(0).getCid());
        assertEquals("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)",x.get(0).getCname());
       assertEquals(100,x.get(0).getPaperCount());

        //不完整
        x= ((ConferenceVOList) conferenceService.searchConferenceByName("2016").getContent()).getConferenceVOList();
        assertEquals(2,x.size());
        assertEquals(5,x.get(1).getCid());
        assertEquals("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)",x.get(1).getCname());
        assertEquals(100,x.get(1).getPaperCount());

        assertEquals(10,x.get(0).getCid());
        assertEquals("2016 IEEE/ACM 38th International Conference on Software Engineering (ICSE)",x.get(0).getCname());
        assertEquals(101,x.get(0).getPaperCount());
    }

    @Test
    public void searchConferenceByNameError() {
        assertFalse(conferenceService.searchConferenceByName("PIG").getSuccess());
        String y=conferenceService.searchConferenceByName("PIG").getMessage();
        assertEquals("Conference Not Found",y);
    }

    @Test
    public void searchConferenceInfoSuccess() {
        ConferenceVO x= (ConferenceVO) conferenceService.searchConferenceInfo(5).getContent();
        assertEquals(5,x.getCid());
        assertEquals("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)",x.getCname());
        assertEquals(100,x.getPaperCount());
    }

    @Test
    public void searchConferenceInfoError() {
        assertFalse(conferenceService.searchConferenceInfo(0).getSuccess());
        String y=conferenceService.searchConferenceInfo(0).getMessage();
        assertEquals("Conference Not Found",y);
    }

    @Test
    public void searchAuthorListSuccess() {
        List<AuthorVO> x= ((AuthorVOList) conferenceService.searchAuthorList(5).getContent()).getAuthorVOList();
        assertEquals(301,x.size());
        assertEquals(3980,x.get(0).getAid());
        assertEquals("M. Hentschel",x.get(0).getAname());
        assertEquals(0.0,x.get(0).getActivity(),0.1);
        assertEquals(2,x.get(0).getPaperCount());
        assertEquals(0,x.get(0).getPaperCited());
        assertEquals(2,x.get(0).getInstitutions().size());
        assertEquals("TU Darmstadt, Darmstadt, Germany",x.get(0).getInstitutions().get(0));
        assertEquals("TU Darmstadt, Dept. of Computer Science, Darmstadt, Germany",x.get(0).getInstitutions().get(1));

        assertEquals("S. Maoz",x.get(300).getAname());
    }

    @Test
    public void searchAuthorListError() {
        assertFalse(conferenceService.searchAuthorList(0).getSuccess());
        String y=conferenceService.searchAuthorList(0).getMessage();
        assertEquals("No Author Info",y);
    }

    @Test
    public void searchInstitutionListSuccess() {
        List<InstitutionVO> x= ((InstitutionVOList) conferenceService.searchInstitutionList(5).getContent()).getInstitutionVOList();
        assertEquals(155,x.size());
        assertEquals(2083,x.get(0).getIid());
        assertEquals("TU Darmstadt, Darmstadt, Germany",x.get(0).getIname());
        assertEquals(1.0000,x.get(0).getActivity(),0.1);
        assertEquals(1,x.get(0).getPaperCount());
        assertEquals(0,x.get(0).getPapercited());

        assertEquals("School of Computer Science, Tel Aviv University, Israel",x.get(154).getIname());
    }

    @Test
    public void searchInstitutionListError() {
        assertFalse(conferenceService.searchInstitutionList(0).getSuccess());
        String y=conferenceService.searchInstitutionList(0).getMessage();
        assertEquals("No Institution Info",y);
    }

    @Test
    public void searchKeywordSuccess() {
        List<KeywordVO> x= ((KeywordVOList) conferenceService.searchKeyword(5).getContent()).getKeywordVOList();
        assertEquals(323,x.size());
        assertEquals(2816,x.get(0).getKid());
        assertEquals("symbolic execution",x.get(0).getKname());
        assertEquals(25.0000,x.get(0).getActivity(),0.1);
        assertEquals(25,x.get(0).getPaperCount());
        assertEquals("Debugging",x.get(1).getKname());
        assertEquals("Program Execution Visualization",x.get(2).getKname());
        assertEquals("verification",x.get(3).getKname());
        assertEquals("issue tracker",x.get(320).getKname());
        assertEquals("open source",x.get(321).getKname());
        assertEquals("patches",x.get(322).getKname());
    }

    @Test
    public void searchKeywordError() {
        assertFalse(conferenceService.searchKeyword(0).getSuccess());
        String y=conferenceService.searchKeyword(0).getMessage();
        assertEquals("No Keyword Info",y);
    }

    @Test
    public void searchPaperSuccess() {
        List<PaperVO> x= ((PaperVOList) conferenceService.searchPaper(5).getContent()).getPaperVOList();
        assertEquals(100,x.size());
        assertEquals("The interactive verification debugger: Effective understanding of interactive proof attempts",x.get(0).getDoctitle());
        assertEquals(1690,x.get(0).getPid());
        //assertEquals(5,x.get(0).getCid());
        //assertEquals(3,x.get(0).getAidList().size());
        //assertEquals(3,x.get(0).getIidList().size());
        //assertEquals(5,x.get(0).getKidList().size());
    }

    @Test
    public void searchPaperError() {
        assertTrue(conferenceService.searchPaper(0).getSuccess());
        List<PaperVO> paperVOList= ((PaperVOList) conferenceService.searchPaper(0).getContent()).getPaperVOList();
        assertEquals(0,paperVOList.size());
    }
}