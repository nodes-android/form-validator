package dk.nodes.formvalidator.example


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
    }

    private fun setupForm() {

        loginBtn.setOnClickListener {
            // Validate all fields
            if (loginForm.validateAll()) {
                showToast("Proceed with login")
            } else {
                showToast("Some fields are invalid")
            }
        }

        MutableLiveData<String>().observe(this, Observer {  })
    }


}
