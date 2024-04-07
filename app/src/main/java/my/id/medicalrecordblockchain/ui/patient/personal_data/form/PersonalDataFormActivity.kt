package my.id.medicalrecordblockchain.ui.patient.personal_data.form

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.databinding.ActivityPersonalDataFormBinding
import my.id.medicalrecordblockchain.ui.global.sign_in.SignInActivity
import my.id.medicalrecordblockchain.ui.patient.sign_up.SignUpViewModel
import my.id.medicalrecordblockchain.utils.LoadingDialog
import my.id.medicalrecordblockchain.utils.ResultData
import my.id.medicalrecordblockchain.utils.SnackBarType
import my.id.medicalrecordblockchain.utils.showSnackBar

@AndroidEntryPoint
class PersonalDataFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalDataFormBinding
    private lateinit var signUpRequest: SignUpRequest
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val loadingDialog by lazy { LoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initListener()
        observer()
    }

    private fun init() {
        intent.getParcelableExtra<SignUpRequest>(KEY_SIGN_UP_REQ)?.let {
            signUpRequest = it
        }
    }

    private fun initListener() {
        binding.btnSignUp.setOnClickListener {
            signUpViewModel.validatePersonalData(
                signUpRequest = signUpRequest.copy(
                    dateOfBirth = binding.etDob.text.toString().trim(),
                    weight = binding.etWeight.text.toString().toIntOrNull(),
                    height = binding.etHeight.text.toString().toIntOrNull(),
                    allergies = binding.etAllergy.text.toString().trim(),
                    gender = binding.etGender.text.toString().trim(),
                    bloodGroup = binding.etBloodType.text.toString().trim()
                )
            )
        }
    }

    private fun observer() {
        signUpViewModel.postSignUp.observe(this) { state ->
            when (state) {
                is ResultData.Loading -> {
                    loadingDialog.show()
                }

                is ResultData.Success -> {
                    loadingDialog.dismiss()
                    showSnackBar(
                        message = "Daftar berhasil",
                        snackBarType = SnackBarType.SUCCESS
                    )

                    Handler(Looper.getMainLooper()).postDelayed({
                        SignInActivity.launch(
                            context = this
                        )
                        finishAffinity()
                    }, 1000)

                }

                is ResultData.Failure -> {
                    loadingDialog.dismiss()
                    showSnackBar(
                        message = state.error,
                        snackBarType = SnackBarType.ERROR
                    )
                }
            }
        }
    }

    companion object {
        private const val KEY_SIGN_UP_REQ = "KEY_SIGN_UP_REQ"
        fun launch(context: Context, signUpRequest: SignUpRequest) {
            context.startActivity(
                Intent(
                    context,
                    PersonalDataFormActivity::class.java
                ).apply {
                    putExtra(KEY_SIGN_UP_REQ, signUpRequest)
                }
            )
        }
    }
}