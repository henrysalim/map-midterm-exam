package com.example.midtermexam.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.midtermexam.R
import com.example.midtermexam.data.ScanResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Adapter ini menerima sebuah fungsi (lambda) sebagai parameter konstruktor.
// Fungsi ini akan dipanggil setiap kali ikon pin di-klik.
class HistoryAdapter(
    private val onPinClicked: (ScanResult) -> Unit
) : ListAdapter<ScanResult, HistoryAdapter.HistoryViewHolder>(ScanResultDiffCallback()) {

    /**
     * Membuat ViewHolder baru setiap kali RecyclerView membutuhkannya.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_scan, parent, false)
        // Teruskan fungsi onPinClicked ke konstruktor ViewHolder
        return HistoryViewHolder(view, onPinClicked)
    }

    /**
     * Menghubungkan data dari posisi tertentu dalam daftar ke ViewHolder.
     */
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val scanResult = getItem(position)
        holder.bind(scanResult)
    }

    /**
     * ViewHolder bertugas menyimpan referensi ke view dalam satu item layout
     * dan menempatkan data ke dalamnya.
     */
    class HistoryViewHolder(
        itemView: View,
        private val onPinClicked: (ScanResult) -> Unit // Simpan fungsi callback
    ) : RecyclerView.ViewHolder(itemView) {

        // Simpan referensi ke semua view yang akan diubah
        private val imageViewScan: ImageView = itemView.findViewById(R.id.imageViewScan)
        private val textViewPrediction: TextView = itemView.findViewById(R.id.textViewPrediction)
        private val textViewConfidence: TextView = itemView.findViewById(R.id.textViewConfidence)
        private val textViewDate: TextView = itemView.findViewById(R.id.textViewScanDate)
        private val imageViewPin: ImageView = itemView.findViewById(R.id.imageViewPin)

        /**
         * Fungsi ini dipanggil dari onBindViewHolder untuk mengisi view dengan data
         * dari objek ScanResult.
         */
        fun bind(scanResult: ScanResult) {
            // Set teks prediksi dan akurasi
            textViewPrediction.text = scanResult.prediction
            textViewConfidence.text = String.format(itemView.context.getString(R.string.accuracy_format), scanResult.confidence * 100)

            // Format timestamp menjadi tanggal yang mudah dibaca
            val sdf = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.getDefault())
            textViewDate.text = sdf.format(Date(scanResult.timestamp))

            // Muat gambar dari URL menggunakan Glide
            Glide.with(itemView.context)
                .load(scanResult.imageUrl)
                .placeholder(R.drawable.ic_placeholder) // Gambar saat loading
                .error(R.drawable.ic_error)             // Gambar jika terjadi error
                .into(imageViewScan)

            // Atur ikon pin berdasarkan status isPinned dari data
            if (scanResult.isPinned) {
                imageViewPin.setImageResource(R.drawable.ic_star_on)
            } else {
                imageViewPin.setImageResource(R.drawable.ic_star_off)
            }

            // Atur OnClickListener untuk ikon pin
            imageViewPin.setOnClickListener {
                // Panggil fungsi callback yang diberikan dari Fragment,
                // kirim data item yang di-klik sebagai parameter.
                onPinClicked(scanResult)
            }
        }
    }
}

/**
 * DiffUtil membantu RecyclerView untuk mengetahui item mana yang berubah,
 * ditambahkan, atau dihapus, sehingga animasi bisa berjalan dengan efisien
 * tanpa perlu me-refresh seluruh daftar.
 */
class ScanResultDiffCallback : DiffUtil.ItemCallback<ScanResult>() {
    override fun areItemsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
        // Item dianggap sama jika ID-nya sama
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ScanResult, newItem: ScanResult): Boolean {
        // Konten dianggap sama jika semua properti objeknya sama
        return oldItem == newItem
    }
}
