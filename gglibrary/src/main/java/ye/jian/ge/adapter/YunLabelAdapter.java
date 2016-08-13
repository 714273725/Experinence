package ye.jian.ge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import ye.jian.ge.BaseContext;
import ye.jian.ge.R;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class YunLabelAdapter extends BaseAdapter {
    public void setBeans(List<String> beans) {
        this.beans = beans;
        notifyDataSetChanged();
    }

    /**
     * 该方法返回被选择的特色便签的list
     *
     * @return
     */
    public List getSelectTag() {
        return null;
    }

    View.OnClickListener mOnClickListener;

    {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v);
            }
        };
    }

    private void onItemClick(View v) {
        v.setSelected(!v.isSelected());
    }

    List<String> beans;

    @Override
    public int getCount() {
        return null == beans ? 0 : beans.size();
    }

    @Override
    public Object getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LabelViewHolder holder;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) BaseContext.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.label_item_features, null);
            holder = new LabelViewHolder();
            holder.tv_label = (TextView) convertView.findViewById(R.id.tv_tag);
            convertView.setTag(holder);
        }
        holder = (LabelViewHolder) convertView.getTag();
        holder.tv_label.setText(beans.get(position));
        holder.tv_label.setOnClickListener(mOnClickListener);
        return convertView;
    }

    public static class LabelViewHolder {
        public TextView tv_label;
    }
}
