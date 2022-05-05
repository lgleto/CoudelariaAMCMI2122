package ipca.example.coudelaria.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ipca.example.coudelaria.Backend
import ipca.example.coudelaria.R
import ipca.example.coudelaria.databinding.FragmentCavaloDetailBinding
import ipca.example.coudelaria.databinding.FragmentHomeBinding
import ipca.example.coudelaria.models.Cavalo
import java.util.*

class CavaloDetailFragment : Fragment() {

    private var _binding: FragmentCavaloDetailBinding? = null
    private val binding get() = _binding!!

    private var cod_Cavalo: Int? = null

    private var cavalo : Cavalo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cod_Cavalo = it.getInt("Cod_Cavalo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCavaloDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cod_Cavalo?.let {
            if(it > 0){
                Backend.getCavalo(it) {
                    cavalo = it
                    binding.editTextNameCavalo.setText(it.nome_Cavalo)
                }
            }else{
                binding.buttonDelete.visibility = View.GONE
            }
        }

        binding.buttonSave.setOnClickListener {
            cod_Cavalo?.let {
                if(it < 0){
                    Backend.addCavalo(
                        Cavalo( (201005302..301005102).random(),
                            binding.editTextNameCavalo.text.toString(),
                            Date(),"F",
                            2010053102,
                            2016011001,
                            10,
                            10)
                    ){
                        if (it){
                            findNavController().popBackStack()
                        }else{
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    cavalo?.nome_Cavalo = binding.editTextNameCavalo.text.toString()
                    Backend.updateCavalo(it,
                                        cavalo!!
                        ){
                        if (it){
                            findNavController().popBackStack()
                        }else{
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }


        binding.buttonDelete.setOnClickListener {
            cod_Cavalo?.let {
                if(it > 0){
                    Backend.deleteCavalo(cod_Cavalo!!){
                        if (it){
                            findNavController().popBackStack()
                        }else{
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }


    }



}