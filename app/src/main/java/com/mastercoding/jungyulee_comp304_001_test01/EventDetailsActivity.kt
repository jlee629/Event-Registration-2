package com.mastercoding.jungyulee_comp304_001_test01

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var buttonSelectDate: Button
    private lateinit var spinnerType: Spinner
    private lateinit var checkBoxTD: CheckBox
    private lateinit var checkBoxCentennial: CheckBox
    private lateinit var checkBoxAccenture: CheckBox
    private lateinit var checkBoxCIBC: CheckBox
    private lateinit var spinnerNumberOfTickets: Spinner
    private lateinit var editTextPrice: EditText
    private lateinit var buttonNext: Button
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        editTextName = findViewById(R.id.editTextName)
        editTextLocation = findViewById(R.id.editTextLocation2)
        buttonSelectDate = findViewById(R.id.button)
        spinnerType = findViewById(R.id.spinnerType)
        checkBoxTD = findViewById(R.id.checkBox)
        checkBoxCentennial = findViewById(R.id.checkBox2)
        checkBoxAccenture = findViewById(R.id.checkBox3)
        checkBoxCIBC = findViewById(R.id.checkBox4)
        spinnerNumberOfTickets = findViewById(R.id.spinnerNumberOfTickets)
        editTextPrice = findViewById(R.id.editTextPrice)
        buttonNext = findViewById(R.id.buttonNext)

        buttonSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                buttonSelectDate.text = selectedDate
            }, year, month, day).show()
        }

        buttonNext.setOnClickListener {
            if (validateInputs()) {
                val eventName = editTextName.text.toString()
                val pricePerTicket = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
                val selectedNumberOfTickets = spinnerNumberOfTickets.selectedItemPosition + 1
                val totalTicketsPrice = pricePerTicket * selectedNumberOfTickets

                val intent = Intent(this, UserDetailsActivity::class.java).apply {
                    putExtra("eventName", eventName)
                    putExtra("totalTicketsPrice", totalTicketsPrice)
                    putExtra("numberOfTickets", selectedNumberOfTickets)
                }
                startActivity(intent)
            }
        }
    }


    private fun validateInputs(): Boolean {
        val name = editTextName.text.toString()
        val location = editTextLocation.text.toString()
        val isCheckedTD = checkBoxTD.isChecked
        val isCheckedCentennial = checkBoxCentennial.isChecked
        val isCheckedAccenture = checkBoxAccenture.isChecked
        val isCheckedCIBC = checkBoxCIBC.isChecked
        val sponsorCount = listOf(isCheckedTD, isCheckedCentennial, isCheckedAccenture, isCheckedCIBC).count { it }
        val price = editTextPrice.text.toString()

        return when {
            name.isBlank() -> {
                Toast.makeText(this, "Event name is required", Toast.LENGTH_SHORT).show()
                false
            }
            location.isBlank() -> {
                Toast.makeText(this, "Event location is required", Toast.LENGTH_SHORT).show()
                false
            }
            selectedDate.isEmpty() -> {
                Toast.makeText(this, "Start date is required", Toast.LENGTH_SHORT).show()
                false
            }
            sponsorCount < 2 -> {
                Toast.makeText(this, "Please select at least two sponsors", Toast.LENGTH_SHORT).show()
                false
            }
            price.isBlank() || price.toDouble() <= 0 -> {
                Toast.makeText(this, "Price per ticket must be greater than zero", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}
