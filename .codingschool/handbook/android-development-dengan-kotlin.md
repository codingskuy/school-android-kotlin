# Android development dengan Kotlin — Learning Notes

> _This handbook records each learning session with theory summaries and practice notes._

---

## 2026-08-11 11:18:29

**Topic:** Jetpack Compose Dasar

**Theory:**
Jetpack Compose = toolkit UI modern Android (seperti Flutter/SwiftUI). Deklaratif: kita deskripsikan UI berdasarkan state, bukan instruksi step-by-step.
- Composable = fungsi Kotlin dengan anotasi @Composable yang "menggambar" UI
- UI dipasang lewat setContent { } di dalam Activity (biasanya MainActivity)
- Data flow parent → child lewat parameter fungsi (seperti props Flutter)
- @Preview = lihat tampilan UI tanpa run app
- Scaffold = container Material 3, Modifier = penyesuaian layout (padding, fillMaxSize)
- Analogi Flutter: Widget→Composable, setState→mutableStateOf/recompose, Column/Row sama persis

**Practice:**
- Bikin project TipCalculator (Empty Activity template, Kotlin, package io.codingskuy.kalkulatortip)
- App berhasil running di emulator
- Ubah Greeting name="HAlo, Rois! Ini app Android pertamaku" → muncul "Hello HAlo, Rois!..."
- Belajar bahwa prefix "Hello" berasal dari dalam composable Greeting (data flow parent→child)
- Cek pemahaman: @Composable membuat fungsi bisa render UI ✅, @Preview = preview cepat tanpa run ✅

**Progress:** 100% complete

---
## 2026-08-11 11:52:30

**Topic:** Layout Compose & Material 3

**Theory:**
- Layout Compose: Column (vertikal) & Row (horizontal) — nama sama persis dengan Flutter
- Modifier = "rantai pengaturan" yang ditempel ke composable (padding, fillMaxWidth, width, height)
- Arrangement.spacedBy(16.dp) = jarak antar elemen (≈ SizedBox di Flutter)
- Arrangement.SpaceEvenly = sebar elemen rata (≈ mainAxisAlignment.spaceEvenly)
- Material 3 = sistem desain Google (KalkulatorTipTheme, Scaffold) ≈ MaterialApp di Flutter
- CONTOH KESALAHAN: modifier.width(16.dp) = bikin lebar 16dp, BUKAN jarak antar elemen
- ATURAN EMAS: modifier yang diterima composable dipasang SATU KALI di elemen root, sisanya pakai Modifier fresh
- SafeArea ≠ margin: innerPadding dari Scaffold hanya berisi inset sistem (status bar atas, nav bar bawah). Di mode potret, inset kiri/kanan = 0
- Margin desain ditambah manual: modifier.padding(16.dp) — padding menumpuk di atas innerPadding

**Practice:**
- Rombak Greeting jadi kerangka Kalkulator Tip: Column (judul + subtitle) + Spacer + Row (5% 10% 15%)
- Debug: Row tidak melebar (fix: fillMaxWidth), modifier dipakai 3x (fix: sekali di root), SafeArea terbuang (fix: Column(modifier = modifier)), margin kiri tidak ada (fix: modifier.padding(16.dp))
- Hasil akhir sesuai ekspektasi: judul, subtitle, deretan persentase dengan jarak 16dp dari tepi

**Progress:** 100% complete

---
## 2026-08-11 15:07:52

**Topic:** Proyek 1: Kalkulator Tip 🚀

**Theory:**
- State di Compose: `var x by remember { mutableStateOf(...) }` — butuh import getValue/setValue
- Pola universal input: `value = state` + `onValueChange = { state = it }` (TextField, Slider, dll)
- Derived state: JANGAN simpan hasil hitungan sebagai state — hitung langsung dari state sumber (bill, tipPercent → billAmount, tipAmount, total)
- Null safety: `toDoubleOrNull()` mengembalikan null (bukan crash) + elvis `?: 0.0` untuk default (≈ `Number(x) ?? 0` di TS)
- var = state yang bisa berubah; val = turunan read-only
- Format angka: String.format("%,d", value) → ribuan pakai titik
- Slider: valueRange = 0f..30f, steps = jumlah titik diskrit antar min-max

**Practice:**
- Proyek 1 KALKULATOR TIP SELESAI ✅ (app Android pertama!)
- Langkah 1: OutlinedTextField + state bill + tampilkan ketikan
- Langkah 2: Slider + state tipPercent (Float, default 15f)
- Langkah 3: perhitungan (billAmount, tipAmount, total) + tampilan hasil + format ribuan
- Bonus: String.format("%,d", tipAmount.toInt()) untuk format Rp 7.750
- Kebiasaan baik: membersihkan import yang tidak terpakai

**Progress:** 100% complete

---
## 2026-08-11 15:41:13

**Topic:** LazyColumn & List

**Theory:**
- LazyColumn ≈ ListView.builder di Flutter — hanya menggambar item yang terlihat (lazy/efisien), cocok untuk list panjang
- Column = semua anak digambar langsung (layout statis, <10 item); LazyColumn = sesuai kebutuhan (list)
- items(list) { item -> ... } dari androidx.compose.foundation.lazy
- Di era XML: RecyclerView + Adapter + ViewHolder butuh 50+ baris — LazyColumn cuma 5 baris
- Konvensi root modifier tetap berlaku: LazyColumn(modifier = modifier)

