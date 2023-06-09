package com.example.projet_esimed_mobile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatecoursActivity : AppCompatActivity() {

    private lateinit var addUserLink: TextView
    private lateinit var addUserWrapper: LinearLayout
    private var count: Int = 1
    var idgroupe = ""
    var iduser = ""
    var end = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.createcours_activity)
        val editNomCours = findViewById<EditText>(R.id.CoursNom)





        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(resources.getDrawable(R.color.rose))
        }

        val createButton = findViewById<Button>(R.id.createButton)


        //Créer un groupe avec le nom de groupe saisi au click sur 'créer un groupe'
        createButton.setOnClickListener {
            val nomCours = editNomCours.text.toString()
            CarService.Car.userAccountRoutes.createCours(nomCours).enqueue(object :
                Callback<AuthenticationResult> {
                override fun onResponse(
                    call: Call<AuthenticationResult>,
                    response: Response<AuthenticationResult>
                ) {
                    val layoutUtilisateur = findViewById<View>(R.id.utilisateursLayout)
                    layoutUtilisateur.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
                    Log.e("TAG", "Error: " + t);
                    Log.d("TAG", "Impossible de créer un groupe : Erreur serveur ");
                    Tools.displayError(this@CreatecoursActivity, R.string.error_connection_failed)
                }

            })
        }

    }}