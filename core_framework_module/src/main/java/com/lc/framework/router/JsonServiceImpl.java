package com.lc.framework.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.ps.lc.utils.json.GsonUtil;

import java.lang.reflect.Type;

/**
 * 类名：JsonServiceImpl
 * 描述：Arouter的方法中 with(Object obj)  需要用到这个json转换
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/14 17:29
 */
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        return GsonUtil.toObject(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return GsonUtil.toJsonString(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return GsonUtil.toCommonObject(input, clazz);
    }
}
