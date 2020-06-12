package com.seciii.oasis.blImpl.statistics;

import com.seciii.oasis.bl.statistics.StatisticsService;
import com.seciii.oasis.po.Institution;
import com.seciii.oasis.vo.InstitutionRank;
import com.seciii.oasis.vo.PaperRefRank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StatisticsServiceImplTest {
    @Resource
    private StatisticsService statisticsService;

    @Test
    public void getInstitutionsPapersSuccess() {
        InstitutionRank x= (InstitutionRank) statisticsService.getInstitutionsPapers().getContent();
        ArrayList<Institution> li1=x.getInstitutionList();
        ArrayList<Integer> li2=x.getCountList();
        assertEquals(10,li1.size());
        assertEquals(10,li2.size());

        /*assertEquals(2223,li1.get(1).getIid());
        assertEquals(2353,li1.get(0).getIid());
        assertEquals(1435,li1.get(2).getIid());
        assertEquals(1665,li1.get(3).getIid());
        assertEquals(1443,li1.get(4).getIid());
        assertEquals(1901,li1.get(5).getIid());
        assertEquals(2014,li1.get(6).getIid());
        assertEquals(2310,li1.get(7).getIid());
        assertEquals(1672,li1.get(8).getIid());
        assertEquals(1489,li1.get(9).getIid());

        assertEquals(15,li2.get(1).intValue());
        assertEquals(15,li2.get(0).intValue());
        assertEquals(12,li2.get(2).intValue());
        assertEquals(10,li2.get(3).intValue());
        assertEquals(10,li2.get(4).intValue());
        assertEquals(10,li2.get(5).intValue());
        assertEquals(9,li2.get(6).intValue());
        assertEquals(9,li2.get(7).intValue());
        assertEquals(9,li2.get(8).intValue());
        assertEquals(8,li2.get(9).intValue());*/
    }

    @Test
    public void getMostRefPapers() {
        PaperRefRank p=(PaperRefRank)statisticsService.getMostRefPapers().getContent();
        ArrayList<Integer> li1=p.getPidlist();
        ArrayList<String> li2=p.getTitlelist();
        ArrayList<Integer> li3=p.getReflist();
        assertEquals(10, li1.size());
        assertEquals(10, li2.size());
        assertEquals(10, li3.size());

        assertEquals(2257,li1.get(0).intValue());
        assertEquals(1454,li1.get(1).intValue());
        assertEquals(2204,li1.get(2).intValue());
        assertEquals(1611,li1.get(3).intValue());
        assertEquals(1473,li1.get(4).intValue());
        assertEquals(1449,li1.get(5).intValue());
        assertEquals(2214,li1.get(6).intValue());
        assertEquals(2197,li1.get(7).intValue());
        assertEquals(2264,li1.get(8).intValue());
        assertEquals(2380,li1.get(9).intValue());

        assertEquals(187,li3.get(0).intValue());
        assertEquals(119,li3.get(1).intValue());
        assertEquals(96,li3.get(2).intValue());
        assertEquals(95,li3.get(3).intValue());
        assertEquals(84,li3.get(4).intValue());
        assertEquals(83,li3.get(5).intValue());
        assertEquals(79,li3.get(6).intValue());
        assertEquals(73,li3.get(7).intValue());
        assertEquals(65,li3.get(8).intValue());
        assertEquals(63,li3.get(9).intValue());
    }
}