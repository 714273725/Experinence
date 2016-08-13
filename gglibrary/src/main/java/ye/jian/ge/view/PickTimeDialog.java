package ye.jian.ge.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import ye.jian.ge.R;

/**
 * Created by Administrator on 2016/6/1.
 */
public class PickTimeDialog extends AlertDialog implements View.OnClickListener {
    private final TimePicker mTimePicker;
    private final Button mbtnPositve;
    private final TextView title;
    private OnClickListener mListenerPostive;
    private final Button mbtnNegative;
    private OnClickListener mListenerNegative;

    public PickTimeDialog(Context context) {
        super(context);
        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.dialog_time_picker, null);
        setView(view);
        title = (TextView) inflater.inflate(R.layout.dialog_title, null);

        setCustomTitle(title);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        mbtnNegative = (Button) view.findViewById(R.id.btn_date_cancel);
        mbtnPositve = (Button) view.findViewById(R.id.btn_date_confirm);
    }

    @Override
    public Button getButton(int whichButton) {
        switch (whichButton) {
            case AlertDialog.BUTTON_NEGATIVE:
                return mbtnNegative;
            case AlertDialog.BUTTON_POSITIVE:
                return mbtnPositve;
            default:
                return null;
        }
    }

    public void show(String titles) {
        title.setText(titles);
        show();
    }

    public String getTimeString() {
        return mTimePicker.getCurrentHour() + ":" + (mTimePicker.getCurrentMinute() < 10 ? "0" + mTimePicker.getCurrentMinute() : mTimePicker.getCurrentMinute());
    }

    public static boolean compare(String time, String target) {
        boolean temp = false;
        //如果目标是空的 可以设置
        if (target == null||target.isEmpty()) {
            return true;
        }
        String[] times;
        String[] targets;
        //没有开始时间时，返回false
        try {
            times = time.split(String.valueOf(":"));
            targets = target.split(String.valueOf(":"));
            if (Integer.valueOf(times[0]) > Integer.valueOf(targets[0])) {
                temp = true;
            } else if (Integer.valueOf(times[1]) > Integer.valueOf(targets[1])) {
                temp = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return temp;
        }
        return temp;
    }

    @Override
    public void show() {
        mbtnPositve.setOnClickListener(this);
        mbtnNegative.setOnClickListener(this);
        super.show();
    }

    @Override
    public void setButton(int whichButton, CharSequence text, OnClickListener listener) {
        switch (whichButton) {
            case AlertDialog.BUTTON_NEGATIVE:
                mbtnNegative.setText(text);
                mListenerNegative = listener;
                break;
            case AlertDialog.BUTTON_POSITIVE:
                mbtnPositve.setText(text);
                mListenerPostive = listener;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_date_confirm) {
            if (mListenerPostive != null)
                mListenerPostive.onClick(this, AlertDialog.BUTTON_POSITIVE);
        }
        if (v.getId() == R.id.btn_date_cancel) {
            if (mListenerNegative != null)
                mListenerNegative.onClick(this, AlertDialog.BUTTON_NEGATIVE);
        }
        dismiss();
    }
}
