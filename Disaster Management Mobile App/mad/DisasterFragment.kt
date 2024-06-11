package com.example.mad3

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad3.database.Disaster
import com.example.mad3.database.DisasterDatabase
import com.example.mad3.database.DisasterRepository
import com.example.mad3.model.FormData
import com.example.mad3.model.validation.validationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisasterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisasterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: DisasterAdapter
    private lateinit var viewModel: MainActivityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView= inflater.inflate(R.layout.fragment_disaster, container, false)

        val recycleView: RecyclerView = rootView.findViewById(R.id.rvdisaster)
        val repository = DisasterRepository(DisasterDatabase.getInstance(requireActivity()))

        val add: Button = rootView.findViewById(R.id.add)

        add.setOnClickListener{
            displayAlert(repository)
        }
        viewModel = ViewModelProvider(requireActivity())[MainActivityData::class.java]


        viewModel.data.observe(requireActivity()) {
            adapter =DisasterAdapter(it,repository,viewModel)
            recycleView.adapter = adapter
            recycleView.layoutManager = LinearLayoutManager(requireActivity())
        }
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllDisaster()

            withContext(Dispatchers.Main){
                viewModel.setData(data)
            }
        }
        return  rootView;
    }
    private fun displayAlert(repository: DisasterRepository) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Enter Disaster Details")

        val layout = LinearLayout(requireActivity())
        layout.orientation = LinearLayout.VERTICAL

        val editTextId = EditText(requireActivity())
        editTextId.hint = "Disaster name"
        layout.addView(editTextId)

        val editTextName = EditText(requireActivity())
        editTextName.hint = "Description"
        layout.addView(editTextName)

        builder.setView(layout)

        builder.setPositiveButton("OK") { dialog, which ->
            val disasterName = editTextId.text.toString().trim()
            val description = editTextName.text.toString().trim()

            val formData = FormData(disasterName, description)

            val nameValidation = formData.validatename()
            val descriptionValidation = formData.validatediscription()

            when (nameValidation) {
                is validationResult.Empty -> {
                    showToast(requireActivity(), nameValidation.errorMessage)
                }
                is validationResult.Invalid -> {
                    showToast(requireActivity(), nameValidation.errorMessage)
                }
                validationResult.Valid -> {
                    when (descriptionValidation) {
                        is validationResult.Empty -> {
                            showToast(requireActivity(), descriptionValidation.errorMessage)
                        }
                        is validationResult.Invalid -> {
                            showToast(requireActivity(), descriptionValidation.errorMessage)
                        }
                        validationResult.Valid -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                repository.insert(Disaster(disasterName, description))
                                val data = repository.getAllDisaster()
                                withContext(Dispatchers.Main) {
                                    viewModel.setData(data)
                                }
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showToast(context: Context, errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisasterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisasterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}