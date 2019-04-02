package com.tpo_hr.tpohr.views.activities.slide.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tpo_hr.tpohr.R
import com.tpo_hr.tpohr.views.activities.main.MainActivity
import kotlinx.android.synthetic.main.fragment_slide_three.*

class SlideThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_slide_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSkip.setOnClickListener {
            activity?.startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }


}
