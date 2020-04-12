package admin.biz.speac;

import admin.common.ServerResponse;
import admin.mapper.ISpeacMapper;
import admin.po.Speac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeacServiceImpl implements SpeacService {
    @Autowired
    private ISpeacMapper speacMapper;

    @Override
    public ServerResponse findSpeac() {
        List<Speac> speacList= speacMapper.selectList(null);
        return ServerResponse.success(speacList);
    }
}
