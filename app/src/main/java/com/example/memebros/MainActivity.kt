package com.example.memebros

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageurl:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intro()
    }

    fun intro(){
        Handler().postDelayed(Runnable { loadmeme() }, 5000)
    }


    private fun loadmeme(){
        val progress=findViewById<ProgressBar>(R.id.progress)
        progress.visibility=View.VISIBLE        //to ensure that the user sees the progress bar revolving until the new meme is loading
    //    val meme  =findViewById<ImageView>(R.id.meme)
// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = com.android.volley.toolbox.JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    currentImageurl = response.getString("url")
                    Glide.with(this).load(currentImageurl).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?,
                                                  model: Any?,
                                                  target: Target<Drawable>?,
                                                  isFirstResource: Boolean): Boolean {
                            progress.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?,
                                                     model: Any?,
                                                     target: Target<Drawable>?,
                                                     dataSource: DataSource?,
                                                     isFirstResource: Boolean): Boolean {
                            progress.visibility = View.GONE
                            return false
                        }
                    }).into(findViewById<ImageView>(R.id.meme))
                },
                Response.ErrorListener {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
    fun shareMeme(view: View) {
    val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey MemeBro! Check this out $currentImageurl")
        val chooser=Intent.createChooser(intent, "Share this meme using......")
        startActivity(chooser)
    }

    fun nextMeme(view: View) {
    loadmeme()
    }


}