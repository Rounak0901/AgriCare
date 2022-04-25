package com.vesit.paddy_ver1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultsActivity extends AppCompatActivity {

    TextView diseaseName, diseaseScientificName, diseasePreventiveMeasure;
    ImageView plantImage;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseUser user = auth.getCurrentUser();

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        diseaseName = findViewById(R.id.txtDiseaseName);
        diseaseScientificName = findViewById(R.id.txtDiseaseScientificName);
        diseasePreventiveMeasure = findViewById(R.id.txtPreventiveMethods);
        plantImage = findViewById(R.id.imgResults);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        String txt1 = extras.getString("ResultDisease");

        String[] diseaseNames = new String[]{"Healthy","Leaf_Blast","Neck_Blast","False_Smut","Leaf_Scald"};
        String[] diseaseNamesToDisplay = new String[]{getString(R.string.healthy),getString(R.string.leaf_blast),getString(R.string.neck_blast),getString(R.string.false_smut),getString(R.string.leaf_scald)};
        String[] diseaseScientificNames = new String[]{getString(R.string.healthy),getString(R.string.leaf_blast_scientific_name),getString(R.string.leaf_blast_scientific_name),getString(R.string.false_smut_scientific_name),getString(R.string.leaf_scald_scientific_name)};
        String[] diseasePreventiveMeasures = new String[]{getString(R.string.preventive_measures_healthy),getString(R.string.preventive_measures_leaf_blast),getString(R.string.preventive_measures_leaf_blast),getString(R.string.preventive_measures_false_smut),getString(R.string.preventive_measures_leaf_scald)};

        int diseaseIndex = 0;
        for (int i=0; i<diseaseNames.length; i++){
            if(txt1.trim().equals(diseaseNames[i])){
                diseaseIndex=i;
            }
        }
        Bitmap img = extras.getParcelable("ImageBitmap");

        plantImage.setImageBitmap(img);
        diseaseName.setText(diseaseNamesToDisplay[diseaseIndex]);
        diseaseScientificName.setText(diseaseScientificNames[diseaseIndex]);
        diseasePreventiveMeasure.setText(diseasePreventiveMeasures[diseaseIndex]);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.feedback_form);
        dialog.setMessage(R.string.feedback_message);
        LayoutInflater inflater = LayoutInflater.from(this);

        View reg_layout = inflater.inflate(R.layout.alert_send_feedback,null);

        final EditText edtFeedback = reg_layout.findViewById(R.id.etFeedback);
        final RatingBar rating = reg_layout.findViewById(R.id.ratingBar);

        dialog.setView(reg_layout);

        dialog.setPositiveButton(R.string.send, (dialogInterface, i) -> {
            String StrFeedback;
            if(rating.getRating()>0){
                StrFeedback = (rating.getRating() +" "+ edtFeedback.getText().toString()).trim();
            }
            else{
                StrFeedback = edtFeedback.getText().toString().trim();
            }


            if(!StrFeedback.isEmpty()) {
                DatabaseReference feedback = database.getReference("Feedback");

                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                feedback.child(user.getDisplayName()).child(dateTime.format(formatObj)).setValue(StrFeedback);
            }
            ResultsActivity.super.onBackPressed();
        });

        dialog.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            dialogInterface.dismiss();
            ResultsActivity.super.onBackPressed();
        });

        dialog.show();
    }
}
