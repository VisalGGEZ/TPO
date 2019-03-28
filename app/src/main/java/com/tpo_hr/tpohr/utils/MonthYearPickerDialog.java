package com.tpo_hr.tpohr.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.tpo_hr.tpohr.R;

import java.util.Calendar;
import java.util.Objects;

public class MonthYearPickerDialog extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private int daysOfMonth = 31;

    private NumberPicker monthPicker;
    private NumberPicker yearPicker;
    private NumberPicker dayPicker;

    private Calendar cal = Calendar.getInstance();

    public static final String MONTH_KEY = "monthValue";
    public static final String DAY_KEY = "dayValue";
    public static final String YEAR_KEY = "yearValue";

    public static String showType = "";
    public static final String MM_YYYY = "MM-YYYY";
    public static final String YYYY = "YYYY";
    public static final String DD_MM_YYYY = "DD-MM-YYYY";
    public static final String MMMM = "MM";

    int monthVal = -1, dayVal = -1, yearVal = -1;
    private int daysIndex;
    private int monthIndex;
    private int yearIndex;
    private int yearPlus;

    public String[] listMonths = {"មករា", "កុម្ភៈ", "មិនា", "មេសា", "ឧសភា", "មិថុនា", "កក្កដា", "សីហា", "កញ្ញា", "តុលា", "វិច្ឆកា", "ធ្នូ"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null) {
            monthVal = extras.getInt(MONTH_KEY, -1);
            dayVal = extras.getInt(DAY_KEY, -1);
            yearVal = extras.getInt(YEAR_KEY, -1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getDialog().getWindow()).setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setShowType(String showType) {
        MonthYearPickerDialog.showType = showType;
    }

    public static MonthYearPickerDialog newInstance(int monthIndex, int daysIndex, int yearIndex, int yearPlus, String showType) {
        MonthYearPickerDialog f = new MonthYearPickerDialog();
        f.setShowType(showType);
        if (monthIndex == 0) {
            monthIndex = 1;
        }

        if (daysIndex == 0) {
            daysIndex = 1;
        }

        if (yearIndex == 0) {
            yearIndex = 1990;
        }
        f.setDate(monthIndex, daysIndex, yearIndex, yearPlus);

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(MONTH_KEY, monthIndex);
        args.putInt(DAY_KEY, daysIndex);
        args.putInt(YEAR_KEY, yearIndex);
        f.setArguments(args);

        return f;
    }

    private void setDate(int monthIndex, int daysIndex, int yearIndex, int yearPlus) {
        this.monthIndex = monthIndex;
        this.daysIndex = daysIndex;
        this.yearIndex = yearIndex;
        this.yearPlus = yearPlus;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //getDialog().setTitle("Select your Birthday Date");

        cal.add(Calendar.YEAR, yearPlus);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.month_year_picker, null);
        monthPicker = dialog.findViewById(R.id.picker_month);
        yearPicker = dialog.findViewById(R.id.picker_year);
        dayPicker = dialog.findViewById(R.id.picker_day);
        TextView tvOK = dialog.findViewById(R.id.tvOK);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);

        if (showType.equals(MM_YYYY)) {
            dayPicker.setVisibility(View.GONE);
        } else if (showType.equals(YYYY)) {
            dayPicker.setVisibility(View.GONE);
            monthPicker.setVisibility(View.GONE);
        } else if (showType.equals(MMMM)) {
            dayPicker.setVisibility(View.GONE);
            yearPicker.setVisibility(View.GONE);
        }

        setDividerColor(monthPicker, getActivity().getResources().getColor(R.color.colorPrimaryDark));
        setDividerColor(yearPicker, getActivity().getResources().getColor(R.color.colorPrimaryDark));
        setDividerColor(dayPicker, getActivity().getResources().getColor(R.color.colorPrimaryDark));

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);


        if (monthVal != -1)// && (monthVal > 0 && monthVal < 13))
            monthPicker.setValue(monthVal);
        else
            monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

//        monthPicker.setDisplayedValues(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"});

        monthPicker.setDisplayedValues(listMonths);


        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(daysOfMonth);

        dayPicker.setDisplayedValues(new String[]{"០១", "០២", "០៣", "០៤", "០៥", "០៦", "០៧", "០៨", "០៩", "១០"
                , "១១", "១២", "១៣", "១៤", "១៥", "១៦", "១៧", "១៨", "១៩", "២០"
                , "២១", "២២", "២៣", "២៤", "២៥", "២៦", "២៧", "២៨", "២៩", "៣០", "៣១"});

        if (dayVal != -1)
            dayPicker.setValue(dayVal);
        else
            dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                switch (newVal) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        daysOfMonth = 31;
                        dayPicker.setMaxValue(daysOfMonth);
                        break;
                    case 2:
                        daysOfMonth = 28;
                        dayPicker.setMaxValue(daysOfMonth);
                        break;

                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        daysOfMonth = 30;
                        dayPicker.setMaxValue(daysOfMonth);
                        break;
                }

            }
        });

        int maxYear = cal.get(Calendar.YEAR);//2018
        final int minYear = 1980;
        int arraySize = maxYear - minYear;

        String[] tempArray = new String[arraySize];
        tempArray[0] = "--/--";
        int tempYear = minYear + 1;

        for (int i = 0; i < arraySize; i++) {
            if (i != 0) {
                tempArray[i] = covertToKhmerNumber(String.valueOf(tempYear));
            }
            tempYear++;
        }
        Log.d("", "onCreateDialog: " + tempArray.length);
        yearPicker.setMinValue(minYear + 1);
        yearPicker.setMaxValue(maxYear);
        yearPicker.setDisplayedValues(tempArray);

        dayPicker.setValue(daysIndex);
        monthPicker.setValue(monthIndex);
        yearPicker.setValue(yearIndex);

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                try {
                    if (isLeapYear(picker.getValue())) {
                        daysOfMonth = 29;
                        dayPicker.setMaxValue(daysOfMonth);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = yearPicker.getValue();
                if (year != (minYear + 1)) {
                    listener.onDateSet(null, year, monthPicker.getValue(), dayPicker.getValue());
                    MonthYearPickerDialog.this.getDialog().dismiss();
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthYearPickerDialog.this.getDialog().cancel();
            }
        });

        builder.setView(dialog);
        return builder.create();
    }

    public String covertToKhmerNumber(String number) {

        if (number.length() == 1) {
            return "០" + switchNumber(number);
        } else {
            StringBuilder tempNum = new StringBuilder();
            for (int i = 0; i < number.length(); i++) {
                tempNum.append(switchNumber(String.valueOf(number.charAt(i))));
            }
            return tempNum.toString();
        }
    }

    private String switchNumber(String number) {
        switch (number) {
            case "1":
                return "១";
            case "2":
                return "២";
            case "3":
                return "៣";
            case "4":
                return "៤";
            case "5":
                return "៥";
            case "6":
                return "៦";
            case "7":
                return "៧";
            case "8":
                return "៨";
            case "9":
                return "៩";
            case "0":
                return "០";
        }

        return "០";
    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static boolean isLeapYear2(int year) {
        return year % 4 == 0 && (year % 400 == 0 || year % 100 != 0);
    }
}