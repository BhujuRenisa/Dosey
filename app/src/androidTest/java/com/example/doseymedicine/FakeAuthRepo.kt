import com.example.doseymedicine.model.DoseyModel
import com.example.doseymedicine.respo.AuthRepo

class FakeAuthRepo : AuthRepo {

    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        callback(true, "Login Success (Fake)")
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        callback(true, "Register Success", "fakeUserId")
    }

    override fun addUserToDatabase(
        userId: String,
        model: DoseyModel,
        callback: (Boolean, String) -> Unit
    ) {
        callback(true, "User added successfully (Fake)")
    }

    override fun forgotPassword(email: String, callback: (Boolean, String) -> Unit) {
        callback(true, "Password reset email sent (Fake)")
    }

    override fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) {
        callback(true, "Account deleted (Fake)")
    }

    override fun editProfile(
        userId: String,
        model: DoseyModel,
        callback: (Boolean, String) -> Unit
    ) {
        callback(true, "Profile updated (Fake)")
    }

    override fun getUserById(
        userId: String,
        callback: (Boolean, String, DoseyModel?) -> Unit
    ) {
        callback(
            true,
            "User found (Fake)",
            DoseyModel(
                id = "fakeUserId",
                firstName = "Fake",
                lastName = "User",
                email = "fake@email.com",
                gender = "Other"
            )
        )
    }
}