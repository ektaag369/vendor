package com.example.vendor.MachineRent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vendor.R;
import com.example.vendor.Vendor.VendorDashboard;
import com.example.vendor.Vendor.VendorDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;

public class MachineRenterDetails extends AppCompatActivity {
    EditText tname, tphone, tcity,tpost,tdistrict,tstate,tpincode;
    Button submit, showimg;
    ImageView timage;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    Uri filepath;
    Bitmap bitmap;
    String imageurl;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_renter_details);

        tname = findViewById(R.id.renter_name);
        tphone = findViewById(R.id.renter_phone);
        tcity = findViewById(R.id.renter_city);
        tpost = findViewById(R.id.renter_post);
        tdistrict = findViewById(R.id.renter_district);
        tstate = findViewById(R.id.renter_state);
        tpincode = findViewById(R.id.renter_pincode);
        submit = findViewById(R.id.renter_Submit_btn);
        showimg=findViewById(R.id.rentershowimg);
        timage=findViewById(R.id.renterimg);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        userid = currentuser.getUid();

        ProgressDialog pd=new ProgressDialog(MachineRenterDetails.this);
        pd.show();

        firestore.collection("MachineLender").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    pd.dismiss();
                    Intent i=new Intent(MachineRenterDetails.this, MachineDashboard.class);
                    startActivity(i);
                    finish();
                }
                else{
                    pd.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Intent i=new Intent(MachineRenterDetails.this,MachineRenterDetails.class);
                startActivity(i);

            }
        });

        showimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(MachineRenterDetails.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Images"),101);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tname.toString().isEmpty() || tcity.toString().isEmpty() ||
                        tpost.toString().isEmpty() ||
                        tdistrict.toString().isEmpty() ||
                        tstate.toString().isEmpty() ||
                        tpincode.toString().isEmpty() ||
                        tphone.toString().isEmpty()) {
                    Toast.makeText(MachineRenterDetails.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    add_lendor(filepath);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==101 && resultCode==RESULT_OK){
            filepath=data.getData();

            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                timage.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void add_lendor(Uri filepath) {
        ProgressDialog pd1=new ProgressDialog(MachineRenterDetails.this);
        pd1.setCancelable(false);
        pd1.setMessage("Adding Details...");
        pd1.show();

        String name = tname.getText().toString();
        String city = tcity.getText().toString();
        String post = tpost.getText().toString();
        String district = tdistrict.getText().toString();
        String state = tstate.getText().toString();
        String pincode = tpincode.getText().toString();
        String phone = tphone.getText().toString();

        HashMap<String, Object> rentor = new HashMap<>();
        rentor.put("Name", name);
        rentor.put("City", city);
        rentor.put("Post", post);
        rentor.put("District", district);
        rentor.put("State", state);
        rentor.put("Pincode", pincode);
        rentor.put("Phone", phone);

        FirebaseStorage storage= FirebaseStorage.getInstance();
        String filename=getfilenamefromuri(filepath );
        StorageReference renteruploader=storage.getReference().child("/Lenderimages").child(filename);

        renteruploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                renteruploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageurl=(String.valueOf(uri));
                        rentor.put("ImageUrl",imageurl);
                        firestore.collection("MachineLender").document(userid)
                                .set(rentor)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        pd1.dismiss();
                                        Toast.makeText(MachineRenterDetails.this, "Added", Toast.LENGTH_SHORT).show();
                                        Intent i=new Intent(MachineRenterDetails.this, MachineDetails.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd1.dismiss();
                                        Toast.makeText(MachineRenterDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        });


    }

    @SuppressLint("Range")
    private String getfilenamefromuri(Uri filepath) {
        String result=null;
        if(filepath.getScheme().equals("content")){
            Cursor cursor=getContentResolver().query(filepath,null,null,null,null );
            try{
                if(cursor!=null && cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(result==null){
            result=filepath.getPath();
            int cut=result.lastIndexOf('/');
            if(cut!= -1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }

}