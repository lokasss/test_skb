package fapp.bush.skb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

    }
    public void click(View view)
    {
        String expression = editText.getText().toString();
        //Toast.makeText(this, S1, Toast.LENGTH_SHORT).show();
        TestParser pm = new TestParser();

        try{
            textView.setText( expression + "= " + pm.Parse(expression) );
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
