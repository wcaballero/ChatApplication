package com.williamcaballero.chatapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    // grab edit text
    private EditText editMessage;
    // Firebase Database to send message from user
    private DatabaseReference mDatabase;
    // grab messages from db
    private RecyclerView mMessageList;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grab message from user
        editMessage = (EditText) findViewById(R.id.editMessageE);
        // reference to db
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Messages");

        mMessageList = (RecyclerView) findViewById(R.id.messageRec);

        // display the text with a linear layout
        mMessageList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // allows for message list ot display from end
        linearLayoutManager.setStackFromEnd(true);
        mMessageList.setLayoutManager(linearLayoutManager);
    }

    public void sendBtnClicked(View view){

        final String messageValue = editMessage.getText().toString().trim();
        // check to make message is not empty
        if (!TextUtils.isEmpty(messageValue)) {
            final DatabaseReference newPost = mDatabase.push();
            newPost.child("content").setValue(messageValue);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // populates the screen with information stored in the database
        FirebaseRecyclerAdapter <Message, MessageViewHolder> FBRA = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(
                Message.class,
                R.layout.singlemessagelayout,
                MessageViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
               viewHolder.setContent(model.getContent());
            }
        };

        mMessageList.setAdapter(FBRA);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public MessageViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setContent(String content) {
            TextView message_content = (TextView) mView.findViewById(R.id.messageText);
            message_content.setText(content);
        }
    }
}
