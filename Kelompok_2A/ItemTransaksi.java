/** Satu baris barang dalam transaksi. */
public class ItemTransaksi {
    private final String nama;
    private final int jumlah;
    private final double harga;

    public ItemTransaksi(String nama, int jumlah, double harga) {
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh negatif");
        this.nama = nama;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public String getNama() { return nama; }
    public int getJumlah() { return jumlah; }
    public double getHarga() { return harga; }
    public double getSubtotal() { return jumlah * harga; }
}
