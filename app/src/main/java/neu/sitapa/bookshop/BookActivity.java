package neu.sitapa.bookshop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookActivity extends AppCompatActivity {

    //Explicit
    private TextView textView;
    private ListView listView;
    private static final String urlJON = "http://swiftcodingthai.com/neu/get_product.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //Bind Widget
        textView = (TextView) findViewById(R.id.textView6);
        listView = (ListView) findViewById(R.id.listView);

        //Show View
        String strName = getIntent().getStringExtra("Name");
        textView.setText(strName);

        //Create ListView
        createListView();


    }   //Main Method

    private class SynProduct extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {


                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {

                return null;

            }



        }   //doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("22June", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                String[] iconStrings=new String[jsonArray.length()];
                String[] nameStrings=new String[jsonArray.length()];
                String[] priceStrings=new String[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    iconStrings[i]=jsonObject.getString("Cover");
                    nameStrings[i]=jsonObject.getString("Name");
                    priceStrings[i]=jsonObject.getString("Price");

                }   //for

                BaseAdapter baseAdapter=new BookAdapter(BookActivity.this,iconStrings,nameStrings,priceStrings);
                listView.setAdapter(baseAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }   //onPost


    }   //Class


    private void createListView() {

        SynProduct synProduct = new SynProduct();
        synProduct.execute();

    }
}   //Main Class
