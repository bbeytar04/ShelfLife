package com.berra.shelflife.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.berra.shelflife.R
import com.berra.shelflife.model.ProductCategory
import com.berra.shelflife.model.ProductLocation
import android.app.DatePickerDialog
import android.widget.Button
import java.util.Calendar
import android.widget.EditText
import android.widget.Toast
import com.berra.shelflife.model.Product
class AddProductFragment : Fragment() {

    private var purchaseDate: Long? = null
    private var expiryDate: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_add_product,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categorySpinner =
            view.findViewById<Spinner>(R.id.spinnerCategory)

        val locationSpinner =
            view.findViewById<Spinner>(R.id.spinnerLocation)

        val purchaseDateButton =
            view.findViewById<Button>(R.id.buttonPurchaseDate)

        val expiryDateButton =
            view.findViewById<Button>(R.id.buttonExpiryDate)

        val productNameEditText =
            view.findViewById<EditText>(R.id.editTextProductName)

        val quantityEditText =
            view.findViewById<EditText>(R.id.editTextQuantity)

        val notesEditText =
            view.findViewById<EditText>(R.id.editTextNotes)

        val saveButton =
            view.findViewById<Button>(R.id.buttonSave)

        val categoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ProductCategory.entries
        )

        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        categorySpinner.adapter = categoryAdapter

        val locationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ProductLocation.entries
        )

        locationAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        locationSpinner.adapter = locationAdapter

        purchaseDateButton.setOnClickListener {

            showDatePicker { selectedDate ->

                purchaseDate = selectedDate

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selectedDate

                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendar.get(Calendar.MONTH) + 1
                val year = calendar.get(Calendar.YEAR)

                purchaseDateButton.text = "$day/$month/$year"
            }
        }

        expiryDateButton.setOnClickListener {

            showDatePicker { selectedDate ->

                expiryDate = selectedDate

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selectedDate

                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendar.get(Calendar.MONTH) + 1
                val year = calendar.get(Calendar.YEAR)

                expiryDateButton.text = "$day/$month/$year"
            }
        }

        saveButton.setOnClickListener {

            val productName =
                productNameEditText.text.toString().trim()

            if (productName.isEmpty()) {
                productNameEditText.error = "Ürün adı boş bırakılamaz"
                return@setOnClickListener
            }

            if (expiryDate == null) {
                Toast.makeText(
                    requireContext(),
                    "Son kullanma tarihi seçmelisiniz",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val quantity =
                quantityEditText.text.toString().toDoubleOrNull() ?: 0.0

            val notes =
                notesEditText.text.toString().trim()

            val selectedCategory =
                categorySpinner.selectedItem as ProductCategory

            val selectedLocation =
                locationSpinner.selectedItem as ProductLocation

            val product = Product(
                name = productName,
                category = selectedCategory,
                location = selectedLocation,
                quantity = quantity,
                purchaseDate = purchaseDate,
                expiryDate = expiryDate!!,
                notes = notes
            )

            Toast.makeText(
                requireContext(),
                "Form geçerli",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun showDatePicker(onDateSelected: (Long) -> Unit) {

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val selectedCalendar = Calendar.getInstance()

                selectedCalendar.set(
                    selectedYear,
                    selectedMonth,
                    selectedDay,
                    0,
                    0,
                    0
                )

                selectedCalendar.set(Calendar.MILLISECOND, 0)

                onDateSelected(selectedCalendar.timeInMillis)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

}