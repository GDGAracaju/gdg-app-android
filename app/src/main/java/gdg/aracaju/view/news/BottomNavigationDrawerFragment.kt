package gdg.aracaju.view.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.dashboard_menu_view.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    private lateinit var actions: MutableLiveData<Action>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dashboard_menu_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            when (menuItem!!.itemId) {
                R.id.exit -> actions.value = Action.Logout
                R.id.contribute -> actions.value = Action.Contribute
            }
            true
        }
    }

    fun setListener(l: MutableLiveData<Action>) {
        actions = l
    }
}