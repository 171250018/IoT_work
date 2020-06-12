package com.seciii.oasis.controller.author;

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
public class AuthorControllerTest {
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
        mvc.perform(MockMvcRequestBuilders.get("/author/searchByName")
                .param("name", "H. Zhang")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aid").value("2765"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aname").value("H. Zhang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions.size()").value("13"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[0]").value("Tsinghua University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[1]").value("The University of Newcastle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[4]").value("Ohio State Univ., Columbus, OH, USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[5]").value("Key Lab. of High Confidence Software Technol., Peking Univ., Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[8]").value("Univ. of Newcastle, Newcastle, NSW, Australia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[9]").value("The University of Newcastle, Australia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[12]").value("Univ. of Newcastle, Callaghan, NSW, Australia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCount").value("21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCited").value("874"))*/
        ;

    }

    @Test
    public void searchByNameSuccess2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/author/searchByName")
                .param("name", "Zhang")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList.size()").value("22"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[14].aid").value("2751"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[14].aname").value("K. Zhang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[14].institutions.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[14].institutions[0]").value("Wayne State University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[14].paperCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[14].paperCited").value("18"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].aid").value("3036"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].aname").value("D. Zhang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].institutions.size()").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].institutions[0]").value("Microsoft Research"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].institutions[1]").value("Microsoft Research Asia, Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].institutions[2]").value("Microsoft Res., Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].paperCount").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[17].paperCited").value("198"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[12].aid").value("3316"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[12].aname").value("S. Zhang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[12].institutions.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[12].institutions[0]").value("Computer Science & Engineering, University of Washington, USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[12].paperCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[12].paperCited").value("35"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].aid").value("3858"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].aname").value("Z. Zhang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].institutions.size()").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].institutions[0]").value("State Key Laboratory of Computer Science Institute of Software, Chinese Academy of Sciences, Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].institutions[1]").value("Purdue University, USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].paperCount").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[16].paperCited").value("106"));*/
    }

    @Test
    public void searchByNameError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/author/searchByName")
                .param("name", "PIG")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Author Not Found"));*/
    }

    @Test
    public void searchAuthorInfoSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/author/authorInfo")
                .param("aid", "3000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.aid").value("3000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.aname").value("T. Pfitzer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperCited").value("43"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutions.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutions[0]").value("Robert Bosch Automotive Steering GmbH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.activity").value("0.6719"))*/
        ;
    }

    @Test
    public void searchAuthorInfoError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/author/authorInfo")
                .param("aid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Author Not Found"));
    }

    @Test
    public void searchAuthorKeywordSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/author/keyword")
                .param("aid", "3000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList.size()").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].kid").value("2957"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].kname").value("search-based testing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].paperCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].activity").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[1].kname").value("automated driving"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[2].kname").value("automated test generation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[3].kname").value("experience paper"))*/
        ;
    }

    @Test
    public void searchAuthorKeywordError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/author/keyword")
                .param("aid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList.size()").value("0"))*/
        ;
    }

    @Test
    public void searchAuthorRelationSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/author/collaborate")
                .param("aid", "3000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.author.aid").value("3000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.author.aname").value("T. Pfitzer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration.size()").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[0].aname").value("C. Gladisch"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[1].aname").value("T. Heinz"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[2].aname").value("C. Heinzemann"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[3].aname").value("J. Oehlerking"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[4].aname").value("A. von Vietinghoff"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance.size()").value("5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[0]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[1]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[2]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[3]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[4]").value("1"))*/
        ;
    }

    @Test
    public void searchAuthorRelationError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/author/collaborate")
                .param("aid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Such Author"));*/
    }

    @Test
    public void searchPaperSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/author/paperList")
                .param("aid", "3000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].pid").value("1321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].doctitle").value("Experience Paper: Search-Based Testing in Automated Driving Control Applications"))*/
        ;
    }

    @Test
    public void searchPaperError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/author/paperList")
                .param("aid", "1")
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
    public void searchConferenceSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/author/conferenceList")
                .param("aid", "3000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].cname").value("2019 34th IEEE/ACM International Conference on Automated Software Engineering (ASE)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].cid").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList[0].paperCount").value("154"))*/
        ;
    }

    @Test
    public void searchConferenceError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/author/conferenceList")
                .param("aid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.conferenceVOList.size()").value("0"))
        ;
    }
}