package c.dell.inteligent_home_kotlin

import android.app.Fragment
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_light.*
import kotlinx.android.synthetic.main.fragment_light.view.*
/**
 * Graph fragment activity shows last 10 items from database
 * @property mDatabase initialize firebase database
 */

class LightFragment : Fragment() {

    private var mDatabase: DatabaseReference? = null

    /**
     * Created fragment activity
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_light, container, false)
        view.switch1.setOnCheckedChangeListener {
                checked -> changestate(checked,"LIGHT_1")
        }
        view.switch2.setOnCheckedChangeListener {
                checked -> changestate(checked,"LIGHT_2")
        }
        return view
    }

    /**
     *
     * change shwith state 0 or 1 if checked and send changes to firebase
     * @param boolean switch checked state
     * @param light LIGHT switch name
     */
     fun changestate(boolean: Boolean, light: String){
        val ref = FirebaseDatabase.getInstance().getReference(light)
        if (boolean) ref.setValue(1) else ref.setValue(0)
    }

}
