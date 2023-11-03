package com.kewargs.cs309.activity.dashboard;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import org.json.*;

import com.kewargs.cs309.R;
import com.kewargs.cs309.activity.AbstractActivity;
import com.kewargs.cs309.activity.auth.LoginActivity;
import com.kewargs.cs309.activity.course.CourseListActivity;
import com.kewargs.cs309.core.models.in.UserDeserializable;
import com.kewargs.cs309.core.utils.backend.factory.UserRequestFactory;


public class AuditUploadAcitivity extends AbstractActivity {
    public AuditUploadAcitivity() {
        super(R.layout.activity_pdf_uploader);
    }

    Button backDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backDash.setOnClickListener(this::backDashCallback);
    }

    private void backDashCallback(View view) {switchToActivity(DashboardActivity.class);}

    private void switchToActivity(Class<?> newActivity) {
        Intent intent = new Intent(AuditUploadAcitivity.this, newActivity);
        startActivity(intent);
    }
    @Override
    protected void collectElements() {
        backDash =findViewById(R.id.backDashboard);
    }

}
