/** Implementor dummy untuk uji Abstraction tanpa menunggu kode Dev. */
public class MediaKonsolTest implements MediaKeluaran {
    private final String nama;
    public MediaKonsolTest(String nama) { this.nama = nama; }
    @Override public void kirim(String judul, String isi) {
        System.out.println("=== [" + nama + "] " + judul + " ===");
        System.out.println(isi);
    }
    @Override public String getNamaMedia() { return nama; }
}
