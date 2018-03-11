package com.example.mhamed_kassem.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mhamed on 3/5/18.
 *
 */

public class WebServices_Utility {

    private static Map<String,Object> retrData;

    private DocumentReference mDocRefusers = FirebaseFirestore.getInstance().document("GPDatabase/users");
    public DocumentReference sensorsDataRec = FirebaseFirestore.getInstance().document("GPDatabase/sensorsData");


    void saveUserdata(Map<String, String> datatoSave) {
        int index = fetchallusersData().size();
        Map<Integer,Map<String, String>> dataToUpload = new HashMap<>();
        dataToUpload.put(index,datatoSave);

        mDocRefusers.set(dataToUpload).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Health case","Data saved successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Health case","Oh no!, Data Don`t saved",e);
            }
        });
    }


    private void getData(){

        mDocRefusers.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if(documentSnapshot.exists()){
                   retrData = documentSnapshot.getData();

               }
           }
        });
    }


    public Map<String,Object> fetchallusersData(){
        getData();
        return retrData;
    }


    public void uploadSensorsData(int userId,Map<String,Integer>sDataw){
        Map<Integer,Map<String,Integer>>sDatam = new HashMap<>();
        sDatam.put(userId,sDataw);
        sensorsDataRec.set(sDatam).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("uploading sensors data","great!, sensors data is uploaded successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("uploading sensors data","Ooh, uploading sensors data failed, you get an exception "+e);
            }
        });//Add listener
    }

}
