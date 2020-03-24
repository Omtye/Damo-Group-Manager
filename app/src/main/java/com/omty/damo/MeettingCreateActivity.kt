package com.omty.damo

import android.os.AsyncTask
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meetting_create)



        confirm_meetting.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val name: String = meetting_title.text.toString()
                val country: String = meetting_category.text.toString()
            }
        })

    }
}


class InsertData : AsyncTask<String, Void, String>(){

    override fun doInBackground(vararg params: String?): String {
        val name: String? = params[1]
        val country : String? = params[2]

        val serverURL : String? = params[0]
        val postParameters : String = "name=$name&country=$country"

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


            return sb.toString();

        }catch (e : Exception){
            return "Error" + e.message
        }

    }

}
