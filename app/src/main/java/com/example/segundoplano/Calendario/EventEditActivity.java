package com.example.segundoplano.Calendario;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.segundoplano.R;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET,eventTimeHour;
    private TextView eventDateTV, eventTimeTV;

    private LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Fecha: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        eventTimeHour = findViewById(R.id.hora);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveEventAction(View view)
    {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:MM");
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate,LocalTime.parse(eventTimeHour.getText().toString()));
        Event.eventsList.add(newEvent);
        finish();
    }
}