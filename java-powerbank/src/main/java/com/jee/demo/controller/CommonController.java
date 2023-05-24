package com.jee.demo.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.jee.demo.domain.Result;
import com.jee.demo.utils.ResultUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/img")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"})
public class CommonController {

    /**
     * 文件下载
     * @param session HttpSession对象
     * @param filename 文件名
     * @return 文件下载的响应实体
     * @throws IOException 发生IO异常时抛出
     */
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> fileDown(HttpSession session, @PathVariable String filename) throws IOException {
        ServletContext servletContext = session.getServletContext();
        String realPath = servletContext.getRealPath("/static/img/" + filename);
        InputStream is = new FileInputStream(realPath);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + filename);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        is.close();
        return responseEntity;
    }

    /**
     * 文件上传
     * @param photo MultipartFile对象，代表上传的文件
     * @param session HttpSession对象
     * @return 文件上传的结果
     * @throws IOException 发生IO异常时抛出
     */
    @PostMapping()
    public Result testUp(MultipartFile photo, HttpSession session) throws IOException {
        // 获取文件的原始名字
        String filename = photo.getOriginalFilename();
        String hzName = filename.substring(filename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        filename = uuid + hzName;

        ServletContext servletContext = session.getServletContext();
        String filePath = servletContext.getRealPath("/static/img");
        String finalPath = filePath + File.separator + filename;
        File file = new File(finalPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        photo.transferTo(new File(finalPath));
        return ResultUtil.success("上传图片成功", filename);
    }

}
