package sg.edu.rp.c346.id22011199.probleml11;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditActivity<DBHelper> extends AppCompatActivity {

    EditText etId, etTitle, etGenre, etYear;
    Spinner spinnerRating;
    Button btnUpdate, btnDelete, btnCancel;

    TextView tvUpdate, tvDelete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etId = findViewById(R.id.etId);
        etTitle = findViewById(R.id.etTitle);
        etGenre = findViewById(R.id.etGenre);
        etYear = findViewById(R.id.etYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        spinnerRating = findViewById(R.id.spinnerr);
        tvUpdate = findViewById(R.id. tvUpdate);
        tvDelete = findViewById(R.id.tvDelete);

        Intent intent = getIntent();
        Movie data = (Movie) intent.getSerializableExtra("movie");
        etId.setText(String.valueOf(data.getId()));
        etTitle.setText(data.getTitle());
        etGenre.setText(data.getGenre());
        etYear.setText(String.valueOf(data.getYear()));
        spinnerRating.setSelection(getIndex(spinnerRating, data.getRating()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(EditActivity.this);
                String selectedItem = spinnerRating.getSelectedItem().toString();
                data.setTitle(etTitle.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                data.setRating(selectedItem);
                db.updateMovie(data);
                db.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie" + etTitle + "?");
                myBuilder.setCancelable(false);
                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvDelete.setText("");
                    }
                }) ;

        myBuilder.setNeutralButton("CANCEL", null);
        AlertDialog myDialog = myBuilder.create();
        myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);
                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard changes?");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvDelete.setText("");
                    }
                });

                myBuilder.setNegativeButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvDelete.setText("");
                    }
                }) ;

                myBuilder.setNeutralButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
                finish();
            }





    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}