package com.example.administrator.accounapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener,CategoryRecycleAdapter.OnCategoryClickListen {

    private String TAG = "AddRecordActivity";
    private String userInput = "";
    private TextView amountText;
    private RecyclerView recyclerView;
    private CategoryRecycleAdapter adapter;
    RecordBean record = new RecordBean();
    private  boolean inEdit = false;

    private String category = "Genral";
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
    private String remark = category;
    private EditText editView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);

        amountText = (TextView) findViewById(R.id.textView_amount);
        editView = (EditText) findViewById(R.id.editText);
        editView.setText(remark);

        hanleDot();
        hanleTypeChange();
        hanleBackspace();
        hanleDone();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CategoryRecycleAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();

        adapter.setOnCategoryClickListen(this);
        //TODO
        RecordBean record = (RecordBean) getIntent().getSerializableExtra("record");

        if (record != null) {
//            editView.setText(record.getRemark());
//            Log.i(TAG,"getIntent  " + record.getRemark());
            inEdit = true;
            this.record = record;
        }

    }

    private void hanleDot(){
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userInput.contains("."))
                {
                    //"."  ->  "0."
                    if(userInput.equals("")){
                        userInput += "0.";
                    }else {
                        userInput += ".";
                    }
                }
                updateAmountText();
            }
        });
    }

    private void hanleTypeChange(){
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
                    type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                }else {
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                }

                adapter.changeType(type);
                category = adapter.getSelected();
                editView.setText(category);

            }
        });
    }
    private void hanleBackspace(){
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"backspace");
                if(userInput.length()>0) {
                    if(userInput.charAt(userInput.length()-1) == '.'){
                        userInput = userInput.substring(0, userInput.length() - 1);
                    }
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                updateAmountText();
            }
        });
    }
    private void hanleDone(){
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {

            private double amount = 0.0;

            @Override
            public void onClick(View v) {
                if(userInput.length()>0)
                {
                    amount = Double.valueOf(userInput);

                    record.setAmount(amount);
                    record.setType((type == RecordBean.RecordType.RECORD_TYPE_EXPENSE)?1:2);
                    record.setCategory(adapter.getSelected());
                    record.setRemark(editView.getText().toString());

                    if(inEdit){
                        GlobalUtil.getInstance().databaseHelper.editRecord(record.getUuid(),record);
                    }else {
                        GlobalUtil.getInstance().databaseHelper.addRecord(record);
                    }
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Amount is 0!!!",Toast.LENGTH_SHORT).show();
                }
//                Log.i(TAG,amount + "");
            }
        });
    }


    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        String input = button.getText().toString();

        if(userInput.contains(".")){
            if(userInput.split("\\.").length == 1 || userInput.split("\\.")[1].length() < 2){
                userInput += input;
            }
        }else {
            userInput += input;
        }
        Log.i(TAG + "1",userInput);
        updateAmountText();
    }


    private void updateAmountText(){

        if(userInput.contains(".")){
            if(userInput.split("\\.").length == 1){
                amountText.setText(userInput + "00");
            }
            else if (userInput.split("\\.")[1].length() == 1){
                amountText.setText(userInput + "0");
            }else if (userInput.split("\\.")[1].length() == 2){
                amountText.setText(userInput);
            }
        }
        else{
            if(userInput.equals("")){
                amountText.setText("0.00");
            }else{
                amountText.setText(userInput + ".00");
            }
        }
        Log.i(TAG + "2",userInput);

    }


    @Override
    public void onClick(String categoty) {
        this.category = categoty;
        Log.i(TAG,"Category:" + categoty);
        editView.setText(category);
        Log.i(TAG,adapter.getItemCount() + "");
    }
}
