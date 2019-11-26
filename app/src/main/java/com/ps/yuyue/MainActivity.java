package com.ps.yuyue;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.lc.framework.core.activity.CommonAbsActivity;
import com.lc.framework.router.share.ShareIntentManager;
import com.ps.lc.utils.widgets.titlebar.TitleBarType;
import com.ps.yuyue.utils.FastJsonUtil;
import com.ps.yuyue.utils.ForbidDeliverPhoneList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends CommonAbsActivity {

    @BindView(R.id.test_show)
    Button mTestShow;
    @BindView(R.id.fragment_show)
    Button mFragmentShow;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTitleName() {
        return "主界面";
    }

    /**
     * 标题栏
     */
    @Override
    public void initTitleView() {
        mTitleBarManager.type(TitleBarType.RIGHT_STRING).apply();
    }

    @Override
    protected void initStatusBar() {
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true, 0.2f)
                .barColor(R.color.transparent)
                // 解决软键盘与底部输入框冲突问题
                .keyboardEnable(true)
                .titleBar(mTitleBarManager.getTitleBar()).init();
    }

    @Override
    protected void initView(Bundle savedInstanceState, LinearLayout containerLay) {

    }

    private String result;

    @OnClick({R.id.test_show, R.id.fragment_show, R.id.fragment_tab_show, R.id.fastjson, R.id.umeng_event})
    void onActionEvent(View view) {
        switch (view.getId()) {
            case R.id.test_show:
                /*try {
                    result= compress(data);
                    ToastUtil.showToast(mActivity, result);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                ShareIntentManager.intentToMainActivity();
                break;
            case R.id.fragment_show:
                /*try {
                    String rr = uncompress(result.getBytes(StandardCharsets.ISO_8859_1));
                    try {
                        JSONObject json = new JSONObject(rr);
                        if(json.isNull("phoneList")){
                           return;
                        }
                        JSONArray array = json.getJSONArray("phoneList");
                        List<String> string = FastJsonUtil.toJSONOList(array.toString(), String.class);
                        ForbidDeliverPhoneList bean = new ForbidDeliverPhoneList();
                        String shareStr = FastJsonUtil.toJSONString(bean);
                        bean.setPhoneList(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ForbidDeliverPhoneList bean = (ForbidDeliverPhoneList) FastJsonUtil.toJSONObject(result, ForbidDeliverPhoneList.class);
                    ToastUtil.showToast(mActivity, rr);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                AppIntentManager.intentToFragment1Activity();
                break;
            case R.id.fragment_tab_show:
                AppIntentManager.intentToFragment2Activity();
                break;
            case R.id.fastjson:
                ForbidDeliverPhoneList bean = (ForbidDeliverPhoneList) FastJsonUtil.toJSONObject(data, ForbidDeliverPhoneList.class);
                bean.getPhoneList();
                break;
            case R.id.umeng_event:
                AppIntentManager.intentToUMengMainActivity();
                break;
            default:
        }
    }

    private String data = "{\"phoneList\":null}";

    @SuppressLint("NewApi")
    public static String compress(String str) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(str.getBytes(StandardCharsets.UTF_8));
            }
//            return out.toByteArray();
            return out.toString(String.valueOf(StandardCharsets.ISO_8859_1));
            // Some single byte encoding
        }
    }

    @SuppressLint("NewApi")
    public static String uncompress(byte[] str) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str))) {
            int b;
            while ((b = gis.read()) != -1) {
                baos.write((byte) b);
            }
        }
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }
}
