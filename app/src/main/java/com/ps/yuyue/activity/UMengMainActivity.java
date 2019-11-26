package com.ps.yuyue.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lc.framework.core.activity.CommonAbsActivity;
import com.ps.yuyue.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.OnClick;

import static com.ps.yuyue.AppRouterContants.APP_UMENG_MAIN;

/**
 * 类名：com.ps.yuyue.activity
 * 描述：友盟统计主界面
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/11/25 19:06
 */
@Route(path = APP_UMENG_MAIN)
public class UMengMainActivity extends CommonAbsActivity {
    @Override
    public int layoutId() {
        return R.layout.acty_umeng_main;
    }

    @Override
    protected String getTitleName() {
        return "友盟统计";
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {
        MobclickAgent.onProfileSignIn("19974915532");
    }

    @OnClick({R.id.comm_event, R.id.custom_event})
    void onEventClick(View view) {
        switch (view.getId()) {
            case R.id.comm_event:
                /*Map<String, Object> music = new HashMap<String, Object>();
                music.put("music_type", "popular");//自定义参数：音乐类型，值：流行
                music.put("singer", "JJ"); //歌手：(林俊杰)JJ
                music.put("song_name","A_Thousand_Years_Later"); //歌名：一千年以后
                music.put("song_price",100); //价格：100元
                MobclickAgent.onEventObject(this, "play_music", music);*/
                MobclickAgent.onEvent(this, "click", "button");
                break;
            case R.id.custom_event:
                MobclickAgent.onEvent(this, "custom_click", "custom_click");
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MobclickAgent.onProfileSignOff();
    }
}
