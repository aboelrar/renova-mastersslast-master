package com.example.lenovo.renova.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.lenovo.renova.R;
import com.example.lenovo.renova.library.NetworkChangeReceiver;
import com.example.lenovo.renova.library.progressdialog;

public class searchActivity extends AppCompatActivity {
    Button search;
    TextView skip;
    EditText editSearch;
    progressdialog progressdialog;
    NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editSearch=(EditText)findViewById(R.id.editSearch);
        progressdialog=new progressdialog();
        goToSearchResult();
        onChange();
        bottomNav();
    }

    public void goToSearchResult()
    {

        search=(Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.progressDialog(searchActivity.this);
               Intent intent=new Intent(searchActivity.this,search.class);
                String text=editSearch.getText().toString();
                intent.putExtra("text",text);
                startActivity(intent);

            }
        });
        skip=(TextView)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.progressDialog(searchActivity.this);
                Intent intent=new Intent(searchActivity.this,search.class);
                intent.putExtra("text","0");
                startActivity(intent);


            }
        });

    }
    public void onChange()
    {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final ViewSwitcher viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
                if(editSearch.length()>0){
                    viewSwitcher.setDisplayedChild(1); //or switcher.showPrevious()
                }else if(editSearch.getText().toString().isEmpty())
                {
                    viewSwitcher.setDisplayedChild(0);//or switcher.showPrevious()

                }
            }
        });
    }

    public void bottomNav() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.products) {
                    finish();
                    startActivity(new Intent(searchActivity.this, products.class));

                } else if (item.getItemId() == R.id.profile) {
                    finish();
                    startActivity(new Intent(searchActivity.this, profileMem.class));

                }else if(item.getItemId()==R.id.searchnav)
                {
                    finish();
                    startActivity(new Intent(searchActivity.this, searchActivity.class));

                }
                return true;
            }
        });
    }
}
