
---

# Aplikasi Deteksi Depresi Berbasis Android

Aplikasi ini adalah sebuah prototipe yang dirancang untuk membantu pengguna dalam mendeteksi potensi tingkat depresi melalui analisis gambar wajah. Dibangun dengan teknologi Android modern, aplikasi ini menawarkan antarmuka yang ramah pengguna dan fungsionalitas yang kuat.

---

## **Group Members**
* **Henry Salim**
* **Livia Junike**
* **Willbert Budi Lian**
* **Yehezkiel Natanael**


## ✨ Fitur Utama

Aplikasi ini dirancang dengan beberapa fitur inti yang berpusat pada pemindaian wajah untuk prediksi depresi, didukung oleh fungsionalitas yang modern dan *user-friendly*.

### 1. Prediksi Tingkat Depresi Melalui Gambar
Fitur paling utama dari aplikasi ini adalah kemampuannya untuk menganalisis gambar wajah dan memberikan hasil prediksi tingkat depresi.

- **Dua Pilihan Sumber Gambar**: Pengguna memiliki fleksibilitas untuk memilih gambar dari dua sumber:
  - **Buka Kamera**: Mengambil foto secara langsung menggunakan kamera perangkat untuk analisis *real-time*.
  - **Pilih dari Galeri**: Mengunggah gambar yang sudah ada dari galeri ponsel.
- **Pratinjau Gambar**: Sebelum diunggah, gambar yang dipilih atau diambil akan ditampilkan dalam pratinjau (`ImageView`) untuk memastikan pengguna memilih gambar yang benar.
- **Proses Unggah dan Analisis**: Setelah pengguna menekan tombol "Upload", aplikasi akan:
  - Mengonversi gambar ke format yang sesuai (`MultipartBody.Part`).
  - Mengirim gambar ke backend untuk diproses oleh model *machine learning*.
  - Menampilkan hasil prediksi (misalnya, "Tingkat Depresi: Depresi") langsung di bawah area pratinjau.
- **Manajemen Izin (Permission Handling)**: Aplikasi secara cerdas meminta izin yang diperlukan (`CAMERA` dan `READ_MEDIA_IMAGES`/`READ_EXTERNAL_STORAGE`) sebelum mengakses kamera atau galeri, memastikan aplikasi berjalan lancar dan aman sesuai panduan Android terbaru.

### 2. Riwayat Pemindaian (Scan History)
Semua hasil pemindaian disimpan dalam daftar riwayat yang interaktif.

- **Tampilan Berbasis Card**: Setiap entri riwayat ditampilkan dalam *card* yang informatif.
- **Pin & Urutkan**: Pengguna dapat menandai item riwayat sebagai "penting". Item yang di-*pin* akan selalu berada di urutan teratas, diikuti oleh item lain yang diurutkan berdasarkan waktu terbaru.

### 3. Fitur Artikel Edukatif
Aplikasi ini menyediakan kumpulan artikel pilihan untuk meningkatkan kesadaran pengguna tentang kesehatan mental.

- **Daftar Artikel Menarik**: Artikel disajikan dalam format *card* yang menarik secara visual, lengkap dengan gambar thumbnail, judul, dan estimasi waktu baca.
- **Akses ke Sumber Asli**: Saat artikel ditekan, aplikasi akan membuka browser untuk menampilkan konten lengkap dari sumbernya.

---

## 🏛️ Arsitektur Modern
Aplikasi ini dibangun menggunakan komponen dan pustaka modern yang direkomendasikan oleh Google untuk memastikan kode yang bersih, efisien, dan mudah dikelola.

- **Bahasa & Asynchronous**:
  - **Kotlin**: Kode ditulis sepenuhnya dalam Kotlin.
  - **Coroutines**: Dimanfaatkan untuk menangani proses *asynchronous* seperti upload gambar tanpa memblokir UI.
- **Komponen Arsitektur Android**:
  - **Activity Result API**: Menggunakan API modern (`registerForActivityResult`) untuk menangani permintaan izin dan hasil dari galeri/kamera, menggantikan metode `onActivityResult` yang sudah usang.
  - **Fragment & ViewModel**: Menggunakan arsitektur berbasis Fragment dengan **ViewModel** (misalnya `AuthViewModel`) untuk mengelola dan mempertahankan *state* data, bahkan saat konfigurasi perangkat berubah.
