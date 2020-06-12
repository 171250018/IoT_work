package com.seciii.oasis.controller.statistics;

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
public class StatisticsControllerTest {
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
    public void getPaperRefRankSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/statistics/paperrefrank")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist.size()").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.titlelist.size()").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist.size()").value("10"))
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[0]").value("1949"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[1]").value("1740"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[2]").value("1876"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[3]").value("1841"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[4]").value("1925"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[5]").value("1806"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[6]").value("1859"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[7]").value("1886"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[8]").value("2355"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.pidlist[9]").value("1371"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[0]").value("127"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[1]").value("115"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[2]").value("105"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[3]").value("101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[4]").value("100"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[5]").value("95"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[6]").value("92"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[7]").value("92"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[8]").value("92"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.reflist[9]").value("89"));*/
                ;
    }


    @Test
    public void getInstitutionCountRank() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/statistics/institutionrank")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList.size()").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList.size()").value("10"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[0].iid").value("2223"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[1].iid").value("2353"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[2].iid").value("1435"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[3].iid").value("1901"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[4].iid").value("1665"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[5].iid").value("1443"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[6].iid").value("2014"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[7].iid").value("2310"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[8].iid").value("1672"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.institutionList[9].iid").value("1489"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[0]").value("15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[1]").value("15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[2]").value("12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[3]").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[4]").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[5]").value("10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[6]").value("9"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[7]").value("9"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[8]").value("9"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.countList[9]").value("8"))
                ;
    }
}