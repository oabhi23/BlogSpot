package com.example.abhi.blogspot.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.abhi.blogspot.R
import com.example.abhi.blogspot.model.User
import com.example.abhi.blogspot.ui.feed.ArticleFeedActivity
import com.example.abhi.blogspot.ui.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.log_in_module.*
import kotlinx.android.synthetic.main.sign_up_module.*
import javax.inject.Inject

class LoginActivity: BaseActivity(), LoginMvpView {

    private var onLogIn = true
    @Inject lateinit var loginPresenter: LoginPresenter
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()

        getActivityComponent()?.let {
            it.inject(this)
            loginPresenter.attachView(this)
        }

        setContentView(R.layout.activity_login)

        log_in_button.setOnClickListener {
            log_in_underline.visibility = View.VISIBLE
            sign_up_underline.visibility = View.GONE

            log_in_container.visibility = View.VISIBLE
            sign_up_container.visibility = View.GONE

            onLogIn = true
        }

        sign_up_button.setOnClickListener {
            sign_up_underline.visibility = View.VISIBLE
            log_in_underline.visibility = View.GONE

            log_in_container.visibility = View.GONE
            sign_up_container.visibility = View.VISIBLE

            onLogIn = false
        }

        //log in values
        val login_container: View = findViewById(R.id.log_in_container)
        val email_login = login_container.findViewById<TextInputEditText>(R.id.email)
        val password_login = login_container.findViewById<TextInputEditText>(R.id.password)

        //sign up values
        val signup_container: View = findViewById(R.id.sign_up_container)
        val name_signup = signup_container.findViewById<TextInputEditText>(R.id.name)
        val email_signup = signup_container.findViewById<TextInputEditText>(R.id.email)
        val password_signup = signup_container.findViewById<TextInputEditText>(R.id.password)

        go_click.setOnClickListener {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(go_click.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)

            //multiple clicks of go are not allowed
            go_click.isClickable = false

            if (onLogIn) {
                loginPresenter.authenticateUser(mAuth, this, email_login.text.toString(),
                    password_login.text.toString())
            } else {
                loginPresenter.signupUser(mAuth, this, name_signup.text.toString(),
                    email_signup.text.toString(), password_signup.text.toString())
            }
        }
    }

    override fun startArticleFeedIntent(user: User?) {
        val articleFeedIntent = Intent(this, ArticleFeedActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("user_object", user)
        articleFeedIntent.putExtra("bundle_user", bundle)
        startActivity(articleFeedIntent)
    }

}