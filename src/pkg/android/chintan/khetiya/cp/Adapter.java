package pkg.android.chintan.khetiya.cp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;


    public Adapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.cell, null);

        TextView info = (TextView)vi.findViewById(R.id.transaction); // title
        TextView date = (TextView)vi.findViewById(R.id.date); // artist name
        TextView amount = (TextView)vi.findViewById(R.id.amount); // duration
        TextView debOrCred = (TextView)vi.findViewById(R.id.debitOrCredit); // duration


        HashMap<String, String> transactions = data.get(position);

        // Setting all values in listview
        info.setText(transactions.get(AndroidCamera.KEY_INFO));
        date.setText(transactions.get(AndroidCamera.KEY_DATE));
        amount.setText(transactions.get(AndroidCamera.KEY_AMOUNT));

        if(transactions.get(AndroidCamera.KEY_DEBORCRED) == "+") {

            debOrCred.setTextColor(Color.rgb(00, 200, 00));
        }
        else {
            debOrCred.setTextColor(Color.rgb(200, 00, 00));
        }
        debOrCred.setText(transactions.get(AndroidCamera.KEY_DEBORCRED));

        return vi;
    }
}