**Practice:**
- Project baru TodoApp (package io.codingskuy.todo) dengan template Empty Activity
- List statis 5 tugas tampil dengan LazyColumn + items + padding 16dp + bodyLarge
- Berhasil run dan scroll mulus

**Progress:** 100% complete

---
## 2026-08-11 16:46:53

**Topic:** Navigation Compose

**Theory:**
- Navigation Compose: pindah layar dengan rute (route string). `rememberNavController()` + `NavHost(startDestination = ...)` mendefinisikan peta layar; `composable("rute") { ... }` mendaftarkan tiap layar; `navController.navigate("rute")` untuk pindah; `popBackStack()` untuk kembali.
- Lifecycle Compose: `remember { }` mati saat composable keluar dari composition (navigate = hilang). Karena itu state yang dipakai banyak layar harus diangkat ke ViewModel.
- Version pinning: dialog/IDE bisa kasih versi beta; selalu pin versi stable. navigation-compose 2.10.0-beta01 butuh compileSdk 37; stable 2.9.8 kompatibel dengan compileSdk 36.

**Practice:**
- ToDo app: NavHost dengan route "tasks" & "add", FAB → navigate("add"), Simpan → popBackStack(), LazyColumn pakai modifier.padding(innerPadding) dari Scaffold.
- libs.versions.toml: navigationCompose = "2.9.8" (pin stable, bukan beta).

**Progress:** 100% complete

---
## 2026-08-11 17:22:24

**Topic:** Proyek 2: To-Do List

**Theory:**
- Pola "Mailbox" (SavedStateHandle): layar pengirim taruh hasil ke `previousBackStackEntry.savedStateHandle.set("key", value)` sebelum `popBackStack()`. Layar penerima baca lewat `currentBackStackEntry.savedStateHandle.getStateFlow("key", null)` + `collectAsState()`. PENTING: reset key ke `null` setelah dikonsumsi biar tidak terbaca dua kali.
- Hoisting state: data yang dipakai banyak layar diangkat ke atas NavHost dengan `remember { mutableStateListOf(...) }` — hidup di atas navigasi, nggak ikut mati pas pindah layar.
- mutableListOf vs mutableStateListOf: list biasa perubahannya tidak diamati Compose (bekerja "kebetulan"); mutableStateListOf = snapshot state yang memicu recomposition otomatis (bekerja "by design").

**Practice:**
- ToDo app: tambah tugas sekarang berfungsi. AddTaskScreen onSave(newTask) → mailbox → LaunchedEffect konsumsi → tasks.add(it). TaskListScreen menerima tasks dari state yang di-hoist.
- Bug yang ditemukan & diperbaiki: `{newTask}.toString()` (lambda bukan nilai!) dan state yang tidak reactive.
- TODO sesi depan: centang selesai (done), hapus tugas (delete), lalu ganti mutableListOf → mutableStateListOf.

**Progress:** 100% complete

---
## 2026-08-12 10:04:45

**Topic:** Proyek 2: To-Do List

**Theory:**
- Data model: ganti List<String> → List<Task> (data class dengan id, title, done). id = identitas unik (analogi NIK) — jangan hapus/centang pakai title karena bisa dobel.
- Pola immutable update: `task.copy(done = true)` — data class bikin salinan baru, data lama tidak berubah.
- Update di SnapshotStateList: `tasks[index] = tasks[index].copy(...)` dan `tasks.removeAll { it.id == id }`.
- Id generation yang benar: `(tasks.maxOfOrNull { it.id } ?: 0) + 1` — dihitung dari list yang ada, bukan hardcode. Kesalahan yang ditemukan: hardcode Task(9, ...) bikin id dobel → hapus satu menghapus semua yang id-nya sama.

**Practice:**
- ToDo app lengkap: tambah tugas (mailbox + hoisted state), checkbox centang selesai, tombol hapus.
- Debugging session: ClassCastException String→Task karena sisi tulis (AddTaskScreen) kirim String tapi sisi baca (tasks screen) baca Task. Solusi: Opsi B — penulis kirim String, pembaca yang merakit Task lengkap dengan id unik.
- Lesson: dua sisi mailbox harus kompak soal tipe data. Dead code (fungsi empty()) dihapus.

**Progress:** 100% complete

---
## 2026-08-12 10:44:22

**Topic:** View System & XML Layout

