package com.seciii.oasis.controller.paper;

import com.seciii.oasis.bl.paper.UploadService;
import com.seciii.oasis.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadcsv")
    @ResponseBody
    public ResponseVO upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String saveFileName = file.getOriginalFilename();
            File saveFile = new File(request.getSession().getServletContext().getRealPath("/uploadcsv/") + saveFileName);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return uploadService.uploadCsv(request.getSession().getServletContext().getRealPath("/uploadcsv/") + saveFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ResponseVO.buildFailure("上传失败");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseVO.buildFailure("上传失败");
            }
        } else {
            return ResponseVO.buildFailure("文件为空");
        }
    }

    @RequestMapping("/testupload")
    public ResponseVO testupload(){
        return uploadService.uploadCsv("src/main/resources/csv/icse15_16_17_18_19.csv");
    }


}
