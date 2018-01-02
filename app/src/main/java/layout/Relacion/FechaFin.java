package layout.Relacion;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import principal.android.empresa.eduardo.R;

/**
 * Created by Home on 20/03/2017.
 */

public class FechaFin extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // USAR CALENDARIO
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //MOSTRAR CALENDARIO AL TEXTVIEW
        TextView tv1 = (TextView) getActivity().findViewById(R.id.fechafin);
        tv1.setText(view.getYear() + "-" + view.getMonth() + "-" + view.getDayOfMonth());

    }
}
