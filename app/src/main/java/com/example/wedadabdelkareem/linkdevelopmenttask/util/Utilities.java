package com.example.wedadabdelkareem.linkdevelopmenttask.util;

import android.content.Context;
import android.widget.Toast;

public class Utilities {
    public static void displayToast(String message , Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
