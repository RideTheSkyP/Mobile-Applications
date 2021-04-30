package com.example.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.pic_property_fragment.*

class ImgPropertiesFragment : Fragment()
{

    var desc = ""
    var rate = 0f

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(
            R.layout.pic_property_fragment,
            container, false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        detailRatingBar.setIsIndicator(false)
        val b = arguments
        if (b != null)
        {
            var item: MyPicItem = b.getParcelable("item")
            desc = item.desc
            rate = item.rating

            detailRatingBar.rating = rate
            description.text = desc

            description.setOnLongClickListener {
                editDescription()
            }
        }
    }

    fun editDescription() : Boolean
    {
        val builder = AlertDialog.Builder(activity)
        val et = EditText(activity)
        et.text = SpannableStringBuilder(desc)
        et.setSelectAllOnFocus(true)
        builder.setTitle("Change title")
        builder.setPositiveButton("done") {_, _ ->
            desc = et.text.toString()
            description.text = desc
        }
        builder.setView(et)
        builder.show()
        return true
    }
}