package com.example.firebasegroupapp10.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasegroupapp10.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : BaseActivity() {

    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var homeAddressEditText: TextInputEditText
    private lateinit var businessAddressEditText: TextInputEditText
    private lateinit var paymentMethodSpinner: Spinner
    private lateinit var placeOrderButton: MaterialButton
    private lateinit var cardDetailsContainer: LinearLayout
    private lateinit var textViewCardNumber: TextView
    private lateinit var textViewCardExpiry: TextView
    private lateinit var textViewCardCVV: TextView
    private lateinit var radioButtonHomeAddress: RadioButton
    private lateinit var radioButtonBusinessAddress: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        // Initialize views
        nameEditText = findViewById(R.id.editTextName)
        emailEditText = findViewById(R.id.editTextEmail)
        homeAddressEditText = findViewById(R.id.editTextHomeAddress)
        businessAddressEditText = findViewById(R.id.editTextBusinessAddress)
        paymentMethodSpinner = findViewById(R.id.spinnerPaymentMethod)
        placeOrderButton = findViewById(R.id.buttonPlaceOrder)
        cardDetailsContainer = findViewById(R.id.cardDetailsContainer)
        textViewCardNumber = findViewById(R.id.textViewCardNumber)
        textViewCardExpiry = findViewById(R.id.textViewCardExpiry)
        textViewCardCVV = findViewById(R.id.textViewCardCVV)
        radioButtonHomeAddress = findViewById(R.id.radioButtonHomeAddress)
        radioButtonBusinessAddress = findViewById(R.id.radioButtonBusinessAddress)

        // Set up payment method spinner
        val paymentMethods = arrayOf("COD", "PayPal", "Credit/Debit Card")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentMethodSpinner.adapter = adapter

        paymentMethodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (paymentMethods[position] == "Credit/Debit Card") {
                    showCardDetailsDialog()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        placeOrderButton.setOnClickListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
        }

        // Radio button toggle logic
        radioButtonHomeAddress.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                homeAddressEditText.isEnabled = true
                businessAddressEditText.isEnabled = false
                radioButtonBusinessAddress.isChecked = false
            }
        }

        radioButtonBusinessAddress.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                businessAddressEditText.isEnabled = true
                homeAddressEditText.isEnabled = false
                radioButtonHomeAddress.isChecked = false
            }
        }

        // Back button functionality
        val backBtn: ImageView = findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun showCardDetailsDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_card_details, null)
        val editTextCardNumber = dialogLayout.findViewById<EditText>(R.id.editTextCardNumber)
        val editTextCardExpiry = dialogLayout.findViewById<EditText>(R.id.editTextCardExpiry)
        val editTextCardCVV = dialogLayout.findViewById<EditText>(R.id.editTextCardCVV)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Confirm") { dialog, _ ->
            textViewCardNumber.text = "Card Number: ${editTextCardNumber.text}"
            textViewCardExpiry.text = "Expiry Date: ${editTextCardExpiry.text}"
            textViewCardCVV.text = "CVV: ${editTextCardCVV.text}"
            cardDetailsContainer.visibility = View.VISIBLE
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
