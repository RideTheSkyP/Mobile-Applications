package com.example.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.pic_property_fragment.*

class ImgPropertiesFragment : Fragment()
{

    var desc = ""
    var rate = 0f

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.pic_property_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        detailRatingBar.setIsIndicator(false)
        val args = arguments
        if (args != null)
        {
            val item: MyPicItem? = args.getParcelable("item")
            if (item != null) {
                desc = item.desc
            }
            if (item != null) {
                rate = item.rating
            }

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
        val editText = EditText(activity)
        editText.text = SpannableStringBuilder(desc)
        editText.setSelectAllOnFocus(true)
        builder.setTitle("Change title")
        builder.setPositiveButton("done") {_, _ ->
            desc = editText.text.toString()
            description.text = desc
        }
        builder.setView(editText)
        builder.show()
        return true
    }
}