**Theory:**
- XML Layout = HTML versi disiplin: elemen bersarang, atribut, case-sensitive. `<LinearLayout>` = Column/Row (atur via android:orientation vertical/horizontal), `<TextView>` = Text, `<Button>` = Button.
- orientation ≠ gravity: orientation = arah susunan item (ban berjalan); gravity = posisi item DI DALAM ruang (atas ban). android:gravity = ngatur anak; android:layout_gravity = ngatur diri sendiri di parent.
- ConstraintLayout = "paku magnet": tiap elemen diikat ke parent/saudara. Aturan emas: DUA SUMBU wajib keikat (horizontal + vertikal), kalau tidak → warning kuning & posisi "loncat" saat runtime. Pola: Start_toEndOf (geser kanan), Top_toTopOf (sebaris), End_toEndOf/Bottom_toBottomOf (pojok).
- String resources: teks TIDAK boleh nembak di layout (hardcoded). Pisahkan di res/values/strings.xml, panggil dengan @string/nama. Alasan: lokalisasi (bikin file terjemahan per bahasa tanpa bongkar layout) — konsep separation of concerns.
- Layout Editor punya preview instan karena XML cuma data (beda Compose yang harus compile dulu).

**Practice:**
- latihan_layout.xml: LinearLayout horizontal + 3 Button + gravity center; eksperimen orientation & gravity.
- latihan_constrain.xml: ConstraintLayout, tombol diikat ke parent, tombol 2 diikat ke tombol 1 (Start_toEndOf + Top_toTopOf). Perbaiki: 1 sumbu → 2 sumbu, hardcoded string → @string.
- strings.xml: app_name, fab_saya, ke_2.

**Progress:** 100% complete

---
## 2026-08-12 15:11:55

**Topic:** Activity, Fragment & ViewBinding

**Theory:**
- ComponentActivity vs AppCompatActivity: ganti theme crash dengan memilih base class yang pas (ComponentActivity untuk XML ViewBinding tanpa butuhan fitur AppCompat).
- Clickable widget di Compose: `Modifier.clickable { }` — menyamakan sensor sentuh ke baris item LazyColumn.
- Navigasi Intent dari MainActivity (Compose) ke Activity XML: `context.startActivity(Intent(context, CobaActivity::class.java))` dan ke ViewActivity.
- Kedua bug lama sudah diperbaiki: CobaActivity double‑inflate → satu inflate + setContentView(binding.root); ViewActivity findViewById sebelum setContentView → dipindahkan ke dalam onCreate.

**Practice:**
- CobaActivity.kt & ViewActivity.kt berhasil dibangun & diuji (build 0 error, navigasi lancar).
- Clickable(task item) di TaskListScreen berhasil mengarahkan ke CobaActivity & ViewActivity.
- Tombol back di ViewActivity functional via `finish()`.

**Roadmap items updated:** 6/29 total done → 7/29 (menambah item Activity-Fragment-ViewBinding). XP 350 → 400 (+50 dari finishing tahap ini). Competencies: Knowledge 84, Implementation 86, Debugging 84, Teaching 68 (dari mentor-scaffolding tadi).

**Progress:** 100% complete

---
## 2026-08-12 16:57:03

**Topic:** Activity, Fragment & ViewBinding

**Theory:**
- ComponentActivity vs FragmentActivity: untuk Fragment kita butuh FragmentActivity (atau AppCompatActivity) karena menyediakan supportFragmentManager. Memilih kelas yang sesuai menghindari crash dan menyediakan dukungan fragment manager.
- Fragment sebagai modul UI yang dapat ditukar di dalam Activity menggunakan FragmentManager dan FragmentTransaction.
- Pola factory Fragment dengan argumen opsional (newInstance) untuk mengirimkan data ke fragment saat dibentuk.
- ViewBinding bekerja dengan Fragment sama seperti di Activity (inflate binding di onCreateView).

**Practice:**
- Membuat `FragmentActivity` (mengextends FragmentActivity) dengan ViewBinding (`ActivityFragmentBinding.inflate`) dan `setContentView(binding.root)`.
- Dua fragment sederhana: `HeroFragment` (menampilkan teks "Home here") dan `SettingsFragment` (menampilkan teks "Setting here").
- Navigasi antar fragment menggunakan tombol di layout Activity: `supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HeroFragment()).commit()`.
- Semua berjalan lancar: build 0 error, navigasi fragment tanpa crash, tema tetap kompatibel (tanpa perlu AppCompat theme karena menggunakan FragmentActivity).
- Integrasi dengan MainActivity: tombol di MainActivity (Compose) yang membuka FragmentActivity melalui Intent (`context.startActivity(Intent(context, FragmentActivity::class.java))`).

**Roadmap items updated:** 7/29 total done (Activity-Fragment-ViewBinding selesai lengkap dengan praktik Fragment). XP tetap 400/2000 Level 1 (tidak ada penambahan XP baru karena item sudah ditandai done sebelumnya, namun pemahamanFragment kini lengkap). Competencies: Knowledge 84, Implementation 86, Debugging 84, Teaching 68 (tidak berubah signifikan).

**Progress:** 100% complete

---
## 2026-08-12 18:48:49

**Topic:** RecyclerView & Adapter

**Theory:**
- RecyclerView adalah widget legacy untuk menampilkan daftar data yang dapat didaur‑ulang (recycling) – mirip dengan LazyColumn di Compose, tapi memerlukan Adapter, ViewHolder, dan layout manager.
- Adapter menghubungkan data (list) ke ViewHolder yang mem‑inflate item layout.
- LayoutManager menentukan cara item ditata: LinearLayoutManager (vertikal/horizontal) atau GridLayoutManager.

