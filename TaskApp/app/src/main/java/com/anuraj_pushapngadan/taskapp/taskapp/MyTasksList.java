package com.anuraj_pushapngadan.taskapp.taskapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.anuraj_pushapngadan.taskapp.DB.DBHelper;
import com.anuraj_pushapngadan.taskapp.models.Task;

import java.util.List;

public class MyTasksList extends AppCompatActivity {

    ListView listView;
    private DBHelper TaskDb = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tasks_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.MyListView);
        /*
        Creating new TaskAdapter object
         */

        TaskAdapter adapter = new TaskAdapter(this,  TaskDb.getAllTasks());

        listView.setAdapter(adapter); //Set custom adapter for ListView



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
            case R.id.action_settings : startActivity (new Intent(this, Settings.class));
                break;
            case R.id.action_home : startActivity (new Intent(this, AddTask.class));
                break;
            // If the clicked menu item isRest, deleteAllTasks() method will be called in DBHelper class.
            case R.id.action_reset_db :
                TaskDb.deleteAllTasks();
                //refreshing the task list
                TaskAdapter adapter = new TaskAdapter(this,  TaskDb.getAllTasks());
                listView.setAdapter(adapter); //Set custom adapter for ListView
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


class TaskAdapter extends ArrayAdapter<Task>
{
    private Context context;
    public List<Task> Mytasks;

    public TaskAdapter(Context context, List<Task> Mytasks) //constructor
    {
        super(context, R.layout.row_mytasks_layout, Mytasks); //calling the parent class constructor
        this.context   = context;
        this.Mytasks = Mytasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_mytasks_layout, parent, false);

        Task task   = Mytasks.get(position);  //get items (Task objects) from Mytasks list and assign it to task variable of type Task

        TextView TaskTitleAndLocation = (TextView) view.findViewById(R.id.row_title);
        TextView TaskDate = (TextView) view.findViewById(R.id.row_start_and_end_date);

        TaskTitleAndLocation.setText(task.title+", "+task.location); // Set title and location to the row_title TextView
        TaskDate.setText(task.start +" - "+task.end); // Set start and end date to the row_start_and_end_date TextView

        return view;
    }

    @Override
    public int getCount()
    {
        return Mytasks.size();
    }
}
