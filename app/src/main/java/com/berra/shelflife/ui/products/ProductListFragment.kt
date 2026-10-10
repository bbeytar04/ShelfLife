package com.berra.shelflife.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.berra.shelflife.R
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.berra.shelflife.model.Product
import com.berra.shelflife.model.ProductCategory
import com.berra.shelflife.model.ProductLocation
import java.time.LocalDate
import java.time.ZoneId
import com.berra.shelflife.adapter.ProductAdapter

class ProductListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_product_list,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerViewProducts)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        val productList = listOf(
            Product(
                name = "Süt",
                category = ProductCategory.FOOD,
                location = ProductLocation.FRIDGE,
                expiryDate = dateAfterDays(12)
            ),

            Product(
                name = "Yoğurt",
                category = ProductCategory.FOOD,
                location = ProductLocation.FRIDGE,
                expiryDate = dateAfterDays(0)
            ),

            Product(
                name = "Şampuan",
                category = ProductCategory.COSMETIC,
                location = ProductLocation.BATHROOM,
                expiryDate = dateAfterDays(-3)
            )
        )

        val productAdapter = ProductAdapter(productList)

        recyclerView.adapter = productAdapter
    }

    private fun dateAfterDays(days: Long): Long {

        return LocalDate.now()
            .plusDays(days)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}