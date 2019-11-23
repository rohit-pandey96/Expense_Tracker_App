package com.example.myapplication.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.modelclass.Expense;
import com.example.myapplication.servicesimpl.ExpenseImpl;
import com.example.myapplication.servicesinterface.ExpenseInterface;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddExpense extends AppCompatActivity {
    LocalDate date = LocalDate.now();
    DayOfWeek dow = date.getDayOfWeek();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
    String strDate =mdformat.format(calendar.getTime());
    Date c = Calendar.getInstance().getTime();

    String expense_type_selected;
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c);

    private EditText place,spend;
    private Button addbtn;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        place = findViewById(R.id.add_place);
        spend = findViewById(R.id.add_expense);
        addbtn = findViewById(R.id.add_expense_btn);
        addbtn.setEnabled(false);
        spinner = findViewById(R.id.expense_type_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.
                createFromResource(this,R.array.expense_type,R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expense_type_selected=spinner.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                expense_type_selected = spinner.getSelectedItem().toString();

            }
        });


        place.addTextChangedListener(textWatcher);
        spend.addTextChangedListener(textWatcher);
        place.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkForTheValidation();
                return false;
            }
        });
        spend.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                checkForTheValidation();
                return false;
            }
        });



        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Expense expense = new Expense();
                expense.setPlace(place.getText().toString());
                expense.setSpend(spend.getText().toString());
                expense.setDay(String.valueOf(dow));
                expense.setTime(String.valueOf(strDate));
                expense.setDate(formattedDate);
                expense.setExpense_type(expense_type_selected);
                ExpenseInterface expenseInterface = new ExpenseImpl();
                expenseInterface.addExpense(expense,getApplicationContext());
                finish();
            }
        });

    }
    private TextWatcher textWatcher = new TextWatcher(){

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(checkForTheValidation()){
                addbtn.setEnabled(true);
            }


        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(checkForTheValidation()){
                addbtn.setEnabled(true);
            }

        }
    };

    boolean checkForTheValidation() {
        if (TextUtils.isEmpty(place.getText().toString())) {
            place.setError("Field required");
            addbtn.setEnabled(false);
            return false;


        }
        if (TextUtils.isEmpty(spend.getText().toString())) {
            spend.setError("Field required");
            addbtn.setEnabled(false);
            return false;


        }
        return true;
    }

}

