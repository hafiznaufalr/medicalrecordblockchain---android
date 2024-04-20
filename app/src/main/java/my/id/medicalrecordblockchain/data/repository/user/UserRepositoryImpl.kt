package my.id.medicalrecordblockchain.data.repository.user

import my.id.medicalrecordblockchain.data.APIService
import my.id.medicalrecordblockchain.data.requests.SignInRequest
import my.id.medicalrecordblockchain.data.requests.SignUpRequest
import my.id.medicalrecordblockchain.data.requests.UpdateAppointmentRequest
import my.id.medicalrecordblockchain.data.response.AccountResponse
import my.id.medicalrecordblockchain.data.response.AppointmentResponse
import my.id.medicalrecordblockchain.data.response.BasicResponse
import my.id.medicalrecordblockchain.data.response.DetailAppointmentResponse
import my.id.medicalrecordblockchain.data.response.ExportResponse
import my.id.medicalrecordblockchain.data.response.SignInResponse
import my.id.medicalrecordblockchain.utils.NetworkHandler
import my.id.medicalrecordblockchain.utils.ResultData
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : UserRepository {
    override suspend fun postSignIn(signInRequest: SignInRequest): ResultData<SignInResponse> {
        return NetworkHandler.safeApiCall {
            apiService.postSignIn(signInRequest)
        }
    }

    override suspend fun postSignUp(signUpRequest: SignUpRequest): ResultData<SignInResponse> {
        return NetworkHandler.safeApiCall {
            apiService.postSignUp(signUpRequest)
        }
    }

    override suspend fun getAccount(): ResultData<AccountResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getAccount()
        }
    }

    override suspend fun getAppointmentPatient(
        scheduleDate: String?,
        healthServiceId: String?,
        status: String?,
        isDoctor: Boolean?,
        patientId: String?
    ): ResultData<AppointmentResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getListAppointments(
                scheduleDate = scheduleDate,
                healthServiceId = healthServiceId,
                status = status,
                isDoctor = isDoctor,
                patientId = patientId
            )
        }
    }

    override suspend fun getAppointmentById(appointmentId: String): ResultData<DetailAppointmentResponse> {
        return NetworkHandler.safeApiCall {
            apiService.getAppointmentById(id = appointmentId)
        }
    }

    override suspend fun updateAppointmentStatus(
        appointmentId: String,
        request: UpdateAppointmentRequest
    ): ResultData<DetailAppointmentResponse> {
        return NetworkHandler.safeApiCall {
            apiService.updateAppointmentStatus(
                id = appointmentId,
                body = request
            )
        }
    }

    override suspend fun exportMedicalRecord(appointmentId: String): ResultData<ExportResponse> {
        return NetworkHandler.safeApiCall {
            apiService.exportMedicalRecord(
                appointmentId = appointmentId
            )
        }
    }

}