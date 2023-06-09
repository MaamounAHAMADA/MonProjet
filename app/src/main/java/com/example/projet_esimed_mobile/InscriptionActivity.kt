package com.example.projet_esimed_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InscriptionActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inscription_activity)

        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(resources.getDrawable(R.color.rose))
        }
        val validerButton = findViewById<Button>(R.id.validerButton)
        validerButton.setOnClickListener {
            // Code pour traiter le clic sur le bouton Valider
            val editTextEmail = findViewById<EditText>(R.id.email)
            val editTextMdp = findViewById<EditText>(R.id.motdepasse)
            val editTextPseudo = findViewById<EditText>(R.id.pseudo)
            val editTextNom = findViewById<EditText>(R.id.nom)
            val editTextPrenom = findViewById<EditText>(R.id.prenom)
            val email = editTextEmail.text.toString()
            val motDePasse = editTextMdp.text.toString()
            val prenom = editTextPrenom.text.toString()
            val pseudo = editTextPseudo.text.toString()
            val nom = editTextNom.text.toString()



            CarService.Car.userAccountRoutes.inscription(nom, prenom, email, motDePasse, pseudo)
                .enqueue(object :
                    Callback<AuthenticationResult> {
                    override fun onResponse(
                        call: Call<AuthenticationResult>,
                        response: Response<AuthenticationResult>
                    ) {
                        // handle the response
                        Log.d("TAG", "Value of response: " + response);
                        val result = response.body()
                        if (response.isSuccessful) {
                            if (result != null ) {
                                // Afficher un message si tout est bon
                                Log.d("TAG", "Value of the response: " + result);
                                Toast.makeText(
                                    this@InscriptionActivity,
                                    "Inscription effectuée",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@InscriptionActivity, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Log.d("TAG", "Value of the yee: " + response );
                                Tools.displayError(
                                    this@InscriptionActivity,
                                    R.string.error_empty_token
                                )
                            }
                        }
                    }


                    //     call.enqueue(object : Callback<AuthenticationResult> {


                    override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
                        Log.e("TAG", "Error: " + t);
                        // Afficher un message d'erreur en cas d'échec de la connexion
                        Tools.displayError(
                            this@InscriptionActivity,
                            R.string.error_connection_failed
                        )
                    }
                })
        }
    }

}

