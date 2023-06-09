package com.example.projet_esimed_mobile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class CreatgroupActivity : AppCompatActivity() {

    private lateinit var addUserLink: TextView
    private lateinit var addUserWrapper: LinearLayout
    private var count: Int = 1
    var idgroupe = ""
    var iduser = ""
    var end = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.creategroup_activity)
        val editNomGroup = findViewById<EditText>(R.id.nomGroupeEditText)
        val editNomUser = findViewById<EditText>(resources.getIdentifier("utilisateur${count}EditText", "id", packageName))
        val editNameUser = findViewById<EditText>(R.id.utilisateur1EditText)



        supportActionBar?.let { actionBar ->
            actionBar.setBackgroundDrawable(resources.getDrawable(R.color.rose))
        }
        addUserLink = findViewById(R.id.ajouterNouvelleTextView)
        addUserWrapper = findViewById(R.id.utilisateursLayout)
        val createButton = findViewById<Button>(R.id.validerButton)
        val finishButton = findViewById<Button>(R.id.terminerButton)
        val ajouterButton = findViewById<Button>(R.id.ajouterButton)


        //Créer un groupe avec le nom de groupe saisi au click sur 'créer un groupe'
        createButton.setOnClickListener {
            val nomGroupe = editNomGroup.text.toString()
            CarService.Car.userAccountRoutes.createGroupe(nomGroupe).enqueue(object : Callback<AuthenticationResult> {
                override fun onResponse(call: Call<AuthenticationResult>, response: Response<AuthenticationResult>) {
                    val layoutUtilisateur =  findViewById<View>(R.id.utilisateursLayout)
                    layoutUtilisateur.visibility = View.VISIBLE
                }

                override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
                    Log.e("TAG", "Error: " + t);
                    Log.d("TAG", "Impossible de créer un groupe : Erreur serveur " );
                    Tools.displayError(this@CreatgroupActivity, R.string.error_connection_failed)
                }

            })}
        var str: String? = ""
        var tokenonly: String? = ""
        if (intent != null) {

            if (intent.hasExtra("token")) { // vérifie qu'une valeur est associée à la clé “token”
                str = intent.getStringExtra("token") // token = 'Bearer ${token}
            }
            if (intent.hasExtra("tokenonly")) {
                tokenonly = intent.getStringExtra("token") // tokenonly = '${token}
            }
        }
        var myString = str !!
        fun findUser (){
            val nomuser = editNomUser.text.toString()
            val nomGroupe = editNomGroup.text.toString()
            CarService.Car.userAccountRoutes.findUserByPseudo(nomuser,myString).enqueue(object :
                Callback<GetResult> {
                override fun onResponse(call: Call<GetResult>, response: Response<GetResult>) {
                    val getResult = response.body()
                    iduser = getResult?.id !!
                    Log.d("TAG", "Value of pseudo: " + nomuser);
                    Log.d("TAG", "Value of response : " + response);
                    Log.d("TAG", "token " + myString );
                    Log.d("TAG", "RESPONSE BODY " + getResult );
                    return ;
                } override fun onFailure(call: Call<GetResult>, t: Throwable) {

                    Log.e("TAG", "Error: " + t);
                    Log.d("TAG", "Impossible de Trouver le user : Erreur serveur " );
                    Tools.displayError(this@CreatgroupActivity, R.string.error_connection_failed)
                }})}
        fun findGroup (){
            val nomGroupe = editNomGroup.text.toString()
            CarService.Car.userAccountRoutes.findGroupByName(nomGroupe,myString).enqueue(object :
                Callback<GetResultGroup> {
                override fun onResponse(call: Call<GetResultGroup>, response: Response<GetResultGroup>) {
                    Log.d("TAG", "RESPONSE BODY GROUPE " + response.body() );
                    val getResultGroup = response.body()
                    Log.d("TAG", "Value of pseudo: " + nomGroupe);
                    Log.d("TAG", "Value of email TOP: " + response);
                    val idgroup = getResultGroup?.id
                    idgroupe = idgroup !!
                } override fun onFailure(call: Call<GetResultGroup>, t: Throwable) {

                    Log.e("TAG", "Error: " + t);
                    Log.d("TAG", "Impossible de Trouver le Groupe : Erreur serveur " );
                    Tools.displayError(this@CreatgroupActivity, R.string.error_connection_failed)
                }})}


        fun addInGroup (finish : Boolean , userId : String){

            val resultat =  findUser ();
            Log.d("TAG", "RESULTAT BUTTON " + resultat );
            findGroup();
            if (finish == false){
                CarService.Car.userAccountRoutes.addUserInGroup(idgroupe,iduser).enqueue(object :
                    Callback<AuthenticationResult> {
                    override fun onResponse(call: Call<AuthenticationResult>, response: Response<AuthenticationResult>) {
                        Toast.makeText(
                            this@CreatgroupActivity,
                            "Utilisateur ${count} ajouté avec succes",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("TAG", "Ajout du user avec l'id ${iduser} au group avec l'id ${idgroupe}" );
                    }
                    override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
                        Log.e("TAG", "Error: " + t);
                        Log.d("TAG", "Impossible d'Ajouter le user au groupe' : Erreur serveur " );
                        Tools.displayError(this@CreatgroupActivity, R.string.error_connection_failed)
                    }
                })}
            else if (finish == true){CarService.Car.userAccountRoutes.addUserInGroup(idgroupe,userId).enqueue(object :
                Callback<AuthenticationResult> {
                override fun onResponse(call: Call<AuthenticationResult>, response: Response<AuthenticationResult>) {
                    Toast.makeText(
                        this@CreatgroupActivity,
                        "Le Groupe à été créé avec succes",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG", "Ajout du user avec l'id ${iduser} au group avec l'id ${idgroupe}" );
                }
                override fun onFailure(call: Call<AuthenticationResult>, t: Throwable) {
                    Log.e("TAG", "Error: " + t);
                    Log.d("TAG", "Impossible d'Ajouter le user connecté au groupe' : Erreur serveur " );
                    Tools.displayError(this@CreatgroupActivity, R.string.error_connection_failed)
                }
            })}


        }
        ajouterButton.setOnClickListener {addInGroup(end , iduser)}
        addUserLink.setOnClickListener {  count++
            val newField = LinearLayout(this)
            newField.orientation = LinearLayout.HORIZONTAL
            newField.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val label = TextView(this)
            label.text = "Utilisateur $count:"
            label.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val editText = EditText(this)
            val editTextId ="utilsateur${count}EditText "
            editText.id = resources.getIdentifier(editTextId, "id", packageName)
            editText.hint = "Entrez le pseudo de l'utilisateur $count"
            editText.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )


            val button = Button(this)
            button.text = "Ajouter"
            button.setOnClickListener { addInGroup(end , iduser); }

            newField.addView(label)
            newField.addView(editText)
            newField.addView(button)

            addUserWrapper.addView(newField) }
        finishButton.setOnClickListener {
            end = true
            if (tokenonly != ""){
                val userConnected = FindUserConnected(tokenonly !!)
                addInGroup(end , userConnected);
            }
            val intent = Intent(this@CreatgroupActivity, AccueilActivity ::class.java)
            startActivity(intent)
        }
    }


}
