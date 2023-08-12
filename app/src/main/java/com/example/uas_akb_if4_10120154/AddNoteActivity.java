// NIM      : 10120049
// Nama     : Mochammad Gymnastiar
// Kelas    : IF-2

package com.example.uas_akb_if4_10120154;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;


public class AddNoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titleInput;
    private EditText descInput;
    private Button submitButton;
    private Spinner selectCategory;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleInput = findViewById(R.id.form_add_title);
        descInput = findViewById(R.id.form_add_desc);
        submitButton = findViewById(R.id.submit_button);
        selectCategory = findViewById(R.id.select_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.custom_spinner_layout);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_layout);
        selectCategory.setAdapter(adapter);
        selectCategory.setOnItemSelectedListener(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    public void saveNote() {
        String title = titleInput.getText().toString();
        String desc = descInput.getText().toString();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(AddNoteActivity.this, "Semua Field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Date currentDate = new Date();
        Timestamp timestamp = new Timestamp(currentDate);

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notes");
        collectionReference.add(new Note(title, desc, category, timestamp));
        Toast.makeText(this, "Berhasil menambah Note!", Toast.LENGTH_SHORT).show();
        resetInput();
        finish();

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
}