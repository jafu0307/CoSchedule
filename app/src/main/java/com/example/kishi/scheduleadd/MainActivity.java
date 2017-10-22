package com.example.kishi.scheduleadd;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;



public class MainActivity extends ActionBarActivity {

    private DatePickerDialog.OnDateSetListener varDateSetListener;
    private TimePickerDialog.OnTimeSetListener varTimeSetListener;
    private EditText editText1, editText2, editText3, editText4;
    private Button done;
    private Context context;
    private CharSequence text1 ="有沒輪入的地方";

    int Year;
    int MonthOfYear;
    int DayOfMonth;
    int HourOfDay=30;
    int Minute;


    public static final String Firebase_Server_URL = "https://elderlyproject-46505.firebaseio.com/";
    Firebase firebase;
    DatabaseReference databaseReference;
    public static final String Database_Path = "Calendar";
    public String groupNum = "7309";
    public String dateHolder,timeHolder, titleHolder, contentHolder;

    private PendingIntent pendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(MainActivity.this);
        firebase = new Firebase(Firebase_Server_URL);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        //SOL1
        Intent intent = new Intent(this, CalendarIntentService.class);
        startService(intent);
        /*Intent intent = new Intent(this, CalendarService.class);
        startService(intent);*/

        //SOL2
        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(MainActivity.this, CalendarAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        //Init
        editText1 = (EditText)findViewById(R.id.editText01);
        editText2 = (EditText)findViewById(R.id.editText02);
        editText3 = (EditText)findViewById(R.id.editText03);//Title
        editText4 = (EditText)findViewById(R.id.editText04);//Content
        context = getApplicationContext();
        done = (Button) findViewById(R.id.button5);

        varDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view , int year , int monthOfYear , int dayOfMonth){

                editText1.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                Year=year;
                MonthOfYear=monthOfYear;
                DayOfMonth=dayOfMonth;

            }
        };

        varTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(
                    TimePicker view , int hourOfDay , int minute
            ){
                editText2.setText(hourOfDay + "點" + minute+"分");
                HourOfDay=hourOfDay;
                Minute=minute;
            }
        };

        ((Button)findViewById(R.id.button1))
                .setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        Calendar calendar = Calendar.getInstance();
                        DatePickerDialog dateDialog = new DatePickerDialog(
                                MainActivity.this,
                                varDateSetListener,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        );
                        dateDialog.show();
                    }
                });
        ((Button)findViewById(R.id.button2))
                .setOnClickListener(new View.OnClickListener(){
                    public void onClick(View view){
                        Calendar calendar = Calendar.getInstance();
                        TimePickerDialog timeDialog = new TimePickerDialog(
                                MainActivity.this,
                                varTimeSetListener,
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                false
                        );
                        timeDialog.show();
                    }
                });


        done.setOnClickListener(new View.OnClickListener() {

            // Click done Button  必須要打曰期時間title,內容不一定打
            @Override
            public void onClick(View view) {
                final TextView Text1=(TextView)findViewById(R.id.textView);

                String Title = editText3.getText().toString();
                String Content = editText4.getText().toString();


              if(Year==0)
                  Toast.makeText(context, text1+"1", Toast.LENGTH_SHORT).show();

              else if(HourOfDay==30)
                  Toast.makeText(context, text1+"2", Toast.LENGTH_SHORT).show();

              else  if(Title.toString().equals("") != false)
                  Toast.makeText(context, Title+"3", Toast.LENGTH_SHORT).show();

              else  if(Year==0&&HourOfDay==30)
                  Toast.makeText(context, text1+"4", Toast.LENGTH_SHORT).show();

              else  if(Year==0&&Title.toString().equals("") == false)
                  Toast.makeText(context, text1+"5", Toast.LENGTH_SHORT).show();

              else  if(HourOfDay==30&&Title.toString().equals("") == false)
                  Toast.makeText(context, text1+"6", Toast.LENGTH_SHORT).show();

              else  if(HourOfDay==30&&Title==null&&Year==0&&Title.toString().equals("") == false)
                  Toast.makeText(context, text1+"7", Toast.LENGTH_SHORT).show();

             else {
                  Toast.makeText(context, "完了", Toast.LENGTH_SHORT).show();

                  CalendarDetails calendarDetails = new CalendarDetails();
                  GetDataFromEditText();

                  calendarDetails.setDate(dateHolder);
                  calendarDetails.setTime(timeHolder);
                  calendarDetails.setTitle(titleHolder);
                  calendarDetails.setContent(contentHolder);

                  String CalendarEventID = databaseReference.push().getKey();
                  calendarDetails.seteventId(CalendarEventID);

                  databaseReference.child("calendar"+groupNum).child(CalendarEventID).setValue(calendarDetails);
                  /*實際用在famifilm則是： 當group產生，calendar_ID內外同時產生，再加入各個eventId和其他內值*/

                  Toast.makeText(MainActivity.this,"Data Inserted Successfully into Firebase Database", Toast.LENGTH_LONG).show();

              }
            }
        });
    }

    public void GetDataFromEditText(){

        dateHolder = editText1.getText().toString().trim();
        timeHolder = editText2.getText().toString().trim();
        titleHolder = editText3.getText().toString().trim();
        contentHolder = editText4.getText().toString().trim();
    }
}

