package test.screenlocker.com.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import test.screenlocker.com.myapplication.listener.SliderListener;
import test.screenlocker.com.myapplication.utils.PreferencesConstants;
import test.screenlocker.com.myapplication.utils.PreferencesHandler;

public class UserFregment extends Fragment {
    private EditText etEmailAddrss;
    private EditText etPhoneNumber;
    private Button btnSubmit, btnclear, btnimage;
    TextView heading;
    View view;
    SlidingPaneLayout mSlidingLayout;
    ImageButton slidingPaneButton;
    private  static  final int SELECTED_PICTURE=1;
    public UserFregment() {
    }

    public static UserFregment newInstance() {
        Bundle args = new Bundle();
        UserFregment sampleFragment = new UserFregment();
        sampleFragment.setArguments(args);
        return sampleFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
       if(etEmailAddrss==null && etPhoneNumber==null) {
        //   new AlertDialog.Builder(getActivity()).setTitle("Alert!").setMessage("Insert Phone number and Email ID.").setIcon(R.drawable.icon).setNeutralButton("OK", null).show();
       }
        initView();
        setListeners();
        return view;
    }

    public void initView() {
        etPhoneNumber = (EditText) view.findViewById(R.id.editText4);
        etEmailAddrss = (EditText) view.findViewById(R.id.editText5);
        btnclear = (Button) view.findViewById(R.id.button3);
        btnSubmit = (Button) view.findViewById(R.id.button2);
        btnimage = (Button) view.findViewById(R.id.image);
        heading = (TextView) view.findViewById(R.id.textview);

        etPhoneNumber.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.phone));
        etEmailAddrss.setText(PreferencesHandler.getStringPreferences(PreferencesConstants.email));

        heading.setText("User Profile");
        mSlidingLayout = (SlidingPaneLayout) getActivity().findViewById(R.id.sliding_pane_layout);
        mSlidingLayout.setPanelSlideListener(SliderListener.getInstance(getActivity()));
        slidingPaneButton = (ImageButton) view.findViewById(R.id.left_button_header);
        slidingPaneButton.setVisibility(View.VISIBLE);
        slidingPaneButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.top_left_menu_icon));
    }

    public void setListeners() {
        etEmailAddrss.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isEmailAddress(etEmailAddrss, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validate_profile.isPhoneNumber(etPhoneNumber, false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmailAddrss.setText("");
                etPhoneNumber.setText("");
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidation()) {
                    submitForm();
                } else
                    Toast.makeText(getActivity(), "Please fill the fields properly!", Toast.LENGTH_LONG).show();
            }
        });

        slidingPaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingLayout.openPane();
            }
        });

     /*   btnimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(i, SELECTED_PICTURE);
            }
         public void   onActivtyResult( int requestCode, int resultCode, Intent data)
            {
            UserFregment.super.onActivityResult( requestCode,  resultCode, data);
                switch (requestCode)
                {
                    case SELECTED_PICTURE:
                        if(requestCode==RESULT_OK)
                        {
                            Uri uri=data.getData();
                            String[] projection={MediaStore.Images.Media.DATA};
                            Cursor cursor= getContentResolver().query(uri, projection, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex=cursor.getColumnIndex(projection[0]);
                            String filePath=cursor.getString(columnIndex);
                            cursor.close();
                            Bitmap yourSelectedImage= BitmapFactory.decodeFile(filePath);
                            Drawable d=new BitmapDrawable(yourSelectedImage);
                        }
                        break;
                    default:
                        break;
                }
            }
        });*/
    }

    private void submitForm() {
        PreferencesHandler.updatePreferences(PreferencesConstants.email, etEmailAddrss.getText().toString().trim());
        PreferencesHandler.updatePreferences(PreferencesConstants.phone, etPhoneNumber.getText().toString().trim());

        Toast.makeText(getActivity(), "Profile Created!", Toast.LENGTH_SHORT).show();

    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validate_profile.isEmailAddress(etEmailAddrss, true)) ret = false;
        if (!Validate_profile.isPhoneNumber(etPhoneNumber, true)) ret = false;

        return ret;
    }
}