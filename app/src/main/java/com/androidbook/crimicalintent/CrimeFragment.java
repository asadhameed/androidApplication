package com.androidbook.crimicalintent;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    private Button btnDate;
    private CheckBox cBoxSolved;
    private Button btnCrimeReport;
    private Button btnSuspect;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private static final String ARG_CRIME_ID="crime_id";
    private static final int REQUEST_CODE=0;
    private static final int REQUEST_CONTACT=1;
    private static final int REQUEST_PHOTO=2;
    private File mPhotoFile;

    public  static  CrimeFragment newInstance(UUID crimeid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeid);
        CrimeFragment fragment= new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeID= (UUID) getArguments().getSerializable(ARG_CRIME_ID);
                mCrime= CrimeLab.get(getActivity()).getCrime(crimeID);
                mPhotoFile=CrimeLab.get(getActivity()).getPhotoFile(mCrime);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField= v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDate= v.findViewById(R.id.crime_date);
        btnDate.setText(mCrime.getmDate().toString());
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePackerFragment dialog= DatePackerFragment.newIntent(mCrime.getmDate());
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_CODE);
                dialog.show(getFragmentManager(),"DialogDate");
            }
        });
        mTitleField.setText(mCrime.getmTitle());
        cBoxSolved=v.findViewById(R.id.crime_solved);
        cBoxSolved.setChecked(mCrime.ismSolved());
        cBoxSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setmSolved(isChecked);
            }
        });

        btnCrimeReport=v.findViewById(R.id.crime_report);
        btnCrimeReport.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,getCrime());
                i.putExtra(Intent.EXTRA_SUBJECT,"CrimeInalIntent crime report");
                //Always ask from user to call the default option
                i= Intent.createChooser(i,getString(R.string.send_report));
                startActivity(i);
            }
        });
        final Intent pickContact= new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        btnSuspect = v.findViewById(R.id.crime_suspect);
        btnSuspect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivityForResult(pickContact,REQUEST_CONTACT);

            }
        });
        if(mCrime.getmSuspect()!=null)
            btnSuspect.setText(mCrime.getmSuspect());

        mPhotoButton =v.findViewById(R.id.crime_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  boolean canTakePhoto=mPhotoFile != null && captureImage.resolveActivity(pa)
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= FileProvider.getUriForFile(getActivity(),"com.androidbook.crimicalintent",mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                List<ResolveInfo> cameraAct= getActivity().getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
                for(ResolveInfo actvity:cameraAct){
                    getActivity().grantUriPermission(actvity.activityInfo.packageName,uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });
        mPhotoView = v.findViewById(R.id.crime_photo);
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
                return;
        if(requestCode==REQUEST_CODE){
            Date newCrimeDate=(Date) data.getSerializableExtra(DatePackerFragment.ARG_DATE);
            mCrime.setmDate(newCrimeDate);
            btnDate.setText(mCrime.getmDate().toString());
        }
        if(requestCode==REQUEST_CONTACT && data!=null){
            Uri contactUri=data.getData();
            String[] queryFields= new String []{ContactsContract.Contacts.DISPLAY_NAME};
            Cursor cursor=getActivity().getContentResolver().query(contactUri, queryFields, null,null,null);

            try {
                if(cursor.getCount()==0){
                    return;
                }
                cursor.moveToFirst();
                String supect= cursor.getString(0);
                mCrime.setmSuspect(supect);
                btnSuspect.setText(supect);
            }finally {
                cursor.close();
            }
        }
    }

    @Override
    public  void onPause(){
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    private String getCrime(){
        String solvedString = null;
        if(mCrime.ismSolved()){
            solvedString="Report is solved";
        }
        else
            solvedString ="Report is not solved yet";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        String dateString= sdf.format(mCrime.getmDate());

        String supect=mCrime.getmSuspect();
        if(supect == null){
            supect="there is no suspect.";
        }
        else {
            supect= "the suspect is" + supect;
        }

        String report =getString(R.string.crime_report,mCrime.getmTitle(),dateString,solvedString,supect);
        return report;
    }
}
