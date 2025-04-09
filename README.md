# 🚍 Dashfleet

Dashfleet adalah sebuah aplikasi simulasi kontrol armada bus yang memungkinkan pengguna untuk memantau dan melacak status bus secara real-time, seperti status mesin, kecepatan, kondisi pintu, serta rute perjalanan.

## 📱 Fitur Utama

- 🔧 **Simulasi Engine Status**  
  Mesin dapat menyala dan mati sesuai kondisi rute.

- 🚪 **Status Pintu Bus**  
  Menampilkan kondisi pintu (terbuka/tertutup) dengan notifikasi khusus jika pintu terbuka saat bus berjalan.

- ⚡ **Kecepatan Bus**  
  Kecepatan dihitung berdasarkan pergerakan titik koordinat dan diperbarui secara berkala.

- 📍 **Live Tracking**  
  Visualisasi pergerakan bus di peta menggunakan Google Maps.

- ⚠️ **In-App Alert**  
  Notifikasi ditampilkan saat terjadi kondisi seperti:
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

## 🧩 Struktur Data

### `bus_data.json`
```json
[
  {
    "id": "bus1",
    "name": "TransJakarta",
    "engineStatus": "off",
    "speed": "0",
    "doorStatus": "closed"
  },
  ...
]
