package com.example.mycrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 7 editext, 5 button
    EditText txtEditTextId, txtEditTextName, txtEditTextLastName, txtEditTextAddress,
            txtEditTextCedula, txtEditTextSalary, txtEditTextDepartament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEditTextId = (EditText) findViewById(R.id.main_editText_Id);
        txtEditTextName = (EditText) findViewById(R.id.main_editText_Name);
        txtEditTextLastName = (EditText) findViewById(R.id.main_editText_LastName);
        txtEditTextAddress = (EditText) findViewById(R.id.main_editText_Address);
        txtEditTextCedula = (EditText) findViewById(R.id.main_editText_Cedula);
        txtEditTextSalary = (EditText) findViewById(R.id.main_editText_Salary);
        txtEditTextDepartament = (EditText) findViewById(R.id.main_editText_Departament);
    }

    public void insertUser(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "admin", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String strUserId = txtEditTextId.getText().toString();
        String strName = txtEditTextName.getText().toString();
        String strLastName = txtEditTextLastName.getText().toString();
        String strAddress = txtEditTextAddress.getText().toString();
        String strCedula = txtEditTextCedula.getText().toString();
        String strSalary = txtEditTextSalary.getText().toString();
        String strDepartament = txtEditTextDepartament.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("ID", strUserId);
        registro.put("name", strName);
        registro.put("last_name", strLastName);
        registro.put("address", strAddress);
        registro.put("cedula", strCedula);
        registro.put("departament", strDepartament);
        registro.put("salary", strSalary);

        if (strUserId.isEmpty() || strName.isEmpty() || strCedula.isEmpty()) return;

        db.insert("user", null, registro);

        db.close();
        cleanField();
        Toast.makeText(this, "Datos insertados",Toast.LENGTH_SHORT).show();
    }

    public void deleteUser(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "admin", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String strUserId = txtEditTextId.getText().toString();
        if (strUserId.isEmpty() ) return;

        db.delete("user", "ID=" + strUserId, null);
        Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show();
        db.close();
        cleanField();
    }

    public void editUser(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "admin", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        String strUserId = txtEditTextId.getText().toString();
        String strName = txtEditTextName.getText().toString();
        String strLastName = txtEditTextLastName.getText().toString();
        String strAddress = txtEditTextAddress.getText().toString();
        String strCedula = txtEditTextCedula.getText().toString();
        String strSalary = txtEditTextSalary.getText().toString();
        String strDepartament = txtEditTextDepartament.getText().toString();

        registro.put("ID", strUserId);
        registro.put("name", strName);
        registro.put("last_name", strLastName);
        registro.put("address", strAddress);
        registro.put("cedula", strCedula);
        registro.put("departament", strDepartament);
        registro.put("salary", strSalary);

        if (strUserId.isEmpty() || strName.isEmpty() || strCedula.isEmpty()) return;
        int cant = db.update("user", registro, "ID=" + strUserId, null);

        if (cant == 1)
            Toast.makeText(this, "Data modified", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Doesn't Exist",Toast.LENGTH_SHORT).show();

        db.close();
        cleanField();
    }

    public void searchUserById(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "admin", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        String strUserId = txtEditTextId.getText().toString();
        Cursor line = db.rawQuery("SELECT name, last_name, address, cedula, departament," +
                " salary where Id=" + strUserId, null);

        if (line.moveToFirst()) {
            txtEditTextName.setText(line.getString(1));
            txtEditTextLastName.setText(line.getString(2));
            txtEditTextCedula.setText(line.getString(3));
            txtEditTextDepartament.setText(line.getString(4));
            txtEditTextAddress.setText(line.getString(5));
            txtEditTextSalary.setText(line.getString(6));
        }
        else {
            Toast.makeText(this, "Doesn't exist", Toast.LENGTH_SHORT).show();
        }

        db.close();
        cleanField();
    }

    public void cleanField() {
        txtEditTextId.setText("");
        txtEditTextName.setText("");
        txtEditTextLastName.setText("");
        txtEditTextAddress.setText("");
        txtEditTextCedula.setText("");
        txtEditTextSalary.setText("");
        txtEditTextDepartament.setText("");
    }
}