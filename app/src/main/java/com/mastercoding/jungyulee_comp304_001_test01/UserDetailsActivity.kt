package com.mastercoding.jungyulee_comp304_001_test01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var textViewInformation: TextView
    private lateinit var editTextId: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var radioGroupType: RadioGroup
    private lateinit var buttonPrev: Button
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        textViewInformation = findViewById(R.id.textViewInformation)

        // 4 digits generated randomly
        editTextId = findViewById(R.id.editTextId)
        editTextId.setText((Random().nextInt(9000) + 1000).toString())

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextNameEmail)
        radioGroupType = findViewById(R.id.radioGroupType)
        buttonPrev = findViewById(R.id.buttonPrev)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        val eventName = intent.getStringExtra("eventName")
        val totalTicketsPrice = intent.getDoubleExtra("totalTicketsPrice", 0.0)

        textViewInformation.text = "Event Information: \nEvent Name: $eventName, Total Price: $$totalTicketsPrice"


        buttonPrev.setOnClickListener {
            finish()
        }

        buttonSubmit.setOnClickListener {
            if (validateInputs()) {
                val eventName = intent.getStringExtra("eventName")
                val userName = editTextName.text.toString().trim()
                val totalPrice = intent.getDoubleExtra("totalTicketsPrice", 0.0)
                val numberOfPersons = intent.getIntExtra("numberOfTickets", 1)
                val message = "Event Name: $eventName \nUser Name: $userName \nTotal Price: $$totalPrice \nNumber of Persons: $numberOfPersons"

                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun validateInputs(): Boolean {
        if (editTextId.text.isBlank() || editTextName.text.isBlank() || editTextEmail.text.isBlank()) {
            Toast.makeText(this, "All text fields are required", Toast.LENGTH_SHORT).show()
            return false
        }

        if (radioGroupType.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select the event mode", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


}
