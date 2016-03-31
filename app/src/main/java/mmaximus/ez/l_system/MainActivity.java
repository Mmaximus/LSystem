package mmaximus.ez.l_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editAxiom;
    EditText editNewF;
    EditText editQ;
    EditText editN;
    String newF, axiom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editAxiom = (EditText) findViewById(R.id.editTextAxiom);
        editNewF = (EditText) findViewById(R.id.editTextNewF);
        editQ = (EditText) findViewById(R.id.editTextQ);
        editN = (EditText) findViewById(R.id.editTextN);
        editN.setText("1");


    }

    public void OnClickKust(View view)
    {
        editAxiom.setText("F");
        editNewF.setText("-F+F+[+F-F-]-[-F+F+F]");
        editQ.setText("22.5");
    }
    public void OnClickSnejinka(View view)
    {
        editAxiom.setText("[F]+[F]+[F]+[F]+[F]+[F]");
        editNewF.setText("F[+FF][-FF]FF[+F][-F]FF");
        editQ.setText("60");
    }

    public void OnClickButtonGO(View view)
    {
        newF = editNewF.getText().toString();
        axiom = editAxiom.getText().toString();
        String temp = "";
        for(int i = 0; i < Integer.parseInt(editN.getText().toString()); i++)
        {
            temp = "";
            for (int j = 0; j < axiom.length(); j++)
            {
                if(axiom.charAt(j) == 'F') temp = temp + newF;
                else temp = temp + axiom.charAt(j);
            }
            axiom = temp;

        }
        Intent intent = new Intent(MainActivity.this, DrawActivity.class);
        intent.putExtra("axiom", axiom);
        intent.putExtra("q", editQ.getText().toString());
        startActivity(intent);


    }
}
