package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.json.JSONArray
import java.io.File


class EditionActivity : AppCompatActivity()
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
                val item: MyPicItem = intent.extras.getParcelable("item")
                val imgParameters = Bundle()
                imgParameters.putInt("picId", item.imgId)
                val fragment : Fragment = DetailedImgFragment()
                fragment.arguments = imgParameters

                val propertiesFragment : Fragment = ImgPropertiesFragment()
                val propertiesParams = Bundle()
                propertiesParams.putParcelable("item", item)
                propertiesFragment.arguments = propertiesParams
                transaction.add(R.id.detailLayout, fragment, "f1")
                transaction.add(R.id.detailLayout, propertiesFragment, "f2")
                transaction.commit()
            }
        }
    }

    override fun onResume()
    {
        super.onResume()
    }

    override fun onPause()
    {
        super.onPause()
    }

    override fun onStop()
    {
        super.onStop()

    }

    override fun onRestart()
    {
        super.onRestart()
    }

    override fun onDestroy()
    {
        super.onDestroy()
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