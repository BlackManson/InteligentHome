package c.dell.inteligent_home_kotlin

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.transition.CircularPropagation
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_temp.*
import kotlinx.android.synthetic.main.fragment_temp.view.*
import android.support.annotation.NonNull
import androidx.fragment.app.Fragment

/**
 * Temperature and humidity activity fragment shows actual temp and humidity in real time
 * @property mDatabase firebase database initialization
 *
 */

class TempFragment : Fragment() {

    private var mDatabase: DatabaseReference? = null
    /**
     * Created fragment activity
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val adapter = CircularProgressIndicator.ProgressTextAdapter { v -> v.toString() + "°C" }
        val adapter1 = CircularProgressIndicator.ProgressTextAdapter { v -> v.toString() + "%" }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_temp, container, false)
        mDatabase = FirebaseDatabase.getInstance().getReference("sensor_2")
        view.tempprogress.maxProgress = 50.0
        view.humiprogress.maxProgress = 100.0
        val sensorListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (h in p0.children){
                        val dane = h.getValue(DHT_sensor::class.java)
                        //Log.d("TAG",dane!!.temp.toString())
                        view.humiprogress.setProgressTextAdapter(adapter1)
                        view.tempprogress.setProgressTextAdapter(adapter)
                        view.humiprogress.setCurrentProgress(dane!!.humi.toDouble())
                        view.tempprogress.setCurrentProgress(dane!!.temp.toDouble())
                        view.current_time.text = dane!!.date
                    }
                }
            }
        }
        mDatabase!!.orderByKey().limitToLast(1).addValueEventListener(sensorListener)
        return view
    }
}
