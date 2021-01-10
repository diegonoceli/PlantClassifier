package ndn.noceli.com.plantaclassifier

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.TextView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.facebook.stetho.Stetho
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_chaves.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import kotlin.properties.Delegates


class ChavesAct : AppCompatActivity() {


    var arraylist = ArrayList<PlantaRealm>()
    var arraylistChave = ArrayList<ChaveRealm>()
    private var realm: Realm by Delegates.notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chaves)
        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .name("bdclassificacao.db")
            .build()
        Realm.setDefaultConfiguration(config)
        realm = Realm.getDefaultInstance()





        btnclassa.setOnClickListener { v ->
            /* val person = realm.where(PlantaRealm::class.java).findFirst()
             if (person != null) {
                 showStatus(person.nomeIdentificacao)
             }else {
                 Log.e("didi", "vazio")
             }*/nextPage("1")
        }
        btnclassb.setOnClickListener { v ->
            nextPage("2")
        }
        btnclassc.setOnClickListener { v ->
            nextPage("3")
        }


    }

    private fun showStatus(txt: String) {
        Log.e("RealmDidi", txt)
    }

    override fun onStart() {
        super.onStart()
        Log.e("didi", "" + realm.where(PlantaRealm::class.java).count())
        if (realm.where(PlantaRealm::class.java).count() < 1) {
            Log.e("realmdidi", "N tem nada salvo")
            getChaves()
            getPlantas()
            /*arraylist.forEach(){
                realm.insert(it)
            }
            arraylistChave.forEach(){
                realm.insert(it)
            }*/

        } else {
            Log.e("realmdidi", "TEm salvo")
        }


    }

    fun nextPage(chave: String) {
        val intent = Intent(this@ChavesAct, ClassifierAct::class.java)
        intent.putExtra("chave", chave)
        startActivity(intent)
    }


    fun getPlantas() {
        val progre = ProgreDialog(this@ChavesAct, "Carregando informações...por favor aguarde")
        val strreq = object : StringRequest(
            Request.Method.POST,
            "http://didi.arielgranato.com.br/listartudo.php",
            Response.Listener {

                Log.d("teste", it)
                for (i in 0 until 804) {
                    try {

                        val array: JSONArray = JSONArray(it);
                        val obj: JSONObject = array.getJSONObject(i);
                        val aux: PlantaRealm = PlantaRealm()
                        realm.executeTransaction() {
                            aux.caminho1 = obj.getString("caminho1")
                            aux.codChave = obj.getInt("codChave")
                            aux.nomeIdentificacao = obj.getString("nomeIdentificacao")
                            aux.codIdentificacao = obj.getInt("codIdentificacao")
                            aux.id = obj.getInt("id")
                            it.insert(aux)
                            arraylist.add(aux)
                            Log.e("teste2", "foi " + i)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                progre.pararProg()
            }, Response.ErrorListener { e ->
                e.printStackTrace()
                Log.e("Erro", e.message)
            }) {
            public override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map.put("codigo", "1")
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
    }

    fun getChaves() {
        val progre = ProgreDialog(this@ChavesAct, "Baixando informações, isso só irá ser feito uma vez e não demora nada  :D ...por favor aguarde")
        progre.pg.max=803
        progre.pg.progress=0
        progre.pg.show()
        val strreq = object : StringRequest(
            Request.Method.POST,
            "http://didi.arielgranato.com.br/listartudo.php",
            Response.Listener {

                Log.d("teste", it)
                for (i in 0 until it.length) {
                    try {

                        val array: JSONArray = JSONArray(it);
                        val obj: JSONObject = array.getJSONObject(i)
                        realm.executeTransaction() {
                            val aux: ChaveRealm = ChaveRealm()
                            aux.codChave = obj.getInt("codChave")
                            aux.nomeChave = obj.getString("nomeChave")
                            it.insert(aux)
                            arraylistChave.add(aux)
                            Log.e("teste", "foi " + i)
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                progre.pararProg()
            }, Response.ErrorListener { e ->
                e.printStackTrace()
                Log.e("Erro", e.message)
            }) {
            public override fun getParams(): Map<String, String> {
                val map = HashMap<String, String>()
                map.put("codigo", "0")
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
    }


}
