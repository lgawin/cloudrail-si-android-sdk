package com.cloudrail.loginwithsample;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditStatus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditStatus extends Fragment {
    private static final String ARG_TOKEN = "token";

    private final View.OnClickListener mUpdateStatusListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
            final EditText editView = new EditText(mContext);

            builder.setTitle("New Status");
            builder.setView(editView);

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });

            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newStatus = editView.getText().toString();
                    new UpdateStatus().execute(newStatus);
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private String mToken;
    private TextView mStatusView;
    private Context mContext;


    public EditStatus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token The token that can be used to talk to the server.
     * @return A new instance of fragment EditStatus.
     */
    public static EditStatus newInstance(String token) {
        EditStatus fragment = new EditStatus();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_status, container, false);

        mStatusView = (TextView) v.findViewById(R.id.status);

        Button update = (Button) v.findViewById(R.id.edit);
        update.setOnClickListener(mUpdateStatusListener);

        new ReceiveCurrentStatus().execute();

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private class ReceiveCurrentStatus extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return new Communication().getStatus(mToken);
        }

        @Override
        protected void onPostExecute(String s) {
            mStatusView.setText(s);
        }
    }

    private class UpdateStatus extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            new Communication().updateStatus(params[0], mToken);
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            mStatusView.setText(s);
        }
    }
}
