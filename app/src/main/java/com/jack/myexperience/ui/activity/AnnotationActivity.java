package com.jack.myexperience.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import com.jack.myexperience.R;
import com.jack.myexperience.annnotation.AutoBind;
import com.jack.myexperience.tools.AnnotationUtils;

/**
 * Created by Administrator on 2016/2/25 0025.
 */
@AutoBind(field ={R.id.tv_annotation_text_view,R.id.btn_annotation_button,R.id.ig_annotation_image_view})
public class AnnotationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annnotation);
        AnnotationUtils.bind(this);

    }
}
