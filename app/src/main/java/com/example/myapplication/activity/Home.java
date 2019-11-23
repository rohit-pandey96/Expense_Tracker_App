package com.example.myapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.AddExpense;
import com.example.myapplication.modelclass.Expense;
import com.example.myapplication.servicesimpl.ExpenseImpl;
import com.example.myapplication.servicesinterface.ExpenseInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Home extends AppCompatActivity {
    List<Expense> expenseList = new ArrayList<>();
    EditText maxlimitedt;
    Button maxlimitbtn;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    public SimpleAdapter simpleAdapter;
    public ListView listView;
    ExpenseInterface expenceImpl;
    Integer totalspent=0;
    TextView spenttxt,availabletxt,maxlimittxt;
    int maxlimit=10000;
    public Integer efood=0,etravel=0,egroceries=0,eothers=0;
    Dialog dialog;
    PieChartView pieChartView;
    List pieData = new ArrayList();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listView = findViewById(R.id.list);

        spenttxt = findViewById(R.id.spenttxt);
        availabletxt =findViewById(R.id.availabletxt);
        registerForContextMenu(listView);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.max_limit_home_dialog);
        dialog.setTitle("This is maxlimit dialog");
        maxlimitedt = dialog.findViewById(R.id.maxlimitedt);
        maxlimitbtn = dialog.findViewById(R.id.maxlimitsavebtn);
         pieChartView = findViewById(R.id.chart);
        maxlimittxt = findViewById(R.id.maxlimittext);

        maxlimitbtn.setEnabled(false);
        maxlimitedt.addTextChangedListener(textWatcher);



        maxlimitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxlimit = Integer.parseInt(maxlimitedt.getText().toString());
                maxlimittxt.setText(String.valueOf(maxlimit));
                availabletxt.setText(String.valueOf(maxlimit-totalspent));
                dialog.dismiss();

            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddExpense.class));


            }
        });
        loadItem();
    }

    @Override
    protected void onStart() {
        list.clear();

        super.onStart();
        loadItem();
    }


    private TextWatcher textWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(TextUtils.isEmpty(maxlimitedt.getText().toString())){
                maxlimitedt.setError("Field required");
                maxlimitbtn.setEnabled(false);
            }else{
                maxlimitbtn.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(TextUtils.isEmpty(maxlimitedt.getText().toString())){
                maxlimitedt.setError("Field required");
                maxlimitbtn.setEnabled(false);
            }else{
                maxlimitbtn.setEnabled(true);
            }

        }
    };

    public void loadItem(){
        totalspent = 0;
        efood=etravel=egroceries=eothers=0;
        expenceImpl = new ExpenseImpl();
        expenseList = expenceImpl.getList(getApplicationContext());
        if(expenseList!=null) {
            HashMap<String, String> item;
            for (Expense e : expenseList) {
                totalspent = totalspent + Integer.valueOf(e.getSpend());
                switch (e.getExpense_type()){
                    case "Food":
                        efood = efood + Integer.parseInt(e.getSpend());
                        break;
                    case "Travel":
                        etravel = etravel + Integer.parseInt(e.getSpend());
                        break;
                    case "Groceries":
                        egroceries = egroceries + Integer.parseInt(e.getSpend());
                        break;
                    case "Others":
                        eothers =eothers +Integer.parseInt(e.getSpend());
                        break;
                }



                item = new HashMap<String, String>();
                item.put("id",String.valueOf(e.getExpense_id()));
                item.put("place", e.getPlace());
                item.put("date", e.getDate());
                item.put("day", e.getDay());
                item.put("time", e.getTime());
                item.put("expense_type",e.getExpense_type());
                item.put("spend", e.getSpend());
                list.add(item);



            }
            updatePieChart();





            maxlimittxt.setText(String.valueOf(maxlimit));
            availabletxt.setText(String.valueOf(maxlimit-totalspent));
            spenttxt.setText(String.valueOf(totalspent));
            setAdapter();

        }else{
            Toast.makeText(this,"Add Expense!",Toast.LENGTH_SHORT).show();
        }

    }
    public void setAdapter(){
        simpleAdapter = new SimpleAdapter(this,list,R.layout.list_item,new String[]{"place","date","day","time","spend"}
        ,new int[]{R.id.placetxt,R.id.datetxt,R.id.daytxt,R.id.timetxt,R.id.spendtxt});

        listView.setAdapter(simpleAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         switch (item.getItemId()){
             case R.id.limit:
                 maxLimitDialog();
                 break;



         }
         return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.delete:
                // remove stuff here
                int s=info.position;

                HashMap<String,String> hashMap = list.get(s);
                expenceImpl = new ExpenseImpl();
                expenceImpl.deleteExpense(Integer.parseInt(hashMap.get("id")),this);
                totalspent=totalspent-Integer.parseInt(list.get(s).get("spend"));
                switch (list.get(s).get("expense_type")){
                    case "Food":
                        efood = efood - Integer.parseInt(list.get(s).get("spend"));
                        break;
                    case "Travel":
                        etravel = etravel - Integer.parseInt(list.get(s).get("spend"));
                        break;
                    case "Groceries":
                        egroceries = egroceries - Integer.parseInt(list.get(s).get("spend"));
                        break;
                    case "Others":
                        eothers =eothers - Integer.parseInt(list.get(s).get("spend"));
                        break;

                }

                spenttxt.setText(String.valueOf(totalspent));
                availabletxt.setText(String.valueOf(maxlimit-totalspent));
                list.remove(s);
                simpleAdapter.notifyDataSetChanged();
                if(list!=null) {
                    updatePieChart();
                }else{
                    pieData.clear();
                }


                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void maxLimitDialog(){

        dialog.show();
    }

    public void updatePieChart() {
        pieData.clear();

        PieChartData pieChartData;
        if (totalspent != 0) {
        pieData.add(new SliceValue(((efood)), Color.parseColor("#008080")).setLabel(""));
        pieData.add(new SliceValue((etravel),  Color.parseColor("#800000")).setLabel(""));
        pieData.add(new SliceValue((egroceries),  Color.parseColor("#808000")).setLabel(""));
        pieData.add(new SliceValue((eothers),  Color.parseColor("#4682B4")).setLabel(""));
        }else{
        pieData.add(new SliceValue(1, Color.parseColor("#E0E0E0")).setLabel(""));
        }
            pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("Expenses").setCenterText1FontSize(25)
                    .setCenterText1Color(Color.parseColor("#E0E0E0"));
            pieChartView.setPieChartData(pieChartData);




    }




}
