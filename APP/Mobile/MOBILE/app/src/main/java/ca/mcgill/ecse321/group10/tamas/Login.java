package ca.mcgill.ecse321.group10.tamas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class Login extends AppCompatActivity {

    ProfileController pc;
    ProfileManager pm;
    List<Student> students;
    EditText usernameField;
    EditText passwordField;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pm = ((TAMAS) getApplication()).getProfileManager();
        students = pm.getStudents();
        Log.d("students", "#students: " + String.valueOf(students.size()));
        errorText = (TextView) findViewById(R.id.errors);
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);

        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    View tempView = findViewById(R.id.loginButton);
                    loginClicked(tempView);
                    handled = true;
                }
                return handled;
            }
        });


    }

    public void usernameClicked(View v){
                if(v.getId() == R.id.username){
                       if (usernameField.getText().toString().trim().equalsIgnoreCase("")) {
                                usernameField.setError("This field can not be blank");
                           }
                    }
           }

    public void loginClicked(View v){
        if(v.getId() == R.id.loginButton){
            String errors = "";
            int studentIndex = getStudentIndex(students,usernameField.getText().toString());
            Log.d("students","usernameField To String: " + usernameField.getText().toString());
            Log.d("students", "first student: " + pm.getStudent(0).getUsername());
            if(studentIndex == -1) errors += "Student username not found. \n";
            if (errors.length() == 0){
                Student student = pm.getStudent(studentIndex);
                String password = student.getPassword();
                if(passwordField.getText().toString().equals(password)){
                    ((TAMAS) getApplication()).setStudent(student);
                    finish();
                }
                else{
                    errors += "Password incorrect. \n";
                }

                Toast toast = Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER,0,0);
                if(errors.length() == 0) toast.show();
            }
            else{
                usernameField.setText("");
            }
            passwordField.setText("");
            errorText.setText(errors);
        }

    }
    private static int getStudentIndex(List<Student> students, String name){
        for (int i = 0; i< students.size(); i++){
            if(students.get(i).getUsername().equals(name)){
                return i;
            }
        }
        return -1;
    }



}
