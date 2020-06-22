package admin.controller;

import admin.common.ServerResponse;
import admin.util.OSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@RequestMapping("file")
@Controller
public class FileController {
    @RequestMapping("uploadImg")
    @ResponseBody
    public ServerResponse uploadImg(MultipartFile image, HttpServletRequest request) {

        try {
            InputStream is = image.getInputStream();
            String filename = image.getOriginalFilename();
            /*String realPath = request.getServletContext().getRealPath(SystemConstant.UPLOAD_IMG_PATH);
            String s = FileUtil.copyFile(is, filename, realPath);
            return ServerResponse.success(SystemConstant.UPLOAD_IMG_PATH+s);
            */
            String upload = OSSUtil.upload(is, filename);
return ServerResponse.success(upload);

        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }
    @RequestMapping("upload")
    @ResponseBody
    public ServerResponse upload(@RequestParam MultipartFile[] image, HttpServletRequest request) {

        try {
            StringBuilder paths = new StringBuilder();
            for (MultipartFile i:image){
                InputStream is = i.getInputStream();
                String filename = i.getOriginalFilename();
                String upload = OSSUtil.upload(is, filename);
                paths.append(",").append(upload);
            }
           return ServerResponse.success(paths.length()>0?paths.substring(1):"");
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

}
