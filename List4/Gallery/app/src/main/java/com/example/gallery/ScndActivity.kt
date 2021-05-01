package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class ScndActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            if (intent.extras != null) {
                val itm: MyPicItem = intent.extras.getParcelable("item")
                val imgParameters = Bundle()
                imgParameters.putInt("picId", itm.imgId)
                val fragment : Fragment = DetailedImgFragment()
                fragment.arguments = imgParameters

                val propertiesFragment : Fragment = ImgPropertiesFragment()
                val propertiesParams = Bundle()
                propertiesParams.putParcelable("item", itm)
                propertiesFragment.arguments = propertiesParams
                transaction.add(R.id.detailLayout, fragment, "f1")
                transaction.add(R.id.detailLayout, propertiesFragment, "f2")
                transaction.commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean
    {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed()
    {
        val desc = findViewById<TextView>(R.id.description).text.toString()
        val rate = findViewById<RatingBar>(R.id.detailRatingBar).rating
        val id = R.id.detailedImage
        val f = MyPicItem(id, desc, rate)
        val myIntent = Intent()

        myIntent.putExtra("after", f)
        setResult(Activity.RESULT_OK, myIntent)
        finish()
        super.onBackPressed()
    }
}