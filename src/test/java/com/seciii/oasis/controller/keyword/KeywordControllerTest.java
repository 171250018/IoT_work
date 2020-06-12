package com.seciii.oasis.controller.keyword;

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
public class KeywordControllerTest {
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
    public void searchKeywordInfoSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/keyword/keywordInfo")
                .param("kid", "2627")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.kid").value("2627"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.kname").value("deep learning"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperCount").value("21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.activity").value(21))
        ;
    }

    @Test
    public void searchKeywordInfoError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/keyword/keywordInfo")
                .param("kid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Keyword Not Found"));
    }

    @Test
    public void searchKeywordRelationSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/keyword/relation")
                .param("kid", "2627")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keyword.kid").value("2627"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keyword.kname").value("deep learning"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keyword.paperCount").value("21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keyword.activity").value(21))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration.size()").value("59"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[0].kname").value("comment generation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[10].kname").value("Mutation Testing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[20].kname").value("Code retrieval"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[30].kname").value("code clone detection"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[40].kname").value("Recurrent Neural Network"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[50].kname").value("defect prediction"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[58].kname").value("joint embedding"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance.size()").value("59"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[0]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[10]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[20]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[30]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[40]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[50]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[58]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[5]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[16]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[31]").value("1"))
        ;
    }

    @Test
    public void searchKeywordRelationError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/keyword/relation")
                .param("kid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Keyword Not Exist"));
    }

    @Test
    public void searchPaperSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/keyword/paperList")
                .param("kid", "2627")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList.size()").value("21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].pid").value("1229"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].cid").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].aidList.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].iidList.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].kidList.size()").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].doctitle").value("Retrieve and Refine: Exemplar-Based Neural Comment Generation"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[10].doctitle").value("Combining Deep Learning with Information Retrieval to Localize Buggy Files for Bug Reports (N)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[20].doctitle").value("Deep Code Search"))*/
        ;
    }

    @Test
    public void searchPaperError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/keyword/paperList")
                .param("kid", "1")
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
        mvc.perform(MockMvcRequestBuilders.get("/keyword/authorList")
                .param("kid", "2627")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList.size()").value("82"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aid").value("2746"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aname").value("B. Wei"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].activity").value(10.5000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCount").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCited").value("21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[0]").value("Peking University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[10].aname").value("Y. Liu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[81].aname").value("S. Kim"))*/
        ;
    }

    @Test
    public void searchAuthorError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/keyword/authorList")
                .param("kid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList.size()").value("0"))
        ;
    }

    @Test
    public void searchInstitutionSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/keyword/institutionList")
                .param("kid", "2627")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList.size()").value("35"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iid").value("1435"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iname").value("Peking University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].activity").value(12.0000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].paperCount").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].papercited").value("568"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[10].iname").value("Microsoft Research"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[20].iname").value("Technion, Israel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[30].iname").value("Res. Sch. of Comput. Sciecne, Australian Nat. Univ., Canberra, ACT, Australia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[34].iname").value("Univ. of Newcastle, Callaghan, NSW, Australia"))*/
        ;
    }

    @Test
    public void searchInstitutionError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/keyword/institutionList")
                .param("kid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList.size()").value("0"))
        ;
    }
}