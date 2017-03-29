package com.rave.tipcoverdemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rave.tipcoverview.TipCoverHelper;

public class MainActivity extends Activity {
    private LinearLayout mIdContainer;
    private TextView mIdTop;
    private TextView mIdMiddle;
    private TextView mIdBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIdContainer = (LinearLayout) findViewById(R.id.id_container);
        mIdTop = (TextView) findViewById(R.id.id_top);
        mIdTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mIdTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                addHightView();
            }
        });
        mIdMiddle = (TextView) findViewById(R.id.id_middle);
        mIdBottom = (TextView) findViewById(R.id.id_bottom);
    }


    private void addHightView() {

        final TipCoverHelper highLight = new TipCoverHelper(this)
                .anchor(findViewById(R.id.id_container)) //绑定根布局，在Activity中可不写
                .setIntercept(true) // 查看注释和代码，可设置其他属性
                .setShadow(false)
                .setIsNeedBorder(false)
                .setRadius(600)
                .setMyBroderType(TipCoverHelper.MyType.DASH_LINE)
                .addTipCover(R.id.id_top, R.layout.user_tip_top, true, new TipCoverHelper.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, TipCoverHelper.MarginInfo marginInfo) {
                        marginInfo.rightMargin = 0;
                        marginInfo.topMargin = rectF.bottom - 100;
                    }
                })
                .addTipCover(R.id.id_middle, R.layout.user_tip_middle, false, new TipCoverHelper.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, TipCoverHelper.MarginInfo marginInfo) {
                        marginInfo.rightMargin = 0;
                        marginInfo.topMargin = rectF.bottom / 2;
                    }
                })
                .addTipCover(R.id.id_bottom, R.layout.user_tip_bottom, false, new TipCoverHelper.OnPosCallback() {
                    @Override
                    public void getPos(float rightMargin, float bottomMargin, RectF rectF, TipCoverHelper.MarginInfo marginInfo) {
                        marginInfo.rightMargin = 0;
                        marginInfo.bottomMargin = 10;
                    }
                });
//通知栏颜色
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.colorAccent);//通知栏所需颜色
//        }
        highLight.show();

        highLight.setOnClickCallback(new TipCoverHelper.OnClickCallback() {
            @Override
            public void onClick() {
                //通知栏颜色
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    setTranslucentStatus(true);
//                    SystemBarTintManager tintManager = new SystemBarTintManager(MyDemoActivity.this);
//                    tintManager.setStatusBarTintEnabled(true);
//                    tintManager.setStatusBarTintResource(R.color.colorAccent);//通知栏所需颜色
//
//                }
                highLight.remove();
            }
        });
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

