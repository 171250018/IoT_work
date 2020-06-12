package com.seciii.oasis.controller.paper;

import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.Paper;
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
public class SearchControllerTest {
    @Resource
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Resource
    PaperMapper paperMapper;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();

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
    public void getSearchResult0Success() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "" + 0)
                .param("content","Happy?")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].doctitle").value("Why Happy?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[1].doctitle").value("Not Happy?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[2].doctitle").value("Why Not Happy?"));
    }

    @Test
    public void getSearchResult0Error() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "" + 0)
                .param("content","Bappy")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("没有找到符合条件的结果"));
    }

    @Test
    public void getSearchResult1Success() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "" + 1)
                .param("content","X. Chen")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist.size()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].pid").value("1230"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[1].pid").value("1235"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[2].pid").value("1323"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[3].pid").value("1342"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[4].pid").value("1548"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[5].pid").value("1567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[6].pid").value("1680"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[7].pid").value("1764"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[8].pid").value("1788"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[9].pid").value("1955"))*/
        ;
    }

    @Test
    public void getSearchResult1Error() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "1")
                .param("content","67")
                .param("page","1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("没有找到符合条件的结果"));
    }

    @Test
    public void getSearchResult2Success() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "" + 2)
                .param("content","Tsinghua")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist.size()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].pid").value("1228"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[1].pid").value("1237"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[2].pid").value("1352"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[3].pid").value("1525"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[4].pid").value("1548"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[5].pid").value("1751"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[6].pid").value("1776"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[7].pid").value("1795"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[8].pid").value("1808"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[9].pid").value("2351"));*/
                ;
    }

    @Test
    public void getSearchResult2Error() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "2")
                .param("content","NJU")
                .param("page","1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("没有找到符合条件的结果"));
    }

    @Test
    public void getSearchResult3Success() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "" + 3)
                .param("content","2013")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist.size()").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].pid").value("1225"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[9].pid").value("1406"));
    }

    @Test
    public void getSearchResult3Error() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "3")
                .param("content","13th")
                .param("page","1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("没有找到符合条件的结果"));
    }

    @Test
    public void getSearchResult4Success() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "" + 4)
                .param("content","Build Failures")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].pid").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[1].pid").value("1276"));*/
                ;
    }

    @Test
    public void getSearchResult4Error() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "4")
                .param("content","ABCD")
                .param("page","1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("没有找到符合条件的结果"));
    }

    @Test
    public void getSearchResultError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/search")
                .param("type", "7")
                .param("content","ABCD")
                .param("page","1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No such type"));
    }

    @Test
    public void getCombineSearchResultSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cbsearch")
                .param("title", "Happy")
                .param("startyear","207")
                .param("endyear","209")
                .param("doi","" )
                .param("pubtitle", "")
                .param("author","A;B" )
                .param("keyword","")
                .param("institution","")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].doctitle").value("Why Happy?"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[1].doctitle").value("Not Happy?"));
    }

    @Test
    public void getCombineSearchResultSuccess2() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cbsearch")
                .param("title", "Happy")
                .param("startyear","207")
                .param("endyear","209")
                .param("doi","" )
                .param("pubtitle", "")
                .param("author","A;B" )
                .param("keyword","mmm")
                .param("institution","bb")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist.size()").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.resultlist[0].doctitle").value("Not Happy?"));
    }

    @Test
    public void getCombineSearchResultError() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cbsearch")
                .param("title", "Happy")
                .param("startyear","207")
                .param("endyear","209")
                .param("doi","" )
                .param("pubtitle", "")
                .param("author","A;B" )
                .param("keyword","mmm")
                .param("institution","cc;dd")
                .param("page","" + 1)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("没有找到符合条件的结果"));
    }
}