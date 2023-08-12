package com.example.uas_akb_if4_10120154;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText titleInput;
    private EditText descInput;
    private Button submitButton;
    private Spinner selectCategory;
    String category;

    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        documentId =  getIntent().getStringExtra("documentId");

        titleInput = findViewById(R.id.form_edit_title);
        descInput = findViewById(R.id.form_edit_desc);
        submitButton = findViewById(R.id.submit_button);
        selectCategory = findViewById(R.id.select_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.custom_spinner_layout);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_layout);
        selectCategory.setAdapter(adapter);
        selectCategory.setOnItemSelectedListener(this);

        if (documentId != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("Notes").document(documentId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String oldTitle = documentSnapshot.getString("title");
                                String oldCategory = documentSnapshot.getString("category");
                                String oldDescription = documentSnapshot.getString("description");

                                titleInput.setText(oldTitle);
                                int position = adapter.getPosition(oldCategory);
                                if (position != -1) {
                                    selectCategory.setSelection(position);
                                }
                                descInput.setText(oldDescription);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("EDIT_NOTE_ACTIVITY", "Error : " + e);
                        }
                    });
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote(documentId);
            }
        });
    }

    public void resetInput() {
        titleInput.setText("");
        descInput.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        category = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void editNote(String documentId) {
        String title = titleInput.getText().toString();
        String desc = descInput.getText().toString();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(EditNoteActivity.this, "Semua Field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate);

        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("title", title);
        updatedData.put("description", desc);
        updatedData.put("category", category);
        updatedData.put("updatedOn", timestamp);

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notes");
        collectionReference.document(documentId)
                .update(updatedData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditNoteActivity.this, "Berhasil mengupdate Note!", Toast.LENGTH_SHORT).show();
                        resetInput();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("EditNoteActivity", "Error updating note: " + e.getMessage());
                    }
                });
    }
}