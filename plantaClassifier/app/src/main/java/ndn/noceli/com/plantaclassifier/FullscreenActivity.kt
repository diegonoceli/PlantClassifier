package ndn.noceli.com.plantaclassifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_fullscreen.*
import android.content.Intent



class FullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        val welcomeThread = object : Thread() {

            override fun run() {
                try {
                    super.run()
                    Thread.sleep(2500)  //Delay of 10 seconds
                } catch (e: Exception) {

                } finally {

                    val i = Intent(this@FullscreenActivity, ChavesAct::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }
        welcomeThread.start()
    }


}
