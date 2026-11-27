import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ABSTRACTION (Bridge Pattern).
 * Menyimpan referensi ke MediaKeluaran (Implementor) dan mendelegasikan
 * pengiriman ke sana. Subclass cukup menentukan JUDUL dan ISI dokumen.
 */
public abstract class DokumenTransaksi {
    protected MediaKeluaran media;                 // jembatan ke Implementor
    protected final LocalDateTime waktu = LocalDateTime.now();

    protected DokumenTransaksi(MediaKeluaran media) {
        setMedia(media);
    }

    /** Media bisa diganti kapan saja tanpa mengubah dokumen. */
    public void setMedia(MediaKeluaran media) {
        if (media == null) throw new IllegalArgumentException("Media keluaran tidak boleh null");
        this.media = media;
    }

    public MediaKeluaran getMedia() { return media; }

    protected abstract String getJudul();
    protected abstract String buatIsi();

    /** Template method: bentuk isi lalu serahkan ke media. */
    public void kirim() {
        media.kirim(getJudul(), buatIsi());
    }

    protected String formatWaktu() {
        return waktu.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
}
