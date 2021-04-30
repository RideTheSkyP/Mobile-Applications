package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.detailed_pic_fragment.*
import kotlinx.android.synthetic.main.pic_property_fragment.*

class ScndActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
                val fragment = DetailedImgFragment()
                fragment.arguments = imgParameters

                val propertiesFragment = ImgPropertiesFragment()
                val propertiesParams = Bundle()
                propertiesParams.putParcelable("item", itm)
                propertiesFragment.arguments = propertiesParams
                transaction.add(R.id.detailLayout, fragment, "f1")
                transaction.add(R.id.detailLayout, propertiesFragment, "f2")

                transaction.commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        Log.d("infod", "Wsteczny dziala")
        val desc = description.text.toString()
        val rate = detailRatingBar.rating
        val id = detailedImage.id
        var f = MyPicItem(id, desc, rate)
        val myIntent = Intent()

        myIntent.putExtra("after", f)
        setResult(Activity.RESULT_OK, myIntent)
        finish()
        super.onBackPressed()
    }
}