package com.itfitness.opencvrehearse;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itfitness.opencvrehearse.base.BaseActivity;
import com.itfitness.opencvrehearse.beauty.BeautyActivity;
import com.itfitness.opencvrehearse.utils.RehearseUtil;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends BaseActivity {
    private RecyclerView mRv;
    private BaseQuickAdapter<String, BaseViewHolder> mBaseAdapter;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initJNIOpenCVLibrary();
    }

    /**
     * 加载OpenCV自带的JNI库
     */
    private void initJNIOpenCVLibrary() {
        if(OpenCVLoader.initDebug()){
            Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        mRv = findViewById(R.id.activity_main_rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mBaseAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_opencvrehearse, RehearseUtil.getRehearseList()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.item_opencvrehearse_tv,item);
            }
        };
        mBaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        //人像美颜
                        gotoActivity(BeautyActivity.class);
                        break;
                }
            }
        });
        mRv.setAdapter(mBaseAdapter);
    }
}
