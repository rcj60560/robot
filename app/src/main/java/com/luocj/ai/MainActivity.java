package com.luocj.ai;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.luocj.ai.adapter.MultiItemAdapter;
import com.luocj.ai.model.LeftModel;
import com.luocj.ai.utils.KeyBoardUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private SmartRefreshLayout smartrefreshlayout;
    private RecyclerView recyclerview;
    private ArrayList<LeftModel> models;
    private int index = 0;
    private Button btnSend;
    private EditText et;
    private MultiItemAdapter adapter;

    private String info = "";
    private Activity mActivity;
    private LinearLayout mLl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;
        models = new ArrayList<>();

        initView();
    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        Log.i("tag", "最外层的高度" + root.getRootView().getHeight());
                        // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了
                        if (rootInvisibleHeight > 100) {
                            //软键盘弹出来的时候
                            int[] location = new int[2];
                            // 获取scrollToView在窗体的坐标
                            scrollToView.getLocationInWindow(location);
                            // 计算root滚动高度，使scrollToView在可见区域的底部
                            int srollHeight = (location[1] + scrollToView
                                    .getHeight()) - rect.bottom;
                            root.scrollTo(0, srollHeight);
                            recyclerview.scrollToPosition(models.size() - 1);
                        } else {
                            // 软键盘没有弹出来的时候
                            root.scrollTo(0, 0);
                        }
                    }
                });
    }

    private void getData(final String info) {
        OkGo.<String>get(URL.BASE_URL)
                .tag("tag")
                .params("key", "0708f7b458934929914def3150ea86dd")
                .params("info", info)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LeftModel leftModel = JSONObject.parseObject(response.body().toString(), LeftModel.class);
                        if (models.size() % 2 == 0) {
                            leftModel.setType(1);
                        } else {
                            leftModel.setType(0);
                        }
                        models.add(leftModel);
                        adapter.upDate(models);
                        Log.i(TAG, "onSuccess: " + models.get(index).getItemType());
                        index++;
                        recyclerview.scrollToPosition(models.size() - 1);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i(TAG, "onError: " + response.body());
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                        LeftModel preModel = new LeftModel();
                        preModel.setText(info);
                        if (models.size() % 2 == 0) {
                            preModel.setType(1);

                        } else {
                            preModel.setType(0);
                        }
                        models.add(preModel);
                        adapter.upDate(models);
                    }
                });
        controlKeyboardLayout(mLl_root, et);

    }

    private ArrayList<LeftModel> initDatas() {
        ArrayList<LeftModel> leftModels = new ArrayList<>();
        return leftModels;
    }

    private void initView() {
        smartrefreshlayout = findViewById(R.id.smartrefreshlayout);
        recyclerview = findViewById(R.id.recyclerview);
        mLl_root = findViewById(R.id.ll_root);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        adapter = new MultiItemAdapter(mContext, models);
        recyclerview.setAdapter(adapter);

//        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getData();
//            }
//        });
        btnSend = findViewById(R.id.btn_send);
        et = findViewById(R.id.et_content);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: " + s);
                info = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: " + s);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(info);
            }
        });

//        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                KeyBoardUtil.hideKeyboard(mActivity);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
    }
}
