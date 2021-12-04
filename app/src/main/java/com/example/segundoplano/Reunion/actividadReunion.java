package com.example.segundoplano.Reunion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.segundoplano.MainActivity;
import com.example.segundoplano.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetViewListener;
import org.jitsi.meet.sdk.log.JitsiMeetLogger;

import java.util.Map;

public class actividadReunion extends JitsiMeetActivity implements JitsiMeetViewListener, JitsiMeetActivityInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_reunion);


        JitsiMeetConferenceOptions options
                = new JitsiMeetConferenceOptions.Builder()
                .setRoom((String) getIntent().getExtras().get("codigo"))
                .build();
        JitsiMeetActivity.launch(this, options);
        actividadReunion.this.finish();

    }

    @Override
    public void onConferenceTerminated(Map<String, Object> data) {
        super.onConferenceTerminated(data);
        JitsiMeetLogger.i("Conference terminated: " + data);
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}