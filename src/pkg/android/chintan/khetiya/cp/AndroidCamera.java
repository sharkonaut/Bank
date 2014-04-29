package pkg.android.chintan.khetiya.cp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AndroidCamera extends Activity implements SurfaceHolder.Callback
	{

		Camera camera;
		SurfaceView surfaceView;
		SurfaceHolder surfaceHolder;
        Button accountInfo, transfer, budget, search, cancelSearch, exitAccountInfo, cancelTransfer;
        ImageView card1, card2;
        TextView accountLabel, balanceLabel, scanIt, saveAccountLabel, saveBalanceLabel, startLabel;
        EditText searchField;
        LinearLayout accountInfoLayout;
        Animation anim;

		boolean previewing = false;
		LinearLayout get_more;

        //for my list adapter
        static final String KEY_INFO = "info";
        static final String KEY_DATE = "date";
        static final String KEY_AMOUNT = "amount";
        static final String KEY_DEBORCRED = "debOrCred";

        ListView list;
        Adapter adapter;

		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState)
			{
				super.onCreate(savedInstanceState);
				setContentView(R.layout.main);


                ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>(); //empty arraylist with a hashmap for the listview data
                list = (ListView)findViewById(R.id.listView); //listview in xml layout file
                map = generateData(); //function that generates fake data for the bank


                adapter = new Adapter(this, map); //initializing the adapter

                list.setAdapter(adapter); //setting the adapter to the list


				surfaceView = (SurfaceView) findViewById(R.id.surfaceview);

				surfaceHolder = surfaceView.getHolder();
				surfaceHolder.addCallback(this);
				surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                accountInfo = (Button)findViewById(R.id.accountInfo);
                transfer = (Button)findViewById(R.id.transfer);
                budget = (Button)findViewById(R.id.budget);
                card1 = (ImageView)findViewById(R.id.cardOutline1);
                card2 = (ImageView)findViewById(R.id.card2);
                accountLabel = (TextView)findViewById(R.id.accountLabel);
                balanceLabel = (TextView)findViewById(R.id.balanceLabel);
                scanIt = (TextView)findViewById(R.id.scanIt);
                saveAccountLabel = (TextView)findViewById(R.id.saveAccountLabel);
                saveBalanceLabel = (TextView)findViewById(R.id.saveBalanceLabel);
                startLabel = (TextView)findViewById(R.id.startLabel);

                //all the stuff
                search = (Button)findViewById(R.id.search);
                searchField = (EditText)findViewById(R.id.editText);
                cancelSearch = (Button)findViewById(R.id.cancelSearch);
                exitAccountInfo = (Button)findViewById(R.id.exitAccountInfo);
                accountInfoLayout = (LinearLayout)findViewById(R.id.accountInfoLayout);
                cancelTransfer = (Button)findViewById(R.id.cancelTransfer);


                accountInfo.bringToFront(); //you have to bring to front if you want it in front of the surfaceview

                accountInfo.setVisibility(View.INVISIBLE);
                transfer.setVisibility(View.INVISIBLE);
                budget.setVisibility(View.INVISIBLE);

                card2.setVisibility(View.INVISIBLE);
                balanceLabel.setVisibility(View.INVISIBLE);
                accountLabel.setVisibility(View.INVISIBLE);
                searchField.setVisibility(View.INVISIBLE);
                cancelSearch.setVisibility(View.INVISIBLE);
                accountInfoLayout.setVisibility(View.INVISIBLE);
                scanIt.setVisibility(View.INVISIBLE);
                saveAccountLabel.setVisibility(View.INVISIBLE);
                saveBalanceLabel.setVisibility(View.INVISIBLE);

                startLabel.setVisibility(View.INVISIBLE);

                card1.setVisibility(View.INVISIBLE);
                cancelTransfer.setVisibility(View.INVISIBLE);



                search.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int vis = searchField.getVisibility();
                        if(vis == 0) {
                            //does nothing, search button is not visible

                        }
                        else {
                            searchField.setVisibility(View.VISIBLE);
                            searchField.bringToFront();
                            cancelSearch.setVisibility(View.VISIBLE);
                            cancelSearch.bringToFront();
                            search.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                cancelSearch.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchField.setVisibility(View.INVISIBLE);
                        cancelSearch.setVisibility(View.INVISIBLE);
                        search.setVisibility(View.VISIBLE);
                        search.bringToFront();
                    }
                });

                exitAccountInfo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accountInfoLayout.setVisibility(View.INVISIBLE);
                        accountInfo.setVisibility(View.VISIBLE);
                        transfer.setVisibility(View.VISIBLE);
                        budget.setVisibility(View.VISIBLE);
                    }
                });

                accountInfo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(accountInfoLayout.getVisibility() == 0) {
                            //if layout is visible
                            //does nothing, account info will be gone
                        }
                        else {
                            //if layout is invisible
                            accountInfoLayout.setVisibility(View.VISIBLE);
                            accountInfoLayout.bringToFront();
                            accountInfo.setVisibility(View.INVISIBLE);
                            transfer.setVisibility(View.INVISIBLE);
                            budget.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                //transfer money
                transfer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        card2.setVisibility(View.VISIBLE);
                        card2.bringToFront();
                        scanIt.setVisibility(View.VISIBLE);
                        scanIt.bringToFront();
                        cancelTransfer.setVisibility(View.VISIBLE);
                        cancelTransfer.bringToFront();
                        accountInfo.setVisibility(View.INVISIBLE);
                        transfer.setVisibility(View.INVISIBLE);
                        budget.setVisibility(View.INVISIBLE);
                        blink(true, card2);
                        //make it blink??
                        card2.postDelayed(new Runnable() {
                            public void run() {
                               saveAccountLabel.setVisibility(View.VISIBLE);
                                saveBalanceLabel.setVisibility(View.VISIBLE);
                                saveAccountLabel.bringToFront();
                                saveBalanceLabel.bringToFront();
                                scanIt.setVisibility(View.INVISIBLE);
                                blink(false, card2);




                            }
                        }, 4000);

                    }
                });

                cancelTransfer.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //return stuff back to original state
                        cancelTransfer.setVisibility(View.INVISIBLE);
                        card2.setVisibility(View.INVISIBLE);
                        saveBalanceLabel.setVisibility(View.INVISIBLE);
                        saveAccountLabel.setVisibility(View.INVISIBLE);
                        accountInfo.setVisibility(View.VISIBLE);
                        accountInfo.bringToFront();
                        transfer.setVisibility(View.VISIBLE);
                        transfer.bringToFront();
                        budget.setVisibility(View.VISIBLE);
                        budget.bringToFront();
                    }
                });

			}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void surfaceCreated(SurfaceHolder holder)
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder)
			{
				// TODO Auto-generated method stub

			}

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);
            return super.onCreateOptionsMenu(menu);
        }

        //generate fake data
        public ArrayList<HashMap<String, String>> generateData() {
            ArrayList<HashMap<String, String>> aMap = new ArrayList<HashMap<String, String>>();


            ArrayList<String> dateList = new ArrayList<String>();
            dateList.add(0, "Jan");
            dateList.add(1, "Feb");
            dateList.add(2, "Mar");
            dateList.add(3, "Apr");
            dateList.add(4, "May");
            dateList.add(5, "Jun");
            dateList.add(6, "Jul");
            dateList.add(7, "Aug");
            dateList.add(8, "Sep");
            dateList.add(9, "Oct");
            dateList.add(10, "Nov");
            dateList.add(11, "Dec");

            for(int i = 0; i < 200; i++) {
                HashMap<String, String> dataMap = new HashMap<String, String>();
                //get the amount
                double transaction = 1.00 + (double)(Math.random() * ((1100.01 - 1.00) + 1));
                double roundOff = Math.round(transaction * 100.0) / 100.0;
                dataMap.put(KEY_AMOUNT, "$" + String.valueOf(roundOff));

                //get the debit or credit
                int deb = 0 + (int)(Math.random() * ((1 - 0) + 1));
                String debit = "";
                if(deb == 0) {
                    debit = "-";
                }
                else if(deb == 1) {
                    debit = "+";
                }
                dataMap.put(KEY_DEBORCRED, debit);

                //get the date
                int dateIndex = 0 + (int)(Math.random() * ((11 - 0) + 1));
                String date = dateList.get(dateIndex);
                int dayOfMonth = 1 + (int)(Math.random() * ((30 - 1) + 1));
                String day = String.valueOf(dayOfMonth);
                String fullDate = day + " " + date;
                dataMap.put(KEY_DATE, fullDate);

                //get the transaction information
                dataMap.put(KEY_INFO, getInfo());


                //add the hashmap to the arraylist
                aMap.add(i, dataMap);
            }


            return aMap;
        }

        //generates fake bank data for the above function
        public String getInfo() {
            ArrayList<String> infoList = new ArrayList<String>();
            infoList.add(0, "CHECK CRD PURCHASE 04/20 MCDONALD'S F15751 LOS ANGELES CA");
            infoList.add(0, "ONLINE TRANSFER FROM REF #IBEMZLP8WK SAVINGS SHOES");
            infoList.add(0, "TARGET DEBIT CRD ACH TRAN 140419 000470995991958 TARGET");
            infoList.add(0, "CTL 8002441111 WEB 5755415544623");
            infoList.add(0, "USAA.COM PAYMNT P&C 140418 XXXXX9487");
            infoList.add(0, "SPRINT8006396111 ACHBILLPAY 140417 XXXXX9894");
            infoList.add(0, "POS PURCHASE - RACK ROOM SHOES 0426 LAS CRUCES NM 6144 005");
            infoList.add(0, "CHECK CRD PURCHASE 04/20 EPIA PARKING EL PASO TX 434258XXX");
            infoList.add(0, "NEW MEXICO STATE PAYROLL 140411 XXXXX4338");
            infoList.add(0, "ONLINE TRANSFER FROM REF #IBE5J8Q7RG BUSINESS MARKET RATE");
            infoList.add(0, "TANRIM DESIGN IAT PAYPAL 140410 J222222HQ67DY");

            String random = infoList.get(0 + (int)(Math.random() * ((10 - 0) + 1)));
            return random;
        }

        //menu
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.buttonStartCameraPreview:
                    //start camera preview
                    if (!previewing)
                    {
                        camera = Camera.open();
                        if (camera != null)
                        {
                            try
                            {
                                camera.setPreviewDisplay(surfaceHolder);
                                camera.startPreview();
                                previewing = true;
                                card1.setVisibility(View.VISIBLE);
                                card1.bringToFront();
                                blink(true, card1);
                                startLabel.setVisibility(View.VISIBLE);
                                startLabel.bringToFront();

                                accountInfo.postDelayed(new Runnable() {
                                    public void run() {
                                        accountInfo.setVisibility(View.VISIBLE);
                                        accountInfo.bringToFront();
                                        transfer.setVisibility(View.VISIBLE);
                                        transfer.bringToFront();
                                        budget.setVisibility(View.VISIBLE);
                                        budget.bringToFront();
                                        blink(false, card1);
                                        accountLabel.setVisibility(View.VISIBLE);
                                        accountLabel.bringToFront();
                                        balanceLabel.setVisibility(View.VISIBLE);
                                        balanceLabel.bringToFront();
                                        startLabel.setVisibility(View.INVISIBLE);
                                        Log.v("Visibility: ", String.valueOf(accountInfo.getVisibility()));
                                    }
                                }, 5000);
//
                            } catch (IOException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    return true;
                case R.id.buttonStopCameraPreview:
                    //stop camera preview
                    if (camera != null && previewing)
                    {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                        previewing = false;
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        private void blink(boolean b, ImageView i){
            anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(300); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            if(b) {
                i.startAnimation(anim);
            }
            else {
                anim = new AlphaAnimation(1.0f, 1.0f);
                i.startAnimation(anim);
            }
        }

	}

/*
 * chintan Dec 25, 2013 AndroidCamera.java
 */