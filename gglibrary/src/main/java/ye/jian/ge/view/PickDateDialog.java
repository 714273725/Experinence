package ye.jian.ge.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


import java.util.Date;

import ye.jian.ge.R;
import ye.jian.ge.utils.DateUtil;

/**
 * Created by Administrator on 2015/11/19.
 */
public class PickDateDialog extends AlertDialog implements View.OnClickListener {
    private final DatePicker mDatePicker;

    private final Button mbtnPositve;
    private OnClickListener mListenerPostive;

    private final Button mbtnNegative;
    private OnClickListener mListenerNegative;

    public PickDateDialog(Context context) {
        super(context);

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.dialog_date_picker, null);
        setView(view);
        setCustomTitle(inflater.inflate(R.layout.dialog_title, null));

        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mbtnNegative = (Button) view.findViewById(R.id.btn_date_cancel);
        mbtnPositve = (Button) view.findViewById(R.id.btn_date_confirm);
    }


    /**
     * Gets one of the buttons used in the dialog. Returns null if the specified
     * button does not exist or the dialog has not yet been fully created (for
     * example, via {@link #show()} or {@link #create()}).
     *
     * @param whichButton The identifier of the button that should be returned.
     *                    For example, this can be
     *                    {@link DialogInterface#BUTTON_POSITIVE}.
     * @return The button from the dialog, or null if a button does not exist.
     */
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

    /**
     * Gets the {@link DatePicker} contained in this dialog.
     *
     * @return The calendar view.
     */
    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    /**
     * 根据当前选中的时间，返回一个Date对象
     * <br/>ps:DatePicker获取到的月份是0~11，所以这里+1处理
     *
     * @return Date
     */
    public Date getCurrentDate() {
        return DateUtil.intToDate(mDatePicker.getYear(), mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
    }

    /**
     * 返回当前选中的时间字符串，格式为 “yyyy年MM月dd日”
     *
     * @return
     */
    public String getCurrentDateString() {
        return DateUtil.getFormatDateString(getCurrentDate(), "yyyy-MM-dd");
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
    public void setButton(int whichButton, CharSequence text, Message msg) {
        //不用该方法
    }


    @Override
    public void show() {
        mbtnPositve.setOnClickListener(this);
        mbtnNegative.setOnClickListener(this);
        super.show();
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
