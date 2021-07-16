package com.barafael.dodecaControl;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CommunicateActivity extends AppCompatActivity {

    private TextView connectionText, messagesView;
    private EditText messageBox;
    private Button sendButton, connectButton;
    private Button darkenButton, brightenButton;

    private Button actionAButton, actionBButton, actionCButton;

    private CommunicateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Setup our activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        // Enable the back button in the action bar if possible
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Setup our ViewModel
        viewModel = new ViewModelProvider(this).get(CommunicateViewModel.class);

        // This method return false if there is an error, so if it does, we should close.
        if (!viewModel.setupViewModel(getIntent().getStringExtra("device_name"), getIntent().getStringExtra("device_mac"))) {
            finish();
            return;
        }

        // Setup our Views
        connectionText = findViewById(R.id.communicate_connection_text);
        messagesView = findViewById(R.id.communicate_messages);
        messageBox = findViewById(R.id.communicate_message);
        sendButton = findViewById(R.id.communicate_send);
        connectButton = findViewById(R.id.communicate_connect);
        darkenButton = findViewById(R.id.darken_button);
        brightenButton = findViewById(R.id.brighten_button);

        actionAButton = findViewById(R.id.action_a_button);
        actionBButton = findViewById(R.id.action_b_button);
        actionCButton = findViewById(R.id.action_c_button);

        // Start observing the data sent to us by the ViewModel
        viewModel.getConnectionStatus().observe(this, this::onConnectionStatusChanged);
        viewModel.getDeviceName().observe(this, name -> setTitle(getString(R.string.device_name_format, name)));
        viewModel.getMessages().observe(this, message -> {
            if (TextUtils.isEmpty(message)) {
                message = getString(R.string.no_messages);
            }
            messagesView.setText(message);
        });
        viewModel.getMessage().observe(this, message -> {
            // Only update the message if the ViewModel is trying to reset it
            if (TextUtils.isEmpty(message)) {
                messageBox.setText(message);
            }
        });

        // Setup the send button click action
        sendButton.setOnClickListener(v -> viewModel.sendMessage(messageBox.getText().toString()));

        darkenButton.setOnClickListener(v -> viewModel.sendMessage("d"));
        brightenButton.setOnClickListener(v -> viewModel.sendMessage("i"));

        actionAButton.setOnClickListener(v -> viewModel.sendMessage("a"));
        actionBButton.setOnClickListener(v -> viewModel.sendMessage("b"));
        actionCButton.setOnClickListener(v -> viewModel.sendMessage("c"));
    }

    // Called when the ViewModel updates us of our connectivity status
    private void onConnectionStatusChanged(CommunicateViewModel.ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            case CONNECTED:
                connectionText.setText(R.string.status_connected);
                messageBox.setEnabled(true);
                sendButton.setEnabled(true);
                connectButton.setEnabled(true);
                connectButton.setText(R.string.disconnect);
                connectButton.setOnClickListener(v -> viewModel.disconnect());
                darkenButton.setEnabled(true);
                brightenButton.setEnabled(true);
                actionAButton.setEnabled(true);
                actionBButton.setEnabled(true);
                actionCButton.setEnabled(true);
                break;

            case CONNECTING:
                connectionText.setText(R.string.status_connecting);
                messageBox.setEnabled(false);
                sendButton.setEnabled(false);
                connectButton.setEnabled(false);
                connectButton.setText(R.string.connect);
                darkenButton.setEnabled(false);
                brightenButton.setEnabled(false);
                actionAButton.setEnabled(false);
                actionBButton.setEnabled(false);
                actionCButton.setEnabled(false);
                break;

            case DISCONNECTED:
                connectionText.setText(R.string.status_disconnected);
                messageBox.setEnabled(false);
                sendButton.setEnabled(false);
                connectButton.setEnabled(true);
                connectButton.setText(R.string.connect);
                connectButton.setOnClickListener(v -> viewModel.connect());
                darkenButton.setEnabled(false);
                brightenButton.setEnabled(false);
                actionAButton.setEnabled(false);
                actionBButton.setEnabled(false);
                actionCButton.setEnabled(false);
                break;
        }
    }

    // Called when a button in the action bar is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// If the back button was pressed, handle it the normal way
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Called when the user presses the back button
    @Override
    public void onBackPressed() {
        // Close the activity
        finish();
    }
}
