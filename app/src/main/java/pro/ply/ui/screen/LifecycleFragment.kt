package pro.ply.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pro.ply.R

class LifecycleFragment : Fragment(R.layout.fragment_lifecycle) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {
        private const val TAG = "LifecycleFragment"
    }
}
