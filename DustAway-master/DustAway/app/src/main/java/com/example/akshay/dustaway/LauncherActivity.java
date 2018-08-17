package com.example.akshay.dustaway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class LauncherActivity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.launcher_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.media_route_menu_item){
            Intent i = new Intent(this,LeaderBoard.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);


        final ConversationService myConversationService =
                new ConversationService(
                        "2017-05-26",
                        getString(R.string.username),
                        getString(R.string.password)
                );


        final ChatView chatView = (ChatView) findViewById(R.id.chat_view);
        chatView.addMessage(new ChatMessage("Message received", System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                final String inputText = chatMessage.getMessage();
                Log.d("msg",inputText);
                MessageRequest request = new MessageRequest.Builder()
                        .inputText(inputText)
                        .build();

                myConversationService
                        .message(getString(R.string.workspace), request)
                        .enqueue(new ServiceCallback<MessageResponse>() {
                            @Override
                            public void onResponse(MessageResponse response) {
                                // More code here
                                final String outputText = response.getText().get(0);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        chatView.addMessage(new ChatMessage(outputText, System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Exception e) {}
                        });



                return true;
            }
        });

        chatView.setTypingListener(new ChatView.TypingListener() {
            @Override
            public void userStartedTyping() {

            }

            @Override
            public void userStoppedTyping() {

            }
        });



    }


    public void camera(View view)
    {
        Intent i = new Intent(this, cameraActivity.class);
        startActivity(i);
    }

    public void dustbin(View view)
    {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void cycle(View view)
    {
        Intent i = new Intent(this, cycleActivity.class);
        startActivity(i);
    }


}