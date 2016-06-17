package com.example.odniprovskyi7708.lab4;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myNewDb; //creating an instance of helper method

    //Text field instances
    EditText editFirstName, editLastName, editMarks, editTextId;

    //buttons instances
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdateData;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myNewDb = new DatabaseHelper(this);//Created a new instance of our "Helper method" from database helper method
        //and created our database and our table

        //Getting values of text fields by "id"
        editFirstName = (EditText)findViewById(R.id.editText_firstName);
        editLastName = (EditText)findViewById(R.id.editText_lastName);
        editMarks = (EditText)findViewById(R.id.editText_marks );
        editTextId = (EditText)findViewById(R.id.editText_id);

        btnAddData = (Button)findViewById(R.id.button_add); //Assign to an instance of AddData the button id of this button
        btnViewAll = (Button)findViewById(R.id.button_view_data); //Assign to an instance of ViewAll data the button id of this button
        btnUpdateData = (Button)findViewById(R.id.button_update); //Assign to an instance of UpdateData the button id of this button
        btnDelete = (Button)findViewById(R.id.button_delete); //Assign to an instance of Delete the button id of this button

        //Calling functions for activities "add", "view all", "update data"
        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
    }

    //Our Add data method
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myNewDb.insertData(editFirstName.getText().toString(),
                                editLastName.getText().toString(),
                                editMarks.getText().toString());
                        if(isInserted == true)//Here we are checking for any inserted data
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();//show message if data was inserted
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();//show message if data wasn't inserted
                    }
                }
        );
    }

    //Our View All data method
    public void ViewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myNewDb.getAllData();
                        if (res.getCount() == 0) {//Here we are checking for any data available for us
                            showMessage("Error", "Nothing found");//show message if there is no data
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();//Here we are storing our data in the buffer variable
                        while (res.moveToNext()){
                            buffer.append("Id :"+ res.getString(0)+ "\n");
                            buffer.append("First Name :"+ res.getString(1)+ "\n");
                            buffer.append("Last Name :"+ res.getString(2)+ "\n");
                            buffer.append("Mark :"+ res.getString(3)+ "\n\n");
                        }

                        //Show all data
                        showMessage("Data", buffer.toString());//show message if there are some data
                    }
                }
        );
    }

    //Our Update method
    public void UpdateData(){
        btnUpdateData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myNewDb.updateData(editTextId.getText().toString(),
                                editFirstName.getText().toString(),
                                editLastName.getText().toString(),
                                editMarks.getText().toString());
                        if(isUpdated == true)//Here we are checking for any updated data
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();//show message if data was updated
                        else
                            Toast.makeText(MainActivity.this, "Data is not Updated", Toast.LENGTH_SHORT).show();//show message if data wasn't updated
                    }
                }
        );
    }

    //Our Delete method
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myNewDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)//checking for some rows to delete
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();//show message if data was deleted
                        else
                            Toast.makeText(MainActivity.this, "Data is not Deleted", Toast.LENGTH_SHORT).show();//show message if data wasn't deleted
                    }
                }
        );
    }





    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);//An instance of "AlertDialog" window, takes the argument it self that's why we use this in brackets
        builder.setCancelable(true);//Using this builder
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
