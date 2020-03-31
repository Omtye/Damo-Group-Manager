package com.omty.damo


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.meetting_create.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL


class MeettingCreateActivity : AppCompatActivity(){

    private val IP_ADDRESS = "10.0.2.2"
    private val TAG = "phptest"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meetting_create)

        /*카테고리 항목 달기*/
        val COUNTRIES = arrayOf<String?>("Item 1", "Item 2", "Item 3", "Item 4")

        val adapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            applicationContext,
            R.layout.drapdown_category,
            COUNTRIES
        )

        val editTextFilledExposedDropdown: AutoCompleteTextView = filled_exposed_dropdown

        editTextFilledExposedDropdown.setAdapter(adapter)




    }


}


class InsertData : AsyncTask<String, Void, String>(){

    override fun doInBackground(vararg params: String?): String {
        val title: String? = params[1]

        val serverURL : String? = params[0]
        val postParameters : String = "title=$title"

        Log.d("testt","title : "+title)

        try{
            val url = URL(serverURL)
            val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection


            httpURLConnection.readTimeout = 5000
            httpURLConnection.connectTimeout = 5000
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.connect()


            val outputStream: OutputStream = httpURLConnection.outputStream
            outputStream.write(postParameters.toByteArray(charset("UTF-8")))
            outputStream.flush()
            outputStream.close()

            val responseStatusCode : Int = httpURLConnection.responseCode


            val inputStream: InputStream
            inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                httpURLConnection.inputStream
            } else {
                httpURLConnection.errorStream
            }


            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            val bufferedReader = BufferedReader(inputStreamReader)

            val sb = StringBuilder()
            var line: String? = null

            while (bufferedReader.readLine().also({ line = it }) != null) {
                sb.append(line)
            }

            bufferedReader.close();

            Log.d("testt","doinbackground"+sb.toString())
            return sb.toString();

        }catch (e : Exception){
            return "Error" + e.message
        }

    }

}
