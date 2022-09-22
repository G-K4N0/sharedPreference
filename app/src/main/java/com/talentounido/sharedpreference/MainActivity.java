package com.talentounido.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private Button btnGuardar, btnBuscar, btnActualizar, btnEliminar;
    private EditText txtId, txtName, txtLastName, txtAddress, txtAge, txtPhone, txtSalary;
    private ListView lstDatos;
    private ArrayAdapter adapter;
    private ArrayList<String> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        txtAddress = findViewById(R.id.txtAddress);
        txtAge = findViewById(R.id.txtAge);
        txtPhone = findViewById(R.id.txtPhone);
        txtSalary = findViewById(R.id.txtSalary);
        lstDatos = findViewById(R.id.lstData);

        eventListener();

        getAll();
        btnGuardar.setOnClickListener(v -> {
            String id, name, last_name, address, age, phone, salary;
            id = txtId.getText().toString();
            name = txtName.getText().toString();
            last_name = txtLastName.getText().toString();
            address = txtAddress.getText().toString();
            age = txtAge.getText().toString();
            phone = txtPhone.getText().toString();
            salary = txtSalary.getText().toString();

            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(id, String.valueOf(create_json(name, last_name, address, age, phone, salary)));
            editor.apply();

            txtId.setText("");
            txtName.setText("");
            txtLastName.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            txtPhone.setText("");
            txtSalary.setText("");
            getAll();
        });

        btnBuscar.setOnClickListener(v -> {
            String id;
            id = txtId.getText().toString();
            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
            String defaultValue = "No existe";
            String highScore = sharedPref.getString(id, defaultValue);
            if (highScore != defaultValue) {
                try {
                    JSONObject json = new JSONObject(highScore);
                    txtName.setText(json.getString("name"));
                    txtLastName.setText(json.getString("last"));
                    txtAddress.setText(json.getString("address"));
                    txtAge.setText(json.getString("age"));
                    txtPhone.setText(json.getString("phone"));
                    txtSalary.setText(json.getString("salary"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "-> " + highScore, Toast.LENGTH_SHORT).show();
            }
        });

        btnActualizar.setOnClickListener(v -> {
            String id, name, last_name, address, age, phone, salary;
            id = txtId.getText().toString();
            name = txtName.getText().toString();
            last_name = txtLastName.getText().toString();
            address = txtAddress.getText().toString();
            age = txtAge.getText().toString();
            phone = txtPhone.getText().toString();
            salary = txtSalary.getText().toString();

            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(id, String.valueOf(create_json(name, last_name, address, age, phone, salary)));
            editor.apply();

            getAll();
            txtId.setText("");
            txtName.setText("");
            txtLastName.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            txtPhone.setText("");
            txtSalary.setText("");
        });

        btnEliminar.setOnClickListener(v -> {
            String id;
            id = txtId.getText().toString();
            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(id);
            editor.apply();
            getAll();
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
        });

    }

    private void getAll() {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        HashMap highScore = (HashMap) sharedPref.getAll();
        list_data = new ArrayList<>();

        for (Object value : highScore.values()) {

            String chain = value.toString();
            try {
                JSONObject json = new JSONObject(chain);
                String data = json.getString("name") + " " +
                        json.getString("last") + " " +
                        json.getString("address") + " " +
                        json.getString("age") + " años  " +
                        json.getString("phone") + "  $" +
                        json.getString("salary");

                Toast.makeText(this, "-> PROOF" + data, Toast.LENGTH_SHORT).show();
                list_data.add(data);
            } catch (JSONException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list_data);
        lstDatos.setAdapter(adapter);
    }

    private void eventListener()
    {
        lstDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    private JSONObject create_json(String name,String last_name,String address,String age,String phone,String salary)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("last",last_name);
            jsonObject.put("address",address);
            jsonObject.put("age",age);
            jsonObject.put("phone",phone);
            jsonObject.put("salary",salary);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}