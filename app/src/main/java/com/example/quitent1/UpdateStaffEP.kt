package com.example.quitent1

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.quitent1.FireBase.FireStoreClass
import kotlinx.android.synthetic.main.activity_update_staff_ep.*

class UpdateStaffEP : CommonData() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_staff_ep)

        update_staff_pass_submit.setOnClickListener {
            onclick()
        }

        update_staff_email_submit.setOnClickListener {
            onclick1()
        }
    }

    fun displayMsg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        hideProgressDialog()
        update_staff_email.text!!.clear()
        update_staff_password.text!!.clear()
    }

    private fun onclick() {
        val password = update_staff_password.text.toString().trim { it <= ' ' }

        try {
            if (validateForm(password)) {
                showProgressDialog(resources.getString(R.string.please_wait))
                val cUser = FireStoreClass().getCurrentUserRef()
                cUser!!.updatePassword(password).addOnSuccessListener {
                    StoreData.cStaff!!.staffPassword = password
                    FireStoreClass().updateStaffEP(this, StoreData.cStaff!!, 1)
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun onclick1() {
        val email = update_staff_email.text.toString().trim { it <= ' ' }

        try {
            if (validateForm1(email)) {
                showProgressDialog(resources.getString(R.string.please_wait))
                val cUser = FireStoreClass().getCurrentUserRef()
                cUser!!.updateEmail(email).addOnSuccessListener {
                    StoreData.cStaff!!.staffEmail = email
                    FireStoreClass().updateStaffEP(this, StoreData.cStaff!!, 2)
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }


    private fun validateForm(
        password: String
    ): Boolean {
        return when {
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please Enter Password")
                false
            }
            else -> true
        }
    }

    private fun validateForm1(
        email: String
    ): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please Enter Email")
                false
            }
            else -> true
        }
    }

}