# map-midterm-exam

Repository for IF570 Mobile Application Programming Midterm Exam for Group A, integrated with Machine Learning to detect whether someone is depressed/not by scanning their facial expressions

## **Group Members**
* **Henry Salim**
* **Livia Junike**
* **Willbert Budi Lian**
* **Yehezkiel Natanael**

Aplikasi Deteksi Depresi Berbasis Android

Aplikasi ini adalah sebuah prototipe yang dirancang untuk membantu pengguna dalam mendeteksi potensi tingkat depresi melalui analisis gambar wajah. Dibangun dengan teknologi Android modern, aplikasi ini menawarkan antarmuka yang ramah pengguna dan fungsionalitas yang kuat.

## ✨ Fitur Utama

Aplikasi ini dirancang dengan beberapa fitur inti yang berpusat pada pemindaian wajah untuk prediksi depresi, didukung oleh fungsionalitas yang modern dan user-friendly.

* **1. Prediksi Tingkat Depresi Melalui Gambar**

Fitur paling utama dari aplikasi ini adalah kemampuannya untuk menganalisis gambar wajah dan memberikan hasil prediksi tingkat depresi.

Dua Pilihan Sumber Gambar: Pengguna memiliki fleksibilitas untuk memilih gambar dari dua sumber:

Buka Kamera: Mengambil foto secara langsung menggunakan kamera perangkat untuk analisis real-time.

Pilih dari Galeri: Mengunggah gambar yang sudah ada dari galeri ponsel.

Pratinjau Gambar: Sebelum diunggah, gambar yang dipilih atau diambil akan ditampilkan dalam pratinjau (ImageView) untuk memastikan pengguna memilih gambar yang benar.

Proses Unggah dan Analisis: Setelah pengguna menekan tombol "Upload", aplikasi akan:

Mengonversi gambar ke format yang sesuai (MultipartBody.Part).

Mengirim gambar ke backend untuk diproses oleh model machine learning.

Menampilkan hasil prediksi (misalnya, "Tingkat Depresi: Depresi") langsung di bawah area pratinjau.

Manajemen Izin (Permission Handling): Aplikasi secara cerdas meminta izin yang diperlukan (CAMERA dan READ_MEDIA_IMAGES/READ_EXTERNAL_STORAGE) sebelum mengakses kamera atau galeri, memastikan aplikasi berjalan lancar dan aman sesuai panduan Android terbaru.

* **2. Riwayat Pemindaian (Scan History)**

Semua hasil pemindaian disimpan dalam daftar riwayat yang interaktif.

Tampilan Berbasis Card: Setiap entri riwayat ditampilkan dalam card yang informatif.

Pin & Urutkan: Pengguna dapat menandai item riwayat sebagai "penting". Item yang di-pin akan selalu berada di urutan teratas, diikuti oleh item lain yang diurutkan berdasarkan waktu terbaru.

* **3. Fitur Artikel Edukatif**

Aplikasi ini menyediakan kumpulan artikel pilihan untuk meningkatkan kesadaran pengguna tentang kesehatan mental.

Daftar Artikel Menarik: Artikel disajikan dalam format card yang menarik secara visual, lengkap dengan gambar thumbnail, judul, dan estimasi waktu baca.

Akses ke Sumber Asli: Saat artikel ditekan, aplikasi akan membuka browser untuk menampilkan konten lengkap dari sumbernya.

* **🏛️ Arsitektur Modern**

Aplikasi ini dibangun menggunakan komponen dan pustaka modern yang direkomendasikan oleh Google.

Kotlin & Coroutines: Kode ditulis sepenuhnya dalam Kotlin dengan memanfaatkan Coroutines untuk menangani proses asynchronous seperti upload gambar tanpa memblokir UI.

Activity Result API: Menggunakan API modern (registerForActivityResult) untuk menangani permintaan izin dan hasil dari galeri/kamera, menggantikan metode onActivityResult yang sudah usang.

Fragment & ViewModel: Menggunakan arsitektur berbasis Fragment dengan ViewModel (AuthViewModel) untuk mengelola dan mempertahankan state data, bahkan saat konfigurasi perangkat berubah (seperti rotasi layar).

