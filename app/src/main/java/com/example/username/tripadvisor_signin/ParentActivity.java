package com.example.username.tripadvisor_signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ParentActivity extends AppCompatActivity {

    /** The current view */
    private int contentView = R.layout.activity_parent;

    /** Account information */
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        account = new Account();

        final ListView destinationsListView = (ListView) findViewById(R.id.dest_list);

        String[] destinationsListValues = new String[account.destinations.size()];

        for (int i = 0; i < account.destinations.size(); i++) {
            destinationsListValues[i] = new String(account.destinations.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, destinationsListValues);

        destinationsListView.setAdapter(adapter);

        destinationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        (String) destinationsListView.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dest_list:
                // User chose the "Destinations" item, show the destinations list in the UI...
                if (contentView != R.layout.activity_parent) {
                    Intent intent = new Intent(this, ParentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                return true;

            case R.id.action_route_map:
                // User chose the "Map" action, show the current route
                if (contentView != R.layout.activity_map) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Account", account);
                    Intent intent = new Intent(this, MapActivity.class);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                return true;

            case R.id.action_preferences_list:
                //User chose the "Preferences" action, show graph of preferences
                if (contentView != R.layout.activity_preferences) {
                    Intent intent = new Intent(this, PreferencesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                return true;

            case R.id.action_friends_list:
                //User chose the "Friends" action, show list of trip friends
                if (contentView != R.layout.activity_friends) {
                    Intent intent = new Intent(this, FriendsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                return true;

            case R.id.action_account_profile:
                //User chose the "Account" action, show profile/login page
                if (contentView != R.layout.activity_login) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}