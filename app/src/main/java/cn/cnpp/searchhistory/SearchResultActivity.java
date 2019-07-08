package cn.cnpp.searchhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: SearchResultActivity
 *
 * @author: dengyalan
 * Date: 2018-05-05 09:33
 */
public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.auto_search)
    AutoCompleteTextView autoSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        ButterKnife.bind(this);

        if (getIntent() != null) {
            Intent in = getIntent();
            String keyword = in.getStringExtra("key");
            autoSearch.setText(keyword);
        }
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                setResult(RESULT_OK);
                finish();
                break;
            default:
                break;
        }
    }
}
