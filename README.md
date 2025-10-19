
---

# MindLens, Aplikasi Deteksi Depresi Berbasis Android

Aplikasi ini adalah sebuah prototipe yang dirancang untuk membantu pengguna dalam mendeteksi potensi tingkat depresi melalui analisis gambar wajah. Dibangun dengan teknologi Android modern, aplikasi ini menawarkan antarmuka yang ramah pengguna dan fungsionalitas yang kuat.

---

## **Group Members**
* **Henry Salim**
* **Livia Junike**
* **Willbert Budi Lian**
* **Yehezkiel Natanael**


## Fitur Utama

Aplikasi ini dirancang dengan beberapa fitur inti yang berpusat pada pemindaian wajah untuk prediksi depresi, didukung oleh fungsionalitas yang modern dan *user-friendly*.

### 1. Prediksi Tingkat Depresi Melalui Gambar
Fitur paling utama dari aplikasi ini adalah kemampuannya untuk menganalisis gambar wajah dan memberikan hasil prediksi tingkat depresi.

- **Dua Pilihan Sumber Gambar**: Pengguna memiliki fleksibilitas untuk memilih gambar dari dua sumber:
  - **Buka Kamera**: Mengambil foto secara langsung menggunakan kamera perangkat untuk analisis *real-time*.
  - **Pilih dari Galeri**: Mengunggah gambar yang sudah ada dari galeri ponsel.
- **Pratinjau Gambar**: Sebelum diunggah, gambar yang dipilih atau diambil akan ditampilkan dalam pratinjau (`ImageView`) untuk memastikan pengguna memilih gambar yang benar.
- **Manajemen Izin (Permission Handling)**: Aplikasi secara cerdas meminta izin yang diperlukan (`CAMERA` dan `READ_MEDIA_IMAGES`/`READ_EXTERNAL_STORAGE`) sebelum mengakses kamera atau galeri, memastikan aplikasi berjalan lancar dan aman sesuai panduan Android terbaru.

### 2. Riwayat Pemindaian (Scan History)
Semua hasil pemindaian disimpan dalam daftar riwayat yang interaktif.

- **Tampilan Berbasis Card**: Setiap entri riwayat ditampilkan dalam *card* yang informatif.
- **Pin & Urutkan**: Pengguna dapat menandai item riwayat sebagai "penting". Item yang di-*pin* akan selalu berada di urutan teratas, diikuti oleh item lain yang diurutkan berdasarkan waktu terbaru.

### 3. Fitur Artikel Edukatif
Aplikasi ini menyediakan kumpulan artikel pilihan untuk meningkatkan kesadaran pengguna tentang kesehatan mental.

- **Daftar Artikel Menarik**: Artikel disajikan dalam format *card* yang menarik secara visual, lengkap dengan gambar thumbnail, judul, dan estimasi waktu baca.
- **Akses ke Sumber Asli**: Saat artikel ditekan, aplikasi akan membuka browser untuk menampilkan konten lengkap dari sumbernya.

### 4. Fitur Profile
Pengguna dapat mengurus akun yang telah dibuat di aplikasi ini melalui halaman Profile ini.

- **Edit Profile** : Pengguna dapat menyunting _credentials_ akun mereka seperti nama, usernama, dll.
- **Change Password** : Pengguna dapat mengubah kata sandi dari akun yang mereka gunakan.
- **Logout** : Pengguna dapat mengeluarkan akun (logout) dari aplikasi yang sudah dilengkapi confirmation popup guna mencegah ketidaksengajaan.
---
