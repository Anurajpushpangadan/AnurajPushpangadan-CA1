package com.anuraj_pushapngadan.taskapp.taskapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.anuraj_pushapngadan.taskapp.DB.DBHelper;

import java.util.Calendar;


public class AddTask extends AppCompatActivity {


    String date_time = "";
    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;

    Button StartDateButton, EndDateButton, SaveButton, CancelButton;
    TextView StartDateTxt, EndDateTxt, TotalTasks, UserNameField;
    EditText TitleTextField, LocationTextField, NoteFiled;
    String Title,Location,StartDate,EndDate;

    /*
    Creating an object of DBHelper Class
     */
    private DBHelper TaskDb = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UserNameField = (TextView) findViewById(R.id.UserNameTxt);
        UserNameField.setText("Logged in as: "+Login.UserName);

        StartDateButton = (Button) findViewById(R.id.StartBtn);
        EndDateButton = (Button) findViewById(R.id.EndBtn);
        SaveButton = (Button) findViewById(R.id.SaveBtn);
        CancelButton = (Button) findViewById(R.id.CancelBtn);

        TitleTextField = (EditText) findViewById(R.id.TitleTxt);
        LocationTextField = (EditText) findViewById(R.id.LocationTxt);
        NoteFiled = (EditText) findViewById(R.id.NoteTxt);

        StartDateTxt = (TextView) findViewById(R.id.StartDateTxtView);
        EndDateTxt = (TextView) findViewById(R.id.EndDateTxtView);
        TotalTasks = (TextView) findViewById(R.id.TotalTasksEditText);

        SetNoOfTotalTasks(); // updating the no of tasks added

        //Button Click events
        StartDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //datePicker method will call when the StartDateButton is clicked
                datePicker(StartDateTxt);
            }
        });

        EndDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePicker(EndDateTxt);

            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Title = TitleTextField.getText().toString();
                Location = LocationTextField.getText().toString();
                StartDate = StartDateTxt.getText().toString();
                EndDate = EndDateTxt.getText().toString();

                // Validating the strings. If the strings are not empty Validate method will return true.
                    if(Validate(Title, Location, StartDate, EndDate)) {
                        TaskDb.insertTask(Title,Location,StartDate,EndDate); //Calling insertTask method in DBHelper class
                        AddNewTaskMessage();
                             ClearFields(); // clear fields after adding new item to th e array list
                             SetNoOfTotalTasks(); // updating the no of tasks added
                    }

            }
        });

        CancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ClearFields();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

/*
Using switch case to identify which menu item is clicked
 */
        switch (item.getItemId())
        {
            // If the clicked menu item is Settings, Settings window is displayed.
            case R.id.action_settings : startActivity (new Intent(this, Settings.class));
                break;
            // If the clicked menu item is My Tasks List, MyTaskList window is displayed.
            case R.id.action_my_tasks : startActivity (new Intent(this, MyTasksList.class));
                break;
            // If the clicked menu item isRest, deleteAllTasks() method will be called in DBHelper class.
            case R.id.action_reset_db :
                TaskDb.deleteAllTasks();
                SetNoOfTotalTasks(); // updating the no of tasks added
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

//This will disable the Reset menu button if there are no tasks in the database
        if(TaskDb.getTotalNumberOfTasks()>0) {
            menu.getItem(3).setEnabled(true);
        }else{
            menu.getItem(3).setEnabled(false);
        }
        return true;
    }


    private void datePicker(TextView t){
/*
This method will Launch Date Picker Dialog.
Selected date will be assign to the date_time variable
 */

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final TextView txt = t;
        // Launch Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        //Call Time Picker Here
                        timePicker(txt);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(TextView t){
        /*
This method will Launch Time Picker Dialog.
Selected time and the date from the datePicker will concatenate and update the TextView Text.
 */

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        final TextView txt = t;
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;

                        txt.setText(date_time+" "+hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void AddNewTaskMessage(){
            Toast.makeText(this, "New task added!", Toast.LENGTH_SHORT).show();

    }

    private void SetNoOfTotalTasks(){

        //getTotalNumberOfTasks() in db helper class is calling. It will return the total number of rows in table
        TotalTasks.setText("Total tasks: "+TaskDb.getTotalNumberOfTasks());
    }

    private boolean Validate(String  title, String  location, String  start, String  end){
        /*
        Checking whether the strings are empty.
        If it is empty this method will return false with a Toast message.
        If not empty this will return true.
         */
        if(title.equals("")){
            Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(location.equals("")){
            Toast.makeText(this, "Location cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(start.equals("")){
            Toast.makeText(this, "Start date and time cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(end.equals("")){
            Toast.makeText(this, "End date and time cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //This method will set text fields to empty
    private void ClearFields(){
        TitleTextField.setText(null);
        LocationTextField.setText(null);
        StartDateTxt.setText(null);
        EndDateTxt.setText(null);
        NoteFiled.setText(null);
    }

}
