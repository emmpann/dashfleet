# 🚍 Dashfleet

Dashfleet adalah sebuah aplikasi simulasi kontrol armada bus yang memungkinkan pengguna untuk memantau dan melacak status bus secara real-time, seperti status mesin, kecepatan, kondisi pintu, serta rute perjalanan.

## 📱 Fitur Utama
Ada 5 fitur utama yang dikembangkan pada aplikasi simulasi ini. Tentunya ke-5 fitur ini hanya menampilkan fake data atau data yang telah disimulasikan sehingga data berjalan sebagai mana mestinya.

- 🔧 **Simulasi Engine Status**  
  Mengetahui kondisi mesin (on/off).

- 🚪 **Status Pintu Bus**  
  Menampilkan status pintu bus (open/close).

- ⚡ **Kecepatan Bus**  
  Memperlihatkan kecepatan bus secara realtime, pada simulasi ini kecepatan dihitung berdasarakan jarak titik koordinat.

- 📍 **Live Tracking**  
  Visualisasi pergerakan bus di peta menggunakan Google Maps. Simulasi pergerakkan bus memanfaatkan kumpulan koordinat yang dikumpulkan pada sebuah file json, sehingga bus terlihat bergerak.

- ⚠️ **In-App Alert**  
  Sebuah alert yang ditampilkan saat terjadi kondisi seperti:
    - Kecepatan melebihi 80 km/h
    - Pintu terbuka saat bus berjalan
    - Mesin menyala/mati

## 🏗️ Arsitektur

Aplikasi ini menggunakan **MVVM (Model-View-ViewModel)** sebagai arsitektur utama untuk menjaga pemisahan tanggung jawab dan kemudahan dalam pengujian.

### Teknologi yang digunakan:
- Kotlin
- Android Jetpack (LiveData, ViewModel, Navigation)
- Dagger Hilt (Dependency Injection)
- Google Maps API
- JSON (simulasi data lokal)

## 🛠️ Clone Repository
Clone repositori ke lokal dengan perintah berikut:

```bash
git clone https://github.com/emmpann/dashfleet.git
cd dashfleet
```

## 🖼️ Screenshots
<p align="center">
  <img src="images/home.png" width="200" alt="Home" />
  <img src="images/live_track_bus.png" width="200" alt="Live Track" />
</p>