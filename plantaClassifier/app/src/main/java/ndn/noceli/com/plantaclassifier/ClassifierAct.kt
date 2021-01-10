package ndn.noceli.com.plantaclassifier

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_classifier.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import java.nio.file.Files.size
import android.content.pm.ResolveInfo
import android.support.v7.widget.Toolbar


class ClassifierAct : AppCompatActivity() {

    var realm = Realm.getDefaultInstance()
    var codChave: String = "0"

    var arraylist = ArrayList<PlantaRealm>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classifier)

        toolbaractclassifier.setNavigationOnClickListener{t->
            finish()
        }

        btnduvida.setOnClickListener { t ->
            val intent = Intent(this@ClassifierAct, pdfReader::class.java)
            startActivity(intent)
        }
        btnvalida.setOnClickListener { t ->

            if (rb1.isChecked) {
                val aux = arraylist.get(0).caminho1.toIntOrNull()
                if (arraylist.get(0).caminho1 == "chaveB") {
                    Toast.makeText(
                        this@ClassifierAct,
                        "mudamos a chave para B...",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    carregaInfoRealm(
                        "1"
                        , "2"
                    )

                } else if (aux != null) {
                    carregaInfoRealm(
                        arraylist.get(0).caminho1.toString()
                        , arraylist.get(0).codChave.toString()
                    )
                } else {
                    Toast.makeText(
                        this@ClassifierAct,
                        arraylist.get(0).caminho1,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (rb2.isChecked) {
                val aux = arraylist.get(1).caminho1.toIntOrNull()
                if (aux != null) {
                    carregaInfoRealm(
                        arraylist.get(1).caminho1.toString()
                        , arraylist.get(1).codChave.toString()
                    )
                } else {
                    Toast.makeText(
                        this@ClassifierAct,
                        arraylist.get(1).caminho1,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(this@ClassifierAct, "Selecione uma opção", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        codChave = getIntent().extras.get("chave").toString()
        carregaInfoRealm("1", codChave)
    }

    fun carregaInfoRealm(codIdentificacao: String, codChave: String) {
        arraylist.clear()
        //Log.e("didi","antes do for")
        for (planta in realm.where(PlantaRealm::class.java)
            .beginGroup()
            .equalTo("codIdentificacao", codIdentificacao.toInt())
            .and()
            .equalTo("codChave", codChave.toInt())
            .endGroup()
            .findAll()) {
            //Log.e("didi",planta.nomeIdentificacao)
            arraylist.add(planta)
        }

        preencheRadios(arraylist)
    }


    fun preencheRadios(list: ArrayList<PlantaRealm>) {
        rb1.text = list.get(0).nomeIdentificacao
        rb2.text = list.get(1).nomeIdentificacao
        rg.clearCheck()
    }


    /*fun carregaInfo(codIdentificacao: String, codChave: String) {
    arraylist.clear()
    val progre = ProgreDialog(this@ClassifierAct, "Carregando informações...por favor aguarde")
    val strreq = object : StringRequest(
        Request.Method.POST,
        "http://didi.arielgranato.com.br/identificacao.php",
        Response.Listener {

            Log.d("teste", it)
            for (i in 0 until it.length) {
                try {

                    val array: JSONArray = JSONArray(it);
                    val obj: JSONObject = array.getJSONObject(i);
                    val aux: Planta = Planta()
                    aux.caminho1 = obj.getString("caminho1")
                    aux.codChave = obj.getInt("codChave")
                    aux.nomeIdentificacao = obj.getString("nomeIdentificacao")
                    aux.codIdentificacao = obj.getInt("codIdentificacao")
                    aux.id = obj.getInt("id")
                    arraylist.add(aux)


                } catch (e: JSONException) {
                    e.printStackTrace();
                }
            }

            preencheRadios(arraylist)
            progre.pararProg()
        }, Response.ErrorListener { e ->
            e.printStackTrace()
            Log.e("Erro", e.message)
        }) {
        public override fun getParams(): Map<String, String> {
            val map = HashMap<String, String>()
            map.put("codIdentificacao", codIdentificacao)
            map.put("codChave", codChave)
            return map
        }
    }
    val socketTimeout = 30000//30 seconds - change to what you want
    val policy = DefaultRetryPolicy(
        socketTimeout,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )
    strreq.setRetryPolicy(policy)
    val queue = Volley.newRequestQueue(this)
    queue.add(strreq)
    queue.addRequestFinishedListener(RequestQueue.RequestFinishedListener<Any> {
        queue.getCache().clear()
    })
}*/


}
