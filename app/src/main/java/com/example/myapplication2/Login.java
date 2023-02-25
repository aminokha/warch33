package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.myapplication2.MainActivity.ANDROID_ID;
import static com.example.myapplication2.MainActivity.active_Key;

public class Login extends AppCompatActivity {
    EditText key_editText;
    TextView id_TextView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ANDROID_ID = getAndroidId();
        active_Key = ANDROID_ID.hashCode();
        key_editText = findViewById(R.id.key);
        id_TextView = findViewById(R.id.id);
        id_TextView.setText(ANDROID_ID);
        setClipboard(this, ANDROID_ID);

    }
    String getAndroidId() {
        return Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    private void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(this, "تم نسخ الرقم", Toast.LENGTH_LONG).show();
    }

    public void activate(View view) {
        String user_key = key_editText.getText().toString();
        if (user_key.equals(String.valueOf(active_Key))) {
            Toast.makeText(this, "تم تفعيل التطبيق بنجاح", Toast.LENGTH_LONG).show();
            dbHelper = new DBHelper(this);
            if (dbHelper.insertContact(ANDROID_ID, user_key)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "مفتاح التفعيل خاطئ", Toast.LENGTH_LONG).show();
        }
    }
}