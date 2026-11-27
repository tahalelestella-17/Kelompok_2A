import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Refined Abstraction: nota utang pelanggan. */
public class NotaUtang extends DokumenTransaksi {
    private final String namaPelanggan;
    private final double nominalUtang;
    private double sudahDibayar;
    private final LocalDate jatuhTempo;
    private final String catatan;

    public NotaUtang(MediaKeluaran media, String namaPelanggan, double nominalUtang,
                     LocalDate jatuhTempo, String catatan) {
        super(media);
        if (nominalUtang <= 0) throw new IllegalArgumentException("Nominal utang harus lebih dari 0");
        this.namaPelanggan = namaPelanggan;
        this.nominalUtang = nominalUtang;
        this.jatuhTempo = jatuhTempo;
        this.catatan = catatan;
    }

    public void bayarCicilan(double jumlah) {
        if (jumlah <= 0) throw new IllegalArgumentException("Cicilan harus lebih dari 0");
        if (jumlah > getSisa()) throw new IllegalArgumentException("Cicilan melebihi sisa utang");
        sudahDibayar += jumlah;
    }

    public double getSisa() { return nominalUtang - sudahDibayar; }
    public double getNominalUtang() { return nominalUtang; }

    @Override
    protected String getJudul() { return "NOTA UTANG - " + namaPelanggan; }

    @Override
    protected String buatIsi() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tanggal     : ").append(formatWaktu()).append("\n");
        sb.append("Pelanggan   : ").append(namaPelanggan).append("\n");
        sb.append("Total utang : ").append(FormatUtil.rupiah(nominalUtang)).append("\n");
        sb.append("Sudah bayar : ").append(FormatUtil.rupiah(sudahDibayar)).append("\n");
        sb.append("SISA UTANG  : ").append(FormatUtil.rupiah(getSisa())).append("\n");
        sb.append("Jatuh tempo : ").append(jatuhTempo.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
        if (catatan != null && !catatan.isEmpty()) sb.append("Catatan     : ").append(catatan).append("\n");
        return sb.toString();
    }
}
