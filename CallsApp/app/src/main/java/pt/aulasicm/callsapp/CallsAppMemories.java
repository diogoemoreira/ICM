package pt.aulasicm.callsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CallsAppMemories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls_app_memories);

    }

    public void memoryOkClick(View v){
        EditText ed1 = (EditText) findViewById(R.id.memoryNameView);
        EditText ed2 = (EditText) findViewById(R.id.memoryPhoneView);
        Intent i = getIntent();
        i.putExtra("Name",ed1.getText().toString());
        i.putExtra("Phone",ed2.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
}