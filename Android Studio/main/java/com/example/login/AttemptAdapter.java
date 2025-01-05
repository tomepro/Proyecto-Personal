package com.example.login;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.login.db.FailedAttempt;

import java.util.ArrayList;


public class AttemptAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FailedAttempt> failedAttempts;

    public AttemptAdapter(Context context, ArrayList<FailedAttempt> failedAttempts) {
        this.context = context;
        this.failedAttempts = failedAttempts;
    }

    public int getCount() {
        return failedAttempts.size();
    }

    public Object getItem(int position) {
        return failedAttempts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.attempt_adapter, null);
        }

        TextView usernameTextView = view.findViewById(R.id.textViewUsername);
        TextView passwordTextView = view.findViewById(R.id.textViewPassword);
        TextView timestampTextView = view.findViewById(R.id.textViewTimestamp);

        FailedAttempt failedAttempt = failedAttempts.get(position);

        usernameTextView.setText(failedAttempt.getUsername());
        passwordTextView.setText(failedAttempt.getPassword());
        timestampTextView.setText(failedAttempt.getTimestamp());

        return view;
    }
}
