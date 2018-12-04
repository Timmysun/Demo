package com.timmy.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.timmy.demo.model.DataPool;
import com.timmy.demo.ui.main.MainViewModel;
import com.timmy.demo.ui.main.SplashFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SplashFragment.newInstance())
                    .commitNow();
        }

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Log.e("Timmy", "activity" + viewModel);
        viewModel.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, int propertyId) {
                Log.e("Timmy", "MainAcitivty onPropertyChanged " + propertyId);
                if (propertyId == BR.toastMessage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    ((DataPool) sender).getToastMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}
