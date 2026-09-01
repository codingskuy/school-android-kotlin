# Roadmap: Android Development dengan Kotlin

## 🎯 Tahap 1: Proyek Pertama — "Kalkulator Tip" (Jetpack Compose)
- [x] **Jetpack Compose Dasar** — Text, Button, state (`remember`/`mutableStateOf`). UI deklaratif: kita deskripsikan "UI harus seperti apa", bukan "bagaimana membuatnya" step-by-step.
- [x] **Layout Compose & Material 3** — Column, Row, Box, Spacer, padding, tema.
- [x] **Proyek 1: Kalkulator Tip** 🚀 — app pertama: input nominal, slider, hitung tip.

## 📋 Tahap 2: List & Navigasi — "Aplikasi To-Do"
- [x] **LazyColumn & List** — menampilkan data berulang dengan 5 baris kode (bandingkan: RecyclerView butuh 50+ baris — kita lihat nanti di Tahap 3).
- [x] **Navigation Compose** — pindah antar layar dengan rute.
- [x] **Proyek 2: To-Do List** 🚀 — tambah tugas, centang selesai, hapus.

## 🏗️ Tahap 3: XML Literacy — Bisa Baca & Rawat Kode Legacy
- [x] **View System & XML Layout** — LinearLayout, ConstraintLayout, atribut XML. Kenapa masih dipakai: jutaan app lama belum dimigrasi.
- [x] **Activity, Fragment & ViewBinding** — `findViewById` vs ViewBinding, lifecycle Activity/Fragment. Kamu akan banyak menemui ini di proyek kantor.
- [x] **RecyclerView & Adapter** — "LazyColumn-nya era lama". Paham kenapa 50+ baris ini, supaya kamu bisa jelaskan kenapa Compose lebih baik (pertanyaan favorit interviewer!).
- [x] **Proyek 3: Layar Profil XML** 🚀 — bikin 1 layar pakai XML + RecyclerView. Cukup buat jujur bilang "saya bisa dua-duanya" di wawancara.

## 🏛️ Tahap 4: Arsitektur Production-Grade
- [x] **ViewModel & MVVM** — pisahkan UI dari logika. Fondasi semua app profesional.
- [x] **Coroutines & Flow** — async programming (kenapa `suspend`, bukan thread).
- [x] **Separation of Concerns & Clean Architecture** — pecah project jadi layer: UI → Domain → Data. Wajib biar project gampang dirawat & discale.
- [x] **Design Patterns Android** — Repository, Singleton, Factory. Di mana dan kapan dipakai.
- [x] **Dependency Injection dengan Hilt** — kelola dependensi otomatis, hilangkan manual wiring.
- [x] **Proyek 4: Refactor To-Do List** 🚀 — rombak jadi Clean Architecture + Hilt. Latihan "pindah dari bikin app → engineer".

## 🌐 Tahap 5: Networking — "Aplikasi Cuaca"
- [x] **Retrofit & JSON Parsing** — ambil data dari API (API cuaca gratis), parsing JSON ke data class.
- [x] **State Management untuk API** — loading, sukses, error (sealed class + StateFlow).
- [x] **Proyek 5: Aplikasi Cuaca** 🚀 — tampilkan cuaca per kota, lengkap dengan error handling.

## 💾 Tahap 6: Penyimpanan Data — "Aplikasi Catatan"
- [x] **Room Database** — simpan data permanen (SQLite yang dibungkus rapi, punya fitur Flow).
- [x] **Proyek 6: Catatan Harian** 🚀 — tulis, edit, hapus catatan tersimpan di database.

## 🧪 Tahap 7: Testing — "Jangan takut merusak kode"
- [x] **Unit Testing** — JUnit + test ViewModel & repository (logic bener).
- [x] **UI Testing** — Compose UI Test (tombol bisa diklik, tampilan muncul).
- [x] **Regression Testing** — strategi jalankan SEMUA test lama tiap perubahan (Unit + UI) untuk deteksi bongkaran. Bukan jenis test baru, tapi kebiasaan pro. Praktik: ubah entity → lihat test lama merah → betulin.
- [x] **Proyek 7: Enhance Testing Weather App** 🚀 — enhance weather-app: AirQuality feature (TDD) + 4 unit test (UseCase/Repo/ViewModel) + 5 UI test (Weather & AirQualityScreen) + regression via qa-regression.sh (--auto, generic, branch) + Kover. Dianggap impas untuk To-Do List (level sama, beda app).

## 🤖 Tahap 8: CI/CD — Deploy Otomatis (senjata andalanmu)
- [x] **GitHub Actions: Build Otomatis** — tiap `git push`, workflow otomatis compile project.
- [x] **Regression Otomatis via CI (Quality Gate)** — tiap push/PR, robot CI jalanin SEMUA test (Unit + UI) sebagai regression. Kalau merah / coverage turun → PR ditolak. Ini regression yang kamu tanya!
- [x] **Firebase App Distribution (App Tester)** — hasil build otomatis dikirim ke tester, lengkap dengan catatan rilis. Tester install lewat app "Firebase App Tester" — nggak perlu USB.
- [x] **Signing & Release** — signed APK/AAB, versioning (semantic version), siap distribusi.
- [x] **Proyek 8: Pipeline CI/CD To-Do List** 🚀 — push → GitHub Actions build + regression test → otomatis ter-deploy ke Firebase App Tester. Ini yang bakal kamu demo-in di wawancara!

## 🏆 Tahap 9: Proyek Portofolio Final
- [x] **Proyek Final: App Portofolio** 🏆 — kamu pilih ide sendiri. Wajib: arsitektur clean, testing, regression, CI/CD ke Firebase. Inilah bukti "aku siap kerja".
