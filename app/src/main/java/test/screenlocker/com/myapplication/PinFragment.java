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
import com.github.orangegangsters.lollipin.lib.managers.AppLock;

import test.screenlocker.com.myapplication.PinLock.CustomPinActivity;

public class PinFragment extends Fragment
{
    Switch s2, s3;


    TextView t1, t2;
    SharedPreferences mSp,mSpPattern,mAp, mApPattern;

    ListView lv;
    String[] itemname ={
            "Create Pin",
            "Change Pin",


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
        View v= inflater.inflate(R.layout.fragment_pin, container, false);

        s2 = (Switch) v.findViewById(R.id.switch3);
        s3 = (Switch) v.findViewById(R.id.switch2);

        mSp = getActivity().getSharedPreferences("SmartPinLock", Context.MODE_PRIVATE);
        mSpPattern = getActivity().getSharedPreferences("SmartPatternLock", Context.MODE_PRIVATE);

        String servicePattern = mSpPattern.getString("servicePattern", "");
        String service = mSp.getString("servicePin", "");

        if(servicePattern.equals("true"))
        {
            Toast.makeText(getActivity(), "Pattern Service On ",
                    Toast.LENGTH_LONG).show();
            s2.setEnabled(false);
        }
        else
        {
            s2.setEnabled(true);
            if (service.equals("true")) {
                s2.setChecked(true);
                getActivity().startService(new Intent(getActivity(), PinLockService.class));

            } else {
                s2.setChecked(false);
                getActivity().stopService(new Intent(getActivity(), PinLockService.class));

            }

        }

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (s2.isChecked()) {
                    SharedPreferences.Editor editorrr = mSp.edit();
                    editorrr.putString("servicePin", "true");
                    editorrr.commit();
                    Toast.makeText(getActivity(), "Service On ",
                            Toast.LENGTH_LONG).show();
                    getActivity(). startService(new Intent(getActivity(), PinLockService.class));

                } else {
                    SharedPreferences.Editor editorrr = mSp.edit();
                    editorrr.putString("servicePin", "false");
                    editorrr.commit();
                    Toast.makeText(getActivity(), "Service Off ",
                            Toast.LENGTH_LONG).show();
                    getActivity().stopService(new Intent(getActivity(), PinLockService.class));


                }

            }
        });

        mAp = getActivity().getSharedPreferences("PinLock", Context.MODE_PRIVATE);
        mApPattern = getActivity().getSharedPreferences("PatternLock", Context.MODE_PRIVATE);

        String servicePattern1 = mApPattern.getString("simplePattern", "");
        String service1 = mAp.getString("simplePin", "");

        if(servicePattern1.equals("true"))
        {
            Toast.makeText(getActivity(), "Pattern Service On! ",
                    Toast.LENGTH_LONG).show();
            s3.setEnabled(false);
        }
        else
        {
            s3.setEnabled(true);
            if (service1.equals("true")) {
                s3.setChecked(true);
                getActivity().startService(new Intent(getActivity(), ActivatePinService.class));

            } else {
                s3.setChecked(false);
                getActivity(). stopService(new Intent(getActivity(), ActivatePinService.class));

            }

        }

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (s3.isChecked()) {
                    SharedPreferences.Editor editor = mAp.edit();
                    editor.putString("simplePin", "true");
                    editor.commit();
                    Toast.makeText(getActivity(), "Service On ",
                            Toast.LENGTH_LONG).show();
                    getActivity().startService(new Intent(getActivity(), ActivatePinService.class));

                } else {
                    SharedPreferences.Editor editor = mAp.edit();
                    editor.putString("simplePin", "false");
                    editor.commit();
                    Toast.makeText(getActivity(), "Service Off ",
                            Toast.LENGTH_LONG).show();
                    getActivity().stopService(new Intent(getActivity(), ActivatePinService.class));


                }

            }
        });



        EnablePatternAdaptor adapter = new EnablePatternAdaptor(getActivity(), itemname, imgid);
        lv = (ListView) v.findViewById(R.id.listview);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                switch (position) {
                    case 0:
                         Intent intent = new Intent(getActivity(), CustomPinActivity.class);
                          intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                          startActivity(intent);
                          break;
                    case 1:
                         Intent i = new Intent(getActivity(), CustomPinActivity.class);
                        i.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
                          startActivity(i);
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