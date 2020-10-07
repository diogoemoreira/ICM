package pt.aulasICM.app01;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "pt.aulasICM.app01.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main); //the use of 'this' is unnecessary
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.i("MainActivity", "\t got onResume callback!");
    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.i("MainActivity", "\t got onPause callback!");
    }


    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Log.i("Button","\t send_message got tapped!");
        
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}