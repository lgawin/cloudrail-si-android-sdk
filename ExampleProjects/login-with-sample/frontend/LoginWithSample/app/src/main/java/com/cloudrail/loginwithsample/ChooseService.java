package com.cloudrail.loginwithsample;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cloudrail.si.interfaces.Profile;
import com.cloudrail.si.services.Facebook;
import com.cloudrail.si.services.GooglePlus;
import com.cloudrail.si.services.LinkedIn;
import com.cloudrail.si.services.Twitter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseService.OnTokenListener} interface
 * to handle interaction events.
 * Use the {@link ChooseService#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseService extends Fragment {

    private Context mContext;
    private OnTokenListener mListener;

    private View.OnClickListener mServiceSelectedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Profile profile;

            switch (v.getId()) {
                case R.id.Facebook: {
                    profile = new Facebook(mContext, "1021498147933996", "67d976115ced3937142b92dd9801f012");
                    break;
                }
                case R.id.Twitter: {
                    profile = new Twitter(mContext, "7wDl9gfhMWB7UUa2xXdbfoYjS", "nAJJ8bvX4KBGsgWXNLctXQKnwmbWtidLW7bfkB4oiLwsvPrjOs");
                    break;
                }
                case R.id.GooglePlus: {
                    profile = new GooglePlus(mContext, "1003356524097-3of9si9hiqdkf116d90aa51g4ibkro9f.apps.googleusercontent.com", "srmYxA2hMOcgecTkw69B4ck2");
                    break;
                }
                case R.id.LinkedIn: {
                    profile = new LinkedIn(mContext, "77anwn10tgmgui", "7Cwx2g0yPjmOuprv");
                    break;
                }
                default:
                    throw new RuntimeException("Unknown Button ID!!");
            }

            new PerformLogin().execute(profile);
        }
    };

    public ChooseService() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChooseService.
     */
    public static ChooseService newInstance() {
        return new ChooseService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_choose, container, false);

        Button facebook = (Button) v.findViewById(R.id.Facebook);
        facebook.setOnClickListener(mServiceSelectedListener);
        Button twitter = (Button) v.findViewById(R.id.Twitter);
        twitter.setOnClickListener(mServiceSelectedListener);
        Button google = (Button) v.findViewById(R.id.GooglePlus);
        google.setOnClickListener(mServiceSelectedListener);
        Button linkedin = (Button) v.findViewById(R.id.LinkedIn);
        linkedin.setOnClickListener(mServiceSelectedListener);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTokenListener) {
            mListener = (OnTokenListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTokenListener");
        }
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnTokenListener) {
            mListener = (OnTokenListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnTokenListener");
        }
        mContext = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mContext = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTokenListener {
        void onToken(String token);
    }

    /**
     * Performs the login that presents a website to the user where he has to login to the selected
     * service. After that we send the obtained access token to our local server who will insert
     * the new user into the database if he isn't registered already. Server returns a token that
     * can be used for further requests to that server.
     */
    private class PerformLogin extends AsyncTask<Profile, Void, String> {

        @Override
        protected String doInBackground(Profile... params) {
            Profile profile = params[0];
            profile.login();

            return new Communication().registerUserSync(profile);
        }

        @Override
        protected void onPostExecute(String s) {
            mListener.onToken(s);
        }
    }
}
