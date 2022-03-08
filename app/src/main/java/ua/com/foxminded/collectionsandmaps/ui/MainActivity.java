package ua.com.foxminded.collectionsandmaps.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ua.com.foxminded.collectionsandmaps.R;
import ua.com.foxminded.collectionsandmaps.models.DaggerBenchmarkComponent;

public class MainActivity extends AppCompatActivity {

    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.setAppComponent(DaggerBenchmarkComponent.create());

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager2 pager2 = findViewById(R.id.viewPager);

        pager2.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getLifecycle()));

        tabLayoutMediator = new TabLayoutMediator(tabLayout, pager2, (tab, position) ->
                tab.setText(getResources().getStringArray(R.array.collectionsMapsTXT)[position]));
        tabLayoutMediator.attach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tabLayoutMediator.detach();
    }
}
