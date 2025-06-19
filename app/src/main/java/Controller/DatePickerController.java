package Controller;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerController {
    final Calendar calendar = Calendar.getInstance();
    final Context context;
    final EditText targetEditText;


    public DatePickerController(Context context, EditText targetEditText) {
        this.context = context;
        this.targetEditText = targetEditText;
    }

    public void showDate(EditText targetedEditText) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                targetedEditText.setText(sdf.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(context, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
