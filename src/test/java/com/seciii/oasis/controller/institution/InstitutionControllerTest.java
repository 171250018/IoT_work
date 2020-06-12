package com.seciii.oasis.controller.institution;

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
public class InstitutionControllerTest {
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
        mvc.perform(MockMvcRequestBuilders.get("/institution/searchByName")
                .param("name", "Tsinghua University")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList.size()").value("4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iid").value("1434"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iname").value("Tsinghua University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].paperCount").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].papercited").value("38"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].activity").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[1].iname").value("School of Software, Tsinghua University, TNLIST, KLISS, Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[2].iname").value("School of Software, TNLIST, KLISS, Tsinghua University, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[3].iname").value("Tsinghua University, China"))*/
        ;
    }

    @Test
    public void searchByNameSuccess2() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/institution/searchByName")
                .param("name", "Tsinghua")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList.size()").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[0].iname").value("Tsinghua University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[1].iname").value("School of Software, Tsinghua University, TNLIST, KLISS, Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[2].iname").value("School of Software, TNLIST, KLISS, Tsinghua University, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[3].iname").value("Sch. of Software, Tsinghua Univ., Beijing, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[4].iname").value("Tsinghua University, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList[5].iname").value("Dept. of Ind. Eng., Tsinghua Univ., Beijing, China"))*/
        ;
    }

    @Test
    public void searchByNameError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/institution/searchByName")
                .param("name", "PIG")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionVOList.size()").value("0"))
        ;
    }

    @Test
    public void searchInstitutionInfoSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/institutionInfo")
                .param("iid", "1434")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.iid").value("1434"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.iname").value("Tsinghua University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperCount").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.papercited").value("38"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.activity").value(3))*/
        ;
    }

    @Test
    public void searchInstitutionInfoError1() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/institution/institutionInfo")
                .param("iid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Such Institution"));

    }

    @Test
    public void searchInstitutionInfoError2() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/institution/institutionInfo")
                .param("iid", "-1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Such Institution"));

    }

    @Test
    public void searchAuthorSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/authorList")
                .param("iid", "1434")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList.size()").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aid").value("2741"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].aname").value("Y. Wang"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].activity").value(127.5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCount").value("9"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].paperCited").value("461"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions.size()").value("9"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[0]").value("Tsinghua University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[1]").value("Nanjing University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[2]").value("Rochester Institute of Technology"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[3]").value("Alibaba Group"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[4]").value("Key Laboratory for Information System Security, Ministry of Education, China"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[5]").value("Ohio State Univ., Columbus, OH, USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[6]").value("University of California, Davis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[7]").value("Northeastern University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[0].institutions[8]").value("NA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[6].aname").value("Z. Gu"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.authorVOList[11].aname").value("Y. Jiang"))*/
        ;
    }

    @Test
    public void searchAuthorError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/institution/authorList")
                .param("iid", "1")
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
    public void searchPaperSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/paperList")
                .param("iid", "1434")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList.size()").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].pid").value("1228"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[0].doctitle").value("TsmartGP: A Tool for Finding Memory Defects with Pointer Analysis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[1].doctitle").value("Ares: Inferring Error Specifications through Static Analysis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.paperVOList[2].doctitle").value("VisFuzz: Understanding and Intervening Fuzzing with Interactive Visualization"))*/
        ;
    }

    @Test
    public void searchPaperError() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/institution/paperList")
                .param("iid", "1")
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
    public void searchKeywordSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/keyword")
                .param("iid", "1434")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList.size()").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].kid").value("2621"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].kname").value("pointer analysis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].activity").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[0].paperCount").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[1].kname").value("uninitialized pointer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[2].kname").value("sensitivity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[3].kname").value("multi-entry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[4].kname").value("Error Handling"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[5].kname").value("Error Specification"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[6].kname").value("Static Analysis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[7].kname").value("Fuzz Testing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[8].kname").value("software testing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList[9].kname").value("Visualization"))*/
        ;
    }

    @Test
    public void searchKeywordError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/keyword")
                .param("iid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.keywordVOList.size()").value("0"))
        ;
    }

    @Test
    public void searchCollaborateSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/collaborate")
                .param("iid", "1434")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institution.iid").value("1434"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institution.iname").value("Tsinghua University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration.size()").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[0].iname").value("Nanjing University of Aeronautics and Astronautics"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.collaboration[1].iname").value("Waterloo University"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance.size()").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[0]").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.relevance[1]").value("1"))*/
        ;
    }

    @Test
    public void searchCollaborateError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/institution/collaborate")
                .param("iid", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No Such Institution"));
    }
}