**Practice:**
- Buat Activity `RecyclerActivity` dengan layout berisi `<androidx.recyclerview.widget.RecyclerView android:id="@+id/rvItems" ...>`.
- Buat item layout `item_task.xml` (TextView + Checkbox).
- Buat `TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>` yang menerima `MutableList<Task>` dan callback `onItemClick`/`onCheckChanged`.
- Di `RecyclerActivity.onCreate`, inisialisasi `rvItems.layoutManager = LinearLayoutManager(this)` dan set adapter.
- Tambahkan contoh data dummy (mis: 10 task) untuk demonstrasi.
- Pastikan scrolling lancar, perubahan checkbox dapat mem‑update list (gunakan `notifyItemChanged`).

**Roadmap Item:** sedang *in_progress* – nanti setelah implementasi selesai, ubah status ke `done`.


**Progress:** 100% complete

---
## 2026-08-13 12:50:45

**Topic:** RecyclerView & Adapter

**Theory:**
- RecyclerView = widget legacy untuk daftar data, memakai prinsip "recycle" (daur ulang view) — hanya ~12 view dibuat untuk ribuan data.
- 3 komponen utama: LayoutManager (pemandu tata letak), Adapter (penerjemah data → tampilan), ViewHolder (pegangan view, hindari findViewById berulang).
- ViewBinding class itu DIGENERATE oleh Gradle saat build — bukan file yang ditulis tangan. Nama binding mengikuti nama file XML (items_task.xml → ItemsTaskBinding). Kalau binding "belum muncul", jalankan Build/Make Project (atau invalidate cache kalau IDE lupa).
- Overload inflate: `inflate(inflater)` vs `inflate(inflater, parent, attachToParent)` — argumen `parent` wajib agar item tahu ukuran induknya.
- compare dengan LazyColumn Compose: LazyColumn cuma 5 baris vs RecyclerView 50+ baris — alasan Compose lebih produktif (pertanyaan favorit interviewer).

**Practice:**
- `activity_recycler.xml`: ConstraintLayout + RecyclerView (@+id/rv_tasks).
- `items_task.xml`: LinearLayout horizontal, TextView (layout_weight=1) + CheckBox.
- `TaskAdapter.kt`: extends RecyclerView.Adapter<TaskViewHolder>, override onCreateViewHolder (inflate ItemsTaskBinding), onBindViewHolder (isi title & checked, setOnCheckedChangeListener → callback), getItemCount.
- `RecyclerActivity.kt`: ComponentActivity + ViewBinding, set layoutManager = LinearLayoutManager(this), set adapter = TaskAdapter(tasks) { task, isChecked -> update list pakai copy() }.
- Berhasil: build clean, scroll RecyclerView lancar, checkbox update data.
- Lesson: error "binding belum muncul" → solusi invalidate cache (IDE lupa generate); error inflate → argumen parent hilang.

**Progress:** 100% complete

---
## 2026-08-15 22:37:55

**Topic:** Proyek 3: Layar Profil XML

**Theory:**
Layar profil dibangun dengan View System XML + RecyclerView: activity_profile.xml (ConstraintLayout + header + RecyclerView), item_profile.xml (layout item dengan data binding variable ProfileItem), ProfileAdapter (onCreateViewHolder/onBindViewHolder/getItemCount), wiring via LinearLayoutManager + adapter di ProfileActivity. Inti: adapter = mediator antara data (List) dan tampilan; position = indeks data, items.size = jumlah data (kontrak yang membuat position selalu valid); emptyList() → 0 item, list kosong tanpa crash.

**Practice:**
- ProfileItem data class (id, title, icon)
- ProfileAdapter dengan 3 override wajib
- Wiring: profileItems → LinearLayoutManager → ProfileAdapter
- Verifikasi: BUILD SUCCESSFUL, install sukses, launch sukses, topResumedActivity = ProfileActivity di Samsung SM-A057F
- Bonus debugging 4 error environment: (1) jlink not found → JRE VS Code vs JDK → org.gradle.java.home; (2) core-ktx 1.19.0 butuh compileSdk 37 → turunkan ke 1.17.0; (3) ShapeAppearance.ProfilePage not found → implicit parent style → parent=""; (4) SecurityException not exported → launcher activity wajib exported=true

**Progress:** 100% complete

---
## 2026-08-19 16:28:59

**Topic:** ViewModel & MVVM

**Theory:**
MVVM = pisahkan UI (View) dari data & logika (ViewModel). 
- View (composable): tata letak, kondisi tampilan, observer lewat collectAsState()
- ViewModel: olah data + logika bisnis, memegang state dalam StateFlow (wadah reaktif / "papan pengumuman")
- Observer: StateFlow + collectAsState() = jembatan; UI menggambar ulang otomatis saat state berubah
- Backing property pattern: `private val _tasks = MutableStateFlow(...)` + `val tasks = _tasks.asStateFlow()` — kunci pintu, mencegah mutasi dari luar (single source of truth)
- Activity = navigation root saja
- ViewModel bertahan saat rotasi (beda dengan remember yang hilang)

