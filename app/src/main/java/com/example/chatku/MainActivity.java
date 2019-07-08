package com.example.chatku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    EditText chat;
    Socket socket;
    ArrayList<String> chat_data=new ArrayList<>();
    ArrayList<String> pengirim_=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        adapter= new chat_adapter(chat_data,pengirim_);
        recyclerView.setAdapter(adapter);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        chat=findViewById(R.id.chat);
        try{
            socket= IO.socket("http://192.168.1.20:3000");
        }
        catch (URISyntaxException e){
            Log.d("error","onCreate"+e.toString());
        }
        socket.connect();
        socket.on("pesan",handling);
    }

    private Emitter.Listener handling=new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tambahpesan(args[0].toString(),null);
                }
            });
        }
    };

    public void kirim(View v) {
        kirim_pesan();
    }
    public void kirim_pesan()
    {
        String pesan=chat.getText().toString().trim();
        chat.setText("");
        tambahpesan(pesan,"me");
        socket.emit("pesan",pesan);
        Log.d("deneme",pengirim_.toString()+"\nchat data"+chat_data.toString());
    }

    private void tambahpesan(String pesan, String pengirim) {
        chat_data.add(pesan);
        this.pengirim_.add(pengirim);
        adapter.notifyItemInserted(chat_data.size()-1);
        recyclerView.smoothScrollToPosition(chat_data.size()-1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
