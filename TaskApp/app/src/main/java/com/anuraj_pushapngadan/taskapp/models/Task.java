package com.anuraj_pushapngadan.taskapp.models;


public class Task {
    public String  title, location, start, end;
/*
object of this class will holds all the information about a task
 */
    public Task(String  title, String  location, String  start, String  end) // constructor
    {
        this.title = title;
        this.location = location;
        this.start = start;
        this.end = end;
    }

}
