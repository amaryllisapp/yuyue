package com.ps.yuyue.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alipay.sdk.app.PayTask;
import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.ps.yuyue.R;
import com.ps.yuyue.alipay.PayResult;
import com.ps.yuyue.sobot.SobotHelper;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名：com.ps.yuyue.fragment
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 17:02
 */
public class Fragment1 extends BaseAbsFragment {

    @BindView(R.id.sobot)
    Button mSobotView;

    private static final int SDK_PAY_FLAG = 1;

    public static Fragment1 create() {
        return new Fragment1();
    }

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState, ViewGroup containerLay) {

    }

    @OnClick({R.id.sobot, R.id.alipay})
    public void onEventBtn(View view) {
        switch (view.getId()) {
            case R.id.sobot:
                SobotHelper.startSobotChat(_mActivity, "");
                break;
            case R.id.alipay:
                openAliPay();
                break;
            default:
        }

    }

    private void openAliPay() {
        final String orderParam = "alipay_sdk=alipay-sdk-java-3.7.4.ALL&app_id=2019040463790145&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22ali01907171832187833252750227855%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fpre.xhg.com%2Fpay-notify%2Falipay%2Fnotify&sign=Bq2xB8wy6uXq%2F2o7vLSAfW7PWuYlSsiBoIDfIXkwRwWiNTdqSeCAn83bvIgXB7TJrRg3%2F%2FL919wcqj7SeJC1gcsrVpHI%2FUl2eF4fcRCOxng3E1lL9il7UjUV02vEjLRd6kGYTkz6JNLyLqGwN4NbOHdBYvweh%2BJh%2B%2FO6Z7RHMBMONPHtlHE5gLR2OxDMdRVpfcII%2B4hQ%2F0Prp36GQrGSh0RNmZuuEy6y9Np6ac%2BAcSLQnQaRseT%2BlHGHFopmItIXp9FwxZuzwGFsRNawfsd%2FfWQ5DMK%2FZhdbILLwKztDzpQ%2FzTWE4advIrUDddGwdyVPUCwp9o%2FP8Vd6yi7lhmLDUQ%3D%3D&sign_type=RSA2&timestamp=2019-07-17+18%3A32%3A18&version=1.0";
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderParam, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(getContext(), getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(getContext(), getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }


}
