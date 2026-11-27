Sistem pencetakan dokumen transaksi toko (struk penjualan, laporan harian, nota utang)
yang dapat dikeluarkan lewat berbagai media (printer thermal, PDF, WhatsApp)
menggunakan **Bridge Pattern**.


## 1. Latar Belakang Masalah
Toko perlu menghasilkan tiga jenis dokumen (struk penjualan, laporan harian, nota utang) lewat tiga media (printer thermal, PDF, WhatsApp). Jika tiap kombinasi dibuat menjadi satu kelas, dibutuhkan 3 x 3 = 9 kelas, kodenya berulang, dan sulit dikembangkan setiap ada dokumen atau media baru. Proyek ini memakai Bridge Pattern untuk memisahkan dokumen (Abstraction) dari media keluaran (Implementor), sehingga cukup 3 + 3 = 6 kelas dan media dapat diganti kapan saja, termasuk lewat fitur fallback saat media utama gagal.

## 2. Solusi: Bridge Pattern
Program dipisah menjadi dua sisi yang berdiri sendiri:

| Sisi | Fungsi | Kelas |
|---|---|---|
| **Abstraction** | Apa yang dicetak | `DokumenTransaksi`, `StrukPenjualan`, `LaporanHarian`, `NotaUtang` |
| **Implementor** | Dicetak/dikirim lewat apa | `MediaKeluaran`, `PrinterThermal`, `DokumenPDF`, `WhatsApp` |

Penghubungnya adalah variabel `media` di dalam `DokumenTransaksi`.
`MediaFactory` membuat media sesuai `config.properties`, dan fitur *fallback*
memakai media cadangan jika media utama gagal.


## 3. Struktur Folder
```
KELOMPOK-2A/
├── src/
│   └── main/
│       └── java/
│           └── toko/
│               ├── DokumenTransaksi.java     Abstraction: kelas induk (abstract)
│               ├── StrukPenjualan.java       Abstraction: struk belanja
│               ├── LaporanHarian.java        Abstraction: rekap harian
│               ├── NotaUtang.java            Abstraction: nota utang
│               ├── MediaKeluaran.java        Implementor: interface (versi sementara)
│               ├── ContohPenggunaan.java     Contoh pakai: ganti media Printer -> WhatsApp
│               └── TesAbstraction.java       Pengujian Abstraction (7 tes)
├── docs/
│   └── laporan.pdf
└── README.md
```

## 4. Persyaratan
- Java JDK [VERSI 0.60.0]
- [Library lain pustaka PDF]

## 5. Cara Menjalankan

### Program Java
```bash
cd java
javac *.java
java [KELOMPOK-2A]
```

Untuk pengujian bagian Abstraction:
```bash
java MainAbstractionDemo.java
```


## 6. Contoh Penggunaan
```java
StrukPenjualan struk = new StrukPenjualan(printer, "001", "Stella");
struk.tambahItem("Beras 5kg", 1, 68000);
struk.tambahItem("Minyak 1L", 2, 18000);
struk.setBayar(110000);
struk.kirim();          // keluar lewat printer

struk.setMedia(wa);     // ganti media
struk.kirim();          // dokumen yang sama, keluar lewat WhatsApp
```

**Contoh keluaran:**
```
=== [PrinterThermal] STRUK PENJUALAN #001 ===
Tanggal : 27-11-2026 19:49
Kasir   : Stella
--------------------------------
Beras 5kg       1 x  Rp 68.000
                       Rp 68.000
Minyak 1L       2 x  Rp 18.000
                       Rp 36.000
--------------------------------
TOTAL   : Rp 104.000
BAYAR   : Rp 110.000
KEMBALI : Rp 6.000

>> Ganti media ke WhatsApp tanpa ubah kelas struk
=== [WhatsApp] STRUK PENJUALAN #001 ===
Tanggal : 27-11-2026 19:49
Kasir   : Stella
--------------------------------
Beras 5kg       1 x  Rp 68.000
                       Rp 68.000
Minyak 1L       2 x  Rp 18.000
                       Rp 36.000
--------------------------------
TOTAL   : Rp 104.000
BAYAR   : Rp 110.000
KEMBALI : Rp 6.000

=== [WhatsApp] NOTA UTANG - Mama Yosef ===
Tanggal     : 27-11-2026 19:49
Pelanggan   : Mama Yosef
Total utang : Rp 150.000
Sudah bayar : Rp 50.000
SISA UTANG  : Rp 100.000
Jatuh tempo : 11-12-2026
Catatan     : Belanja bulanan

=== [PrinterThermal] LAPORAN HARIAN 27-11-2026 ===
Jumlah transaksi : 1
Total penjualan  : Rp 104.000
Utang baru       : 1 nota, Rp 150.000
Rata-rata/struk  : Rp 104.000

[OK] Struk kosong ditolak: Struk belum memiliki item
[OK] Cicilan berlebih ditolak: Cicilan melebihi sisa utang
```

## 7. Pengujian
| No | Skenario | Hasil yang diharapkan | Status |
|---|---|---|---|
| 1 | Struk 2 barang | Total Rp 104.000 | [LULUS] |
| 2 | Ganti media Printer ke WhatsApp | Isi sama, media berbeda | [LULUS] |
| 3 | Struk tanpa barang | Ditolak | [LULUS] |
| 4 | Cicilan 50.000 dari 150.000 | Sisa Rp 100.000 | [LULUS] |
| 5 | Cicilan melebihi sisa | Ditolak | [LULUS] |
| 6 | Laporan harian 1 struk + 1 utang | Total penjualan Rp 104.000 | [LULUS] |
| 7 | Media null | Ditolak | [LULUS] |
| 8 | Media utama gagal, fallback aktif | Pindah ke media cadangan | [LULUS] |

## 8. Pembagian Tugas
| Anggota | Tanggung jawab |
|---|---|
| **Abigael** | Analisis dan perancangan sistem, UML Class & Sequence Diagram, program Python, bagian awal laporan, penggabungan hasil |
| **Stella** | Abstraction (`DokumenTransaksi`, `StrukPenjualan`, `LaporanHarian`, `NotaUtang`), kode Java Abstraction, pengujian, laporan Abstraction dan cara kerja sistem |
| **Dev** | Implementor (`MediaKeluaran`, `PrinterThermal`, `DokumenPDF`, `WhatsApp`), `MediaFactory`, `config.properties`, fitur fallback, pengujian, laporan Implementor dan Factory |
| **Bersama** | Pengujian Java dan Python, README, video demo dan presentasi, pemeriksaan laporan |

## 9. Demo
Video demonstrasi: [LINK VIDEO]
