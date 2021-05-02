package com.example.gallery

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat


class DetailedImgFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.detailed_pic_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        val args = arguments
        if (args != null)
        {
            val imgId = args.getInt("picId")
            view?.findViewById<ImageView>(R.id.detailedImage)?.setImageDrawable(ContextCompat.getDrawable(requireContext(), imgId))
        }
    }
}