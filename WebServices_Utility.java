package com.example.mhamed_kassem.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * Created by mhamed on 3/5/18.
 *
 */

public class WebServices_Utility {

    private static Map<String,Object> retrData;
    public DocumentReference mDocRef = FirebaseFirestore.getInstance().document("users/mhamed");

    void saveData(Map<String, Object> datatoSave) {

        mDocRef.set(datatoSave).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               if(documentSnapshot.exists()){
                   retrData = documentSnapshot.getData();

               }
           }
        });
    }


    public Map<String,Object> fetchData(){
        getData();
        return retrData;
    }

}
