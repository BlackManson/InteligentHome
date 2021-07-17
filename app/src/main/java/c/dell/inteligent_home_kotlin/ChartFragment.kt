package c.dell.inteligent_home_kotlin

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_chart.view.*
import android.R.attr.entries
import android.R.attr.entries
import android.util.Log
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineData

/**
 * Graph fragment activity shows last 10 items from database
 * @property mDatabase initialize firebase database
 * @property lineChart type of graph
 */

class ChartFragment : Fragment() {

    var mDatabase: DatabaseReference? = null
    var lineChart: LineChart? = null
    var lineChart2 : LineChart? = null
    /**
     * main function, creating view for fragment
     *
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(container, null)
        lineChart = view.chart // assign lieChart object to chart id chart_layout
        lineChart2 = view.chart2
        lineChart!!.description.text = "Temperature" // set graph description
        lineChart!!.setBackgroundColor(Color.GRAY) // set background color
        lineChart2!!.description.text = "Humidity" // set graph description
        lineChart2!!.setBackgroundColor(Color.GRAY) // set background color
        var entries = ArrayList<Entry>()    // create entries array list
        var entries2 = ArrayList<Entry>()
        mDatabase = FirebaseDatabase.getInstance().getReference("sensor_2") // get reference to sensor_2 obejct
        var co = 1F // count X in graph
        var co1 = 1F
        /*
            listener for database event listener as object
         */
        val sensorListener = object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    for (h in p0.children){
                        val dane = h.getValue(DHT_sensor::class.java) // assign dht object to dane
                        Log.d("GRAPH",dane!!.temp.toString())
                        Log.d("GRAPH2",dane!!.humi.toString())// simply log for see changes
                        entries?.add(Entry(co++.toFloat(), dane!!.temp)) // add current temp to entries list
                        entries2?.add(Entry(co1++.toFloat(), dane!!.humi))
                    }

                }
                var dataSet = LineDataSet(entries, "Label") // set label
                dataSet.setDrawValues(false)

                var dataSet2 = LineDataSet(entries2, "Label") // set label
                dataSet2.setDrawValues(false)

                var lineData = LineData(dataSet)
                lineData.notifyDataChanged()

                var lineData2 = LineData(dataSet2)
                lineData2.notifyDataChanged()

                lineChart?.data = lineData
                Log.d("asd",entries.toString())
                lineChart?.invalidate() // auto refresh graph when data update

                lineChart2?.data = lineData2
                lineChart2?.invalidate()
                /*
                show only 10 reads
                 */
                if(entries.size > 12){
                    entries.clear()
                    co = 1F
                    Log.d("counn",entries.lastIndex.toString())
                }

                if(entries2.size > 12){
                    entries.clear()
                    co1 = 1F
                    Log.d("counn",entries2.lastIndex.toString())
                }
            }
        }
        mDatabase!!.orderByKey().limitToLast(12).addValueEventListener(sensorListener) // add value event listener for changed on database and download only 10 last reads


        return view
    }

}