View Binding & Coil: Menggunakan View Binding untuk interaksi UI yang aman dan Coil untuk memuat gambar dari URI secara efisien ke ImageView.

FileProvider: Mengimplementasikan FileProvider untuk berbagi URI file dengan aman antara aplikasi, sesuai dengan praktik keamanan terbaik Android.

## Prerequisites & Dependencies

Before you begin, ensure you have met the following requirements:

*   **Android Studio:** The official IDE for Android app development. Download the latest version from [Android Developers](https://developer.android.com/studio).
*   **Kotlin:** Ensure that Kotlin is properly configured within Android Studio.
*   **Android SDK:** Install the necessary Android SDK versions required by the project. Configure through Android Studio's SDK Manager.
*   **Gradle:** The project uses Gradle as its build system. Android Studio typically handles Gradle setup automatically.
*   **Emulator/Physical Device:** An Android emulator or a physical Android device is required to run the application.
*   **Machine Learning Libraries:** (Specify here, e.g., TensorFlow Lite) - If the project uses specific ML libraries, mention them and their versions.

## Installation & Setup Instructions

1.  **Clone the Repository:**

    ```bash
    git clone https://github.com/rascalosh/map-midterm-exam.git
    cd map-midterm-exam
    ```

2.  **Open the Project in Android Studio:**

    *   Launch Android Studio.
    *   Select "Open an Existing Project" and navigate to the cloned `map-midterm-exam` directory.

3.  **Sync Gradle:**

    *   Android Studio will prompt you to sync the Gradle project. Click "Sync Now".

4.  **Install Dependencies:**

    *   If the project uses any specific libraries or dependencies, they will be downloaded and installed automatically by Gradle. If any errors occur, check your internet connection and Gradle configuration.

5.  **Configure Emulator/Connect Device:**

    *   Start an Android emulator (using Android Virtual Device Manager) or connect your physical Android device to your computer. Ensure that USB debugging is enabled on your device.

6.  **Run the Application:**

    *   Click the "Run" button (green play icon) in Android Studio.
    *   Select your connected device or emulator.
    *   The application will be built and installed on the selected device/emulator.

## Usage Examples & API Documentation

Due to the nature of this project being a midterm exam submission, detailed API documentation might not be available. Refer to the code comments for specific functionalities and usage details.

*   **Running the App:** Once installed, the app's main screen will allow users to interact with the depression detection feature.
*   **Input Methods:** The app might require user input through text fields, questionnaires, or sensor data (if applicable).
*   **Output Interpretation:** The app will provide an output based on the machine learning model's analysis. Interpret the results carefully and seek professional help if needed.

## Configuration Options

This section describes any configurable settings or parameters within the application.

*   **ML Model Configuration:** If possible, provide details on how the machine learning model can be configured or updated. This might involve specifying model file paths or adjusting prediction thresholds.
*   **API Keys (if applicable):** If the app uses any external APIs, provide instructions on setting up API keys.
*   **User Interface Settings:** Any user-customizable settings within the app (e.g., theme, font size) should be explained here.

## Contributing Guidelines

Contributions are welcome! If you'd like to contribute to this project, please follow these guidelines:

1.  **Fork the Repository:** Create your own fork of the repository.
2.  **Create a Branch:** Create a new branch for your feature or bug fix.
3.  **Make Changes:** Implement your changes, adhering to the project's coding style and conventions.
4.  **Test Thoroughly:** Ensure that your changes are well-tested and do not introduce new issues.
5.  **Submit a Pull Request:** Submit a pull request to the `main` branch of the original repository.
6.  **Code Review:** Your pull request will be reviewed by the project owner. Be prepared to address any feedback or suggestions.

## License Information

The project does not currently have a specified license. All rights are reserved by the group.

## Acknowledgments

*   The development of this project was guided by the course materials of the Mobile Application Programming course.
*   The implementation leverages the machine learning capabilities of (Specify used ML Libraries, e.g., TensorFlow Lite)
