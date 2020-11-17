package com.example.workerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(3);
    boolean flag = true;
    public static String userName,phoneNo,email;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID = mAuth.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DocumentReference documentReference = db.collection("workerDetail").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error==null){
                    if(value.exists()){
                        userName = value.getString("name");
                        phoneNo = value.getString("phone");
                        email = value.getString("email");
                    }
                }
            }
        });

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
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MainActivity.userName = userName;
    }

    public static String getPhoneNo() {
        return phoneNo;
    }

    public static void setPhoneNo(String phoneNo) {
        MainActivity.phoneNo = phoneNo;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        MainActivity.email = email;
    }
}
