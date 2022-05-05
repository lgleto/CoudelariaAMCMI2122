package ipca.example.coudelaria.ui.home

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ipca.example.coudelaria.Backend
import ipca.example.coudelaria.R
import ipca.example.coudelaria.databinding.FragmentHomeBinding
import ipca.example.coudelaria.models.Cavalo
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alimentosAdapter = AlimentosAdapter()
        binding.listViewCavalos.adapter = alimentosAdapter
        Backend.getAllCavalos {
            cavalos = it
            alimentosAdapter.notifyDataSetChanged()
        }

        binding.fabAdd.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("Cod_Cavalo",-1)
            findNavController().navigate(R.id.action_navigation_home_to_cavaloDetailFragment,bundle )
            /*
            */
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var cavalos : List<Cavalo> = arrayListOf( )

    inner class AlimentosAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return cavalos.size
        }

        override fun getItem(position: Int): Any {
            return cavalos[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_cavalo,viewGroup,false)

            val textViewName = rootView.findViewById<TextView>(R.id.textViewName)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            textViewName.text = cavalos[position].nome_Cavalo
            textViewDescription.text = "${cavalos[position].genero}"

            rootView.setOnClickListener {

                val bundle = Bundle()
                bundle.putInt("Cod_Cavalo", cavalos[position].cod_Cavalo!!)
                findNavController().navigate(R.id.action_navigation_home_to_cavaloDetailFragment,bundle )
            }


            return rootView
        }
    }

}