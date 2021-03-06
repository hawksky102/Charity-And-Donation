package com.example.donations.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.donations.R;

/**
 * Created by umert on 4/30/2019.
 */
public class CustomListAdapter extends ArrayAdapter {


    //to store the animal images
    //private final Bitmap[] imageIDarray;

    //to store the list of countries
    private final String[] typeArray;

    private final String[] descriptionArray;

    private final String[] dateArray;

    private final String[] amountArray;

    private  static Activity context;

    public CustomListAdapter(Activity context, String[] typeArrayParam, String[] fromArrayParam, String[] toArrayParam, String[] countArrayParam){

        super(context,R.layout.list_view_data , typeArrayParam);
        this.context=context;
        this.typeArray = typeArrayParam;
        this.descriptionArray = fromArrayParam;
        this.dateArray = toArrayParam;
        this.amountArray = countArrayParam;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view_data, null,true);
        rowView.setBackgroundColor(context.getColor(R.color.transparent));
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params2.setMargins(10,30,10,10);
        rowView.setLayoutParams(params2);

        //this code gets references to objects in the listview_row.xml file
        TextView description_list = (TextView) rowView.findViewById(R.id.description_list);
        TextView date_list = (TextView) rowView.findViewById(R.id.date_list);
        TextView type_list = (TextView) rowView.findViewById(R.id.type_list);
        TextView amount_list = (TextView) rowView.findViewById(R.id.amount_list);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);
        
        //this code sets the values of the objects to values from the arrays
        description_list.setText(descriptionArray[position]);
        date_list.setText(dateArray[position]);
        type_list.setText(typeArray[position]);
        amount_list.setText(amountArray[position]);



//        Bitmap resized = Bitmap.createScaledBitmap(imageIDarray[position], 50, 50, true);


        return rowView;

    };
}
