package com.fh.biz;

import com.fh.common.ServerResponse;
import com.fh.po.Area;

public interface AreaService {
    ServerResponse findArea();

    ServerResponse add(Area area);

    ServerResponse delete(Long id);

    ServerResponse getId(Long id);

    ServerResponse update(Area area);

    ServerResponse batchDelete(String ids);

}