- **UI & Interaksi**:
  - **View Binding**: Untuk interaksi UI yang aman dari *null pointer exceptions*.
  - **Coil**: Untuk memuat gambar dari URI secara efisien ke `ImageView`.
- **Keamanan**:
  - **FileProvider**: Mengimplementasikan `FileProvider` untuk berbagi URI file dengan aman antara aplikasi, sesuai dengan praktik keamanan terbaik Android.


## Prasyarat & Dependensi

Sebelum memulai, pastikan Anda telah memenuhi persyaratan berikut:

  * **Android Studio:** IDE resmi untuk pengembangan aplikasi Android. Unduh versi terbaru dari [Android Developers](https://developer.android.com/studio).
  * **Kotlin:** Pastikan Kotlin telah dikonfigurasi dengan benar di dalam Android Studio.
  * **Android SDK:** Instal versi Android SDK yang diperlukan oleh proyek. Konfigurasi dapat dilakukan melalui SDK Manager di Android Studio.
  * **Gradle:** Proyek ini menggunakan Gradle sebagai sistem build. Android Studio biasanya menangani pengaturan Gradle secara otomatis.
  * **Emulator/Perangkat Fisik:** Emulator Android atau perangkat Android fisik diperlukan untuk menjalankan aplikasi.
  * **Pustaka Machine Learning:** (Sebutkan di sini, contoh: TensorFlow Lite) - Jika proyek menggunakan pustaka ML tertentu, sebutkan nama dan versinya.

-----

## Instalasi & Panduan Pengaturan

1.  **Clone Repositori:**

    ```bash
    git clone https://github.com/rascalosh/map-midterm-exam.git
    cd map-midterm-exam
    ```

2.  **Buka Proyek di Android Studio:**

      * Jalankan Android Studio.
      * Pilih "Open an Existing Project" dan arahkan ke direktori `map-midterm-exam` yang telah di-clone.

3.  **Sinkronkan Gradle:**

      * Android Studio akan meminta Anda untuk menyinkronkan proyek Gradle. Klik "Sync Now".

4.  **Instal Dependensi:**

      * Jika proyek menggunakan pustaka atau dependensi tertentu, mereka akan diunduh dan diinstal secara otomatis oleh Gradle. Jika terjadi *error*, periksa koneksi internet dan konfigurasi Gradle Anda.

5.  **Konfigurasi Emulator/Hubungkan Perangkat:**

      * Jalankan emulator Android (menggunakan Android Virtual Device Manager) atau hubungkan perangkat Android fisik Anda ke komputer. Pastikan *USB debugging* telah diaktifkan pada perangkat Anda.

6.  **Jalankan Aplikasi:**

      * Klik tombol "Run" (ikon play hijau) di Android Studio.
      * Pilih perangkat atau emulator yang terhubung.
      * Aplikasi akan di-*build* dan diinstal pada perangkat/emulator yang dipilih.

-----

## Panduan Berkontribusi

Kontribusi sangat diterima! Jika Anda ingin berkontribusi pada proyek ini, silakan ikuti panduan berikut:

1.  **Fork Repositori:** Buat *fork* dari repositori ini ke akun Anda.
2.  **Buat Branch:** Buat *branch* baru untuk fitur atau perbaikan *bug* Anda.
3.  **Lakukan Perubahan:** Terapkan perubahan Anda, dengan mematuhi gaya dan konvensi pengkodean proyek.
4.  **Uji Secara Menyeluruh:** Pastikan perubahan Anda telah diuji dengan baik dan tidak menimbulkan masalah baru.
5.  **Kirim Pull Request:** Kirim *pull request* ke *branch* `main` dari repositori asli.
6.  **Tinjauan Kode:** *Pull request* Anda akan ditinjau oleh pemilik proyek. Bersiaplah untuk menanggapi setiap masukan atau saran.

-----

## Informasi Lisensi

Proyek ini saat ini tidak memiliki lisensi yang spesifik. Semua hak dilindungi oleh kelompok.

-----

## Ucapan Terima Kasih

  * Pengembangan proyek ini dibimbing oleh materi dari mata kuliah Pemrograman Aplikasi Mobile.
  * Implementasinya memanfaatkan kemampuan *machine learning* dari (Sebutkan Pustaka ML yang digunakan, contoh: TensorFlow Lite).
