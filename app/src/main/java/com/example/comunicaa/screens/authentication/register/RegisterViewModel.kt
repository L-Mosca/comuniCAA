package com.example.comunicaa.screens.authentication.register

import com.example.comunicaa.R
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.user.buildLoginBody
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepositoryContract
) : BaseViewModel() {

    val showEmptyEmail = SingleLiveData<Unit>()
    val usernameError = SingleLiveData<Unit>()
    val confirmPasswordError = SingleLiveData<Unit>()
    val passwordEmpty = SingleLiveData<Unit>()
    val confirmPasswordEmpty = SingleLiveData<Unit>()
    val loginSuccess = SingleLiveData<Unit>()
    val loginError = SingleLiveData<Unit>()

    val passwordLength = SingleLiveData<Int>()
    val passwordUpperLetter = SingleLiveData<Int>()
    val passwordLowerLetter = SingleLiveData<Int>()
    val passwordNumber = SingleLiveData<Int>()
    private var isValidPassword = false

    fun register(username: String, email: String, password: String, confirmPassword: String) {
        defaultLaunch (exceptionHandler = {
            loginError.postValue(Unit)
        }) {
            if (formIsOk(username, email, password, confirmPassword)) {
                val userModel = userRepository.register(buildLoginBody(username, email, password))
                if (userModel != null) loginSuccess.postValue(Unit)
                else loginError.postValue(Unit)
            }
        }
    }

    private fun formIsOk(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): Boolean {
        val nameIsOk = checkNameContent(username)
        val emailIsOk = checkEmailContent(email)
        val passwordIsOk = checkPasswordsContents(password, confirmPassword)
        return nameIsOk && emailIsOk && passwordIsOk && isValidPassword
    }

    private fun checkNameContent(username: String): Boolean {
        if (username.isEmpty()) usernameError.postValue(Unit)
        return username.isNotEmpty()
    }

    private fun checkEmailContent(email: String): Boolean {
        if (email.isEmpty()) showEmptyEmail.postValue(Unit)
        return email.isNotEmpty()
    }

    private fun checkPasswordsContents(password: String, confirmPassword: String): Boolean {
        if (password.isEmpty()) passwordEmpty.postValue(Unit)
        if (confirmPassword.isEmpty()) confirmPasswordEmpty.postValue(Unit)
        if (password != confirmPassword) confirmPasswordError.postValue(Unit)

        return password == confirmPassword
    }

    fun validatePasswordRequirements(newPassword: String?) {
        val password = newPassword ?: ""
        val length = password.length >= 8
        val upperLetter = Regex("[A-Z]").containsMatchIn(password)
        val lowerCase = Regex("[a-z]").containsMatchIn(password)
        val number = Regex("[0-9]").containsMatchIn(password)

        isValidPassword = length && upperLetter && lowerCase && number

        passwordLength.postValue(if (length) R.color.green_700 else R.color.red_700)
        passwordUpperLetter.postValue(if (upperLetter) R.color.green_700 else R.color.red_700)
        passwordLowerLetter.postValue(if (lowerCase) R.color.green_700 else R.color.red_700)
        passwordNumber.postValue(if (number) R.color.green_700 else R.color.red_700)
    }
}