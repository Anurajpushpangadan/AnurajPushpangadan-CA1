package com.anuraj_pushapngadan.taskapp.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.anuraj_pushapngadan.taskapp.DB.DBHelper;

public class Settings extends AppCompatActivity {

    private DBHelper TaskDb = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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


        switch (item.getItemId())
        {
            case R.id.action_home : startActivity (new Intent(this, AddTask.class));
                break;
            case R.id.action_my_tasks : startActivity (new Intent(this, MyTasksList.class));
                break;
            // If the clicked menu item isRest, deleteAllTasks() method will be called in DBHelper class.
            case R.id.action_reset_db :
                TaskDb.deleteAllTasks();
                if(TaskDb.getTotalNumberOfTasks()>0) {
                    item.setEnabled(true);
                }else{
                    item.setEnabled(false);
                }
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

}
