import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vamzsem.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val context: Context,
    private val repository: ProfileRepository
) : ViewModel() {



    private val _profileName = MutableStateFlow(repository.getProfileName())
    val profileName: StateFlow<String> = _profileName.asStateFlow()

    private val _profileImage = MutableStateFlow("")  // Assume default empty image, update as needed
    val profileImage: StateFlow<String> = _profileImage.asStateFlow()

    private val _exerciseMaxTime = MutableStateFlow(repository.getExerciseMaxTime())
    val exerciseMaxTime: StateFlow<Int> = _exerciseMaxTime.asStateFlow()

    private val _caloriesMaxValue = MutableStateFlow(repository.getCaloriesMaxValue())
    val caloriesMaxValue: StateFlow<Int> = _caloriesMaxValue.asStateFlow()

    private val _drinkWaterNotification = MutableStateFlow(repository.getDrinkWaterNotification())
    val drinkWaterNotification: StateFlow<Boolean> = _drinkWaterNotification.asStateFlow()

    private val _caloriesIntakeNotification = MutableStateFlow(repository.getCaloriesIntakeNotification())
    val caloriesIntakeNotification: StateFlow<Int> = _caloriesIntakeNotification.asStateFlow()

    private val _exerciseReminderNotification = MutableStateFlow(repository.getExerciseReminderNotification())
    val exerciseReminderNotification: StateFlow<Boolean> = _exerciseReminderNotification.asStateFlow()

    // Add methods to update these values
    fun updateProfileName(newName: String) {
        _profileName.value = newName
        viewModelScope.launch {
            repository.saveProfileName(newName)
        }
    }

    fun updateExerciseMaxTime(time: Int) {
        _exerciseMaxTime.value = time
        viewModelScope.launch {
            repository.saveExerciseMaxTime(time)
        }
    }

    fun updateCaloriesMaxValue(value: Int) {
        _caloriesMaxValue.value = value
        viewModelScope.launch {
            repository.saveCaloriesMaxValue(value)
        }
    }

    fun toggleDrinkWaterNotification() {
        _drinkWaterNotification.value = !_drinkWaterNotification.value
        viewModelScope.launch {
            repository.saveDrinkWaterNotification(_drinkWaterNotification.value)
        }
    }

    fun updateCaloriesIntakeNotification(frequency: Int) {
        _caloriesIntakeNotification.value = frequency
        viewModelScope.launch {
            repository.saveCaloriesIntakeNotification(frequency)
        }
    }

    fun toggleExerciseReminderNotification() {
        _exerciseReminderNotification.value = !_exerciseReminderNotification.value
        viewModelScope.launch {
            repository.saveExerciseReminderNotification(_exerciseReminderNotification.value)
        }
    }




}
