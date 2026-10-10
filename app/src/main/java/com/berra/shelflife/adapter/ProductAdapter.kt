package com.berra.shelflife.adapter

    import android.view.View
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.berra.shelflife.R
    import com.berra.shelflife.model.Product
    import android.view.LayoutInflater
    import android.view.ViewGroup
    import java.text.SimpleDateFormat
    import java.util.Date
    import java.util.Locale
    import java.time.Instant
    import java.time.LocalDate
    import java.time.ZoneId
    import java.time.temporal.ChronoUnit

class ProductAdapter (

        private val productList: List<Product>
        ) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

        class ProductViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {

            val productNameTextView: TextView =
                itemView.findViewById(R.id.textViewProductName)

            val categoryTextView: TextView =
                itemView.findViewById(R.id.textViewCategory)

            val locationTextView: TextView =
                itemView.findViewById(R.id.textViewLocation)

            val expiryDateTextView: TextView =
                itemView.findViewById(R.id.textViewExpiryDate)

            val expiryStatusTextView: TextView =
                itemView.findViewById(R.id.textViewExpiryStatus)
        }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_product,
                parent,
                false
            )

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {

        val product = productList[position]

        holder.productNameTextView.text = product.name
        holder.categoryTextView.text = "Kategori: ${product.category}"
        holder.locationTextView.text = "Bulunduğu Yer: ${product.location}"

        holder.expiryDateTextView.text =
            "Son Kullanma: ${formatDate(product.expiryDate)}"

        holder.expiryStatusTextView.text =
            getExpiryStatus(product.expiryDate)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    private fun formatDate(time: Long): String {

        val formatter =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return formatter.format(Date(time))
    }

    private fun getExpiryStatus(expiryDate: Long): String {

        val zoneId = ZoneId.systemDefault()

        val today = LocalDate.now(zoneId)

        val expiry = Instant.ofEpochMilli(expiryDate)
            .atZone(zoneId)
            .toLocalDate()

        val daysDifference =
            ChronoUnit.DAYS.between(today, expiry)

        return when {
            daysDifference > 0 ->
                "$daysDifference gün kaldı"

            daysDifference == 0L ->
                "Bugün bitiyor"

            else ->
                "${-daysDifference} gün geçti"
        }
    }

    }
