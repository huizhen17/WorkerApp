package com.example.workerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(3);
    boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        integerDeque.push(R.id.nav_home);
        loadFragment(new HomeFragment());

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(integerDeque.contains(id)){
                if(id== R.id.nav_home){
                    if(integerDeque.size()!=0){
                        if(flag){
                            integerDeque.addFirst(R.id.nav_home);
                            flag = false;
                        }
                    }
                }
                integerDeque.remove(id);
            }
            integerDeque.push(id);
            loadFragment(getFragment(item.getItemId()));
            return true;
        }
    });

}

    private Fragment getFragment(int itemId) {
        switch (itemId){
            case  R.id.nav_home:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return new HomeFragment();
            case  R.id.nav_his:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new HistoryFragment();
            case  R.id.nav_acc:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new MyAccFragment();
        }

        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment,fragment,fragment.getClass().getSimpleName())
                .commitNow();
    }

    @Override
    public void onBackPressed() {
        integerDeque.pop();
        if(!integerDeque.isEmpty()){
            loadFragment(getFragment(integerDeque.peek()));
        }else{
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
