package com.plataformas.lab06.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.plataformas.lab06.databinding.FragmentRoomInfoDialogBinding
import com.plataformas.lab06.model.Room

class RoomInfoDialogFragment : DialogFragment() {

    private var _binding: FragmentRoomInfoDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_ROOM_ID = "room_id"
        private const val ARG_ROOM_NAME = "room_name"
        private const val ARG_ROOM_TYPE = "room_type"
        private const val ARG_ROOM_DESCRIPTION = "room_description"
        private const val ARG_ROOM_AREA = "room_area"
        private const val ARG_ROOM_CAPACITY = "room_capacity"
        private const val ARG_ROOM_ADDITIONAL_INFO = "room_additional_info"

        fun newInstance(room: Room): RoomInfoDialogFragment {
            val fragment = RoomInfoDialogFragment()
            val args = Bundle().apply {
                putString(ARG_ROOM_ID, room.id)
                putString(ARG_ROOM_NAME, room.name)
                putString(ARG_ROOM_TYPE, room.type)
                putString(ARG_ROOM_DESCRIPTION, room.description)
                putFloat(ARG_ROOM_AREA, room.area)
                putInt(ARG_ROOM_CAPACITY, room.capacity)
                putString(ARG_ROOM_ADDITIONAL_INFO, room.additionalInfo)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            val name = args.getString(ARG_ROOM_NAME, "")
            val type = args.getString(ARG_ROOM_TYPE, "")
            val description = args.getString(ARG_ROOM_DESCRIPTION, "")
            val area = args.getFloat(ARG_ROOM_AREA, 0f)
            val capacity = args.getInt(ARG_ROOM_CAPACITY, 0)
            val additionalInfo = args.getString(ARG_ROOM_ADDITIONAL_INFO, "")

            binding.textRoomName.text = name

            val typeText = when (type) {
                "patio" -> "Patio"
                "salon" -> "Salón"
                else -> type.replaceFirstChar { it.uppercase() }
            }
            binding.textRoomType.text = "Tipo: $typeText"

            binding.textRoomDescription.text = description

            if (area > 0) {
                binding.textRoomArea.text = "Área: %.2f m²".format(area)
                binding.textRoomArea.visibility = View.VISIBLE
            } else {
                binding.textRoomArea.visibility = View.GONE
            }

            if (capacity > 0) {
                binding.textRoomCapacity.text = "Capacidad: $capacity personas"
                binding.textRoomCapacity.visibility = View.VISIBLE
            } else {
                binding.textRoomCapacity.visibility = View.GONE
            }

            if (additionalInfo.isNotEmpty()) {
                binding.textAdditionalInfo.text = additionalInfo
                binding.textAdditionalInfo.visibility = View.VISIBLE
            } else {
                binding.textAdditionalInfo.visibility = View.GONE
            }
        }

        binding.buttonClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}