import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** Refined Abstraction: rekap penjualan dan utang dalam satu hari. */
public class LaporanHarian extends DokumenTransaksi {
    private final LocalDate tanggal;
    private final List<StrukPenjualan> daftarStruk = new ArrayList<>();
    private final List<NotaUtang> daftarUtang = new ArrayList<>();

    public LaporanHarian(MediaKeluaran media, LocalDate tanggal) {
        super(media);
        this.tanggal = tanggal;
    }

    public void tambahStruk(StrukPenjualan struk) { daftarStruk.add(struk); }
    public void tambahUtang(NotaUtang nota) { daftarUtang.add(nota); }

    public double getTotalPenjualan() {
        double total = 0;
        for (StrukPenjualan s : daftarStruk) total += s.getTotal();
        return total;
    }

    public double getTotalUtangBaru() {
        double total = 0;
        for (NotaUtang n : daftarUtang) total += n.getNominalUtang();
        return total;
    }

    @Override
    protected String getJudul() {
        return "LAPORAN HARIAN " + tanggal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    protected String buatIsi() {
        StringBuilder sb = new StringBuilder();
        sb.append("Jumlah transaksi : ").append(daftarStruk.size()).append("\n");
        sb.append("Total penjualan  : ").append(FormatUtil.rupiah(getTotalPenjualan())).append("\n");
        sb.append("Utang baru       : ").append(daftarUtang.size()).append(" nota, ")
          .append(FormatUtil.rupiah(getTotalUtangBaru())).append("\n");
        if (!daftarStruk.isEmpty()) {
            double rata = getTotalPenjualan() / daftarStruk.size();
            sb.append("Rata-rata/struk  : ").append(FormatUtil.rupiah(rata)).append("\n");
        }
        return sb.toString();
    }
}