**Practice:**
Refactor ToDo ke MVVM:
1. Tambah dependency lifecycle-viewmodel-compose 2.6.1 (selaras dengan lifecycleRuntimeKtx — satu keluarga satu versi)
2. Buat ToDoViewModel : ViewModel() — _tasks MutableStateFlow private + tasks StateFlow public + addTask/deleteTask/toggleDone
3. MainActivity: `val todoVM: ToDoViewModel = viewModel()` + `val tasks by todoVM.tasks.collectAsState()`
4. Hapus savedStateHandle smuggling — layar Add langsung panggil todoVM.addTask(title)
5. Simplifikasi callback: onCheckedChange = { onCheckedChange(task) }
6. VERIFIKASI: putar layar → tugas tetap ada (bukti ViewModel bekerja)

**Progress:** 100% complete

---
## 2026-08-20 11:18:19

**Topic:** Coroutines & Flow

**Theory:**
- Coroutine = cara menjalankan operasi lambat tanpa membekukan UI thread. Analogi: pelayan restoran yang bisa suspend (jeda) melayani meja A, melayani meja B, lalu resume ke A.
- `suspend fun` = fungsi yang bisa dijeda & dilanjutkan tanpa memblokir thread. Hanya bisa dipanggil dari dalam coroutine (scope).
- `delay()` = simulasi jeda (misal koneksi server) — UI tetap hidup.
- `rememberCoroutineScope()` = scope coroutine yang hidup selama composable tampil; `launch { }` menugaskan coroutine di dalamnya. Import: androidx.compose.runtime.rememberCoroutineScope + kotlinx.coroutines.launch.
- StateFlow adalah anggota keluarga Flow — sudah menyentuh Flow sejak MVVM.
- Pola state loading: MutableStateFlow<Boolean> private + StateFlow public (backing property) → UI collect & tampilkan kondisi (if (isSaving) Text("Menyimpan...")).

**Practice:**
Di ToDoViewModel:
- private val _isSaving = MutableStateFlow(false) + val isSaving = _isSaving.asStateFlow()
- suspend fun addTask(title): _isSaving=true → Log.d("TODO_VM","Mulai") → delay(1000) → tambah Task → _isSaving=false → Log.d("TODO_VM","Selesai")
Di MainActivity:
- val scope = rememberCoroutineScope(); onSave = { scope.launch { todoVM.addTask(title); navController.popBackStack() } } (popBackStack di dalam launch supaya layar Add menampilkan "Menyimpan..." dulu)
- val isSaving by todoVM.isSaving.collectAsState(); AddTaskScreen(isSaving = isSaving)
Verifikasi: RUN → teks "Menyimpan...⏳" tampil 1 detik → tugas masuk → Logcat menampilkan TODO_VM: addTask: Mulai/Selesai. BERHASIL.

**Progress:** 100% complete

---
## 2026-08-20 05:12:11

**Topic:** Separation of Concerns & Clean Architecture

**Theory:**
Clean Architecture (Uncle Bob 4 lingkaran) memecah app jadi layer terpisah:
- 🧱 Entities: `domain/model/Task.kt` — pure data, zero Android imports
- 🧑‍🍳 Use Cases: `domain/usecase/TasksUseCase.kt` — 4 use case (GetTasks, AddTask, DeleteTask, ToggleDone), satu file karena domain sederhana
- 🔌 Interface Adapters: `domain/repositories/TaskRepository.kt` (interface), `data/repositories/TaskRepositoryImpl.kt`, `presentation/viewmodel/ToDoViewModel.kt`
- 🚗 Frameworks: `presentation/MainActivity.kt`, Composables

Aturan panah: domain = pusat tata surya. Domain TIDAK boleh import dari data/presentation. Data & presentation hanya import dari domain.

**Practice:**
Folder structure: data/sources, data/repositories, domain/model, domain/repositories, domain/usecase, presentation/viewmodel, presentation/tasklist, presentation/taskadd.

TaskRepository interface di domain/repositories — kontrak 4 method: task (StateFlow), addTask, deleteTask, toggleDone. TaskRepositoryImpl di data/repositories — gudang memegang _tasks sendiri.

ToDoViewModel refactor: constructor pakai 4 Use Cases, val tasks = getTask(), suspend fun addTask + _isSaving backing property, deleteTask/toggleDone delegasi ke Use Case via taskId.

MainActivity: ViewModelProvider.Factory manual (temporary, Hilt ganti nanti). TaskRepositoryImpl() dibuat di presentation layer — sengaja, karena presentation boleh tahu data layer.

Single file UseCase (TasksUseCase.kt) — valid untuk app kecil. Aturan Clean Architecture tentang arah panah, bukan jumlah file.

**Progress:** 100% complete

---
## 2026-08-21 09:29:18

**Topic:** Design Patterns Android

**Theory:**
Design Patterns ≠ Architecture Patterns ≠ Principles.

Architecture Pattern (level bangunan): MVVM, Clean Architecture — "app punya layer apa?"
Design Pattern (level kode/kelas): Repository, Singleton, Factory — "kelas ini harus dibuat gimana?"
Principle (level filosofi): SOLID, KISS, DRY — "kode ini harus ditulis gimana?"

