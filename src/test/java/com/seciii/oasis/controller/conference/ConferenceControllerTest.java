package com.seciii.oasis.controller.conference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConferenceControllerTest {
    @Resource
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }

    @Test
    public void searchByNameSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/conference/searchByName")
                .param("name", "2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].cid").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].cname").value("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].paperCount").value("100"))*/
        ;
    }

    @Test
    public void searchByNameSuccess2() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/searchByName")
                .param("name", "2016")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList.size()").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[1].cid").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[1].cname").value("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[1].paperCount").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].cid").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].cname").value("2016 IEEE/ACM 38th International Conference on Software Engineering (ICSE)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].paperCount").value("101"))*/
        ;
    }

    @Test
    public void searchByNameError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/searchByName")
                .param("name", "PIG")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Conference Not Found"));
    }

    @Test
    public void searchConferenceInfoSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/conference/conferenceInfo")
                .param("cid", "5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.cid").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.cname").value("2016 31st IEEE/ACM International Conference on Automated Software Engineering (ASE)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperCount").value("100"))*/
        ;
    }

    @Test
    public void searchConferenceInfoError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/conferenceInfo")
                .param("cid", "0")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Conference Not Found"));

    }

    @Test
    public void searchPaperSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/conference/paperList")
                .param("cid", "5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList.size()").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].pid").value("1691"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].doctitle").value("The interactive verification debugger: Effective understanding of interactive proof attempts"))*/
        ;
    }

    @Test
    public void searchPaperError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/paperList")
                .param("cid", "0")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList.size()").value("0"))
        ;
    }

    @Test
    public void searchAuthorSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/conference/authorList")
                .param("cid", "5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList.size()").value("301"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aid").value("3980"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aname").value("M. Hentschel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].activity").value(14.5000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCount").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCited").value("29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions.size()").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[0]").value("TU Darmstadt, Darmstadt, Germany"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[1]").value("TU Darmstadt, Dept. of Computer Science, Darmstadt, Germany"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[300].aname").value("S. Maoz"))*/
        ;
    }

    @Test
    public void searchAuthorError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/authorList")
                .param("cid", "0")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Author Info"));

    }

    @Test
    public void searchInstitutionSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/conference/institutionList")
                .param("cid", "5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList.size()").value("155"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iid").value("2083"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iname").value("TU Darmstadt, Darmstadt, Germany"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].activity").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].paperCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].papercited").value("15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[154].iname").value("School of Computer Science, Tel Aviv University, Israel"))*/
        ;
    }

    @Test
    public void searchInstitutionError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/institutionList")
                .param("cid", "0")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Institution Info"));

    }

    @Test
    public void searchKeywordSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/conference/keyword")
                .param("cid", "5")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList.size()").value("323"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].kid").value("2816"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].kname").value("symbolic execution"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].activity").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].paperCount").value("25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[1].kname").value("Debugging"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[2].kname").value("Program Execution Visualization"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[3].kname").value("verification"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[320].kname").value("issue tracker"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[321].kname").value("open source"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[322].kname").value("patches"))*/
        ;
    }

    @Test
    public void searchKeywordError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/conference/keyword")
                .param("cid", "0")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Keyword Info"));

    }
}