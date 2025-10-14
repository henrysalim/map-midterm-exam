package com.example.midtermexam.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.midtermexam.adapter.HistoryAdapter
import com.example.midtermexam.data.ScanResult
import com.example.midtermexam.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter
    // Gunakan MutableList agar kita bisa memodifikasi daftar (misalnya, status pin)
    private var historyData = mutableListOf<ScanResult>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Hanya muat data dummy sekali saja saat aplikasi pertama kali dibuka
        if (historyData.isEmpty()) {
            loadDummyHistoryData()
        }

        // Panggil fungsi untuk mengurutkan dan menampilkan daftar
        updateAndSortList()
    }

    private fun setupRecyclerView() {
        // Inisialisasi adapter dan berikan aksi untuk 'onPinClicked'
        historyAdapter = HistoryAdapter { scanResult ->
            // Aksi ini akan dijalankan saat ikon pin di-klik
            handlePinClick(scanResult)
        }
        binding.recyclerViewHistory.adapter = historyAdapter
    }


    private fun handlePinClick(clickedItem: ScanResult) {

        val itemIndex = historyData.indexOfFirst { it.id == clickedItem.id }

        // Pastikan item ditemukan
        if (itemIndex != -1) {
            val oldItem = historyData[itemIndex]

            val newItem = oldItem.copy(isPinned = !oldItem.isPinned)

            historyData[itemIndex] = newItem
        }

        updateAndSortList()
    }


    private fun updateAndSortList() {
        // 1. Item yang di-pin (isPinned=true) akan selalu di atas.
        // 2. Item lain akan diurutkan berdasarkan waktu (timestamp) terbaru di atas.
        val sortedList = historyData.sortedWith(
            compareByDescending<ScanResult> { it.isPinned }
                .thenByDescending { it.timestamp }
        )

        // Cek jika daftar kosong, tampilkan pesan
        if (sortedList.isEmpty()) {
            binding.recyclerViewHistory.visibility = View.GONE
            binding.textViewEmptyHistory.visibility = View.VISIBLE
        } else {
            binding.recyclerViewHistory.visibility = View.VISIBLE
            binding.textViewEmptyHistory.visibility = View.GONE
            // Kirim daftar yang sudah terurut ke adapter
            historyAdapter.submitList(sortedList.toList()) // .toList() untuk keamanan
        }
    }

    private fun loadDummyHistoryData() {
        // Data dummy untuk "scan muka depresi"
        // Kita tambahkan data ini ke `historyData`
        historyData.addAll(listOf(
            ScanResult(
                id = "1",
                prediction = "Tingkat Depresi: Rendah",
                confidence = 0.852,
                timestamp = System.currentTimeMillis() - 86400000, // 1 hari yang lalu
                imageUrl = "https://i.pravatar.cc/150?img=5"
            ),
            ScanResult(
                id = "2",
                prediction = "Tidak Terdeteksi Depresi",
                confidence = 0.971,
                timestamp = System.currentTimeMillis() - 172800000, // 2 hari yang lalu
                imageUrl = "https://i.pravatar.cc/150?img=12"
            ),
            ScanResult(
                id = "3",
                prediction = "Tingkat Depresi: Sedang",
                confidence = 0.785,
                timestamp = System.currentTimeMillis() - 259200000, // 3 hari yang lalu
                imageUrl = "https://i.pravatar.cc/150?img=3",
                isPinned = true // Contoh item yang sudah di-pin dari awal
            ),
            ScanResult(
                id = "4",
                prediction = "Tingkat Depresi: Tinggi",
                confidence = 0.913,
                timestamp = System.currentTimeMillis() - 345600000, // 4 hari yang lalu
                imageUrl = "https://i.pravatar.cc/150?img=8"
            )
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set _binding ke null untuk menghindari memory leak
        _binding = null
    }
}