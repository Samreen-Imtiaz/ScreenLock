package test.screenlocker.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import test.screenlocker.com.myapplication.PatternLock.PatternLockActivity;
import test.screenlocker.com.myapplication.PatternLock.ResetPatternActivity;
import test.screenlocker.com.myapplication.PatternLock.SetPatternActivity;

import static android.app.Activity.RESULT_FIRST_USER;

public class PatternFragment extends Fragment
{
    private static final int _ReqCreatePattern = 1;
    private static final int _ReqSignIn = 2;
    public static final int _ResultFailed = RESULT_FIRST_USER + 1;

    SharedPreferences invisibleSetting;
    private static final String _confirm_started = PatternLockActivity.class
            .getName();


    private static String _ClassName;
    public static final String _ActionCreatePattern = _confirm_started
            + ".create_pattern";
    public static final String _ActionComparePattern = _confirm_started
            + ".compare_pattern";
    public static final String _Pattern = _confirm_started + ".pattern";
    public static final String _ExtraRetryCount = _confirm_started + ".retry_count";

    public Intent intent;
    private static String pattern;
    Bundle b;
    SharedPreferences settings, sharedPreferences;
    Switch s1, s4;
    static boolean switch1;
    SharedPreferences mSp,mSpPin, sp , spPin;
    public static String PIN_STATE = "PIN_STATE";
    public static String PATTERN_STATE = "PATTERN_STATE";
    TextView t3, t4;

    ListView lv;
    String[] itemname ={
            "Create Pattern",
            "Change Pattern",


    };

    Integer[] imgid={
            R.drawable.ic_pen,
            R.drawable.ic_pin,



    };


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_pattern, container, false);
        invisibleSetting = this.getActivity().getSharedPreferences("mySetting", 0);
        settings = this.getActivity().getSharedPreferences("First", 0);
        s1 = (Switch) v.findViewById(R.id.switch4);
        s4 = (Switch) v.findViewById(R.id.switch1);

        mSp = this.getActivity().getSharedPreferences("SmartPatternLock", Context.MODE_PRIVATE);
        mSpPin = this.getActivity().getSharedPreferences("SmartPinLock", Context.MODE_PRIVATE);
        final String servicePin = mSpPin.getString("servicePin", "");

        String service = mSp.getString("servicePattern", "");
        if(servicePin.equals("true"))
        {
            Toast.makeText(getActivity(), "Pin Service On ",
                    Toast.LENGTH_LONG).show();
            s1.setEnabled(false);
        }
        else
        {
            s1.setEnabled(true);
            if (service.equals("true")) {
                s1.setChecked(true);
                getActivity().startService(new Intent(getActivity(), PatternLockService.class));

            } else {
                s1.setChecked(false);
                getActivity().stopService(new Intent(getActivity(), PatternLockService.class));

            }
        }


        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (s1.isChecked()) {
                    SharedPreferences.Editor editorrr = mSp.edit();
                    editorrr.putString("servicePattern", "true");
                    editorrr.commit();
                    Toast.makeText(getActivity(), "Service On ",
                            Toast.LENGTH_LONG).show();
                    getActivity().startService(new Intent(getActivity(), PatternLockService.class));
                } else {
                    SharedPreferences.Editor editorrr = mSp.edit();
                    editorrr.putString("servicePattern", "false");
                    editorrr.commit();
                    Toast.makeText(getActivity(), "Service Off ",
                            Toast.LENGTH_LONG).show();
                    getActivity().stopService(new Intent(getActivity(), PatternLockService.class));

                }

            }
        });

        sp = this.getActivity().getSharedPreferences("PatternLock", Context.MODE_PRIVATE);
        spPin = this.getActivity().getSharedPreferences("PinLock", Context.MODE_PRIVATE);

        final String servicePin1 = spPin.getString("simplePin", "");
        String service2 = sp.getString("simplePattern", "");

        if(servicePin1.equals("true"))
        {
            Toast.makeText(getActivity(), "Pin Service On! ",
                    Toast.LENGTH_LONG).show();
            s4.setEnabled(false);
        }
        else
        {
            s4.setEnabled(true);
            if (service2.equals("true")) {
                s4.setChecked(true);
                getActivity().startService(new Intent(getActivity(), ActivatePatternService.class));

            } else {
                s4.setChecked(false);
                getActivity(). stopService(new Intent(getActivity(), ActivatePatternService.class));

            }
        }


        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (s4.isChecked()) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("simplePattern", "true");
                    editor.commit();
                    Toast.makeText(getActivity(), "Service On ",
                            Toast.LENGTH_LONG).show();
                    getActivity().startService(new Intent(getActivity(), ActivatePatternService.class));
                } else {
                    SharedPreferences.Editor editorrr = sp.edit();
                    editorrr.putString("simplePattern", "false");
                    editorrr.commit();
                    Toast.makeText(getActivity(), "Service Off ",
                            Toast.LENGTH_LONG).show();
                    getActivity().stopService(new Intent(getActivity(), ActivatePatternService.class));

                }

            }
        });








        EnablePinAdaptor adapter=new  EnablePinAdaptor(getActivity(), itemname, imgid);
        lv=(ListView) v.findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                switch (position) {
                    case 0:
                        intent = new Intent(
                                getActivity(), ResetPatternActivity.class);
                       startActivityForResult(intent, _ReqCreatePattern);
                        break;
                    case 1:
                        intent = new Intent(_ActionCreatePattern, null,
                                getActivity(), SetPatternActivity.class);
                        startActivityForResult(intent, _ReqCreatePattern);
                        break;

                }

            }
        });



        return v;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}