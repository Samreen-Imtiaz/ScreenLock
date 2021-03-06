/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package test.screenlocker.com.myapplication.PatternLock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.screenlocker.com.myapplication.R;
import test.screenlocker.com.myapplication.utils.PatternLockUtils;
import test.screenlocker.com.myapplication.utils.ToastUtils;


public class ResetPatternActivity extends ThemedAppCompatActivity {


    Button mOkButton;
    Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reset_pattern_activity);
        mOkButton = (Button) findViewById(R.id.ok_button);
        mCancelButton = (Button) findViewById(R.id.cancel_button);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatternLockUtils.clearPattern(ResetPatternActivity.this);
                ToastUtils.show(R.string.pattern_reset, ResetPatternActivity.this);
                finish();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