3 Design Patterns utama Android:
- Repository: Gudang data — akses dari mana saja via interface. ToDo pakai TaskRepository (interface) + TaskRepositoryImpl (implementasi).
- Singleton: Cuma 1 instance — `object` di Kotlin. TaskRepositoryImpl dijadikan object supaya semua ViewModel pakai data yang sama.
- Factory: Pabrik objek — buat objek dengan parameter. ViewModelProvider.Factory dibutuhkan karena ViewModel punya constructor params.

Use Case boleh terima banyak repository (cross-domain operations). Transformasi data gabungan dilakukan di Use Case (domain layer), bukan ViewModel — karena Use Case paham bisnis. ViewModel hanya terima data siap tampil.

**Practice:**
TaskRepositoryImpl diubah dari `class` ke `object` (Singleton). Cara pakai: `TaskRepositoryImpl` tanpa `()`.

Factory function diperjelas: `fun todoViewModelFactory()` mengembalikan `ViewModelProvider.Factory`, di dalamnya create `ToDoViewModel` dengan 4 Use Cases, masing-masing menerima `TaskRepositoryImpl` langsung.

Use Case dengan 2 repository: `class GetTasksWithLogsUseCase(taskRepo, logRepo)` — operator invoke mengembalikan `Flow<List<TaskWithLog>>` dengan `combine()`. Entity baru `TaskWithLog(task, log)` didefinisikan di domain layer.

**Progress:** 100% complete

---
## 2026-08-26 14:45:57

**Topic:** Dependency Injection dengan Hilt

**Theory:**
Dependency Injection (DI) = teknik memberikan dependency ke kelas dari luar, bukan dibuat sendiri di dalam kelas. Hilt = wrapper Dagger untuk Android — annotation processor yang generate code injection otomatis.

Konsep Dagger:
- @Inject constructor — "Saya butuh dependency ini"
- @Provides — "Ini cara buat dependency-nya"
- @Singleton — "Cuma 1 instance seumur app"
- @Module — kumpulan @Provides
- @InstallIn — component hidup kapan (SingletonComponent = seumur app)
- @HiltViewModel — ViewModel pakai Hilt
- @AndroidEntryPoint — Activity/Fragment pakai Hilt

Alur kerja: ViewModel minta dependency → Dagger cek AppModule → Dagger buat + kirim dependency → ViewModel terima otomatis.

Composable + Hilt:
- hilt() function dari hilt-navigation-compose untuk inject langsung ke Composable
- Preview TIDAK bisa pakai Hilt — harus instance manual
- Strategi code coverage: Preview (visual, manual instance) → Unit Test (logic, fake instance) → UI Test (integration, Hilt)

**Practice:**
Setup Hilt: tambah dependencies di libs.versions.toml (hilt 2.59.2, hilt-navigation-compose 1.4.0, ksp 2.2.10-2.0.2), tambah plugins di build.gradle.kts, buat ToDoApplication.kt dengan @HiltAndroidApp, daftar di AndroidManifest.xml.

AppModule di package di/ — berisi @Provides untuk TaskRepository (Singleton), 4 UseCases. @InstallIn(SingletonComponent::class).

ToDoViewModel: @HiltViewModel + @Inject constructor — hapus manual factory, cukup 1 baris hiltViewModel() di Activity.

Debug lessons:
- KSP version harus match Kotlin version (2.2.10 → 2.2.10-2.0.2)
- Hilt 2.57.1 tidak compatible AGP 9.x → upgrade ke 2.59.2
- android.disallowKotlinSourceSets=false untuk KSP + AGP 9 built-in Kotlin
- compileSdk 37.1 untuk hilt-navigation-compose 1.4.0
- DuplicateBindings = 2 file Module — hapus salah satu
- Flow.map() menghasilkan Flow, bukan StateFlow — tambah .stateIn()

**Progress:** 100% complete

---
## 2026-08-26 15:48:12

**Topic:** Proyek 4: Refactor To-Do List 🚀

**Theory:**
Refactor adalah mengubah struktur kode tanpa mengubah fungsionalitasnya. Clean Architecture yang konsisten: setiap layer hanya tahu layer di bawahnya.

**Yang dikerjakan:**
1. TaskFormatter dipindah dari presentation/utils → domain/usecase/TaskFormatterUseCase.kt (transformasi data = bisnis logic)
2. Hardcoded Intent switching di MainActivity dihapus (dead code dari Proyek 3)
3. onItemTask dibiarkan kosong dengan TODO comment sebagai technical debt tracker

**Pemahaman yang dibuktikan:**
- Clean Architecture dependency direction (domain ↔ presentation)
- @Singleton vs non-Singleton (state vs stateless objects)
- Preview sebagai development tool (pengecualian DI rules)
- Hardcoded navigation = bad practice (fragile code)
- TODO comment sebagai technical debt tracker

**Arsitektur final:**
- data/repositories/ → TaskRepositoryImpl (Singleton)
- di/ → AppModule (Hilt providers)
- domain/model/ → Task data class
- domain/repositories/ → TaskRepository interface
- domain/usecase/ → 5 Use Cases
- presentation/viewmodel/ → ToDoViewModel (Hilt inject)
- presentation/ → Composable screens

