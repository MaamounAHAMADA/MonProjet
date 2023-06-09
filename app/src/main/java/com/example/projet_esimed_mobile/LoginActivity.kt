package com.example.projet_esimed_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.view.View
import android.util.Log;
import android.widget.EditText
import android.content.Intent
import android.content.ComponentName
import com.example.projet_esimed_mobile.AccueilActivity


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(resources.getDrawable(R.color.rose))
        }


        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val buttonConnect = findViewById<Button>(R.id.buttonConnect)
        val buttonInscritpion = findViewById<Button>(R.id.signupButton)
        val jwtToken = JWTToken.getInstance(this)
        val editTextEmail = findViewById<EditText>(R.id.editTextLogin)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)


        buttonConnect.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = editTextEmail.text.toString()
            val motDePasse = editTextPassword.text.toString()

            //Envoyer la requête avec la métohde postAuthenticate grâce à l'instance retrofit 'userAccountRoutes'
            CarService.Car.userAccountRoutes.postauthenticate(email, motDePasse).enqueue(object : Callback<AuthenticationResult> {
            override fun onResponse(call: Call<AuthenticationResult>, response: Response<AuthenticationResult>) {
                    if (response.isSuccessful) {
                        Log.d("TAG", "Value of email : " + email);
                        Log.d("TAG", "Value of motDePasse : " + motDePasse);

                        progressBar.visibility = View.INVISIBLE
                        val authenticationResult = response.body()
                        if (authenticationResult != null ) {
                            // Afficher le message si tout est bon
                            jwtToken.tokenWithBearer = jwtToken.toString();
                            val usertoken = jwtToken.toString()
                            Log.d("TAG", "Value of token RESPONSE: " + authenticationResult.token);
                            Toast.makeText(
                                this@LoginActivity,
                                "Authentification réussie",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Redirection vers une  Acceuilactivity
                            val intent = Intent(this@LoginActivity,AccueilActivity::class.java)
                            intent.putExtra("token", authenticationResult.token)
                            startActivity(intent)

                        } else {
                            progressBar.visibility = View.INVISIBLE
                            // Afficher un message d'erreur si le champ token est vide
                            jwtToken.tokenWithBearer = ""
                            Tools.displayError(this@LoginActivity, R.string.error_empty_token)
                        }
                    }
            }


            override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {

                Log.e("TAG", "Error: " + t);
                Log.d("TAG", "Value of email: " + email);
                Log.d("TAG", "Value of motDepasse: " + motDePasse);
                progressBar.visibility = View.INVISIBLE
                // Afficher un message d'erreur en cas d'échec de la connexion
                jwtToken.tokenWithBearer = ""
                Tools.displayError(this@LoginActivity, R.string.error_connection_failed)
            }
        })
    }

    //Redirection vers InscriptionActivity
    buttonInscritpion.setOnClickListener {
        val intent = Intent(this@LoginActivity,InscriptionActivity::class.java)
        startActivity(intent)
    }

    }}

