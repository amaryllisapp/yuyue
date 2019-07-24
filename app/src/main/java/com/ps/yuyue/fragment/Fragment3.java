package com.ps.yuyue.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lc.framework.core.activity.fragment.BaseAbsFragment;
import com.ps.lc.utils.log.LogHelper;
import com.ps.lc.utils.permission.PermissionCallback;
import com.ps.lc.utils.permission.PermissionUtil;
import com.ps.yuyue.R;
import com.ps.yuyue.validapk.NetworkUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类名：com.ps.yuyue.fragment
 * 描述：
 *
 * @author liucheng - liucheng@xhg.com
 * @date 2019/6/24 17:02
 */
public class Fragment3 extends BaseAbsFragment {

    @BindView(R.id.read_file)
    Button mReadFileBtn;
    @BindView(R.id.write_file)
    Button mWriteFileBtn;
    @BindView(R.id.show)
    TextView mShowView;

    private File mFile;
    private String fileName;
    private String md5;

    public static Fragment3 create() {
        return new Fragment3();
    }

    @Override
    protected int layout() {
        return R.layout.fragment_3;
    }

    @Override
    protected void initView(Bundle savedInstanceState, ViewGroup containerLay) {

    }

    private void requestPermission(View view) {
        PermissionUtil.requestStoragePermission(_mActivity, new PermissionCallback() {
            /**
             * 有权限被授予时回调
             *
             * @param granted           请求成功的权限组
             * @param isAll             是否全部授予了
             */
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        switch (view.getId()) {
                            case R.id.read_file:
                                File file = new File("/sdcard/apk/");
                                if (file.isDirectory()) {
                                    File[] files = file.listFiles();
                                    if (file != null && file.length() > 0) {
                                        mFile = files[files.length - 1];
                                        md5 = NetworkUtils.getFileToAlgorithm(mFile, "md5");
                                        LogHelper.i("文件名：[" + mFile.getName() + "]的MD5值为=" + md5);
                                        mShowView.setText("包名Md5" + md5);
                                    }
                                } else {
                                    String md5 = NetworkUtils.getFileToAlgorithm(file, "md5");
                                    LogHelper.i("文件名：[" + file.getName() + "]的MD5值为=" + md5);
                                }

                                break;
                            case R.id.write_file:
                                String fileName = mFile.getName().substring(0, mFile.getName().lastIndexOf("."));
                                fileName = fileName+"$"+md5 + mFile.getName().substring(mFile.getName().lastIndexOf("."));
                                String filePath = mFile.getPath().substring(0, mFile.getPath().lastIndexOf("/"));
                                String newPath = filePath + "/" +fileName;
                                mFile.renameTo(new File(newPath));
                                mShowView.setText("文件目录及名称：["+mFile.getPath()+"]");
                                break;
                            default:
                        }
                    }
                });

            }

            /**
             * 有权限被拒绝授予时回调
             *
             * @param denied 请求失败的权限组
             * @param quick  是否有某个权限被永久拒绝了
             */
            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }

    @OnClick({R.id.read_file, R.id.write_file})
    void onEventClick(View view) {
        requestPermission(view);

    }
}
