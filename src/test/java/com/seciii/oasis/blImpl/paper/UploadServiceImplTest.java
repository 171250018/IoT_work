package com.seciii.oasis.blImpl.paper;

import com.seciii.oasis.bl.paper.UploadService;
import com.seciii.oasis.data.paper.PaperMapper;
import com.seciii.oasis.po.Paper;
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
public class UploadServiceImplTest {
    @Resource
    UploadService uploadService;
    @Resource
    PaperMapper paperMapper;

    @Test
    public void uploadTest(){}

    /*
    @Test
    public void uploadCsv() {
        uploadService.uploadCsv("test/java/com/seciii/oasis/test.csv");
        List<Paper> x=paperMapper.selectPaperByTitle("angry",0,10);
        assertEquals(1,x.size());
        assertEquals("angry angry angry",x.get(0).getDoctitle());

        x=paperMapper.selectPaperByTitle("happy",0,10);
        assertEquals(1,x.size());
        assertEquals("happy",x.get(0).getDoctitle());
    }

     */
}