package pt.aulasicm.callsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String phone1;
    String phone2;
    String phone3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button)findViewById(R.id.memory1);
        Button b2 = (Button)findViewById(R.id.memory2);
        Button b3 = (Button)findViewById(R.id.memory3);



        //memories long click
        b1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(v.getContext(), CallsAppMemories.class);
                i.putExtra("memID","1");
                startActivityForResult(i, 1);
                return false;
            }
        });

        b2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(v.getContext(), CallsAppMemories.class);
                i.putExtra("memID","2");
                startActivityForResult(i, 1);
                return false;
            }
        });

        b3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(v.getContext(), CallsAppMemories.class);
                i.putExtra("memID","3");
                startActivityForResult(i, 1);
                return false;
            }
        });
    }

    public void numbersClick(View v){
        TextView tv = (TextView) findViewById(R.id.numberView);
        if(tv.length()<15) {
            Button b = (Button) v;
            tv.append(b.getText());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==1){
            String memID = data.getStringExtra("memID");
            String phone = data.getStringExtra("Phone");
            String contName = data.getStringExtra("Name");

            if(memID.equals("1")){
                Button b = (Button)findViewById(R.id.memory1);
                b.setText(contName);
                phone1 = phone;
            }
            else if(memID.equals("2")){
                Button b = (Button)findViewById(R.id.memory2);
                b.setText(contName);
                phone2 = phone;
            }
            else{
                Button b = (Button)findViewById(R.id.memory3);
                b.setText(contName);
                phone3 = phone;
            }


        }



    }

    public void clearClick(View v){
        TextView tv = (TextView) findViewById(R.id.numberView);
        if(tv.length()>0)
            tv.setText(tv.getText().subSequence(0,tv.length()-1));
    }

    public void callClick(View V){
        TextView tv = (TextView) findViewById(R.id.numberView);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+tv.getText().toString()));
        startActivity(intent);
    }

    public void memoriesClick1(View v){
        TextView tv = (TextView) findViewById(R.id.numberView);
        tv.setText(phone1);

    }
    public void memoriesClick2(View v){
        TextView tv = (TextView) findViewById(R.id.numberView);
        tv.setText(phone2);
    }
    public void memoriesClick3(View v){
        TextView tv = (TextView) findViewById(R.id.numberView);
        tv.setText(phone3);
    }

}