package com.jack.myexperience.ui.activity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jack.myexperience.MyApplication;
import com.jack.myexperience.R;
import com.jack.myexperience.ui.adpater.FunctionAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements View.OnClickListener{
    @Bind(R.id.rv_function)
    RecyclerView mFunctionList;
    @Bind(R.id.btn_vertical)
    Button mVertical;
    @Bind(R.id.btn_horizontal)
    Button mHorizontal;
    @Bind(R.id.btn_grid)
    Button mGrid;
    FunctionAdapter mFunctionAdapter;
    RecyclerView.LayoutManager layoutManager;
    {
        Toast.makeText(MyApplication.getInstance(),"匿名代码块",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
    private void init() {
        mFunctionAdapter = new FunctionAdapter();
        String[] function = getResources().getStringArray(R.array.function_list);
        List<String> data = Arrays.asList(function);
        layoutManager = new LinearLayoutManager(this);
        mFunctionList.setLayoutManager(layoutManager);
        mFunctionAdapter.setData(data);
        mFunctionList.setAdapter(mFunctionAdapter);
        mVertical.setOnClickListener(this);
        mHorizontal.setOnClickListener(this);
        mGrid.setOnClickListener(this);
        mFunctionAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                v.setBackgroundColor(Color.parseColor("#CCCCFF"));
                selectPosition(position,v);
            }
        });
    }

    private void selectPosition(int position,View v) {
        switch (position){
            case 0:
                forward(TransparentActivity.class);
                break;
            case 1:
                forward(DependencyInjectActivity.class);
                break;
            case 2:
                forward(RxAndroidActivity.class);
                break;
            case 3:
                forward(MVPActivity.class);
                break;
            case 4:
                forward(AnnotationActivity.class);
                break;
            case 5:
                forward(ThreadPoolActivity.class);
                break;
            default:
                break;
        }
        v.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onClick(View v) {
        if(layoutManager!=null){
            switch (v.getId()){
                case R.id.btn_vertical:
                        layoutManager=new LinearLayoutManager(this);
                    ((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
                    mFunctionList.setLayoutManager(layoutManager);
                    break;
                case R.id.btn_horizontal:
                        layoutManager=new LinearLayoutManager(this);
                    ((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                    mFunctionList.setLayoutManager(layoutManager);
                    break;
                case R.id.btn_grid:
                    layoutManager=new GridLayoutManager(this,3);
                    mFunctionList.setLayoutManager(layoutManager);
                    break;
                default:
                    break;
            }
        }
    }
    public void forward(Class<? extends Activity> clz){
        if(null!=clz){
            Intent intent = new Intent(this,clz);
            startActivity(intent);
        }
    }
}
