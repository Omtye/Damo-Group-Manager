package com.omty.damo

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
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



        confirm_meetting.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val title: String = meetting_title.text.toString()

                Log.d("testt","ClickOnclickListener")
                val task = InsertData()
                task.execute("http://" + IP_ADDRESS.toString() + "/insert.php", title)
            }
        })

    }
}


class InsertData : AsyncTask<String, Void, String>(){



    override fun doInBackground(vararg params: String?): String {
        val title: String? = params[1]

        val serverURL : String? = params[0]
        val postParameters : String = "title=$title"

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
