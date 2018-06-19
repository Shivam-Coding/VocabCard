package com.example.shivamawasthi.vocabcard;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MyActivity extends Activity implements ActionBar.TabListener, AdapterView.OnItemSelectedListener {

    String name ="meta",name1;
    File file,path,path1,file1,file2;
    List meta;
    List[] l1;
    Iterator r ,r1;
    int strt,end,count,in = -1,curr;
    TextView t,t1,t5,ct,t7, tt1,tt2,tt3,tt4,tt5;
    Button b,butt,butt1,butt2,butt3;
    EditText et,et1;
    EditText tq,tq1;
    TextView tv,tv1;
    String sw,word_edit,mean_edit;
    boolean desion;
    private Spinner spinner1,spinner2;
    Context context;
    String deviceId;
    ScrollView scroll;




    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    private InterstitialAd interstitial;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        context = getApplicationContext();

        scroll = new ScrollView(this);

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-2785868738816505/8452989277");
        // Create ad request.
        AdRequest adRequestt = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequestt);




        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position1) {
                actionBar.setSelectedNavigationItem(position1);



            }
        });



        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        path = context.getDir("VocabCard",context.MODE_PRIVATE);
            file = new File(path,name);

            new readFile().execute();




    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        displayInterstitial();
        this.finish();
    }


    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }}

    public void addListenerOnSpinnerItemSelection(){

       spinner2.setOnItemSelectedListener(this);
       spinner1.setOnItemSelectedListener(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.item1:
            {

                if(isExternalStorageWritable())
                {
                    path1 = new File(Environment.getExternalStorageDirectory(),"/VocabCard");
                   // path1 = context.getExternalFilesDir("VocabCard");
                    path1.mkdir();
                    new backup().execute();
                    return true;
                    //file1 = new File(path1,"meta");

                }else
                {
                    Toast t = Toast.makeText(context,"No External media",Toast.LENGTH_SHORT);
                    t.show();
                    return true;
                }

            }

            case R.id.item2:
            {

                if(isExternalStorageWritable())
                {
                    try {
                        path1 = new File(Environment.getExternalStorageDirectory(), "/VocabCard");
                        //path1 = context.getExternalFilesDir("VocabCard");
                       if( path1.isDirectory())
                       {
                           new Restore().execute();
                           return  true;
                       }
                        else
                       {
                           Toast t = Toast.makeText(context,"No Backup exist",Toast.LENGTH_SHORT);
                           t.show();
                           return  true;
                       }
                    }catch(NullPointerException e)
                    {
                        Toast t = Toast.makeText(context,"No Backup exist",Toast.LENGTH_SHORT);
                        t.show();
                        return true;

                    }

                }else
                {
                    Toast t = Toast.makeText(context,"No External media",Toast.LENGTH_SHORT);
                    t.show();
                    return true;
                }

            }

            case R.id.item3: {

                displayInterstitial();

                this.finish();
                //System.exit(0);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());

       // spinner2.setSelection(position);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


       int id = adapterView.getId();
        switch(id){
            case R.id.spinnera: {

                Button bck = (Button)findViewById(R.id.button3);
                String lab = bck.getText().toString();
                if(lab.equals("Back"))
                {
                    EditText um = (EditText)findViewById(R.id.editTextm);
                    EditText uw = (EditText)findViewById(R.id.editTextw);

                    // Button ub = (Button)findViewById(R.id.button4);
                    Button btt = (Button)findViewById(R.id.buttonr);
                    // ub.setVisibility(v.INVISIBLE);
                    btt.setVisibility(View.VISIBLE);
                    btt.setText("Next");
                    t = (TextView)findViewById(R.id.textVieww);
                    t.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    t1 = (TextView)findViewById(R.id.textViewm);

                    t1.setMovementMethod(new ScrollingMovementMethod());
                    t1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    //scroll.addView(t1);
                    t.setEnabled(true);
                    t1.setEnabled(true);
                    t.setText(word_edit);
                    t1.setText(mean_edit);
                    um.setVisibility(View.INVISIBLE);
                    uw.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.VISIBLE);
                    t1.setVisibility(View.VISIBLE);
                    bck.setText("Remove");
                }

                t5 = (TextView)findViewById(R.id.textViewjour);
                t5.setText((String) spinner2.getSelectedItem()+" journal");
                t7 = (TextView)findViewById(R.id.textViewjorn);
                t7.setText((String) spinner2.getSelectedItem()+" journal");


                try{
                    name = (String) spinner2.getSelectedItem();
                }catch(Exception e){

                }

                try {
                    file = new File(path, name);

                    b = (Button) findViewById(R.id.buttonr);
                    t = (TextView) findViewById(R.id.textVieww);
                    t.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    t1 = (TextView) findViewById(R.id.textViewm);
                    t1.setMovementMethod(new ScrollingMovementMethod());
                    t1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });

                    //scroll.addView(t1);
                    ct = (TextView) findViewById(R.id.roll);
                    //String na = b.getText().toString();


                    if (file.exists()) {
                        TextView ty = (TextView) findViewById(R.id.textViewjorn);


                        try {
                            FileInputStream f = new FileInputStream(file);
                            ObjectInputStream ois = new ObjectInputStream(f);
                            l1 = (List[]) ois.readObject();
                            ois.close();
                            f.close();
                        } catch (Exception e) {

                        }

                        List q = new ArrayList();
                        q = l1[0];
                        if (l1[0].size() != 0) {
                            int sz = l1[0].size();
                            int ln = (l1[0].size()) / 10;
                            if ((l1[0].size()) > (ln * 10)) {
                                ln++;
                            }

                            spinner1 = (Spinner) findViewById(R.id.spinnerr);
                            List<String> list = new ArrayList<String>();
                            for (int j = 0; j < ln; j++) {
                                list.add("List " + (j + 1));
                            }


                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (this, android.R.layout.simple_spinner_item, list);

                            dataAdapter.setDropDownViewResource
                                    (android.R.layout.simple_spinner_dropdown_item);

                            spinner1.setAdapter(dataAdapter);

                            // Spinner item selection Listener
                            addListenerOnSpinnerItemSelection();
                            spinner1.setVisibility(View.VISIBLE);

                            ty.setText(name + " journal ( " + sz + " )");
                            t5.setText(name + " journal ( " + sz + " )");
                            ct.setText("" + curr);
                        } else {
                            b.setText("Start");
                            spinner1.setVisibility(View.INVISIBLE);
                            t.setText("Word");
                            t1.setText("Meaning");
                            ct.setText("");


                        }
                    } else {
                        b.setText("Start");
                        spinner1.setVisibility(View.INVISIBLE);
                        t.setText("Word");
                        t1.setText("Meaning");
                        ct.setText("");

                    }
                }catch (NullPointerException e)
                {

                }
                }
            break;
            case R.id.spinnerr:{



                Button bck = (Button)findViewById(R.id.button3);
                String lab = bck.getText().toString();
                if(lab.equals("Back"))
                {
                    EditText um = (EditText)findViewById(R.id.editTextm);
                    EditText uw = (EditText)findViewById(R.id.editTextw);

                    // Button ub = (Button)findViewById(R.id.button4);
                    Button btt = (Button)findViewById(R.id.buttonr);
                    // ub.setVisibility(v.INVISIBLE);
                    btt.setVisibility(View.VISIBLE);
                    btt.setText("Next");
                    t = (TextView)findViewById(R.id.textVieww);
                    t.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    t1 = (TextView)findViewById(R.id.textViewm);

                    t1.setMovementMethod(new ScrollingMovementMethod());
                    t1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    //scroll.addView(t1);
                    t.setEnabled(true);
                    t1.setEnabled(true);
                    t.setText(word_edit);
                    t1.setText(mean_edit);
                    um.setVisibility(View.INVISIBLE);
                    uw.setVisibility(View.INVISIBLE);
                    t.setVisibility(View.VISIBLE);
                    t1.setVisibility(View.VISIBLE);
                    bck.setText("Remove");
                }




                b = (Button)findViewById(R.id.buttonr);
                t = (TextView)findViewById(R.id.textVieww);
                t.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        correct(view);
                        return false;
                    }
                });
                t1 = (TextView)findViewById(R.id.textViewm);
                t1.setMovementMethod(new ScrollingMovementMethod());
                //scroll.addView(t1);
                t1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        correct(view);
                        return false;
                    }
                });
                ct = (TextView)findViewById(R.id.roll);
                //t.setText("Word");
                t1.setText("");
                strt = i*10;
                end = strt + 9;
                count = 1;
                curr = strt+1;
                try{

                    FileInputStream f = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(f);
                    l1 = (List[]) ois.readObject();
                    ois.close();
                }catch(Exception e)
                {

                }
                r = l1[0].listIterator(strt);
                r1 = l1[1].listIterator(strt);
                t.setText((CharSequence) r.next());
                b.setText("Means");
                ct.setText(""+curr);

            }
            break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0: return PlaceholderFragment.newInstance(deviceId);
                case 1: return Add.newInstance("h","t");
                case 2: return Revise.newInstance("e","y");
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1);
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section3);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
       // private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

        String mParam1;

        public static PlaceholderFragment newInstance(String device) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
           args.putString("id", device);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {


        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);




            if (getArguments() != null) {
                mParam1 = getArguments().getString("id");
                //mParam2 = getArguments().getString(ARG_PARAM2);

            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
           // return root;

            return rootView;
        }





    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public class readFile extends AsyncTask<String, Void, String> {


        ProgressDialog pd = null;
        @Override
        protected void onPreExecute() {


            pd = ProgressDialog.show(MyActivity.this, "Please wait",
                    "Loading..", true);
            pd.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... params) {

            name = "meta";
            file = new File(path, name);
            if (file.exists()) {

                try {
                    FileInputStream f = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(f);
                    meta = (List) ois.readObject();
                    ois.close();
                    f.close();
                } catch (Exception e) {

                }
            }else
            {

            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {

            if (file.exists() && meta.size()!=0) {
                spinner2 = (Spinner) findViewById(R.id.spinnera);
                spinner1 = (Spinner) findViewById(R.id.spinnerr);
                addListenerOnSpinnerItemSelection();
                //List<String> list = new ArrayList<String>();


                ArrayAdapter dataAdapter = new ArrayAdapter<String>
                        (context, android.R.layout.simple_spinner_item, meta);

                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                spinner2.setAdapter(dataAdapter);
            }else
            {
                butt=(Button)findViewById(R.id.journald);
                butt.setEnabled(false);
                butt1 = (Button)findViewById(R.id.buttona);
                butt1.setEnabled(false);
                butt2 = (Button)findViewById(R.id.buttonr);
                butt2.setEnabled(false);
                butt3 = (Button)findViewById(R.id.button3);
                butt3.setEnabled(false);
                spinner2 = (Spinner) findViewById(R.id.spinnera);
                spinner1 = (Spinner) findViewById(R.id.spinnerr);
                spinner1.setVisibility(View.INVISIBLE);
                spinner2.setVisibility(View.INVISIBLE);
            }
            //spinner2.setSelection(position);
            //spinner2.setEnabled(true);
           // spinner2.setVisibility(true);
            pd.dismiss();

            // Spinner item selection Listener
           // spinner2.setOnItemSelectedListener();


        }
    }


    public void delete(View v)
    {

        Button bck = (Button)findViewById(R.id.button3);
        String lab = bck.getText().toString();
        if(lab.equals("Back"))
        {
            EditText um = (EditText)findViewById(R.id.editTextm);
            EditText uw = (EditText)findViewById(R.id.editTextw);

            // Button ub = (Button)findViewById(R.id.button4);
            Button btt = (Button)findViewById(R.id.buttonr);
            // ub.setVisibility(v.INVISIBLE);
            btt.setVisibility(View.VISIBLE);
            btt.setText("Next");
            t = (TextView)findViewById(R.id.textVieww);
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            t1 = (TextView)findViewById(R.id.textViewm);

            t1.setMovementMethod(new ScrollingMovementMethod());
            t1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            //scroll.addView(t1);
            t.setEnabled(true);
            t1.setEnabled(true);
            t.setText(word_edit);
            t1.setText(mean_edit);
            um.setVisibility(View.INVISIBLE);
            uw.setVisibility(View.INVISIBLE);
            t.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            bck.setText("Remove");
        }


        Context c = null  ;


        try{
            c= this;



            sw = (String) spinner2.getSelectedItem();
        }catch(NullPointerException e)
        {
            sw = null;
        }
        if(!sw.equals(null))
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    c);

            // set title
            alertDialogBuilder.setTitle("Delete "+sw);

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click YES to delete")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            desion = true;
                            delete(desion,sw);
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            desion = false;
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }

        desion = false;
    }

    public void delete(boolean t,String sw)
    {
        file=new File(path,"meta");
        if(!sw.equals(null) && t)
        {
            int e = spinner2.getSelectedItemPosition();


                meta.remove(e);


            //


            //List<String> list = new ArrayList<String>();



            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,meta);

            dataAdapter.setDropDownViewResource
                    (android.R.layout.simple_spinner_dropdown_item);

            spinner2.setAdapter(dataAdapter);

            // Spinner item selection Listener
            if((meta.size()-1)> e) {
                spinner2.setSelection(e + 1);

            }else
            { if(meta.size()-1==e) {
                spinner2.setSelection(meta.size() - 1);
            }if(meta.size()-1<e)
            {
                spinner2.setSelection(0);
            }
            }


                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(file);


                    ObjectOutputStream oos = new ObjectOutputStream(os);


                    oos.writeObject(meta);
                    oos.close();

                    Toast toa = Toast.makeText(context, "Journal deleted", Toast.LENGTH_SHORT);
                    toa.show();

                } catch (IOException e1) {

                }

                file = new File(path, sw);
                if (file.exists()) {
                    file.delete();
                }

        }else
        {
            butt=(Button)findViewById(R.id.journald);
            butt.setEnabled(false);
            butt1 = (Button)findViewById(R.id.buttona);
            butt1.setEnabled(false);
            butt2 = (Button)findViewById(R.id.buttonr);
            butt2.setEnabled(false);
            butt3 = (Button)findViewById(R.id.button3);
            butt3.setEnabled(false);
            tt1 = (TextView)findViewById(R.id.textViewjorn);
            tt2 = (TextView)findViewById(R.id.textViewjour);
            tt3 = (TextView)findViewById(R.id.roll);
            tt4 = (TextView)findViewById(R.id.textVieww);
            tt4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            tt5 = (TextView)findViewById(R.id.textViewm);
            tt5.setMovementMethod(new ScrollingMovementMethod());
           // scroll.addView(tt5);
            tt5.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            tt1.setText("");
            tt2.setText("");
            tt3.setText("");
            tt4.setText("Word");
            tt5.setText("Meaning");

            spinner1.setVisibility(View.INVISIBLE);
            spinner2.setVisibility(View.INVISIBLE);
        }

        if(meta.size()==0)
        {
            butt=(Button)findViewById(R.id.journald);
            butt.setEnabled(false);
            butt1 = (Button)findViewById(R.id.buttona);
            butt1.setEnabled(false);
            butt2 = (Button)findViewById(R.id.buttonr);
            butt2.setEnabled(false);
            butt3 = (Button)findViewById(R.id.button3);
            butt3.setEnabled(false);


            tt1 = (TextView)findViewById(R.id.textViewjorn);
            tt2 = (TextView)findViewById(R.id.textViewjour);
            tt3 = (TextView)findViewById(R.id.roll);
            tt4 = (TextView)findViewById(R.id.textVieww);
            tt4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            tt5 = (TextView)findViewById(R.id.textViewm);
            tt5.setMovementMethod(new ScrollingMovementMethod());
           // scroll.addView(tt5);
            tt5.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            tt1.setText("");
            tt2.setText("");
            tt3.setText("");
            tt4.setText("Word");
            tt5.setText("Meaning");

            spinner1.setVisibility(View.INVISIBLE);
            spinner2.setVisibility(View.INVISIBLE);
        }
    }


    public void journal(View v)
    {

        Button bck = (Button)findViewById(R.id.button3);
        String lab = bck.getText().toString();
        if(lab.equals("Back"))
        {
            EditText um = (EditText)findViewById(R.id.editTextm);
            EditText uw = (EditText)findViewById(R.id.editTextw);

            // Button ub = (Button)findViewById(R.id.button4);
            Button btt = (Button)findViewById(R.id.buttonr);
            // ub.setVisibility(v.INVISIBLE);
            btt.setVisibility(View.VISIBLE);
            btt.setText("Next");
            t = (TextView)findViewById(R.id.textVieww);
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            t1 = (TextView)findViewById(R.id.textViewm);

            t1.setMovementMethod(new ScrollingMovementMethod());
            t1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            //scroll.addView(t1);
            t.setEnabled(true);
            t1.setEnabled(true);
            t.setText(word_edit);
            t1.setText(mean_edit);
            um.setVisibility(View.INVISIBLE);
            uw.setVisibility(View.INVISIBLE);
            t.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            bck.setText("Remove");
        }



        Context context = getApplicationContext();
        Toast toast;
        EditText t3 = (EditText)findViewById(R.id.editTextj);
        String j = t3.getText().toString();
        if(j.equals(""))
        {

            CharSequence text ="Field empty";
            int duration = Toast.LENGTH_SHORT;

            toast = Toast.makeText(context, text, duration);
            toast.show();
        }else
        {
            name = "meta";
            file= new File(path,name);
            if(file.exists())
            {

                try{
                    FileInputStream f = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(f);
                    meta  = (List) ois.readObject();
                    ois.close();
                }catch(Exception e)
                {

                }
                meta.add(j);
                FileOutputStream os = null;
                try {
                    os =new FileOutputStream(file);


                    ObjectOutputStream oos = new ObjectOutputStream(os);


                    oos.writeObject(meta);
                    oos.close();
                    os.close();

                } catch (IOException e) {

                }

                //List<String> list = new ArrayList<String>();



                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item,meta);

                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                spinner2.setAdapter(dataAdapter);
                spinner2.setSelection(meta.size()-1);

                // Spinner item selection Listener


            }else
            {
                List<String> ts = new ArrayList<String>();
                ts.add(j);
                FileOutputStream os = null;
                try {
                    os =new FileOutputStream(file);

                    ObjectOutputStream oos = new ObjectOutputStream(os);


                    oos.writeObject(ts);
                    oos.close();


                } catch (IOException e) {

                }


                //List<String> list = new ArrayList<String>();



                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item,ts);


                dataAdapter.setDropDownViewResource
                        (android.R.layout.simple_spinner_dropdown_item);

                spinner2.setAdapter(dataAdapter);




                // Spinner item selection Listener

            }

            butt=(Button)findViewById(R.id.journald);
            butt.setEnabled(true);
            butt1 = (Button)findViewById(R.id.buttona);
            butt1.setEnabled(true);
            butt2 = (Button)findViewById(R.id.buttonr);
            butt2.setEnabled(true);
            butt3 = (Button)findViewById(R.id.button3);
            butt3.setEnabled(true);
            //spinner1.setVisibility(View.VISIBLE);
            spinner2.setVisibility(View.VISIBLE);

            Toast t = Toast.makeText(context,"Journal added",Toast.LENGTH_SHORT);
            t.show();


            t3.setText("");


        }

            }



    public void save(View v)
    {
        int spp ;
        EditText et,et1;
        List l1[]=null;

        Button bck = (Button)findViewById(R.id.button3);
        String lab = bck.getText().toString();
        if(lab.equals("Back"))
        {
            EditText um = (EditText)findViewById(R.id.editTextm);
            EditText uw = (EditText)findViewById(R.id.editTextw);

            // Button ub = (Button)findViewById(R.id.button4);
            Button btt = (Button)findViewById(R.id.buttonr);
            // ub.setVisibility(v.INVISIBLE);
            btt.setVisibility(View.VISIBLE);
            btt.setText("Next");
            t = (TextView)findViewById(R.id.textVieww);
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            t1 = (TextView)findViewById(R.id.textViewm);

            t1.setMovementMethod(new ScrollingMovementMethod());
            t1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            //scroll.addView(t1);
            t.setEnabled(true);
            t1.setEnabled(true);
            t.setText(word_edit);
            t1.setText(mean_edit);
            um.setVisibility(View.INVISIBLE);
            uw.setVisibility(View.INVISIBLE);
            t.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            bck.setText("Remove");
        }


        try{
            name = (String) spinner2.getSelectedItem();
        }catch(Exception e){

        }
        try{
            file = new File(path,name);

            Context context = getApplicationContext();
            Toast toast;

            et = (EditText)findViewById(R.id.word);
            et1 = (EditText)findViewById(R.id.mean);
            String word = et.getText().toString();
            String mean = et1.getText().toString();

            if(word.equals("") || mean.equals(""))
            {
                CharSequence text ="Field(s) empty";
                int duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();


            }else
            {
                if(file.exists())
                {

                    try{

                        FileInputStream f = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(f);
                        l1 = (List[]) ois.readObject();
                        ois.close();
                    }catch(Exception e)
                    {

                    }

                    // List l[]  = new ArrayList[2];
                    // l[0] = new ArrayList();
                    // l[1] = new ArrayList();
                    l1[0].add(word);
                    l1[1].add(mean);

                    spp = l1[0].size();

                    FileOutputStream os = null;
                    try {
                        os =new FileOutputStream(file);


                        ObjectOutputStream oos = new ObjectOutputStream(os);


                        oos.writeObject(l1);
                        oos.close();
                        os.close();
                        l1=null;
                    } catch (IOException e) {

                    }

                }else
                {
                    List l[]  = new ArrayList[2];
                    l[0] = new ArrayList();
                    l[1] = new ArrayList();
                    l[0].add(word);
                    l[1].add(mean);

                    spp = l[0].size();

                    FileOutputStream os = null;
                    try {
                        os =new FileOutputStream(file);

                        ObjectOutputStream oos = new ObjectOutputStream(os);


                        oos.writeObject(l);
                        oos.close();
                        os.close();
                        l=null;
                    } catch (IOException e) {

                    }
                }



                t5 = (TextView)findViewById(R.id.textViewjour);
                t5.setText(name+" journal ( "+ spp + " )");

                spinner1.setVisibility(View.VISIBLE);


                try{
                    name = (String) spinner2.getSelectedItem();
                }catch(Exception e){

                }

                try {
                    file = new File(path, name);

                    b = (Button) findViewById(R.id.buttonr);
                    t = (TextView) findViewById(R.id.textVieww);
                    t.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    t1 = (TextView) findViewById(R.id.textViewm);
                    t1.setMovementMethod(new ScrollingMovementMethod());
                    t1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                   // scroll.addView(t1);
                    ct = (TextView) findViewById(R.id.roll);
                    //String na = b.getText().toString();


                    if (file.exists()) {
                        TextView ty = (TextView) findViewById(R.id.textViewjorn);


                        try {
                            FileInputStream f = new FileInputStream(file);
                            ObjectInputStream ois = new ObjectInputStream(f);
                            l1 = (List[]) ois.readObject();
                            ois.close();
                            f.close();
                        } catch (Exception e) {

                        }

                        List q = new ArrayList();
                        q = l1[0];
                        if (l1[0].size() != 0) {
                            int sz = l1[0].size();
                            int ln = (l1[0].size()) / 10;
                            if ((l1[0].size()) > (ln * 10)) {
                                ln++;
                            }

                            spinner1 = (Spinner) findViewById(R.id.spinnerr);
                            List<String> list = new ArrayList<String>();
                            for (int j = 0; j < ln; j++) {
                                list.add("List " + (j + 1));
                            }


                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (this, android.R.layout.simple_spinner_item, list);

                            dataAdapter.setDropDownViewResource
                                    (android.R.layout.simple_spinner_dropdown_item);

                            spinner1.setAdapter(dataAdapter);

                            // Spinner item selection Listener
                            addListenerOnSpinnerItemSelection();
                            spinner1.setVisibility(View.VISIBLE);

                            ty.setText(name + " journal ( " + sz + " )");
                            t5 = (TextView)findViewById(R.id.textViewjour);
                            t5.setText(name + " journal ( " + sz + " )");
                            ct.setText("" + curr);
                        } else {
                            b.setText("Start");
                            spinner1.setVisibility(View.INVISIBLE);
                            t.setText("Word");
                            t1.setText("Meaning");
                            ct.setText("");


                        }
                    } else {
                        b.setText("Start");
                        spinner1.setVisibility(View.INVISIBLE);
                        t.setText("Word");
                        t1.setText("Meaning");
                        ct.setText("");

                    }
                }catch (NullPointerException e)
                {

                }


                CharSequence text ="Word Added";
                int duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();


                et1.setText(null);
                et.setText(null);
            }
        }
        catch(NullPointerException e)
        {

        }
    }


    public void show(View v)
    {

        try{
            name = (String) spinner2.getSelectedItem();
        }catch(Exception e){

        }
        Context context = getApplicationContext();
        Toast toast;
        try{
            file = new File(path,name);

            b = (Button)findViewById(R.id.buttonr);
            t = (TextView)findViewById(R.id.textVieww);
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            t1 = (TextView)findViewById(R.id.textViewm);
            t1.setMovementMethod(new ScrollingMovementMethod());
           // scroll.addView(t1);
            t1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            ct = (TextView)findViewById(R.id.roll);
            String na = b.getText().toString();


            if(na.equals("Means"))
            {
                t1.setText((CharSequence) r1.next());
                b.setText("Next");
                count++;
                curr++;

            }else if(na.equals("Start"))
            {
                if(file.exists())
                {
                    TextView ty = (TextView)findViewById(R.id.textViewjorn);




                    try{
                        FileInputStream f = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(f);
                        l1 = (List[]) ois.readObject();
                        ois.close();
                        f.close();
                    }catch(Exception e)
                    {

                    }

                    List q = new ArrayList();
                    q = l1[0];
                    if(l1[0].size()!=0)
                    {
                        int sz = l1[0].size();
                        int ln = (l1[0].size())/10;
                        if((l1[0].size())>(ln*10))
                        {
                            ln++;
                        }

                        spinner1 = (Spinner) findViewById(R.id.spinnerr);
                        List<String> list = new ArrayList<String>();
                        for(int i =0;i<ln;i++){
                            list.add("List "+(i+1));
                        }


                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                (this, android.R.layout.simple_spinner_item,list);

                        dataAdapter.setDropDownViewResource
                                (android.R.layout.simple_spinner_dropdown_item);

                        spinner1.setAdapter(dataAdapter);

                        // Spinner item selection Listener
                        addListenerOnSpinnerItemSelection();

                        ty.setText(name+" journal ( "+ sz +" )");
                        t5 = (TextView)findViewById(R.id.textViewjour);
                        t5.setText(name + " journal ( " + sz + " )");
                        ct.setText(""+curr);
                    }
                    else
                    {
                        CharSequence text ="Please add words in journal";
                        int duration = Toast.LENGTH_SHORT;

                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }else
                {
                    CharSequence text ="Please add words in journal";
                    int duration = Toast.LENGTH_SHORT;

                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }



            }else if(na.equals("Next"))
            {
                if(r.hasNext() && count<=10)
                {

                    t.setText((CharSequence) r.next());
                    t1.setText("");
                    b.setText("Means");
                    ct.setText(""+ curr);
                }
                else{
                    b.setText("Repeat");
                    t.setText("Word");
                    t1.setText("Meaning");
                    ct.setText("");

                }

            }else if(na.equals("Repeat"))
            {
                r = l1[0].listIterator(strt);
                r1 = l1[1].listIterator(strt);
                t.setText((CharSequence) r.next());
                count = 1;
                curr = strt+1;
                t1.setText("");
                b.setText("Means");
                ct.setText(""+ curr);


            }else if(na.equals("Update"))
            {
                Button btt = (Button)findViewById(R.id.buttonr);

                Toast to;
                int cr = strt + count ;
                cr = cr-2;
                EditText um = (EditText)findViewById(R.id.editTextm);
                EditText uw = (EditText)findViewById(R.id.editTextw);
                String w = uw.getText().toString();
                String m = um.getText().toString();

                if(w.equals("") || m.equals(""))
                {
                    CharSequence text ="Field(s) empty";
                    int duration = Toast.LENGTH_SHORT;

                    to = Toast.makeText(context, text, duration);
                    to.show();

                }
                else{

                    l1[0].set(cr, w);
                    l1[1].set(cr, m);
                    um.setVisibility(v.INVISIBLE);
                    uw.setVisibility(v.INVISIBLE);
                    t = (TextView)findViewById(R.id.textVieww);
                    t.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    t1 = (TextView)findViewById(R.id.textViewm);
                    t1.setMovementMethod(new ScrollingMovementMethod());
                   // scroll.addView(t1);
                    t1.setMovementMethod(new ScrollingMovementMethod());
                    t1.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            correct(view);
                            return false;
                        }
                    });
                    t.setText(w);
                    t1.setText(m);
                    t.setVisibility(v.VISIBLE);
                    t1.setVisibility(v.VISIBLE);
                   // Button ub = (Button)findViewById(R.id.button4);
                   // ub.setVisibility(v.INVISIBLE);
                    //btt.setVisibility(v.VISIBLE);
                    btt.setText("Next");

                    FileOutputStream os = null;
                    try {
                        os =new FileOutputStream(new File(path,name));


                        ObjectOutputStream oos = new ObjectOutputStream(os);


                        oos.writeObject(l1);
                        oos.close();

                    } catch (IOException e) {

                    }
                    Button bckk = (Button)findViewById(R.id.button3);
                    bckk.setText("Remove");

                    CharSequence text ="Updated";
                    int duration = Toast.LENGTH_SHORT;

                    toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }

        }
        catch(NullPointerException e)
        {

        }
    }


    public void update(View v)
    {
        Button bck = (Button)findViewById(R.id.button3);
        String lab = bck.getText().toString();
        if(lab.equals("Remove"))
        {
            try{
                name = (String) spinner2.getSelectedItem();
            }catch(Exception e)
            {

            }
            try{
                file = new File(path,name);
                Context c = this;
                Toast toast;
                if(b.getText().equals("Next"))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            c);

                    // set title
                    alertDialogBuilder.setTitle("Remove");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Click YES to remove")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    desion = true;
                                    rer(desion);
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    desion = false;
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }

            }
            catch(NullPointerException e)
            {

            }
        }else
        {
            EditText um = (EditText)findViewById(R.id.editTextm);
            EditText uw = (EditText)findViewById(R.id.editTextw);

           // Button ub = (Button)findViewById(R.id.button4);
            Button btt = (Button)findViewById(R.id.buttonr);
           // ub.setVisibility(v.INVISIBLE);
            btt.setVisibility(v.VISIBLE);
            btt.setText("Next");
            t = (TextView)findViewById(R.id.textVieww);
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            t1 = (TextView)findViewById(R.id.textViewm);

            t1.setMovementMethod(new ScrollingMovementMethod());
            t1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            //scroll.addView(t1);
            t.setEnabled(true);
            t1.setEnabled(true);
            t.setText(word_edit);
            t1.setText(mean_edit);
            um.setVisibility(v.INVISIBLE);
            uw.setVisibility(v.INVISIBLE);
            t.setVisibility(v.VISIBLE);
            t1.setVisibility(v.VISIBLE);
            bck.setText("Remove");

        }
    }


    public void rer(boolean wert)
    {
        Context context = getApplicationContext();
        Toast toast;
        ct = (TextView)findViewById(R.id.roll);
        TextView ty = (TextView)findViewById(R.id.textViewjorn);
        if(wert)
        {
            r.remove();
            r1.remove();

            FileOutputStream os = null;
            try {
                os =new FileOutputStream(file);


                ObjectOutputStream oos = new ObjectOutputStream(os);


                oos.writeObject(l1);
                oos.close();

            } catch (IOException e) {

            }

            CharSequence text ="Removed";
            int duration = Toast.LENGTH_SHORT;

            toast = Toast.makeText(context, text, duration);
            toast.show();
            if(r.hasNext() && count<=10)
            {
                t.setText((CharSequence) r.next());
                t1.setText("");
                b.setText("Means");
                ty.setText(name+" journal ( "+ (l1[0].size()) +" )");
                tt2 = (TextView)findViewById(R.id.textViewjour);
                tt2.setText(name+" journal ( "+ (l1[0].size()) +" )");
            }
            else{
                b.setText("Start");
                t.setText("Word");
                t1.setText("Meaning");
                ct.setText("");
                ty.setText(name+" journal ( "+ (l1[0].size()) +" )");
                tt2 = (TextView)findViewById(R.id.textViewjour);
                tt2.setText(name+" journal ( "+ (l1[0].size()) +" )");
                if(l1[0].size()==0)
                spinner1.setVisibility(View.INVISIBLE);

            }}
    }



    public void correct(View v)
    {
        Button bt = (Button)findViewById(R.id.buttonr);

        if(bt.getText().toString().equals("Next"))
        {
           // bt.setVisibility(v.INVISIBLE);
            bt.setText("Update");

            t = (TextView)findViewById(R.id.textVieww);
            t.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            t1 = (TextView)findViewById(R.id.textViewm);
            t1.setMovementMethod(new ScrollingMovementMethod());
            t1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    correct(view);
                    return false;
                }
            });
            //scroll.addView(t1);
            word_edit = t.getText().toString();
            mean_edit = t1.getText().toString();
            t.setVisibility(v.INVISIBLE);
            t.setEnabled(false);
            t1.setVisibility(v.INVISIBLE);
            t1.setEnabled(false);
            // t1.setText("");
            EditText um = (EditText)findViewById(R.id.editTextm);
            EditText uw = (EditText)findViewById(R.id.editTextw);
            uw.setText(word_edit);
            um.setText(mean_edit);
            um.setVisibility(v.VISIBLE);
            uw.setVisibility(v.VISIBLE);
           // Button ub = (Button)findViewById(R.id.button4);
           // ub.setVisibility(v.VISIBLE);
            Button bckk = (Button)findViewById(R.id.button3);
            bckk.setText("Back");
        }
    }


    public class backup extends AsyncTask<String, Void, String> {


        ProgressDialog pd = null;
        List lw[] =null;

        @Override
        protected void onPreExecute() {


            pd = ProgressDialog.show(MyActivity.this, "Please wait",
                    "Creating backup..", true);
            pd.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... params) {

            name = "meta";
            try {
                file = new File(path, name);
            }catch (NullPointerException e)
            {

            }
            if (file.exists()) {

                try {
                    FileInputStream f = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(f);
                    meta = (List) ois.readObject();
                    ois.close();
                    f.close();
                } catch (Exception e) {

                }

                file1 = new File(path1,"meta");
                FileOutputStream os = null;
                try {
                    os =new FileOutputStream(file1);


                    ObjectOutputStream oos = new ObjectOutputStream(os);


                    oos.writeObject(meta);
                    oos.close();

                } catch (IOException e) {

                }

                Iterator it = meta.iterator();
                while(it.hasNext())
                {
                    String vx = (String) it.next();
                    file2 = new File(path, vx);
                    if (file2.exists()) {

                        try {
                            FileInputStream f = new FileInputStream(file2);
                            ObjectInputStream ois = new ObjectInputStream(f);
                            lw = (List[]) ois.readObject();
                            ois.close();
                            f.close();
                        } catch (Exception e) {

                        }

                    }

                    file1 = new File(path1,vx);
                    //FileOutputStream os = null;
                    try {
                        os =new FileOutputStream(file1);


                        ObjectOutputStream oos = new ObjectOutputStream(os);


                        oos.writeObject(lw);
                        oos.close();

                    } catch (IOException e) {

                    }

                    }


            } else {



            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {


            pd.dismiss();

                Toast t = Toast.makeText(context,"Backup created",Toast.LENGTH_SHORT);
                t.show();



        }
    }

    public class Restore extends AsyncTask<String, Void, String> {


        ProgressDialog pd = null;
        List lw[] =null;
        List le = null;

        @Override
        protected void onPreExecute() {


            pd = ProgressDialog.show(MyActivity.this, "Please wait",
                    "Restoring..", true);
            pd.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... params) {


            name = "meta";
            try {
                file1 = new File(path1, "meta");
            }catch (NullPointerException e)
            {

            }
            if (file1.exists()) {

                try {
                    FileInputStream f = new FileInputStream(file1);
                    ObjectInputStream ois = new ObjectInputStream(f);
                    le = (List) ois.readObject();
                    ois.close();
                    f.close();
                } catch (Exception e) {

                }

                file = new File(path,name);
                FileOutputStream os = null;
                try {
                    os =new FileOutputStream(file);


                    ObjectOutputStream oos = new ObjectOutputStream(os);


                    oos.writeObject(le);
                    oos.close();

                } catch (IOException e) {

                }

                Iterator it = le.iterator();
                while(it.hasNext())
                {
                    String vx = (String) it.next();
                    file2 = new File(path1, vx);
                    if (file2.exists()) {

                        try {
                            FileInputStream f = new FileInputStream(file2);
                            ObjectInputStream ois = new ObjectInputStream(f);
                            lw = (List[]) ois.readObject();
                            ois.close();
                            f.close();
                        } catch (Exception e) {

                        }

                    }

                    file = new File(path,vx);
                    //FileOutputStream os = null;
                    try {
                        os =new FileOutputStream(file);


                        ObjectOutputStream oos = new ObjectOutputStream(os);


                        oos.writeObject(lw);
                        oos.close();

                    } catch (IOException e) {

                    }

                }

                   return "true";

            } else {



            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {


            if(result.equals("true"))
            {
                butt=(Button)findViewById(R.id.journald);
                butt.setEnabled(true);
                butt1 = (Button)findViewById(R.id.buttona);
                butt1.setEnabled(true);
                butt2 = (Button)findViewById(R.id.buttonr);
                butt2.setEnabled(true);
                butt3 = (Button)findViewById(R.id.button3);
                butt3.setEnabled(true);
                //spinner1.setVisibility(View.VISIBLE);
                spinner2.setVisibility(View.VISIBLE);
            }


            pd.dismiss();

            Toast t = Toast.makeText(context,"Restored",Toast.LENGTH_SHORT);
            t.show();
            new readFile().execute();

        }
    }





}
