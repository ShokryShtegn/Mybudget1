package com.example.mybudget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import com.example.mybudget.MainHelpers.GMailSender;
import com.example.mybudget.User;

import java.util.List;
import java.util.Random;

import kotlin.collections.AbstractMutableList;

public class SendEmailToResetPassword extends Activity {

    EditText etContent, etRecipient;
    Button btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email);
        //etContent = (EditText) findViewById(R.id.etContent);
        etRecipient = (EditText)findViewById(R.id.etRecipient);
        btnSend = (Button) findViewById(R.id.btnSend);
        final Context context = this;
        final DataBaseHandler db = new DataBaseHandler(context);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> data2 = db.readData();
                for(int i = 0; i <= (data2.size()-1); i++) {
                    if ((data2.get(i).getEmail().trim().equals(etRecipient.getText().toString().trim()))){
                        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                        String s = sendMessage();
                        Intent intent = new Intent(context, VerifyCode.class);
                        intent.putExtra("randomNumber", s);
                        intent.putExtra("etRecipient", etRecipient.getText().toString());
                        startActivity(intent);
                        break;
                    } else if(!data2.get(i).getEmail().trim().equals(etRecipient.getText().toString().trim())){
                        Toast.makeText(context, "This email doesn't exist.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private String sendMessage() {
        final int randomNumber = new Random().nextInt((99999999- 2678)) + 5678;// 5678 - 99999999
        final String code = Integer.toString(randomNumber);
        final ProgressDialog dialog = new ProgressDialog(SendEmailToResetPassword.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("gradmybudget@gmail.com", "mybudget123456789**");
                    sender.sendMail("My budget App",
                            (code),
                            "gradmybudget@gmail.com",
                            etRecipient.getText().toString());
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
        return code;
    }
}
