package c.dell.inteligent_home_kotlin

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.util.Log
import com.bitvale.switcher.SwitcherX
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


/**
 *
 *
 * This is a main class for initialize all fragments activities
 *
 *
 * @property mOnNavigationItemSelectedListener It's listener for bottom navigation bar
 *
 * @constructor Creates an empty group.
 */
class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->
        when(item.itemId){
            R.id.temp ->{
                replacefragment(TempFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.light ->{
                replacefragment(LightFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.graph ->{
                replacefragment(ChartFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
    /**
     * This create function, when program starts it's replace activity to TempFragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replacefragment(TempFragment())
    }
    /**
     * This function replace fragment
     * @param fragment which fragment should by replaced
     */
     fun replacefragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment)
        fragmentTransaction.commit()
    }

}
