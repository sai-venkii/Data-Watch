package com.example.networkmonitor2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAppAdapter extends ArrayAdapter<AppInfo> {
    private List<SelectedApps> selectedApps = new ArrayList<>();
    public ListAppAdapter(Context context, int resource, List<AppInfo> appInfo){
        super(context,resource,appInfo);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AppInfo app=getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.single_app_layout,parent,false);
        }
        TextView tv=(TextView) convertView.findViewById(R.id.app_name);
        ImageView iv=(ImageView) convertView.findViewById(R.id.icon);
        CheckBox cb=(CheckBox) convertView.findViewById(R.id.checkBox);

        tv.setText(app.getAppName());
        if (app.getAppIcon() != null) {
            iv.setImageDrawable(app.getAppIcon());
        }

        cb.setChecked(app.isSelected());
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                app.setSelected(checkBox.isChecked());
                if (checkBox.isChecked()) {
                    selectedApps.add(new SelectedApps(app.getPackageName(),app.getUid()));
                } else {
                    selectedApps.remove(new SelectedApps(app.getPackageName(),app.getUid()));
                }
            }
        });
        return convertView;
    }
    public List<SelectedApps> getSelectedApps() {
        return selectedApps;
    }
}

