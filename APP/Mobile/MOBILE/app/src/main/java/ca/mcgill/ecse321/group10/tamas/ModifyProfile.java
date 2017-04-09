package ca.mcgill.ecse321.group10.tamas;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.group10.TAMAS.model.ProfileManager;
import ca.mcgill.ecse321.group10.TAMAS.model.Student;
import ca.mcgill.ecse321.group10.controller.ProfileController;

public class ModifyProfile extends AppCompatActivity {
    
    Student student;
    TextView userInfo;
    EditText editFirstName,editLastName,editPassword,editExperience;
    Switch degreeSwitch;

    Student.Degree degree = Student.Degree.UNDERGRAD;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);
        
        student = ((TAMAS) this.getApplication()).getStudent();


        userInfo = (TextView) findViewById(R.id.userInfo);
        editFirstName = (EditText) findViewById(R.id.modFirstName);
        editLastName = (EditText) findViewById(R.id.modLastName);
        editPassword = (EditText) findViewById(R.id.modPassword);
        editExperience = (EditText) findViewById(R.id.experience);
        degreeSwitch = (Switch) findViewById(R.id.degree);
        String userDescription = "";

        if (student == null){
            userDescription = "Please Login first";
            userInfo.setText(userDescription);
            return;
        }

        userDescription = "Hello " + student.getFirstName() + " " + student.getLastName();
        userInfo.setText(userDescription);

        String experience = student.getExperience();
        editExperience.setText(experience);


        degreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    degreeSwitch.setText("Graduate");
                    degree = Student.Degree.GRADUATE;
                }
                else{
                    degreeSwitch.setText("Undergraduate");
                    degree = Student.Degree.UNDERGRAD;
                }
            }
        });

        if(student.getDegree().equals(Student.Degree.GRADUATE)){
            degreeSwitch.setChecked(true);
        }




    }



    public void applyChangesClicked (View v){
        if (v.getId() == R.id.modProfileButton){

            if(student == null){
                Context userInfoContext = findViewById(R.id.userInfo).getContext();
                userInfo.setTextColor(ContextCompat.getColor(userInfoContext,R.color.errorColor));
                if(userInfo.getTypeface().equals(Typeface.BOLD)){
                    userInfo.setAllCaps(true);
                }
                else{
                    Typeface bold = Typeface.defaultFromStyle(Typeface.BOLD);
                    userInfo.setTypeface(bold);
                }
                return;
            }

            String newFirstName = editFirstName.getText().toString();
            String newLastName = editLastName.getText().toString();
            String newPassword = editPassword.getText().toString();

            String setFirstname, setLastName, setPassword;

            if(newFirstName.equals("")){
                setFirstname = student.getFirstName();
            }
            else{
                setFirstname = newFirstName;
            }

            if(newLastName.equals("")){
                setLastName = student.getLastName();
            }
            else{
                setLastName = newLastName;
            }

            if(newPassword.equals("")){
                setPassword = student.getPassword();
            }
            else{
                setPassword = newPassword;
            }



            try{
                ProfileManager pm = ((TAMAS) this.getApplication()).getProfileManager();
                ProfileController pc = ((TAMAS) this.getApplication()).getProfileController();
                String experience = editExperience.getText().toString();
                pc.modifyStudent(student.getUsername(),setPassword, setFirstname, setLastName,experience,degree);
            } catch (Exception e){
                //this should never happen because strings are checked beforehand and username doesn't change
                Context userInfoContext = findViewById(R.id.userInfo).getContext();
                userInfo.setTextColor(ContextCompat.getColor(userInfoContext,R.color.errorColor));
                userInfo.setText(e.getMessage());
            }
            editFirstName.setText("");
            editLastName.setText("");
            editPassword.setText("");

            Toast toast = Toast.makeText(this, "Changes Applied", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP | Gravity.CENTER,0,0);
            toast.show();
        }
    }
}
