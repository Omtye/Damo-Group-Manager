package com.omty.damo


import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.meetting_create.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL


private var categoryJsonString : String? = null
private var CATEGORY : ArrayList<String?>? = arrayListOf<String?>()
private var jsonArray: JSONArray? = null

class MeettingCreateActivity : AppCompatActivity(){

    private val IP_ADDRESS = "10.0.2.2"

    private val MAX_USER_CNT = arrayListOf<Int?>(10,20,30,40,50)
    private val OPEN_FLAGS = arrayListOf<String?>("YES","NO")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.meetting_create)


        val task = getData()
        task.execute("http://$IP_ADDRESS/getCategoryJson.php", "")


        /*카테고리 항목 달기*/
        var categoryAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            applicationContext,
            R.layout.drapdown,
            CATEGORY!!
        )


        var category: AutoCompleteTextView = category
        category.setAdapter(categoryAdapter)

        /*최대인원수 항목 달기*/
        var maxUserCntAdapter: ArrayAdapter<Int?> = ArrayAdapter<Int?>(
            applicationContext,
            R.layout.drapdown,
            MAX_USER_CNT
        )
        var maxUserCnt: AutoCompleteTextView = maxUserCnt
        maxUserCnt.setAdapter(maxUserCntAdapter)

        /*공개여부 항목 달기*/
        var isOpenAdapter: ArrayAdapter<String?> = ArrayAdapter<String?>(
            applicationContext,
            R.layout.drapdown,
            OPEN_FLAGS
        )
        var isOpen: AutoCompleteTextView = isOpen
        isOpen.setAdapter(isOpenAdapter)


        confirm_meetting.setOnClickListener{
            val meetingName : String = meetingName.text.toString()
            val categorySeq : String = category.text.toString()
            val maxUserCnt  : String = maxUserCnt.text.toString()
            val isOpen      : String = isOpen.text.toString()
            val comments    : String = comments.text.toString()

            val task = InsertData()
            task.execute("http://$IP_ADDRESS/insert.php", meetingName, categorySeq, maxUserCnt, isOpen, comments)



        }




    }

    private class InsertData : AsyncTask<String, Void, String>(){


        override fun doInBackground(vararg params: String?): String {

            val serverURL   : String? = params[0]
            val meetingName : String? = params[1]
            val maxUserCnt  : String? = params[3]
            val comments    : String? = params[5]
            var categorySeq : String? = null
            var isOpen      : String? = null
            var nowUserCnt  : String? = "1"

            for (i in 0 until jsonArray!!.length()){
                if(jsonArray!!.getJSONObject(i).getString("categoryName") == params[2])
                    categorySeq = jsonArray!!.getJSONObject(i).getString("categorySeq")
            }

            if (params[4] == "YES")
                isOpen = "1"
            else
                isOpen = "0"

            Log.d("check","meetingName : " + meetingName + " maxUserCnt : " + maxUserCnt+ " isOpen : " + isOpen + " comments : " + " categorySeq : " + categorySeq)

            val postParameters : String = "meetingName=$meetingName&categorySeq=$categorySeq&comments=$comments&maxUserCnt=$maxUserCnt&nowUserCnt=$nowUserCnt&isOpen=$isOpen"





            try{
                val url = URL(serverURL)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.readTimeout    = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod  = "POST"
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

                Log.d("Check","success" + sb.toString())
                return sb.toString();

            }catch (e : Exception){
                Log.d("Check","Error" + e.message)
                return "Error" + e.message
            }

        }

    }

    private class getData : AsyncTask<String, Void, String>() {

        var errorString: String? = null

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Log.d("check","onPostExecute")
            if (result == null) {

                return
            } else {
                categoryJsonString = result
                showResult()
            }
        }

        override fun doInBackground(vararg params: String?): String {
            val serverURL = params[0]
            val postParameters = params[1]

            Log.d("check","doInBackgrond")
            return try {
                val url = URL(serverURL)
                val httpURLConnection = url.openConnection() as HttpURLConnection



                httpURLConnection.readTimeout    = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod  = "POST"
                httpURLConnection.doInput = true
                httpURLConnection.connect()


                val outputStream = httpURLConnection.outputStream
                outputStream.write(postParameters!!.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()


                val responseStatusCode = httpURLConnection.responseCode

                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream
                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)
                val sb = java.lang.StringBuilder()
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    sb.append(line)
                }

                bufferedReader.close()

                sb.toString().trim { it <= ' ' }
            } catch (e: Exception) {
                e.toString()
            }
        }

    }


}

private fun showResult() : Unit {

    val TAG_JSON : String = "category"
    val TAG_CategorySeq  : String = "categorySeq"
    val TAG_CategoryName : String = "categoryName"



    try {
        val jsonObject = JSONObject(categoryJsonString)
        jsonArray = jsonObject.getJSONArray(TAG_JSON)


        for (i in 0 until jsonArray!!.length()){

            val item = jsonArray!!.getJSONObject(i)
            CATEGORY!!.add(item.getString(TAG_CategoryName))

        }

    }catch (e : JSONException){

    }


}






