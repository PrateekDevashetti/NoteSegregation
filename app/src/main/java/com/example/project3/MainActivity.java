package com.example.project3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Cell> allFilesPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for the storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
        }else{
            // show the images
            showImages();
        }
    }
    // shows the images on the screen
    private void showImages(){
        // this is the folder with all the images
        String path = Environment.getExternalStorageDirectory()
                                    .getAbsolutePath()+ " /Images/";
        allFilesPath = new ArrayList<>();
        allFilesPath = listAllFiles(path);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);
        // THIS MAKES  THE LIST OF THE IMAGES
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Cell> cells = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(),cells);
        recyclerView.setAdapter(adapter);
    }

    //prepare the images for the list
    private ArrayList<Cell> prepareData(){
        ArrayList<Cell> allImages = new ArrayList<>();
        for (Cell c : allFilesPath){
            Cell cell = new Cell();
            cell.setTitle(c.getTitle());
            cell.setPath(c.getPath());
            allImages.add(cell);
        }
        return allImages;
    }

    //load all the files from the folder
    private List<Cell> listAllFiles(String pathName){
        List<Cell>allFiles = new ArrayList<>();
        File file = new File(pathName);
        File[] files = file.listFiles();
        if (files != null){
            for (File f : files){
                Cell cell = new Cell();
                cell.setTitle(f.getName());
                cell.setPath(f.getAbsolutePath());
                allFiles.add(cell);
            }
        }
        return allFiles;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //SHOW THE IMAGES
                showImages();
            }else{
                Toast.makeText(this,"Permission not granted !",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
