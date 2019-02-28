package com.example.abhi.blogspot.ui.build

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_build_article.*
import javax.inject.Inject
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import com.example.abhi.blogspot.ui.feed.ArticleFeedActivity
import com.google.firebase.storage.FirebaseStorage


class BuildArticleActivity: BaseActivity(), BuildArticleMvpView {

    @Inject lateinit var buildArticlePresenter: BuildArticlePresenter
    private lateinit var authUser: User

    private val firebaseStorage = FirebaseStorage.getInstance()
    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        getActivityComponent()?.let {
            it.inject(this)
            buildArticlePresenter.attachView(this)
        }

        val bundle = intent.getBundleExtra("bundle_user")
        authUser = bundle.getParcelable("user_object") as User

        setContentView(R.layout.activity_build_article)

        author.text = authUser.name
        var title: TextInputEditText = findViewById(R.id.title)
        var content: TextInputEditText = findViewById(R.id.content)

        btn_load_image.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
            btn_load_image.visibility = View.GONE
        }

        //save article_list_element
        btn_publish.setOnClickListener {
            buildArticlePresenter.publishArticle(authUser, title.text.toString(),
                content.text.toString(), photoUri)

            //head back to feed
            val feedIntent = Intent(this@BuildArticleActivity, ArticleFeedActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("user_object", authUser)
            feedIntent.putExtra("bundle_user", bundle)
            startActivity(feedIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            photoUri = data!!.data
            if (photoUri != null) {
                try {
                    val currentImage = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
                    image.setImageBitmap(currentImage)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}