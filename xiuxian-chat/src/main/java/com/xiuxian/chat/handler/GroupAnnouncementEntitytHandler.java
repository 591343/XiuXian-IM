package com.xiuxian.chat.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class GroupAnnouncementEntitytHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.fillStrategy(metaObject, "publishmentTime", new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "publishmentTime", new Date());
    }
}
