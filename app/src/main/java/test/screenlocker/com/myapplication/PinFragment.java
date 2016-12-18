package test.screenlocker.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.manusunny.pinlock.PinListener;

import static android.content.Context.MODE_PRIVATE;


public class PinFragment extends Fragment
{
    Switch s2, s3;

    public static final int REQUEST_CODE_SET_PIN = 0;
    public static final int REQUEST_CODE_CHANGE_PIN = 1;
    public static final int REQUEST_CODE_CONFIRM_PIN = 2;
    static SharedPreferences pinLockPrefs;
    TextView t1, t2;
    SharedPreferences mSp,mSpPattern,mAp, mApPattern;
    TextView setPin, confirmPin;

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
        pinLockPrefs = getActivity().getSharedPreferences("PinLockPrefs", MODE_PRIVATE);
        setPin = (TextView) v.findViewById(R.id.set_pin);
        confirmPin = (TextView) v.findViewById(R.id.confirm_pin);
        init();

        mSp = getActivity().getSharedPreferences("SmartPinLock", MODE_PRIVATE);
        mSpPattern = getActivity().getSharedPreferences("SmartPatternLock", MODE_PRIVATE);

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

        mAp = getActivity().getSharedPreferences("PinLock", MODE_PRIVATE);
        mApPattern = getActivity().getSharedPreferences("PatternLock", MODE_PRIVATE);

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
        return v;
    }

    private void init() {
        String pin = pinLockPrefs.getString("pin", "");
        if (pin.equals("")) {
            confirmPin.setEnabled(false);
        } else {
            setPin.setText("Change PIN");
        }

        View.OnClickListener clickListener = getOnClickListener();
        setPin.setOnClickListener(clickListener);
        confirmPin.setOnClickListener(clickListener);
    }

    @NonNull
    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                String pin = pinLockPrefs.getString("pin", "");

                if (id == R.id.set_pin && pin.equals("")) {
                    Intent intent = new Intent(getActivity(), SetPinActivitySample.class);
                    startActivityForResult(intent, REQUEST_CODE_SET_PIN);
                } else if (id == R.id.set_pin) {
                    Intent intent = new Intent(getActivity(), ConfirmPinActivitySample.class);
                    startActivityForResult(intent, REQUEST_CODE_CHANGE_PIN);
                } else if (id == R.id.confirm_pin) {
                    Intent intent = new Intent(getActivity(), ConfirmPinActivitySample.class);
                    startActivityForResult(intent, REQUEST_CODE_CONFIRM_PIN);
                }
            }
        };
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
    private void refreshActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_SET_PIN : {
                if(resultCode == PinListener.SUCCESS){
                    Toast.makeText(getActivity(), "Pin is set :)", Toast.LENGTH_SHORT).show();
                } else if(resultCode == PinListener.CANCELLED) {
                    Toast.makeText(getActivity(), "Pin set cancelled :|", Toast.LENGTH_SHORT).show();
                }
                refreshActivity();
                break;
            }
            case REQUEST_CODE_CHANGE_PIN : {
                if(resultCode == PinListener.SUCCESS){
                    Intent intent = new Intent(getActivity(), SetPinActivitySample.class);
                    startActivityForResult(intent, REQUEST_CODE_SET_PIN);
                } else if(resultCode == PinListener.CANCELLED){
                    Toast.makeText(getActivity(), "Pin change cancelled :|", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case REQUEST_CODE_CONFIRM_PIN : {
                if(resultCode == PinListener.SUCCESS){
                    Toast.makeText(getActivity(), "Pin is correct :)", Toast.LENGTH_SHORT).show();
                } else if(resultCode == PinListener.CANCELLED) {
                    Toast.makeText(getActivity(), "Pin confirm cancelled :|", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }

}
