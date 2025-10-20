package com.plataformas.lab06.viewmodel
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plataformas.lab06.model.Building
import com.plataformas.lab06.model.Room
import com.plataformas.lab06.repository.BuildingRepository
class BuildingViewModel : ViewModel() {
    private val _building = MutableLiveData<Building>()
    val building: LiveData<Building> = _building

    private val _selectedRoom = MutableLiveData<Room?>()
    val selectedRoom: LiveData<Room?> = _selectedRoom

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadBuilding(context: Context) {
        try {
            val repository = BuildingRepository()
            val loadedBuilding = repository.loadBuildingFromAssets(context)
            _building.value = loadedBuilding
        } catch (e: Exception) {
            _error.value = "Error al cargar el edificio: ${e.message}"
        }
    }

    fun selectRoom(room: Room?) {
        _selectedRoom.value = room
    }

    fun clearSelection() {
        _selectedRoom.value = null
    }
}