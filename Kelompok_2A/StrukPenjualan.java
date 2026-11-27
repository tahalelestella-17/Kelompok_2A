import java.util.ArrayList;
import java.util.List;

/** Refined Abstraction: struk belanja pelanggan. */
public class StrukPenjualan extends DokumenTransaksi {
    private final String noStruk;
    private final String kasir;
    private final List<ItemTransaksi> items = new ArrayList<>();
    private double bayar;

    public StrukPenjualan(MediaKeluaran media, String noStruk, String kasir) {
        super(media);
        this.noStruk = noStruk;
        this.kasir = kasir;
    }

    public void tambahItem(String nama, int jumlah, double harga) {
        items.add(new ItemTransaksi(nama, jumlah, harga));
    }

    public void setBayar(double bayar) { this.bayar = bayar; }

    public double getTotal() {
        double total = 0;
        for (ItemTransaksi i : items) total += i.getSubtotal();
        return total;
    }

    @Override
    protected String getJudul() { return "STRUK PENJUALAN #" + noStruk; }

    @Override
    protected String buatIsi() {
        if (items.isEmpty()) throw new IllegalStateException("Struk belum memiliki item");
        StringBuilder sb = new StringBuilder();
        sb.append("Tanggal : ").append(formatWaktu()).append("\n");
        sb.append("Kasir   : ").append(kasir).append("\n");
        sb.append("--------------------------------\n");
        for (ItemTransaksi i : items) {
            sb.append(String.format("%-14s %2d x %10s%n", i.getNama(), i.getJumlah(),
                    FormatUtil.rupiah(i.getHarga())));
            sb.append(String.format("%32s%n", FormatUtil.rupiah(i.getSubtotal())));
        }
        sb.append("--------------------------------\n");
        sb.append("TOTAL   : ").append(FormatUtil.rupiah(getTotal())).append("\n");
        sb.append("BAYAR   : ").append(FormatUtil.rupiah(bayar)).append("\n");
        sb.append("KEMBALI : ").append(FormatUtil.rupiah(Math.max(0, bayar - getTotal()))).append("\n");
        return sb.toString();
    }
}