**Progress:** 100% complete

---
## 2026-08-26 15:49:53

**Topic:** Proyek 4: Refactor To-Do List

**Theory:**
Refactor adalah mengubah struktur kode tanpa mengubah fungsionalitasnya. Clean Architecture yang konsisten: setiap layer hanya tahu layer di bawahnya.

**Yang dikerjakan:**
1. TaskFormatter dipindah dari presentation/utils → domain/usecase/TaskFormatterUseCase.kt (transformasi data = bisnis logic)
2. Hardcoded Intent switching di MainActivity dihapus (dead code dari Proyek 3)
3. onItemTask dibiarkan kosong dengan TODO comment sebagai technical debt tracker

**Pemahaman yang dibuktikan:**
- Clean Architecture dependency direction (domain ↔ presentation)
- @Singleton vs non-Singleton (state vs stateless objects)
- Preview sebagai development tool (pengecualian DI rules)
- Hardcoded navigation = bad practice (fragile code)
- TODO comment sebagai technical debt tracker

**Arsitektur final:**
- data/repositories/ → TaskRepositoryImpl (Singleton)
- di/ → AppModule (Hilt providers)
- domain/model/ → Task data class
- domain/repositories/ → TaskRepository interface
- domain/usecase/ → 5 Use Cases
- presentation/viewmodel/ → ToDoViewModel (Hilt inject)
- presentation/ → Composable screens

**Progress:** 100% complete

---
## 2026-08-27 10:57:50

**Topic:** Retrofit & JSON Parsing

**Theory:**
Retrofit = HTTP client untuk Android. Cara kerja:
1. API Interface = "daftar menu" (definisi endpoint)
2. Retrofit Instance = "koki" (baca menu, kirim request)
3. Response = "makanan jadi" (data dari server)

Dependencies:
- retrofit:2.9.0 (engine utama)
- converter-gson:2.9.0 (parser JSON)
- okhttp logging-interceptor:4.12.0 (debug, opsional)

Analogi: Retrofit = kurir online yang mengambil data dari API → kirim ke app-mu.

**Practice:**
1. API Interface dengan @GET, @Query annotations
2. Data class untuk response (WeatherResponse)
3. RetrofitClient (singleton) dengan GsonConverterFactory
4. Repository untuk abstract API calls
5. ViewModel dengan StateFlow (weather, isLoading, errorMessage)
6. Composable UI dengan loading spinner, error message, data display

**Key Concepts:**
- suspend functions untuk coroutines compatibility
- @GET("path") untuk HTTP GET requests
- @Query("param") untuk URL query parameters
- GsonConverterFactory untuk auto JSON parsing
- try-catch untuk error handling

**Progress:** 100% complete

---
## 2026-08-27 11:52:58

**Topic:** State Management untuk API

**Theory:**
State Management untuk API menggunakan **sealed class** + **StateFlow**:
- **Sealed class** (`WeatherState`) merepresentasikan semua kemungkinan state: `Idle`, `Loading`, `Success(data)`, `Error(message)`
- **StateFlow** (bukan LiveData) — modern, coroutine-friendly, lifecycle-aware
- **Collect di Compose**: `val state by viewModel.state.collectAsState()` → otomatis trigger recomposition saat state berubah

**Pattern:**
```kotlin
sealed class WeatherState {
    data object Idle: WeatherState()
    data object Loading: WeatherState()
    data class Success(val data: WeatherResponse): WeatherState()
    data class Error(val message: String): WeatherState()
}

// ViewModel
_state.value = WeatherState.Loading
try {
    val result = repository.getWeather(lat, lng)
    _state.value = WeatherState.Success(result)
} catch (e: Exception) {
    _state.value = WeatherState.Error(e.message)
}

// Compose UI
when(val currentState = state) {
    is WeatherState.Idle -> { /* ... */ }
    is WeatherState.Loading -> { CircularProgressIndicator() }
    is WeatherState.Success -> { /* tampilkan data */ }
    is WeatherState.Error -> { /* tampilkan error */ }
}
```

**Practice:**
Bug umum yang di-fix: ViewModel punya 4 StateFlow paralel tapi UI hanya observe 1 → state tidak pernah berubah. Solusi: gunakan 1 sealed class state saja.

**Key Insight:**
Satu state flow mengalir dari ViewModel → Compose. Tidak ada race condition, tidak ada state yang stale. Ini pattern yang dipakai di production apps.

**Progress:** 100% complete

---
## 2026-08-27 16:11:09

**Topic:** Task: Rename package wheater_app → weather_app

Selesai: semua file menggunakan package name yang konsisten sekarang.

**Progress:** 100% complete

---
## 2026-08-28 12:30:28

**Topic:** Proyek 5: Aplikasi Cuaca

**Theory:**
Clean Architecture production-grade untuk Networking: Domain di tengah (Weather entity, WeatherRepository interface, GetCurrentWeatherUseCase), Data di luar (WeatherRepositoryImpl dengan mapping WeatherResponse DTO -> Weather domain, WeatherApi, Retrofit), Presentation di luar (WeatherViewModel dengan WeatherState sealed Idle/Loading/Success/Error + StateFlow, WeatherScreen dengan collectAsState). Dependency rule: Data boleh import Domain, Domain tidak boleh import Data. DI: AppModule object menyediakan OkHttp/Retrofit/WeatherApi sebagai @Singleton @Provides, interface di-bind via @Provides, UseCase pakai @Inject constructor sehingga tidak perlu @Provides manual. Build SUCCESS, GRC low risk.

