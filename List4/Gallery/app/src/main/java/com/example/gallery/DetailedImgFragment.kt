package com.example.gallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.detailed_pic_fragment.*

class DetailedImgFragment : Fragment()
{

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(
            R.layout.detailed_pic_fragment,
            container, false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        val b = arguments
        if (b != null)
        {
            val imgId = b.getInt("picId")
            detailedImage.setImageDrawable(ContextCompat.getDrawable(context!!, imgId))
        }
    }
}