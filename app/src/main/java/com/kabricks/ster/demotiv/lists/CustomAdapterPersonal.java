package com.kabricks.ster.demotiv.lists;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kabricks.ster.demotiv.R;

import java.util.ArrayList;


class CustomAdapterPersonal extends ArrayAdapter<DataModel> {
    private ArrayList<DataModel> dataSet;
    Context mContext;
    PersonalFragment fragment;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
    }

    CustomAdapterPersonal(ArrayList<DataModel> data, Context context, PersonalFragment fragment) {
        super(context, R.layout.lists_view, data);
        this.dataSet = data;
        this.mContext = context;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public DataModel getItem(int position) {
        return dataSet.get(position);
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_view, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        final DataModel item = getItem(position);


        assert item != null;
        viewHolder.txtName.setText(item.name);
        viewHolder.checkBox.setChecked(item.checked);

        //update view of those from the database
        if(viewHolder.checkBox.isChecked()){
            viewHolder.txtName.setPaintFlags(viewHolder.txtName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.txtName.setPaintFlags(0);
        }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update view of textview :)
                if(viewHolder.checkBox.isChecked()){
                    viewHolder.txtName.setPaintFlags(viewHolder.txtName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.txtName.setPaintFlags(0);
                }
                fragment.updateProgress(viewHolder.checkBox.isChecked(), item.id);
            }
        });

        viewHolder.txtName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fragment.itemLongClick(item.id);
                return true;
            }
        });

        return result;
    }
}
