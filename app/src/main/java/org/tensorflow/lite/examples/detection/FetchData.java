package org.tensorflow.lite.examples.detection;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class FetchData extends AsyncTask<URL,Void ,String> {

     String data;
     String dataparsed;
     String singleParsed;

     // Create two Arraylist dis[in meteres] and direction[left,right,etc ]

    private ArrayList<Float> dist;
    private ArrayList<String>  direc;
    @Override
    protected String doInBackground(URL... urls) {
      try{

          URL url = urls[0];
          HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
          InputStream inputStream = httpURLConnection.getInputStream();
          BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
          String line="";
          String templine = "";
          data="";
          dataparsed ="";
          singleParsed ="";
          while(templine!=null){
              templine = bufferedReader.readLine();
             Log.d("impo",templine);

              URL url1 = new URL("http://bemyeyes.ap-1.evennode.com/job/1");
              HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
              InputStream inputStream1 = httpURLConnection1.getInputStream();
              BufferedReader bufferedReader1=new BufferedReader(new InputStreamReader(inputStream1));

              while(line!=null){
                  line = bufferedReader1.readLine();
                  data=data+line;
                  Log.d("secon", "doInBackground:"+data);
              }

              if(line!=null) {
                  Log.d("secon", "doInBackground:"+data);

                  /*
                  JSONObject JAOUT = new JSONObject(data);

                  JSONArray JA = new JSONArray(JAOUT.get("result"));
                  for (int i = 0; i < JA.length(); i++) {
                      JSONObject JO = (JSONObject) JA.get(i);
                      singleParsed = JO.get("index") + " Direction: " + JO.get("direction") + "\n" + JO.get("distance") + "\n";
                      dataparsed = dataparsed + singleParsed;
                  }
                  */

                   return(data);


              }


          }




      }catch(MalformedURLException e){
          e.printStackTrace();
      }catch(IOException e){
          e.printStackTrace();
      }catch(Exception e){
          e.printStackTrace();
      }


        return null;
    }

    @Override
    protected void onPostExecute(String datext) {
        super.onPostExecute(datext);
        Log.d("postex", "onPostExecute: "+data);
        dist = new ArrayList<Float>();
        direc = new ArrayList<String>();

        try {
            JSONObject json= (JSONObject) new JSONTokener(data).nextValue();
            JSONArray json2 = json.getJSONArray("result");
            Log.d("postext", "onPostExecute: "+json2);
            for (int i = 0; i < json2.length(); i++) {
                JSONObject JO = (JSONObject) json2.get(i);
                singleParsed = JO.get("index") + " Direction: " + JO.get("direction") + "\n" + JO.get("distance") + "\n";
                String tp[] = String.valueOf(JO.get("direction")).split("\\s+");
                Log.d("tp", "onPostExecute: "+ Arrays.toString(tp));

                direc.add(tp[0]+" "+tp[1]);

                String dp[] = String.valueOf(JO.get("distance")).split("\\s+");
                Log.d("dp", "onPostExecute: "+Arrays.toString(dp));
                float distance=0;
                if(String.valueOf(JO.get("distance")).contains("k")){
                    distance = Float.parseFloat(dp[0])*1000;
                }else{
                    distance =Float.parseFloat(dp[0]);
                }
                dist.add(distance);



                dataparsed = dataparsed + singleParsed;
            }



            CameraActivity.tt.setText(dataparsed);
            Log.d("direcarray", "onPostExecute: "+direc);
            Log.d("distarray", "onPostExecute: "+dist);


             direc.add("Destination  Reached");
            //
            SensorListener.newStepCounter=0;
            SensorListener.stepCounter=0;
            SensorListener.currentStepsDetected=0;

            CameraActivity.startTracking(direc,dist);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ArrayList<Float> getDistance(){
        return dist;
    }
    ArrayList<String> getDirection(){
        return direc;
    }

}
