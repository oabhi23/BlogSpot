package com.example.abhi.blogspot.ui.login

import android.util.Log
import com.example.abhi.blogspot.injections.ActivityScoped
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.base.BasePresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

@ActivityScoped
class LoginPresenter @Inject
constructor(): BasePresenter<LoginMvpView>() {
    val ref = FirebaseDatabase.getInstance().reference
    var root = ref.child("users")

    /**
     * Authenticate login, send logged in user object to activity for future use
     */
    fun authenticateUser(mAuth: FirebaseAuth?, loginActivity: LoginActivity, email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(loginActivity) { task ->
                if (task.isSuccessful) {
                    var user: User? = null
                    root.addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            //intentionally left empty
                        }

                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                user = snapshot.child(mAuth.currentUser!!.uid).getValue(User::class.java)
                            }
                            baseView?.startArticleFeedIntent(user) //contact login activity, allow user to next activity
                        }
                    })
                } else {
                    Log.d("signinuser", "fail")
                }
            }
    }

    fun signupUser(mAuth: FirebaseAuth?, loginActivity: LoginActivity, name: String, email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(loginActivity) { task ->
                if (task.isSuccessful) {
                    val uid = mAuth.currentUser?.uid

                    root.child(uid!!).child("name").setValue(name)
                    root.child(uid!!).child("email").setValue(email)
                    root.child(uid!!).child("uid").setValue(uid)
                }
            }
    }

}
