package com.example.osos;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ItemsAdapter itemsAdapter;
    ArrayList<ItemsModelClass> itemsList = new ArrayList<>();
    int itemPosition = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       FloatingActionButton fab = findViewById(R.id.floating_action_button);
       fab.setOnClickListener(view -> OpenPopUp());

        RecyclerView recyclerView =  findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemsAdapter = new ItemsAdapter(itemsList,this);
        recyclerView.setAdapter(itemsAdapter);

        itemsAdapter.onBind = ((holder, position,imageAdapter) ->
                holder.selectImages.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            itemPosition = position;
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Pictures"), 0);
        }));

    }

    @SuppressLint("NotifyDataSetChanged")
    private void OpenPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null, false);
        builder.setView(view);

        EditText Title = view.findViewById(R.id.title_et);

        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                if(!Title.getText().toString().trim().isEmpty()){
                    itemsList.add(new ItemsModelClass(Title.getText().toString().trim(),new ArrayList<>()));
                    itemsAdapter.notifyDataSetChanged();
                }
        });
        builder.setNegativeButton("Cancle", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.show();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            if(resultCode == Activity.RESULT_OK) {
                ArrayList<Uri> imageList =  new ArrayList<>();
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for(int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageList.add(imageUri);
                    }
                    UpdateList(imageList);
                }else if(data.getData() != null) {
                    Toast.makeText(this, "Please select more than 1 images", Toast.LENGTH_SHORT).show();
                    Uri imgaeUri = Uri.fromFile(new File(data.getData().getPath()));
                    imageList.add(imgaeUri);
                    UpdateList(imageList);
                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void UpdateList(ArrayList<Uri> imageList) {
        String  title = itemsList.get(itemPosition).title;
        itemsList.set(itemPosition,new ItemsModelClass(title,imageList));
        itemsAdapter.notifyDataSetChanged();
    }


}