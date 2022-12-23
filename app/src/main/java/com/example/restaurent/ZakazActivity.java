package com.example.restaurent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;

public class ZakazActivity extends AppCompatActivity {

    public static ListView zakazlist;
    private TextView total;
    private String totalSum;
    private Button againScan, setZacaz;
    int summa;
    public static ArrayList<String> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakaz);

        zakazlist = (ListView) findViewById(R.id.zakazList);
        total = (TextView) findViewById(R.id.TotalSum);
        againScan = (Button) findViewById(R.id.againScan);
        setZacaz = (Button) findViewById(R.id.setZakaz);

        products = ScanActivity.product;
        int count = products.size();
        if(count == 0){
            setZacaz.setVisibility(View.INVISIBLE);
        }
        else{
            setZacaz.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        zakazlist.setAdapter(adapter);
        summa = ScanActivity.sum;
        totalSum = "Total: " + String.valueOf(summa) + "$";
        total.setText(totalSum);
        adapter.notifyDataSetChanged();

        zakazlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                AlertDialog.Builder quitDialog = new AlertDialog.Builder(ZakazActivity.this);
                quitDialog.setTitle("Вы уверены что хотите удалить это блюдо?");
                quitDialog.setIcon(R.drawable.sticker);
                quitDialog.setCancelable(false);
                quitDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        products.remove(index);
                        int c = products.size();
                        if(c == 0){
                            setZacaz.setVisibility(View.INVISIBLE);
                        }
                        else{
                            setZacaz.setVisibility(View.VISIBLE);
                        }
                        summa = 0;
                        totalSum = "";
                        int pric = 0;
                        for(String prod : products){
                            String[] sstr = new String[prod.length()];
                            int i = 0;
                            for(String res : prod.split(" ")){
                                sstr[i] = res;
                                i++;
                            }
                            String s = sstr[3];
                            s = StringUtils.substring(s, 0, s.length()-1);
                            pric = Integer.parseInt(s);
                            summa += pric;
                        }
                        totalSum = "Total: " + String.valueOf(summa) + "$";
                        total.setText(totalSum);
                        Toast.makeText(ZakazActivity.this, "Блюдо успешно удалено из вашего заказа", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });
                quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                quitDialog.show();
                return true;
            }
        });

        zakazlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                String str = zakazlist.getItemAtPosition(index).toString();
                String[] sstr = new String[str.length()];
                int i = 0;
                for(String res : str.split(" ")){
                    sstr[i] = res;
                    i++;
                }
                String st = sstr[2];
                String[] ssstr = new String[st.length()];
                int ig = 0;
                for(String resul : st.split("\n")){
                    ssstr[ig] = resul;
                    ig++;
                }
                String name = sstr[1] + " " + ssstr[0];
                if(name.equals("Cheese burger")){
                    Intent intent = new Intent(ZakazActivity.this, InfoActivity.class);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.chesb);
                    b = Bitmap.createScaledBitmap(b, 350, 350, true);
                    intent.putExtra("img_food", b);
                    startActivity(intent);
                }
                if(name.equals("Double cheese")){
                    Intent intent = new Intent(ZakazActivity.this, InfoActivity.class);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.doubc);
                    b = Bitmap.createScaledBitmap(b, 350, 350, true);
                    intent.putExtra("img_food", b);
                    startActivity(intent);
                }
                if(name.equals("Bacon burger")){
                    Intent intent = new Intent(ZakazActivity.this, InfoActivity.class);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.baconb);
                    b = Bitmap.createScaledBitmap(b, 350, 350, true);
                    intent.putExtra("img_food", b);
                    startActivity(intent);
                }
                if(name.equals("French fries")){
                    Intent intent = new Intent(ZakazActivity.this, InfoActivity.class);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.frenfri);
                    b = Bitmap.createScaledBitmap(b, 350, 350, true);
                    intent.putExtra("img_food", b);
                    startActivity(intent);
                }
                if(name.equals("Cheese fries")){
                    Intent intent = new Intent(ZakazActivity.this, InfoActivity.class);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.chesfri);
                    b = Bitmap.createScaledBitmap(b, 350, 350, true);
                    intent.putExtra("img_food", b);
                    startActivity(intent);
                }
                if(name.equals("Branded milkshake")){
                    Intent intent = new Intent(ZakazActivity.this, InfoActivity.class);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.milkshake);
                    b = Bitmap.createScaledBitmap(b, 350, 350, true);
                    intent.putExtra("img_food", b);
                    startActivity(intent);
                }
            }
        });

        againScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZakazActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        setZacaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZakazActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ZakazActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}