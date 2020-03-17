package com.omty.damo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_individual.*
import kotlinx.android.synthetic.main.fragment_individual.view.*


class IndividualFragment : Fragment(), View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view : View = inflater.inflate(R.layout.fragment_individual, container, false)

        view.create_meetting.setOnClickListener(this)


        return view
    }

    override fun onClick(v: View?) {
        when (v){
            create_meetting -> {

                startActivity(Intent(activity, MeettingCreateActivity::class.java))
            }
            else -> null
        }
    }
}