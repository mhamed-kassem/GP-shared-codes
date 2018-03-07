package com.example.mhamed_kassem.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentListenOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et1 =(EditText)findViewById(R.id.editText);
    EditText et2 =(EditText)findViewById(R.id.editText2);
    EditText et3 =(EditText)findViewById(R.id.editText3);
    TextView tv1 =(TextView)findViewById(R.id.textview1);

    WebServices_Utility firebasec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void saveHData(View view) {
        String name =et1.getText().toString();
        String age  =et2.getText().toString();
        String gender=et3.getText().toString();
        Map<String,Object> uData = new HashMap<String, Object>();
        uData.put("Name",name);
        uData.put("Age",age);
        uData.put("Gender",gender);

        firebasec =new WebServices_Utility();

        firebasec.saveData(uData);
    }

    public void fetchh(View view) {
        Map<String,Object>fetchedD =firebasec.fetchData();
        tv1.setText(fetchedD.toString());

    }

    @Override
    protected void onStart(){
        super.onStart();
        DocumentListenOptions dloptions = new DocumentListenOptions();
        dloptions.includeMetadataChanges();

        firebasec.mDocRef.addSnapshotListener(this,dloptions,new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    Log.d("RealTime","Got a"+(documentSnapshot.getMetadata().hasPendingWrites()?"local":"server")+" update");
                    Object name = documentSnapshot.get("Name");
                    Object age = documentSnapshot.get("Age");
                    Object gender = documentSnapshot.get("Gender");
                    tv1.setText("user name: "+name.toString()+" ,age: "+age.toString()+" ,gender: "+gender.toString());
                }else if(e!=null){
                    Log.d("Real Time","Got an exception!",e);
                }
            }
        });
    }

}
