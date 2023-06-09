package com.example.projet_esimed_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import android.util.Base64
import org.json.JSONObject


class AccueilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)
        // Header

        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(resources.getDrawable(R.color.rose))
        }
        // Navbar
        val homeButton = findViewById<Button>(R.id.homeButton)
        val coursesButton = findViewById<Button>(R.id.coursesButton)
        val groupsButton = findViewById<Button>(R.id.groupsButton)

        // Recherche
        val searchInput = findViewById<TextView>(R.id.searchInput)
        val searchButton = findViewById<Button>(R.id.searchButton)

        // Deconnexion
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Card 1

        val lessonTitle = findViewById<TextView>(R.id.lessonTitle)
        val lessonDescription = findViewById<TextView>(R.id.lessonDescription)
        val lessonButton = findViewById<Button>(R.id.lessonButton)

        // Card 2
        val groupsTitle = findViewById<TextView>(R.id.groupsTitle)
        val groupsList = findViewById<LinearLayout>(R.id.groupsList)
        val createGroupButton = findViewById<Button>(R.id.createGroupButton)

        // Modal
        val logoutModal = findViewById<View>(R.id.logoutModal)
        val cancelLogoutButton = findViewById<Button>(R.id.cancelLogoutButton)
        val confirmLogoutButton = findViewById<Button>(R.id.confirmLogoutButton)

        // Set click listeners
        homeButton.setOnClickListener { /* Handle home button click */ }
        coursesButton.setOnClickListener { /* Handle courses button click */ }
        groupsButton.setOnClickListener { /* Handle groups button click */ }
        searchButton.setOnClickListener { /* Handle search button click */ }
        logoutButton.setOnClickListener { logoutModal.visibility = View.VISIBLE }
        cancelLogoutButton.setOnClickListener { logoutModal.visibility = View.GONE }

        //Confirme de ce déconnecter -> vide le token puis redirige vers loginActivity
        confirmLogoutButton.setOnClickListener {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()
        editor.putString("token", "") // Efface le token en le remplaçant par une chaîne vide
        editor.apply()
        val intent = Intent(this@AccueilActivity, LoginActivity::class.java)
        startActivity(intent)
        }


        var str: String? = ""
        if (intent != null) {
            if (intent.hasExtra("token")) { // vérifie qu'une valeur est associée à la clé “token”
                str = intent.getStringExtra("token") // on récupère la valeur associée à la clé
            }
        }
        //Redirige vers createGroupeActivity si click sur le bouton 'créer un groupe'
        createGroupButton.setOnClickListener {

            val BearerToken = "Bearer ${str}"
            val intent = Intent(this@AccueilActivity, CreatgroupActivity::class.java)
            intent.putExtra("token", BearerToken)
            intent.putExtra("tokenonly", str)
            startActivity(intent)
        }

        lessonButton.setOnClickListener {
            try{
                FindUserConnected(str!!);
                Log.d("TAG:",  "SUCCES" )}
            catch (e : Exception){
                Log.e("TAG:",  "ECHEC" + e )
            }
        }
    }

}














fun FindUserConnected (Token : String) : String{


     val userConnectedId = ""
    fun decodeJwt(token: String): JSONObject? {
        val jwtParts = token.split(".")
        if (jwtParts.size != 3) {
            // Token invalide
            return null
        }

        val payloadBase64 = jwtParts[1]
        val payloadBytes = Base64.decode(payloadBase64, Base64.DEFAULT)
        val payloadString = payloadBytes.toString(charset("UTF-8"))

        return try {
            JSONObject(payloadString)
        } catch (e: Exception) {
            // Erreur lors du décodage du JSON
            null
        }
    }



    val jwtToken = Token

    val decodedPayload = decodeJwt(jwtToken)

    if (decodedPayload != null) {
        val username = decodedPayload.getString("nom")
        val userId = decodedPayload.getString("id")

        // Fais ce que tu souhaites avec les informations extraites du token
        Log.d("TAG:",  "Prenom" + username)
        Log.d("TAG:",  "ID" + userId)
        return userId
    } else {
        Log.d("TAG:",  "Erreur récupération des prenom et Id" )
    }
    return userConnectedId
}




// Card


// Lecon
