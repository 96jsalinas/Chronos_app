package com.example.a96jsa.chronos_app;

import android.graphics.Color;

public class CustomColors {
    public static int getColor(String color){

        switch (color){
            case "Dark blue":
                return Color.parseColor("#0C374D");
            case "Light blue":
                return Color.parseColor("#3C6478");
            case "Dark green":
                return Color.parseColor("#829356");
            case "Light green":
                return Color.parseColor("#B5C689");
            case "Dark yellow":
                return Color.parseColor("#BCA136");
            case "Light yellow":
                return Color.parseColor("#EFD469");
            case "Dark orange":
                return Color.parseColor("#C2571A");
            case "Light orange":
                return Color.parseColor("#F58B4C");
            case "Dark red":
                return Color.parseColor("#9A2617");
            case "Light red":
                return Color.parseColor("#CD594A");
            case "Background color":
                return Color.parseColor("#ffebd8");
        }
        return Color.parseColor("#0C374D");

    }
}
