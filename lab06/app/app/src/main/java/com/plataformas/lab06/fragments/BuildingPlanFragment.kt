package com.plataformas.lab06.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.plataformas.lab06.databinding.FragmentBuildingPlanBinding
import com.plataformas.lab06.viewmodel.BuildingViewModel
import com.plataformas.lab06.model.Room

class BuildingPlanFragment : Fragment() {

    private var _binding: FragmentBuildingPlanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BuildingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildingPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()

        // Cargar el edificio
        viewModel.loadBuilding(requireContext())
    }

    private fun setupObservers() {
        // Observar el edificio cargado
        viewModel.building.observe(viewLifecycleOwner) { building ->
            binding.buildingPlanView.setRooms(building.rooms)
            binding.textBuildingName.text = building.name
        }

        // Observar la habitación seleccionada
        viewModel.selectedRoom.observe(viewLifecycleOwner) { room ->
            binding.buildingPlanView.setSelectedRoom(room)

            if (room != null) {
                showRoomInfo(room)
            }
        }

        // Observar errores
        viewModel.error.observe(viewLifecycleOwner) { error ->
            binding.textBuildingName.text = error
        }
    }

    private fun setupListeners() {
        // Listener para clicks en los ambientes
        binding.buildingPlanView.setOnRoomClickListener { room ->
            viewModel.selectRoom(room)
        }
    }

    private fun showRoomInfo(room: Room) {
        val dialog = RoomInfoDialogFragment.newInstance(room)
        dialog.show(childFragmentManager, "RoomInfoDialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}