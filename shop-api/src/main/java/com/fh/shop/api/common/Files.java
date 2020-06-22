package com.fh.shop.api.common;

import com.fh.shop.api.util.OssUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("files")
@RestController
@CrossOrigin("*")
public class Files {

    @DeleteMapping("deleteFiles")
    public ServerResponse deleteFiles(String img, HttpServletRequest request) {
       /* String realPath = request.getServletContext().getRealPath(img);
        File file = new File(realPath);
        if (file.exists()) {
            file.delete();
        }*/
        OssUtil.deletes(img);
        return ServerResponse.success();
    }

    @RequestMapping("/uploadImage")
    @ResponseBody()
    public ServerResponse uploadImage(MultipartFile img, HttpServletRequest request) {

        try {
            InputStream is = img.getInputStream();
            String filename = img.getOriginalFilename();
           /* String realPath = request.getServletContext().getRealPath(SystemConstant.UPLOAD_IMG_PATH);
            String uploadFilename = FileUtil.copyFile(is,filename,realPath);
            return ServerResponse.success(SystemConstant.UPLOAD_IMG_PATH+uploadFilename);*/
            String upload = OssUtil.upload(is, filename);
            return ServerResponse.success(upload);
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }

}

