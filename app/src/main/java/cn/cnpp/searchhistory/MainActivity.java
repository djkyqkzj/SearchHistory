package cn.cnpp.searchhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author dengyalan
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.auto_search)
    AutoCompleteTextView autoSearch;
    @BindView(R.id.keyword_fl)
    ZFlowLayout keywordFl;
    @BindView(R.id.history_fl)
    ZFlowLayout historyFl;

    public static String[] searchWord = {"净水器", "手机", "电动车", "洗衣机", "沙发", "冰箱", "瓷砖", "空调", "床垫", "卫浴", "热水器", "床", "家具", "手表", "电视", "集成灶", "领带", "保温杯", "童装", "自行车", "空气净化器", "地板", "硅藻泥", "油烟机", "智能家居"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initKeyword(searchWord);
        initHistory();
        String[] data = SPUtils.getInstance(this).getHistoryList();

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<String>(this,
                R.layout.view_mw_textview, data);
        autoSearch.setAdapter(autoCompleteAdapter);

        autoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
//                    toSearchTv.setText(getString(R.string.clear));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initHistory() {
        final String[] data = SPUtils.getInstance(this).getHistoryList();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        historyFl.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (isNullorEmpty(data[i])) {
                return;
            }
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            historyFl.addView(paramItemView, layoutParams);

            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    autoSearch.setText(data[j]);
                }
            });
        }
    }

    private void initKeyword(final String[] keyword) {
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        keywordFl.removeAllViews();
        for (int i = 0; i < keyword.length; i++) {
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(keyword[j]);
            keyWordTv.setBackgroundResource(R.drawable.whitebg_strokegrey_radius3);
            keywordFl.addView(paramItemView, layoutParams);

            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    autoSearch.setText(keyword[j]);
                }
            });
        }
    }

    @OnClick({R.id.iv_back, R.id.clear_iv, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String searchKey = autoSearch.getText().toString();
                if (!isNullorEmpty(searchKey)) {
                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra("key", searchKey);
                    startActivityForResult(intent, 0);
                    String keyWord = autoSearch.getText().toString();
                    if (!isNullorEmpty(keyWord)) {
                        SPUtils.getInstance(MainActivity.this).save(autoSearch.getText().toString());
                    }
                } else {
                    showToastShort(this, "搜索内容为空！");
                }
                break;
            case R.id.clear_iv:
                SPUtils.getInstance(MainActivity.this).cleanHistory();
                showToastShort(this, "已清除历史记录！");
                initHistory();
                break;
            default:
                break;
        }
    }

    private boolean isNullorEmpty(String str) {
        return str == null || "".equals(str);
    }

    private void showToastShort(Context context, String data) {
        Toast toast = Toast.makeText(context, data, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                initHistory();
            }
        }
    }
}
