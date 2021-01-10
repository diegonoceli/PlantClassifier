package ndn.noceli.com.plantaclassifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView
import kotlinx.android.synthetic.main.activity_pdf_reader.*

class pdfReader : AppCompatActivity() {

    val pdf: PDFView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)
        toolbarpdf.setNavigationOnClickListener{t->
            finish()
        }



        pdfView.fromAsset("pdfcortado.pdf").load()
    }
}
