package com.example.anurag.conducive;

import android.graphics.drawable.Drawable;

/**
 * Created by root on 16/1/16.
 */
public class Action{
    public String actionName;
    public Drawable icon;
    public void setActionName(String actionName){
        this.actionName=actionName;
    }
    public void setIcon(Drawable icon){
        this.icon=icon;
    }
    public String getActionName(){return this.actionName;}
    public Drawable getIcon(){return this.icon;}

}
