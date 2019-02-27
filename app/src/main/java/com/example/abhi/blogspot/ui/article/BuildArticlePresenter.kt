package com.example.abhi.blogspot.ui.article

import android.content.ContentValues.TAG
import android.net.Uri
import com.example.abhi.blogspot.injections.ActivityScoped
import com.example.abhi.blogspot.model.Article
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.base.BasePresenter
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

@ActivityScoped
class BuildArticlePresenter @Inject
constructor(): BasePresenter<BuildArticleMvpView>() {
    val ref = FirebaseDatabase.getInstance().reference
    val articleRef = ref.child("articles")

    fun publishArticle(authUser: User, title: String, content: String, photoUri: Uri) {
        //generate article_list_element id
        val articleUid = FirebaseDatabase.getInstance().reference.push().key

        //generate path for image
        val storageReference = FirebaseStorage.getInstance().reference
            .child("/$articleUid.jpg")


        //upload image in firebase storage and retrieve its url into article_list_element
        storageReference.putFile(photoUri).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->
//                Log.d(TAG, "onSuccess: uri= " + uri.toString())
                val article = Article(authUser, title, uri.toString(), articleUid!!, content)
                articleRef.child(articleUid!!).setValue(article) //push article_list_element object to db
            }
        }
    }
}
