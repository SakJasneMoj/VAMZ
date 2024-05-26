import android.content.Context
import android.content.SharedPreferences

class ProfileRepository(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

    fun saveProfileName(newName: String) {
        sharedPreferences.edit().putString("profileName", newName).apply()
    }

    fun getProfileName(): String {
        return sharedPreferences.getString("profileName", "John Doe") ?: "John Doe"
    }

    fun saveExerciseMaxTime(time: Int) {
        sharedPreferences.edit().putInt("exerciseMaxTime", time).apply()
    }

    fun getExerciseMaxTime(): Int {
        return sharedPreferences.getInt("exerciseMaxTime", 60)
    }

    fun saveCaloriesMaxValue(value: Int) {
        sharedPreferences.edit().putInt("caloriesMaxValue", value).apply()
    }

    fun getCaloriesMaxValue(): Int {
        return sharedPreferences.getInt("caloriesMaxValue", 2000)
    }

    fun saveDrinkWaterNotification(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("drinkWaterNotification", enabled).apply()
    }

    fun getDrinkWaterNotification(): Boolean {
        return sharedPreferences.getBoolean("drinkWaterNotification", false)
    }

    fun saveCaloriesIntakeNotification(frequency: Int) {
        sharedPreferences.edit().putInt("caloriesIntakeNotification", frequency).apply()
    }

    fun getCaloriesIntakeNotification(): Int {
        return sharedPreferences.getInt("caloriesIntakeNotification", 0)
    }

    fun saveExerciseReminderNotification(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("exerciseReminderNotification", enabled).apply()
    }

    fun getExerciseReminderNotification(): Boolean {
        return sharedPreferences.getBoolean("exerciseReminderNotification", false)
    }
}
