package admin.controller;

import admin.biz.speac.SpeacService;
import admin.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("speac")
public class SpeacControler {
    @Autowired
    private SpeacService speacService;

@RequestMapping("findSpeac")
@ResponseBody
    public ServerResponse findSpeac(){
        return speacService.findSpeac();
    }
}