**Practice:**
1) Cleanup: konsisten package weather_app, hapus folder theme duplikat presentations/ui/ui/theme, tambah INTERNET permission, fix KSP SingletonComponent import
2) Domain: buat domain/entities/Weather (10 field), domain/repository/WeatherRepository (suspend fun getWeather): Weather, domain/usecase/GetCurrentWeatherUseCase @Inject
3) Data: WeatherRepositoryImpl mapping response.current.temperature2M -> Weather, AppModule object dengan BASE_URL const
4) Presentation: WeatherState.Success memegang Weather domain (bukan DTO), ViewModel inject UseCase
Code Review 59->63/100. Paham tradeoff @Inject vs @Provides.

**Context from Coach:** Aplikasi Cuaca selesai dipoles menjadi production-grade dengan 3 milestone (M1 Cleanup, M2 Arsitektur, M3 Review) - semua done, build success.

**Progress:** 100% complete

---
## 2026-08-30 11:53:13

**Topic:** Room Database

**Theory:**
Room Database = lemari arsip permanen (SQLite yang dibungkus). 3 serangkai: @Entity (data class kartu), @Dao (interface petugas dengan @Query/@Insert/@Delete), @Database (abstract class lemari). Kenapa penting: data tidak hilang saat app ditutup, bisa offline, Flow untuk observe perubahan. Hilt: AppModule menyediakan Room.databaseBuilder + Dao sebagai Singleton, App pakai @HiltAndroidApp, MainActivity pakai @AndroidEntryPoint.

**Practice:**
notes-app: buat data/models/schemas/NoteEntity (@Entity tableName notes, @PrimaryKey autoGenerate), NoteDao (getAllNotes Flow, insert, delete, getNoteById), data/models/databases/NoteDatabase. Fix: hapus spasi di libs.versions.toml group androidx.room, betulkan typo compailer->compiler, fix OnConflictStrategy import, tambah return type, tambah tableName. Buat domain/entities/Note, domain/repositories/NoteRepository (Flow), data/repositories/NoteRepositoryImpl dengan mapping Entity<->Note, domain/usecase 4 UseCase terpisah dengan @Inject, presentation/viewmodel/NoteViewModel @HiltViewModel dengan StateFlow notes + selectedNote, fix showDetailNote pakai StateFlow bukan var. Build SUCCESS.

**Progress:** 100% complete

---
## 2026-08-30 23:55:54

**Topic:** Proyek 6: Catatan Harian

**Theory:**
Room + Clean + Compose end-to-end: Entity @Entity tableName notes, Dao @Dao Flow, Database abstract, AppModule object Room.databaseBuilder singleton + Dao + Repository mapping Entity<->Note, domain Note + NoteRepository Flow, 4 UseCases @Inject terpisah, ViewModel @HiltViewModel notes StateFlow, UI NoteListScreen LazyColumn key id + AddNoteDialog remember + delete IconButton, MainActivity @AndroidEntryPoint hiltViewModel. Migration 1->2 DEFAULT 0, inMemory test dengan Robolectric.

**Practice:**
notes-app: buat NoteEntity, NoteDao (fix OnConflictStrategy import & return type), NoteDatabase, Hilt AppModule + App.kt, domain Note + NoteRepository, NoteRepositoryImpl mapping, UseCases, ViewModel, UI List + Dialog. Fix: hapus spasi group androidx.room, typo compailer->compiler, fix @Before/@After + delete->insert di test, fix Icon import & viewModel.notes, tambah priority field di Entity biar sinkron migration. Build SUCCESS, test 2/2 passed, code review 71/100, GRC medium.

**Context from Coach:** Proyek 6 selesai 3 sprint (M1 List, M2 Form, M3 Review) + tips pro migration & Robolectric test.

**Progress:** 100% complete

---
## 2026-08-31 23:24:18

**Topic:** Unit Testing

**Theory:**
Unit Testing = test 1 fungsi/logic terisolasi dengan mock/fake. Mock = boneka remote control (mockk) yang diatur coEvery & diverify, Fake = duplikat mini yang jalan beneran (inMemory). TDD = RED (test gagal/compile error karena code belum ada) -> GREEN (bikin code minimal biar hijau) -> REFACTOR (rapikan nama). Robot CI + coverage gate cegah regression.

**Practice:**
weather-app TDD air-quality: buat domain AirQuality, AirQualityRepository, GetAirQualityUseCase -> test UseCase RED->GREEN, buat data AirQualityResponse/Current + AirQualityApi + AirQualityRepositoryImpl mapping -> test Repository RED->GREEN (fix Any() & pm2_5 naming), ViewModel test GREEN langsung (sudah ada fetchAirQuality). Fix typo aapi->api. Semua test passed. Paham mock vs fake, expectedData naming.

**Progress:** 100% complete

---
