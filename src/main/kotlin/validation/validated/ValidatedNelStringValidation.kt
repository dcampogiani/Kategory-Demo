package validation.validated

import arrow.data.Nel
import arrow.data.Validated
import arrow.data.applicative
import arrow.data.ev
import arrow.data.nel
import arrow.syntax.validated.invalid
import arrow.syntax.validated.valid
import validation.Data
import validation.validMail
import validation.validNumber

object ValidatedNelStringValidation {

    fun validateData(input: Data): Validated<Nel<String>, Data> {

        val mail = input.mail
        val phone = input.phone

        return Validated.applicative<Nel<String>>().map2(mail.validatedMail(), phone.validatedPhoneNumber()) {
            Data(it.a, it.b)
        }.ev()
    }

    private fun String.validatedMail(): Validated<Nel<String>, String> =
        when {
            validMail(this) -> this.valid()
            else -> "Invalid email".nel().invalid()
        }

    private fun String.validatedPhoneNumber(): Validated<Nel<String>, String> =
        when {
            validNumber(this) -> this.valid()
            else -> "Invalid phone number".nel().invalid()
        }
}