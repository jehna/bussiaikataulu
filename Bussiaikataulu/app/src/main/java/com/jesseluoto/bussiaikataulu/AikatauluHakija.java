package com.jesseluoto.bussiaikataulu;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jesseluoto on 09/02/16.
 */
public class AikatauluHakija {

    private ArrayList<AikatauluAika> ajat = new ArrayList<>();

    public AikatauluHakija(int linjaKoodi) {
        //2411240
        try {
            URL url = new URL(String.format("http://api.reittiopas.fi/hsl/prod/?request=stop&code=%d&user=aikataulunaytto&pass=computer&time_limit=360&dep_limit=20", linjaKoodi));
            InputStream istream = url.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(istream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            JSONArray departures = json.getJSONObject(0).getJSONArray("departures");
            for (int i = 0; i  < departures.length(); i++) {
                JSONObject departure = departures.getJSONObject(i);
                String code = departure.getString("code");
                if (code.indexOf("150") != 1) {
                    continue;
                }

                this.ajat.add(new AikatauluAika(departure.getInt("time"), code.substring(1,code.length()-1)));
            }
        } catch (MalformedURLException e) {
            Log.e("jesse", "Vituiks man 1");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("jesse", "Vituiks man 2");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("jesse", "Vituiks man 3");
            e.printStackTrace();
        }
    }

    public ArrayList<AikatauluAika> getAikataulut() {
        return this.ajat;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}

