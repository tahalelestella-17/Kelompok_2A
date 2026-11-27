import java.text.NumberFormat;
import java.util.Locale;

/** Pembantu format rupiah supaya semua dokumen seragam. */
public final class FormatUtil {
    private FormatUtil() {}

    public static String rupiah(double nilai) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);
        return "Rp " + nf.format(nilai);
    }
}
