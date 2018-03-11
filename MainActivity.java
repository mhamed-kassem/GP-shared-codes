package com.example.mhamed_kassem.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentListenOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText et1 = (EditText) findViewById(R.id.editText);
    EditText et2 = (EditText) findViewById(R.id.editText2);
    EditText et3 = (EditText) findViewById(R.id.editText3);
    TextView tv1 = (TextView) findViewById(R.id.textview1);
    TextView tv2 = (TextView) findViewById(R.id.textview2);


    //user id retrieved based on login
    private static int userId = 5; //not real data
    private Map<String, Integer> sensorsReads;
    WebServices_Utility firebasec;

    public MainActivity() {
        firebasec = new WebServices_Utility();
        //not real data
        sensorsReads = new HashMap<>();
        sensorsReads.put("temp", 35);
        sensorsReads.put("hrtRPm", 70);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //our work

    public void upload_sensors_data() {

        /* here i just called 'uploadSensorsData()'
         * method i created it in WebServices_Utility Class
         * it takes 2 parameters 'userID<Integer>' and
         * sensors reads 'map<string,integer>'
         */
        firebasec.uploadSensorsData(userId, sensorsReads);
    } //commented

    public void saveUserDataM(View view) {
        /*here i collect the data from EditTexts i created for testing */
        String name = et1.getText().toString();
        String age = et2.getText().toString();
        String gender = et3.getText().toString();
        //here i put these data in a map<string,string>
        Map<String, String> uData = new HashMap<>();
        uData.put("Name", name);
        uData.put("Age", age);
        uData.put("Gender", gender);
        /*here after i prepared the data to send i call 'saveUserdata' method
         *that i created in WebServices_Utility class
         *takes 1 parameter map<string,string>  */
        firebasec.saveUserdata(uData);
    } //commented

    public void fetchAllUsersDataM(View view) {
        /*here i call 'fetchallusersData' method that i created in WebServices_Utility class
        * it takes no parameters it return all users data as a map<String,object>
        * Object structure like json object */
        Map<String, Object> fetchedD = firebasec.fetchallusersData();
        tv1.setText(fetchedD.toString());
        //here i push that returned data in a textView to see its structure
    }//commented



/*  all the above functions in MainActivity class they for testing
 * you will use WebServices_Utility class`s methods as i used them here
 * but that override below should to be used like it now
 */


    //Real time service
    @Override
    protected void onStart() {
        super.onStart();
        /*here real time method that react to any change in GPDatabase/sensorsData
         *when change happen it update a TextView2 with the last change in GPDatabase/sensorsData
         *'addSnapshotListener' is a builtin method it the responsible for
         * track the changes happen in database
         * 'documentSnapshot' refer to the database because in fact we work with document not database
         */
        DocumentListenOptions dloptions = new DocumentListenOptions();
        dloptions.includeMetadataChanges();

        firebasec.sensorsDataRec.addSnapshotListener(this, dloptions, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Log.d("RealTime", "Got a" + (documentSnapshot.getMetadata().hasPendingWrites() ? "local" : "server") + " update");
                    Object userHDataO = documentSnapshot.get(String.valueOf(userId));
                    tv2.setText(userHDataO.toString());
                } else if (e != null) {
                    Log.d("Real Time", "Got an exception!", e);
                }
            }
        });
    } //commented


}//end of the class


/* now what is we have?
* 1-ADD User Data, 2-Get all Users, 3-ADD sensors Data, 4-Get sensors data -<that track the changes in data>-
* ............
* what is we still need ?
* that what we need to discus it .  */

