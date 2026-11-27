import java.time.LocalDate;

public class MainAbstractionDemo {
    public static void main(String[] args) {
        MediaKeluaran printer = new MediaKonsolTest("PrinterThermal");
        MediaKeluaran wa = new MediaKonsolTest("WhatsApp");

        StrukPenjualan struk = new StrukPenjualan(printer, "001", "Stella");
        struk.tambahItem("Beras 5kg", 1, 68000);
        struk.tambahItem("Minyak 1L", 2, 18000);
        struk.setBayar(110000);
        struk.kirim();

        System.out.println(">> Ganti media ke WhatsApp tanpa ubah kelas struk");
        struk.setMedia(wa);
        struk.kirim();

        NotaUtang utang = new NotaUtang(wa, "Mama Yosef", 150000, LocalDate.now().plusDays(14), "Belanja bulanan");
        utang.bayarCicilan(50000);
        utang.kirim();

        LaporanHarian lap = new LaporanHarian(printer, LocalDate.now());
        lap.tambahStruk(struk);
        lap.tambahUtang(utang);
        lap.kirim();

        // Uji kasus salah
        try {
            new StrukPenjualan(printer, "002", "Stella").kirim();
        } catch (IllegalStateException e) {
            System.out.println("[OK] Struk kosong ditolak: " + e.getMessage());
        }
        try {
            utang.bayarCicilan(999999);
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Cicilan berlebih ditolak: " + e.getMessage());
        }
    }
}
